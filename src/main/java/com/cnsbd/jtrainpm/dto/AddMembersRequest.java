package com.cnsbd.jtrainpm.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddMembersRequest {
    @NotNull(message = "User-email list is required")
    private List<String> userEmails;
}
