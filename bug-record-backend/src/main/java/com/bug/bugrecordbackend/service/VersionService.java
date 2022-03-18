package com.bug.bugrecordbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bug.bugrecordbackend.dto.common.ResponseData;
import com.bug.bugrecordbackend.dto.input.version.VersionCreateInputDto;
import com.bug.bugrecordbackend.dto.input.version.VersionUpdateInputDto;
import com.bug.bugrecordbackend.dto.output.version.VersionOutputDto;
import com.bug.bugrecordbackend.entity.VersionEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;

/**
 * 版本服务
 */
@CacheConfig(cacheNames = "version")
public interface VersionService extends IService<VersionEntity> {
    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @param name
     * @return
     */
    ResponseData<List<VersionOutputDto>> query(Long pageIndex, Long pageSize, String name);

    /**
     * 查询所有
     * @return
     */

    @Cacheable(key = "#root.methodName")
    ResponseData<List<VersionOutputDto>> queryAll();

    /**
     * 根据Id获取
     * @param id
     * @return
     */
    ResponseData<VersionOutputDto> getById(Integer id);

    /**
     * 创建
     * @param inputDto
     * @return
     */
    @CacheEvict(allEntries = true)
    ResponseData<VersionOutputDto> create(VersionCreateInputDto inputDto);

    /**
     * 更新
     * @param inputDto
     * @return
     */
    ResponseData<VersionOutputDto> update(VersionUpdateInputDto inputDto);

    /**
     * 删除
     * @param id
     */
    @CacheEvict(allEntries = true)
    ResponseData<Boolean> delete(Integer id);
}
