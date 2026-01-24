package com.FlowOrder.demo.Services;


import com.FlowOrder.demo.Events.PaymentsEvent;
import com.FlowOrder.demo.Repositories.InventoryRepo;
import com.FlowOrder.demo.Repositories.OrderRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    private final OrderRepository orderRepo;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePayment(PaymentsEvent event){
        Integer productId=1;
        int reserved=inventoryRepo.reserveStock(productId);
        if(reserved==0){
            orderRepo.cancelIfPaid(event.orderId());
            return;
        }
        orderRepo.markConfirmedIfPaid(event.orderId());
    }
}
