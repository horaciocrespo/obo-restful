package com.obo.oborestfulapp.bootstrap;

import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.repositories.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final OrderRepository orderRepository;

    public Bootstrap(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Order order = new Order();
        order.setCarrier("USPS");
        order.setTrackingNumber("RA367146195US");

        orderRepository.save(order);
    }
}
