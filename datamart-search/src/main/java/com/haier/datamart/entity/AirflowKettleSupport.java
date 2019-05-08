package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * airflow_kettle脚本维护表
 * @author 刘志龙123
 * @date 2018-11-13
 */
@TableName("airflow_kettle_support")
public class AirflowKettleSupport extends Model<AirflowKettleSupport> {

    private static final long serialVersionUID = 1L;

   @TableField(exist = false)
    private Map dagRuns;

   public Map getDagRuns() {
      return dagRuns;
   }

   public void setDagRuns(Map dagRuns) {
      this.dagRuns = dagRuns;
   }

   /**
     * 主键
     */
   private String id;
    /**
     * 调度名称 系统根据名称自动生成
     */
   @TableField("dag_name")
   private String dagName;
    /**
     * 名称
     */
   @TableField("bash_command")
   private String bashCommand;
    /**
     * 调度时间
     */
   private String schedule;
    /**
     * 创建人
     */
   @TableField("create_by")
   private String createBy;
    /**
     * 创建时间
     */
   @TableField("create_date")
   private Date createDate;
    /**
     * 更新人
     */
   @TableField("update_by")
   private String updateBy;
    /**
     * 更新时间
     */
   @TableField("update_date")
   private Date updateDate;
    /**
     * 备注
     */
   private String remarks;
   @TableField("service_bussiness")
   private String serviceBussiness;
   @TableField("del_flag")
   private String delFlag;
    /**
     * 所属系统
     */
    @TableField("system")
    private String system;
    /**
     * 需要用户手录
     */
   @TableField("job_name")
   private String jobName;

   public String getRunningState() {
      return runningState;
   }

   public void setRunningState(String runningState) {
      this.runningState = runningState;
   }

   /**
    * 运行状态
    */
   @TableField("running_state")
   private String runningState;
   /**
    * airflow就绪状态
    */
   @TableField("remote_ready")
   private String remoteReady;
   /**
    * 调度开始时间
    */

   @TableField("start_date")
   private String startDate;

   public String getStartDate() {
      return startDate;
   }

   public void setStartDate(String startDate) {
      this.startDate = startDate;
   }

   public String getRemoteReady() {
      return remoteReady;
   }

   public void setRemoteReady(String remoteReady) {
      this.remoteReady = remoteReady;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getDagName() {
      return dagName;
   }

   public void setDagName(String dagName) {
      this.dagName = dagName;
   }

   public String getBashCommand() {
      return bashCommand;
   }

   public void setBashCommand(String bashCommand) {
      this.bashCommand = bashCommand;
   }

   public String getSchedule() {
      return schedule;
   }

   public void setSchedule(String schedule) {
      this.schedule = schedule;
   }

   public String getCreateBy() {
      return createBy;
   }

   public void setCreateBy(String createBy) {
      this.createBy = createBy;
   }

   public Date getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Date createDate) {
      this.createDate = createDate;
   }

   public String getUpdateBy() {
      return updateBy;
   }

   public void setUpdateBy(String updateBy) {
      this.updateBy = updateBy;
   }

   public Date getUpdateDate() {
      return updateDate;
   }

   public void setUpdateDate(Date updateDate) {
      this.updateDate = updateDate;
   }

   public String getRemarks() {
      return remarks;
   }

   public void setRemarks(String remarks) {
      this.remarks = remarks;
   }

   public String getServiceBussiness() {
      return serviceBussiness;
   }

   public void setServiceBussiness(String serviceBussiness) {
      this.serviceBussiness = serviceBussiness;
   }

   public String getDelFlag() {
      return delFlag;
   }

   public void setDelFlag(String delFlag) {
      this.delFlag = delFlag;
   }

   public String getSystem() {
      return system;
   }

   public void setSystem(String system) {
      this.system = system;
   }

   public String getJobName() {
      return jobName;
   }

   public void setJobName(String jobName) {
      this.jobName = jobName;
   }

   @Override
   protected Serializable pkVal() {
      return this.id;
   }

   @Override
   public String toString() {
      return "AirflowKettleSupport{" +
         "id=" + id +
         ", dagName=" + dagName +
         ", bashCommand=" + bashCommand +
         ", schedule=" + schedule +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", serviceBussiness=" + serviceBussiness +
         ", delFlag=" + delFlag +
         ", system=" + system +
         ", jobName=" + jobName +
         "}";
   }
}
