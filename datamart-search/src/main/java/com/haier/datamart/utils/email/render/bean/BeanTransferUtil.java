package com.haier.datamart.utils.email.render.bean;

import com.haier.datamart.entity.MailForQueryBeanParent;
import com.haier.datamart.entity.MailForQueryBeanTableMap;
import com.haier.datamart.entity.MailHeaderInfo;
import com.haier.datamart.entity.MailSettingInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by long on 2019/1/23.
 */
public class BeanTransferUtil {
    /**
     * 通过map转化为实体类 便于模板引擎解析
     * @param entity
     * @return
     */
    public static MultiEmailBean tranOrigToBean(MailForQueryBeanParent entity){
        MultiEmailBean result = new MultiEmailBean();
        MailSettingInfo targetMailInfo = entity.getMailBaseInfo();
        List<MailHeaderInfo> list=targetMailInfo.getMailHeaderInfoLists();
        String s="";
        for(MailHeaderInfo info:list){
        	if("1".equals(info.getIsShow()))
        	s+=info.getImplementStr();
        }
        result.setHearder(s);//第一段落
        //设置表内容列表
        if(entity.getTableMap()!=null){
            List<MailForQueryBeanTableMap> tableList = entity.getTableMap();
            List<TableRenderBean> tables = new ArrayList<>();
            for(MailForQueryBeanTableMap item:tableList){
                TableRenderBean tableItem = new TableRenderBean();
                tableItem.setTitle(String.valueOf(item.getTitle()));
                List<Map<String, Object>> content  = item.getContent();
                List<Map<String, Object>> titleColumnMap  = item.getTitleColumnMap();
                List<Map<String, Object>> bodyStyle  = item.getBodyStyle();
                //构建表头信息
                List<String> tableHeaders = new ArrayList<>();
                for(Map<String, Object> titleColumn:titleColumnMap ){
                    tableHeaders.add(String.valueOf(titleColumn.get("name")));
                }

                //构建表内容信息
                List<List<String>> contentList = new ArrayList<>();
                int x=0;
                for(Map<String, Object> contentItem:content){
                    List<String> contentItemList = new ArrayList<>();
                    for(int i=0;i<tableHeaders.size();i++){
                    	String td="";
                    	String style="";
                    	if(bodyStyle.size()>x){
                    		style="background:"+String.valueOf(String.valueOf(bodyStyle.get(x).get(String.valueOf(titleColumnMap.get(i).get("code")))));
                    	}
                    	td+="<td style='"+style+"'>"+String.valueOf(contentItem.get(String.valueOf(titleColumnMap.get(i).get("code"))))+"</td>";
                    	contentItemList.add(td);
                    }
                	x++;
                    contentList.add(contentItemList);
                }
                
                
                tableItem.setTableHeader(tableHeaders);
                tableItem.setTableContent(contentList);
                tables.add(tableItem);
            }
            result.setTableRenderBeanList(tables);
        }
        return result;
    }
}
