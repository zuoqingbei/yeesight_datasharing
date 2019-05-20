package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SearchIndex;
import com.haier.datamart.entity.SearchIndexHistory;
import com.haier.datamart.entity.SearchIndexMagicRevise;
import com.haier.datamart.mapper.SearchIndexHistoryMapper;
import com.haier.datamart.mapper.SearchIndexMapper;
import com.haier.datamart.service.ISearchIndexMagicReviseService;
import com.haier.datamart.service.ISearchIndexService;
import com.haier.datamart.utils.GenerationSequenceUtil;
import com.haier.datamart.utils.UUIDUtils;
/**
 *
 * @author zuoqb123
 * @date 2018-10-30
 * @todo 业务用户填写的指标业务逻辑订正表 需要审核路由
 */
@RestController
@RequestMapping("/api/searchIndexMagicRevise")
@Api(value = "业务用户填写的指标业务逻辑订正表 需要审核",description="业务用户填写的指标业务逻辑订正表 需要审核 @author zuoqb123")
public class SearchIndexMagicReviseController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(SearchIndexMagicReviseController.class);

    @Autowired
    public ISearchIndexMagicReviseService iSearchIndexMagicReviseService;
    
    @Autowired
	private ISearchIndexService isearchindexservice;
    
    @Autowired
	private SearchIndexMapper searchindexmapper;
    @Autowired
	private SearchIndexHistoryMapper historyMapper;

    /**
     * @date   2018-10-30
     * @author zuoqb123
     * @todo   新增业务用户填写的指标业务逻辑订正表 需要审核
     */
  	@ApiOperation(value = "新增业务用户填写的指标业务逻辑订正表 需要审核", notes = "新增业务用户填写的指标业务逻辑订正表 需要审核", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object add(HttpServletRequest request,SearchIndexMagicRevise entity) {
		try {
			if(StringUtils.isBlank(getLoginUser(request).getId())){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				entity.setShStatus("0");
				if(iSearchIndexMagicReviseService.insert(entity)){
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
     * @date   2018-10-30
     * @author zuoqb123
     * @todo   删除业务用户填写的指标业务逻辑订正表 需要审核
     */
  	@ApiOperation(value = "删除业务用户填写的指标业务逻辑订正表 需要审核", notes = "删除业务用户填写的指标业务逻辑订正表 需要审核", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public Object delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			if(StringUtils.isBlank(getLoginUser(request).getId())){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}
			SearchIndexMagicRevise entity=new SearchIndexMagicRevise();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iSearchIndexMagicReviseService.updateById(entity)){
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
     * @date   2018-10-30
     * @author zuoqb123
     * @todo   更新业务用户填写的指标业务逻辑订正表 需要审核
     */
  	@ApiOperation(value = "更新业务用户填写的指标业务逻辑订正表 需要审核", notes = "更新业务用户填写的指标业务逻辑订正表 需要审核", httpMethod = "PUT")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Object update(HttpServletRequest request,SearchIndexMagicRevise entity) {
		try {
			if(StringUtils.isBlank(getLoginUser(request).getId())){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iSearchIndexMagicReviseService.updateById(entity)){
					return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, "操作失败!");
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
     * @date   2018-10-30
     * @author zuoqb123
     * @todo   查询单个业务用户填写的指标业务逻辑订正表 需要审核
     */
  	@ApiOperation(value = "查询单个业务用户填写的指标业务逻辑订正表 需要审核", notes = "查询单个业务用户填写的指标业务逻辑订正表 需要审核", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET )
  	public Object get(HttpServletRequest request,@PathVariable("id") String id) {
  		SearchIndexMagicRevise entity=null;
  		try {
  			if(StringUtils.isBlank(getLoginUser(request).getId())){
  				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
  			}
  			EntityWrapper<SearchIndexMagicRevise> wrapper = new EntityWrapper<SearchIndexMagicRevise>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iSearchIndexMagicReviseService.selectOne(wrapper);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
  	
  	
  	 /**
     * @date   2018-10-30
     * @author zuoqb123
     * @todo   审核业务用户填写的指标业务逻辑订正表
     */
  	@ApiOperation(value = "审核业务用户填写的指标业务逻辑订正表", notes = "审核业务用户填写的指标业务逻辑订正表", httpMethod = "PUT")
	@RequestMapping(value = "/audit", method = RequestMethod.PUT)
	public Object audit(HttpServletRequest request,String id, boolean isPass,
			@RequestParam(value="remarks",required = false)  String remarks) {
  		try {
  			if(StringUtils.isBlank(getLoginUser(request).getId())){
  				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
  			}
  			if(StringUtils.isNotBlank(id)){
  				EntityWrapper<SearchIndexMagicRevise> wrapper = new EntityWrapper<SearchIndexMagicRevise>();
  	  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  	  			wrapper.eq("id", id);
  	  			SearchIndexMagicRevise entity=iSearchIndexMagicReviseService.selectOne(wrapper);
  				entity.setUpdateDate(new Date());
  				entity.setUpdateBy(getLoginUser(request).getId());
  				entity.setShUser(getLoginUser(request).getId());
  				entity.setRemarks(remarks);
  				if(isPass){
  					entity.setShStatus("1");
  				}else{
  					entity.setShStatus("2");
  				}
  				if(iSearchIndexMagicReviseService.updateById(entity)){
  					if(isPass){
  						//更新指标业务逻辑到指标表 并添加历史
  						SearchIndex index=searchindexmapper.get(entity.getIndexId());
  						if(index!=null){
  							// 新增指标历史表
  							JSONObject object = JSONObject.fromObject(index);
  							SearchIndexHistory history = new SearchIndexHistory();
  							history.setId(GenerationSequenceUtil.getUUID());
  							history.setIndexId(index.getId());
  							history.setJsonData(object.toString());
  							history.setCreateDate(new Date());
  							history.setCreateBy(getLoginUser(request).getId());
  							history.setUpdateDate(new Date());
  							history.setUpdateBy(getLoginUser(request).getId());
  							if(historyMapper.add(history)>0){
  								index.setGetDataMagic(entity.getMagicInfo());
  								index.setUpdateBy(getLoginUser(request).getId());
  								index.setUpdateDate(new Date());
  								if(searchindexmapper.updateById(index)>0){
  									return new PublicResult<>(PublicResultConstant.SUCCESS, null);
  								}else{
  									return new PublicResult<>(PublicResultConstant.ERROR, "指标信息更新失败！");
  								}
  								
  							}else{
  								return new PublicResult<>(PublicResultConstant.ERROR, "指标历史信息备份失败！");
  							}
  						}else{
  							return new PublicResult<>(PublicResultConstant.ERROR, "暂无该指标数据！");
  						}
  					}
  				}else{
  					return new PublicResult<>(PublicResultConstant.ERROR, null);
  				}
  			}else{
  				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "审核主键不能为空!");
  			}
  			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
  	
  	
  	
    /**
     * @date   2018-10-30
     * @author zuoqb123
     * @todo   分页查询业务用户填写的指标业务逻辑订正表 需要审核
     */
  	@ApiOperation(value = "分页查询业务用户填写的指标业务逻辑订正表 需要审核", notes = "分页查询业务用户填写的指标业务逻辑订正表 需要审核", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(SearchIndexMagicRevise entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			if(StringUtils.isBlank(getLoginUser(request).getId())){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}
			EntityWrapper<SearchIndexMagicRevise> wrapper = searchWrapper(request, entity);
			com.github.pagehelper.PageHelper.startPage(pageNum, pageSize);
			List<SearchIndexMagicRevise> list = iSearchIndexMagicReviseService.selectList(wrapper);
			PageInfo<SearchIndexMagicRevise> page = new PageInfo(list);
			for(SearchIndexMagicRevise r:list){
				SearchIndex i=searchindexmapper.get(r.getIndexId());
				if(i!=null){
					r.setIndexCode(i.getCode());
					r.setIndexName(i.getName());
				}
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, list);
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
    private EntityWrapper<SearchIndexMagicRevise> searchWrapper(HttpServletRequest request, SearchIndexMagicRevise entity) {
		EntityWrapper<SearchIndexMagicRevise> wrapper = new EntityWrapper<SearchIndexMagicRevise>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.and("create_by", getLoginUser(request).getId());
		}
				//根据主键模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据指标编码模糊查询
		if(entity.getIndexId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getIndexId()))){
			wrapper.like("index_id", String.valueOf(entity.getIndexId()));
		}
				//根据之前的指标业务逻辑模糊查询
		if(entity.getPreMagicInfo()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getPreMagicInfo()))){
			wrapper.like("pre_magic_info", String.valueOf(entity.getPreMagicInfo()));
		}
				//根据指标业务逻辑模糊查询
		if(entity.getMagicInfo()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMagicInfo()))){
			wrapper.like("magic_info", String.valueOf(entity.getMagicInfo()));
		}
				//根据审核状态 0-未审核  1-审核通过 2-驳回模糊查询
		if(entity.getShStatus()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getShStatus()))){
			wrapper.like("sh_status", String.valueOf(entity.getShStatus()));
		}
				//根据审核人模糊查询
		if(entity.getShUser()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getShUser()))){
			wrapper.like("sh_user", String.valueOf(entity.getShUser()));
		}
				//根据创建人模糊查询
		if(entity.getCreateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
				//根据创建时间模糊查询
		if(entity.getCreateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))){
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
				//根据更新人模糊查询
		if(entity.getUpdateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))){
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
		}
				//根据更新时间模糊查询
		if(entity.getUpdateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))){
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
				//根据备注模糊查询
		if(entity.getRemarks()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))){
			wrapper.like("remarks", String.valueOf(entity.getRemarks()));
		}
				//根据模糊查询
		if(entity.getDelFlag()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))){
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
				wrapper.orderBy("create_date", false);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}

