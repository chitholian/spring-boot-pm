package com.cnsbd.jtrainpm.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {
    @NotBlank(message = "Username/Email is required")
    private String username;
    @NotEmpty(message = "Password is required")
    private String password;
}
