package com.growCode.growCode.services;

import com.growCode.growCode.entity.Dashboard;
import com.growCode.growCode.repo.DashboardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DashboardService  {
    
    @Autowired
    private DashboardRepository dRepository;

    public Dashboard save(Dashboard dashboard){
        try {
            Dashboard saveDashboard = dRepository.save(dashboard);
            return saveDashboard; 
        } catch (Exception e) {
            throw e;
        }
    }
}
