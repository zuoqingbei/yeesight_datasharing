package com.haier.datamart.service.impl;

import com.haier.datamart.entity.MailReceiverUserInfo;
import com.haier.datamart.mapper.MailReceiverUserInfoMapper;
import com.haier.datamart.service.IMailReceiverUserInfoService;
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
 * 邮件模块收件人原始信息信息服务实现类
 */
@Service
@Transactional
public class MailReceiverUserInfoServiceImpl extends ServiceImpl<MailReceiverUserInfoMapper, MailReceiverUserInfo> implements IMailReceiverUserInfoService,Constant {

    @Autowired
    private MailReceiverUserInfoMapper mailReceiverUserInfoMapper;
    
     /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件模块收件人原始信息信息新增或者修改
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean saveOrUpdate(MailReceiverUserInfo entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setId(UUIDUtils.getUuid());
			entity.setCreateDate(new Date());
			return mailReceiverUserInfoMapper.insert(entity)>0;
		}else{
			entity.setUpdateDate(new Date());
			return mailReceiverUserInfoMapper.updateById(entity)>0;
		}
	}
	/**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件模块收件人原始信息信息逻辑删除
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean deleteLogic(String id) {
		MailReceiverUserInfo entity=new MailReceiverUserInfo();
		entity.setId(id);
		entity.setDelFlag(DEL_FLAG);
		entity.setUpdateDate(new Date());
		return mailReceiverUserInfoMapper.updateById(entity)>0;
	}
	/**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件模块收件人原始信息信息单条数据查询
     */
	@Override
	public MailReceiverUserInfo getById(String id) {
		EntityWrapper<MailReceiverUserInfo> wrapper = new EntityWrapper<MailReceiverUserInfo>();
		wrapper.where("del_flag={0}",UN_DEL_FLAG);
		wrapper.eq("id", id);
		return selectOne(wrapper);
	}
	/**
     * @date   @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件模块收件人原始信息信息分页查询
     */
	@Override
	public PageInfo<MailReceiverUserInfo> pageList(BaseController c, HttpServletRequest request, MailReceiverUserInfo entity,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<MailReceiverUserInfo> wrapper = searchWrapper(c,request, entity);
		List<MailReceiverUserInfo> list = mailReceiverUserInfoMapper.selectPage(new RowBounds((pageNum-1)*pageSize, pageSize),wrapper);
		PageInfo<MailReceiverUserInfo> page = new PageInfo<MailReceiverUserInfo>();
		page.setList(list);
		page.setTotal(mailReceiverUserInfoMapper.selectCount(wrapper));
		
		return page;
	}
	
	 /**
     * @date 2019-01-03
     * @author zuoqb123
     * @todo   邮件模块收件人原始信息信息构建查询条件-以后扩展
     */
    private EntityWrapper<MailReceiverUserInfo> searchWrapper(BaseController c,HttpServletRequest request, MailReceiverUserInfo entity) {
		EntityWrapper<MailReceiverUserInfo> wrapper = new EntityWrapper<MailReceiverUserInfo>();
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
			wrapper.like("industry", String.valueOf(entity.getModuleId()));
		}
								//根据用户名称模糊查询
		if(entity.getName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getName()))){
			wrapper.like("name", String.valueOf(entity.getName()));
		}
								//根据电话模糊查询
		if(entity.getTelphone()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getTelphone()))){
			wrapper.like("telphone", String.valueOf(entity.getTelphone()));
		}
								//根据邮箱模糊查询
		if(entity.getMail()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMail()))){
			wrapper.like("mail", String.valueOf(entity.getMail()));
		}
								//根据工号模糊查询
		if(entity.getNumbers()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getNumbers()))){
			wrapper.like("numbers", String.valueOf(entity.getNumbers()));
		}
								//根据部门模糊查询
		if(entity.getDept()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDept()))){
			wrapper.like("dept", String.valueOf(entity.getDept()));
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
