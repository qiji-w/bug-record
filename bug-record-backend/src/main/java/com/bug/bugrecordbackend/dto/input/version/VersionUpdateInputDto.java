package com.bug.bugrecordbackend.dto.input.version;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class VersionUpdateInputDto {
    private Integer id;
    @NotNull(message = "版本不能为空")
    @Length(min = 5,max = 20,message = "版本长度必须在5-20个字符之间")
    private String name;
    @NotNull(message = "描述不能为空")
    private String description;

}
