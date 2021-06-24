package com.obo.oborestfulapp.services;

import com.obo.oborestfulapp.model.OrderDTO;
import com.obo.oborestfulapp.model.OrderListDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

//    List<OrderDTO> getAllOrders();
    OrderListDTO findAll(Pageable pageable);
    OrderDTO getOrderByTrackingNumber(String trackingNumber);
    OrderDTO createNewOrder(OrderDTO orderDTO);
    OrderDTO saveOrderByDTO(Long id, OrderDTO orderDTO);
    OrderDTO patchOrder(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);
    OrderDTO getOrderById(Long id);
}
