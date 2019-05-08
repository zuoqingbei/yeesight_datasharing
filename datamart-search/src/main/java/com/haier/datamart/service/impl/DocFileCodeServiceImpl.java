package com.haier.datamart.service.impl;

import java.util.List;
import java.util.Map;

import com.haier.datamart.entity.DocFileCode;
import com.haier.datamart.mapper.DocFileCodeMapper;
import com.haier.datamart.service.IDocFileCodeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 文件编码表服务实现类
 * @author zuoqb123
 * @date 2018-11-12
 */
@Service
@Transactional
public class DocFileCodeServiceImpl extends ServiceImpl<DocFileCodeMapper, DocFileCode> implements IDocFileCodeService {

    @Autowired
    private DocFileCodeMapper docFileCodeMapper;

	@Override
	public int getMaxNums(String type, String sub1, String sub2, String sub3) {
		//查询序号
		EntityWrapper<DocFileCode> wrapper = new EntityWrapper<DocFileCode>();
		wrapper.where("del_flag={0}", 0);
		wrapper.eq("type", type);
		wrapper.eq("sub1", sub1);
		wrapper.eq("sub2", sub2);
		wrapper.eq("sub3", sub3);
		wrapper.setSqlSelect(" max(nums) as nums");
		List<Map<String, Object>> result=docFileCodeMapper.selectMaps(wrapper);
		if(result!=null&&result.get(0)!=null){
			return Integer.valueOf(result.get(0).get("nums")+"");
		}
		return 0;
	}
   
}
