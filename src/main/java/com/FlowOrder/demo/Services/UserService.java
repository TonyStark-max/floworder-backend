package com.FlowOrder.demo.Services;

import com.FlowOrder.demo.DTO.AuthResDTO;
import com.FlowOrder.demo.DTO.Request;
import com.FlowOrder.demo.Entity.CustomUserDetails;
import com.FlowOrder.demo.Entity.Roles;
import com.FlowOrder.demo.Entity.Users;
import com.FlowOrder.demo.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JWTService jwtService;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public String RegisterUser(Request request){
        if(userRepo.existsByEmail(request.getEmail())){
            return("User with email : "+ request.getEmail()+" already exists");
        }
        Users user=new Users(request.getEmail(), passwordEncoder.encode (request.getPassword()), Roles.USER);
        userRepo.save(user);

        return "User Registered Successfully";
    }

    public AuthResDTO Login(String email, String password){
        Authentication auth=
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email,password)
                );
        CustomUserDetails user=(CustomUserDetails) auth.getPrincipal();
        String token= jwtService.generateToken(user);
        return new AuthResDTO(token);
    }
}
