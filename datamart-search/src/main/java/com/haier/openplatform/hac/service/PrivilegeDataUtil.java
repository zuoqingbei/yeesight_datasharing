package com.haier.openplatform.hac.service;

import java.util.List;



public final class PrivilegeDataUtil {

    private PrivilegeDataUtil() {
    }
	public static List<HacDataPrivilegeDTO> geprivilegeData(String name) throws Exception {

		
    	HacPrivilegeServiceClient hacPrivilegeServiceClient = new HacPrivilegeServiceClient();
    	HacPrivilegeServiceClientPortType hacPrivilegeServiceClientPort = hacPrivilegeServiceClient.getHacPrivilegeServiceClientPort();
    	UserSourceAndScode s=new UserSourceAndScode();
    	
    	s.setSCode(null);
    	s.setUserSource("portal");
    	List<HacDataPrivilegeDTO> privilegeData = hacPrivilegeServiceClientPort.getPrivilegeData1(name, "S00870", "1.0", "59e5300aec9bda649bfd98674b09fd00", s);
    	/*JSONArray json = JSONArray.fromObject(privilegeData);
    	System.out.println(json);*/
    	return privilegeData;
       
	
	    }

	

}
