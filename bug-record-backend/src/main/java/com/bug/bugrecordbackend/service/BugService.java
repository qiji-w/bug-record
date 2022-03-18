package com.bug.bugrecordbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bug.bugrecordbackend.entity.BugEntity;
import com.bug.bugrecordbackend.dto.common.ResponseData;
import com.bug.bugrecordbackend.dto.input.bug.BugCreateInputDto;
import com.bug.bugrecordbackend.dto.input.bug.BugUpdateInputDto;
import com.bug.bugrecordbackend.dto.output.bug.BugOutputDto;

import java.util.List;

/**
 * 版本服务
 */
public interface BugService extends IService<BugEntity> {

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @param title
     * @param description
     * @param developerId
     * @return
     */
    ResponseData<List<BugOutputDto>> query(Long pageIndex,Long pageSize,String title,String description,Integer developerId);

    /**
     * 根据Id获取
     * @param id
     * @return
     */
    ResponseData<BugOutputDto> getById(Integer id);

    /**
     * 创建
     * @param inputDto
     * @return
     */
    ResponseData<BugOutputDto> create(BugCreateInputDto inputDto);

    /**
     * 修改
     * @param inputDto
     * @return
     */
    ResponseData<BugOutputDto> update(BugUpdateInputDto inputDto);

    /**
     * 删除
     * @param id
     */
    ResponseData<Boolean> delete(Integer id);
}
