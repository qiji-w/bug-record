package com.bug.bugrecordbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bug.bugrecordbackend.dto.common.ResponseData;
import com.bug.bugrecordbackend.dto.input.account.LoginDto;
import com.bug.bugrecordbackend.dto.input.account.RegisterDto;
import com.bug.bugrecordbackend.dto.input.user.UserCreateInputDto;
import com.bug.bugrecordbackend.dto.input.user.UserUpdateInputDto;
import com.bug.bugrecordbackend.dto.output.account.LoginOutputDto;
import com.bug.bugrecordbackend.dto.output.user.UserOutputDto;
import com.bug.bugrecordbackend.entity.UserEntity;

import java.util.List;

/**
 * 用户服务
 */
public interface UserService extends IService<UserEntity> {
    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @param username
     * @param name
     * @return
     */
    ResponseData<List<UserOutputDto>> query(Long pageIndex,Long pageSize,String username,String name);

    /**
     * 查询所有数据
     * @return
     */
    ResponseData<List<UserOutputDto>> queryAll();

    /**
     * 根据Id获取
     * @param id
     * @return
     */
    ResponseData<UserOutputDto> getById(Integer id);

    /**
     * 创建
     * @param inputDto
     * @return
     */
    ResponseData<UserOutputDto> create(UserCreateInputDto inputDto);

    /**
     * 更新
     * @param inputDto
     * @return
     */
    ResponseData<UserOutputDto> update(UserUpdateInputDto inputDto);

    /**
     * 删除
     * @param id
     */
    ResponseData<Boolean> delete(Integer id);

    /**
     * 登录
     * @param loginDto
     * @return
     */
    ResponseData<LoginOutputDto> login(LoginDto loginDto);

    /**
     * 注册
     * @param registerDto
     * @return
     */
    ResponseData<Boolean> register(RegisterDto registerDto);

    /**
     * 重置密码
     * @param loginDto
     * @return
     */
    ResponseData<Boolean> resetPassord(LoginDto loginDto);
}
