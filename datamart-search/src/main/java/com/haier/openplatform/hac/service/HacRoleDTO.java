
package com.haier.openplatform.hac.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>hacRoleDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="hacRoleDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://service.hac.openplatform.haier.com/}hacBaseDTO"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="expression" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="groupId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="resources" type="{http://service.hac.openplatform.haier.com/}hacResourceDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="resourcesId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="users" type="{http://service.hac.openplatform.haier.com/}hacUserDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hacRoleDTO", propOrder = {
    "description",
    "expression",
    "groupId",
    "name",
    "resources",
    "resourcesId",
    "users"
})
public class HacRoleDTO
    extends HacBaseDTO
{

    protected String description;
    protected String expression;
    protected long groupId;
    protected String name;
    @XmlElement(nillable = true)
    protected List<HacResourceDTO> resources;
    protected String resourcesId;
    @XmlElement(nillable = true)
    protected List<HacUserDTO> users;

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * 获取expression属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpression() {
        return expression;
    }

    /**
     * 设置expression属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpression(String value) {
        this.expression = value;
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
     * Gets the value of the resources property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resources property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResources().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HacResourceDTO }
     * 
     * 
     */
    public List<HacResourceDTO> getResources() {
        if (resources == null) {
            resources = new ArrayList<HacResourceDTO>();
        }
        return this.resources;
    }

    /**
     * 获取resourcesId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourcesId() {
        return resourcesId;
    }

    /**
     * 设置resourcesId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourcesId(String value) {
        this.resourcesId = value;
    }

    /**
     * Gets the value of the users property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the users property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HacUserDTO }
     * 
     * 
     */
    public List<HacUserDTO> getUsers() {
        if (users == null) {
            users = new ArrayList<HacUserDTO>();
        }
        return this.users;
    }

}
