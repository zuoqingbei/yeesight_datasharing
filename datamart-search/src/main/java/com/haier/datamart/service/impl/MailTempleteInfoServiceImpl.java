package com.haier.datamart.service.impl;

import com.haier.datamart.entity.MailTempleteInfo;
import com.haier.datamart.mapper.MailTempleteInfoMapper;
import com.haier.datamart.service.IMailTempleteInfoService;
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
 * 邮件模板信息（后台配置，给业务前台使用）服务实现类
 */
@Service
@Transactional
public class MailTempleteInfoServiceImpl extends ServiceImpl<MailTempleteInfoMapper, MailTempleteInfo> implements IMailTempleteInfoService,Constant {

    @Autowired
    private MailTempleteInfoMapper mailTempleteInfoMapper;
    
     /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件模板信息（后台配置，给业务前台使用）新增或者修改
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean saveOrUpdate(MailTempleteInfo entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setId(UUIDUtils.getUuid());
			entity.setCreateDate(new Date());
			return mailTempleteInfoMapper.insert(entity)>0;
		}else{
			entity.setUpdateDate(new Date());
			return mailTempleteInfoMapper.updateById(entity)>0;
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
		MailTempleteInfo entity=new MailTempleteInfo();
		entity.setId(id);
		entity.setDelFlag(DEL_FLAG);
		entity.setUpdateDate(new Date());
		return mailTempleteInfoMapper.updateById(entity)>0;
	}
	/**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件模板信息（后台配置，给业务前台使用）单条数据查询
     */
	@Override
	public MailTempleteInfo getById(String id) {
		EntityWrapper<MailTempleteInfo> wrapper = new EntityWrapper<MailTempleteInfo>();
		wrapper.where("del_flag={0}",UN_DEL_FLAG);
		wrapper.eq("id", id);
		return selectOne(wrapper);
	}
	/**
     * @date   @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件模板信息（后台配置，给业务前台使用）分页查询
     */
	@Override
	public PageInfo<MailTempleteInfo> pageList(BaseController c, HttpServletRequest request, MailTempleteInfo entity,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<MailTempleteInfo> wrapper = searchWrapper(c,request, entity);
		List<MailTempleteInfo> list = mailTempleteInfoMapper.selectPage(new RowBounds((pageNum-1)*pageSize, pageSize),wrapper);
		PageInfo<MailTempleteInfo> page = new PageInfo<MailTempleteInfo>();
		page.setList(list);
		page.setTotal(mailTempleteInfoMapper.selectCount(wrapper));
		
		return page;
	}
	
	 /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件模板信息（后台配置，给业务前台使用）构建查询条件-以后扩展
     */
    private EntityWrapper<MailTempleteInfo> searchWrapper(BaseController c,HttpServletRequest request, MailTempleteInfo entity) {
		EntityWrapper<MailTempleteInfo> wrapper = new EntityWrapper<MailTempleteInfo>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(c!=null&&request!=null&&c.getLoginUser(request)!=null&&StringUtils.isNotBlank(c.getLoginUser(request).getId())){
			if(!c.isAdmin(request))
			 wrapper.eq("create_by", c.getLoginUser(request).getId());
		}
								//根据字段ID模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
								//根据模板名称模糊查询
		if(entity.getTempleteName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getTempleteName()))){
			wrapper.like("templete_name", String.valueOf(entity.getTempleteName()));
		}
								//根据收件人信息 monitor_etl_mail_receiver的reciver_id模糊查询
		if(entity.getReciverId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getReciverId()))){
			wrapper.like("reciver_id", String.valueOf(entity.getReciverId()));
		}
								//根据模板样式模糊查询
		if(entity.getStyle()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getStyle()))){
			wrapper.like("style", String.valueOf(entity.getStyle()));
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
