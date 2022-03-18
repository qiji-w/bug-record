package com.bug.bugrecordbackend.handler.TypeHandler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Set<String>转JDBC的字符串
 */
@MappedTypes(Set.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class SetTypeHandler extends BaseTypeHandler<Set> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set parameter, JdbcType jdbcType)
            throws SQLException {
        String param = parameter.toString().replaceAll("\\[|\\]| ", "");
        ps.setString(i, param);
    }

    @Override
    public Set<Integer> getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String sqlSet = rs.getString(columnName);
        return getSet(sqlSet);
    }

    @Override
    public Set<Integer> getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String sqlSet = rs.getString(columnIndex);
        return getSet(sqlSet);
    }

    @Override
    public Set<Integer> getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String sqlSet = cs.getString(columnIndex);
        return getSet(sqlSet);
    }

    private Set<Integer> getSet(String sqlSet) {
        if (StringUtils.isNotBlank(sqlSet)) {
            return Arrays.asList(sqlSet.split(",")).stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
        }
        return new HashSet();
    }
}
