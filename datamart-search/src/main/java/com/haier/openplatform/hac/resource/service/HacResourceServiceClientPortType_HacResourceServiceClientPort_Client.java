
package com.haier.openplatform.hac.resource.service;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.0.16
 * 2018-09-17T15:41:09.978+08:00
 * Generated source version: 3.0.16
 * 
 */
public final class HacResourceServiceClientPortType_HacResourceServiceClientPort_Client {

    private static final QName SERVICE_NAME = new QName("http://service.resource.hac.openplatform.haier.com/", "HacResourceServiceClient");

    private HacResourceServiceClientPortType_HacResourceServiceClientPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = HacResourceServiceClient.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        HacResourceServiceClient ss = new HacResourceServiceClient(wsdlURL, SERVICE_NAME);
        HacResourceServiceClientPortType port = ss.getHacResourceServiceClientPort();  
        
        {
        System.out.println("Invoking getResourceById...");
        java.lang.String _getResourceById_arg0 = "";
        java.lang.Long _getResourceById_arg1 = null;
        java.lang.String _getResourceById_arg2 = "";
        com.haier.openplatform.hac.resource.service.HacResourceDTO _getResourceById__return = port.getResourceById(_getResourceById_arg0, _getResourceById_arg1, _getResourceById_arg2);
        System.out.println("getResourceById.result=" + _getResourceById__return);


        }
        {
        System.out.println("Invoking getAppMarketList...");
        com.haier.openplatform.hac.resource.service.AppMarketQuery _getAppMarketList_arg0 = null;
        com.haier.openplatform.hac.resource.service.Pager _getAppMarketList__return = port.getAppMarketList(_getAppMarketList_arg0);
        System.out.println("getAppMarketList.result=" + _getAppMarketList__return);


        }
        {
        System.out.println("Invoking getGroupByUserAndAppId...");
        java.lang.String _getGroupByUserAndAppId_arg0 = "";
        java.lang.Long _getGroupByUserAndAppId_arg1 = null;
        java.lang.String _getGroupByUserAndAppId_arg2 = "";
        java.lang.String _getGroupByUserAndAppId_arg3 = "";
        java.util.List<com.haier.openplatform.hac.resource.service.HacGroupDTO> _getGroupByUserAndAppId__return = port.getGroupByUserAndAppId(_getGroupByUserAndAppId_arg0, _getGroupByUserAndAppId_arg1, _getGroupByUserAndAppId_arg2, _getGroupByUserAndAppId_arg3);
        System.out.println("getGroupByUserAndAppId.result=" + _getGroupByUserAndAppId__return);


        }
        {
        System.out.println("Invoking getAppMarketLeftTree...");
        java.lang.String _getAppMarketLeftTree_arg0 = "";
        java.lang.String _getAppMarketLeftTree_arg1 = "";
        java.lang.String _getAppMarketLeftTree_arg2 = "";
        java.lang.Long _getAppMarketLeftTree_arg3 = null;
        java.lang.String _getAppMarketLeftTree_arg4 = "";
        java.util.List<com.haier.openplatform.hac.resource.service.TreeNode> _getAppMarketLeftTree__return = port.getAppMarketLeftTree(_getAppMarketLeftTree_arg0, _getAppMarketLeftTree_arg1, _getAppMarketLeftTree_arg2, _getAppMarketLeftTree_arg3, _getAppMarketLeftTree_arg4);
        System.out.println("getAppMarketLeftTree.result=" + _getAppMarketLeftTree__return);


        }
        {
        System.out.println("Invoking getTodoTask...");
        java.lang.String _getTodoTask_arg0 = "";
        java.lang.String _getTodoTask_arg1 = "";
        java.lang.String _getTodoTask_arg2 = "";
        java.lang.String _getTodoTask_arg3 = "";
        java.util.List<com.haier.openplatform.hac.resource.service.HacResourceModelDTO> _getTodoTask__return = port.getTodoTask(_getTodoTask_arg0, _getTodoTask_arg1, _getTodoTask_arg2, _getTodoTask_arg3);
        System.out.println("getTodoTask.result=" + _getTodoTask__return);


        }
        {
        System.out.println("Invoking getResourceFileBytes...");
        java.lang.String _getResourceFileBytes_arg0 = "";
        java.lang.Long _getResourceFileBytes_arg1 = null;
        byte[] _getResourceFileBytes__return = port.getResourceFileBytes(_getResourceFileBytes_arg0, _getResourceFileBytes_arg1);
        System.out.println("getResourceFileBytes.result=" + _getResourceFileBytes__return);


        }
        {
        System.out.println("Invoking getResourcesByAppAndUser...");
        java.lang.String _getResourcesByAppAndUser_arg0 = "";
        java.lang.String _getResourcesByAppAndUser_arg1 = "";
        java.lang.String _getResourcesByAppAndUser_arg2 = "";
        java.lang.String _getResourcesByAppAndUser_arg3 = "";
        com.haier.openplatform.hac.resource.service.UserSourceAndScode _getResourcesByAppAndUser_arg4 = null;
        java.util.List<com.haier.openplatform.hac.resource.service.HacResourceDTO> _getResourcesByAppAndUser__return = port.getResourcesByAppAndUser(_getResourcesByAppAndUser_arg0, _getResourcesByAppAndUser_arg1, _getResourcesByAppAndUser_arg2, _getResourcesByAppAndUser_arg3, _getResourcesByAppAndUser_arg4);
        System.out.println("getResourcesByAppAndUser.result=" + _getResourcesByAppAndUser__return);


        }

        System.exit(0);
    }

}
