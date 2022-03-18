package com.bug.bugrecordbackend.dto.input.bug;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BaseBugInputDto {
    @NotNull(message = "项目不能为空")
    @Length(min = 2,max = 20,message = "项目长度必须在2-20个字符之间")
    private String project;
    @NotNull(message = "模块不能为空")
    @Length(min = 2,max = 20,message = "模块长度必须在2-20个字符之间")
    private String module;
    @NotNull(message = "BUG标题不能为空")
    @Length(min = 2,max = 20,message = "BUG标题长度必须在2-20个字符之间")
    private String title;
    @NotNull(message = "优先级不能为空")
    private String priority;
    @NotNull(message = "严重程度不能为空")
    private String severity;
    @NotNull(message = "开发人员ID不能为空")
    private Integer developerId;
    private String developerName;
    private String description;
    @NotEmpty(message = "受影响版本不能为空")
    private List<String> versions;
}
