package com.obo.oborestfulapp.repositories;

import com.obo.oborestfulapp.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByTrackingNumber(String trackingNumber);
}
