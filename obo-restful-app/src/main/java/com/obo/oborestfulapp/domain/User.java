package com.obo.oborestfulapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    private Long id;

    private String username;
    private String password;
    private boolean active;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
