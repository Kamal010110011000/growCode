package com.growCode.growCode.services;

import java.util.Random;

import com.growCode.growCode.entity.Otp;
import com.growCode.growCode.entity.User;
import com.growCode.growCode.repo.otpRepository;
import com.growCode.growCode.repo.userRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Autowired
    private otpRepository oRepository;

    @Autowired
    private userRepository uRepository;

    public int createOtp(int length){
        Random rand = new Random();
        int llim = (int)Math.pow(10, length);
        int ulim = (int)Math.pow(10, length+1) -1;
        System.out.println(llim+" "+ulim);
        return rand.ints(1, llim, ulim).findFirst().getAsInt();
    }

    public Otp save(long mobileNo) {
        try {
            User user = uRepository.findByPhoneNo(mobileNo);
            int otp = createOtp(6);
            Otp createdOtp = new Otp(otp, user);
            Otp savedOtp = oRepository.save(createdOtp);
            return savedOtp;
        } catch (Exception e) {
            throw e;
        }
    }

}