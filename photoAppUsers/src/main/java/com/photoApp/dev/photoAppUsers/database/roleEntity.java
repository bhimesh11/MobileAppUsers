package com.photoApp.dev.photoAppUsers.database;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name="roles")
public class roleEntity implements Serializable
{

    private static final long serialVersionUID = -4885072869486325720L;


    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false,length = 20)
    private String name ;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;


    @ManyToMany(cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities",
            joinColumns = @JoinColumn(name="roles-id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id",referencedColumnName = "Id"))
    private Collection<authorityEntity> authorities;

    public Collection<authorityEntity> getAuthorities() {
        return authorities;
    }

    public roleEntity(String name, Collection<authorityEntity> authorities)
    {
        this.name = name;
        this.authorities = authorities;
    }
    public roleEntity()
    {

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserEntity> users) {
        this.users = users;
    }
}
