package com.FlowOrder.demo.Repositories;

import com.FlowOrder.demo.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepo extends JpaRepository<Inventory,Integer> {

    @Modifying
    @Query
            ("""
    UPDATE Inventory i
    SET i.quantity = i.quantity-1
    WHERE i.productId = :productId
    AND i.quantity > 0
""")
    int reserveStock(@Param("productId")Integer productId);
}
