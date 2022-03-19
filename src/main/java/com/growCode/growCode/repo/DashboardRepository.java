package com.growCode.growCode.repo;

import com.growCode.growCode.entity.Dashboard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
    
}
