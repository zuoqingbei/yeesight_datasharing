package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.SearchComment;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-27
 */
public interface ISearchCommentService extends IService<SearchComment> {
    int  add(SearchComment comment);
    List<SearchComment> getlist(String indexid,String type);
}
