package com.eskom.back.DTO;


import java.io.Serializable;
import java.util.Date;


public class userDTO implements Serializable {

    private String firstName;
    private String lastName;
    private String picture;
    private Date dob;
    private String phoneNumber;
    private String password;
    private String email;

    public userDTO(String firstName, String lastName, String picture, Date dob, String phoneNumber, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
    }

    public userDTO() {
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
