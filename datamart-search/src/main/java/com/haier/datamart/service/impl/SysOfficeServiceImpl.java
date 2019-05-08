package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.SysOffice;
import com.haier.datamart.mapper.SysOfficeMapper;
import com.haier.datamart.service.IAdminDataContentDetailService;
import com.haier.datamart.service.IAdminDataContentService;
import com.haier.datamart.service.IAdminDatasourceConfigService;
import com.haier.datamart.service.ISysOfficeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 机构表服务实现类
 * @author zuoqb123
 * @date 2018-12-05
 */
@Service
@Transactional
public class SysOfficeServiceImpl extends ServiceImpl<SysOfficeMapper, SysOffice> implements ISysOfficeService {

    @Autowired
    private SysOfficeMapper sysOfficeMapper;
    @Autowired
	private IAdminDatasourceConfigService configService;
	@Autowired
	private IAdminDataContentService contentService;
	@Autowired
	private IAdminDataContentDetailService detailService;

	@Override
	public List<SysOffice> getAllSysOffice(SysOffice sys) {
		return getSysOfficeByPid(sys, "0");
	}
	
	public List<SysOffice> getSysOfficeById(SysOffice sys,String id){
		EntityWrapper<SysOffice> wrapper = new EntityWrapper<SysOffice>();
		wrapper.where("del_flag={0}", 0);
		wrapper.eq("id", id);
		if(StringUtils.isNotBlank(sys.getName())){
			wrapper.like("name", sys.getName());
		}
		wrapper.orderBy("sort", true);
		List<SysOffice> list=sysOfficeMapper.selectList(wrapper);
		for(SysOffice o:list){
			o.setDatasourceConfig(configService.getAllByOfficeId(o.getId()));
		}
		return list;
	}
	public List<SysOffice> getSysOfficeByPid(SysOffice sys,String pid){
		EntityWrapper<SysOffice> wrapper = new EntityWrapper<SysOffice>();
		wrapper.where("del_flag={0}", 0);
		wrapper.eq("parent_id", pid);
		if(StringUtils.isNotBlank(sys.getName())){
			wrapper.like("name", sys.getName());
		}
		wrapper.orderBy("sort", true);
		List<SysOffice> list=sysOfficeMapper.selectList(wrapper);
		for(SysOffice o:list){
			o.setChildren(getSysOfficeByPid(sys,o.getId()));
			o.setDatasourceConfig(configService.getAllByOfficeId(o.getId()));
		}
		return list;
	}
   
}
