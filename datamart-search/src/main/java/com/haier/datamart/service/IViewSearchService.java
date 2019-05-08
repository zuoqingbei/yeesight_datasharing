package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.ViewSearch;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-24
 */
public interface IViewSearchService extends IService<ViewSearch> {

	public List<ViewSearch> getBykeyword(ViewSearch view);
	public List<ViewSearch> getAll(ViewSearch view);
	public ViewSearch getByPK(ViewSearch view);
	
	List<ViewSearch> gettuijian(String type);
	List<ViewSearch> getalltuijian();
	ViewSearch getCount(ViewSearch view);
	int changeVZSNum(String type,String id,String numType);
	List<ViewSearch> getAdd();
	List<ViewSearch> getByKeywordList(ViewSearch view);
}


