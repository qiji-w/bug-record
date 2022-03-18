package com.bug.bugrecordbackend.dto.output.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class LoginOutputDto {
    private String username;
    private String name;
    private String description;
    private List<String> roles;
}
