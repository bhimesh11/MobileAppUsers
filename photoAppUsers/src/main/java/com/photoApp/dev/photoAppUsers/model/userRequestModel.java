package com.photoApp.dev.photoAppUsers.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class userRequestModel
{
public userRequestModel()
{

}
@NotNull(message = "FirstName should not be null")
@Size(min =2,message = "First name should be greater than 2 characters")
    private String firstName;

@NotNull(message = "Last Name should not be null")
@Size(min =2,message = "Last name should be greater than 2 characters")
    private String lastName;

@NotNull(message = "Password should be given ")
@Size(min = 8,max = 12,message = "Password should be greater or equal to 8 characters and less than 12 characters")
    private String password;

@NotNull(message =  "Email is mandatory feild")
@Email
    private String email;

    public userRequestModel(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
