
package com.haier.openplatform.hac.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>searchMergeUser complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="searchMergeUser"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="arg0" type="{http://service.hac.openplatform.haier.com/}userMergeDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchMergeUser", propOrder = {
    "arg0"
})
public class SearchMergeUser {

    protected UserMergeDTO arg0;

    /**
     * 获取arg0属性的值。
     * 
     * @return
     *     possible object is
     *     {@link UserMergeDTO }
     *     
     */
    public UserMergeDTO getArg0() {
        return arg0;
    }

    /**
     * 设置arg0属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link UserMergeDTO }
     *     
     */
    public void setArg0(UserMergeDTO value) {
        this.arg0 = value;
    }

}
