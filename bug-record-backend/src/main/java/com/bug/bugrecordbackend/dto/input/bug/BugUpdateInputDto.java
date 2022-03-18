package com.bug.bugrecordbackend.dto.input.bug;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class BugUpdateInputDto extends BaseBugInputDto {
    @NotNull(message = "ID不能为空")
    private Integer id;
}
