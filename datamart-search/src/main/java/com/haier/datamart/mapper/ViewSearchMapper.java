package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.ViewSearch;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-24
 */
public interface ViewSearchMapper extends BaseMapper<ViewSearch> {
	List<ViewSearch> getAll(ViewSearch view);
	List<ViewSearch> getByKeyword(ViewSearch view);
	ViewSearch getByPK(ViewSearch view);
	List<ViewSearch> getTuijian(@Param("par")String par);
	List<ViewSearch> getallTuijian();
	ViewSearch getCount(ViewSearch view);
	int changeVZSNum(@Param("type")String type,@Param("id")String id,@Param("numType")String numType);
    List<ViewSearch> getNewadd();
    List<ViewSearch> getByKeywordList(ViewSearch view);
}

