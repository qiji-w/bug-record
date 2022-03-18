package com.bug.bugrecordbackend.controller;

import com.bug.bugrecordbackend.dto.common.ResponseData;
import com.bug.bugrecordbackend.dto.output.account.LoginOutputDto;
import com.bug.bugrecordbackend.service.UserService;
import com.bug.bugrecordbackend.dto.input.account.LoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "账号接口")
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseData<LoginOutputDto> login(@RequestBody  @Validated LoginDto loginDto){
        return userService.login(loginDto);
    }
}
