package com.bug.bugrecordbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bug.bugrecordbackend.service.UserService;
import com.bug.bugrecordbackend.dto.common.ResponseData;
import com.bug.bugrecordbackend.dto.input.account.LoginDto;
import com.bug.bugrecordbackend.dto.input.account.RegisterDto;
import com.bug.bugrecordbackend.dto.input.user.UserCreateInputDto;
import com.bug.bugrecordbackend.dto.input.user.UserUpdateInputDto;
import com.bug.bugrecordbackend.dto.output.account.LoginOutputDto;
import com.bug.bugrecordbackend.dto.output.user.UserOutputDto;
import com.bug.bugrecordbackend.entity.UserEntity;
import com.bug.bugrecordbackend.mapper.UserMapper;
import com.bug.bugrecordbackend.util.PasswordUtil;
import com.bug.bugrecordbackend.util.SessionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    SessionUtil sessionUtil;

    //允许配置的角色
    private final String[] ROLES = {"admin","staff"};

    @Override
    public ResponseData<List<UserOutputDto>> query(Long pageIndex,Long pageSize,String username,String name) {
        ResponseData<List<UserOutputDto>> responseData = new ResponseData<>();

        try {
            LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
            if(username!=null){
                queryWrapper.like(UserEntity::getUsername,username);
            }
            if(name!=null){
                queryWrapper.like(UserEntity::getName,name);
            }
            queryWrapper.orderByDesc(UserEntity::getId);
            IPage<UserEntity> queryPage = new Page<>(pageIndex, pageSize);
            queryPage = this.page(queryPage, queryWrapper);

            List<UserOutputDto> outputDtos =  queryPage.getRecords().stream().map(s->modelMapper.map(s,UserOutputDto.class)).collect(Collectors.toList());

            responseData = ResponseData.success(outputDtos,queryPage.getTotal());
        } catch (Exception ex) {
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }

    @Override
    public ResponseData <List <UserOutputDto>> queryAll() {
        ResponseData<List<UserOutputDto>> responseData;

        try {
            List <UserEntity> entities = this.list();
            List <UserOutputDto> outputDtos = entities.stream().map(s -> modelMapper.map(s, UserOutputDto.class)).collect(Collectors.toList());

            responseData = ResponseData.success(outputDtos);
        }catch (Exception ex){
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }
        return responseData;
    }

    @Override
    public ResponseData <UserOutputDto> getById(Integer id) {
        ResponseData<UserOutputDto> responseData;
        try {
            List<String> checkMsgs = new ArrayList<>();
            UserEntity userEntity = super.getById(id);  //获取数据
            if(userEntity == null){
                checkMsgs.add(String.format("ID为[%s]的数据不存在",id));
            }
            if(checkMsgs.size()>0){
                responseData = new ResponseData<>();
                responseData.setCode(1);
                responseData.setMessage(checkMsgs.stream().collect(Collectors.joining(",")));

                return responseData;
            }

            UserOutputDto outputDto = modelMapper.map(userEntity,UserOutputDto.class);

            responseData = ResponseData.success(outputDto);
        }catch (Exception ex){
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }
        return responseData;
    }

    @Override
    public ResponseData <UserOutputDto> create(UserCreateInputDto inputDto) {
        ResponseData<UserOutputDto> responseData;
        try {
            List<String> checkMsgs = new ArrayList<>();
            LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserEntity::getUsername,inputDto.getUsername().trim());
            UserEntity userEntity = this.getOne(queryWrapper,false);
            if(userEntity!=null){
                checkMsgs.add(String.format("用户名[%s]已存在",String.join(",",inputDto.getUsername())));
            }
            List<String> nonExistRoles = inputDto.getRoles().stream().filter(s->Arrays.asList(ROLES).stream().filter(t->t.equalsIgnoreCase(s)).count()<=0).collect(Collectors.toList());
            if(nonExistRoles!=null && nonExistRoles.size()>0){
                checkMsgs.add(String.format("所属角色[%s]不存在",String.join(",",nonExistRoles)));
            }
            if(checkMsgs.size()>0){
                String checkMsg = checkMsgs.stream().collect(Collectors.joining(","));
                responseData = new ResponseData<>();
                responseData.setCode(1);
                responseData.setMessage(checkMsg);

                return responseData;
            }

            UserEntity entity = modelMapper.map(inputDto,UserEntity.class);

            String password = PasswordUtil.encrypt(inputDto.getPassword(),inputDto.getUsername());
            entity.setPassword(password);

            this.save(entity);

            responseData = ResponseData.success();
        }catch (Exception ex){
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }

    @Override
    public ResponseData <UserOutputDto> update(UserUpdateInputDto inputDto) {
        ResponseData<UserOutputDto> responseData;
        try {
            List<String> checkMsgs = new ArrayList<>();
            UserEntity existsEntity = super.getById(inputDto.getId());
            if(existsEntity == null){
                checkMsgs.add(0,String.format("用户ID[%d]不存在",inputDto.getId()));
            }else {
                //验证用户名是否存在
                LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(UserEntity::getUsername, inputDto.getUsername().trim());
                queryWrapper.ne(UserEntity::getId, inputDto.getId());
                UserEntity userEntity = this.getOne(queryWrapper, false);
                if (userEntity != null) {
                    checkMsgs.add(String.format("用户名[%s]已存在", String.join(",", inputDto.getUsername())));
                }
                //验证角色是否存在
                List<String> nonExistRoles = inputDto.getRoles().stream().filter(s->Arrays.asList(ROLES).stream().filter(t->t.equalsIgnoreCase(s)).count()<=0).collect(Collectors.toList());
                if(nonExistRoles!=null && nonExistRoles.size()>0){
                    checkMsgs.add(String.format("所属角色[%s]不存在",String.join(",",nonExistRoles)));
                }
            }
            if(checkMsgs.size()>0){
                String checkMsg = checkMsgs.stream().collect(Collectors.joining(","));
                responseData = new ResponseData<>();
                responseData.setCode(1);
                responseData.setMessage(checkMsg);

                return responseData;
            }

            UserEntity entity = modelMapper.map(inputDto,UserEntity.class);

            this.updateById(entity);

            responseData = ResponseData.success();
        }catch (Exception ex){
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }

    @Override
    public ResponseData <Boolean> delete(Integer id) {
        ResponseData<Boolean> responseData;
        try {
            List<String> checkMsgs = new ArrayList<>();
            UserEntity existsEntity = super.getById(id);
            if(existsEntity == null){
                checkMsgs.add(0,String.format("用户ID[%d]不存在",id));
            }
            if(checkMsgs.size()>0){
                String checkMsg = checkMsgs.stream().collect(Collectors.joining(","));
                responseData = new ResponseData<>();
                responseData.setCode(1);
                responseData.setMessage(checkMsg);

                return responseData;
            }

            Boolean result = this.removeById(id);

            responseData = ResponseData.success(result);
        }catch (Exception ex){
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }

    @Override
    public ResponseData<LoginOutputDto> login(LoginDto loginDto) {

        ResponseData<LoginOutputDto> responseData;

        List<String> checkMsgs = new ArrayList<>();
        if(loginDto.getUsername()==null || loginDto.getUsername().trim().isEmpty() ==true){
            checkMsgs.add("用户名不能为空");
        }
        if(loginDto.getPassword()==null || loginDto.getPassword().trim().isEmpty() ==true){
            checkMsgs.add("密码不能为空");
        }
        if(checkMsgs.size()>0){
            String checkMsg = checkMsgs.stream().collect(Collectors.joining(","));
            responseData = new ResponseData<>();
            responseData.setCode(1);
            responseData.setMessage(checkMsg);

            return responseData;
        }

        try{
            LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserEntity::getUsername, loginDto.getUsername());
            UserEntity userEntity = this.getOne(queryWrapper,false);
            if(userEntity !=null){
                if(userEntity.getPassword().equalsIgnoreCase(PasswordUtil.encrypt(loginDto.getPassword(),loginDto.getUsername()))){
                    LoginOutputDto loginOutputDto = modelMapper.map(userEntity,LoginOutputDto.class);
                    responseData = ResponseData.success(loginOutputDto);

                    SessionUtil.CurrentUser currentUser = new SessionUtil.CurrentUser();
                    currentUser.setUserEntity(userEntity);
                    sessionUtil.setCurrentUser(currentUser);
                }else{
                    responseData = new ResponseData<>();
                    responseData.setCode(3);
                    responseData.setMessage("密码不正确");
                }
            }else{
                responseData = new ResponseData<>();
                responseData.setCode(2);
                responseData.setMessage("用户名不存在");
            }

        }catch (Exception ex){
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }
        return responseData;
    }

    @Override
    public ResponseData<Boolean> register(RegisterDto registerDto) {
        ResponseData<Boolean> responseData;

        List<String> checkMsgs = new ArrayList<>();
        if(registerDto.getUsername()==null || registerDto.getUsername().trim().isEmpty() ==true){
            checkMsgs.add("用户名不能为空");
        }
        if(registerDto.getPassword()==null || registerDto.getPassword().trim().isEmpty() ==true){
            checkMsgs.add("密码不能为空");
        }
        if(checkMsgs.size()>0){
            String checkMsg = checkMsgs.stream().collect(Collectors.joining(","));
            responseData = new ResponseData<>();
            responseData.setCode(1);
            responseData.setMessage(checkMsg);

            return responseData;
        }

        try{
            LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserEntity::getUsername, registerDto.getUsername());
            UserEntity existUser = this.getOne(queryWrapper,false);
            if(existUser ==null){
                UserEntity userEntity = modelMapper.map(registerDto,UserEntity.class);
                String password = PasswordUtil.encrypt(registerDto.getPassword(),registerDto.getUsername());
                userEntity.setPassword(password);
                Boolean result = this.save(userEntity);

                responseData = ResponseData.success(result);
            }else{
                responseData = new ResponseData<>();
                responseData.setCode(2);
                responseData.setMessage("用户名已存在");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }
        return responseData;
    }

    @Override
    public ResponseData<Boolean> resetPassord(LoginDto loginDto) {
        ResponseData<Boolean> responseData;
        try {
            List<String> checkMsgs = new ArrayList<>();
            if(loginDto.getUsername()==null || loginDto.getUsername().trim().isEmpty() ==true){
                checkMsgs.add("用户名不能为空");
            }
            if(loginDto.getPassword()==null || loginDto.getPassword().trim().isEmpty() ==true){
                checkMsgs.add("密码不能为空");
            }
            if(checkMsgs.size()>0){
                String checkMsg = checkMsgs.stream().collect(Collectors.joining(","));
                responseData = new ResponseData<>();
                responseData.setCode(1);
                responseData.setMessage(checkMsg);

                return responseData;
            }

            LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserEntity::getUsername,loginDto.getUsername());
            UserEntity userEntity = this.getOne(queryWrapper,false);
            if(userEntity !=null){
                String password = PasswordUtil.encrypt(loginDto.getPassword(),loginDto.getUsername());
                userEntity.setPassword(password);

                boolean success = this.updateById(userEntity);

                responseData = ResponseData.success(success);
            }else{
                responseData = ResponseData.failure("用户名不存在");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }
}
