
package com.haier.openplatform.hac.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>hacUserDataPrivilegeByAppDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="hacUserDataPrivilegeByAppDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://service.hac.openplatform.haier.com/}hacBaseDTO"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="hacDataPrivilegeDTOs" type="{http://service.hac.openplatform.haier.com/}hacDataPrivilegeDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="hacUserDto" type="{http://service.hac.openplatform.haier.com/}hacUserDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hacUserDataPrivilegeByAppDTO", propOrder = {
    "hacDataPrivilegeDTOs",
    "hacUserDto"
})
public class HacUserDataPrivilegeByAppDTO
    extends HacBaseDTO
{

    @XmlElement(nillable = true)
    protected List<HacDataPrivilegeDTO> hacDataPrivilegeDTOs;
    protected HacUserDTO hacUserDto;

    /**
     * Gets the value of the hacDataPrivilegeDTOs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hacDataPrivilegeDTOs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHacDataPrivilegeDTOs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HacDataPrivilegeDTO }
     * 
     * 
     */
    public List<HacDataPrivilegeDTO> getHacDataPrivilegeDTOs() {
        if (hacDataPrivilegeDTOs == null) {
            hacDataPrivilegeDTOs = new ArrayList<HacDataPrivilegeDTO>();
        }
        return this.hacDataPrivilegeDTOs;
    }

    /**
     * 获取hacUserDto属性的值。
     * 
     * @return
     *     possible object is
     *     {@link HacUserDTO }
     *     
     */
    public HacUserDTO getHacUserDto() {
        return hacUserDto;
    }

    /**
     * 设置hacUserDto属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link HacUserDTO }
     *     
     */
    public void setHacUserDto(HacUserDTO value) {
        this.hacUserDto = value;
    }

}
