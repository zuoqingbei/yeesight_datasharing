
package com.haier.openplatform.hac.resource.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>baseModel complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="baseModel"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pager" type="{http://service.resource.hac.openplatform.haier.com/}agentPager" minOccurs="0"/&gt;
 *         &lt;element name="user" type="{http://service.resource.hac.openplatform.haier.com/}hacUserDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseModel", propOrder = {
    "pager",
    "user"
})
@XmlSeeAlso({
    AppMarketQuery.class
})
public class BaseModel {

    protected AgentPager pager;
    protected HacUserDTO user;

    /**
     * 获取pager属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AgentPager }
     *     
     */
    public AgentPager getPager() {
        return pager;
    }

    /**
     * 设置pager属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AgentPager }
     *     
     */
    public void setPager(AgentPager value) {
        this.pager = value;
    }

    /**
     * 获取user属性的值。
     * 
     * @return
     *     possible object is
     *     {@link HacUserDTO }
     *     
     */
    public HacUserDTO getUser() {
        return user;
    }

    /**
     * 设置user属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link HacUserDTO }
     *     
     */
    public void setUser(HacUserDTO value) {
        this.user = value;
    }

}
