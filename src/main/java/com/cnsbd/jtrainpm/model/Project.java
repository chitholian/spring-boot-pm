package com.cnsbd.jtrainpm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime startDateTime;

    @Column
    private LocalDateTime endDateTime;

    @ManyToOne
    private ProjectStatus status;

    @Column
    private LocalDateTime createdAt;

    @ManyToOne
    private User owner;

    @ManyToMany
    private List<User> members;
}
