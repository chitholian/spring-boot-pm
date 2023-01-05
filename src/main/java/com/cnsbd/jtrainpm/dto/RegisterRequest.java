package com.cnsbd.jtrainpm.dto;

import com.cnsbd.jtrainpm.annotation.UniqueEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    @UniqueEmail(message = "Email already exists")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;
}
