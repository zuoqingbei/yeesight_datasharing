
package com.haier.openplatform.hac.resource.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>hacUserDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="hacUserDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://service.resource.hac.openplatform.haier.com/}hacBaseDTO"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="businessPosition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="businessPositionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="currentLoginIp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataPrivilegeId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="encode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="expiredTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="groupId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="hacGroups" type="{http://service.resource.hac.openplatform.haier.com/}hacGroupDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="haierDataSrc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haierInternetParentUserID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haierOrgCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haierOrgziCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haierPersk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haierUserDeptCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haierUserDeptName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haierUserFirstLineID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hrPosition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hrpositionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idCard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idmOu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lastLoginIp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lastLoginTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="lastModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="loginAttemptTimes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="loginFaildTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nickName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="passwordExpireTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="passwordModifiedFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="registSrc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="userOU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="userType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hacUserDTO", propOrder = {
    "businessPosition",
    "businessPositionCode",
    "currentLoginIp",
    "dataPrivilegeId",
    "email",
    "encode",
    "expiredTime",
    "groupId",
    "hacGroups",
    "haierDataSrc",
    "haierInternetParentUserID",
    "haierOrgCode",
    "haierOrgziCode",
    "haierPersk",
    "haierUserDeptCode",
    "haierUserDeptName",
    "haierUserFirstLineID",
    "hrPosition",
    "hrpositionCode",
    "idCard",
    "idmOu",
    "lastLoginIp",
    "lastLoginTime",
    "lastModifiedBy",
    "loginAttemptTimes",
    "loginFaildTime",
    "name",
    "nickName",
    "password",
    "passwordExpireTime",
    "passwordModifiedFlag",
    "phone",
    "registSrc",
    "source",
    "status",
    "type",
    "userOU",
    "userType"
})
public class HacUserDTO
    extends HacBaseDTO
{

    protected String businessPosition;
    protected String businessPositionCode;
    protected String currentLoginIp;
    protected Long dataPrivilegeId;
    protected String email;
    protected String encode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expiredTime;
    protected long groupId;
    @XmlElement(nillable = true)
    protected List<HacGroupDTO> hacGroups;
    protected String haierDataSrc;
    protected String haierInternetParentUserID;
    protected String haierOrgCode;
    protected String haierOrgziCode;
    protected String haierPersk;
    protected String haierUserDeptCode;
    protected String haierUserDeptName;
    protected String haierUserFirstLineID;
    protected String hrPosition;
    protected String hrpositionCode;
    protected String idCard;
    protected String idmOu;
    protected String lastLoginIp;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastLoginTime;
    protected String lastModifiedBy;
    protected Integer loginAttemptTimes;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar loginFaildTime;
    protected String name;
    protected String nickName;
    protected String password;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar passwordExpireTime;
    protected Integer passwordModifiedFlag;
    protected String phone;
    protected String registSrc;
    protected String source;
    protected Integer status;
    protected Integer type;
    protected String userOU;
    protected String userType;

    /**
     * 获取businessPosition属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessPosition() {
        return businessPosition;
    }

    /**
     * 设置businessPosition属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessPosition(String value) {
        this.businessPosition = value;
    }

    /**
     * 获取businessPositionCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessPositionCode() {
        return businessPositionCode;
    }

    /**
     * 设置businessPositionCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessPositionCode(String value) {
        this.businessPositionCode = value;
    }

    /**
     * 获取currentLoginIp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentLoginIp() {
        return currentLoginIp;
    }

    /**
     * 设置currentLoginIp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentLoginIp(String value) {
        this.currentLoginIp = value;
    }

    /**
     * 获取dataPrivilegeId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDataPrivilegeId() {
        return dataPrivilegeId;
    }

    /**
     * 设置dataPrivilegeId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDataPrivilegeId(Long value) {
        this.dataPrivilegeId = value;
    }

    /**
     * 获取email属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置email属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * 获取encode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncode() {
        return encode;
    }

    /**
     * 设置encode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncode(String value) {
        this.encode = value;
    }

    /**
     * 获取expiredTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpiredTime() {
        return expiredTime;
    }

    /**
     * 设置expiredTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpiredTime(XMLGregorianCalendar value) {
        this.expiredTime = value;
    }

    /**
     * 获取groupId属性的值。
     * 
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * 设置groupId属性的值。
     * 
     */
    public void setGroupId(long value) {
        this.groupId = value;
    }

    /**
     * Gets the value of the hacGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hacGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHacGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HacGroupDTO }
     * 
     * 
     */
    public List<HacGroupDTO> getHacGroups() {
        if (hacGroups == null) {
            hacGroups = new ArrayList<HacGroupDTO>();
        }
        return this.hacGroups;
    }

    /**
     * 获取haierDataSrc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaierDataSrc() {
        return haierDataSrc;
    }

    /**
     * 设置haierDataSrc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaierDataSrc(String value) {
        this.haierDataSrc = value;
    }

    /**
     * 获取haierInternetParentUserID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaierInternetParentUserID() {
        return haierInternetParentUserID;
    }

    /**
     * 设置haierInternetParentUserID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaierInternetParentUserID(String value) {
        this.haierInternetParentUserID = value;
    }

    /**
     * 获取haierOrgCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaierOrgCode() {
        return haierOrgCode;
    }

    /**
     * 设置haierOrgCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaierOrgCode(String value) {
        this.haierOrgCode = value;
    }

    /**
     * 获取haierOrgziCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaierOrgziCode() {
        return haierOrgziCode;
    }

    /**
     * 设置haierOrgziCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaierOrgziCode(String value) {
        this.haierOrgziCode = value;
    }

    /**
     * 获取haierPersk属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaierPersk() {
        return haierPersk;
    }

    /**
     * 设置haierPersk属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaierPersk(String value) {
        this.haierPersk = value;
    }

    /**
     * 获取haierUserDeptCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaierUserDeptCode() {
        return haierUserDeptCode;
    }

    /**
     * 设置haierUserDeptCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaierUserDeptCode(String value) {
        this.haierUserDeptCode = value;
    }

    /**
     * 获取haierUserDeptName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaierUserDeptName() {
        return haierUserDeptName;
    }

    /**
     * 设置haierUserDeptName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaierUserDeptName(String value) {
        this.haierUserDeptName = value;
    }

    /**
     * 获取haierUserFirstLineID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaierUserFirstLineID() {
        return haierUserFirstLineID;
    }

    /**
     * 设置haierUserFirstLineID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaierUserFirstLineID(String value) {
        this.haierUserFirstLineID = value;
    }

    /**
     * 获取hrPosition属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHrPosition() {
        return hrPosition;
    }

    /**
     * 设置hrPosition属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHrPosition(String value) {
        this.hrPosition = value;
    }

    /**
     * 获取hrpositionCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHrpositionCode() {
        return hrpositionCode;
    }

    /**
     * 设置hrpositionCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHrpositionCode(String value) {
        this.hrpositionCode = value;
    }

    /**
     * 获取idCard属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置idCard属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCard(String value) {
        this.idCard = value;
    }

    /**
     * 获取idmOu属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdmOu() {
        return idmOu;
    }

    /**
     * 设置idmOu属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdmOu(String value) {
        this.idmOu = value;
    }

    /**
     * 获取lastLoginIp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置lastLoginIp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastLoginIp(String value) {
        this.lastLoginIp = value;
    }

    /**
     * 获取lastLoginTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置lastLoginTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastLoginTime(XMLGregorianCalendar value) {
        this.lastLoginTime = value;
    }

    /**
     * 获取lastModifiedBy属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * 设置lastModifiedBy属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastModifiedBy(String value) {
        this.lastModifiedBy = value;
    }

    /**
     * 获取loginAttemptTimes属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLoginAttemptTimes() {
        return loginAttemptTimes;
    }

    /**
     * 设置loginAttemptTimes属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLoginAttemptTimes(Integer value) {
        this.loginAttemptTimes = value;
    }

    /**
     * 获取loginFaildTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoginFaildTime() {
        return loginFaildTime;
    }

    /**
     * 设置loginFaildTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoginFaildTime(XMLGregorianCalendar value) {
        this.loginFaildTime = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取nickName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置nickName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickName(String value) {
        this.nickName = value;
    }

    /**
     * 获取password属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置password属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * 获取passwordExpireTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPasswordExpireTime() {
        return passwordExpireTime;
    }

    /**
     * 设置passwordExpireTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPasswordExpireTime(XMLGregorianCalendar value) {
        this.passwordExpireTime = value;
    }

    /**
     * 获取passwordModifiedFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPasswordModifiedFlag() {
        return passwordModifiedFlag;
    }

    /**
     * 设置passwordModifiedFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPasswordModifiedFlag(Integer value) {
        this.passwordModifiedFlag = value;
    }

    /**
     * 获取phone属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置phone属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * 获取registSrc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistSrc() {
        return registSrc;
    }

    /**
     * 设置registSrc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistSrc(String value) {
        this.registSrc = value;
    }

    /**
     * 获取source属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置source属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * 获取status属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置status属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    /**
     * 获取type属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置type属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setType(Integer value) {
        this.type = value;
    }

    /**
     * 获取userOU属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserOU() {
        return userOU;
    }

    /**
     * 设置userOU属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserOU(String value) {
        this.userOU = value;
    }

    /**
     * 获取userType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 设置userType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserType(String value) {
        this.userType = value;
    }

}
