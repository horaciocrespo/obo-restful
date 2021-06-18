package com.obo.oborestfulapp.services;

import com.obo.oborestfulapp.model.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();
    OrderDTO getOrderByTrackingNumber(String trackingNumber);
    OrderDTO createNewOrder(OrderDTO orderDTO);
    OrderDTO saveOrderByDTO(Long id, OrderDTO orderDTO);
    OrderDTO patchOrder(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);
}
