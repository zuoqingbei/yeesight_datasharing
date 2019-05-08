package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 用户-组
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-08-03
 */
@TableName("sys_user_group")
public class SysUserGroup extends Model<SysUserGroup> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private String userId;
    /**
     * 用户组编号
     */
    @TableField("group_id")
    private String groupId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
    public SysUserGroup() {
		super();
	}

    public SysUserGroup(String userId, String groupId) {
		super();
		this.userId = userId;
		this.groupId = groupId;
	}

	@Override
    public String toString() {
        return "SysUserGroup{" +
        "userId=" + userId +
        ", groupId=" + groupId +
        "}";
    }
}
