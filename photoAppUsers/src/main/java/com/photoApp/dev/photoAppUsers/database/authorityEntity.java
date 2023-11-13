package com.photoApp.dev.photoAppUsers.database;


import jakarta.persistence.*;
import javassist.SerialVersionUID;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "authorities")
public class authorityEntity implements Serializable
{
    private static final long serialVersionUID = -4885072869486325729L;



    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,length = 20)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Collection<roleEntity> roles;

    public authorityEntity()
    {

    }
    public authorityEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<roleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<roleEntity> roles) {
        this.roles = roles;
    }
}
