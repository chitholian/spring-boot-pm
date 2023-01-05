package com.cnsbd.jtrainpm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    Date getStartDate();
    @JsonFormat(pattern = "dd-MMM-yyyy")
    Date getEndDate();

    String getIntro();
    String getDescription();
}
