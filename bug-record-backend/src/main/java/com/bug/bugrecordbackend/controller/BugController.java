package com.bug.bugrecordbackend.controller;

import com.bug.bugrecordbackend.dto.common.ResponseData;
import com.bug.bugrecordbackend.dto.input.bug.BugCreateInputDto;
import com.bug.bugrecordbackend.service.BugService;
import com.bug.bugrecordbackend.annotation.UserRight;
import com.bug.bugrecordbackend.dto.input.bug.BugUpdateInputDto;
import com.bug.bugrecordbackend.dto.output.bug.BugOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Tag(name = "Bug接口")
@RestController
@RequestMapping("/bug")
@UserRight(roles = {"admin","staff"})
public class BugController {
    @Autowired
    BugService bugService;

    @Operation(tags = "分页查询",summary = "分页查询数据")
    @GetMapping("/")
    ResponseData<List<BugOutputDto>> query(@RequestParam(defaultValue = "1") Long pageIndex, @RequestParam(defaultValue = "10")Long pageSize,
                                           String title, String description, Integer developerId){
        return bugService.query(pageIndex,pageSize,title,description,developerId);
    }

    @Operation(tags = "根据ID获取数据",summary = "根据ID获取数据详情")
    @GetMapping("/{id}")
    ResponseData<BugOutputDto> getById(@PathVariable Integer id){
        return bugService.getById(id);
    }

    @Operation(tags = "创建",summary = "创建一条新数据")
    @PostMapping("/")
    ResponseData<BugOutputDto> create(@RequestBody  @Validated BugCreateInputDto bugCreateInputDto){
        return bugService.create(bugCreateInputDto);
    }

    @Operation(tags = "修改",summary = "修改一条已存在数据")
    @PutMapping("/")
    ResponseData<BugOutputDto> update(@RequestBody  @Validated BugUpdateInputDto bugInputDto){
        return  bugService.update(bugInputDto);
    }

    @Operation(tags = "删除",summary = "删除一条已存在数据")
    @DeleteMapping("/{id}")
    ResponseData<Boolean> delete(@PathVariable Integer id){
        return bugService.delete(id);
    }
}
