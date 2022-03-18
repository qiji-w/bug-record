package com.bug.bugrecordbackend.handler.TypeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime转JDBC的字符串
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(LocalDateTime.class)
public class DateTimeTypeHanlder extends BaseTypeHandler<LocalDateTime> {

    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /*
     * java属性 - > db属性
     */

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime localDateTime, JdbcType jdbcType) throws SQLException {
        // 这里需要把LocalDateTime格式化成标准的数据格式,否则数据库中字段属性为varchar而不是datetime,则会出现毫秒后面多出几位的情况
        String formatStr = localDateTime.format(df);
        // 这里setObject的原因是 ps.setXXX()只有常见的基本类型,LocalDateTime不属于其中,故需setObject
        ps.setObject(i, formatStr);
    }

    /*
     * 如下3个方法都是 db - > java  即查询出来的数据 -> java属性映射
     */

    @Override
    public LocalDateTime getNullableResult(ResultSet resultSet, String s) throws SQLException {
        // 返回的时候也需要格式化,变成标准格式
        LocalDateTime localDateTime = LocalDateTime.parse(s, df);
        return localDateTime;
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getObject(i, LocalDateTime.class);
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getObject(i, LocalDateTime.class);
    }
}
