
package com.haier.openplatform.hac.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>hacUserDataPrivilegeRelDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="hacUserDataPrivilegeRelDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataPrivilege" type="{http://service.hac.openplatform.haier.com/}hacDataPrivilegeC" minOccurs="0"/&gt;
 *         &lt;element name="user" type="{http://service.hac.openplatform.haier.com/}hacUserC" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hacUserDataPrivilegeRelDTO", propOrder = {
    "dataPrivilege",
    "user"
})
public class HacUserDataPrivilegeRelDTO {

    protected HacDataPrivilegeC dataPrivilege;
    protected HacUserC user;

    /**
     * 获取dataPrivilege属性的值。
     * 
     * @return
     *     possible object is
     *     {@link HacDataPrivilegeC }
     *     
     */
    public HacDataPrivilegeC getDataPrivilege() {
        return dataPrivilege;
    }

    /**
     * 设置dataPrivilege属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link HacDataPrivilegeC }
     *     
     */
    public void setDataPrivilege(HacDataPrivilegeC value) {
        this.dataPrivilege = value;
    }

    /**
     * 获取user属性的值。
     * 
     * @return
     *     possible object is
     *     {@link HacUserC }
     *     
     */
    public HacUserC getUser() {
        return user;
    }

    /**
     * 设置user属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link HacUserC }
     *     
     */
    public void setUser(HacUserC value) {
        this.user = value;
    }

}
