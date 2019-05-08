package com.haier.datamart.controller;

import com.haier.datamart.annotation.Log;
import com.haier.datamart.exception.BusinessException;
import com.haier.datamart.utils.CornShowUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import com.haier.datamart.service.IAirflowKettleSupportService;
import com.haier.datamart.entity.AirflowKettleSupport;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.stringtemplate.v4.ST;

/**
 *
 * @author 刘志龙
 * @date 2018-11-13
 * @todo airflow_kettle脚本维护表路由
 */
@RestController
@RequestMapping("/api/airflowKettleSupport")
@Api(value = "airflow_kettle脚本维护表",description="airflow_kettle脚本维护表 @author 刘志龙")
public class AirflowKettleSupportController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(AirflowKettleSupportController.class);

    @Autowired
    public IAirflowKettleSupportService iAirflowKettleSupportService;

    /**
     * @date   2018-11-13
     * @author 刘志龙
     * @todo   新增airflow_kettle脚本维护表
     */
  	@ApiOperation(value = "新增airflow_kettle脚本维护表", notes = "新增airflow_kettle脚本维护表", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<AirflowKettleSupport> add(HttpServletRequest request,AirflowKettleSupport entity) {
		try {
				//新增
				entity.setCreateBy(getLoginUser(request).getId());
				if(iAirflowKettleSupportService.insert(entity)){
					return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
		} catch (BusinessException e) {
            logger.error(e.getMessage());
            return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
        } catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
    
    /**
     * @date   2018-11-13
     * @author 刘志龙
     * @todo   删除airflow_kettle脚本维护表
     */
  	@ApiOperation(value = "删除airflow_kettle脚本维护表", notes = "删除airflow_kettle脚本维护表", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,produces = { "application/json;charset=UTF-8" })
	public PublicResult<AirflowKettleSupport> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			AirflowKettleSupport entity=new AirflowKettleSupport();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iAirflowKettleSupportService.stopanddelete(entity)){
				 return new PublicResult<>(PublicResultConstant.SUCCESS, null);
			 }else{
				 return new PublicResult<>(PublicResultConstant.ERROR, null);
			 }
		} catch (BusinessException e) {
            logger.error(e.getMessage());
            return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
        }
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
	
	 /**
     * @date   2018-11-13
     * @author 刘志龙
     * @todo   更新airflow_kettle脚本维护表
     */
  	@ApiOperation(value = "更新airflow_kettle脚本维护表", notes = "更新airflow_kettle脚本维护表", httpMethod = "PUT")
	@RequestMapping(value = "/update", method = RequestMethod.PUT,produces = { "application/json;charset=UTF-8" })
	public PublicResult<AirflowKettleSupport> update(HttpServletRequest request,AirflowKettleSupport entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iAirflowKettleSupportService.updateById(entity)){
					return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "修改主键不能为空!",null);
			}
		} catch (BusinessException e) {
            logger.error(e.getMessage());
            return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
        }catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
    
    
    /**
     * @date   2018-11-13
     * @author 刘志龙
     * @todo   查询单个airflow_kettle脚本维护表
     */
  	@ApiOperation(value = "查询单个airflow_kettle脚本维护表", notes = "查询单个airflow_kettle脚本维护表", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<AirflowKettleSupport> get(HttpServletRequest request,@PathVariable("id") String id) {
  		AirflowKettleSupport entity=null;
  		try {
  			EntityWrapper<AirflowKettleSupport> wrapper = new EntityWrapper<AirflowKettleSupport>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iAirflowKettleSupportService.selectOne(wrapper);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		}catch (BusinessException e) {
            logger.error(e.getMessage());
            return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
        } catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2018-11-13
     * @author 刘志龙
     * @todo   分页查询airflow_kettle脚本维护表
     */
  	@ApiOperation(value = "分页查询airflow_kettle脚本维护表", notes = "分页查询airflow_kettle脚本维护表", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public PublicResult<PageInfo<AirflowKettleSupport>> list(AirflowKettleSupport entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<AirflowKettleSupport> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			List<AirflowKettleSupport> list = iAirflowKettleSupportService.selectList(wrapper);
			for(AirflowKettleSupport item:list){
				item.setDagRuns(iAirflowKettleSupportService.getRunningState(item.getId()));
			}
			PageInfo<AirflowKettleSupport> page = new PageInfo<AirflowKettleSupport>(list);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		}catch (BusinessException e) {
            logger.error(e.getMessage());
            return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
        } catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}

	}
    /**
     * @date   2018年9月25日 下午5:36:10
     * @author 刘志龙
     * @todo   构建查询条件-以后扩展
     */
    private EntityWrapper<AirflowKettleSupport> searchWrapper(HttpServletRequest request, AirflowKettleSupport entity) {
		EntityWrapper<AirflowKettleSupport> wrapper = new EntityWrapper<AirflowKettleSupport>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request)){
				wrapper.eq("create_by", getLoginUser(request).getId());
			}
		}
				//根据主键模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据调度名称 系统根据名称自动生成模糊查询
		if(entity.getDagName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDagName()))){
			wrapper.like("dag_name", String.valueOf(entity.getDagName()));
		}
				//根据名称模糊查询
		if(entity.getBashCommand()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getBashCommand()))){
			wrapper.like("bash_command", String.valueOf(entity.getBashCommand()));
		}
				//根据调度时间模糊查询
		if(entity.getSchedule()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getSchedule()))){
			wrapper.like("schedule", String.valueOf(entity.getSchedule()));
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
		if(entity.getServiceBussiness()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getServiceBussiness()))){
			wrapper.like("service_bussiness", String.valueOf(entity.getServiceBussiness()));
		}
				//根据模糊查询
		if(entity.getDelFlag()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))){
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
				//根据所属系统模糊查询
		if(entity.getSystem()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getSystem()))){
			wrapper.like("system", String.valueOf(entity.getSystem()));
		}
				//根据需要用户手录模糊查询
		if(entity.getJobName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getJobName()))){
			wrapper.like("job_name", String.valueOf(entity.getJobName()));
		}
				wrapper.orderBy("create_date", false);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
	/**
	 * @date   2018-11-13
	 * @author 刘志龙
	 * @todo   运行一个调度airflow_kettle脚本维护表
	 */
	@ApiOperation(value = "运行一个调度", notes = "运行一个调度", httpMethod = "POST")
	@RequestMapping(value = "/run/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<AirflowKettleSupport> run(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			iAirflowKettleSupportService.run(id);
			//运行一个调度
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (BusinessException e) {
            logger.error(e.getMessage());
            return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
        } catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
	/**
	 * @date   2018-11-13
	 * @author 刘志龙
	 * @todo   触发一个调度airflow_kettle脚本维护表
	 */
	@ApiOperation(value = "触发一个调度", notes = "触发一个调度", httpMethod = "POST")
	@RequestMapping(value = "/fire/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<AirflowKettleSupport> fire(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			iAirflowKettleSupportService.fire(id);
			//运行一个调度
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
	/**
	 * @date   2018-11-13
	 * @author 刘志龙
	 * @todo   运行一个调度airflow_kettle脚本维护表
	 */
	@ApiOperation(value = "暂停一个调度", notes = "暂停一个调度", httpMethod = "POST")
	@RequestMapping(value = "/pause/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<AirflowKettleSupport> pause(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			//运行一个调度
			iAirflowKettleSupportService.pause(id);
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (BusinessException e) {
            logger.error(e.getMessage());
            return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
        }  catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}

	/**
	 * @date   2018-11-13
	 * @author 刘志龙
	 * @todo   运行一个调度airflow_kettle脚本维护表
	 */
	@ApiOperation(value = "测试一条bash命令", notes = "测试一条bash命令", httpMethod = "POST")
	@Log(description = "API接口:/api/airflowKettleSupport/testbash")
	@RequestMapping(value = "/testbash", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<String> testbash(HttpServletRequest request,AirflowKettleSupport entity) {
		try {
			//运行一个调度
//			iAirflowKettleSupportService.pause(id);
			return new PublicResult<>(PublicResultConstant.SUCCESS, this.iAirflowKettleSupportService.testBash(getLoginUser(request).getId(),entity.getJobName(),entity.getBashCommand()));
		}catch (BusinessException e) {
            logger.error(e.getMessage());
            return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
        } catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
	/**
	 * @date   2018-11-13
	 * @author 刘志龙
	 * @todo   运行一个调度airflow_kettle脚本维护表
	 */
	@ApiOperation(value = "获取最近几次的执行时间", notes = "获取最近几次的执行时间", httpMethod = "POST")
	@RequestMapping(value = "/showcron", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<List> showcron(HttpServletRequest request,AirflowKettleSupport entity) {
		try {

			//运行一个调度
			return new PublicResult<>(PublicResultConstant.SUCCESS, iAirflowKettleSupportService.getRecentFires(entity));
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
	/**
	 * @date   2018-11-13
	 * @author 刘志龙
	 */
	@ApiOperation(value = "获取调度的执行情况", notes = "获取调度的执行情况", httpMethod = "GET")
	@RequestMapping(value = "/runningState/{id}", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
	public PublicResult runningState(@PathVariable("id") String id) {
		try {
			return new PublicResult<>(PublicResultConstant.SUCCESS, iAirflowKettleSupportService.getRunningState(id));
		}catch (BusinessException e) {
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}

	}
	/**
	 * @date   2018-11-13
	 * @author 刘志龙
	 */
	@ApiOperation(value = "获取调度的执行情况的列表", notes = "获取调度的执行情况的列表", httpMethod = "GET")
	@RequestMapping(value = "/runningStateList", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
	public PublicResult runningStateList(String id, String state, @RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
										 @RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize)  {
		try {
			return new PublicResult<>(PublicResultConstant.SUCCESS, iAirflowKettleSupportService.getRunningStateList(id,state,pageNum,pageSize));
		}catch (BusinessException e) {
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
	/**
	 * @date   2018-11-13
	 * @author 刘志龙
	 */
	@ApiOperation(value = "获取调度执行的日志", notes = "获取调度执行的日志", httpMethod = "GET")
	@RequestMapping(value = "/runningStateLog", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
	public PublicResult runningStateLog(String id, String execDate)  {
		try {
			return new PublicResult<>(PublicResultConstant.SUCCESS, iAirflowKettleSupportService.getLogFileContent(id,execDate));
		}catch (BusinessException e) {
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED.getResult(),e.getMessage(), null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
}

