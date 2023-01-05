package com.cnsbd.jtrainpm.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateProjectRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String intro;

    private String description;

    private Boolean startNow = false;
}
