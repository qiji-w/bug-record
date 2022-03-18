package com.bug.bugrecordbackend.dto.input.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserUpdateInputDto {
    @NotNull(message = "ID不能为空")
    private Integer id;
    @NotNull(message = "用户名不能为空")
    @Length(min = 2,max = 20,message = "用户名长度必须在2-20个字符之间")
    private String username;
    private String password;
    @NotNull(message = "姓名不能为空")
    @Length(min = 2,max = 20,message = "姓名长度必须在5-50个字符之间")
    private String name;
    @NotNull(message = "角色不能为空")
    private List<String> roles;
    private String description;

}
