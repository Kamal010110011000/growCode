package com.growCode.growCode.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "name can't be empty")
    private String name;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "email is required")
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private long phoneNo;

    private Date joiningDate = new Date(System.currentTimeMillis());
    
    private boolean isActive = true;

    @OneToOne(mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Otp otp;

    @OneToOne( mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dashboard dashboard;

    public User() {
    }

    public User(long id, @NotBlank(message = "name can't be empty") String name,
            @NotBlank(message = "password is required") String password,
            @NotBlank(message = "email is required") String email,
            @NotBlank(message = "phone number is required") long phoneNo, Date joiningDate, boolean isActive) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNo = phoneNo;
        this.joiningDate = joiningDate;
        this.isActive = isActive;
    }



    public User(long id, @NotBlank(message = "name can't be empty") String name,
            @NotBlank(message = "email is required") String email,
            @NotBlank(message = "phone number is required") long phoneNo, Date joiningDate, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.joiningDate = joiningDate;
        this.isActive = isActive;
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



    public long getPhoneNo() {
        return phoneNo;
    }



    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }



    public Date getJoiningDate() {
        return joiningDate;
    }



    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }



    public boolean isActive() {
        return isActive;
    }



    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Otp getOtp() {
        return otp;
    }

    public void setOtp(Otp otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", id=" + id + ", isActive=" + isActive + ", joiningDate=" + joiningDate
                + ", name=" + name + ", password=" + password + ", phoneNo=" + phoneNo + "]";
    }
    
}
