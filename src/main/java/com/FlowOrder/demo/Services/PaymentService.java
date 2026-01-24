package com.FlowOrder.demo.Services;

import com.FlowOrder.demo.Events.PaymentsEvent;
import com.FlowOrder.demo.Repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderRepository orderRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void processPayment(Integer orderId){
        int updated= orderRepo.markPaidIfPending(orderId);
        if(updated==0){
            return;
        }
        System.out.println(">>> BEFORE publishing PaymentEvent");
        eventPublisher.publishEvent(
                new PaymentsEvent(orderId,true)
        );
        System.out.println(">>> AFTER publishing PaymentEvent");
    }
}
