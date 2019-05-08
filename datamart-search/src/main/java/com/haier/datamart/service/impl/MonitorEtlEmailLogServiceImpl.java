package com.haier.datamart.service.impl;

import com.haier.datamart.entity.MonitorEtlEmailLog;
import com.haier.datamart.mapper.MonitorEtlEmailLogMapper;
import com.haier.datamart.service.IMonitorEtlEmailLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import com.haier.datamart.utils.UUIDUtils;
import com.haier.datamart.config.Constant;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.controller.BaseController;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.apache.commons.lang3.StringUtils;
/**
 * @date 2019-01-25
 * @author zuoqb123
 * 服务实现类
 */
@Service
@Transactional
public class MonitorEtlEmailLogServiceImpl extends ServiceImpl<MonitorEtlEmailLogMapper, MonitorEtlEmailLog> implements IMonitorEtlEmailLogService,Constant {

    @Autowired
    private MonitorEtlEmailLogMapper monitorEtlEmailLogMapper;
    
     /**
     * @date 2019-01-25
     * @author zuoqb123
     * @todo   新增或者修改
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean saveOrUpdate(MonitorEtlEmailLog entity) {
		Wrapper<MonitorEtlEmailLog> wrapper = new EntityWrapper<>();
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setId(UUIDUtils.getUuid());
			entity.setCreateDate(new Date());
			return monitorEtlEmailLogMapper.insert(entity)>0;
		}else{
			entity.setUpdateDate(new Date());
			return this.update(entity, wrapper);
		}
	}
	/**
     * @date 2019-01-25
     * @author zuoqb123
     * @todo   逻辑删除
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean deleteLogic(String id) {
		MonitorEtlEmailLog entity=new MonitorEtlEmailLog();
		entity.setId(id);
		entity.setDelFlag(DEL_FLAG);
		entity.setUpdateDate(new Date());
		return monitorEtlEmailLogMapper.updateById(entity)>0;
	}
	/**
     * @date 2019-01-25
     * @author zuoqb123
     * @todo   单条数据查询
     */
	@Override
	public MonitorEtlEmailLog getById(String id) {
		EntityWrapper<MonitorEtlEmailLog> wrapper = new EntityWrapper<MonitorEtlEmailLog>();
		wrapper.where("del_flag={0}",UN_DEL_FLAG);
		wrapper.eq("id", id);
		return selectOne(wrapper);
	}
	/**
     * @date   @date 2019-01-25
     * @author zuoqb123
     * @todo   分页查询
     */
	@Override
	public PageInfo<MonitorEtlEmailLog> pageList(BaseController c, HttpServletRequest request, MonitorEtlEmailLog entity,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<MonitorEtlEmailLog> wrapper = searchWrapper(c,request, entity);
		List<MonitorEtlEmailLog> list = monitorEtlEmailLogMapper.selectPage(new RowBounds((pageNum-1)*pageSize, pageSize),wrapper);
		PageInfo<MonitorEtlEmailLog> page = new PageInfo<MonitorEtlEmailLog>();
		page.setList(list);
		page.setTotal(monitorEtlEmailLogMapper.selectCount(wrapper));
		
		return page;
	}
	
	 /**
     * @date 2019-01-25
     * @author zuoqb123
     * @todo   构建查询条件-以后扩展
     */
    private EntityWrapper<MonitorEtlEmailLog> searchWrapper(BaseController c,HttpServletRequest request, MonitorEtlEmailLog entity) {
		EntityWrapper<MonitorEtlEmailLog> wrapper = new EntityWrapper<MonitorEtlEmailLog>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(c!=null&&request!=null&&c.getLoginUser(request)!=null&&StringUtils.isNotBlank(c.getLoginUser(request).getId())){
			if(!c.isAdmin(request))
			 wrapper.eq("create_by", c.getLoginUser(request).getId());
		}
								//根据编号模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
								//根据触发邮件的ID模糊查询
		if(entity.getMailId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMailId()))){
			wrapper.like("mail_id", String.valueOf(entity.getMailId()));
		}
								//根据发送邮件的结果 1 成功 0 失败模糊查询
		if(entity.getMailResult()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMailResult()))){
			wrapper.like("mail_result", String.valueOf(entity.getMailResult()));
		}
								//根据发送邮件的结果描述模糊查询
		if(entity.getMailResultDesc()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMailResultDesc()))){
			wrapper.like("mail_result_desc", String.valueOf(entity.getMailResultDesc()));
		}
								//根据发送短信的结果 1 成功 0 失败模糊查询
		if(entity.getMessageResult()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMessageResult()))){
			wrapper.like("message_result", String.valueOf(entity.getMessageResult()));
		}
								//根据发送短信的结果描述模糊查询
		if(entity.getMessageResultDesc()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMessageResultDesc()))){
			wrapper.like("message_result_desc", String.valueOf(entity.getMessageResultDesc()));
		}
								//根据发送消息的上下文 把消息序列化为sjon模糊查询
		if(entity.getContext()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getContext()))){
			wrapper.like("context", String.valueOf(entity.getContext()));
		}
								//根据创建者模糊查询
		if(entity.getCreateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
								//根据创建时间模糊查询
		if(entity.getCreateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))){
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
								//根据更新者模糊查询
		if(entity.getUpdateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))){
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
		}
								//根据更新时间模糊查询
		if(entity.getUpdateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))){
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
								//根据备注信息模糊查询
		if(entity.getRemarks()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))){
			wrapper.like("remarks", String.valueOf(entity.getRemarks()));
		}
								//根据删除标记模糊查询
		if(entity.getDelFlag()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))){
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
				if(entity.getStartDate()!=null){
			wrapper.ge("create_date", entity.getStartDate());
		}
		if(entity.getEndDate()!=null){
			wrapper.le("create_date", entity.getEndDate());
		}
		if(StringUtils.isNoneBlank(entity.getOrderBy())){
			wrapper.orderBy(entity.getOrderBy(), entity.isAsc());
		}else{
			wrapper.orderBy("create_date", false);
		}
		//System.out.println(wrapper.originalSql());
		return wrapper;
	}
   
}
