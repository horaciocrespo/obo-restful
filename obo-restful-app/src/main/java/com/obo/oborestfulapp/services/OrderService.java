package com.obo.oborestfulapp.services;

import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.model.OrderDTO;
import com.obo.oborestfulapp.model.OrderListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.List;

public interface OrderService {

//    List<OrderDTO> getAllOrders();
    OrderListDTO findAll(Pageable pageable);
    Page<Order> findAllWithPagination(Pageable pageable);
    OrderDTO getOrderByTrackingNumber(String trackingNumber);
    Order createNewOrder(OrderDTO orderDTO);
    OrderDTO patchOrder(Long id, OrderDTO orderDTO);
    Order saveOrderByDTO(Long id, OrderDTO orderDTO);
    Order updateOrderByDTO(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);
    Order getOrderById(Long id);
}
