package com.bug.bugrecordbackend.dto.input.version;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class VersionQueryInputDto implements Serializable {
    private String name;
}
