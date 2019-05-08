package com.haier.datamart.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.DataInterfaceExc;
import com.baomidou.mybatisplus.service.IService;


/**
 * 服务类
 * @author zuoqb123
 * @date 2018-12-06
 */
public interface IDataInterfaceExcService extends IService<DataInterfaceExc> {
	/**
	 * 
	 * @time   2018年12月7日 下午7:36:30
	 * @author zuoqb
	 * @todo   根据数据源+表查询接口
	 */
	List<DataInterfaceExc> findExcByDbAndContentId(@Param("dataSourceId")String dataSourceId,
			@Param("contentId")String contentId,@Param("start")int start,@Param("end")int end,
			@Param("userId")String userId,
			@Param("isAdmin")String isAdmin);
	/**
	 * 
	 * @time   2018年12月7日 下午7:36:30
	 * @author zuoqb
	 * @todo   根据指标编码查询接口
	 */
	List<DataInterfaceExc> findExcByIndexId(@Param("indexId")String indexId,@Param("start")int start,
			@Param("end")int end,
			@Param("userId")String userId,
			@Param("isAdmin")String isAdmin);
	/**
	 * 
	 * @time   2018年12月7日 下午7:36:30
	 * @author zuoqb
	 * @todo   根据报表编码查询接口
	 */
	List<DataInterfaceExc> findExcByReportId(@Param("reportId")String reportId,@Param("start")int start,
			@Param("end")int end,
			@Param("userId")String userId,
			@Param("isAdmin")String isAdmin);
}
