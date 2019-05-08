package com.haier.datamart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.Comment;
import com.haier.datamart.service.ICommentService;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author dsh123
 * @since 2018-06-06
 */
@RestController
public class CommentController {
	@Autowired
	private ICommentService iCommentService;

	@GetMapping(value = "/search/comment/list", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/search/comment/list")
	public PublicResult searchCommentResult(@RequestParam(value="artId",required = false) String artId,@RequestParam(value="artType",required = false) String artType,
			@RequestParam(value="pageNum",required = false) int pageNum,@RequestParam(value="pageSize",required = false) int pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Comment comment = new Comment();
			comment.setArtId(artId);
			comment.setArtType(artType);
			PageHelper.startPage(pageNum, pageSize);
			List<Comment> list = iCommentService.findList(comment);
			PageInfo<Comment> page = new PageInfo(list);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,
					e.getMessage());
		}

	}
}
