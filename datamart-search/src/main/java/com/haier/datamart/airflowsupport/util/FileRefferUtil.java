package com.haier.datamart.airflowsupport.util;


import java.io.*;
import java.lang.reflect.Field;
import java.util.stream.Collectors;

/**
 * Created by long on 2018/11/12.
 */
public class FileRefferUtil {
    //2018年11月12日15:39:58

    /**
     * 通过bean的fieldname进行模板的渲染工作 通用功能
     * 替换{{}}包裹的字符串
     * field调用toString进行解析
     *
     * @param templateString
     * @param refferBean
     * @return
     */
    public static String refferedTemplate(String templateString, Object refferBean){
        String target = templateString;
        for(Field item:refferBean.getClass().getDeclaredFields()){
            try {
                item.setAccessible(true);
                String fiedName = item.getName();
                Object fieldValue = item.get(refferBean);
                if(fieldValue!=null){
                    //强制设置成为可以访问
                    //开始替换
                    target = target.replace("{{"+fiedName+"}}",fieldValue.toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        //替换结束
        return target;
    }
    public static String refferedTemplate(File templateFile, Object refferBean) throws IOException {
        //将模板里面的内容读取到String里面
        String templateString = readFileToString(templateFile);

       return refferedTemplate(templateString,refferBean);
    }
    public static String refferedTemplate(InputStream templateFile, Object refferBean) throws IOException {
        //将模板里面的内容读取到String里面
        String templateString = new BufferedReader(new InputStreamReader(templateFile))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        templateFile.close();
        return refferedTemplate(templateString,refferBean);
    }
    public static String readFileToString(File file) throws IOException{
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            System.out.println(s);
        }
        bReader.close();
        String str = sb.toString();
        return str;
    }
}
