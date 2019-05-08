package com.haier.datamart.service.impl;

import com.haier.datamart.entity.MailPlatInfo;
import com.haier.datamart.mapper.MailPlatInfoMapper;
import com.haier.datamart.service.IMailPlatInfoService;
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
 * @date 2019-01-09
 * @author zuoqb123
 * 平台信息服务实现类
 */
@Service
@Transactional
public class MailPlatInfoServiceImpl extends ServiceImpl<MailPlatInfoMapper, MailPlatInfo> implements IMailPlatInfoService,Constant {

    @Autowired
    private MailPlatInfoMapper mailPlatInfoMapper;
    
     /**
     * @date 2019-01-09
     * @author zuoqb123
     * @todo   平台信息新增或者修改
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean saveOrUpdate(MailPlatInfo entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setId(UUIDUtils.getUuid());
			entity.setCreateDate(new Date());
			return mailPlatInfoMapper.insert(entity)>0;
		}else{
			entity.setUpdateDate(new Date());
			return mailPlatInfoMapper.updateById(entity)>0;
		}
	}
	/**
     * @date 2019-01-09
     * @author zuoqb123
     * @todo   平台信息逻辑删除
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean deleteLogic(String id) {
		MailPlatInfo entity=new MailPlatInfo();
		entity.setId(id);
		entity.setDelFlag(DEL_FLAG);
		entity.setUpdateDate(new Date());
		return mailPlatInfoMapper.updateById(entity)>0;
	}
	/**
     * @date 2019-01-09
     * @author zuoqb123
     * @todo   平台信息单条数据查询
     */
	@Override
	public MailPlatInfo getById(String id) {
		EntityWrapper<MailPlatInfo> wrapper = new EntityWrapper<MailPlatInfo>();
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
	public PageInfo<MailPlatInfo> pageList(BaseController c, HttpServletRequest request, MailPlatInfo entity,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<MailPlatInfo> wrapper = searchWrapper(c,request, entity);
		List<MailPlatInfo> list = mailPlatInfoMapper.selectPage(new RowBounds((pageNum-1)*pageSize, pageSize),wrapper);
		PageInfo<MailPlatInfo> page = new PageInfo<MailPlatInfo>();
		page.setList(list);
		page.setTotal(mailPlatInfoMapper.selectCount(wrapper));
		
		return page;
	}
	
	 /**
     * @date 2019-01-09
     * @author zuoqb123
     * @todo   平台信息构建查询条件-以后扩展
     */
    private EntityWrapper<MailPlatInfo> searchWrapper(BaseController c,HttpServletRequest request, MailPlatInfo entity) {
		EntityWrapper<MailPlatInfo> wrapper = new EntityWrapper<MailPlatInfo>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(c!=null&&request!=null&&c.getLoginUser(request)!=null&&StringUtils.isNotBlank(c.getLoginUser(request).getId())){
			if(!c.isAdmin(request))
			 wrapper.eq("create_by", c.getLoginUser(request).getId());
		}
								//根据字段ID模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
								//根据模板id(待用)模糊查询
		if(entity.getTempleteId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getTempleteId()))){
			wrapper.like("templete_id", String.valueOf(entity.getTempleteId()));
		}
								//根据模块id(待用)模糊查询
		if(entity.getModuleId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getModuleId()))){
			wrapper.like("module_id", String.valueOf(entity.getModuleId()));
		}
								//根据平台名称模糊查询
		if(entity.getPlatName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getPlatName()))){
			wrapper.like("plat_name", String.valueOf(entity.getPlatName()));
		}
								//根据申请人名称模糊查询
		if(entity.getProposerName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getProposerName()))){
			wrapper.like("proposer_name", String.valueOf(entity.getProposerName()));
		}
								//根据申请人联系方式模糊查询
		if(entity.getProposerContactWay()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getProposerContactWay()))){
			wrapper.like("proposer_contact_way", String.valueOf(entity.getProposerContactWay()));
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
		//根据is_complete 进行筛选
		if(StringUtils.isNotBlank(entity.getIsComplete())){
			wrapper.eq("is_complete", String.valueOf(entity.getIsComplete()));
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
