package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.Comment;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author dsh123
 * @since 2018-06-06
 */
public interface CommentMapper extends BaseMapper<Comment> {
	List<Comment> findList(Comment comment);
}
