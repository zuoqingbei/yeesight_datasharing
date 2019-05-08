package com.haier.datamart.utils.jdbc.handle;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by long on 2018/10/25.
 * result set 转成 map的类
 */
public class MapResultHandle<T> implements ResultSetHandle<T> {
    @Override
    public T handle(ResultSet resultSet) {
        List<Map<String,Object>> result = new ArrayList<>();
        try {
            //获取列名
            List<String> names= new ArrayList<>();
            System.out.println(resultSet.getMetaData().getColumnCount());
            for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
                names.add(resultSet.getMetaData().getColumnName(i));
            }
            //构造数据
            while (resultSet.next()){
                Map<String,Object> item = new java.util.LinkedHashMap<>();
                for(String name:names){
                    item.put(name,resultSet.getObject(name));
                }
                result.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (T)result;
    }
}
