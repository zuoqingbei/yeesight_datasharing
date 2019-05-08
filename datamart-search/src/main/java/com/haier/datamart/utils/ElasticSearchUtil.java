package com.haier.datamart.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONObject;
import com.haier.datamart.entity.ViewSearch;
/***
 * ElasticSearch工具类
 * @author Tom
 *
 */
@Configuration
public class ElasticSearchUtil {
	private static String esModel="dev";
	//访问的ElasticSearch服务器地址
	private final static String HOST = "182.61.49.107";
	//http请求的端口是9200，客户端是9300，java代码使用的是客户端，所以端口号是9300
	public final static int PORT = 9300;
	
	//private static TransportClient client = null;

	/*public ElasticSearchUtil() {
		// 设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        // 创建client
        try {
			client = new PreBuiltTransportClient(settings)
			            .addTransportAddress(new TransportAddress(InetAddress.getByName(HOST), PORT));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	public static void main(String[] args) {
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		// 创建client
		try {
			TransportClient client = new PreBuiltTransportClient(settings)
			.addTransportAddress(new TransportAddress(InetAddress.getByName("10.138.93.55"), 9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static TransportClient getClient(){
		if(!"dev".equals(esModel)){
			Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
			// 创建client
			try {
				TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new TransportAddress(InetAddress.getByName(HOST), PORT));
				return  client;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}else{
			return null;
		}
	}

	 public static List responseToList(TransportClient client,SearchResponse response){  
	        SearchHits hits = response.getHits();  
	      
	        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
	        for (int i = 0; i<hits.getHits().length; i++) {  
	            Map<String, Object> map = hits.getAt(i).getSourceAsMap();  
	            list.add(map);  
	        }  
	        return list;  
	    }  
	//查询es索引下所有数据条数
		 public static  String matchAllQueryCount(TransportClient client) throws Exception{  
		        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();  
		        SearchResponse response = client.prepareSearch("view_search").setQuery(queryBuilder).get();  
		        long count = response.getHits().getTotalHits();
		        System.out.println(count+"------------------------------------------------------");
		        return response.toString();
		    } 
	 //分页
	 public static Map<String, Object> fenye(TransportClient client,int index,int size) throws Exception {
		 //获取所有的数据条数，为分页
		 QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();  
	     SearchRequestBuilder setQuery = client.prepareSearch("view_search").setQuery(queryBuilder);
	     SearchResponse responseAll = setQuery.get();
	     
	     long count = responseAll.getHits().getTotalHits();
	     String count2Str=String.valueOf(count);
		 //QueryBuilder qb1 = QueryBuilders.matchPhraseQuery("name", "小别克听");
		 SortBuilder sortBuilder = SortBuilders.fieldSort("viewNum")
	                .order(SortOrder.DESC);
	        SearchResponse response = setQuery 
	                .addSort(sortBuilder)
	                .setFrom(index)   
	                .setSize(size)  
	                .execute().actionGet();  
	        List responseToList = responseToList(client,response);
	        Map<String,Object> map=new HashMap<String, Object>();
	        map.put("count", count2Str);
	        //JSONObject jsStr = JSONObject.parseObject(response.toString());
	        map.put("data", responseToList);
	        //return response.toString();
	        System.out.println(responseToList);
	        System.out.println(responseToList.size()+"=================================");
	        client.close();
	        return map;
	  
	    } 
	 //关键词查询
	 public static Map<String, Object> getByKeyword(TransportClient client,String index,String keyword,int page,int pageSize) throws Exception {
		  BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
				 //.should(QueryBuilders.termQuery("name",keyword)).boost(10) 
				  //.should(QueryBuilders.termQuery("descs",keyword)).boost(10)//设置此查询的权重。 匹配此查询的文件（除正常权重之外）的得分乘以提供的提升。  
	              //.should(QueryBuilders.termQuery("tags",keyword)).boost(10); 
		  
	                
          
	       if(keyword!=null&&!keyword.equals("")){  
	           QueryBuilder nameBuilder=QueryBuilders.matchQuery("name", keyword).analyzer("ik_max_word").boost(100);  
	           QueryBuilder labelBuilder=QueryBuilders.matchQuery("descs", keyword).analyzer("ik_max_word").boost(10);  
	           QueryBuilder categoryBuilder=QueryBuilders.matchQuery("tags", keyword).analyzer("ik_max_word").boost(10);  
	           boolQueryBuilder.should(nameBuilder).should(labelBuilder).should(categoryBuilder);  
	        }else{  
	           boolQueryBuilder.must(QueryBuilders.matchAllQuery());  
	        }  
	        SearchRequestBuilder setQuery=client.prepareSearch(index).setTypes(index).setQuery(boolQueryBuilder);  
	        SearchResponse responseAll = setQuery.get();
		    long count = responseAll.getHits().getTotalHits();           
	        SearchResponse response=setQuery  
	              .setFrom(page).setSize(pageSize)  
	              .setExplain(true)  
	              .get();
	        List responseToList = responseToList(client,response);
	        Map<String,Object> map=new HashMap<String, Object>();
	        map.put("count",  String.valueOf(count));
	        //JSONObject jsStr = JSONObject.parseObject(response.toString());
	        map.put("data", responseToList);
			return map;  
		 
	  
	    } 
	 //条件查询-获取推荐有条件
	 public static List getTuiJian(TransportClient client,String index,String type,int pageNum ,int size) throws Exception {  
		 SortBuilder sortBuilder = SortBuilders.fieldSort("viewNum")
	                .order(SortOrder.DESC);
		 QueryBuilder qb1 = QueryBuilders.matchPhraseQuery("type", type);
		 SearchResponse  searchResponse = client.prepareSearch().setIndices(index)
                 .setTypes(index).setQuery(qb1)  
                 .addSort(sortBuilder).setFrom(pageNum).setSize(size).get(); 
		 List responseToList = responseToList(client,searchResponse);
	        return responseToList;
	    } 
	//条件查询-获取推荐
		 public static List getTallTuiJian(TransportClient client,String index,int pageNum ,int size) throws Exception {  
			 SortBuilder sortBuilder = SortBuilders.fieldSort("viewNum")
		                .order(SortOrder.DESC);
			 SearchResponse  searchResponse = client.prepareSearch().setIndices(index)
	                 .addSort(sortBuilder).setFrom(pageNum).setSize(size).get();  
	        // SearchHits hits = searchResponse.getHits();  
			 List responseToList = responseToList(client,searchResponse);
		    return responseToList;
		    } 
    /**
     * 根据index，type和id来获取数据
     * @param index
     * @param type
     * @param id
     * @return
     */
	public static Map<String, Object> getRecordById(TransportClient client,String index, String type,
			String id) {
		GetResponse response = client.prepareGet(index, type, id).get();
		return response.getSourceAsMap();
	}
	/**
	 * 添加一条记录，不指定id，ES会添加默认的id
	 * @param index
	 * @param type
	 * @param record
	 * @return
	 */
	public static int addOneRecord(TransportClient client,String index, String type,
			Map<String, Object> record) {
		IndexResponse response = client.prepareIndex(index, type)
				.setSource(record).get();
		return response.status().getStatus();
	}
	/**
	 * 添加一条记录，指定id
	 * @param index
	 * @param type
	 * @param id
	 * @param record
	 * @return
	 */
	public static int addOneRecordById(TransportClient client,String index, String type,String id,
			Map<String, Object> record) {
		IndexResponse response = client.prepareIndex(index, type,id)
				.setSource(record).get();
		return response.status().getStatus();
	}
	/**
	 * 添加一条记录，通过实体
	 * @param index
	 * @param type
	 * @param id
	 * @param record
	 * @return
	 */
	public static int addOneRecordByEntity(TransportClient client,String index, String type,String id,
			Object record) {
		IndexResponse response = client.prepareIndex(index, type,id)
				.setSource(record,record).get();
		return response.status().getStatus();
	}
	/**
	 * 根据id删除一条记录
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	public static int deleteOneRecordById(TransportClient client,String index, String type,String id){
		DeleteResponse response = client.prepareDelete(index, type, id).get();
		return response.status().getStatus();
	}
	 
	public static void deleteAllIndex(TransportClient client,String indexName){  
	          
	       // String indexName="view_search";  
	          
	        /**  
	         * 两种方式如下:  
	         */  
	          
	        //1)  
	        //可以根据DeleteIndexResponse对象的isAcknowledged()方法判断删除是否成功,返回值为boolean类型.  
	        DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(indexName)  
	                .execute().actionGet();  
	        System.out.println("是否删除成功:"+dResponse.isAcknowledged());  
	  
	          
	        //2)  
	        //如果传人的indexName不存在会出现异常.可以先判断索引是否存在：  
	        //IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(indexName);  
	          
	        //IndicesExistsResponse inExistsResponse = client.admin().indices()  
	       //         .exists(inExistsRequest).actionGet();  
	          
	        //根据IndicesExistsResponse对象的isExists()方法的boolean返回值可以判断索引库是否存在.  
	        //System.out.println("是否删除成功:"+inExistsResponse.isExists());  
	    }  
	/**
	 * 根据关键字匹配查询,会查出所有的数据，默认只显示前10条
	 * @param index
	 * @param type
	 * @param match 要匹配的字段
	 * @param keywords 搜索的关键字
	 * @return
	 */
	public static String queryRecord(TransportClient client,String index, String type,String match,String keywords){
		//TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery(match, keywords);
		MatchPhraseQueryBuilder mpq = QueryBuilders.matchPhraseQuery(match, keywords);
		//client.multiSearch(new MultiSearchRequest())
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index)
		.setTypes(type)
		.setQuery(mpq);
		SearchResponse searchResponse = searchRequestBuilder.get();
		return searchResponse.toString();
	}
	
	/**
	 * 根据关键字匹配查询,分页查询
	 * @param index
	 * @param type
	 * @param size 一页显示的数量
	 * @param page 页数(从1开始)
	 * @param match 要匹配的字段
	 * @param keywords 搜索的关键字
	 * @return
	 */
	public static String queryRecordByPage(TransportClient client,String index, String type,int size,int page,String match,String keywords){
		//TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery(match, keywords);
		MatchPhraseQueryBuilder mpq = QueryBuilders.matchPhraseQuery(match, keywords);
		//client.multiSearch(new MultiSearchRequest())
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index)
		.setTypes(type)
		.setFrom((page-1)*size)
		.setSize(size)
		.setQuery(mpq);
		SearchResponse searchResponse = searchRequestBuilder.get();
		return searchResponse.toString();
	}

	
	
	/**
	 * 实体类转换成Map类型
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {  
	    if (obj == null) {  
	        return null;  
	    }  
	    Map<String, Object> map = new HashMap<String, Object>();  
	    try {  
	        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	        for (PropertyDescriptor property : propertyDescriptors) {  
	            String key = property.getName();  
	            // 过滤class属性  
	            if (!key.equals("class")) {  
	                // 得到property对应的getter方法  
	                Method getter = property.getReadMethod();  
	                Object value = getter.invoke(obj);  
	                map.put(key, value);  
	            }  
	  
	        }  
	    } catch (Exception e) {  
	        System.err.println("transBean2Map Error {}");  
	    }  
	    return map;  
	  
	}  
	
	 //关键词查询
	 public static Map<String, Object> getByKeyword2(TransportClient client,String index,ViewSearch viewsearch,int page,int pageSize) throws Exception {
		  BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
				 //.should(QueryBuilders.termQuery("name",keyword)).boost(10) 
				  //.should(QueryBuilders.termQuery("descs",keyword)).boost(10)//设置此查询的权重。 匹配此查询的文件（除正常权重之外）的得分乘以提供的提升。  
	              //.should(QueryBuilders.termQuery("tags",keyword)).boost(10); 
		  
	                
        
	       if(viewsearch!=null){  
	    	   if(StringUtils.isNotBlank(viewsearch.getName())){
	    		   QueryBuilder nameBuilder=QueryBuilders.matchQuery("name", viewsearch.getName());//.analyzer("ik_max_word").boost(50);  ;  
		           QueryBuilder labelBuilder=QueryBuilders.matchQuery("descs", viewsearch.getName());  
		           QueryBuilder categoryBuilder=QueryBuilders.matchQuery("tags", viewsearch.getName());
		           boolQueryBuilder = boolQueryBuilder.must(nameBuilder).must(labelBuilder).must(categoryBuilder); 
	    	   }
	          
	    	   if(StringUtils.isNotBlank(viewsearch.getCategory1())){
	        	   boolQueryBuilder=boolQueryBuilder.must(QueryBuilders.matchQuery("category1", viewsearch.getCategory1()));
	           }
	         
	           if(StringUtils.isNotBlank(viewsearch.getCategory2())){
	        	   boolQueryBuilder=boolQueryBuilder.must(QueryBuilders.matchQuery("category2", viewsearch.getCategory2()));
	           }
	           if(StringUtils.isNotBlank(viewsearch.getCategory3())){
	        	   boolQueryBuilder=boolQueryBuilder.must(QueryBuilders.matchQuery("category3", viewsearch.getCategory3()));
	           }
	           if(StringUtils.isNotBlank(viewsearch.getSubName())){
	        	   boolQueryBuilder=boolQueryBuilder.must(QueryBuilders.matchQuery("subname", viewsearch.getSubName()));
	           }
	        }else{  
	           boolQueryBuilder.must(QueryBuilders.matchAllQuery());  
	        }  
	       
	        SearchRequestBuilder setQuery=client.prepareSearch(index).setTypes(index).setQuery(boolQueryBuilder); 
	        if(StringUtils.isNotBlank(viewsearch.getViewNum()+"")){
	        	 SortBuilder sortBuilder = SortBuilders.fieldSort("viewNum") .order(SortOrder.DESC);
	        	 setQuery.addSort(sortBuilder);
	 	               
	           }
	        if(StringUtils.isNotBlank(viewsearch.getCreateBy())){
	        	 SortBuilder sortBuilder = SortBuilders.fieldSort("createDate") .order(SortOrder.DESC);
	        	 setQuery.addSort(sortBuilder);
	 	               
	           }
	        SearchResponse responseAll = setQuery.get();
		    long count = responseAll.getHits().getTotalHits();           
	        SearchResponse response=setQuery  
	              .setFrom(page).setSize(pageSize)  
	              .setExplain(true)  
	              .get();
	        List responseToList = responseToList(client,response);
	        Map<String,Object> map=new HashMap<String, Object>();
	        map.put("count",  String.valueOf(count));
	        //JSONObject jsStr = JSONObject.parseObject(response.toString());
	        map.put("data", responseToList);
	        client.close();
			return map;  
		 
	  
	    } 

	
	
	
	
	
	

}
