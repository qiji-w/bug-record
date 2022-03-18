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
@TableName(value="bug",autoResultMap = true)
public class BugEntity extends BaseEntity {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String project;
    private String module;
    private String title;
    private String priority;
    private String severity;
    private Integer developerId;
    private String developerName;
    private String description;
    @TableField(jdbcType = JdbcType.VARCHAR,typeHandler = ListTypeHandler.class)
    private List<String> versions;
}
