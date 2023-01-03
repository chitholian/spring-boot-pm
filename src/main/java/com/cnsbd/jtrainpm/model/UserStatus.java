package com.cnsbd.jtrainpm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class UserStatus {
    public static final Integer ACTIVE = 1;
    public static final Integer INACTIVE = ACTIVE + 1;

    @Id
    @JsonProperty
    private Integer id;

    @Column
    @JsonProperty
    private String title;

    public static UserStatus getDefault() {
        UserStatus status = new UserStatus();
        status.id = INACTIVE;
        status.title = "Inactive";
        return status;
    }
}
