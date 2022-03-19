package com.growCode.growCode.repo;

import com.growCode.growCode.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User, Long> {
    public User findByPhoneNo(long phoneNo);
    public User findByEmail(String email);
}

