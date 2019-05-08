package com.haier.datamart.utils.jdbc.handle;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用于获取查询数据量
 */
public class CountResultHandle<T> implements ResultSetHandle<T> {
    @Override
    public T handle(ResultSet resultSet) {
        Integer result = 0;
        try {
            if(resultSet.next()){
                result = Integer.valueOf(resultSet.getObject(1).toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (T)result;
    }
}
