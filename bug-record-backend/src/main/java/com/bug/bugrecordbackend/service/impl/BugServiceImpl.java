package com.bug.bugrecordbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bug.bugrecordbackend.dto.common.ResponseData;
import com.bug.bugrecordbackend.dto.input.bug.BugCreateInputDto;
import com.bug.bugrecordbackend.entity.BugEntity;
import com.bug.bugrecordbackend.entity.UserEntity;
import com.bug.bugrecordbackend.service.BugService;
import com.bug.bugrecordbackend.service.UserService;
import com.bug.bugrecordbackend.dto.input.bug.BugUpdateInputDto;
import com.bug.bugrecordbackend.dto.output.bug.BugOutputDto;
import com.bug.bugrecordbackend.entity.VersionEntity;
import com.bug.bugrecordbackend.mapper.BugMapper;
import com.bug.bugrecordbackend.service.VersionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BugServiceImpl extends ServiceImpl<BugMapper, BugEntity> implements BugService {
    @Autowired
    VersionService versionService;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    //允许配置的优先级
    private final String[] PRIORITIES = {"High","Normal","Low"};
    //允许配置的严重程度
    private final String[] SEVERITIES = {"Critical","Major","Normal","Trivial"};

    @Override
    public ResponseData<List<BugOutputDto>> query(Long pageIndex, Long pageSize, String title, String description, Integer developerId) {
        ResponseData<List<BugOutputDto>> responseData = new ResponseData<>();

        try {
            LambdaQueryWrapper<BugEntity> queryWrapper = new LambdaQueryWrapper<>();
            if (title != null) {
                queryWrapper.like(BugEntity::getTitle, title);
            }
            if (description != null) {
                queryWrapper.like(BugEntity::getDescription, description);
            }
            if (developerId != null) {
                queryWrapper.eq(BugEntity::getDeveloperId, developerId);
            }
            queryWrapper.orderByDesc(BugEntity::getId);
            IPage<BugEntity> queryPage = new Page<>(pageIndex, pageSize);
            queryPage = this.page(queryPage, queryWrapper);
            List<BugEntity> bugEntities = queryPage.getRecords();

            List<BugOutputDto> bugOutputDtos = bugEntities.stream().map(s -> modelMapper.map(s, BugOutputDto.class)).collect(Collectors.toList());

            responseData = ResponseData.success(bugOutputDtos, queryPage.getTotal());
        } catch (Exception ex) {
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }

    @Override
    public ResponseData<BugOutputDto> getById(Integer id) {
        ResponseData<BugOutputDto> responseData;
        try {
            List<String> checkMsgs = new ArrayList<>();
            BugEntity bugEntity = super.getById(id);
            if (bugEntity == null) {
                checkMsgs.add(String.format("ID为[%s]的数据不存在", id));
            }
            if (checkMsgs.size() > 0) {
                responseData = new ResponseData<>();
                responseData.setCode(1);
                responseData.setMessage(checkMsgs.stream().collect(Collectors.joining(",")));

                return responseData;
            }

            BugOutputDto bugOutputDto = modelMapper.map(bugEntity, BugOutputDto.class);

            responseData = ResponseData.success(bugOutputDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }

    @Override
    public ResponseData<BugOutputDto> create(BugCreateInputDto inputDto) {
        ResponseData<BugOutputDto> responseData;
        try {
            List<String> checkMsgs = new ArrayList<>();
            if (inputDto.getTitle() != null && inputDto.getTitle().isEmpty() == false) {
                LambdaQueryWrapper<BugEntity> bugEntityQueryWrapper = new LambdaQueryWrapper<>();
                bugEntityQueryWrapper.eq(BugEntity::getTitle, inputDto.getTitle());
                List<BugEntity> existUserEntities = this.list(bugEntityQueryWrapper);
                if (existUserEntities != null && existUserEntities.size() > 0) {
                    checkMsgs.add(String.format("标题[%s]已经存在", inputDto.getTitle()));
                }
            }
            if(Arrays.asList(PRIORITIES).contains(inputDto.getPriority()) == false){
                checkMsgs.add(String.format("优先级[%s]不存在",inputDto.getPriority()));
            }
            if(Arrays.asList(SEVERITIES).contains(inputDto.getSeverity()) == false){
                checkMsgs.add(String.format("严重程度[%s]不存在",inputDto.getSeverity()));
            }
            List<UserEntity> userEntities = this.userService.list();
            if(userEntities.stream().anyMatch(s->s.getId().intValue() == inputDto.getDeveloperId().intValue()) == false){
                checkMsgs.add(String.format("开发人员ID[%d]不存在",inputDto.getDeveloperId()));
            }
            List<VersionEntity> versionEntities = versionService.list();
            List<String> versions = inputDto.getVersions();
            if (versions != null && versions.size() > 0) {
                List<String> nonExistsVersions = versions.stream().filter(s -> !versionEntities.stream().anyMatch(t -> t.getName().equalsIgnoreCase(s))).collect(Collectors.toList());
                if (nonExistsVersions != null && nonExistsVersions.size() > 0) {
                    checkMsgs.add(String.format("关联版本[%s]不存在", String.join(",", nonExistsVersions)));
                }
            }
            if (checkMsgs.size() > 0) {
                responseData = new ResponseData<>();
                responseData.setCode(1);
                responseData.setMessage(checkMsgs.stream().collect(Collectors.joining(",")));

                return responseData;
            }

            BugEntity bugEntity = modelMapper.map(inputDto, BugEntity.class);

            UserEntity userEntity = userService.getById((Serializable) bugEntity.getDeveloperId());
            if(userEntity!=null){
                bugEntity.setDeveloperName(userEntity.getName());
            }

            this.save(bugEntity);

            responseData = ResponseData.success();
        } catch (Exception ex) {
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }

    @Override
    public ResponseData<BugOutputDto> update(BugUpdateInputDto inputDto) {
        ResponseData<BugOutputDto> responseData;
        try {
            List<String> checkMsgs = new ArrayList<>();
            BugEntity existsBugEntity = super.getById(inputDto.getId());
            if (existsBugEntity == null) {
                checkMsgs.add(0, String.format("Bug ID[%d]不存在", inputDto.getId()));
            } else {
                if (inputDto.getTitle() != null && inputDto.getTitle().isEmpty() == false) {
                    LambdaQueryWrapper<BugEntity> bugEntityQueryWrapper = new LambdaQueryWrapper<>();
                    bugEntityQueryWrapper.eq(BugEntity::getTitle, inputDto.getTitle());
                    bugEntityQueryWrapper.ne(BugEntity::getId, inputDto.getId());
                    List<BugEntity> existUserEntities = this.list(bugEntityQueryWrapper);
                    if (existUserEntities != null && existUserEntities.size() > 0) {
                        checkMsgs.add(String.format("标题[%s]已经存在", inputDto.getTitle()));
                    }
                }
                if(Arrays.asList(PRIORITIES).contains(inputDto.getPriority()) == false){
                    checkMsgs.add(String.format("优先级[%s]不存在",inputDto.getPriority()));
                }
                if(Arrays.asList(SEVERITIES).contains(inputDto.getSeverity()) == false){
                    checkMsgs.add(String.format("严重程度[%s]不存在",inputDto.getSeverity()));
                }
                List<UserEntity> userEntities = this.userService.list();
                if(userEntities.stream().anyMatch(s->s.getId().intValue() == inputDto.getDeveloperId().intValue()) == false){
                    checkMsgs.add(String.format("开发人员ID[%d]不存在",inputDto.getDeveloperId()));
                }
                List<VersionEntity> versionEntities = versionService.list();
                List<String> versions = inputDto.getVersions();
                if (versions != null && versions.size() > 0) {
                    List<String> nonExistsVersions = versions.stream().filter(s -> !versionEntities.stream().filter(t -> t.getName() != null).anyMatch(t -> t.getName().equalsIgnoreCase(s))).collect(Collectors.toList());
                    if (nonExistsVersions != null && nonExistsVersions.size() > 0) {
                        checkMsgs.add(String.format("关联版本[%s]不存在", String.join(",", nonExistsVersions)));
                    }
                }
            }
            if (checkMsgs.size() > 0) {
                String checkMsg = checkMsgs.stream().collect(Collectors.joining(","));
                responseData = new ResponseData<>();
                responseData.setCode(1);
                responseData.setMessage(checkMsg);

                return responseData;
            }

            BugEntity bugEntity = modelMapper.map(inputDto, BugEntity.class);

            UserEntity userEntity = userService.getById((Serializable) bugEntity.getDeveloperId());
            if(userEntity!=null){
                bugEntity.setDeveloperName(userEntity.getName());
            }

            this.updateById(bugEntity);

            responseData = ResponseData.success();
        } catch (Exception ex) {
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }

    @Override
    public ResponseData<Boolean> delete(Integer id) {
        ResponseData<Boolean> responseData;
        try {
            List<String> checkMsgs = new ArrayList<>();
            BugEntity existsBugEntity = super.getById(id);
            if (existsBugEntity == null) {
                checkMsgs.add(0, String.format("Bug ID[%d]不存在", id));
            }
            if (checkMsgs.size() > 0) {
                String checkMsg = checkMsgs.stream().collect(Collectors.joining(","));
                responseData = new ResponseData<>();
                responseData.setCode(1);
                responseData.setMessage(checkMsg);

                return responseData;
            }

            Boolean result = this.removeById(id);

            responseData = ResponseData.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }
}

