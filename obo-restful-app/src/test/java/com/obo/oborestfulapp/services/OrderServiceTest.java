package com.obo.oborestfulapp.services;

import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.mapper.OrderMapper;
import com.obo.oborestfulapp.model.OrderDTO;
import com.obo.oborestfulapp.model.OrderListDTO;
import com.obo.oborestfulapp.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

//     DEVNOTE Mockito will try to inject mocks either by constructor injection, setter injection, or property injection
//    @InjectMocks
//    OrderServiceImpl orderService;
    OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(OrderMapper.INSTANCE, orderRepository);
    }

    @Test
    @DisplayName("Add operation test")
    void getAllOrders() {
        // given
        List<Order> orders = Arrays.asList(new Order(), new Order(), new Order());
        Page<Order> page = new PageImpl<>(orders);
        when(orderRepository.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(page);

        // when
        Pageable pageable = PageRequest.of(1, 1, Sort.by(Sort.Order.desc("id,desc")));
        OrderListDTO orderListDTO = orderService.findAll(pageable);

        // then
        assertEquals(3, orderListDTO.getOrders().size());
    }

    @Test
    void saveOrderByDTO() {
        OrderDTO paramOrderDTO = new OrderDTO();
        paramOrderDTO.setTrackingNumber("123ABC");

        // mocking
        Order orderFromDB = new Order();
        orderFromDB.setId(1L);
        orderFromDB.setTrackingNumber(paramOrderDTO.getTrackingNumber());
        when(orderRepository.save(ArgumentMatchers.any(Order.class))).thenReturn(orderFromDB);

        // when
        OrderDTO savedDTO = orderService.saveOrderByDTO(1L, paramOrderDTO);

        // then
        assertEquals(paramOrderDTO.getTrackingNumber(), savedDTO.getTrackingNumber());
    }

    @Test
    void createOrder() {
        OrderDTO paramOrderDTO = new OrderDTO();
        paramOrderDTO.setCarrier("USPS");
        paramOrderDTO.setTrackingNumber("RA411342925US");

        Order orderFromDB = new Order();
        orderFromDB.setCarrier("USPS");
        orderFromDB.setTrackingNumber("RA411342925US");
        when(orderRepository.save(ArgumentMatchers.any(Order.class))).thenReturn(orderFromDB);

        OrderDTO returnedDTO = orderService.createNewOrder(paramOrderDTO);

        assertEquals(paramOrderDTO.getCarrier(), returnedDTO.getCarrier());
        assertEquals(paramOrderDTO.getTrackingNumber(), returnedDTO.getTrackingNumber());
    }
}
