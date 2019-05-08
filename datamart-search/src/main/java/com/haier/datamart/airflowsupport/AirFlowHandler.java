package com.haier.datamart.airflowsupport;

import com.alibaba.fastjson.JSON;
import com.haier.datamart.exception.BusinessException;
import com.haier.datamart.service.impl.AirflowKettleSupportServiceImpl;
import com.haier.datamart.utils.StringUtil;
import com.haier.datamart.utils.jdbc.JdbcDataQuery;
import com.haier.datamart.utils.jdbc.handle.CountResultHandle;
import com.haier.datamart.utils.jdbc.handle.MapResultHandle;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 刘志龙
 * airflow的操作
 */
@Component
public class AirFlowHandler {
    @Value("${airflowsupport.AirFlowHandler.airflowIp}")
    private String airflowIp;
    @Value("${airflowsupport.AirFlowHandler.airflowExtraServiceIp}")
    private String airflowExtraServiceIp;
    @Value("${airflowsupport.AirFlowHandler.airflowJdbc}")
    private String airflowJdbc;
    @Value("${airflowsupport.AirFlowHandler.airflowJdbcName}")
    private String airflowJdbcName;
    @Value("${airflowsupport.AirFlowHandler.airflowJdbcPassword}")
    private String airflowJdbcPassword;
    @Value("${airflowsupport.AirflowKettleSupportWebsoket.host}")
    private String host ;
    @Value("${airflowsupport.AirflowKettleSupportWebsoket.port}")
    private int port;
    private static  String AIRFLOW_ADDRESS = "10.138.42.215:12800";
    private static  String AIRFLOW_SERVICE_ADDRESS="10.138.42.215:12801";
    private static  String AIRFLOW_JDBC="jdbc:mysql://10.138.42.215:3306/airflow?useUnicode=true&characterEncoding=utf8";
    private static  String AIRFLOW_JDBC_USER = "airflow";
    private static  String AIRFLOW_JDBC_PASSWORD = "liea$Firf123lowf";
    public static  String COMMAND_TEST_HOST = "localhost";
    public static  int COMMAND_TEST_PORT = 11000;
    private static  Logger logger = LoggerFactory.getLogger(AirflowKettleSupportServiceImpl.class);

    @PostConstruct
    public void init(){
        AIRFLOW_ADDRESS = airflowIp;
        AIRFLOW_SERVICE_ADDRESS = airflowExtraServiceIp;
        AIRFLOW_JDBC = airflowJdbc;
        AIRFLOW_JDBC_USER = airflowJdbcName;
        AIRFLOW_JDBC_PASSWORD = airflowJdbcPassword;
        COMMAND_TEST_HOST = host;
        COMMAND_TEST_PORT = port;
    }
    /**
     * 启动一个调度
     * @param dagName
     */
    public static void run(String dagName){

        String url = "http://"+AIRFLOW_SERVICE_ADDRESS+"/run_dag";
        logger.info("do run:"+url);
        Map<String,String> params = new HashMap<>();
        params.put("dagid",dagName);
        String result =  doPostByForm(url,params);
        Map<String,String> mapType = JSON.parseObject(result,Map.class);
        if(!"S".equals(mapType.get("result"))){
            throw  new BusinessException(mapType.get("resultMsg"));
        }
    }

    /**
     * 暂停一个调度
     * @param dagName
     */
    public static void pause(String dagName){
        String url = "http://"+AIRFLOW_SERVICE_ADDRESS+"/pause_dag";
        logger.info("do pause:"+url);
        Map<String,String> params = new HashMap<>();
        params.put("dagid",dagName);
        String result =  doPostByForm(url,params);
        Map<String,String> mapType = JSON.parseObject(result,Map.class);
        if(!"S".equals(mapType.get("result"))){
            throw  new BusinessException(mapType.get("resultMsg"));
        }
    }

    /**
     * 将dag信息写在服务器上
     * @param dagName
     * @param dagContent
     */
    public static void writedag(String dagName,String dagContent){
        String url = "http://"+AIRFLOW_SERVICE_ADDRESS+"/create_dag";
        logger.info("do writedag:"+url);
        Map<String,String> params = new HashMap<>();
        params.put("dagname",dagName);
        params.put("dagcontent",dagContent);
        String result =  doPostByForm(url,params);
        Map<String,String> mapType = JSON.parseObject(result,Map.class);
        if(!"S".equals(mapType.get("result"))){
            throw  new BusinessException(mapType.get("resultMsg"));
        }
    }
    /**
     * 测试bash命令
     * @param dagName
     * @param bashCommand
     */
    public static String testCommmand(String dagName,String bashCommand){

        if(StringUtils.isNotBlank(bashCommand)){
            if(bashCommand.indexOf("rm ")!=-1){
                throw new BusinessException("当前命令禁止执行！！！");
            }
            if(bashCommand.indexOf("su ")!=-1){
                throw new BusinessException("当前命令禁止执行！！！");
            }
        }
        String url = "http://"+AIRFLOW_SERVICE_ADDRESS+"/bash_test";
        logger.info("do testCommmand:"+url);
        Map<String,String> params = new HashMap<>();
        params.put("dagname",dagName);
        params.put("bashcommand",bashCommand);
        String result =  doPostByForm(url,params);
        Map<String,String> mapType = JSON.parseObject(result,Map.class);
        if(!"S".equals(mapType.get("result"))){
            throw  new BusinessException(mapType.get("resultMsg"));
        }
        return mapType.get("commandresult");
    }
    private static String doPostByForm(String url,Map<String,String> params){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            HttpPost post = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            for(String key:params.keySet()){
                pairs.add(new BasicNameValuePair(key,params.get(key)));
            }

            CloseableHttpResponse response = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
                response = httpClient.execute(post);
                if(response != null && response.getStatusLine().getStatusCode() == 200)
                {
                    HttpEntity entity = response.getEntity();
                    //转化json
                    return EntityUtils.toString(entity);
                }
                throw new BusinessException("调用接口失败");
            } catch (UnsupportedEncodingException e) {
                throw new BusinessException(e);
            } catch (ClientProtocolException e) {
                throw new BusinessException(e);

            } catch (IOException e) {
                throw new BusinessException(e);
            }
        }
        finally {

        }
    }

    /**
     * 刷新一个 dagName
     * @param dagName
     */
    public static  void refreshdag(String dagName){
        String url = "http://"+AIRFLOW_ADDRESS+"/admin/airflow/refresh?dag_id="+dagName;
        logger.info("do refreshdag:"+url);
        CloseableHttpClient httpCilent = HttpClients.createDefault();//Creates CloseableHttpClient instance with default configuration.
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse =  httpCilent.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200||httpResponse.getStatusLine().getStatusCode() == 302){
                return;
            }else if(httpResponse.getStatusLine().getStatusCode() == 500){
                throw new BusinessException("500错误");
            }
        } catch (IOException e) {
            throw new BusinessException(e);
        } finally {
            try {
                httpCilent.close();//释放资源
            } catch (IOException e) {
            }
        }
    }
    /**
     * 运行一个 dagName
     * @param dagName
     */
    public static  void firedag(String dagName){
        String url = "http://"+AIRFLOW_ADDRESS+"/admin/airflow/trigger?dag_id="+dagName;
        logger.info("do firedag:"+url);
        CloseableHttpClient httpCilent = HttpClients.createDefault();//Creates CloseableHttpClient instance with default configuration.
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse =  httpCilent.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200||httpResponse.getStatusLine().getStatusCode() == 302){
                return;
            }else if(httpResponse.getStatusLine().getStatusCode() == 500){
                throw new BusinessException("500错误");
            }
        } catch (IOException e) {
            throw new BusinessException(e);
        } finally {
            try {
                httpCilent.close();//释放资源
            } catch (IOException e) {
            }
        }
    }
    public static boolean isDagReady(String dagName){
        String url = "http://"+AIRFLOW_ADDRESS+"/admin/airflow/code?dag_id="+dagName;
        logger.info("do isDagReady:"+url);
        CloseableHttpClient httpCilent = HttpClients.createDefault();//Creates CloseableHttpClient instance with default configuration.
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse =  httpCilent.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            throw new BusinessException(e);
        } finally {
            try {
                httpCilent.close();//释放资源
            } catch (IOException e) {
            }
        }
    }

    /**
     * 获取一条记录的运行状态
     * @param dagId
     * @return
     */
    public static Map<String,Integer> getRunningState(String dagId){
        String sql = "select count(*) count_, r.state  from dag_run r where r.dag_id = '"+dagId+"' group by r.state;";
        try(JdbcDataQuery jdbcDataQuery = JdbcDataQuery.createJdbcDataQuery(AIRFLOW_JDBC,AIRFLOW_JDBC_USER,AIRFLOW_JDBC_PASSWORD)){
           List<Map> resultList =  jdbcDataQuery.query(new MapResultHandle<>(),sql.toString());
            Map<String,Integer> result = new HashMap<>();
            result.put(RunningState.SUCCESS,0);
            result.put(RunningState.FAILED,0);
            result.put(RunningState.RUNNING,0);
            resultList.forEach(e->{
                String state = (String) e.get("state");
                if(state.equals(RunningState.SUCCESS)){
                    result.put(RunningState.SUCCESS,Integer.valueOf(e.get("count_").toString()));
                }
                if(state.equals(RunningState.FAILED)){
                    result.put(RunningState.FAILED,Integer.valueOf(e.get("count_").toString()));
                }
                if(state.equals(RunningState.RUNNING)){
                    result.put(RunningState.RUNNING,Integer.valueOf(e.get("count_").toString()));
                }
            });
            return result;
        }catch (Exception e){
            throw  new BusinessException(e);
        }
    }
    public static List<Map<String,Object>> getRunningStateList(String dagId,String state){
        String sql = "select count(*) count_, r.state  from dag_run r where r.dag_id = '"+dagId+"' group by r.state;";
        try(JdbcDataQuery jdbcDataQuery = JdbcDataQuery.createJdbcDataQuery(AIRFLOW_JDBC,AIRFLOW_JDBC_USER,AIRFLOW_JDBC_PASSWORD)){
            List<Map<String,Object>> resultList =  jdbcDataQuery.query(new MapResultHandle<>(),sql.toString());
            return resultList;
        }catch (Exception e){
            throw  new BusinessException(e);
        }
    }
    public static Map<String,Object> getRunningStateList(@NotNull String dagId,  String state, @NotNull Integer pageNum, Integer pageSize){
        if(pageSize == null){
            pageSize = 20;
        }
        StringBuffer sb  = new StringBuffer("SELECT " +
                " r.dag_id," +
                " r.state," +
                " r.run_id," +
                " r.start_date," +
                " r.end_date," +
                " r.execution_date" +
                " FROM" +
                " dag_run r" +
                " WHERE" +
                " r.dag_id = '"+dagId+"'" );
        if (StringUtils.isNotBlank(state)){
            sb.append(" and r.state = '"+state+"'");
        }
        String sqlCount = "select count(*) count_ from ( "+sb.toString()+" ) a53f98f";//数量的sql
        sb.append("" +
                "                ORDER BY" +
                "                r.start_date DESC");
        Integer start = (pageNum-1)*pageSize;
        sb.append(" limit "+start+","+pageSize);
        String sqlContent = sb.toString();
        try(JdbcDataQuery jdbcDataQuery = JdbcDataQuery.createJdbcDataQuery(AIRFLOW_JDBC,AIRFLOW_JDBC_USER,AIRFLOW_JDBC_PASSWORD)){
           Integer allCount = jdbcDataQuery.query(new CountResultHandle<>(),sqlCount);
            List<Map<String,Object>> resultList =  jdbcDataQuery.query(new MapResultHandle<>(),sqlContent.toString());
            Map result = new HashMap();
            result.put("allCount",allCount);
            result.put("list",resultList);
            return result;
        }catch (Exception e){
            throw  new BusinessException(e);
        }
    }

    /**
     * 查看远端的dag是否已经就绪
     * 直接从表中查询
     * @param dagId
     * @return
     */
    public static boolean isRemoteReady(String dagId){
        String sql = "select count(*) count_ from dag d where d.dag_id = '"+dagId+"' and d.is_active = '1'";
        try(JdbcDataQuery jdbcDataQuery = JdbcDataQuery.createJdbcDataQuery(AIRFLOW_JDBC,AIRFLOW_JDBC_USER,AIRFLOW_JDBC_PASSWORD)){
            Integer count =  jdbcDataQuery.query(new CountResultHandle<>(),sql.toString());
            if(count>0){
                return true;
            }
            return false;
        }catch (Exception e){
            throw  new BusinessException(e);
        }
    }
    /**
     * TODO
     * @param dagId
     * @param execDate
     * @return
     */
    public static String getFileContent(String dagId,String execDate){
        String url = "http://"+AIRFLOW_SERVICE_ADDRESS+"/readlog";
        logger.info("do getFileContent:"+url);
        Map<String,String> params = new HashMap<>();
        params.put("dag_id",dagId);
        params.put("exec_date",execDate);
        String result =  doPostByForm(url,params);
        return result;
    }

    public static class RunningState{
        public static final String SUCCESS =  "success";
        public static final String FAILED =  "failed";
        public static final String RUNNING =  "running";

    }
}
