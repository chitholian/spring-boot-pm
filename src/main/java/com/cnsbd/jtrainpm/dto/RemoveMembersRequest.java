package com.cnsbd.jtrainpm.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RemoveMembersRequest {
    @NotNull(message = "User-id list is required")
    private List<Long> userIds;
}
