package com.cnsbd.jtrainpm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Date;

public interface IUserProject {
    Long getId();

    Long getOwnerId();

    Long getMemberCount();

    String getOwnerName();

    String getProjectName();

    Integer getStatusId();

    String getStatusName();

    @JsonFormat(pattern = "dd-MMM-yyyy")
    LocalDateTime getStartDate();
    @JsonFormat(pattern = "dd-MMM-yyyy")
    LocalDateTime getEndDate();
    @JsonFormat(pattern = "dd-MMM-yyyy hh:mm a")
    LocalDateTime getCreatedAt();

    String getIntro();
    String getDescription();
}
