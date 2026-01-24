package com.FlowOrder.demo.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productId;
    private Integer quantity;

    public Inventory(Integer productId,Integer quantity){
        this.productId=productId;
        this.quantity=quantity;
    }
}
