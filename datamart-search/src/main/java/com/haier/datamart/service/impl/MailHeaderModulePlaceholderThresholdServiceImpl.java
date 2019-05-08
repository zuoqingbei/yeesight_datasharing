package com.haier.datamart.service.impl;

import com.haier.datamart.entity.MailHeaderModulePlaceholderThreshold;
import com.haier.datamart.mapper.MailHeaderModulePlaceholderThresholdMapper;
import com.haier.datamart.service.IMailHeaderModulePlaceholderThresholdService;
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
 * @date 2019-02-13
 * @author zuoqb123
 * 邮件抬头预设置语句中占位符阈值控制服务实现类
 */
@Service
@Transactional
public class MailHeaderModulePlaceholderThresholdServiceImpl extends ServiceImpl<MailHeaderModulePlaceholderThresholdMapper, MailHeaderModulePlaceholderThreshold> implements IMailHeaderModulePlaceholderThresholdService,Constant {

    @Autowired
    private MailHeaderModulePlaceholderThresholdMapper mailHeaderModulePlaceholderThresholdMapper;
    
     /**
     * @date 2019-02-13
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符阈值控制新增或者修改
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean saveOrUpdate(MailHeaderModulePlaceholderThreshold entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setId(UUIDUtils.getUuid());
			entity.setCreateDate(new Date());
			return mailHeaderModulePlaceholderThresholdMapper.insert(entity)>0;
		}else{
			entity.setUpdateDate(new Date());
			return mailHeaderModulePlaceholderThresholdMapper.updateById(entity)>0;
		}
	}
	
	 @Transactional(rollbackFor = Exception.class)
	public boolean deleteAndSave(List<MailHeaderModulePlaceholderThreshold> list,String placeHolderId) {
		EntityWrapper<MailHeaderModulePlaceholderThreshold> wrapper = new EntityWrapper<MailHeaderModulePlaceholderThreshold>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		wrapper.eq("placeholder_id", placeHolderId);
		mailHeaderModulePlaceholderThresholdMapper.delete(wrapper);
		Date d=new Date();
		for(MailHeaderModulePlaceholderThreshold h:list){
			h.setId(UUIDUtils.getUuid());
			h.setPlaceholderId(placeHolderId);
			h.setCreateDate(d);
			h.setDelFlag(UN_DEL_FLAG);
			mailHeaderModulePlaceholderThresholdMapper.insert(h);
		}
		return true;
	}
	/**
     * @date 2019-02-13
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符阈值控制逻辑删除
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean deleteLogic(String id) {
		MailHeaderModulePlaceholderThreshold entity=new MailHeaderModulePlaceholderThreshold();
		entity.setId(id);
		entity.setDelFlag(DEL_FLAG);
		entity.setUpdateDate(new Date());
		return mailHeaderModulePlaceholderThresholdMapper.updateById(entity)>0;
	}
	/**
     * @date 2019-02-13
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符阈值控制单条数据查询
     */
	@Override
	public MailHeaderModulePlaceholderThreshold getById(String id) {
		EntityWrapper<MailHeaderModulePlaceholderThreshold> wrapper = new EntityWrapper<MailHeaderModulePlaceholderThreshold>();
		wrapper.where("del_flag={0}",UN_DEL_FLAG);
		wrapper.eq("id", id);
		return selectOne(wrapper);
	}
	/**
     * @date   @date 2019-02-13
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符阈值控制分页查询
     */
	@Override
	public PageInfo<MailHeaderModulePlaceholderThreshold> pageList(BaseController c, HttpServletRequest request, MailHeaderModulePlaceholderThreshold entity,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<MailHeaderModulePlaceholderThreshold> wrapper = searchWrapper(c,request, entity);
		List<MailHeaderModulePlaceholderThreshold> list = mailHeaderModulePlaceholderThresholdMapper.selectPage(new RowBounds((pageNum-1)*pageSize, pageSize),wrapper);
		PageInfo<MailHeaderModulePlaceholderThreshold> page = new PageInfo<MailHeaderModulePlaceholderThreshold>();
		page.setList(list);
		page.setTotal(mailHeaderModulePlaceholderThresholdMapper.selectCount(wrapper));
		
		return page;
	}
	
	/**
	 * 
	 * @time   2019年2月13日 下午2:27:36
	 * @author zuoqb
	 * @todo   根据占位符编码查询相应的阈值配置
	 * @param  @param placeHolderId
	 * @param  @return
	 */
	public List<MailHeaderModulePlaceholderThreshold> thresholdListByPlaceholderId(String placeHolderId){
		EntityWrapper<MailHeaderModulePlaceholderThreshold> wrapper = new EntityWrapper<MailHeaderModulePlaceholderThreshold>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		wrapper.eq("placeholder_id", placeHolderId);
		return mailHeaderModulePlaceholderThresholdMapper.selectList(wrapper);
	}
	
	 /**
     * @date 2019-02-13
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符阈值控制构建查询条件-以后扩展
     */
    private EntityWrapper<MailHeaderModulePlaceholderThreshold> searchWrapper(BaseController c,HttpServletRequest request, MailHeaderModulePlaceholderThreshold entity) {
		EntityWrapper<MailHeaderModulePlaceholderThreshold> wrapper = new EntityWrapper<MailHeaderModulePlaceholderThreshold>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(c!=null&&request!=null&&c.getLoginUser(request)!=null&&StringUtils.isNotBlank(c.getLoginUser(request).getId())){
			if(!c.isAdmin(request))
			 wrapper.eq("create_by", c.getLoginUser(request).getId());
		}
								//根据字段ID模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
								//根据占位符编码模糊查询
		if(entity.getPlaceholderId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getPlaceholderId()))){
			wrapper.like("placeholder_id", String.valueOf(entity.getPlaceholderId()));
		}
								//根据运算符  >; < ;>= ;<=模糊查询
		if(entity.getYunsuanfu()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getYunsuanfu()))){
			wrapper.like("yunsuanfu", String.valueOf(entity.getYunsuanfu()));
		}
								//根据阈值 模糊查询
		if(entity.getThresholdValue()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getThresholdValue()))){
			wrapper.like("threshold_value", String.valueOf(entity.getThresholdValue()));
		}
								//根据对比之后的操作 0-显示 1-隐藏模糊查询
		if(entity.getOpt()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getOpt()))){
			wrapper.like("opt", String.valueOf(entity.getOpt()));
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
