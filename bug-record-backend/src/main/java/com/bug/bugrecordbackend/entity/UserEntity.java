package com.bug.bugrecordbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bug.bugrecordbackend.handler.TypeHandler.ListTypeHandler;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Data
@TableName(value = "user",autoResultMap = true)
public class UserEntity  extends BaseEntity{
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String description;
    @TableField(jdbcType = JdbcType.VARCHAR,typeHandler = ListTypeHandler.class)
    private List<String> roles;
}
