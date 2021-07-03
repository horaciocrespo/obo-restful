package com.obo.oborestfulapp.model;

import com.obo.oborestfulapp.controllers.OrderController;
import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.domain.OrderStatus;
import com.obo.oborestfulapp.mapper.OrderMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

// RepresentationModelAssemblerSupport -> RepresentationModelAssembler
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

        if (OrderStatus.IN_PROGRESS == order.getStatus()) {
            orderDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                    .cancel(order.getId())).withRel("cancel")
            );

            orderDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                    .complete(order.getId())).withRel("complete")
            );
        }

        orderDTO.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(OrderController.class)
                        .getAll(null)
                )
                .withRel("orders")
        );

        resultOrderDTO.add(orderDTO.getLinks());

        return resultOrderDTO;
    }
}