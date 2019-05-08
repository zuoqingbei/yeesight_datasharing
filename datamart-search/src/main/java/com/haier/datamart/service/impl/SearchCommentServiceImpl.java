package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.SearchComment;
import com.haier.datamart.mapper.SearchCommentMapper;
import com.haier.datamart.service.ISearchCommentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-27
 */
@Service
public class SearchCommentServiceImpl extends ServiceImpl<SearchCommentMapper, SearchComment> implements ISearchCommentService {
    @Autowired
    private SearchCommentMapper SearchCommentMapper;
	
	@Override
	public int  add(SearchComment comment) {
		
		return SearchCommentMapper.addComment(comment);
	}

	@Override
	public List<SearchComment> getlist(String indexid,String type) {
		SearchComment comment=new SearchComment();
		comment.setArtId(indexid);
		comment.setArtType(type);
	    List<SearchComment> comments=  SearchCommentMapper.getList(comment);
		for (SearchComment searchComment : comments) {
			if ("0".equals(searchComment.getParentId())) {
				searchComment.setParentName("");
			}else {
				SearchComment ccComment=new SearchComment();
				ccComment.setId(searchComment.getParentId());
				List<SearchComment> list=	SearchCommentMapper.getList(ccComment);
			   searchComment.setParentName(list.get(0).getName());
			}
		}
	    
	    return comments;
	}

	
	
}
