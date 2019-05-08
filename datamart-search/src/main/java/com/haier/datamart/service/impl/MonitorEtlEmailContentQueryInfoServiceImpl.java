package com.haier.datamart.service.impl;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.thymeleaf.TemplateEngine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.config.Constant;
import com.haier.datamart.controller.BaseController;
import com.haier.datamart.entity.*;
import com.haier.datamart.utils.UUIDUtils;
import com.haier.datamart.utils.email.EmailUtil;
import com.haier.datamart.utils.email.render.MultiEmailRender;
import com.haier.datamart.utils.email.render.bean.BeanTransferUtil;
import com.haier.datamart.utils.email.render.bean.MultiEmailBean;

import org.apache.calcite.avatica.org.apache.http.client.config.RequestConfig;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.hadoop.hive.ql.parse.HiveParser.foreignKeyConstraint_return;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stringtemplate.v4.compiler.STParser.namedArg_return;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.controller.AdminDataSqlExceLogsController;
import com.haier.datamart.controller.MailSettingInfoController;
import com.haier.datamart.mapper.MonitorEtlEmailContentQueryInfoMapper;
import com.haier.datamart.service.IDataInterfaceExcService;
import com.haier.datamart.service.IMailHeaderModulePlaceholderService;
import com.haier.datamart.service.IMailHeaderModuleService;
import com.haier.datamart.service.IMonitorEtlEmailContentQueryInfoChineseMapService;
import com.haier.datamart.service.IMonitorEtlEmailLogService;
import com.haier.datamart.service.MonitorEtlEmailContentQueryInfoService;
import com.haier.datamart.service.SqlStringHandleService;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 邮件内容数据查询信息 ServiceImpl层
 * @author: ZhangJiang
 * @date: 2018-12-10 下午4:55:34
 */
@Service
@Transactional
public class MonitorEtlEmailContentQueryInfoServiceImpl extends ServiceImpl<MonitorEtlEmailContentQueryInfoMapper, MonitorEtlEmailContentQueryInfo> implements MonitorEtlEmailContentQueryInfoService,Constant {
	private static final String EMAIL_DATASPACE_CONTENT = "content";
	private static final String EMAIL_DATASPACE_HEADER = "header";
	private static final String EMAIL_DATASPACE_BODYSTYLE = "bodyStyle";
	@Autowired
	MonitorEtlEmailContentQueryInfoMapper monitorEtlEmailContentQueryInfoMapper;
	@Autowired
	MonitorEtlEmailContentQueryInfoService emailContenQuerytInfoService;
	@Autowired
	SqlStringHandleService sqlStringHandleService;
	@Autowired
    public IDataInterfaceExcService iDataInterfaceExcService;
	@Autowired
	public IMonitorEtlEmailContentQueryInfoChineseMapService chineseMapService;
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Autowired
    private MailSettingInfoServiceImpl mailService;
	private static TemplateEngine TEMPLATE_ENGIN;
	 @Resource
	    public void setTemplateEngine(TemplateEngine templateEngine){
	        TEMPLATE_ENGIN = templateEngine;
	    }
	@Autowired
	private MailSettingInfoController mailSettingInfoController;
	@Autowired
	private MailHeaderModuleServiceImpl mailHeaderModuleServiceImpl;
	@Autowired
	private IMonitorEtlEmailLogService  iMonitorEtlEmailLogService;
    /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件模板信息（后台配置，给业务前台使用）新增或者修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(MonitorEtlEmailContentQueryInfo entity) {
        if(org.apache.commons.lang3.StringUtils.isBlank(entity.getId())){
            //新增
            entity.setId(UUIDUtils.getUuid());
            entity.setCreateDate(new Date());
            return monitorEtlEmailContentQueryInfoMapper.insert(entity)>0;
        }else{
            entity.setUpdateDate(new Date());
            return monitorEtlEmailContentQueryInfoMapper.updateById(entity)>0;
        }
    }
    /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件模板信息（后台配置，给业务前台使用）逻辑删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteLogic(String id) {
        MonitorEtlEmailContentQueryInfo entity=new MonitorEtlEmailContentQueryInfo();
        entity.setId(id);
        entity.setDelFlag(DEL_FLAG);
        entity.setUpdateDate(new Date());
        return monitorEtlEmailContentQueryInfoMapper.updateById(entity)>0;
    }
    /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件模板信息（后台配置，给业务前台使用）单条数据查询
     */
    @Override
    public MonitorEtlEmailContentQueryInfo getById(String id) {
        EntityWrapper<MonitorEtlEmailContentQueryInfo> wrapper = new EntityWrapper<MonitorEtlEmailContentQueryInfo>();
        wrapper.where("del_flag={0}",UN_DEL_FLAG);
        wrapper.eq("id", id);
        return selectOne(wrapper);
    }

	/**
	 * @date   @date 2019-01-09
	 * @author zuoqb123
	 * @todo   平台信息分页查询
	 */
	@Override
	public PageInfo<MonitorEtlEmailContentQueryInfo> pageList(BaseController c, HttpServletRequest request, MonitorEtlEmailContentQueryInfo entity, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<MonitorEtlEmailContentQueryInfo> wrapper = searchWrapper(c,request, entity);
		List<MonitorEtlEmailContentQueryInfo> list = monitorEtlEmailContentQueryInfoMapper.selectPage(new RowBounds((pageNum-1)*pageSize, pageSize),wrapper);
		PageInfo<MonitorEtlEmailContentQueryInfo> page = new PageInfo<MonitorEtlEmailContentQueryInfo>();
		page.setList(list);
		page.setTotal(monitorEtlEmailContentQueryInfoMapper.selectCount(wrapper));
		return page;
	}

	/**
	 * @date 2019-01-09
	 * @author zuoqb123
	 * @todo   平台信息构建查询条件-以后扩展
	 */
	private EntityWrapper<MonitorEtlEmailContentQueryInfo> searchWrapper(BaseController c,HttpServletRequest request, MonitorEtlEmailContentQueryInfo entity) {
		EntityWrapper<MonitorEtlEmailContentQueryInfo> wrapper = new EntityWrapper<MonitorEtlEmailContentQueryInfo>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(c!=null&&request!=null&&c.getLoginUser(request)!=null&& org.apache.commons.lang3.StringUtils.isNotBlank(c.getLoginUser(request).getId())){
			if(!c.isAdmin(request))
				wrapper.eq("create_by", c.getLoginUser(request).getId());
		}
		//根据字段ID模糊查询
		if(entity.getId()!=null&& org.apache.commons.lang3.StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
		//根据模板id(待用)模糊查询
		if(entity.getTemplateId()!=null&& org.apache.commons.lang3.StringUtils.isNotBlank(String.valueOf(entity.getTemplateId()))){
			wrapper.eq("template_id", String.valueOf(entity.getTemplateId()));
		}
		//根据创建者模糊查询
		if(entity.getCreateBy()!=null&& org.apache.commons.lang3.StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
		//根据创建时间模糊查询
		if(entity.getCreateDate()!=null&& org.apache.commons.lang3.StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))){
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
		//根据更新者模糊查询
		if(entity.getUpdateBy()!=null&& org.apache.commons.lang3.StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))){
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
		}
		//根据更新时间模糊查询
		if(entity.getUpdateDate()!=null&& org.apache.commons.lang3.StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))){
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
		//根据备注信息模糊查询
		if(entity.getRemarks()!=null&& org.apache.commons.lang3.StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))){
			wrapper.like("remarks", String.valueOf(entity.getRemarks()));
		}
		//根据删除标记模糊查询
		if(entity.getDelFlag()!=null&& org.apache.commons.lang3.StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))){
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
		wrapper.orderBy("sql_no",true);
		//System.out.println(wrapper.originalSql());
		return wrapper;
	}

	/**
	 * 添加单条记录
	 * 
	 * @param info
	 * @return
	 */
	protected int add(MonitorEtlEmailContentQueryInfo info) {
		int i = monitorEtlEmailContentQueryInfoMapper.insertSelective(info);
		return i;
	}
	
	public MonitorEtlEmailContentQueryInfo getContentIdsByTemplateId(String templateId) {
		Wrapper<MonitorEtlEmailContentQueryInfo> wrapperInfo = new EntityWrapper<MonitorEtlEmailContentQueryInfo>();
		wrapperInfo.setSqlSelect("group_concat(id) AS id");
		wrapperInfo.eq("del_flag", "0").eq("template_id", templateId);
		return this.selectOne(wrapperInfo);
	}
	
	
	/**
	 * 根据模板Id删除数据
	 */
	@Override
	public int deleteByTmplateId(String tmplateId, User user) {
		MonitorEtlEmailContentQueryInfo querytInfo = new MonitorEtlEmailContentQueryInfo();
		Date date = new Date();
		querytInfo.setTemplateId(tmplateId);
		querytInfo.setUpdateBy(user.getName());
		querytInfo.setUpdateDate(date);
		
		MonitorEtlEmailContentQueryInfo contentIdsByMailId = getContentIdsByTemplateId(tmplateId);
		if(contentIdsByMailId!=null){
			
			List<String> asList = Arrays.asList((contentIdsByMailId.getId() + ','+tmplateId).split(","));
			
			if(asList.size()>0) {
				Wrapper<MonitorEtlEmailContentQueryInfoChineseMap> wrapper = new EntityWrapper<MonitorEtlEmailContentQueryInfoChineseMap>();
				MonitorEtlEmailContentQueryInfoChineseMap chineseMap = new MonitorEtlEmailContentQueryInfoChineseMap();
				wrapper.in("email_content_id", asList);
				chineseMap.setUpdateBy(user.getId());
				chineseMap.setUpdateDate(date);
				chineseMap.setDelFlag("1");
				chineseMapService.update(chineseMap, wrapper);
				
				EntityWrapper<DataInterfaceExc> entityWrapper = new EntityWrapper<DataInterfaceExc>();
				DataInterfaceExc exc = new DataInterfaceExc();
				entityWrapper.in("remark", asList);
				exc.setLastUpdateBy(user.getId());
				exc.setLastUpdateTime(date);
				exc.setDelFlag("1");
				iDataInterfaceExcService.update(exc, entityWrapper);
			}
		}
		monitorEtlEmailContentQueryInfoMapper.deleteByMailId(querytInfo);
		return 1;
	}

	/**
	 * 查询数据
	 */
	@Override
	public List<MonitorEtlEmailContentQueryInfo> selectListCustom(MonitorEtlEmailContentQueryInfo monitorEtlEmailContentQueryInfo) {
		return monitorEtlEmailContentQueryInfoMapper.selectListCustom(monitorEtlEmailContentQueryInfo);
	}
	
	/**
	 * 根据邮箱Id获取数据，执行数据每一条sql语句，返回结果集
	 */
	@Override
	public Map<Integer, List<List<String>>> getResultLists(MonitorEtlEmailContentQueryInfo monitorEtlEmailContentQueryInfo) {
		List<MonitorEtlEmailContentQueryInfo> list = monitorEtlEmailContentQueryInfoMapper.selectListCustom(monitorEtlEmailContentQueryInfo);
		if(null!=list && 0 < list.size()) {
			Map<Integer,List<List<String>>> map = new HashMap<Integer,List<List<String>>>();
			for (MonitorEtlEmailContentQueryInfo info : list) {
				List<List<String>> returnList = sqlStringHandleService.getSqlQueryReturnList(info.getDatasourceId(), info.getSqlString());
				map.put(info.getSqlNo(), returnList);
			}
			return map;
		}
		return null;
	}
	 
	/**
	 * 统一接口数据查询 lzg
	 */
	@Override
	public MailForQueryBeanParent  unifiedInterfaceDataQuery(String mailId) {
		//List<Map<String, Map<String, Object>>>
		MailForQueryBeanParent entity = new MailForQueryBeanParent();
		
		if(StringUtils.isEmpty(mailId)) {return entity;}
		
		//根据邮件id找到对应实体
		Wrapper<MailSettingInfo> wrapper0 = new EntityWrapper<>();
		wrapper0.eq("del_flag", 0).eq("id", mailId);
		MailSettingInfo mailEntity = mailService.selectOne(wrapper0); 
		if(mailEntity==null) {return entity;}
		
		//邮件实体信息
		String templateId = mailEntity.getTempleteId();
		String platId = mailEntity.getPlatId();
		String userId = mailEntity.getCreateBy();
		if(platId==null||userId==null) {return  entity;}
		
		//填充邮件基础信息
		PublicResult<MailSettingInfo>  rest = mailSettingInfoController.getByUserAndPlat(null,platId,userId);
		if(rest!=null) {
			MailSettingInfo targetMailInfo = rest.getData();
			entity.setMailBaseInfo(targetMailInfo);
		}
	
		
		//填充邮件正文
		if(templateId!=null) {
			List<MailForQueryBeanTableMap> entityBody = unifiedInterfaceDataQueryByTemplateId(templateId);
			entity.setTableMap(entityBody);
		}
		return entity;
	}
	@Override
	public void triggerSend(String mailId,String mailSendTo,String mailCC,String messageSendTO,String userId){
		if(StringUtils.isEmpty(userId)) {
			userId = "dmt_sys_diaodu";
		}
		
		MailForQueryBeanParent mailSetting =  unifiedInterfaceDataQuery(mailId);
		MonitorEtlEmailLog logEntity = new MonitorEtlEmailLog();
		List<String> sendTo = null;
		List<String> cc = null;
		List<String> messageTo =null;
		if(org.apache.commons.lang3.StringUtils.isNotBlank(mailSendTo)){
			sendTo = Arrays.asList(mailSendTo.split(","));
		}
		if(org.apache.commons.lang3.StringUtils.isNotBlank(mailCC)){
			cc = Arrays.asList(mailCC.split(","));
		}
		if(org.apache.commons.lang3.StringUtils.isNotBlank(messageSendTO)){
			messageTo = Arrays.asList(messageSendTO.split(","));
		}
		MailSettingInfo mailInfo = mailSetting.getMailBaseInfo();
		if(mailInfo==null) {
			throw new RuntimeException("MailSettingInfo 实体不能为空!");
		}
		
		if("1".equals(mailInfo.getMailOpen())&&StringUtils.isBlank(mailInfo.getMailReciverId())){
			throw new RuntimeException("收件人不能为空!");
		}
		
		logEntity.setMailId(mailId);
		logEntity.setCreateBy(userId);
		if(mailSetting!=null) {
			logEntity.setContext(JSONObject.toJSONString(mailSetting));
		}
		try{
			if("1".equals(mailInfo.getMailOpen())) {//邮箱发送开关 0-关闭 1-开启',
				logEntity.setMailResult("1");//发送邮件的结果 1 成功 0 失败 2未发送
				triggerEmail(mailSetting,sendTo,cc);
			}else {
				logEntity.setMailResult("2");
			}
		}catch (Exception e){
			logEntity.setMailResult("0");//发送邮件的结果 1 成功 0 失败 2未发送
			logEntity.setMailResultDesc(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			if("1".equals(mailInfo.getMessageOpen())) {//短信发送开关 0-关闭 1-开启',
				if(mailInfo.getMessageRecevers()==null||mailInfo.getMessageRecevers().size()==0){
					throw new RuntimeException("收件人不能为空!");
				}
				boolean messageFlag = triggerShortMessage(mailSetting, messageTo);
				if(messageFlag) { logEntity.setMessageResult("1");}else {  logEntity.setMessageResult("0");}
			}else {
				logEntity.setMessageResult("2");
			}
		} catch (Exception e) {
			logEntity.setMessageResult("0");//发送短信的结果 1 成功 0 失败 2未发送
			logEntity.setMessageResultDesc(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			iMonitorEtlEmailLogService.saveOrUpdate(logEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	
	@Override
	public void singleTriggerSend(String content,String mailSendTo,String mailCC,String userId,String subject) throws Exception{
		if(StringUtils.isEmpty(userId)) {return;}
		if(StringUtils.isEmpty(mailSendTo)){return;}
		
		List<String> toList = Arrays.asList(mailSendTo.split(","));
		List<String>  ccList = new ArrayList<>();
		if(!StringUtils.isEmpty(mailCC)) {
			   ccList = Arrays.asList(mailCC.split(","));
		}
		
		
        Context context = new Context();
        context.setVariable("content",content);
      //  String str =   TEMPLATE_ENGIN.process("/mail_template/single_mail.html", context);
        if(StringUtils.isEmpty(subject)) {
        	subject = "邮件预警系统提醒信息";
        }
        EmailUtil.sendEmail(toList,ccList,subject, content);
	}
	
	/**
	 * 发送邮件
	 * @param mailSetting
	 */
	private void triggerEmail(MailForQueryBeanParent mailSetting,List<String> sendTo,List<String> cc) throws Exception{
		MailSettingInfo targetMailInfo = mailSetting.getMailBaseInfo();
		//主要获取收件人、抄送人、邮件主题
		if(sendTo==null||sendTo.size()==0){
			//重新获取收件人和抄送人
			sendTo=new ArrayList<>();
			cc=new ArrayList<>();
			if(targetMailInfo.getMailJsRecevers()!=null){
				for(MailReceiverManager item:targetMailInfo.getMailJsRecevers()){
					sendTo.add(item.getEmail());
				}
			}
			if(targetMailInfo.getMessageCsRecevers()!=null){
				for(MailReceiverManager item:targetMailInfo.getMailJsRecevers()){
					cc.add(item.getEmail());
				}
			}
		}

		String subject = targetMailInfo.getMailTitle();
		EmailUtil.sendEmail(sendTo,cc,subject, MultiEmailRender.renderEmail(mailSetting, "/mail_template/simple_mail"));
		
		
	}

	/**
	 * 发送短信
	 * @param mailSetting
	 */
	private boolean triggerShortMessage(MailForQueryBeanParent mailSetting,List<String> messageTo){
		if(mailSetting==null) { return false; }
		
		MailSettingInfo mail = mailSetting.getMailBaseInfo();
		
		if(mail==null) {return false;}
		
		if(messageTo==null||messageTo.size()==0) {
			//重新获取收件人和抄送人
			messageTo = new ArrayList<>();
			if(mail.getMessageCsRecevers()!=null){
				for(MailReceiverManager item:mail.getMessageCsRecevers()){
					messageTo.add(item.getTel());
				}
			}
		}
		
		if(messageTo.size()==0) {return false;}
		
		MailSettingInfo mailInfo = mailSetting.getMailBaseInfo();
		
		if(mailInfo==null) { return false; }
		
		//String content = mailInfo.getMessageTitle();
		String content = "";
		List<MailHeaderInfo> MailHeaderInfoList = mailInfo.getMailHeaderInfoLists();
		if(MailHeaderInfoList!=null)
		for (MailHeaderInfo mailHeaderInfo : MailHeaderInfoList) {
			String str = mailHeaderInfo.getImplementStr();//此处拼接短信内容
			if(str!=null)  content += str+",";
		}
		if(StringUtils.isEmpty(content)) { return false; }
		
		content=stripHtml(content);
		
		 String url = "http://180.76.188.189:19999/sendSMS.action";
		 
		 HttpPost post = new HttpPost(url);
		
		 post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		 
		 
		 List<BasicNameValuePair> pairs=new ArrayList<>(); 
	      
	      pairs.add(new BasicNameValuePair("loginName","admin"));
	      pairs.add(new BasicNameValuePair("mobiles",StringUtils.join(messageTo, ",")));
	      //System.out.println("【1169数据引擎】"+content.trim());
	      pairs.add(new BasicNameValuePair("content",("【1169数据引擎】"+content.trim())+" "));
	      pairs.add(new BasicNameValuePair("password","3441808d79397ffcc8e90137176c7425"));
	      pairs.add(new BasicNameValuePair("enterpriseID","17824"));
	      
		 try {
			post.setEntity(new UrlEncodedFormEntity(pairs,"utf-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} 
		  
		 CloseableHttpClient client = HttpClients.createDefault();
		 
		 CloseableHttpResponse response = null;
		 
		 try {
			 response = client.execute(post);
			 
			 String rest = EntityUtils.toString(response.getEntity(), "utf-8");
			 
			 if(rest.contains("<Response><Result>0</Result><SmsId>")) {
				 System.out.println("短信发送成功:");
				 System.out.println("【1169数据引擎】"+content.trim()+" 退订信息回N");
				 return true;
			 }
			 
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public String stripHtml(String content) { 
	    // <p>段落替换为换行 
	    content = content.replaceAll("<p .*?>", "\r\n"); 
	    // <br><br/>替换为换行 
	    content = content.replaceAll("<br\\s*/?>", "\r\n"); 
	    // 去掉其它的<>之间的东西 
	    content = content.replaceAll("\\<.*?>", ""); 
	    // 去掉空格 
	    content = content.replaceAll(" ", ""); 
	    return content;   
	}
	/**
	 * 根据templateId返回正文内容
	 */
	@Override
	public List<MailForQueryBeanTableMap> unifiedInterfaceDataQueryByTemplateId(String templateId) {
		List<MailForQueryBeanTableMap> tableList = new ArrayList<>();
		
		if(StringUtils.isEmpty(templateId)) {return tableList;}
		//根据模板配置id找到语句配置实体集合
		
		Wrapper<MonitorEtlEmailContentQueryInfo> wrapperQueryInfo = new EntityWrapper<MonitorEtlEmailContentQueryInfo>();
		
		wrapperQueryInfo.eq("del_flag", "0").eq("template_id", templateId);
		wrapperQueryInfo.orderBy("sql_no", true);
		List<MonitorEtlEmailContentQueryInfo> queryInfoList = emailContenQuerytInfoService.selectList(wrapperQueryInfo);
		
			if (queryInfoList!=null&&queryInfoList.size() > 0) {
				
				for (MonitorEtlEmailContentQueryInfo tempQueryInfo : queryInfoList) {
					
					MailForQueryBeanTableMap tableMap = new MailForQueryBeanTableMap();
					
					String tempDataSpace = tempQueryInfo.getDataSpace();//获取命名空间
					
					String tempDataType = tempQueryInfo.getDataTypeMarking();//获取datatype
					
					String tempTitle = tempQueryInfo.getSingleTableHeaderStr();//获取表头
					
					String titleColor = tempQueryInfo.getSingleTableHeaderStyle();//获取表头颜色
						
							//////////////content是固定表数据的key
							//放入表头
					 tableMap.setTitle(tempTitle);///////////////////title是固定表头key
							//放入表头颜色
					 tableMap.setTitleColor(titleColor);///////////////////titleColor是固定表头颜色key

					/**
					 * 固定逻辑
					 * 内容和表头使用同一个通用接口
					 * 内容的data_type为 content
					 * 表头的data_type为 header
					 */

					//放入基础数据
					tableMap.setContent(mailHeaderModuleServiceImpl.getDatas(null,tempDataType, EMAIL_DATASPACE_CONTENT));
					//放入列名和展示名名映射关系
					tableMap.setTitleColumnMap( mailHeaderModuleServiceImpl.getDatas(null, tempDataType,EMAIL_DATASPACE_HEADER));
					//放入列名和颜色对应关系
					tableMap.setBodyStyle( mailHeaderModuleServiceImpl.getDatas(null, tempDataType,EMAIL_DATASPACE_BODYSTYLE));
					tableList.add(tableMap);
					 
			}
	 
		}
			return tableList;
 }
	
	
	public static void main(String[] args) {
		 MonitorEtlEmailContentQueryInfoServiceImpl triggerShortMessage = new MonitorEtlEmailContentQueryInfoServiceImpl();
			MailForQueryBeanParent m = new MailForQueryBeanParent();
			MailSettingInfo mailInfo = new MailSettingInfo();
			mailInfo.setMessageTitle("标题一旦超过8个字就报错!");
			m.setMailBaseInfo(mailInfo);
			triggerShortMessage.triggerShortMessage(m, Arrays.asList(new String[] {"13361264303"})); 
		}	 
}