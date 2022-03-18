package com.bug.bugrecordbackend.dto.input.bug;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


@Data
public class BugQueryInputDto implements Serializable {
    private String title;
    private String description;
    private Integer developerId;
}
