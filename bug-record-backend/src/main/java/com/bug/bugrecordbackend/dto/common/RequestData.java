package com.bug.bugrecordbackend.dto.common;

import com.bug.bugrecordbackend.dto.common.page.Paging;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestData<T> implements Serializable {
    private Paging page;
    private T conditions;
    private String orderBy;
}
