package com.obo.oborestfulapp.services;

import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.mapper.OrderMapper;
import com.obo.oborestfulapp.model.CreateOrderDTO;
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
import static org.mockito.Mockito.*;

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
        Order savedOrder = orderService.saveOrderByDTO(1L, paramOrderDTO);

        // then
        assertEquals(paramOrderDTO.getTrackingNumber(), savedOrder.getTrackingNumber());
    }

    @Test
    void createOrder() {
        CreateOrderDTO paramOrderDTO = CreateOrderDTO
                .builder()
                .name("2021 - June - Odaban")
                .description("First purchase of Odaban")
                .quantity(100)
                .carrier("USPS")
                .productName("Odaban 30ml")
                .shippingAddress("Calle Damian Rejas #3611")
                .billingAddress("Calle Damian Rejas #3611")
                .build();

        Order orderFromDB = new Order();
        orderFromDB.setName("2021 - June - Odaban");
        orderFromDB.setDescription("First purchase of Odaban");
        orderFromDB.setQuantity(100);
        orderFromDB.setTotal(350.99);
        orderFromDB.setCarrier("USPS");
        orderFromDB.setProductName("Odaban 30ml");
        orderFromDB.setShippingAddress("Calle Damian Rejas #3611");
        orderFromDB.setBillingAddress("Calle Damian Rejas #3611");

        when(orderRepository.save(ArgumentMatchers.any(Order.class))).thenReturn(orderFromDB);

        Order returnedOrder = orderService.createNewOrder(paramOrderDTO);

        assertEquals(paramOrderDTO.getCarrier(), returnedOrder.getCarrier());
//        assertEquals(paramOrderDTO.getTrackingNumber(), returnedOrder.getTrackingNumber());
    }

    @Test
    void deleteOrderById() {
        Long id = 1L;
        orderRepository.deleteById(id);
        verify(orderRepository, times(1)).deleteById(anyLong());
    }
}
