package com.haier.datamart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.EnteringTableSettingDetail;
import com.haier.datamart.entity.EnteringTableSettingDetailData;
import com.haier.datamart.entity.EnteringTableSettingInfo;
import com.haier.datamart.mapper.EnteringTableSettingDetailDataMapper;
import com.haier.datamart.mapper.EnteringTableSettingDetailMapper;
import com.haier.datamart.mapper.EnteringTableSettingInfoMapper;
import com.haier.datamart.service.IEnteringTableSettingInfoService;
import com.haier.datamart.utils.Cron;
import com.haier.datamart.utils.GenerationSequenceUtil;

/**
 * <p>
 * 补录模块-补录数据表配置基础信息 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-07-02
 */
@Service
public class EnteringTableSettingInfoServiceImpl extends ServiceImpl<EnteringTableSettingInfoMapper, EnteringTableSettingInfo> implements IEnteringTableSettingInfoService {
	@Autowired
	private EnteringTableSettingDetailMapper detailMapper;
	@Autowired
	private EnteringTableSettingInfoMapper infoMapper;
	
	@Autowired
	public EnteringTableSettingDetailDataMapper enteringTableSettingDetailDataMapper;
	@Override
	public List<EnteringTableSettingDetail> getBySettingId(String enteringSettingId) {
		return detailMapper.getBySettingId(enteringSettingId);
	}

	@Override
	public List<EnteringTableSettingDetail> getBySettingId(
			String enteringSettingId, String isExport) {
		List<EnteringTableSettingDetail> list=new ArrayList<EnteringTableSettingDetail>();
		List<EnteringTableSettingDetail> all=detailMapper.getBySettingId(enteringSettingId);
		if(StringUtils.isNotBlank(isExport)){
			for(EnteringTableSettingDetail d:all){
				if(isExport.equals(d.getIsExport())){
					list.add(d);
				}
			}
		}else{
			list.addAll(all);
		}
		return list;
	}

	@Override
	public Integer getRelOrder(Integer defineOrder,String entrySettingId) {
		return detailMapper.getRelOrder(defineOrder,entrySettingId);
	}

	@Override
	public Integer getOrderById(String idParameter,String entrySettingId) {
		return detailMapper.getOrderById(idParameter,entrySettingId);
	}
	/**
	 * 根据表名查询数据
	 */
	@Override
	public EnteringTableSettingInfo getByName(String name,String dataId) {
	EnteringTableSettingInfo info=new EnteringTableSettingInfo();
	   info.setName(name);
	   info.setDatasourceConfigId(dataId);
		return infoMapper.getByName(info);
	}

	@Override
	public EnteringTableSettingInfo getById(String id) {
		// TODO Auto-generated method stub
		return infoMapper.getById(id);
	}
	/**
	 * 维护数据补录配置表
	 */
	@Override
	public int save(EnteringTableSettingInfo settingInfo) {
		int rex=0;
		String settingInfoId=settingInfo.getId();
		if(StringUtils.isBlank(settingInfoId)){
			settingInfoId=GenerationSequenceUtil.getUUID();
			settingInfo.setId(settingInfoId);
			rex = infoMapper.insertMethod(settingInfo);
			if (rex>0) {
				//新增成功 调起定时任务
				try {
					if(settingInfo.getStartCron()!=null&&settingInfo.getEndCron()!=null&&StringUtils.isNotBlank(settingInfo.getStartCron())){
						//开始时间调度
						Cron.task(settingInfoId, settingInfo.getStartCron(),settingInfo.getName(),"1");
						//结束时间调度
						Cron.task(settingInfoId, settingInfo.getEndCron(),settingInfo.getName(),"0");
					}
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//删除
					infoMapper.deleteById(settingInfoId);
					return 3;
				}
			}
		}else{
			try {
				
				//先移除任务
				Cron.remove(settingInfo.getName());
				//修改成功 调起定时任务
				rex=infoMapper.updateById(settingInfo);
				try {
					if(settingInfo.getStartCron()!=null&&settingInfo.getEndCron()!=null&&StringUtils.isNotBlank(settingInfo.getStartCron())){

					//开始时间调度
					Cron.task(settingInfoId, settingInfo.getStartCron(),settingInfo.getName(),"1");
					//结束时间调度
					Cron.task(settingInfoId, settingInfo.getEndCron(),settingInfo.getName(),"0");
			
					}
					} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//删除
					
					return 3;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		};
		List<EnteringTableSettingDetail> etDetail = settingInfo.getEtDetail();
		for(int i=0;i<etDetail.size();i++){
			if(StringUtils.isBlank(etDetail.get(i).getId())){
				etDetail.get(i).setId(GenerationSequenceUtil.getUUID());
				etDetail.get(i).setEnteringSettingId(settingInfoId);
				etDetail.get(i).setCreateBy(settingInfo.getCreateBy());
				etDetail.get(i).setCreateDate(settingInfo.getCreateDate());
				etDetail.get(i).setUpdateBy(settingInfo.getUpdateBy());
				etDetail.get(i).setUpdateDate(settingInfo.getUpdateDate());
				etDetail.get(i).setOrderNo(i);
				detailMapper.insert(etDetail.get(i));
			}else{
				//修改
				etDetail.get(i).setEnteringSettingId(settingInfoId);
				etDetail.get(i).setUpdateBy(settingInfo.getUpdateBy());
				etDetail.get(i).setUpdateDate(settingInfo.getUpdateDate());
				//etDetail.get(i).setOrderNo(i);
				detailMapper.updateById(etDetail.get(i));
			}
			
		}
		return rex;
	}

	@Override
	public List<EnteringTableSettingInfo> getSettingInfoDetail(
			EnteringTableSettingInfo settinginfo) {
		// TODO Auto-generated method stub
		return infoMapper.getSettingInfoDetail(settinginfo);
	}

	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		detailMapper.deleteBySettingId(id);
		return infoMapper.deleteById(id);
	}
	public void deleteOneEntry(String tableName, String idOfEntry) {
		infoMapper.deleteOneEntry(tableName,idOfEntry);
	}

	@Override
	public int changestatus(String id,String status) {
		EnteringTableSettingInfo info=new EnteringTableSettingInfo();
		info.setId(id);
		info.setStatus(status);
		return infoMapper.changestatus(info);
	}

	@Override
	public List<EnteringTableSettingInfo> getAll() {
		// TODO Auto-generated method stub
		return infoMapper.getAll();
	}

	@Override
	public EnteringTableSettingInfo getSettingInfoByDesc(String desc) {
		return infoMapper.getSettingInfoByDesc(desc);
	}

	@Override
	public List<EnteringTableSettingDetail> getBySettingIdIncludeData(
			String settingId) {
		List<EnteringTableSettingDetail> list=detailMapper.getBySettingId(settingId);
		for(EnteringTableSettingDetail detail:list){
			List<EnteringTableSettingDetailData> detailData=enteringTableSettingDetailDataMapper.getDetailDataByDetailId(detail.getId());
			detail.setDetailData(detailData);
		}
		return list;
	}


}
