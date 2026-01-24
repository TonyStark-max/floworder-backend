package com.FlowOrder.demo.Controllers;


import com.FlowOrder.demo.DTO.AuthResDTO;
import com.FlowOrder.demo.DTO.Request;
import com.FlowOrder.demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> RegisterUser(@RequestBody Request request){
        String result= userService.RegisterUser(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResDTO> Login(@RequestBody Request request){
        return ResponseEntity.ok(
                userService.Login(request.getEmail(), request.getPassword())
        );
    }

    @GetMapping("/testing")
    public ResponseEntity<String> testing(){
        return ResponseEntity.ok("Connected to user only endpoint");
    }
}
