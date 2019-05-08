package com.haier.datamart.service.impl;

import com.haier.datamart.entity.DataInterfaceExc;
import com.haier.datamart.entity.MailHeaderInfo;
import com.haier.datamart.entity.MailHeaderModule;
import com.haier.datamart.entity.MailHeaderModulePlaceholder;
import com.haier.datamart.entity.MailHeaderModulePlaceholderThreshold;
import com.haier.datamart.entity.MailSettingInfo;
import com.haier.datamart.exception.BusinessException;
import com.haier.datamart.mapper.MailHeaderModuleMapper;
import com.haier.datamart.mapper.MailModuleInfoMapper;
import com.haier.datamart.service.IDataInterfaceExcService;
import com.haier.datamart.service.IMailHeaderModulePlaceholderService;
import com.haier.datamart.service.IMailHeaderModulePlaceholderThresholdService;
import com.haier.datamart.service.IMailHeaderModuleService;
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
import com.haier.datamart.controller.AdminDataSqlExceLogsController;
import com.haier.datamart.controller.BaseController;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.RowBounds;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zuoqb123
 *         邮件抬头预设置语句服务实现类
 * @date 2019-01-03
 */
@Service
@Transactional
public class MailHeaderModuleServiceImpl extends ServiceImpl<MailHeaderModuleMapper, MailHeaderModule> implements IMailHeaderModuleService, Constant {

    @Autowired
    private MailHeaderModuleMapper mailHeaderModuleMapper;
    @Autowired
    public IDataInterfaceExcService iDataInterfaceExcService;
    @Autowired
    private MailModuleInfoMapper mailModuleInfoMapper;
    @Autowired
    private MailSettingInfoServiceImpl mailService;
    @Autowired
    private MailHeaderInfoServiceImpl mailHeaderService;
    @Autowired
    private IMailHeaderModulePlaceholderService IMailHeaderModulePlaceholder;
    @Autowired
    private IMailHeaderModuleService ImailHeaderModuleService;
    @Autowired
    private AdminDataSqlExceLogsController adminDataSqlExceLogsController;
    @Autowired
    public IMailHeaderModulePlaceholderThresholdService iMailHeaderModulePlaceholderThresholdService;

    /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo 邮件抬头预设置语句新增或者修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(MailHeaderModule entity) {
        if (StringUtils.isBlank(entity.getId())) {
            //新增
            entity.setId(UUIDUtils.getUuid());
            entity.setCreateDate(new Date());
            return mailHeaderModuleMapper.insert(entity) > 0;
        } else {
            entity.setUpdateDate(new Date());
            //判断dealContent是否包含通配符
            if(StringUtils.isNotBlank(entity.getDealContent())){
                if(getReplaceChar(entity.getDealContent()).size()==0){
                    throw new BusinessException(entity.getDealContent()+",请指定替换符。格式为${xxx}");
                }
            }

            return mailHeaderModuleMapper.updateById(entity) > 0;
        }
    }

    /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo 邮件抬头预设置语句逻辑删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteLogic(String id) {
        MailHeaderModule entity = new MailHeaderModule();
        entity.setId(id);
        entity.setDelFlag(DEL_FLAG);
        entity.setUpdateDate(new Date());
        return mailHeaderModuleMapper.updateById(entity) > 0;
    }

    /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo 邮件抬头预设置语句单条数据查询
     */
    @Override
    public MailHeaderModule getById(String id) {
        EntityWrapper<MailHeaderModule> wrapper = new EntityWrapper<MailHeaderModule>();
        wrapper.where("del_flag={0}", UN_DEL_FLAG);
        wrapper.eq("id", id);
        return selectOne(wrapper);
    }

    /**
     * @date @date 2019-01-03
     * @author zuoqb123
     * @todo 邮件抬头预设置语句分页查询
     */
    @Override
    public PageInfo<MailHeaderModule> pageList(BaseController c, HttpServletRequest request, MailHeaderModule entity, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        EntityWrapper<MailHeaderModule> wrapper = searchWrapper(c, request, entity);
        List<MailHeaderModule> list = mailHeaderModuleMapper.selectPage(new RowBounds((pageNum - 1) * pageSize, pageSize), wrapper);
        PageInfo<MailHeaderModule> page = new PageInfo<MailHeaderModule>();
        page.setList(list);
        page.setTotal(mailHeaderModuleMapper.selectCount(wrapper));

        return page;
    }

    /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo 邮件抬头预设置语句构建查询条件-以后扩展
     */
    private EntityWrapper<MailHeaderModule> searchWrapper(BaseController c, HttpServletRequest request, MailHeaderModule entity) {
        EntityWrapper<MailHeaderModule> wrapper = new EntityWrapper<MailHeaderModule>();
        wrapper.where("del_flag={0}", UN_DEL_FLAG);
        if (c != null && request != null && c.getLoginUser(request) != null && StringUtils.isNotBlank(c.getLoginUser(request).getId())) {
            if (!c.isAdmin(request))
                wrapper.eq("create_by", c.getLoginUser(request).getId());
        }
        //根据字段ID模糊查询
        if (entity.getId() != null && StringUtils.isNotBlank(String.valueOf(entity.getId()))) {
            wrapper.eq("id", String.valueOf(entity.getId()));
        }
        //根据邮件编码模糊查询
        if (entity.getPlatId() != null && StringUtils.isNotBlank(String.valueOf(entity.getPlatId()))) {
            wrapper.eq("plat_id", String.valueOf(entity.getPlatId()));
        }
        //根据原始语句范例模糊查询
        if (entity.getOrgContent() != null && StringUtils.isNotBlank(String.valueOf(entity.getOrgContent()))) {
            wrapper.like("org_content", String.valueOf(entity.getOrgContent()));
        }
        //根据处理完后语句范例（带有占位符）模糊查询
        if (entity.getDealContent() != null && StringUtils.isNotBlank(String.valueOf(entity.getDealContent()))) {
            wrapper.like("deal_content", String.valueOf(entity.getDealContent()));
        }
        //根据参数来源描述模糊查询
        if (entity.getParamsDesc() != null && StringUtils.isNotBlank(String.valueOf(entity.getParamsDesc()))) {
            wrapper.like("params_desc", String.valueOf(entity.getParamsDesc()));
        }
        //根据短信标题模糊查询
        if (entity.getMessageTitle() != null && StringUtils.isNotBlank(String.valueOf(entity.getMessageTitle()))) {
            wrapper.like("message_title", String.valueOf(entity.getMessageTitle()));
        }
        //根据申请人姓名模糊查询
        if (entity.getApplyUser() != null && StringUtils.isNotBlank(String.valueOf(entity.getApplyUser()))) {
            wrapper.like("apply_user", String.valueOf(entity.getApplyUser()));
        }
        //根据申请人电话模糊查询
        if (entity.getApplyTel() != null && StringUtils.isNotBlank(String.valueOf(entity.getApplyTel()))) {
            wrapper.like("apply_tel", String.valueOf(entity.getApplyTel()));
        }
        //根据申请人邮箱模糊查询
        if (entity.getApplyMail() != null && StringUtils.isNotBlank(String.valueOf(entity.getApplyMail()))) {
            wrapper.like("apply_mail", String.valueOf(entity.getApplyMail()));
        }
        //根据申请人工号模糊查询
        if (entity.getApplyNumber() != null && StringUtils.isNotBlank(String.valueOf(entity.getApplyNumber()))) {
            wrapper.like("apply_number", String.valueOf(entity.getApplyNumber()));
        }
        //根据创建者模糊查询
        if (entity.getCreateBy() != null && StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))) {
            wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
        }
        //根据创建时间模糊查询
        if (entity.getCreateDate() != null && StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))) {
            wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
        }
        //根据更新者模糊查询
        if (entity.getUpdateBy() != null && StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))) {
            wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
        }
        //根据更新时间模糊查询
        if (entity.getUpdateDate() != null && StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))) {
            wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
        }
        //根据备注信息模糊查询
        if (entity.getRemarks() != null && StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))) {
            wrapper.like("remarks", String.valueOf(entity.getRemarks()));
        }
        //根据删除标记模糊查询
        if (entity.getDelFlag() != null && StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))) {
            wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
        }
        if (entity.getStartDate() != null) {
            wrapper.ge("create_date", entity.getStartDate());
        }
        if (entity.getEndDate() != null) {
            wrapper.le("create_date", entity.getEndDate());
        }
        if (StringUtils.isNotBlank(entity.getIsComplete())) {
            wrapper.eq("is_complete", entity.getIsComplete());
        }
        if (StringUtils.isNoneBlank(entity.getOrderBy())) {
            wrapper.orderBy(entity.getOrderBy(), entity.isAsc());
        } else {
            wrapper.orderBy("create_date", false);
        }
        //System.out.println(wrapper.originalSql());
        return wrapper;
    }

    /**
     * 根据平台找到对应语句
     * lzg
     */
    @Override
    public List<MailHeaderModule> getList(String platId) {

        List<MailHeaderModule> targetStrList = new ArrayList<>();

        if (!StringUtils.isEmpty(platId)) {

            //根据平台id找到邮件头信息
            Wrapper<MailHeaderModule> mailHeaderWrapper = new EntityWrapper<MailHeaderModule>();

            mailHeaderWrapper.eq("del_flag", "0").eq("plat_id", platId).orderAsc(Arrays.asList(new String[]{"update_date", "create_date"}));

            List<MailHeaderModule> mailHeaderEntityList = ImailHeaderModuleService.selectList(mailHeaderWrapper);

            if (mailHeaderEntityList != null) {

                for (MailHeaderModule mailHeaderEntity : mailHeaderEntityList) {
                    if (mailHeaderEntity != null) {
                    	String dealContent = mailHeaderEntity.getDealContent();
                    	if(!StringUtils.isEmpty(dealContent)) {
                    		 Map<String,Object> map=parseDealContent(dealContent);
                             mailHeaderEntity.setRemarks(map.get("result")+"");
                             targetStrList.add(mailHeaderEntity);
                    	}
                        
                    }
                }
            }
        }

        return targetStrList;
    }

    /**
     * 根据邮件id拼接对应邮件头
     */
    @Override
    public String getHeaderContent(String mailId) {

        String targetStr = "";

        if (StringUtils.isEmpty(mailId)) {
            return targetStr;
        }

        Wrapper<MailHeaderInfo> wrapper = new EntityWrapper<MailHeaderInfo>();

        wrapper.eq("mail_id", mailId).eq("del_flag", "0").orderAsc(Arrays.asList(new String[]{"order_no", "create_date"}));

        List<MailHeaderInfo> list = mailHeaderService.selectList(wrapper);

        if (list != null) {
            List<String> resultList = new ArrayList<>();
            for (MailHeaderInfo mailHeaderInfo : list) {
                if (mailHeaderInfo != null) {
                    String headerModuleId = mailHeaderInfo.getHeaderModuleId();
                    if (StringUtils.isEmpty(headerModuleId)) {
                        resultList.add("");
                    } else {
                        //根据平台id找到邮件头信息
                        Wrapper<MailHeaderModule> mailHeaderWrapper = new EntityWrapper<MailHeaderModule>();

                        mailHeaderWrapper.eq("del_flag", "0").eq("id", headerModuleId);

                        MailHeaderModule mailHeaderEntity = ImailHeaderModuleService.selectOne(mailHeaderWrapper);
                        if (mailHeaderEntity != null) {
                            //此处逻辑修改
                            //需要占位符的头信息 需要根据 正则表达式获取占位符信息 并且占位符信息和表头模板去掉上下级关联
                            //快速插入的直接返回即可
                            if ("1".equals(mailHeaderEntity.getIsFastContent())) {
                                mailHeaderInfo.setImplementStr(mailHeaderEntity.getOrgContent());
                            } else {
                            	Map<String,Object> map=parseDealContent(mailHeaderEntity.getDealContent());
                                mailHeaderInfo.setImplementStr(map.get("result")+"");
                                mailHeaderInfo.setIsShow(map.get("isShow")+"");
                            }
                            if("true".equals(mailHeaderInfo.getIsShow())){
                            	resultList.add(mailHeaderInfo.getImplementStr());
                            }
                        }

                    }

                }
            }
            if(resultList.size()>0){
            	targetStr =StringUtils.join(resultList,",")+"。";
            }
        }

        return targetStr;
    }


    public List<MailHeaderInfo> getHeaderInfoListByMailId(String mailId) {

        List<MailHeaderInfo> resultList = new ArrayList<>();


        if (StringUtils.isEmpty(mailId)) {
            return resultList;
        }

        Wrapper<MailHeaderInfo> wrapper = new EntityWrapper<MailHeaderInfo>();

        wrapper.eq("mail_id", mailId).eq("del_flag", "0").orderAsc(Arrays.asList(new String[]{"order_no", "create_date"}));
        System.out.println(wrapper.getSqlSelect());
        List<MailHeaderInfo> list = mailHeaderService.selectList(wrapper);

        if (list != null) {
            for (MailHeaderInfo mailHeaderInfo : list) {
                if (mailHeaderInfo != null) {

                    String headerModuleId = mailHeaderInfo.getHeaderModuleId();

                    if (!StringUtils.isEmpty(headerModuleId)) {
                        //根据平台id找到邮件头信息
                        Wrapper<MailHeaderModule> mailHeaderWrapper = new EntityWrapper<MailHeaderModule>();

                        mailHeaderWrapper.eq("del_flag", "0").eq("id", headerModuleId);

                        MailHeaderModule mailHeaderEntity = ImailHeaderModuleService.selectOne(mailHeaderWrapper);

                        if (mailHeaderEntity != null) {

                            //此处逻辑修改
                            //需要占位符的头信息 需要根据 正则表达式获取占位符信息 并且占位符信息和表头模板去掉上下级关联
                            //快速插入的直接返回即可
                            if ("1".equals(mailHeaderEntity.getIsFastContent())) {
                                mailHeaderInfo.setImplementStr(mailHeaderEntity.getOrgContent());
                            } else {
                            	Map<String,Object> map=parseDealContent(mailHeaderEntity.getDealContent());
                                mailHeaderInfo.setImplementStr(map.get("result")+"");
                                mailHeaderInfo.setIsShow(map.get("isShow")+"");
                            }
                            //if("true".equals(mailHeaderInfo.getIsShow()))
                            resultList.add(mailHeaderInfo);
                        }

                    }

                }
            }
        }

        return resultList;
    }

    /**
     * 根据语句配置Id得出占位符和统一接口值的映射
     * lzg
     */
    /*public String[] getValueByDealContent(String dealContent) {

        String[] result = new String[]{"", ""};
        
        if (StringUtils.isEmpty(dealContent)) { return result;  }
        
        String key = parseDealContent(dealContent);
        
        if (StringUtils.isEmpty(key)) { return result;  }
        //根据 key 找到占位符与统一接口配置信息
        Wrapper<MailHeaderModulePlaceholder> placeholdeWrapper = new EntityWrapper<MailHeaderModulePlaceholder>();

        placeholdeWrapper.eq("del_flag", "0").eq("placeholder_key", key).orderDesc(Arrays.asList(new String[]{"update_date", "create_date"}));

        MailHeaderModulePlaceholder placeholdeEntity = IMailHeaderModulePlaceholder.selectOne(placeholdeWrapper);

        if (placeholdeEntity != null) {
            String placeholderKey = placeholdeEntity.getPlaceholderKey();//占位符key
            if (!StringUtils.isEmpty(placeholderKey)) {
                result[0] = placeholderKey;
            }
            String placeholderValue = placeholdeEntity.getPlaceholderValue();//占位符值，一般对应统一接口dataType',
            String params = placeholdeEntity.getParams();//统一接口参数
            String dataSpaceForHeader = placeholdeEntity.getDataKey();//所取的列名

            List<Map<String, Object>> list = getDatas(params, placeholderValue, "");

            if (list != null && list.size() > 0) {
                result[1] = (String) list.get(0).get(dataSpaceForHeader);
            }
        }
        return result;
    }*/


    /**
     * 根据参数获取数据
     *
     * @param params    统一接口参数
     * @param dataType  占位符值，一般对应统一接口dataType
     * @param dataSpace 命名空间
     *                  lzg
     */
    public List<Map<String, Object>> getDatas(String params, String dataType, String dataSpace) {

        List<Map<String, Object>> resultList = new ArrayList<>();

        //找到统一接口配置信息
        Wrapper<DataInterfaceExc> excWrapper = new EntityWrapper<DataInterfaceExc>();
        excWrapper.eq("del_flag", "0").eq("data_type", dataType).orderDesc(Arrays.asList(new String[]{"last_update_time", "create_time"}));
        ;

        if (!StringUtils.isEmpty(dataSpace)) {
            excWrapper.eq("data_space", dataSpace);
        }

        DataInterfaceExc excEntity = iDataInterfaceExcService.selectOne(excWrapper);

        if (excEntity != null) {
            String dataSourceId2 = excEntity.getDataSourceId();
            String dataSql2 = excEntity.getDataSql();

            if (StringUtils.isEmpty(dataSourceId2) || StringUtils.isEmpty(dataSql2)) {
                return resultList;
            }
            Object o = adminDataSqlExceLogsController.execeSql(null, dataSourceId2, null, dataSql2, "", params);
            Map<String, List<Map<String, Object>>> targetMap = null;
            //执行统一接口
            if (o instanceof com.haier.datamart.base.PublicResult) {
                return resultList;
            } else {
            	try {
            		targetMap = (Map<String, List<Map<String, Object>>>) o;
				} catch (ClassCastException e) {
					e.printStackTrace();
					return resultList;
				}
                
            }

            if (targetMap != null) {
                resultList = targetMap.get("values");
                if (resultList != null) return resultList;
            }
        }
        return resultList;
    }

    //替换deal中的占位符信息
    private Map<String,Object> parseDealContent(String dealContent) {
    	Map<String,Object> rMap=new HashMap<String, Object>();
    	 rMap.put("isShow", "1");
        String result = dealContent;
        Set<String> repalcechars = getReplaceChar(dealContent);
        for (String replaceChar : repalcechars) {
        	Map<String,Object> map= getReplaceCharValue(replaceChar);
            String value = map.get("value")+"";
            if(StringUtils.isBlank(value)){
            	value="";
            }
            result = result.replace("${" + replaceChar + "}", value);
            //判断当前信息是否展示
            boolean needCheck=true;
            if(!isShow(map, value)&&needCheck){
            	//result="****不让显示******";
            	 rMap.put("isShow", "0");
            	 needCheck=false;
            };
        }
        rMap.put("result", result);
        return rMap;
    }

	/**
	 * @time   2019年2月14日 上午10:43:36
	 * @author zuoqb
	 * @todo   TODO
	 * @param  @param map
	 * @param  @param value
	 * @return_type   void
	 */
	public boolean isShow(Map<String, Object> map, String value) {
		boolean isShow=true;
		if(map.get("entity")!=null&&StringUtils.isNotBlank(value)){
			MailHeaderModulePlaceholder placeholdeEntity=(MailHeaderModulePlaceholder) map.get("entity");
			List<MailHeaderModulePlaceholderThreshold> thresholds=iMailHeaderModulePlaceholderThresholdService.thresholdListByPlaceholderId(placeholdeEntity.getId());
			if(thresholds!=null&&thresholds.size()>0){
				for(MailHeaderModulePlaceholderThreshold threshold:thresholds){
					value=value.replaceAll("%", "");
					if(value.matches("-?[0-9]+.*[0-9]*")){
						//是数字判断显示
						if(!judgeThresholdShow(threshold,value)){
							isShow=false;
							break;
						};
					}
				}
			}
		}
		return isShow;
	}

	/**
	 * @time   2019年2月14日 上午10:17:40
	 * @author zuoqb
	 * @todo   TODO
	 * @param  @param threshold
	 * @return_type   void
	 */
	public boolean judgeThresholdShow(MailHeaderModulePlaceholderThreshold threshold,String value) {
		String thresholdValue=threshold.getThresholdValue();
		if(StringUtils.isBlank(thresholdValue)){
			return true;
		}
		boolean isShow=true;
		double thresholdValueD=Double.parseDouble(thresholdValue);
		double valueD=Double.parseDouble(value);
		switch (threshold.getYunsuanfu()) {
		case ">":
			if(valueD>thresholdValueD){
				if("隐藏".equals(threshold.getOpt())){
					isShow=false;
				}else{
					isShow=true;
				}
			}else{
				if("隐藏".equals(threshold.getOpt())){
					isShow=true;
				}else{
					isShow=false;
				}
			}
			break;
		case ">=":
			if(valueD>=thresholdValueD){
				if("隐藏".equals(threshold.getOpt())){
					isShow=false;
				}else{
					isShow=true;
				}
			}else{
				if("隐藏".equals(threshold.getOpt())){
					isShow=true;
				}else{
					isShow=false;
				}
			}
			break;
		case "<":
			if(valueD<thresholdValueD){
				if("隐藏".equals(threshold.getOpt())){
					isShow=false;
				}else{
					isShow=true;
				}
			}else{
				if("隐藏".equals(threshold.getOpt())){
					isShow=true;
				}else{
					isShow=false;
				}
			}
			break;
		case "<=":
			if(valueD<=thresholdValueD){
				if("隐藏".equals(threshold.getOpt())){
					isShow=false;
				}else{
					isShow=true;
				}
			}else{
				if("隐藏".equals(threshold.getOpt())){
					isShow=true;
				}else{
					isShow=false;
				}
			}
			break;
		default:
			break;
		}
		return isShow;
		
	}
    private Map<String,Object> getReplaceCharValue(String replaceChar) {
    	Map<String,Object> map=new HashMap<String, Object>();
        Wrapper<MailHeaderModulePlaceholder> placeholdeWrapper = new EntityWrapper<MailHeaderModulePlaceholder>();

        placeholdeWrapper.eq("del_flag", "0").eq("placeholder_key", replaceChar).orderDesc(Arrays.asList(new String[]{"update_date", "create_date"}));

        MailHeaderModulePlaceholder placeholdeEntity = IMailHeaderModulePlaceholder.selectOne(placeholdeWrapper);
        if (placeholdeEntity != null) {
        	map.put("entity", placeholdeEntity);
            String placeholderKey = placeholdeEntity.getPlaceholderKey();//占位符key
            String placeholderValue = placeholdeEntity.getPlaceholderValue();//占位符值，一般对应统一接口dataType',
            String params = placeholdeEntity.getParams();//统一接口参数
            String dataSpaceForHeader = placeholdeEntity.getDataKey();//所取的列名

            List<Map<String, Object>> list = getDatas(params, placeholderValue, "");

            if (list != null && list.size() > 0) {
            	map.put("value", String.valueOf(list.get(0).get(dataSpaceForHeader)));
                //return String.valueOf(list.get(0).get(dataSpaceForHeader));
            	return map;
            }
        }else{
        	 map.put("entity", "");
        	map.put("value","");
        }
        return map;
    }


    /**
     * 解析文件中的替换符
     * 格式为 ${code} 解析出 code
     *
     * @param dealContent
     * @return a set of replace string
     */
    private Set<String> getReplaceChar(String dealContent) {
        Set<String> result = new HashSet<>();
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}"); 
        Map<String, String> keyHolder = new HashMap<>();
        Matcher m = pattern.matcher(dealContent);
        while (m.find()) {
            result.add(m.group(1).trim());
            keyHolder.put(m.group(1).trim(), m.group(0));
        }
        return result;

    }

	@Override
	public Map<String,Object> dealHeaderModule( String sourceStr){
		return parseDealContent(sourceStr);
	}


}
