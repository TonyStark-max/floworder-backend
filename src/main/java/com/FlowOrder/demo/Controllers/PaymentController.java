package com.FlowOrder.demo.Controllers;

import com.FlowOrder.demo.Services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment/{orderId}")
    public ResponseEntity<String> pay(@PathVariable Integer orderId){
        paymentService.processPayment(orderId);
        return ResponseEntity.ok("Payment Processed");
    }
}
