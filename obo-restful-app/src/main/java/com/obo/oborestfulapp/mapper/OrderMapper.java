package com.obo.oborestfulapp.mapper;

import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.model.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO orderToOrderDTO(Order order);
    Order orderDtoToOrder(OrderDTO orderDTO);
}
