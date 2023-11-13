package com.photoApp.dev.photoAppUsers.database;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -9038404189645758923L;

    @Id
    @GeneratedValue

    private long Id;

    @Column(nullable = false,length = 50)
    private String firstName;
    @Column(nullable = false,length = 50)
    private String lastName;
    @Column(nullable = false,length = 120,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private String userId;
    @Column(nullable = false,unique = true)
    private String encryptedPassword;

    @ManyToMany(cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
            @JoinTable(name = "users-roles",
                    joinColumns = @JoinColumn(name="user-id", referencedColumnName = "Id"),
                    inverseJoinColumns = @JoinColumn(name = "roles_id",referencedColumnName = "Id"))
    Collection<roleEntity> roles;

    public void setRoles(Collection<roleEntity> roles) {
        this.roles = roles;
    }

    public Collection<roleEntity> getRoles() {
        return roles;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "Id=" + Id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                '}';
    }
}
