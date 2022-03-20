package com.growCode.growCode.services;

import com.growCode.growCode.entity.Dashboard;
import com.growCode.growCode.entity.User;
import com.growCode.growCode.repo.DashboardRepository;
import com.growCode.growCode.repo.userRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService  {
    
    @Autowired
    private DashboardRepository dRepository;

    @Autowired
    private userRepository uRepository;

    public Dashboard myDashboard(String email){
        try {
            User user =  uRepository.findByEmail(email);
            return user.getDashboard();
        } catch (Exception e) {
            throw e;
        }
    }   

    public Dashboard updateDashboard(Long id, Dashboard dashboard){
        try {
            Dashboard dashboard2 = dRepository.getById(id);
            return dashboard2;
        } catch (Exception e) {
            throw e;
        }
    }
}
