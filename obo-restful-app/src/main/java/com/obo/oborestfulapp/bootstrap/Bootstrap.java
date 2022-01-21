package com.obo.oborestfulapp.bootstrap;

import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.model.OrderDTO;
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
        order.setName("2021 - June - Odaban");
        order.setDescription("First purchase of Odaban");
        order.setQuantity(100);
        order.setTotal(350.99);
        order.setCarrier("USPS");
        order.setProductName("Odaban 30ml");
        order.setShippingAddress("Calle Damian Rejas #3611");
        order.setBillingAddress("Calle Damian Rejas #3611");

        orderRepository.save(order);
    }
}
