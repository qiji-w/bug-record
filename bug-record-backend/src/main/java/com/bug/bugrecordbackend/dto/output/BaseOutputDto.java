package com.bug.bugrecordbackend.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseOutputDto implements Serializable {
    private Integer createById;
    private String createByName;
    private Date createTime;
    private Integer updateById;
    private String updateByName;
    private Date updateTime;
}
