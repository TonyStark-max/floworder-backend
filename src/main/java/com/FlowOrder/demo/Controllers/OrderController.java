package com.FlowOrder.demo.Controllers;


import com.FlowOrder.demo.DTO.OrderRequest;
import com.FlowOrder.demo.Entity.CustomUserDetails;
import com.FlowOrder.demo.Services.CustomUserDetailsService;
import com.FlowOrder.demo.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request, Authentication auth){
        CustomUserDetails user=(CustomUserDetails) auth.getPrincipal();
        Integer userId= user.getUserId();
        return ResponseEntity.ok(orderService.createOrder(userId,request.getAmount()));
    }
}
