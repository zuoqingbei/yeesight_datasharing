package com.haier.datamart.service.impl;

import java.util.Date;
import java.util.List;

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
import com.haier.datamart.entity.MailReceiverManager;
import com.haier.datamart.entity.MonitorEtlMailReceiver;
import com.haier.datamart.mapper.MailReceiverManagerMapper;
import com.haier.datamart.service.IMailReceiverManagerService;
import com.haier.datamart.service.IMonitorEtlMailReceiverService;
import com.haier.datamart.utils.UUIDUtils;
/**
 * @date 2019-01-17
 * @author zuoqb123
 * 邮件收件人管理服务实现类
 */
@Service
@Transactional
public class MailReceiverManagerServiceImpl extends ServiceImpl<MailReceiverManagerMapper, MailReceiverManager> implements IMailReceiverManagerService,Constant {

    @Autowired
    private MailReceiverManagerMapper mailReceiverManagerMapper;
    @Autowired
    private IMonitorEtlMailReceiverService iMonitorEtlMailReceiverService;
    
     /**
     * @date 2019-01-17
     * @author zuoqb123
     * @todo   邮件收件人管理新增或者修改
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean saveOrUpdate(MailReceiverManager entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setId(UUIDUtils.getUuid());
			entity.setCreateDate(new Date());
			return mailReceiverManagerMapper.insert(entity)>0;
		}else{
			entity.setUpdateDate(new Date());
			return mailReceiverManagerMapper.updateById(entity)>0;
		}
	}
	/**
     * @date 2019-01-17
     * @author zuoqb123
     * @todo   邮件收件人管理物理删除
     */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteLogic(String id) {
		Wrapper<MailReceiverManager> wrapper = new EntityWrapper<>();
		wrapper.eq("id", id);
	 	MailReceiverManager entity=new MailReceiverManager();
		entity.setDelFlag(DEL_FLAG);
		entity.setUpdateDate(new Date()); 
		//删除关联信息
		Wrapper<MonitorEtlMailReceiver> wrapper2 = new EntityWrapper<>();
		MonitorEtlMailReceiver entity2 = new MonitorEtlMailReceiver();
		entity2.setDelFlag(DEL_FLAG);
		entity2.setUpdateDate(new Date()); 
		wrapper2.eq("mail_receiver_manager_id", id);
		iMonitorEtlMailReceiverService.update(entity2,wrapper2);
		
		
		return this.update(entity, wrapper);
	}
	/**
     * @date 2019-01-17
     * @author zuoqb123
     * @todo   邮件收件人管理单条数据查询
     */
	@Override
	public MailReceiverManager getById(String id) {
		EntityWrapper<MailReceiverManager> wrapper = new EntityWrapper<MailReceiverManager>();
		wrapper.where("del_flag={0}",UN_DEL_FLAG);
		wrapper.eq("id", id);
		return selectOne(wrapper);
	}
	/**
     * @date   @date 2019-01-17
     * @author zuoqb123
     * @todo   邮件收件人管理分页查询
     */
	@Override
	public PageInfo<MailReceiverManager> pageList(BaseController c, HttpServletRequest request, MailReceiverManager entity,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<MailReceiverManager> wrapper = searchWrapper(c,request, entity);
		wrapper.isNotNull("email");
		List<MailReceiverManager> list = mailReceiverManagerMapper.selectPage(new RowBounds((pageNum-1)*pageSize, pageSize),wrapper);
		PageInfo<MailReceiverManager> page = new PageInfo<MailReceiverManager>();
		page.setList(list);
		page.setTotal(mailReceiverManagerMapper.selectCount(wrapper));
		
		return page;
	}
	
	 /**
     * @date 2019-01-17
     * @author zuoqb123
     * @todo   邮件收件人管理构建查询条件-以后扩展
     */
    private EntityWrapper<MailReceiverManager> searchWrapper(BaseController c,HttpServletRequest request, MailReceiverManager entity) {
		EntityWrapper<MailReceiverManager> wrapper = new EntityWrapper<MailReceiverManager>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(c!=null&&request!=null&&c.getLoginUser(request)!=null&&StringUtils.isNotBlank(c.getLoginUser(request).getId())){
			if(!c.isAdmin(request))
			 wrapper.eq("create_by", c.getLoginUser(request).getId());
		}
								//根据主键模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
								//根据模块id模糊查询
		if(entity.getModuleId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getModuleId()))){
			wrapper.like("module_id", String.valueOf(entity.getModuleId()));
		}
								//根据平台id模糊查询
		if(entity.getPlatId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getPlatId()))){
			wrapper.like("plat_id", String.valueOf(entity.getPlatId()));
		}
								//根据用户名模糊查询
		if(entity.getName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getName()))){
			wrapper.like("name", String.valueOf(entity.getName()));
		}
								//根据电话模糊查询
		if(entity.getTel()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getTel()))){
			wrapper.like("tel", String.valueOf(entity.getTel()));
		}
								//根据邮箱模糊查询
		if(entity.getEmail()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getEmail()))){
			wrapper.like("email", String.valueOf(entity.getEmail()));
		}
								//根据工号模糊查询
		if(entity.getNumbers()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getNumbers()))){
			wrapper.like("numbers", String.valueOf(entity.getNumbers()));
		}
								//根据部门模糊查询
		if(entity.getDept()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDept()))){
			wrapper.like("dept", String.valueOf(entity.getDept()));
		}
								//根据 导入状态 0-成功 1 -失败模糊查询
		if(entity.getIsSuccess()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getIsSuccess()))){
			wrapper.like("is_success", String.valueOf(entity.getIsSuccess()));
		}
								//根据错误列 逗号拼接模糊查询
		if(entity.getErrorCol()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getErrorCol()))){
			wrapper.like("error_col", String.valueOf(entity.getErrorCol()));
		}
								//根据错误信息 逗号拼接模糊查询
		if(entity.getErrorMsg()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getErrorMsg()))){
			wrapper.like("error_msg", String.valueOf(entity.getErrorMsg()));
		}
								//根据排序模糊查询
		if(entity.getOrderNo()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getOrderNo()))){
			wrapper.like("order_no", String.valueOf(entity.getOrderNo()));
		}
								//根据创建时间模糊查询
		if(entity.getCreateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))){
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
								//根据创建者模糊查询
		if(entity.getCreateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
								//根据更新时间模糊查询
		if(entity.getUpdateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))){
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
								//根据更新人模糊查询
		if(entity.getUpdateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))){
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
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
