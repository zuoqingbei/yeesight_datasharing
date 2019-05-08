package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.SearchComment;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-27
 */
public interface SearchCommentMapper extends BaseMapper<SearchComment> {
     int  addComment(SearchComment comment);
     List<SearchComment> getList(SearchComment comment);
}
