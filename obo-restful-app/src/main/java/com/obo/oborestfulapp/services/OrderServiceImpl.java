package com.obo.oborestfulapp.services;

import com.obo.oborestfulapp.controllers.OrderController;
import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.domain.OrderStatus;
import com.obo.oborestfulapp.exceptions.ResourceNotFoundException;
import com.obo.oborestfulapp.mapper.OrderMapper;
import com.obo.oborestfulapp.model.OrderDTO;
import com.obo.oborestfulapp.model.OrderListDTO;
import com.obo.oborestfulapp.repositories.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

//    @Override
//    public List<OrderDTO> getAllOrders() {
//        return orderRepository.findAll()
//                .stream()
//                .map(orderMapper::orderToOrderDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public OrderListDTO findAll(Pageable pageable) {
        Page<Order> pages = orderRepository.findAll(pageable);
        return pageToProductCategoryListDTO(pages);
    }

    @Override
    public Page<Order> findAllWithPagination(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    private OrderListDTO pageToProductCategoryListDTO(Page<Order> pages) {
        List<OrderDTO> categoriesDTOList = pages.getContent()
                .stream()
                .map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());
        return new OrderListDTO(pages.getTotalElements(), categoriesDTOList,
                pages.getTotalPages(), pages.getNumber());
    }

    @Override
    public OrderDTO getOrderByTrackingNumber(String trackingNumber) {
        return orderMapper.orderToOrderDTO(orderRepository.findByTrackingNumber(trackingNumber));
    }

    @Override
    public Order createNewOrder(OrderDTO orderDTO) {
        Order newOrder = orderMapper.orderDtoToOrder(orderDTO);
        newOrder.setStatus(OrderStatus.DRAFT);
        return orderRepository.save(newOrder);
//        return saveAndReturnDTO(orderMapper.orderDtoToOrder(orderDTO));
    }

    @Override
    public Order saveOrderByDTO(Long id, OrderDTO orderDTO) {
        Order order = orderMapper.orderDtoToOrder((orderDTO));
        order.setId(id);
        return orderRepository.save(order);

//        return saveAndReturnDTO(order);
    }

    @Override
    public Order updateOrderByDTO(Long id, OrderDTO orderDTO) {

        return orderRepository
                .findById(id)
                .map(order -> {
                    Order updatedOrder = orderMapper.orderDtoToOrder(orderDTO);
                    updatedOrder.setId(id);
                    return orderRepository.save(updatedOrder);
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    private OrderDTO saveAndReturnDTO(Order order) {
        Order savedOrder = orderRepository.save(order);
        OrderDTO returnedDTO = orderMapper.orderToOrderDTO(savedOrder);
//        returnedDTO.setOrderUrl("/api/v1/order" + savedOrder.getTrackingNumber());
        return returnedDTO;
    }

    @Override
    public OrderDTO patchOrder(Long id, OrderDTO orderDTO) {
        return orderRepository.findById(id)
                .map(order -> {
                    if (orderDTO.getTrackingNumber() != null) {
                        order.setTrackingNumber(orderDTO.getTrackingNumber());
                    }

                    if (orderDTO.getCarrier() != null) {
                        order.setCarrier(orderDTO.getCarrier());
                    }

                    OrderDTO returnDTO = orderMapper.orderToOrderDTO(orderRepository.save(order));
//                    returnDTO.setOrderUrl(getOrderUrl(id));

                    return returnDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    private String getOrderUrl(Long id) {
        return OrderController.BASE_URL + "/" + id;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order getOrderById(Long id) {
//        return orderRepository.findById(id)
//                .map(orderMapper::orderToOrderDTO)
//                .map(orderDTO -> {
//                    return orderDTO;
//                })
//                .orElseThrow(ResourceNotFoundException::new);

        return orderRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }


}
