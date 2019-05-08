package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageHelper;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.DataInterfaceExc;
import com.haier.datamart.entity.DataInterfaceTableRelative;
import com.haier.datamart.entity.DruidParseEntity;
import com.haier.datamart.service.IDataInterfaceExcService;
import com.haier.datamart.service.IDataInterfaceTableRelativeService;
import com.haier.datamart.utils.DruidParseUtils;
import com.haier.datamart.utils.UUIDUtils;
/**
 *
 * @author zuoqb123
 * @date 2018-12-07
 * @todo 统一接口中解析的sql中表关系路由
 */
@RestController
@RequestMapping("/api/dataInterfaceTableRelative")
@Api(value = "统一接口中解析的sql中表关系",description="统一接口中解析的sql中表关系 @author zuoqb123")
public class DataInterfaceTableRelativeController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(DataInterfaceTableRelativeController.class);

    @Autowired
    public IDataInterfaceTableRelativeService iDataInterfaceTableRelativeService;

    @Autowired
    public IDataInterfaceExcService iDataInterfaceExcService;
    
    /**
     * @date   2018-12-07
     * @author zuoqb123
     * @todo   新增统一接口中解析的sql中表关系
     */
  	@ApiOperation(value = "扫描统一接口中解析的sql中表关系", notes = "扫描统一接口中解析的sql中表关系", httpMethod = "POST")
	@RequestMapping(value = "/scanAll", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<DataInterfaceTableRelative> scanAll(HttpServletRequest request) {
		try {
			EntityWrapper<DataInterfaceExc> wrapper = new EntityWrapper<DataInterfaceExc>();
			wrapper.where("del_flag={0}", UN_DEL_FLAG);
			List<DataInterfaceExc> excList=iDataInterfaceExcService.selectList(wrapper);
			Date date=new Date();
			List<DataInterfaceTableRelative> insertList=new ArrayList<DataInterfaceTableRelative>();
			for(DataInterfaceExc exc:excList){
				try {
					if(StringUtils.isNotBlank(exc.getDataSql())){
						System.out.println("------------------------"+exc.getId());
						 DruidParseEntity parse=DruidParseUtils.parseSqlByDruid(exc.getDataSql());
						 List<DataInterfaceTableRelative> relatives=parse.getRelatives();
						 //删除之前
						 Map<String,Object> columnMap=new HashMap<String, Object>();
						 columnMap.put("exc_id", exc.getId());
						 iDataInterfaceTableRelativeService.deleteByMap(columnMap);
						 //新增
						 for(DataInterfaceTableRelative relative:relatives){
							 if(!"*".equals(relative.getContentDetail())){
								 relative.setDelFlag("0");
								 relative.setCreateDate(date);
								 relative.setDataSourceId(exc.getDataSourceId());
								 relative.setExcId(exc.getId()+"");
								 relative.setId(UUIDUtils.getUuid());
								 relative.setCreateBy(getLoginUser(request).getId());
								 insertList.add(relative);
							 }
						 }
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			if(insertList.size()>0){
				int pageSize=500;
				int pageNum=insertList.size()/pageSize;
				if(insertList.size()%pageSize>0){
					pageNum++;
				}
				for(int x=1;x<pageNum+1;x++){
					List<DataInterfaceTableRelative> sub=new ArrayList<DataInterfaceTableRelative>();
					if(x<pageNum){
						sub=insertList.subList((x-1)*pageSize, x*pageSize);
					}else{
						sub=insertList.subList((x-1)*pageSize,(x-1)*pageSize+insertList.size()%pageSize);
					}
					iDataInterfaceTableRelativeService.insertBatch(sub);
				}
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS,null);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
    

    /**
     * @date   2018-12-07
     * @author zuoqb123
     * @todo   新增统一接口中解析的sql中表关系
     */
  	@ApiOperation(value = "新增统一接口中解析的sql中表关系", notes = "新增统一接口中解析的sql中表关系", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<DataInterfaceTableRelative> add(HttpServletRequest request,@RequestBody DataInterfaceTableRelative entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iDataInterfaceTableRelativeService.insert(entity)){
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
     * @date   2018-12-07
     * @author zuoqb123
     * @todo   删除统一接口中解析的sql中表关系
     */
  	@ApiOperation(value = "删除统一接口中解析的sql中表关系", notes = "删除统一接口中解析的sql中表关系", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<DataInterfaceTableRelative> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			DataInterfaceTableRelative entity=new DataInterfaceTableRelative();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iDataInterfaceTableRelativeService.updateById(entity)){
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
     * @date   2018-12-07
     * @author zuoqb123
     * @todo   更新统一接口中解析的sql中表关系
     */
  	@ApiOperation(value = "更新统一接口中解析的sql中表关系", notes = "更新统一接口中解析的sql中表关系", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<DataInterfaceTableRelative> update(HttpServletRequest request,DataInterfaceTableRelative entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iDataInterfaceTableRelativeService.updateById(entity)){
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
     * @date   2018-12-07
     * @author zuoqb123
     * @todo   查询单个统一接口中解析的sql中表关系
     */
  	@ApiOperation(value = "查询单个统一接口中解析的sql中表关系", notes = "查询单个统一接口中解析的sql中表关系", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<DataInterfaceTableRelative> get(HttpServletRequest request,@PathVariable("id") String id) {
  		DataInterfaceTableRelative entity=null;
  		try {
  			EntityWrapper<DataInterfaceTableRelative> wrapper = new EntityWrapper<DataInterfaceTableRelative>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iDataInterfaceTableRelativeService.selectOne(wrapper);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2018-12-07
     * @author zuoqb123
     * @todo   分页查询统一接口中解析的sql中表关系
     */
  	@ApiOperation(value = "分页查询统一接口中解析的sql中表关系", notes = "分页查询统一接口中解析的sql中表关系", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(DataInterfaceTableRelative entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<DataInterfaceTableRelative> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<DataInterfaceTableRelative> page=new Page<DataInterfaceTableRelative>(cu, pageSize);
			page = iDataInterfaceTableRelativeService.selectPage(page,wrapper);
			page.setTotal(iDataInterfaceTableRelativeService.selectCount(wrapper));
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
    private EntityWrapper<DataInterfaceTableRelative> searchWrapper(HttpServletRequest request, DataInterfaceTableRelative entity) {
		EntityWrapper<DataInterfaceTableRelative> wrapper = new EntityWrapper<DataInterfaceTableRelative>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.eq("create_by", getLoginUser(request).getId());
		}
				//根据编号模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据接口编码模糊查询
		if(entity.getExcId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getExcId()))){
			wrapper.like("exc_id", String.valueOf(entity.getExcId()));
		}
				//根据表模糊查询
		if(entity.getContentId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getContentId()))){
			wrapper.like("content_id", String.valueOf(entity.getContentId()));
		}
				//根据字段模糊查询
		if(entity.getContentDetail()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getContentDetail()))){
			wrapper.like("content_detail", String.valueOf(entity.getContentDetail()));
		}
				//根据表所在数据源 展示关系不需要做控制 看明细如果没有相应权限需要申请模糊查询
		if(entity.getDataSourceId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDataSourceId()))){
			wrapper.like("data_source_id", String.valueOf(entity.getDataSourceId()));
		}
				//根据创建者模糊查询
		if(entity.getCreateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
				//根据创建时间模糊查询
		if(entity.getCreateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))){
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
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

