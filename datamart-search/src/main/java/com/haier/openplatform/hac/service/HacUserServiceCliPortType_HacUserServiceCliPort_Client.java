
package com.haier.openplatform.hac.service;

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
 * 2018-09-17T16:01:11.196+08:00
 * Generated source version: 3.0.16
 * 
 */
public final class HacUserServiceCliPortType_HacUserServiceCliPort_Client {

    private static final QName SERVICE_NAME = new QName("http://service.hac.openplatform.haier.com/", "HacUserServiceCli");

    private HacUserServiceCliPortType_HacUserServiceCliPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = HacUserServiceCli.WSDL_LOCATION;
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
      
        HacUserServiceCli ss = new HacUserServiceCli(wsdlURL, SERVICE_NAME);
        HacUserServiceCliPortType port = ss.getHacUserServiceCliPort();  
        
        {
        System.out.println("Invoking searchUserByData...");
        java.lang.String _searchUserByData_arg0 = "";
        java.lang.String _searchUserByData_arg1 = "";
        java.lang.String _searchUserByData_arg2 = "";
        java.lang.String _searchUserByData_arg3 = "";
        java.util.List<com.haier.openplatform.hac.service.HacAllInfoDTO> _searchUserByData_arg4 = null;
        com.haier.openplatform.hac.service.ExecuteResult _searchUserByData__return = port.searchUserByData(_searchUserByData_arg0, _searchUserByData_arg1, _searchUserByData_arg2, _searchUserByData_arg3, _searchUserByData_arg4);
        System.out.println("searchUserByData.result=" + _searchUserByData__return);


        }
        {
        System.out.println("Invoking searchMergeUsers...");
        com.haier.openplatform.hac.service.UserMergeDTO _searchMergeUsers_arg0 = null;
        com.haier.openplatform.hac.service.ExecuteResult _searchMergeUsers__return = port.searchMergeUsers(_searchMergeUsers_arg0);
        System.out.println("searchMergeUsers.result=" + _searchMergeUsers__return);


        }
        {
        System.out.println("Invoking deleteUserAuth...");
        com.haier.openplatform.hac.service.HacUserCodeDTO _deleteUserAuth_arg0 = null;
        java.lang.String _deleteUserAuth__return = port.deleteUserAuth(_deleteUserAuth_arg0);
        System.out.println("deleteUserAuth.result=" + _deleteUserAuth__return);


        }
        {
        System.out.println("Invoking searchUserByGroupIdOrGroupName...");
        com.haier.openplatform.hac.service.HacGroupDTO _searchUserByGroupIdOrGroupName_arg0 = null;
        com.haier.openplatform.hac.service.ExecuteResult _searchUserByGroupIdOrGroupName__return = port.searchUserByGroupIdOrGroupName(_searchUserByGroupIdOrGroupName_arg0);
        System.out.println("searchUserByGroupIdOrGroupName.result=" + _searchUserByGroupIdOrGroupName__return);


        }
        {
        System.out.println("Invoking searchHacUserLoginLog...");
        java.lang.String _searchHacUserLoginLog_arg0 = "";
        com.haier.openplatform.hac.service.ExecuteResult _searchHacUserLoginLog__return = port.searchHacUserLoginLog(_searchHacUserLoginLog_arg0);
        System.out.println("searchHacUserLoginLog.result=" + _searchHacUserLoginLog__return);


        }
        {
        System.out.println("Invoking idsCheckAttributeUnique...");
        com.haier.openplatform.hac.service.UserIDSDTO _idsCheckAttributeUnique_arg0 = null;
        com.haier.openplatform.hac.service.ExecuteResult _idsCheckAttributeUnique__return = port.idsCheckAttributeUnique(_idsCheckAttributeUnique_arg0);
        System.out.println("idsCheckAttributeUnique.result=" + _idsCheckAttributeUnique__return);


        }
        {
        System.out.println("Invoking searchUserByInfo...");
        java.lang.String _searchUserByInfo_arg0 = "";
        java.lang.String _searchUserByInfo_arg1 = "";
        java.lang.String _searchUserByInfo_arg2 = "";
        java.lang.String _searchUserByInfo_arg3 = "";
        com.haier.openplatform.hac.service.HacVariousList _searchUserByInfo_arg4 = null;
        com.haier.openplatform.hac.service.ExecuteResult _searchUserByInfo__return = port.searchUserByInfo(_searchUserByInfo_arg0, _searchUserByInfo_arg1, _searchUserByInfo_arg2, _searchUserByInfo_arg3, _searchUserByInfo_arg4);
        System.out.println("searchUserByInfo.result=" + _searchUserByInfo__return);


        }
        {
        System.out.println("Invoking exportUser...");
        java.lang.String _exportUser_arg0 = "";
        java.lang.String _exportUser__return = port.exportUser(_exportUser_arg0);
        System.out.println("exportUser.result=" + _exportUser__return);


        }
        {
        System.out.println("Invoking searchUserByDemain...");
        java.lang.String _searchUserByDemain_arg0 = "";
        java.lang.String _searchUserByDemain_arg1 = "";
        java.lang.String _searchUserByDemain_arg2 = "";
        com.haier.openplatform.hac.service.HacVariList _searchUserByDemain_arg3 = null;
        com.haier.openplatform.hac.service.ExecuteResult _searchUserByDemain__return = port.searchUserByDemain(_searchUserByDemain_arg0, _searchUserByDemain_arg1, _searchUserByDemain_arg2, _searchUserByDemain_arg3);
        System.out.println("searchUserByDemain.result=" + _searchUserByDemain__return);


        }
        {
        System.out.println("Invoking searchAppAuth...");
        java.lang.String _searchAppAuth_arg0 = "";
        com.haier.openplatform.hac.service.ExecuteResult _searchAppAuth__return = port.searchAppAuth(_searchAppAuth_arg0);
        System.out.println("searchAppAuth.result=" + _searchAppAuth__return);


        }
        {
        System.out.println("Invoking exportHacRole...");
        java.lang.Long _exportHacRole_arg0 = null;
        java.lang.String _exportHacRole__return = port.exportHacRole(_exportHacRole_arg0);
        System.out.println("exportHacRole.result=" + _exportHacRole__return);


        }
        {
        System.out.println("Invoking searchUserByGroupIdOrGroupNameForString...");
        com.haier.openplatform.hac.service.HacGroupDTO _searchUserByGroupIdOrGroupNameForString_arg0 = null;
        java.lang.String _searchUserByGroupIdOrGroupNameForString__return = port.searchUserByGroupIdOrGroupNameForString(_searchUserByGroupIdOrGroupNameForString_arg0);
        System.out.println("searchUserByGroupIdOrGroupNameForString.result=" + _searchUserByGroupIdOrGroupNameForString__return);


        }
        {
        System.out.println("Invoking identifyInvalidUsers...");
        java.util.List<java.lang.String> _identifyInvalidUsers_arg0 = null;
        java.util.List<java.lang.String> _identifyInvalidUsers__return = port.identifyInvalidUsers(_identifyInvalidUsers_arg0);
        System.out.println("identifyInvalidUsers.result=" + _identifyInvalidUsers__return);


        }
        {
        System.out.println("Invoking searchSubUserByUserCode...");
        java.lang.String _searchSubUserByUserCode_arg0 = "";
        com.haier.openplatform.hac.service.ExecuteResult _searchSubUserByUserCode__return = port.searchSubUserByUserCode(_searchSubUserByUserCode_arg0);
        System.out.println("searchSubUserByUserCode.result=" + _searchSubUserByUserCode__return);


        }
        {
        System.out.println("Invoking searchHacUser...");
        java.lang.String _searchHacUser_arg0 = "";
        java.lang.String _searchHacUser_arg1 = "";
        java.util.List<com.haier.openplatform.hac.service.HacUserDTO> _searchHacUser__return = port.searchHacUser(_searchHacUser_arg0, _searchHacUser_arg1);
        System.out.println("searchHacUser.result=" + _searchHacUser__return);


        }
        {
        System.out.println("Invoking idsActiveUser...");
        com.haier.openplatform.hac.service.UserIDSDTO _idsActiveUser_arg0 = null;
        com.haier.openplatform.hac.service.ExecuteResult _idsActiveUser__return = port.idsActiveUser(_idsActiveUser_arg0);
        System.out.println("idsActiveUser.result=" + _idsActiveUser__return);


        }
        {
        System.out.println("Invoking validationData...");
        com.haier.openplatform.hac.service.UserMergeDTO _validationData_arg0 = null;
        com.haier.openplatform.hac.service.ExecuteResult _validationData__return = port.validationData(_validationData_arg0);
        System.out.println("validationData.result=" + _validationData__return);


        }
        {
        System.out.println("Invoking getUsersOfRole...");
        java.lang.String _getUsersOfRole_arg0 = "";
        java.lang.String _getUsersOfRole_arg1 = "";
        java.lang.String _getUsersOfRole_arg2 = "";
        java.lang.String _getUsersOfRole_arg3 = "";
        java.lang.String _getUsersOfRole_arg4 = "";
        java.lang.String _getUsersOfRole__return = port.getUsersOfRole(_getUsersOfRole_arg0, _getUsersOfRole_arg1, _getUsersOfRole_arg2, _getUsersOfRole_arg3, _getUsersOfRole_arg4);
        System.out.println("getUsersOfRole.result=" + _getUsersOfRole__return);


        }
        {
        System.out.println("Invoking validatGenerateRandomNum...");
        java.lang.String _validatGenerateRandomNum_arg0 = "";
        java.lang.String _validatGenerateRandomNum_arg1 = "";
        java.lang.String _validatGenerateRandomNum_arg2 = "";
        java.lang.String _validatGenerateRandomNum_arg3 = "";
        com.haier.openplatform.hac.service.ExecuteResult _validatGenerateRandomNum__return = port.validatGenerateRandomNum(_validatGenerateRandomNum_arg0, _validatGenerateRandomNum_arg1, _validatGenerateRandomNum_arg2, _validatGenerateRandomNum_arg3);
        System.out.println("validatGenerateRandomNum.result=" + _validatGenerateRandomNum__return);


        }
        {
        System.out.println("Invoking searchMergeUserByUserName...");
        com.haier.openplatform.hac.service.UserMergeDTO _searchMergeUserByUserName_arg0 = null;
        com.haier.openplatform.hac.service.ExecuteResult _searchMergeUserByUserName__return = port.searchMergeUserByUserName(_searchMergeUserByUserName_arg0);
        System.out.println("searchMergeUserByUserName.result=" + _searchMergeUserByUserName__return);


        }
        {
        System.out.println("Invoking getAllUsersByName...");
        java.lang.String _getAllUsersByName_arg0 = "";
        java.lang.String _getAllUsersByName_arg1 = "";
        java.lang.String _getAllUsersByName_arg2 = "";
        java.lang.String _getAllUsersByName_arg3 = "";
        java.lang.String _getAllUsersByName_arg4 = "";
        java.lang.String _getAllUsersByName__return = port.getAllUsersByName(_getAllUsersByName_arg0, _getAllUsersByName_arg1, _getAllUsersByName_arg2, _getAllUsersByName_arg3, _getAllUsersByName_arg4);
        System.out.println("getAllUsersByName.result=" + _getAllUsersByName__return);


        }
        {
        System.out.println("Invoking searchHucUser...");
        java.lang.String _searchHucUser_arg0 = "";
        java.lang.String _searchHucUser_arg1 = "";
        java.lang.String _searchHucUser_arg2 = "";
        java.util.List<com.haier.openplatform.hac.service.HucUserDTO> _searchHucUser__return = port.searchHucUser(_searchHucUser_arg0, _searchHucUser_arg1, _searchHucUser_arg2);
        System.out.println("searchHucUser.result=" + _searchHucUser__return);


        }
        {
        System.out.println("Invoking mergeUserLogin...");
        com.haier.openplatform.hac.service.UserMergeDTO _mergeUserLogin_arg0 = null;
        com.haier.openplatform.hac.service.ExecuteResult _mergeUserLogin__return = port.mergeUserLogin(_mergeUserLogin_arg0);
        System.out.println("mergeUserLogin.result=" + _mergeUserLogin__return);


        }
        {
        System.out.println("Invoking searchHacUserByAppAndDate...");
        java.lang.String _searchHacUserByAppAndDate_arg0 = "";
        java.lang.String _searchHacUserByAppAndDate_arg1 = "";
        java.lang.String _searchHacUserByAppAndDate_arg2 = "";
        java.lang.String _searchHacUserByAppAndDate_arg3 = "";
        com.haier.openplatform.hac.service.ExecuteResult _searchHacUserByAppAndDate__return = port.searchHacUserByAppAndDate(_searchHacUserByAppAndDate_arg0, _searchHacUserByAppAndDate_arg1, _searchHacUserByAppAndDate_arg2, _searchHacUserByAppAndDate_arg3);
        System.out.println("searchHacUserByAppAndDate.result=" + _searchHacUserByAppAndDate__return);


        }
        {
        System.out.println("Invoking searchMergeUser...");
        com.haier.openplatform.hac.service.UserMergeDTO _searchMergeUser_arg0 = null;
        com.haier.openplatform.hac.service.ExecuteResult _searchMergeUser__return = port.searchMergeUser(_searchMergeUser_arg0);
        System.out.println("searchMergeUser.result=" + _searchMergeUser__return);


        }
        {
        System.out.println("Invoking mergeUserLoginDisabled...");
        com.haier.openplatform.hac.service.UserMergeDTO _mergeUserLoginDisabled_arg0 = null;
        com.haier.openplatform.hac.service.ExecuteResult _mergeUserLoginDisabled__return = port.mergeUserLoginDisabled(_mergeUserLoginDisabled_arg0);
        System.out.println("mergeUserLoginDisabled.result=" + _mergeUserLoginDisabled__return);


        }
        {
        System.out.println("Invoking searchUserByDatas...");
        com.haier.openplatform.hac.service.ServiceClassDTO _searchUserByDatas_arg0 = null;
        com.haier.openplatform.hac.service.SucOrErrDTO _searchUserByDatas__return = port.searchUserByDatas(_searchUserByDatas_arg0);
        System.out.println("searchUserByDatas.result=" + _searchUserByDatas__return);


        }
        {
        System.out.println("Invoking getUserInfoByNickName...");
        java.lang.String _getUserInfoByNickName_arg0 = "";
        java.util.List<com.haier.openplatform.hac.service.DataGovUserDTO> _getUserInfoByNickName__return = port.getUserInfoByNickName(_getUserInfoByNickName_arg0);
        System.out.println("getUserInfoByNickName.result=" + _getUserInfoByNickName__return);


        }
        {
        System.out.println("Invoking idsUserLogout...");
        com.haier.openplatform.hac.service.UserIDSDTO _idsUserLogout_arg0 = null;
        com.haier.openplatform.hac.service.ExecuteResult _idsUserLogout__return = port.idsUserLogout(_idsUserLogout_arg0);
        System.out.println("idsUserLogout.result=" + _idsUserLogout__return);


        }
        {
        System.out.println("Invoking idsResendActiveCode...");
        com.haier.openplatform.hac.service.UserIDSDTO _idsResendActiveCode_arg0 = null;
        com.haier.openplatform.hac.service.ExecuteResult _idsResendActiveCode__return = port.idsResendActiveCode(_idsResendActiveCode_arg0);
        System.out.println("idsResendActiveCode.result=" + _idsResendActiveCode__return);


        }
        {
        System.out.println("Invoking getUsersOfPrivilege...");
        java.lang.String _getUsersOfPrivilege_arg0 = "";
        java.lang.String _getUsersOfPrivilege_arg1 = "";
        java.lang.String _getUsersOfPrivilege_arg2 = "";
        java.lang.String _getUsersOfPrivilege_arg3 = "";
        java.lang.String _getUsersOfPrivilege_arg4 = "";
        java.lang.String _getUsersOfPrivilege__return = port.getUsersOfPrivilege(_getUsersOfPrivilege_arg0, _getUsersOfPrivilege_arg1, _getUsersOfPrivilege_arg2, _getUsersOfPrivilege_arg3, _getUsersOfPrivilege_arg4);
        System.out.println("getUsersOfPrivilege.result=" + _getUsersOfPrivilege__return);


        }
        {
        System.out.println("Invoking idsFindUserBySSOID...");
        com.haier.openplatform.hac.service.UserIDSDTO _idsFindUserBySSOID_arg0 = null;
        com.haier.openplatform.hac.service.ExecuteResult _idsFindUserBySSOID__return = port.idsFindUserBySSOID(_idsFindUserBySSOID_arg0);
        System.out.println("idsFindUserBySSOID.result=" + _idsFindUserBySSOID__return);


        }

        System.exit(0);
    }

}
