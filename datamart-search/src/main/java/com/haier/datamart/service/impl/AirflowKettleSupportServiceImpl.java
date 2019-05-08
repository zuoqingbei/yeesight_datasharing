package com.haier.datamart.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.haier.datamart.airflowsupport.AirFlowHandler;
import com.haier.datamart.airflowsupport.model.KettleSupport;
import com.haier.datamart.airflowsupport.util.AirFlowDagStateOKCall;
import com.haier.datamart.airflowsupport.util.AirFlowDagStateRunnner;
import com.haier.datamart.airflowsupport.util.FileRefferUtil;
import com.haier.datamart.aspect.RecordLog;
import com.haier.datamart.entity.AirflowKettleSupport;
import com.haier.datamart.exception.BusinessException;
import com.haier.datamart.mapper.AirflowKettleSupportMapper;
import com.haier.datamart.service.IAirflowKettleSupportService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.utils.CornShowUtil;
import com.haier.datamart.utils.StringUtil;
import com.haier.datamart.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * airflow_kettle脚本维护表服务实现类
 * @author 刘志龙123
 * @date 2018-11-13
 */
@Service
@Transactional
public class AirflowKettleSupportServiceImpl extends ServiceImpl<AirflowKettleSupportMapper, AirflowKettleSupport> implements IAirflowKettleSupportService {
    private Logger logger = LoggerFactory.getLogger(AirflowKettleSupportServiceImpl.class);
    @Autowired
    private AirflowKettleSupportMapper airflowKettleSupportMapper;
    private static  final String SIMPLE_KETTLE_TEMPLATE="airflow_support/kettle_template/simple_template.py";

    //重写add方法
    public boolean insert(AirflowKettleSupport entity){
        if(StringUtils.isBlank(entity.getId())){
            //新增
            entity.setId(UUIDUtils.getUuid());
            entity.setCreateDate(new Date());
            //校验必填字段
            if(StringUtils.isBlank(entity.getJobName())
                    ||StringUtils.isBlank(entity.getBashCommand())
                    ||StringUtils.isBlank(entity.getSchedule())
                    ||StringUtils.isBlank(entity.getSystem())){
                throw new BusinessException("必填字段未填写");

            }
            //判断是否重复
            //重复规则 同一个系统下不能有两个有效的重复名称
            EntityWrapper<AirflowKettleSupport> wrapper = new EntityWrapper<AirflowKettleSupport>();
            wrapper.where("del_flag={0}", "0");
            wrapper.eq("job_name", String.valueOf(entity.getJobName()));
            wrapper.eq("system", String.valueOf(entity.getSystem()));
            List<AirflowKettleSupport> list = this.selectList(wrapper);
            if(list!=null&&list.size()>0){
                throw new BusinessException("重复的数据，同一个系统不能有相同的job名称！");
            }
            //设置dag_name
            entity.setDagName(this.dagNameCreater(entity));
            if(super.insert(entity)){
                //创建完毕之后 创建dagfile
                AirFlowHandler.writedag(entity.getDagName(),this.construtKettleContent(entity));
                //TODO 此处需要做获取刷新状态的服务
                new Thread(new AirFlowDagStateRunnner(entity.getDagName(), new AirFlowDagStateOKCall() {
                    @Override
                    public void call() {
                        //更新状态
                        AirflowKettleSupport target = selectById(entity.getId());
                        target.setRemoteReady("1");
                        update(target);
                        logger.info("更新remoteReady状态！");
                    }
                })).start();
            }else{
                throw new BusinessException("");
            }
        }else{
            throw new BusinessException("新增主键必须为空");
        }
        return true;
    }
    //重写update方法 只能修改命令和备注 其他的不能改
    public boolean updateById(AirflowKettleSupport entity){
        AirflowKettleSupport target = this.selectById(entity.getId());
        target.setSchedule(entity.getSchedule());
        target.setBashCommand(entity.getBashCommand());
        target.setRemarks(entity.getRemarks());
        target.setUpdateDate(new Date());
        target.setUpdateBy(entity.getUpdateBy());
        //TODO 重新执行文件 并刷新
        if(super.updateById(entity)){
                AirFlowHandler.writedag(target.getDagName(),this.construtKettleContent(target));
                //刷新文件
                //这个操作忽略失败
               try{
                   AirFlowHandler.refreshdag(target.getDagName());
               }catch (Exception e){
                   logger.info("刷新dagname失败，不过这里策略默认忽略"+e.getMessage());
               }
            return true;
        }
        return  false;
    };
    //重写update方法 只能修改命令和备注 其他的不能改
    public boolean updateByIdJust(AirflowKettleSupport entity){
        AirflowKettleSupport target = this.selectById(entity.getId());
        target.setSchedule(entity.getSchedule());
        target.setBashCommand(entity.getBashCommand());
        target.setRemarks(entity.getRemarks());
        target.setUpdateDate(new Date());
        target.setUpdateBy(entity.getUpdateBy());
        //TODO 重新执行文件 并刷新
        if(super.updateById(entity)){
            return true;
        }
        return  false;
    };
    /**
     * 创建dag_name
     * 命名规则 system +job_name +随机码
     * @param kettleSupport
     * @return
     */
   private String dagNameCreater(AirflowKettleSupport kettleSupport){
       String dagName = "";
       String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
       dagName = currentTime+"_"+kettleSupport.getSystem()+"_"+ kettleSupport.getJobName();
       return  dagName;
   }

    @Override
    public void run(String id) {
        EntityWrapper<AirflowKettleSupport> wrapper = new EntityWrapper<AirflowKettleSupport>();
        wrapper.where("del_flag={0}", "0");
        wrapper.eq("id", id);
        AirflowKettleSupport	entity=this.selectOne(wrapper);
        //创建3分钟之后才需要进行如下判断
        if(entity.getCreateDate() !=null){
            if(System.currentTimeMillis() - entity.getCreateDate().getTime()<(180*1000)){
                throw new BusinessException("当前job在AirFlow服务器上未处于就绪状态，请等待5分钟之后重试。");
            }

        }
        //未就绪的话 返回位处于就绪状态
        if(!"1".equals(entity.getRemoteReady())){
            throw new BusinessException("当前job在AirFlow服务器上未处于就绪状态，请等待5分钟之后重试。");
        }
        AirFlowHandler.run(entity.getDagName());
        entity.setRunningState("1");
        super.updateById(entity);
    }

    @Override
    public void pause(String id) {
        EntityWrapper<AirflowKettleSupport> wrapper = new EntityWrapper<AirflowKettleSupport>();
        wrapper.where("del_flag={0}", "0");
        wrapper.eq("id", id);
        AirflowKettleSupport entity=this.selectOne(wrapper);
        AirFlowHandler.pause(entity.getDagName());
        entity.setRunningState("0");
        super.updateById(entity);
    }

    @Override
    public String testBash(@NotNull String execUser, @NotNull String jobName,@NotNull String bashCommand) {
        logger.info("execUser:"+execUser+",jobName:"+jobName+",bashCommand:"+bashCommand);
        return AirFlowHandler.testCommmand(jobName,bashCommand);
    }

    @Override
    public boolean stopanddelete(AirflowKettleSupport entity) {

        try{
            this.pause(entity.getId());
        }catch (Exception e){
        }
        AirflowKettleSupport target = this.selectById(entity.getId());
        target.setUpdateDate(new Date());
        target.setUpdateBy(entity.getUpdateBy());
        target.setDelFlag("1");
        return super.updateById(target);
    }

    @Override
    public List<String> getRecentFires(AirflowKettleSupport entity) {
        if(StringUtils.isBlank(entity.getSchedule())||StringUtils.isBlank(entity.getStartDate())){
            throw  new BusinessException("请输入调度时间和开始时间。");
        }
        /**
         *
         * 1 因为airflow支持的corn与标准的不一致 没有秒字段 需要加上秒
         * 2 最后一位表示星期的 airflow支持 *，标准的是? 需要替换掉
         * 3 airflow的调度，会晚一个周期，需要过滤掉
         */
        String cornOgin = entity.getSchedule();
        cornOgin = cornOgin.trim();
        if(cornOgin.split(" ").length !=5){
            throw new BusinessException("表达式格式错误，必须是5段格式的表达式，类似 0 0 * * *");
        }
        cornOgin = "0 "+cornOgin;//加上秒
        //替换最后的?
       Character last = cornOgin.charAt(cornOgin.length()-1);
        if(last.equals('*')){
            cornOgin =    cornOgin.substring(0,cornOgin.length()-1) +"?";
        }
        SimpleDateFormat ds  =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<Date> result = CornShowUtil.getRecent(cornOgin,ds.parse(entity.getStartDate()),11);
            List<String> trueResult = new ArrayList<>();
            boolean skipFirst = true;//跳过第一个
            for (Date item :result){
                if(skipFirst){
                    skipFirst = false;
                    continue;
                }
                trueResult.add(ds.format(item));
            }
            return trueResult;
        } catch (ParseException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 获取调度执行的成功 失败的次数
     * @param id
     * @return
     */
    @Override
    public Map<String, Integer> getRunningState(String id) {
        AirflowKettleSupport target = this.selectById(id);
        //获取id
        return AirFlowHandler.getRunningState(target.getDagName());
    }

    /**
     * 获取调度执行成功或者失败的记录（如果状态是空 查询所有的 按照时间进行排序）
     * @param id
     * @param state
     * @return
     */
    @Override
    public List<Map<String, Object>> getRunningStateList(String id, String state) {
        AirflowKettleSupport target = this.selectById(id);
        return null;
    }

    /**
     *  dag_id
     state
     run_id
     start_date
     end_date
     execution_date
     * @param id
     * @param state
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Map<String, Object> getRunningStateList(String id, String state, @NotNull Integer pageNum, Integer pageSize) {
        AirflowKettleSupport target = this.selectById(id);
        Map result = AirFlowHandler.getRunningStateList(target.getDagName(),state,pageNum,pageSize);
        Integer allCount  = (Integer) result.get("allCount");
        List<Map<String,Object>> list = (List<Map<String, Object>>) result.get("list");
        Map<String, Object> resulte = new HashMap();
        resulte.put("allCount",allCount);
        resulte.put("pageNum",pageNum);
        resulte.put("pageSize",pageSize);
         List<Map> result1 = list.stream().map(e->{
             Map<String,String > iteme= new HashMap<String, String>();
             iteme.put("dag_id",e.get("dag_id").toString());
             iteme.put("state",e.get("state").toString());
             iteme.put("run_id",e.get("run_id").toString());
             Date start_date = (Date) e.get("start_date");
             Date end_date = (Date) e.get("end_date");
             SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             iteme.put("start_date",start_date==null?"":sf.format(start_date));
             iteme.put("end_date",end_date==null?"":sf.format(end_date));
             iteme.put("execution_date",iteme.get("run_id").split("__")[1]);
             return iteme;
        }).collect(Collectors.toList());
        resulte.put("data",result1);

        return resulte;
    }


    /**
     * 获取某次调度执行的日志
     * 这个需要协同的调用远端的接口
     * @param id
     * @param execDate
     * @return
     */
    @Override
    public String getLogFileContent(String id, String execDate) {
        AirflowKettleSupport target = this.selectById(id);
        return AirFlowHandler.getFileContent(target.getDagName(),execDate);
    }

    @Override
    public void fire(String id) {
        EntityWrapper<AirflowKettleSupport> wrapper = new EntityWrapper<AirflowKettleSupport>();
        wrapper.where("del_flag={0}", "0");
        wrapper.eq("id", id);
        AirflowKettleSupport	entity=this.selectOne(wrapper);
        //未就绪的话 返回位处于就绪状态
        if(!"1".equals(entity.getRemoteReady())){
            throw new BusinessException("当前job在AirFlow服务器上未处于就绪状态，请等待5分钟之后重试。");
        }
        AirFlowHandler.firedag(entity.getDagName());
    }

    /**
     *
     * @param support
     * @return
     */
    private String construtKettleContent(AirflowKettleSupport support)  {
        KettleSupport sp = new KettleSupport();
        sp.setSchedule(support.getSchedule());
        sp.setBashCommand(support.getBashCommand());
        sp.setDagName(support.getDagName());
        String startDate = support.getStartDate().replace(":",",").replace(" ",",").replace("-",",");
        sp.setStartDate(startDate);
        try {
            return FileRefferUtil.refferedTemplate(this.getClass().getClassLoader().getResourceAsStream(SIMPLE_KETTLE_TEMPLATE),sp);
        } catch (IOException e) {
           throw new BusinessException(e);
        }
    }
    public  void update(AirflowKettleSupport entity){
        super.updateById(entity);
    }
}
