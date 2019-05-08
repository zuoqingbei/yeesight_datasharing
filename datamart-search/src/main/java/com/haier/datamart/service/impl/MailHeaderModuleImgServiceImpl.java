package com.haier.datamart.service.impl;

import com.haier.datamart.entity.MailHeaderModuleImg;
import com.haier.datamart.mapper.MailHeaderModuleImgMapper;
import com.haier.datamart.service.IMailHeaderModuleImgService;
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
 * 邮件抬头预设置图片信息服务实现类
 */
@Service
@Transactional
public class MailHeaderModuleImgServiceImpl extends ServiceImpl<MailHeaderModuleImgMapper, MailHeaderModuleImg> implements IMailHeaderModuleImgService,Constant {

    @Autowired
    private MailHeaderModuleImgMapper mailHeaderModuleImgMapper;
    
     /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置图片信息新增或者修改
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean saveOrUpdate(MailHeaderModuleImg entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setId(UUIDUtils.getUuid());
			entity.setCreateDate(new Date());
			return mailHeaderModuleImgMapper.insert(entity)>0;
		}else{
			entity.setUpdateDate(new Date());
			return mailHeaderModuleImgMapper.updateById(entity)>0;
		}
	}
	/**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置图片信息逻辑删除
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean deleteLogic(String id) {
		MailHeaderModuleImg entity=new MailHeaderModuleImg();
		entity.setId(id);
		entity.setDelFlag(DEL_FLAG);
		entity.setUpdateDate(new Date());
		return mailHeaderModuleImgMapper.updateById(entity)>0;
	}
	/**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置图片信息单条数据查询
     */
	@Override
	public MailHeaderModuleImg getById(String id) {
		EntityWrapper<MailHeaderModuleImg> wrapper = new EntityWrapper<MailHeaderModuleImg>();
		wrapper.where("del_flag={0}",UN_DEL_FLAG);
		wrapper.eq("id", id);
		return selectOne(wrapper);
	}
	/**
     * @date   @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置图片信息分页查询
     */
	@Override
	public PageInfo<MailHeaderModuleImg> pageList(BaseController c, HttpServletRequest request, MailHeaderModuleImg entity,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<MailHeaderModuleImg> wrapper = searchWrapper(c,request, entity);
		List<MailHeaderModuleImg> list = mailHeaderModuleImgMapper.selectPage(new RowBounds((pageNum-1)*pageSize, pageSize),wrapper);
		PageInfo<MailHeaderModuleImg> page = new PageInfo<MailHeaderModuleImg>();
		page.setList(list);
		page.setTotal(mailHeaderModuleImgMapper.selectCount(wrapper));
		
		return page;
	}
	
	 /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置图片信息构建查询条件-以后扩展
     */
    private EntityWrapper<MailHeaderModuleImg> searchWrapper(BaseController c,HttpServletRequest request, MailHeaderModuleImg entity) {
		EntityWrapper<MailHeaderModuleImg> wrapper = new EntityWrapper<MailHeaderModuleImg>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(c!=null&&request!=null&&c.getLoginUser(request)!=null&&StringUtils.isNotBlank(c.getLoginUser(request).getId())){
			if(!c.isAdmin(request))
			 wrapper.eq("create_by", c.getLoginUser(request).getId());
		}
								//根据字段ID模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
								//根据抬头编码模糊查询
		if(entity.getHeaderModuleId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getHeaderModuleId()))){
			wrapper.like("header_module_id", String.valueOf(entity.getHeaderModuleId()));
		}
								//根据图片地址模糊查询
		if(entity.getUrl()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUrl()))){
			wrapper.like("url", String.valueOf(entity.getUrl()));
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
