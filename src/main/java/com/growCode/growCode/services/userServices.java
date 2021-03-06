package com.growCode.growCode.services;

import java.util.ArrayList;

import com.growCode.growCode.entity.Dashboard;
import com.growCode.growCode.entity.User;
import com.growCode.growCode.exceptions.NotFoundException;
import com.growCode.growCode.repo.DashboardRepository;
import com.growCode.growCode.repo.userRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userServices implements UserDetailsService{

    @Autowired
    private userRepository uRepository;

    @Autowired
    private DashboardRepository dashboardRepository;
    
    
    public User save(User user){
        try {
            String password = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(password);
            Dashboard dashboard = new Dashboard();
            dashboard.setTitle(user.getName()+" Dashboard");
            User savedUser = uRepository.save(user);
            dashboard.setUser(savedUser);
            dashboardRepository.save(dashboard);
            return savedUser;
        }catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<User> getAll()throws NotFoundException{
        try {
            ArrayList<User> users = (ArrayList<User>) uRepository.findAll();
            if(users.isEmpty()){
                throw new NotFoundException("Users Not Found!");
            }
            return users;
        }catch (Exception e) {
            throw e;
        }
    }
    
    public User getByEmail(String email) throws NotFoundException{
        User user = uRepository.findByEmail(email);
        return user;
    }
    
    public User getOne(Long id)throws NotFoundException{
        try {
            System.out.println(id);
            User user = (User) uRepository.getById(id);
            return user;
        }catch (Exception e) {
            throw e;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = uRepository.findByEmail(email);
        return  new org.springframework.security.core.userdetails.User(email, user.getPassword(), new ArrayList<>());
    }
  
    public String getUserName(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = null;
        if(principal instanceof UserDetails){
            email = ((UserDetails)principal).getUsername();
        }else{
            email = principal.toString();
        }
        return email;
    }
}
