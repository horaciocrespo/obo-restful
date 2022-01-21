package com.obo.oborestfulapp.services;

import com.obo.oborestfulapp.bootstrap.Bootstrap;
import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.mapper.OrderMapper;
import com.obo.oborestfulapp.model.OrderDTO;
import com.obo.oborestfulapp.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class OrderServiceIntegrationTest {

    @Autowired
    OrderRepository orderRepository;

    OrderService orderService;

    @BeforeEach
    void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(orderRepository);
        bootstrap.run();

        orderService = new OrderServiceImpl(OrderMapper.INSTANCE, orderRepository);
    }

//    @Test
//    void patchCustomerUpdateTrackingNumber() {
//        long id = getOrderId();
//        Order orderFromDB = orderRepository.getById(id);
//        Assertions.assertNotNull(orderFromDB);
////        String originalTrackingNo = orderFromDB.getTrackingNumber();
//
//        String newTrackingNo = "RA053932896US";
//        OrderDTO paramOrderDTO = new OrderDTO();
//        paramOrderDTO.setTrackingNumber(newTrackingNo);
//
//        orderService.patchOrder(id, paramOrderDTO);
//
//        Order updatedOrderFromDB = orderRepository.findById(id).get();
//
//        Assertions.assertNotNull(updatedOrderFromDB);
//        Assertions.assertEquals(newTrackingNo, updatedOrderFromDB.getTrackingNumber());
//    }

    private long getOrderId() {
        List<Order> orders = orderRepository.findAll();
        return orders.get(0).getId();
    }
}
