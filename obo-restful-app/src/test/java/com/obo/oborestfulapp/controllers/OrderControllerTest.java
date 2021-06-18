package com.obo.oborestfulapp.controllers;


import com.obo.oborestfulapp.model.OrderDTO;
import com.obo.oborestfulapp.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @MockBean
    OrderService orderService;

// This mock will be automatically initialized
//    @Mock
//    OrderService orderService;

// OrderService will be automatically injected here
//    @InjectMocks
//    OrderController orderController;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testListOrders() throws Exception {
        OrderDTO orderDTO1 = new OrderDTO();
        orderDTO1.setTrackingNumber("123");

        OrderDTO orderDTO2 = new OrderDTO();
        orderDTO2.setTrackingNumber("1234");

        List<OrderDTO> orders = Arrays.asList(orderDTO1, orderDTO2);
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orders", hasSize(2)));
    }

//    @Test
//    void createNewOrder() throws Exception {
//        // given
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setTrackingNumber("123");
//
//        OrderDTO returnedDto = new OrderDTO();
//        returnedDto.setTrackingNumber(orderDTO.getTrackingNumber());
//        returnedDto.setOrderUrl("/api/v1/customers/1");
//
//        when(orderService.createNewOrder(orderDTO)).thenReturn(returnedDto);
//
//        mockMvc.perform(post("/api/v1/orders")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(orderDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.tracking", equalTo("123")));
//
//    }
}
