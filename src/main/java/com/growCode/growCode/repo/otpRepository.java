package com.growCode.growCode.repo;

import com.growCode.growCode.entity.Otp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface otpRepository extends JpaRepository<Otp, Long> {
    
}
