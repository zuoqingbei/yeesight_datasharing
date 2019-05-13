package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.haier.openplatform.hac.resource.service.HacResourceDTO;
import com.haier.openplatform.hac.service.HacDataPrivilegeDTO;
import com.haier.openplatform.hac.service.UserMergeDTO;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
@TableName("sys_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;
    @TableField(exist=false)
    private List<HacResourceDTO> resourceDTOList;
    @TableField(exist=false)
    private List<HacDataPrivilegeDTO> privilegeDTOlist;
    @TableField(exist=false)
    private UserMergeDTO userMerge;
	/**
     * 编号
     */
    private String id;
    /**
     * token
     */
    @TableField(exist=false)
    private String token;
    /**
     * 登录名
     */
    @TableField("login_name")
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 工号
     */
    private String no;
    /**
     * 姓名
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 用户类型
     */
    @TableField("user_type")
    private String userType;
    /**
     * 用户头像
     */
    private String photo;
    /**
     * 最后登陆IP
     */
    @TableField("login_ip")
    private String loginIp;
    /**
     * 最后登陆时间
     */
    @TableField("login_date")
    private Date loginDate;
    /**
     * 是否可登录
     */
    @TableField("login_flag")
    private String loginFlag;
    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 更新者
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField("update_date")
    private Date updateDate;
    /**
     * 备注信息
     */
    private String remarks;
    /**
     * 删除标记
     */
    @TableField("del_flag")
    private String delFlag;
    @TableField(exist=false)
    private String salt; //加密密码盐
    @TableField(exist=false)
    private List<Group> groups;
    @TableField(exist=false)
    private List<Role> roles;
    @TableField(exist=false)
    private List<Menu> menus;
    //列策略
    @TableField(exist=false)
    private List<AdminColumnRuleStrategy> ruleStrategy;
    //行策略
    @TableField(exist=false)
    private List<AdminDataStrategy> dataStrategies;
    //开放策略
    @TableField(exist=false)
    private List<AdminContentOpenStrategy> openStrategies;
    @TableField(exist=false)
    private List<String> dataSourceConfigId=new ArrayList<String>();
    @TableField(exist=false)
    private boolean hasEntering;//是否拥有补录权限
    @TableField(exist=false)
    private boolean hasAdmin;//是否拥有后台管理权限
    @TableField(exist=false)
    private List<SearchIndex> indexList=new ArrayList<SearchIndex>();//用户可以查看的指标
    public List<SearchIndex> getIndexList() {
		return indexList;
	}

	public void setIndexList(List<SearchIndex> indexList) {
		this.indexList = indexList;
	}

	public boolean isHasEntering() {
		return hasEntering;
	}

	public void setHasEntering(boolean hasEntering) {
		this.hasEntering = hasEntering;
	}

	public boolean isHasAdmin() {
		return hasAdmin;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setHasAdmin(boolean hasAdmin) {
		this.hasAdmin = hasAdmin;
	}

	public List<String> getDataSourceConfigId() {
		return dataSourceConfigId;
	}

	public void setDataSourceConfigId(List<String> dataSourceConfigId) {
		this.dataSourceConfigId = dataSourceConfigId;
	}

	public List<AdminColumnRuleStrategy> getRuleStrategy() {
		return ruleStrategy;
	}

	public void setRuleStrategy(List<AdminColumnRuleStrategy> ruleStrategy) {
		this.ruleStrategy = ruleStrategy;
	}

	public List<AdminDataStrategy> getDataStrategies() {
		return dataStrategies;
	}

	public void setDataStrategies(List<AdminDataStrategy> dataStrategies) {
		this.dataStrategies = dataStrategies;
	}

	public List<AdminContentOpenStrategy> getOpenStrategies() {
		return openStrategies;
	}

	public void setOpenStrategies(List<AdminContentOpenStrategy> openStrategies) {
		this.openStrategies = openStrategies;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
       return this.loginName+this.salt;
    }
	public UserMergeDTO getUserMerge() {
		return userMerge;
	}

	public void setUserMerge(UserMergeDTO userMerge) {
		this.userMerge = userMerge;
	}

	public List<HacDataPrivilegeDTO> getPrivilegeDTOlist() {
		return privilegeDTOlist;
	}
	
	public void setPrivilegeDTOlist(List<HacDataPrivilegeDTO> privilegeDTOlist) {
		this.privilegeDTOlist = privilegeDTOlist;
	}

	public List<HacResourceDTO> getResourceDTOList() {
		return resourceDTOList;
	}

	public void setResourceDTOList(List<HacResourceDTO> resourceDTOList) {
		this.resourceDTOList = resourceDTOList;
	}
    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", loginName=" + loginName +
        ", password=" + password +
        ", no=" + no +
        ", name=" + name +
        ", email=" + email +
        ", phone=" + phone +
        ", mobile=" + mobile +
        ", userType=" + userType +
        ", photo=" + photo +
        ", loginIp=" + loginIp +
        ", loginDate=" + loginDate +
        ", loginFlag=" + loginFlag +
        ", createBy=" + createBy +
        ", createDate=" + createDate +
        ", updateBy=" + updateBy +
        ", updateDate=" + updateDate +
        ", remarks=" + remarks +
        ", delFlag=" + delFlag +
        "}";
    }
}
