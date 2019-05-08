package com.haier.datamart.service;

import com.haier.datamart.entity.DocFileCode;
import com.baomidou.mybatisplus.service.IService;


/**
 * 文件编码表服务类
 * @author zuoqb123
 * @date 2018-11-12
 */
public interface IDocFileCodeService extends IService<DocFileCode> {
	public int getMaxNums(String type,String sub1,String sub2,String sub3);
 	
}
