
package com.haier.openplatform.hac.resource.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.openplatform.hac.resource.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetResourcesByAppAndUser_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getResourcesByAppAndUser");
    private final static QName _GetResourcesByAppAndUserResponse_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getResourcesByAppAndUserResponse");
    private final static QName _GetGroupByUserAndAppId_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getGroupByUserAndAppId");
    private final static QName _GetGroupByUserAndAppIdResponse_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getGroupByUserAndAppIdResponse");
    private final static QName _GetTodoTask_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getTodoTask");
    private final static QName _GetTodoTaskResponse_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getTodoTaskResponse");
    private final static QName _GetAppMarketLeftTree_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getAppMarketLeftTree");
    private final static QName _GetAppMarketLeftTreeResponse_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getAppMarketLeftTreeResponse");
    private final static QName _GetResourceById_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getResourceById");
    private final static QName _GetResourceByIdResponse_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getResourceByIdResponse");
    private final static QName _GetAppMarketList_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getAppMarketList");
    private final static QName _GetAppMarketListResponse_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getAppMarketListResponse");
    private final static QName _GetResourceFileBytes_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getResourceFileBytes");
    private final static QName _GetResourceFileBytesResponse_QNAME = new QName("http://service.resource.hac.openplatform.haier.com/", "getResourceFileBytesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.openplatform.hac.resource.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TreeNode }
     * 
     */
    public TreeNode createTreeNode() {
        return new TreeNode();
    }

    /**
     * Create an instance of {@link TreeNode.Attributes }
     * 
     */
    public TreeNode.Attributes createTreeNodeAttributes() {
        return new TreeNode.Attributes();
    }

    /**
     * Create an instance of {@link GetResourcesByAppAndUser }
     * 
     */
    public GetResourcesByAppAndUser createGetResourcesByAppAndUser() {
        return new GetResourcesByAppAndUser();
    }

    /**
     * Create an instance of {@link GetResourcesByAppAndUserResponse }
     * 
     */
    public GetResourcesByAppAndUserResponse createGetResourcesByAppAndUserResponse() {
        return new GetResourcesByAppAndUserResponse();
    }

    /**
     * Create an instance of {@link GetGroupByUserAndAppId }
     * 
     */
    public GetGroupByUserAndAppId createGetGroupByUserAndAppId() {
        return new GetGroupByUserAndAppId();
    }

    /**
     * Create an instance of {@link GetGroupByUserAndAppIdResponse }
     * 
     */
    public GetGroupByUserAndAppIdResponse createGetGroupByUserAndAppIdResponse() {
        return new GetGroupByUserAndAppIdResponse();
    }

    /**
     * Create an instance of {@link GetTodoTask }
     * 
     */
    public GetTodoTask createGetTodoTask() {
        return new GetTodoTask();
    }

    /**
     * Create an instance of {@link GetTodoTaskResponse }
     * 
     */
    public GetTodoTaskResponse createGetTodoTaskResponse() {
        return new GetTodoTaskResponse();
    }

    /**
     * Create an instance of {@link GetAppMarketLeftTree }
     * 
     */
    public GetAppMarketLeftTree createGetAppMarketLeftTree() {
        return new GetAppMarketLeftTree();
    }

    /**
     * Create an instance of {@link GetAppMarketLeftTreeResponse }
     * 
     */
    public GetAppMarketLeftTreeResponse createGetAppMarketLeftTreeResponse() {
        return new GetAppMarketLeftTreeResponse();
    }

    /**
     * Create an instance of {@link GetResourceById }
     * 
     */
    public GetResourceById createGetResourceById() {
        return new GetResourceById();
    }

    /**
     * Create an instance of {@link GetResourceByIdResponse }
     * 
     */
    public GetResourceByIdResponse createGetResourceByIdResponse() {
        return new GetResourceByIdResponse();
    }

    /**
     * Create an instance of {@link GetAppMarketList }
     * 
     */
    public GetAppMarketList createGetAppMarketList() {
        return new GetAppMarketList();
    }

    /**
     * Create an instance of {@link GetAppMarketListResponse }
     * 
     */
    public GetAppMarketListResponse createGetAppMarketListResponse() {
        return new GetAppMarketListResponse();
    }

    /**
     * Create an instance of {@link GetResourceFileBytes }
     * 
     */
    public GetResourceFileBytes createGetResourceFileBytes() {
        return new GetResourceFileBytes();
    }

    /**
     * Create an instance of {@link GetResourceFileBytesResponse }
     * 
     */
    public GetResourceFileBytesResponse createGetResourceFileBytesResponse() {
        return new GetResourceFileBytesResponse();
    }

    /**
     * Create an instance of {@link UserSourceAndScode }
     * 
     */
    public UserSourceAndScode createUserSourceAndScode() {
        return new UserSourceAndScode();
    }

    /**
     * Create an instance of {@link HacResourceDTO }
     * 
     */
    public HacResourceDTO createHacResourceDTO() {
        return new HacResourceDTO();
    }

    /**
     * Create an instance of {@link HacBaseDTO }
     * 
     */
    public HacBaseDTO createHacBaseDTO() {
        return new HacBaseDTO();
    }

    /**
     * Create an instance of {@link HacGroupDTO }
     * 
     */
    public HacGroupDTO createHacGroupDTO() {
        return new HacGroupDTO();
    }

    /**
     * Create an instance of {@link HacResourceModelDTO }
     * 
     */
    public HacResourceModelDTO createHacResourceModelDTO() {
        return new HacResourceModelDTO();
    }

    /**
     * Create an instance of {@link AppMarketQuery }
     * 
     */
    public AppMarketQuery createAppMarketQuery() {
        return new AppMarketQuery();
    }

    /**
     * Create an instance of {@link BaseModel }
     * 
     */
    public BaseModel createBaseModel() {
        return new BaseModel();
    }

    /**
     * Create an instance of {@link AgentPager }
     * 
     */
    public AgentPager createAgentPager() {
        return new AgentPager();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link HacUserDTO }
     * 
     */
    public HacUserDTO createHacUserDTO() {
        return new HacUserDTO();
    }

    /**
     * Create an instance of {@link TreeNode.Attributes.Entry }
     * 
     */
    public TreeNode.Attributes.Entry createTreeNodeAttributesEntry() {
        return new TreeNode.Attributes.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourcesByAppAndUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getResourcesByAppAndUser")
    public JAXBElement<GetResourcesByAppAndUser> createGetResourcesByAppAndUser(GetResourcesByAppAndUser value) {
        return new JAXBElement<GetResourcesByAppAndUser>(_GetResourcesByAppAndUser_QNAME, GetResourcesByAppAndUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourcesByAppAndUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getResourcesByAppAndUserResponse")
    public JAXBElement<GetResourcesByAppAndUserResponse> createGetResourcesByAppAndUserResponse(GetResourcesByAppAndUserResponse value) {
        return new JAXBElement<GetResourcesByAppAndUserResponse>(_GetResourcesByAppAndUserResponse_QNAME, GetResourcesByAppAndUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGroupByUserAndAppId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getGroupByUserAndAppId")
    public JAXBElement<GetGroupByUserAndAppId> createGetGroupByUserAndAppId(GetGroupByUserAndAppId value) {
        return new JAXBElement<GetGroupByUserAndAppId>(_GetGroupByUserAndAppId_QNAME, GetGroupByUserAndAppId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGroupByUserAndAppIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getGroupByUserAndAppIdResponse")
    public JAXBElement<GetGroupByUserAndAppIdResponse> createGetGroupByUserAndAppIdResponse(GetGroupByUserAndAppIdResponse value) {
        return new JAXBElement<GetGroupByUserAndAppIdResponse>(_GetGroupByUserAndAppIdResponse_QNAME, GetGroupByUserAndAppIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTodoTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getTodoTask")
    public JAXBElement<GetTodoTask> createGetTodoTask(GetTodoTask value) {
        return new JAXBElement<GetTodoTask>(_GetTodoTask_QNAME, GetTodoTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTodoTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getTodoTaskResponse")
    public JAXBElement<GetTodoTaskResponse> createGetTodoTaskResponse(GetTodoTaskResponse value) {
        return new JAXBElement<GetTodoTaskResponse>(_GetTodoTaskResponse_QNAME, GetTodoTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAppMarketLeftTree }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getAppMarketLeftTree")
    public JAXBElement<GetAppMarketLeftTree> createGetAppMarketLeftTree(GetAppMarketLeftTree value) {
        return new JAXBElement<GetAppMarketLeftTree>(_GetAppMarketLeftTree_QNAME, GetAppMarketLeftTree.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAppMarketLeftTreeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getAppMarketLeftTreeResponse")
    public JAXBElement<GetAppMarketLeftTreeResponse> createGetAppMarketLeftTreeResponse(GetAppMarketLeftTreeResponse value) {
        return new JAXBElement<GetAppMarketLeftTreeResponse>(_GetAppMarketLeftTreeResponse_QNAME, GetAppMarketLeftTreeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getResourceById")
    public JAXBElement<GetResourceById> createGetResourceById(GetResourceById value) {
        return new JAXBElement<GetResourceById>(_GetResourceById_QNAME, GetResourceById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getResourceByIdResponse")
    public JAXBElement<GetResourceByIdResponse> createGetResourceByIdResponse(GetResourceByIdResponse value) {
        return new JAXBElement<GetResourceByIdResponse>(_GetResourceByIdResponse_QNAME, GetResourceByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAppMarketList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getAppMarketList")
    public JAXBElement<GetAppMarketList> createGetAppMarketList(GetAppMarketList value) {
        return new JAXBElement<GetAppMarketList>(_GetAppMarketList_QNAME, GetAppMarketList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAppMarketListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getAppMarketListResponse")
    public JAXBElement<GetAppMarketListResponse> createGetAppMarketListResponse(GetAppMarketListResponse value) {
        return new JAXBElement<GetAppMarketListResponse>(_GetAppMarketListResponse_QNAME, GetAppMarketListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceFileBytes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getResourceFileBytes")
    public JAXBElement<GetResourceFileBytes> createGetResourceFileBytes(GetResourceFileBytes value) {
        return new JAXBElement<GetResourceFileBytes>(_GetResourceFileBytes_QNAME, GetResourceFileBytes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResourceFileBytesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.resource.hac.openplatform.haier.com/", name = "getResourceFileBytesResponse")
    public JAXBElement<GetResourceFileBytesResponse> createGetResourceFileBytesResponse(GetResourceFileBytesResponse value) {
        return new JAXBElement<GetResourceFileBytesResponse>(_GetResourceFileBytesResponse_QNAME, GetResourceFileBytesResponse.class, null, value);
    }

}
