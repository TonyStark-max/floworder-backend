package com.FlowOrder.demo.Controllers;

import com.FlowOrder.demo.Repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logistics")
@RequiredArgsConstructor
public class DeliveryController {
    private final OrderRepository orderRepo;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("{orderId}/delivered")
    public ResponseEntity<String> markDelivered(@PathVariable Integer orderId){
        int updated=orderRepo.deliverIfShipped(orderId);
        if(updated==0){
            return ResponseEntity
                    .badRequest()
                    .body("Order is not SHIPPED yet");
        }
        return ResponseEntity.ok("Order Delivered");
    }
}