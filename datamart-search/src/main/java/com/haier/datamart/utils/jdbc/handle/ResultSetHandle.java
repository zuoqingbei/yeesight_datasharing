package com.haier.datamart.utils.jdbc.handle;

import java.sql.ResultSet;

/**
 * Created by long on 2018/10/25.
 */
public interface ResultSetHandle<T> {
    public T handle(ResultSet resultSet);
}
