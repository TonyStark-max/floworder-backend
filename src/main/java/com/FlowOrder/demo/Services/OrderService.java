package com.FlowOrder.demo.Services;

import com.FlowOrder.demo.Events.SecurityUtil;
import com.FlowOrder.demo.Entity.Orders;
import com.FlowOrder.demo.Repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;

    public Orders createOrder(Integer userId, BigDecimal amount) {
        Orders orders = new Orders(userId, amount);
        return orderRepo.save(orders);
    }

    public void markOrderPaid(Integer orderId) {
        Orders orders = getOrder(orderId);
        orders.markPaid();
        orderRepo.save(orders);
    }

    public void confirmOrder(Integer orderId) {
        Orders orders = getOrder(orderId);
        orders.confirm();
        orderRepo.save(orders);
    }

    private Orders getOrders(Integer orderId){
        return orderRepo.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found with id :"+orderId));
    }
    public Orders getOrder(Integer orderId){
        Integer userId= SecurityUtil.getCurrentUserId();

        return orderRepo.findByIdAndUserId(orderId,userId)
                .orElseThrow(()->new AccessDeniedException("Not Your Order"));
    }
}
