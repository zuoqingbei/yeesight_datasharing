package com.haier.datamart.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.config.Constant;
import com.haier.datamart.controller.BaseController;
import com.haier.datamart.entity.MailPlatInfo;
import com.haier.datamart.entity.MailSettingInfo;
import com.haier.datamart.mapper.MailSettingInfoMapper;
import com.haier.datamart.service.IMailPlatInfoService;
import com.haier.datamart.service.IMailSettingInfoService;
import com.haier.datamart.utils.UUIDUtils;
/**
 * @date 2019-01-03
 * @author zuoqb123
 * 邮件设置信息服务实现类
 */
@Service
@Transactional
public class MailSettingInfoServiceImpl extends ServiceImpl<MailSettingInfoMapper, MailSettingInfo> implements IMailSettingInfoService,Constant {

    @Autowired
    private MailSettingInfoMapper mailSettingInfoMapper;
    @Autowired
    public IMailPlatInfoService iMailPlatInfoService;
     /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件设置信息新增或者修改
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean saveOrUpdate(MailSettingInfo entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setId(UUIDUtils.getUuid());
			entity.setCreateDate(new Date());
			return mailSettingInfoMapper.insert(entity)>0;
		}else{
			entity.setUpdateDate(new Date());
			return mailSettingInfoMapper.updateById(entity)>0;
		}
	}
	/**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件设置信息逻辑删除
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean deleteLogic(String id) {
		MailSettingInfo entity=new MailSettingInfo();
		entity.setId(id);
		entity.setDelFlag(DEL_FLAG);
		entity.setUpdateDate(new Date());
		return mailSettingInfoMapper.updateById(entity)>0;
	}
	/**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件设置信息单条数据查询
     */
	@Override
	public MailSettingInfo getById(String id) {
		EntityWrapper<MailSettingInfo> wrapper = new EntityWrapper<MailSettingInfo>();
		wrapper.where("del_flag={0}",UN_DEL_FLAG);
		wrapper.eq("id", id);
		return selectOne(wrapper);
	}
	/**
     * @date   @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件设置信息分页查询
     */
	@Override
	public PageInfo<MailSettingInfo> pageList(BaseController c, HttpServletRequest request, MailSettingInfo entity,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<MailSettingInfo> wrapper = searchWrapper(c,request, entity);
		List<MailSettingInfo> list = mailSettingInfoMapper.selectPage(new RowBounds((pageNum-1)*pageSize, pageSize),wrapper);
		PageInfo<MailSettingInfo> page = new PageInfo<MailSettingInfo>();
		page.setList(list);
		page.setTotal(mailSettingInfoMapper.selectCount(wrapper));
		
		return page;
	}
	
	 /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件设置信息构建查询条件-以后扩展
     */
    private EntityWrapper<MailSettingInfo> searchWrapper(BaseController c,HttpServletRequest request, MailSettingInfo entity) {
		EntityWrapper<MailSettingInfo> wrapper = new EntityWrapper<MailSettingInfo>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(c!=null&&request!=null&&c.getLoginUser(request)!=null&&StringUtils.isNotBlank(c.getLoginUser(request).getId())){
			if(!c.isAdmin(request))
			 wrapper.eq("create_by", c.getLoginUser(request).getId());
		}
								//根据字段ID模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
								//根据平台模糊查询
		if(entity.getPlatId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getPlatId()))){
			wrapper.like("plat_id", String.valueOf(entity.getPlatId()));
		}
								//根据模块模糊查询
		if(entity.getModuleId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getModuleId()))){
			wrapper.like("module_id", String.valueOf(entity.getModuleId()));
		}
								//根据发送时间模糊查询
		if(entity.getSendDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getSendDate()))){
			wrapper.like("send_date", String.valueOf(entity.getSendDate()));
		}
								//根据短信发送开关 0-关闭 1-开启模糊查询
		if(entity.getMessageOpen()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMessageOpen()))){
			wrapper.like("message_open", String.valueOf(entity.getMessageOpen()));
		}
								//根据短信标题模糊查询
		if(entity.getMessageTitle()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMessageTitle()))){
			wrapper.like("message_title", String.valueOf(entity.getMessageTitle()));
		}
								//根据短信收件人组编码  引自monitor_etl_mail_receiver的reciver_id模糊查询
		if(entity.getMessageReciverId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMessageReciverId()))){
			wrapper.like("message_reciver_id", String.valueOf(entity.getMessageReciverId()));
		}
								//根据邮箱发送开关 0-关闭 1-开启模糊查询
		if(entity.getMailOpen()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMailOpen()))){
			wrapper.like("mail_open", String.valueOf(entity.getMailOpen()));
		}
								//根据邮件标题模糊查询
		if(entity.getMailTitle()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMailTitle()))){
			wrapper.like("mail_title", String.valueOf(entity.getMailTitle()));
		}
								//根据邮件收件人组编码  引自monitor_etl_mail_receiver的reciver_id模糊查询
		if(entity.getMailReciverId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMailReciverId()))){
			wrapper.like("mail_reciver_id", String.valueOf(entity.getMailReciverId()));
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
    
    
    
    /**
     * 根据平台id获取模块
     */
	@Override
	public String getModuleIdByPlatId(String platId) {
			 Wrapper<MailPlatInfo> wrapeper = new EntityWrapper<>();
			 wrapeper.eq("id", platId);
			 MailPlatInfo plat = iMailPlatInfoService.selectOne(wrapeper);
			 if(plat.getModuleId()!=null) {
				 return plat.getModuleId();
			 }
		return null;
	}
	
	/**
	  *  找到时间段内的邮件id
	 */
	@Override
	public Map<String,List<String>> getEmailControl(Date startDate, Date endDate) {
		Map<String,List<String>> map = new HashMap<>();
		List<String> resultList = new ArrayList<>();
		map.put("mailIds", resultList);
		
		Wrapper<MailSettingInfo> wrapper = new EntityWrapper<>();
		wrapper.eq("del_flag", 0);
		List<MailSettingInfo> entityList  =  this.selectList(wrapper);
		
		if(entityList!=null) {
			for (MailSettingInfo entity : entityList) {
			String nextSendTime = entity.getNextSendTime();
			if(!StringUtils.isEmpty(nextSendTime)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Date targetDate = sdf.parse(nextSendTime);
					if(targetDate.getTime()>=startDate.getTime()
					 &&targetDate.getTime()<=endDate.getTime()) {
						resultList.add(entity.getId());
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			 
			}
		}
			
		return map;
	}
}
