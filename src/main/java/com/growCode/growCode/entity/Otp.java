package com.growCode.growCode.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "otps")
public class Otp {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int otp;
    private Timestamp expTime = new Timestamp(System.currentTimeMillis() + 300000);
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Otp() {
    }

    public Otp(int otp, Timestamp expTime, Timestamp createdAt, User user) {
        this.otp = otp;
        this.expTime = expTime;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Otp(int otp, User user) {
        this.otp = otp;
        this.user = user;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public Timestamp getExpTime() {
        return expTime;
    }

    public void setExpTime(Timestamp expTime) {
        this.expTime = expTime;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Otp [createdAt=" + createdAt + ", expTime=" + expTime + ", otp=" + otp + ", user=" + user + "]";
    }   
    

}
