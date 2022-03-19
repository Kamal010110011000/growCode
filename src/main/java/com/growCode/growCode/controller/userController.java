package com.growCode.growCode.controller;

import java.util.ArrayList;

import com.growCode.growCode.entity.AuthenticationResonse;
import com.growCode.growCode.entity.Otp;
import com.growCode.growCode.entity.User;
import com.growCode.growCode.exceptions.NotFoundException;
import com.growCode.growCode.responses.CreatedResponse;
import com.growCode.growCode.responses.NotFoundResponse;
import com.growCode.growCode.responses.OkResponse;
import com.growCode.growCode.responses.Response;
import com.growCode.growCode.responses.ServerErrorResponse;
import com.growCode.growCode.services.JwtUtil;
import com.growCode.growCode.services.OtpService;
import com.growCode.growCode.services.userServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class userController {

    @Autowired
    private userServices uServices;

    @Autowired
    private OtpService oService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtToken;

    @PostMapping("/create")
    public ResponseEntity<Response> saveUser(@RequestBody User user){
        try {
            User savedUser = uServices.save(user);
            return new ResponseEntity<>(new CreatedResponse<User>(savedUser), null, 201);
        }catch (Exception e) {
            return new ResponseEntity<>(new ServerErrorResponse(e), null, 500);
        }
    }

    @GetMapping("")
    public ResponseEntity<Response> getUsers(){
        try {
            ArrayList<User> users = (ArrayList<User>) uServices.getAll();
            return new ResponseEntity<>(new OkResponse<ArrayList<User>>(users), null, 200);
        }catch(NotFoundException ne){
            return new ResponseEntity<>(new NotFoundResponse(ne), null, 404);
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerErrorResponse(e), null, 500);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Response> getProfile(){
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = null;
            if(principal instanceof UserDetails){
                email = ((UserDetails)principal).getUsername();
            }else{
                email = principal.toString();
            }
            User user = uServices.getByEmail(email);
            return ResponseEntity.ok().body(new OkResponse<User>(user));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ServerErrorResponse(e));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response>  getUser(@PathVariable String id){
        try {
            System.out.println();
            User user = uServices.getOne(Long.parseLong(id));
            User retrivedUser = new User(user.getId(), user.getName(), user.getEmail(), user.getPhoneNo(), user.getJoiningDate(), user.isActive());
            return new ResponseEntity<>(new OkResponse<User>(retrivedUser), null, 200);
        }catch(NotFoundException ne){
            return new ResponseEntity<>(new NotFoundResponse(ne), null, 404);
        }catch (Exception e) {
            return new ResponseEntity<>(new ServerErrorResponse(e), null, 500);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user)throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Incorrect email or password", e);
        }
        System.out.println(user.toString());
        final UserDetails userDetails = uServices.loadUserByUsername(user.getEmail());
        final String token = jwtToken.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResonse(token));
    }


    @PostMapping("/otp")
    public ResponseEntity<Response> sendOtp(@RequestBody User user){
        try {
            long mobileNo = user.getPhoneNo();
            Otp otp = oService.save(mobileNo);
            return new ResponseEntity<>(new CreatedResponse<Otp>(otp), null, 201);
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerErrorResponse(e), null, 500);
        }
    }


    
}
