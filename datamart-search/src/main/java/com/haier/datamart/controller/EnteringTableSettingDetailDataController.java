package com.haier.datamart.controller;

import org.springframework.stereotype.Controller;
import com.haier.datamart.controller.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import com.haier.datamart.service.IEnteringTableSettingDetailDataService;
import com.haier.datamart.entity.EnteringTableSettingDetailData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.haier.datamart.utils.UUIDUtils;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.web.bind.annotation.RequestBody;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageHelper;
/**
 *
 * @author zuoqb123
 * @date 2018-12-11
 * @todo 补录模块-补录数据表配置字段明细数据来源配置信息路由
 */
@RestController
@RequestMapping("/api/enteringTableSettingDetailData")
@Api(value = "补录模块-补录数据表配置字段明细数据来源配置信息",description="补录模块-补录数据表配置字段明细数据来源配置信息 @author zuoqb123")
public class EnteringTableSettingDetailDataController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(EnteringTableSettingDetailDataController.class);

    @Autowired
    public IEnteringTableSettingDetailDataService iEnteringTableSettingDetailDataService;

    /**
     * @date   2018-12-11
     * @author zuoqb123
     * @todo   新增补录模块-补录数据表配置字段明细数据来源配置信息
     */
  	@ApiOperation(value = "新增补录模块-补录数据表配置字段明细数据来源配置信息", notes = "新增补录模块-补录数据表配置字段明细数据来源配置信息", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<EnteringTableSettingDetailData> add(HttpServletRequest request,@RequestBody EnteringTableSettingDetailData entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iEnteringTableSettingDetailDataService.insert(entity)){
					return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "新增主键必须为空!",null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
    
    /**
     * @date   2018-12-11
     * @author zuoqb123
     * @todo   删除补录模块-补录数据表配置字段明细数据来源配置信息
     */
  	@ApiOperation(value = "删除补录模块-补录数据表配置字段明细数据来源配置信息", notes = "删除补录模块-补录数据表配置字段明细数据来源配置信息", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<EnteringTableSettingDetailData> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			EnteringTableSettingDetailData entity=new EnteringTableSettingDetailData();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iEnteringTableSettingDetailDataService.updateById(entity)){
				 return new PublicResult<>(PublicResultConstant.SUCCESS, null);
			 }else{
				 return new PublicResult<>(PublicResultConstant.ERROR, null);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
	
	 /**
     * @date   2018-12-11
     * @author zuoqb123
     * @todo   更新补录模块-补录数据表配置字段明细数据来源配置信息
     */
  	@ApiOperation(value = "更新补录模块-补录数据表配置字段明细数据来源配置信息", notes = "更新补录模块-补录数据表配置字段明细数据来源配置信息", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<EnteringTableSettingDetailData> update(HttpServletRequest request,EnteringTableSettingDetailData entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iEnteringTableSettingDetailDataService.updateById(entity)){
					return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "修改主键不能为空!",null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
    
    
    /**
     * @date   2018-12-11
     * @author zuoqb123
     * @todo   查询单个补录模块-补录数据表配置字段明细数据来源配置信息
     */
  	@ApiOperation(value = "查询单个补录模块-补录数据表配置字段明细数据来源配置信息", notes = "查询单个补录模块-补录数据表配置字段明细数据来源配置信息", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<EnteringTableSettingDetailData> get(HttpServletRequest request,@PathVariable("id") String id) {
  		EnteringTableSettingDetailData entity=null;
  		try {
  			EntityWrapper<EnteringTableSettingDetailData> wrapper = new EntityWrapper<EnteringTableSettingDetailData>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iEnteringTableSettingDetailDataService.selectOne(wrapper);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2018-12-11
     * @author zuoqb123
     * @todo   分页查询补录模块-补录数据表配置字段明细数据来源配置信息
     */
  	@ApiOperation(value = "分页查询补录模块-补录数据表配置字段明细数据来源配置信息", notes = "分页查询补录模块-补录数据表配置字段明细数据来源配置信息", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(EnteringTableSettingDetailData entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<EnteringTableSettingDetailData> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<EnteringTableSettingDetailData> page=new Page<EnteringTableSettingDetailData>(cu, pageSize);
			page = iEnteringTableSettingDetailDataService.selectPage(page,wrapper);
			page.setTotal(iEnteringTableSettingDetailDataService.selectCount(wrapper));
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
    /**
     * @date   2018年9月25日 下午5:36:10
     * @author zuoqb123
     * @todo   构建查询条件-以后扩展
     */
    private EntityWrapper<EnteringTableSettingDetailData> searchWrapper(HttpServletRequest request, EnteringTableSettingDetailData entity) {
		EntityWrapper<EnteringTableSettingDetailData> wrapper = new EntityWrapper<EnteringTableSettingDetailData>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.eq("create_by", getLoginUser(request).getId());
		}
				//根据编号模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据配置编码模糊查询
		if(entity.getDetailId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDetailId()))){
			wrapper.like("detail_id", String.valueOf(entity.getDetailId()));
		}
				//根据sheet序号模糊查询
		if(entity.getSheetIndex()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getSheetIndex()))){
			wrapper.like("sheet_index", String.valueOf(entity.getSheetIndex()));
		}
				//根据数据范围  格式D5:F10模糊查询
		if(entity.getDataRange()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDataRange()))){
			wrapper.like("data_range", String.valueOf(entity.getDataRange()));
		}
				//根据排序模糊查询
		if(entity.getOrderNo()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getOrderNo()))){
			wrapper.like("order_no", String.valueOf(entity.getOrderNo()));
		}
				//根据创建时间模糊查询
		if(entity.getCreateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))){
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
				//根据创建者模糊查询
		if(entity.getCreateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
				//根据更新者模糊查询
		if(entity.getUpdateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))){
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
		}
				//根据更新时间模糊查询
		if(entity.getUpdateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))){
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
				//根据备注信息模糊查询
		if(entity.getRemarks()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))){
			wrapper.like("remarks", String.valueOf(entity.getRemarks()));
		}
				//根据删除标记模糊查询
		if(entity.getDelFlag()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))){
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
				wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}

