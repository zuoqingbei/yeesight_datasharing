package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.AdminDataContentDetail;

/**
 * <p>
 * 数据展示内容配置明细 Mapper 接口
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
public interface AdminDataContentDetailMapper extends BaseMapper<AdminDataContentDetail> {
	List<AdminDataContentDetail> findList(AdminDataContentDetail admindatacontentdetail);
	List<AdminDataContentDetail> getInclude(String id);
	public void insertBatch(@Param(value="contentDetail") List<AdminDataContentDetail> contentDetail);
	List<AdminDataContentDetail> findListByScan(AdminDataContentDetail adminDataContentDetail);
	List<AdminDataContentDetail> findListByScan2(AdminDataContentDetail adminDataContentDetail);
	void update(AdminDataContentDetail admindatacontentdetail);
	void updateListByScan(@Param(value="contentDetail") List<AdminDataContentDetail> contentDetail);
	int add(AdminDataContentDetail detail);
	AdminDataContentDetail getDetail(AdminDataContentDetail detail);
	List<AdminDataContentDetail> getByCid(String id);
    List<AdminDataContentDetail> getAll();
    List<AdminDataContentDetail> getAllby(String id);
    List<AdminDataContentDetail> getAllbyName(String id);
    List<AdminDataContentDetail> getList(AdminDataContentDetail detail);
    String  selectByuid(@Param("uid")String uid);
    int deletebyconfigID(String id);
    
    List<AdminDataContentDetail> getColumnByContent(AdminDataContentDetail detail);
    
    List<AdminDataContentDetail> getAllByDb(String dataSourceId);
}
