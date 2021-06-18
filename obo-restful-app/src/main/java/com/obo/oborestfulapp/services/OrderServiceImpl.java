package com.obo.oborestfulapp.services;

import com.obo.oborestfulapp.controllers.OrderController;
import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.exceptions.ResourceNotFoundException;
import com.obo.oborestfulapp.mapper.OrderMapper;
import com.obo.oborestfulapp.model.OrderDTO;
import com.obo.oborestfulapp.repositories.OrderRepository;
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

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderByTrackingNumber(String trackingNumber) {
        return orderMapper.orderToOrderDTO(orderRepository.findByTrackingNumber(trackingNumber));
    }

    @Override
    public OrderDTO createNewOrder(OrderDTO orderDTO) {
        return saveAndReturnDTO(orderMapper.orderDtoToOrder(orderDTO));
    }

    private OrderDTO saveAndReturnDTO(Order order) {
        Order savedOrder = orderRepository.save(order);
        OrderDTO returnedDTO = orderMapper.orderToOrderDTO(savedOrder);
        returnedDTO.setOrderUrl("/api/v1/order" + savedOrder.getTrackingNumber());
        return returnedDTO;
    }

    @Override
    public OrderDTO saveOrderByDTO(Long id, OrderDTO orderDTO) {
        Order order = orderMapper.orderDtoToOrder((orderDTO));
        order.setId(id);

        return saveAndReturnDTO(order);
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
                    returnDTO.setOrderUrl(getOrderUrl(id));

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
}
