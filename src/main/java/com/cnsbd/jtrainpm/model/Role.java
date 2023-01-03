package com.cnsbd.jtrainpm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    public static final Integer ADMIN = 1;
    public static final Integer MANAGER = ADMIN + 1;
    public static final Integer DEVELOPER = MANAGER + 1;

    @Id
    @JsonProperty
    private Integer id;

    @Column
    @JsonProperty
    private String title;

    public static Role getDefault() {
        Role role = new Role();
        role.id = DEVELOPER;
        role.title = "Developer";
        return role;
    }
}
