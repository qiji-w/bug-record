package com.bug.bugrecordbackend.handler.TypeHandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List<String>转JDBC的字符串
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class ListTypeHandler implements TypeHandler<List<String>> {
    /**
     * 保存到数据库时，List<String>转VARCHAR
     * @param preparedStatement
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i,List <String> parameter, JdbcType jdbcType) throws SQLException {
        String params = null;
        if(parameter!=null && parameter.size()>0) {
            params = parameter.stream().collect(Collectors.joining(","));
        }
        preparedStatement.setString(i, params);
    }

    /**
     * 从数据库获取时，VARCHAR转List<String>
     * @param resultSet
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public List <String> getResult(ResultSet resultSet, String columnName) throws SQLException {
        return Arrays.asList(resultSet.getString(columnName).split(","));
    }

    @Override
    public List <String> getResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return Arrays.asList(resultSet.getString(columnIndex).split(","));
    }

    @Override
    public List <String> getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        String params = callableStatement.getString(columnIndex);
        return Arrays.asList(params.split(","));
    }
}
