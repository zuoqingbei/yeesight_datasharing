package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 补录模块-补录数据操作历史
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-07-17
 */

@TableName("entering_logs")
public class EnteringLogs extends Model<EnteringLogs> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 总条数
     */
    @TableField("all_num")
    private String allNum;
    /**
     * 成功条数
     */
    @TableField("success_num")
    private String successNum;
    /**
     * 失败条数
     */
    @TableField("fail_num")
    private String failNum;
    /**
     * 失败数据
     */
    @TableField("fail_data")
    private String failData;
    /**
     * 导入的产业
     */
    @TableField("industry_id")
    private String industryId;
    /**
     * 导入的指标
     */
    @TableField("setting_id")
    private String settingId;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private String createDate;
    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 更新者
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField("update_date")
    private String updateDate;
    /**
     * 备注信息
     */
    private String remarks;
    /**
     * 删除标记
     */
    @TableField("del_flag")
    private String delFlag;

    
    public EnteringLogs() {
		super();
	}


	@Override
    protected Serializable pkVal() {
        return this.id;
    }


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAllNum() {
		return allNum;
	}


	public void setAllNum(String allNum) {
		this.allNum = allNum;
	}


	public String getSuccessNum() {
		return successNum;
	}


	public void setSuccessNum(String successNum) {
		this.successNum = successNum;
	}


	public String getFailNum() {
		return failNum;
	}


	public void setFailNum(String failNum) {
		this.failNum = failNum;
	}


	public String getFailData() {
		return failData;
	}


	public void setFailData(String failData) {
		this.failData = failData;
	}


	public String getIndustryId() {
		return industryId;
	}


	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}



	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public String getCreateBy() {
		return createBy;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public String getUpdateBy() {
		return updateBy;
	}


	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}


	public String getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public EnteringLogs(String id, String allNum, String successNum, String failNum, String failData, String industryId,
			String settingId, String createDate, String createBy, String updateBy, String updateDate, String remarks,
			String delFlag) {
		super();
		this.id = id;
		this.allNum = allNum;
		this.successNum = successNum;
		this.failNum = failNum;
		this.failData = failData;
		this.industryId = industryId;
		this.settingId = settingId;
		this.createDate = createDate;
		this.createBy = createBy;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.remarks = remarks;
		this.delFlag = delFlag;
	}


	@Override
	public String toString() {
		return "EnteringLogs [id=" + id + ", allNum=" + allNum + ", successNum=" + successNum + ", failNum=" + failNum
				+ ", failData=" + failData + ", industryId=" + industryId + ", settingId=" + settingId + ", createDate="
				+ createDate + ", createBy=" + createBy + ", updateBy=" + updateBy + ", updateDate=" + updateDate
				+ ", remarks=" + remarks + ", delFlag=" + delFlag + "]";
	}


	public String getSettingId() {
		return settingId;
	}


	public void setSettingId(String settingId) {
		this.settingId = settingId;
	}



	
	
    
}
