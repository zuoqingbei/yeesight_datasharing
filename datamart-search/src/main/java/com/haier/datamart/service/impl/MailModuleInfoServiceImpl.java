package com.haier.datamart.service.impl;

import com.haier.datamart.entity.DataInterfaceExc;
import com.haier.datamart.entity.MailHeaderInfo;
import com.haier.datamart.entity.MailHeaderModule;
import com.haier.datamart.entity.MailHeaderModulePlaceholder;
import com.haier.datamart.entity.MailModuleInfo;
import com.haier.datamart.entity.MailSettingInfo;
import com.haier.datamart.mapper.MailModuleInfoMapper;
import com.haier.datamart.service.IDataInterfaceExcService;
import com.haier.datamart.service.IMailHeaderModulePlaceholderService;
import com.haier.datamart.service.IMailHeaderModuleService;
import com.haier.datamart.service.IMailModuleInfoService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.commons.lang3.StringUtils;
/**
 * @date 2019-01-10
 * @author zuoqb123
 * 邮件抬头信息服务实现类
 */
@Service
@Transactional
public class MailModuleInfoServiceImpl extends ServiceImpl<MailModuleInfoMapper, MailModuleInfo> implements IMailModuleInfoService,Constant {
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
     /**
     * @date 2019-01-10
     * @author zuoqb123
     * @todo   邮件抬头信息新增或者修改
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean saveOrUpdate(MailModuleInfo entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setId(UUIDUtils.getUuid());
			entity.setCreateDate(new Date());
			return mailModuleInfoMapper.insert(entity)>0;
		}else{
			entity.setUpdateDate(new Date());
			return mailModuleInfoMapper.updateById(entity)>0;
		}
	}
	/**
     * @date 2019-01-10
     * @author zuoqb123
     * @todo   邮件抬头信息逻辑删除
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean deleteLogic(String id) {
		MailModuleInfo entity=new MailModuleInfo();
		entity.setId(id);
		entity.setDelFlag(DEL_FLAG);
		entity.setUpdateDate(new Date());
		return mailModuleInfoMapper.updateById(entity)>0;
	}
	/**
     * @date 2019-01-10
     * @author zuoqb123
     * @todo   邮件抬头信息单条数据查询
     */
	@Override
	public MailModuleInfo getById(String id) {
		EntityWrapper<MailModuleInfo> wrapper = new EntityWrapper<MailModuleInfo>();
		wrapper.where("del_flag={0}",UN_DEL_FLAG);
		wrapper.eq("id", id);
		return selectOne(wrapper);
	}
	/**
     * @date   @date 2019-01-10
     * @author zuoqb123
     * @todo   邮件抬头信息分页查询
     */
	@Override
	public PageInfo<MailModuleInfo> pageList(BaseController c, HttpServletRequest request, MailModuleInfo entity,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<MailModuleInfo> wrapper = searchWrapper(c,request, entity);
		List<MailModuleInfo> list = mailModuleInfoMapper.selectPage(new RowBounds((pageNum-1)*pageSize, pageSize),wrapper);
		PageInfo<MailModuleInfo> page = new PageInfo<MailModuleInfo>();
		page.setList(list);
		page.setTotal(mailModuleInfoMapper.selectCount(wrapper));
		
		return page;
	}
	
	 /**
     * @date 2019-01-10
     * @author zuoqb123
     * @todo   邮件抬头信息构建查询条件-以后扩展
     */
    private EntityWrapper<MailModuleInfo> searchWrapper(BaseController c,HttpServletRequest request, MailModuleInfo entity) {
		EntityWrapper<MailModuleInfo> wrapper = new EntityWrapper<MailModuleInfo>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(c!=null&&request!=null&&c.getLoginUser(request)!=null&&StringUtils.isNotBlank(c.getLoginUser(request).getId())){
			if(!c.isAdmin(request))
			 wrapper.eq("create_by", c.getLoginUser(request).getId());
		}
								//根据字段ID模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
								//根据平台名称模糊查询
		if(entity.getModuleName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getModuleName()))){
			wrapper.like("module_name", String.valueOf(entity.getModuleName()));
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
