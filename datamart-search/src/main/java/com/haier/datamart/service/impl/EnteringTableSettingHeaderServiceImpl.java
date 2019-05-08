package com.haier.datamart.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.datamart.entity.EnteringTableSettingHeader;
import com.haier.datamart.mapper.EnteringTableSettingHeaderMapper;
import com.haier.datamart.service.IEnteringTableSettingHeaderService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 补录模块Excel头部信息服务实现类
 * @author zuoqb123
 * @date 2018-10-16
 */
@Service
@Transactional
public class EnteringTableSettingHeaderServiceImpl extends ServiceImpl<EnteringTableSettingHeaderMapper, EnteringTableSettingHeader> implements IEnteringTableSettingHeaderService {

    @Autowired
    private EnteringTableSettingHeaderMapper enteringTableSettingHeaderMapper;

	@Override
	public int saveHeaders(List<EnteringTableSettingHeader> headers,String enteringSettingId) {
		int result=0;
		deleteHeadersByEnteringId(enteringSettingId);
		for(EnteringTableSettingHeader header:headers){
			header.setCreateDate(new Date());
			header.setDelFlag("0");
			header.setEnteringSettingId(enteringSettingId);
			result+=enteringTableSettingHeaderMapper.insert(header);
		}
		return result;
	}

	@Override
	public List<EnteringTableSettingHeader> getHeadersByEnteringId(
			String enteringSettingId) {
		EntityWrapper<EnteringTableSettingHeader> wrapper = new EntityWrapper<EnteringTableSettingHeader>();
		wrapper.where("del_flag={0}", 0);
		wrapper.eq("entering_setting_id", enteringSettingId);
		wrapper.orderBy("rownum", true);
		wrapper.orderBy("order_no", true);
		return enteringTableSettingHeaderMapper.selectList(wrapper);
	}

	@Override
	public int deleteHeadersByEnteringId(String enteringSettingId) {
		Map<String, Object> columnMap=new HashMap<String, Object>();
		columnMap.put("entering_setting_id", enteringSettingId);
		return enteringTableSettingHeaderMapper.deleteByMap(columnMap);
	}

	@Override
	public int getHeadersRowNums(String enteringSettingId) {
		EntityWrapper<EnteringTableSettingHeader> wrapper = new EntityWrapper<EnteringTableSettingHeader>();
		wrapper.setSqlSelect(" max(rownum) as rownum ");
		wrapper.where("del_flag={0}", 0);
		wrapper.eq("entering_setting_id", enteringSettingId);
		List<EnteringTableSettingHeader> list=enteringTableSettingHeaderMapper.selectList(wrapper);
		if(list!=null&&list.size()>0&&list.get(0)!=null){
			return list.get(0).getRownum()+1;
		}
		return 0;
	}
   
}
