package com.haier.datamart.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SearchComment;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.ISearchCommentService;
import com.haier.datamart.utils.GenerationSequenceUtil;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-27
 */
@RestController
public class SearchCommentController extends BaseController{
	
	@Autowired
	private ISearchCommentService commentService;
	
	/**
	 * 评论新增
	 * @author lixiaoyi
	 * @date 2018年6月27日 下午2:15:12
	 * @TODO
	 */
	@GetMapping(value = "/comment/add", produces = { "application/json;charset=UTF-8" })
	@Log(description="API接口:/comment/add")
	public Object addComment(HttpServletRequest request,SearchComment comment){
		try {
			User user=  getLoginUser(request);  
			int re =0;
			if (comment.getParentId()!=null&&!"".equals(comment.getParentId())) {
				if (!comment.getCreateBy().equals(user.getId())) {
			 comment.setId(GenerationSequenceUtil.getUUID());
			 comment.setCreateBy(user.getId());
			 comment.setUpdateBy(user.getId());
			 comment.setCreateDate(new Date());
			 comment.setUpdateDate(new Date());
		 re =	  commentService.add(comment);
				}
				else {
					return new PublicResult<>(PublicResultConstant.ERROR, "本人不能回复自己");
				}	
			}else {
				 comment.setId(GenerationSequenceUtil.getUUID());
				 comment.setCreateBy(user.getId());
				 comment.setUpdateBy(user.getId());
				 comment.setCreateDate(new Date());
				 comment.setUpdateDate(new Date());
			 re =	  commentService.add(comment);
			}
		if (re<=0) {
			return new PublicResult<>(PublicResultConstant.ERROR, null);
		}else {
			return new PublicResult<>(PublicResultConstant.SUCCESS, re);	
		}
	
		} catch (Exception e) {
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		
	}

	/**
	 * 评论列表
	 * @author lixiaoyi
	 * @date 2018年6月27日 下午3:21:15
	 * @TODO
	 */
	@GetMapping(value = "/comment/list", produces = { "application/json;charset=UTF-8" })
	@Log(description="API接口:/comment/list")
	public Object list(HttpServletRequest request){
		try {
		     
		  List<SearchComment> comments= commentService.getlist(request.getParameter("indexid"),request.getParameter("type"));
		  return new PublicResult<>(PublicResultConstant.SUCCESS, comments);	
		} catch (Exception e) {
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		
	}
}

