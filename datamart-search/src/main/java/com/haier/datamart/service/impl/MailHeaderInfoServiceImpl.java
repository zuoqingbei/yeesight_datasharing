package com.haier.datamart.service.impl;

import com.haier.datamart.entity.MailHeaderInfo;
import com.haier.datamart.mapper.MailHeaderInfoMapper;
import com.haier.datamart.service.IMailHeaderInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import com.haier.datamart.utils.UUIDUtils;
import com.haier.datamart.config.Constant;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.controller.BaseController;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.apache.commons.lang3.StringUtils;
/**
 * @date 2019-01-03
 * @author zuoqb123
 * 邮件抬头信息服务实现类
 */
@Service
@Transactional
public class MailHeaderInfoServiceImpl extends ServiceImpl<MailHeaderInfoMapper, MailHeaderInfo> implements IMailHeaderInfoService,Constant {

    @Autowired
    private MailHeaderInfoMapper mailHeaderInfoMapper;
    
     /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头信息新增或者修改
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean saveOrUpdate(MailHeaderInfo entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setId(UUIDUtils.getUuid());
			entity.setCreateDate(new Date());
			return mailHeaderInfoMapper.insert(entity)>0;
		}else{
			entity.setUpdateDate(new Date());
			return mailHeaderInfoMapper.updateById(entity)>0;
		}
	}
	/**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头信息逻辑删除
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean deleteLogic(String id) {
		MailHeaderInfo entity=new MailHeaderInfo();
		entity.setId(id);
		entity.setDelFlag(DEL_FLAG);
		entity.setUpdateDate(new Date());
		return mailHeaderInfoMapper.updateById(entity)>0;
	}
	/**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头信息单条数据查询
     */
	@Override
	public MailHeaderInfo getById(String id) {
		EntityWrapper<MailHeaderInfo> wrapper = new EntityWrapper<MailHeaderInfo>();
		wrapper.where("del_flag={0}",UN_DEL_FLAG);
		wrapper.eq("id", id);
		return selectOne(wrapper);
	}
	/**
     * @date   @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头信息分页查询
     */
	@Override
	public PageInfo<MailHeaderInfo> pageList(BaseController c, HttpServletRequest request, MailHeaderInfo entity,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<MailHeaderInfo> wrapper = searchWrapper(c,request, entity);
		List<MailHeaderInfo> list = mailHeaderInfoMapper.selectPage(new RowBounds((pageNum-1)*pageSize, pageSize),wrapper);
		PageInfo<MailHeaderInfo> page = new PageInfo<MailHeaderInfo>();
		page.setList(list);
		page.setTotal(mailHeaderInfoMapper.selectCount(wrapper));
		
		return page;
	}
	
	 /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头信息构建查询条件-以后扩展
     */
    private EntityWrapper<MailHeaderInfo> searchWrapper(BaseController c,HttpServletRequest request, MailHeaderInfo entity) {
		EntityWrapper<MailHeaderInfo> wrapper = new EntityWrapper<MailHeaderInfo>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(c!=null&&request!=null&&c.getLoginUser(request)!=null&&StringUtils.isNotBlank(c.getLoginUser(request).getId())){
			if(!c.isAdmin(request))
			 wrapper.eq("create_by", c.getLoginUser(request).getId());
		}
								//根据字段ID模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
								//根据邮件编码模糊查询
		if(entity.getMailId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMailId()))){
			wrapper.like("mail_id", String.valueOf(entity.getMailId()));
		}
								//根据原始语句范例模糊查询
		if(entity.getContent()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getContent()))){
			wrapper.like("content", String.valueOf(entity.getContent()));
		}
								//根据抬头预设置编码  如果是直接输入的值则为空模糊查询
		if(entity.getHeaderModuleId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getHeaderModuleId()))){
			wrapper.like("header_module_id", String.valueOf(entity.getHeaderModuleId()));
		}
								//根据排序模糊查询
		if(entity.getOrderNo()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getOrderNo()))){
			wrapper.like("order_no", String.valueOf(entity.getOrderNo()));
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
    
	@Override
	@Transactional
	public boolean save(String maiId, List<MailHeaderInfo> headerModules) {
		//先删除
		EntityWrapper<MailHeaderInfo> wrapper = new EntityWrapper<MailHeaderInfo>();
		wrapper.eq("mail_id", maiId);
		boolean flag1  =  delete(wrapper);//物理删除
		//增加
		boolean flag2 = insertBatch(headerModules);
		
		return flag2&&flag1;
	}
   
}
