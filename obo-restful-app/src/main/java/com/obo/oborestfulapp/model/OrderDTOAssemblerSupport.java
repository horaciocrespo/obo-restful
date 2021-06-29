package com.obo.oborestfulapp.model;

import com.obo.oborestfulapp.controllers.OrderController;
import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.mapper.OrderMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

// RepresentationModelAssemblerSupport
@Component
public class OrderDTOAssemblerSupport extends RepresentationModelAssemblerSupport<Order, OrderDTO> {

    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    public OrderDTOAssemblerSupport() {
        super(OrderController.class, OrderDTO.class);
    }

    @Override
    public OrderDTO toModel(Order order) {
        OrderDTO orderDTO = createModelWithId(order.getId(), order);

        OrderDTO resultOrderDTO = orderMapper.orderToOrderDTO(order);
        resultOrderDTO.add(orderDTO.getLinks());

        return resultOrderDTO;
    }
}