package com.bug.bugrecordbackend.dto.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Paging implements Serializable {
    @NotNull(message="当前页号不能为空")
    private Long pageIndex;
    @NotNull(message="每页大小不能为空")
    private Long pageSize;
}
