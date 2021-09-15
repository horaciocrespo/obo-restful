package com.obo.oborestfulapp.services;

import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.model.CreateOrderDTO;
import com.obo.oborestfulapp.model.OrderDTO;
import com.obo.oborestfulapp.model.OrderListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

//    List<OrderDTO> getAllOrders();
    OrderListDTO findAll(Pageable pageable);
    Page<Order> findAllWithPagination(Pageable pageable);
    OrderDTO getOrderByTrackingNumber(String trackingNumber);
    Order createNewOrder(CreateOrderDTO orderDTO);
    OrderDTO patchOrder(Long id, CreateOrderDTO orderDTO);
    Order saveOrderByDTO(Long id, OrderDTO orderDTO);
    Order updateOrderByDTO(Long id, CreateOrderDTO orderDTO);
    void deleteOrder(Long id);
    Order getOrderById(Long id);
}
