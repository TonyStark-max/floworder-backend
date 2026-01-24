package com.FlowOrder.demo.Repositories;

import com.FlowOrder.demo.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders,Integer> {

    @Modifying
    @Query("""
            UPDATE Orders o
            SET o.status='PAID'
            WHERE o.id= :orderId
            AND o.status='PENDING'
            """)
    int markPaidIfPending(@Param("orderId") Integer orderId);

    @Modifying
    @Query
            ("""
                    UPDATE Orders o
                    SET o.status='CONFIRMED'
                    WHERE o.id= :orderId
                    AND o.status='PAID'
                    """)
    int markConfirmedIfPaid(@Param("orderId") Integer orderId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
            """
                    UPDATE Orders o
                    SET o.status='CANCELLED'
                    WHERE o.id=:orderId
                    AND o.status='PAID'
                    """
    )
    int cancelIfPaid(@Param("orderId") Integer orderId);

    @Modifying
    @Query("""
            UPDATE Orders o
            SET o.status='SHIPPED'
            WHERE o.id=:orderId
            AND o.status = 'CONFIRMED'
            """)
    int shipIfConfirmed(@Param("orderId") Integer orderId);

    @Modifying
    @Query("""
            UPDATE Orders o
            SET o.status='DELIVERED'
            WHERE id=:orderId
            AND o.status='SHIPPED'
            """)
    int deliverIfShipped(@Param("orderId")Integer orderId);

    Optional<Orders> findByIdAndUserId(Integer Id,Integer UserId);
}
