
package com.haier.openplatform.hac.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>hacUserC complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="hacUserC"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://service.hac.openplatform.haier.com/}cacheEntry"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="businessPosition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="businessPositionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="currentLoginIp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="departments" type="{http://service.hac.openplatform.haier.com/}hacDepartmentC" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="encode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="expiredTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="haierDataSrc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haierOrgCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haierOrgziCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haierPersk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haierUserFirstLineID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hrPosition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hrpositionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lastLoginIp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lastLoginTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="loginAttemptTimes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="loginFaildTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nickName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="passwordExpireTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="passwordModifiedFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "hacUserC", propOrder = {
    "businessPosition",
    "businessPositionCode",
    "currentLoginIp",
    "departments",
    "email",
    "encode",
    "expiredTime",
    "haierDataSrc",
    "haierOrgCode",
    "haierOrgziCode",
    "haierPersk",
    "haierUserFirstLineID",
    "hrPosition",
    "hrpositionCode",
    "lastLoginIp",
    "lastLoginTime",
    "loginAttemptTimes",
    "loginFaildTime",
    "name",
    "nickName",
    "passwordExpireTime",
    "passwordModifiedFlag",
    "phone",
    "status",
    "type",
    "userOU",
    "userType"
})
public class HacUserC
    extends CacheEntry
{

    protected String businessPosition;
    protected String businessPositionCode;
    protected String currentLoginIp;
    @XmlElement(nillable = true)
    protected List<HacDepartmentC> departments;
    protected String email;
    protected String encode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expiredTime;
    protected String haierDataSrc;
    protected String haierOrgCode;
    protected String haierOrgziCode;
    protected String haierPersk;
    protected String haierUserFirstLineID;
    protected String hrPosition;
    protected String hrpositionCode;
    protected String lastLoginIp;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastLoginTime;
    protected Integer loginAttemptTimes;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar loginFaildTime;
    protected String name;
    protected String nickName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar passwordExpireTime;
    protected Integer passwordModifiedFlag;
    protected String phone;
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
     * Gets the value of the departments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the departments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDepartments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HacDepartmentC }
     * 
     * 
     */
    public List<HacDepartmentC> getDepartments() {
        if (departments == null) {
            departments = new ArrayList<HacDepartmentC>();
        }
        return this.departments;
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
