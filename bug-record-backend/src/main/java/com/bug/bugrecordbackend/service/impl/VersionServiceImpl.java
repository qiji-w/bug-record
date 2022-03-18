package com.bug.bugrecordbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bug.bugrecordbackend.dto.common.ResponseData;
import com.bug.bugrecordbackend.dto.input.version.VersionUpdateInputDto;
import com.bug.bugrecordbackend.dto.output.version.VersionOutputDto;
import com.bug.bugrecordbackend.dto.input.version.VersionCreateInputDto;
import com.bug.bugrecordbackend.entity.VersionEntity;
import com.bug.bugrecordbackend.mapper.VersionMapper;
import com.bug.bugrecordbackend.service.VersionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VersionServiceImpl extends ServiceImpl<VersionMapper, VersionEntity> implements VersionService {
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseData<List<VersionOutputDto>> query(Long pageIndex, Long pageSize, String name) {
        ResponseData<List<VersionOutputDto>> responseData = new ResponseData<>();

        try {
            LambdaQueryWrapper<VersionEntity> queryWrapper = new LambdaQueryWrapper<>();
            if(name !=null){
                queryWrapper.like(VersionEntity::getName,name);
            }
            queryWrapper.orderByDesc(VersionEntity::getId);
            IPage<VersionEntity> queryPage = new Page<>(pageIndex,pageSize);
            queryPage = this.page(queryPage, queryWrapper);

            List<VersionOutputDto> versionOutputDtos =  queryPage.getRecords().stream().map(s->modelMapper.map(s,VersionOutputDto.class)).collect(Collectors.toList());

            responseData = ResponseData.success(versionOutputDtos,queryPage.getTotal());
        } catch (Exception ex) {
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }

    @Override
    public ResponseData<List<VersionOutputDto>> queryAll() {
        ResponseData<List<VersionOutputDto>> responseData;

        try {
            List <VersionEntity> entities = this.list();
            List <VersionOutputDto> outputDtos = entities.stream().map(s -> modelMapper.map(s, VersionOutputDto.class)).collect(Collectors.toList());

            responseData = ResponseData.success(outputDtos);
        }catch (Exception ex){
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }
        return responseData;
    }

    @Override
    public ResponseData <VersionOutputDto> getById(Integer id) {
        ResponseData<VersionOutputDto> responseData;
        try {
            List<String> checkMsgs = new ArrayList<>();
            VersionEntity versionEntity = super.getById(id); //获取数据
            if(versionEntity == null){
                checkMsgs.add(String.format("ID为[%s]的数据不存在",id));
            }
            if(checkMsgs.size()>0){
                responseData = new ResponseData<>();
                responseData.setCode(1);
                responseData.setMessage(checkMsgs.stream().collect(Collectors.joining(",")));

                return responseData;
            }

            VersionOutputDto outputDto = modelMapper.map(versionEntity,VersionOutputDto.class);

            responseData = ResponseData.success(outputDto);
        }catch (Exception ex){
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }

    @Override
    public ResponseData <VersionOutputDto> create(VersionCreateInputDto inputDto) {
        ResponseData<VersionOutputDto> responseData;

        try {
            List<String> checkMsgs = new ArrayList<>();
            LambdaQueryWrapper<VersionEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(VersionEntity::getName,inputDto.getName().trim());
            VersionEntity userEntity = this.getOne(queryWrapper,false);
            if(userEntity!=null){
                checkMsgs.add(String.format("版本[%s]已存在",String.join(",",inputDto.getName())));
            }
            if(checkMsgs.size()>0){
                String checkMsg = checkMsgs.stream().collect(Collectors.joining(","));
                responseData = new ResponseData<>();
                responseData.setCode(1);
                responseData.setMessage(checkMsg);

                return responseData;
            }

            VersionEntity entity = modelMapper.map(inputDto,VersionEntity.class);

            this.save(entity);

            responseData = ResponseData.success();
        }catch (Exception ex){
            ex.printStackTrace();
            responseData = ResponseData.failure(ex.toString());
        }

        return responseData;
    }

    @Override
    public ResponseData <VersionOutputDto> update(VersionUpdateInputDto inputDto) {
        ResponseData<VersionOutputDto> responseData;
        try {
            List<String> checkMsgs = new ArrayList<>();
            VersionEntity existsEntity = super.getById(inputDto.getId());
            if(existsEntity == null){
                checkMsgs.add(0,String.format("版本ID[%d]不存在",inputDto.getId()));
            }else {
                LambdaQueryWrapper<VersionEntity> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.ne(VersionEntity::getId, inputDto.getId());
                queryWrapper.eq(VersionEntity::getName, inputDto.getName().trim());
                VersionEntity userEntity = this.getOne(queryWrapper, false);
                if (userEntity != null) {
                    checkMsgs.add(String.format("版本[%s]已存在", String.join(",", inputDto.getName())));
                }
            }
            if(checkMsgs.size()>0){
                String checkMsg = checkMsgs.stream().collect(Collectors.joining(","));
                responseData = new ResponseData<>();
                responseData.setCode(1);
                responseData.setMessage(checkMsg);

                return responseData;
            }

            VersionEntity entity = modelMapper.map(inputDto,VersionEntity.class);

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
            VersionEntity existsEntity = super.getById(id);
            if(existsEntity == null){
                checkMsgs.add(0,String.format("版本ID[%d]不存在",id));
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
}
