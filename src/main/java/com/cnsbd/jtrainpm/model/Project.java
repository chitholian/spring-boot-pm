package com.cnsbd.jtrainpm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String intro;

    @Column
    private String description;

    @Column
    private Date startDateTime;

    @Column
    private Date endDateTime;

    @ManyToOne
    private ProjectStatus status;

    @ManyToOne
    private User owner;

    @ManyToMany
    private List<User> members;
}
