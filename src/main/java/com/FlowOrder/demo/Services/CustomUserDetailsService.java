package com.FlowOrder.demo.Services;

import com.FlowOrder.demo.Entity.CustomUserDetails;
import com.FlowOrder.demo.Entity.Users;
import com.FlowOrder.demo.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Users users=userRepo.findByEmail(email)
                .orElseThrow(
                        ()->new UsernameNotFoundException("User not found with this email :"+email)
                );

        return new CustomUserDetails(
                users.getId(),
                users.getEmail(),
                users.getPassword(),
                List.of(new SimpleGrantedAuthority(users.getRole().name()))
        );
    }
}
