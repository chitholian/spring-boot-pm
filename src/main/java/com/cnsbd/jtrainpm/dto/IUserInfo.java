package com.cnsbd.jtrainpm.dto;

public interface IUserInfo {
    Long getId();

    String getName();

    String getEmail();

    String getUsername();

    String getStatusName();

    Integer getStatusId();

    String getRoleName();

    Integer getRoleId();

    Long getProjectCount();

    Long getContribCount();
}
