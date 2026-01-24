package com.FlowOrder.demo.Events;

public record PaymentsEvent(
        Integer orderId,boolean success
) {
}
