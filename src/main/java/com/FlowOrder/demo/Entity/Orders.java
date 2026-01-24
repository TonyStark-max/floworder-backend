package com.FlowOrder.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@RequiredArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private Integer userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status=OrderStatus.PENDING;
    @NonNull
    private BigDecimal totalAmount;
    @NonNull
    private LocalDateTime createdAt=LocalDateTime.now();

    public void markPaid(){
        if(this.status!=OrderStatus.PENDING){
            throw new IllegalStateException(
                    "Invalid transition: "+this.status+" -> PAID"
            );
        }
        this.status=OrderStatus.PAID;
    }

    public void confirm(){
        if(this.status!=OrderStatus.PAID){
            throw new IllegalStateException(
                    "Invalid transition: "+this.status+"-> CONFIRMED"
            );
        }
        this.status=OrderStatus.CONFIRMED;
    }

    public void ship(){
        if(this.status!=OrderStatus.CONFIRMED){
            throw new IllegalStateException(
                    "Invalid transition"+this.status+"-> SHIPPED"
            );
        }
        this.status=OrderStatus.SHIPPED;
    }

    public void deliver(){
        if(this.status!=OrderStatus.SHIPPED){
            throw new IllegalStateException(
                    "Invalid transitions"+this.status+"->DELIVERED"
            );
        }
        this.status=OrderStatus.DELIVERED;
    }
    public void cancel(){
        if(this.status!=OrderStatus.SHIPPED){
            throw new IllegalStateException(
                    "Cannot cancel order at this state"+this.status
            );
        }
    }
}
