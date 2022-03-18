package com.bug.bugrecordbackend.dto.output.bug;

import com.bug.bugrecordbackend.dto.output.BaseOutputDto;
import lombok.Data;

import java.util.List;

@Data
public class BugOutputDto extends BaseOutputDto {
    private Integer id;
    private String project;
    private String module;
    private String title;
    private String priority;
    private String severity;
    private Integer developerId;
    private String developerName;
    private String description;
    private List<String> versions;
}
