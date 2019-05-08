
package com.haier.openplatform.hac.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>searchUserByDatasResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="searchUserByDatasResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://service.hac.openplatform.haier.com/}sucOrErrDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchUserByDatasResponse", propOrder = {
    "_return"
})
public class SearchUserByDatasResponse {

    @XmlElement(name = "return")
    protected SucOrErrDTO _return;

    /**
     * 获取return属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SucOrErrDTO }
     *     
     */
    public SucOrErrDTO getReturn() {
        return _return;
    }

    /**
     * 设置return属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SucOrErrDTO }
     *     
     */
    public void setReturn(SucOrErrDTO value) {
        this._return = value;
    }

}
