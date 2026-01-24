package com.FlowOrder.demo.Services;


import com.FlowOrder.demo.Events.PaymentsEvent;
import com.FlowOrder.demo.Events.ShippingEvent;
import com.FlowOrder.demo.Repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class ShippingService {
    private final OrderRepository orderRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onOrderConfirmed(PaymentsEvent event){
        int shipped=orderRepo.shipIfConfirmed(event.orderId());
        if(shipped==1){
            eventPublisher.publishEvent(new ShippingEvent(event.orderId()));
        }
    }
}
