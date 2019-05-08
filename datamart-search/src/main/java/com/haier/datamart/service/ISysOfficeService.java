package com.haier.datamart.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.SysOffice;


/**
 * 机构表服务类
 * @author zuoqb123
 * @date 2018-12-05
 */
public interface ISysOfficeService extends IService<SysOffice> {
	public List<SysOffice> getAllSysOffice(SysOffice sys);
 	
}
