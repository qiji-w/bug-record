package com.bug.bugrecordbackend.controller;

import com.bug.bugrecordbackend.dto.common.ResponseData;
import com.bug.bugrecordbackend.dto.input.account.LoginDto;
import com.bug.bugrecordbackend.dto.input.user.UserCreateInputDto;
import com.bug.bugrecordbackend.dto.input.user.UserUpdateInputDto;
import com.bug.bugrecordbackend.dto.output.user.UserOutputDto;
import com.bug.bugrecordbackend.service.UserService;
import com.bug.bugrecordbackend.annotation.UserRight;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户接口")
@RestController
@RequestMapping("/user")
@UserRight(roles = {"admin"})
public class UserController {
    @Autowired
    UserService userService;

    @Operation(tags = "分页查询",summary = "分页查询数据")
    @GetMapping("/")
    ResponseData<List<UserOutputDto>> query(@RequestParam(defaultValue = "1") Long pageIndex, @RequestParam(defaultValue = "10")Long pageSize,
                                            String username, String name){
        return userService.query(pageIndex,pageSize,username,name);
    }

    @Operation(tags = "查询所有数据",summary = "查询所有数据")
    @GetMapping("/queryAll")
    @UserRight(roles = {"admin","staff"})
    ResponseData<List<UserOutputDto>> queryAll(){
        return userService.queryAll();
    }

    @Operation(tags = "根据ID获取数据",summary = "根据ID获取数据详情")
    @GetMapping("/{id}")
    ResponseData<UserOutputDto> getById(@PathVariable Integer id){
        return userService.getById(id);
    }

    @Operation(tags = "创建",summary = "创建一条新数据")
    @PostMapping("/")
    ResponseData<UserOutputDto> create(@RequestBody @Validated UserCreateInputDto inputDto){
        return userService.create(inputDto);
    }

    @Operation(tags = "修改",summary = "修改一条已存在数据")
    @PutMapping("/")
    ResponseData<UserOutputDto> update(@RequestBody  @Validated UserUpdateInputDto inputDto){
        return  userService.update(inputDto);
    }

    @Operation(tags = "删除",summary = "删除一条已存在数据")
    @DeleteMapping("/{id}")
    ResponseData<Boolean> delete(@PathVariable Integer id){
        return userService.delete(id);
    }

    @Operation(tags = "重置密码",summary = "重置密码")
    @PostMapping("/resetPassword")
    public ResponseData<Boolean> resetPassword(@RequestBody  @Validated LoginDto loginDto){
        return userService.resetPassord(loginDto);
    }
}
