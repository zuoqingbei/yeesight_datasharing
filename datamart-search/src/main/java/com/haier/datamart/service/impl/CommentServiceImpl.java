package com.haier.datamart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.Comment;
import com.haier.datamart.mapper.CommentMapper;
import com.haier.datamart.service.ICommentService;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author dsh123
 * @since 2018-06-06
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
	@Autowired
	private CommentMapper commentMapper;
	@Override
	public List<Comment> findList(Comment comment) {
		return commentMapper.findList(comment);
	}

}
