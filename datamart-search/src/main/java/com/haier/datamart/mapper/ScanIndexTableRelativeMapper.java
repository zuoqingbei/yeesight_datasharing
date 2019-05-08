package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.entity.ScanIndexTableRelative;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 指标与列行对应表 Mapper 接口
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
public interface ScanIndexTableRelativeMapper extends BaseMapper<ScanIndexTableRelative> {
	List<ScanIndexTableRelative> findList(ScanIndexTableRelative scanindextablerelative);
   int add(ScanIndexTableRelative relative);
   
   int updateDelete(ScanIndexTableRelative tableRelative);
   
   List<ScanIndexTableRelative> getIndex(String id);
   List<AdminDataContentDetail> getColumn(ScanIndexTableRelative relative);
   
   int update(ScanIndexTableRelative relative);
    String selectByuid(@Param("uid")String uid); 
  
}
