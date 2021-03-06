package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.EnteringTableSettingHeader;
import com.haier.datamart.service.IEnteringTableSettingHeaderService;
import com.haier.datamart.utils.ExcleUtils;
import com.haier.datamart.utils.ReadMergeRegionExcel;
import com.haier.datamart.utils.UUIDUtils;
/**
 *
 * @author zuoqb123
 * @date 2018-10-16
 * @todo 补录模块Excel头部信息路由
 */
@RestController
@RequestMapping("/api/enteringTableSettingHeader")
@Api(value = "补录模块Excel头部信息",description="补录模块Excel头部信息 @author zuoqb123")
public class EnteringTableSettingHeaderController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(EnteringTableSettingHeaderController.class);

    @Autowired
    public IEnteringTableSettingHeaderService iEnteringTableSettingHeaderService;

    /**
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   新增补录模块Excel头部信息
     */
  	@ApiOperation(value = "新增补录模块Excel头部信息", notes = "新增补录模块Excel头部信息", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<EnteringTableSettingHeader> add(HttpServletRequest request,EnteringTableSettingHeader entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iEnteringTableSettingHeaderService.insert(entity)){
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
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   删除补录模块Excel头部信息
     */
  	@ApiOperation(value = "删除补录模块Excel头部信息", notes = "删除补录模块Excel头部信息", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,produces = { "application/json;charset=UTF-8" })
	public PublicResult<EnteringTableSettingHeader> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			EnteringTableSettingHeader entity=new EnteringTableSettingHeader();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iEnteringTableSettingHeaderService.updateById(entity)){
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
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   更新补录模块Excel头部信息
     */
  	@ApiOperation(value = "更新补录模块Excel头部信息", notes = "更新补录模块Excel头部信息", httpMethod = "PUT")
	@RequestMapping(value = "/update", method = RequestMethod.PUT,produces = { "application/json;charset=UTF-8" })
	public PublicResult<EnteringTableSettingHeader> update(HttpServletRequest request,EnteringTableSettingHeader entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iEnteringTableSettingHeaderService.updateById(entity)){
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
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   查询单个补录模块Excel头部信息
     */
  	@ApiOperation(value = "查询单个补录模块Excel头部信息", notes = "查询单个补录模块Excel头部信息", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<EnteringTableSettingHeader> get(HttpServletRequest request,@PathVariable("id") String id) {
  		EnteringTableSettingHeader entity=null;
  		try {
  			EntityWrapper<EnteringTableSettingHeader> wrapper = new EntityWrapper<EnteringTableSettingHeader>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iEnteringTableSettingHeaderService.selectOne(wrapper);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
    /**
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   分页查询补录模块Excel头部信息
     */
  	@ApiOperation(value = "分页查询补录模块Excel头部信息", notes = "分页查询补录模块Excel头部信息", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public PublicResult<PageInfo<EnteringTableSettingHeader>> list(EnteringTableSettingHeader entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<EnteringTableSettingHeader> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			List<EnteringTableSettingHeader> list = iEnteringTableSettingHeaderService.selectList(wrapper);
			PageInfo<EnteringTableSettingHeader> page = new PageInfo<EnteringTableSettingHeader>(list);
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
    private EntityWrapper<EnteringTableSettingHeader> searchWrapper(HttpServletRequest request, EnteringTableSettingHeader entity) {
		EntityWrapper<EnteringTableSettingHeader> wrapper = new EntityWrapper<EnteringTableSettingHeader>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.and("create_by", getLoginUser(request).getId());
		}
				//根据编号模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据配置编码模糊查询
		if(entity.getEnteringSettingId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getEnteringSettingId()))){
			wrapper.like("entering_setting_id", String.valueOf(entity.getEnteringSettingId()));
		}
				//根据行号模糊查询
		if(entity.getRownum()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRownum()))){
			wrapper.like("rownum", String.valueOf(entity.getRownum()));
		}
				//根据haeder名称模糊查询
		if(entity.getName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getName()))){
			wrapper.like("name", String.valueOf(entity.getName()));
		}
				//根据haeder宽度模糊查询
		if(entity.getWidth()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getWidth()))){
			wrapper.like("width", String.valueOf(entity.getWidth()));
		}
				//根据haeder高度模糊查询
		if(entity.getHight()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getHight()))){
			wrapper.like("hight", String.valueOf(entity.getHight()));
		}
				//根据haeder样式模糊查询
		if(entity.getStyle()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getStyle()))){
			wrapper.like("style", String.valueOf(entity.getStyle()));
		}
				//根据行开始位置模糊查询
		if(entity.getRowFrom()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRowFrom()))){
			wrapper.like("row_from", String.valueOf(entity.getRowFrom()));
		}
				//根据列开始位置模糊查询
		if(entity.getColumnFrom()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getColumnFrom()))){
			wrapper.like("column_from", String.valueOf(entity.getColumnFrom()));
		}
				//根据行结束位置模糊查询
		if(entity.getRowTo()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRowTo()))){
			wrapper.like("row_to", String.valueOf(entity.getRowTo()));
		}
				//根据列结束位置模糊查询
		if(entity.getColumnTo()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getColumnTo()))){
			wrapper.like("column_to", String.valueOf(entity.getColumnTo()));
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
    
    
    
    
	/**
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   根据补录配置编码查询相应Excel头部信息
     */
  	@ApiOperation(value = "根据补录配置编码查询相应Excel头部信息", notes = "根据补录配置编码查询相应Excel头部信息", httpMethod = "GET")
  	@RequestMapping(value = "/getHeaderByEnteringId", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public PublicResult<List<EnteringTableSettingHeader>> getHeaderByEnteringId(@RequestParam(value="enteringSettingId",required = true) String enteringSettingId,HttpServletRequest request) {
		try {
			List<EnteringTableSettingHeader> list = iEnteringTableSettingHeaderService.getHeadersByEnteringId(enteringSettingId);
			return new PublicResult<>(PublicResultConstant.SUCCESS, list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
	/**
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   根据补录配置编码查询相应Excel头部信息
     */
  	@ApiOperation(value = "根据补录配置编码删除相应Excel头部信息", notes = "根据补录配置编码删除相应Excel头部信息", httpMethod = "PUT")
  	@RequestMapping(value = "/deleteHeaderByEnteringId", method = RequestMethod.PUT,produces = { "application/json;charset=UTF-8" })
    public PublicResult<List<EnteringTableSettingHeader>> deleteHeaderByEnteringId(@RequestParam(value="enteringSettingId",required = true) String enteringSettingId,HttpServletRequest request) {
		try {
			iEnteringTableSettingHeaderService.deleteHeadersByEnteringId(enteringSettingId);
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}
	}
  	
  	/**
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   根据补录配置编码查询相应Excel头部信息
     */
  	@ApiOperation(value = "根据补录配置编码删除相应Excel头部信息", notes = "根据补录配置编码删除相应Excel头部信息", httpMethod = "POST")
  	@RequestMapping(value = "/importHeaders", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
    public Object importHeaders(@RequestParam(value="enteringSettingId",required = true) String enteringSettingId,
    		HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		try {
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 验证文件名是否合格
			if (!ExcleUtils.validateExcel(fileName)) {
				// session.setAttribute("msg","文件必须是excel格式！");
				return new PublicResult<>(PublicResultConstant.ERROR,"请选择Excel文件", null);
			}
			// 进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
			long size = file.getSize();
			if (StringUtils.isEmpty(fileName) || size == 0) {
				return new PublicResult<>(PublicResultConstant.ERROR,"文件不能为空！", null);
			}
			InputStream is = null;
			//根据新建的文件实例化输入流
    	    is = file.getInputStream();
    	    //根据版本选择创建Workbook的方式
            Workbook wb = null;
            //根据文件名判断文件是2003版本还是2007版本
            if(ExcleUtils.isExcel2007(fileName)){
        	  wb = new XSSFWorkbook(is); 
            }else{
        	  wb = new HSSFWorkbook(is); 
            }
            List<EnteringTableSettingHeader>  headers=ReadMergeRegionExcel.readExcel(wb, 0, 0, 0);
            iEnteringTableSettingHeaderService.saveHeaders(headers, enteringSettingId);
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}
	}
}

