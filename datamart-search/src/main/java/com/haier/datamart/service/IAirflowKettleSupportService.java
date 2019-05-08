package com.haier.datamart.service;

import com.haier.datamart.entity.AirflowKettleSupport;
import com.baomidou.mybatisplus.service.IService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


/**
 * airflow_kettle脚本维护表服务类
 * @author 刘志龙123
 * @date 2018-11-13
 */
public interface IAirflowKettleSupportService extends IService<AirflowKettleSupport> {
 	public void run(String id);
    public void pause(String id);
    public String testBash(String execUser,String jobName,String bashCommand);
    public boolean stopanddelete(AirflowKettleSupport entity);
    public List<String> getRecentFires(AirflowKettleSupport entity);
    public Map<String,Integer> getRunningState(String id);//根据ID 获取调度的运行时间
    public List<Map<String,Object>> getRunningStateList(String id,String state);//根据ID 和状态 获取调度的执行记录
    public Map<String, Object> getRunningStateList(String id, String state,  Integer pageNum, Integer pageSize);//根据ID 和状态 获取调度的执行记录 分页
    public String getLogFileContent(String id,String execDate);//根据执行时间和ID 获取执行的log
    public void fire(String id);

}
