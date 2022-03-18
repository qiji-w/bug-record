package com.bug.bugrecordbackend.dto.output.user;

import com.bug.bugrecordbackend.dto.output.BaseOutputDto;
import lombok.Data;

import java.util.List;

@Data
public class UserOutputDto extends BaseOutputDto {
    private Integer id;
    private String username;
    private String name;
    private List<String> roles;
    private String description;
}
