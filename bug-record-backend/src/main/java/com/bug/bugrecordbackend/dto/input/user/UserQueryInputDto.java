package com.bug.bugrecordbackend.dto.input.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserQueryInputDto implements Serializable {
    private String username;
    private String name;
    private List<String> roles;
    private String description;
}
