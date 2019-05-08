
package com.haier.openplatform.hac.resource.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>agentPager complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="agentPager"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://service.resource.hac.openplatform.haier.com/}pager"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pageNo" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agentPager", propOrder = {
    "pageNo"
})
public class AgentPager
    extends Pager
{

    protected Long pageNo;

    /**
     * 获取pageNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPageNo() {
        return pageNo;
    }

    /**
     * 设置pageNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPageNo(Long value) {
        this.pageNo = value;
    }

}
