package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.SearchIndexPlat;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 指标平台中间表 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-07-09
 */
public interface SearchIndexPlatMapper extends BaseMapper<SearchIndexPlat> {
        int add(SearchIndexPlat plat);
        int update(SearchIndexPlat plat);
        int delete(String indexid);
        List<SearchIndexPlat>  selectbyid(String id);
}
