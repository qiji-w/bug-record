package com.bug.bugrecordbackend.dto.output.version;

import com.bug.bugrecordbackend.dto.output.BaseOutputDto;
import lombok.Data;

@Data
public class VersionOutputDto extends BaseOutputDto {
    private Integer id;
    private String name;
    private String description;
}
