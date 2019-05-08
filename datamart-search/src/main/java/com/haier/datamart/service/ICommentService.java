package com.haier.datamart.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.Comment;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author dsh123
 * @since 2018-06-06
 */
public interface ICommentService extends IService<Comment> {
	List<Comment> findList(Comment comment);
}
