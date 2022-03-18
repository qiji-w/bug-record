package com.bug.bugrecordbackend.controller;

import com.bug.bugrecordbackend.dto.common.ResponseData;
import com.bug.bugrecordbackend.dto.output.version.VersionOutputDto;
import com.bug.bugrecordbackend.annotation.UserRight;
import com.bug.bugrecordbackend.dto.input.version.VersionCreateInputDto;
import com.bug.bugrecordbackend.dto.input.version.VersionUpdateInputDto;
import com.bug.bugrecordbackend.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "版本接口")
@RestController
@RequestMapping("/version")
@UserRight(roles = {"admin"})
public class VersionController {
    @Autowired
    VersionService versionService;

    @Operation(tags = "分页查询",summary = "分页查询数据")
    @GetMapping("/")
    ResponseData<List<VersionOutputDto>> query(@RequestParam(defaultValue = "1") Long pageIndex, @RequestParam(defaultValue = "10")Long pageSize,
                                               String name){
        return versionService.query(pageIndex,pageSize,name);
    }

    @Operation(tags = "查询所有数据",summary = "查询所有数据")
    @GetMapping("/queryAll")
    @UserRight(roles = {"admin","staff"})
    ResponseData<List<VersionOutputDto>> queryAll(){
        return versionService.queryAll();
    }

    @Operation(tags = "根据ID获取数据",summary = "根据ID获取数据详情")
    @GetMapping("/{id}")
    ResponseData<VersionOutputDto> getById(@PathVariable Integer id){
        return versionService.getById(id);
    }

    @Operation(tags = "创建",summary = "创建一条新数据")
    @PostMapping("/")
    ResponseData<VersionOutputDto> create(@RequestBody @Validated VersionCreateInputDto inputDto){
        return versionService.create(inputDto);
    }

    @Operation(tags = "修改",summary = "修改一条已存在数据")
    @PutMapping("/")
    ResponseData<VersionOutputDto> update(@RequestBody @Validated VersionUpdateInputDto inputDto){
        return  versionService.update(inputDto);
    }

    @Operation(tags = "删除",summary = "删除一条已存在数据")
    @DeleteMapping("/{id}")
    ResponseData<Boolean> delete(@PathVariable Integer id){
        return versionService.delete(id);
    }
}
