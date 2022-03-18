package com.bug.bugrecordbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("version")
public class VersionEntity  extends BaseEntity {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String name;
    private String description;
}
