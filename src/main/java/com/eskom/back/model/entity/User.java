package com.eskom.back.model.entity;

import com.eskom.back.DTO.ROLE;
import com.eskom.back.DTO.userDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "`user`")
public class User {
    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    @CollectionTable(
            name = "roles",
            joinColumns = @JoinColumn(name = "user_email")
    )
    @Column(name = "user_role")
    private Set<ROLE> roles;

    public User() {
    }
    public User(userDTO user) {
        this.name = user.getFirstName();
        this.email = user.getEmail();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ROLE> getRoles() {
        return roles;
    }

    public void setRoles(Set<ROLE> roles) {
        this.roles = roles;
    }
}
