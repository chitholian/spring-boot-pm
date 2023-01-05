package com.cnsbd.jtrainpm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @JsonIgnore
    @Column
    private String password;
    @Column
    private String username;

    @Column
    private String email;

    @Column
    private Boolean approved;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "owner")
    private List<Project> projects;

    @ManyToMany(mappedBy = "members")
    private List<Project> memberOfProjects;

    @ManyToOne
    private UserStatus status;
}
