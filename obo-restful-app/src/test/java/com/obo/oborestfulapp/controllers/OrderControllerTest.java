package com.obo.oborestfulapp.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.obo.oborestfulapp.model.OrderDTO;
import com.obo.oborestfulapp.model.OrderListDTO;
import com.obo.oborestfulapp.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        OrderListDTO orderListDTO = new OrderListDTO(2, orders, 1, 1);

        when(orderService.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(orderListDTO);

        mockMvc.perform(get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.orders", hasSize(2)))
                .andExpect(jsonPath("$.totalItems").exists())
                .andExpect(jsonPath("$.totalItems").isNotEmpty())
                .andExpect(jsonPath("$.totalItems").isNumber())
                .andExpect(jsonPath("$.totalItems", is(2)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.currentPage", is(1)));
    }

    @Test
    void testUpdateOrder() throws Exception {
        OrderDTO paramOrderDTO = new OrderDTO();
        paramOrderDTO.setTrackingNumber("123ABC");

        OrderDTO returnedOrderDTO = new OrderDTO();
        returnedOrderDTO.setTrackingNumber(paramOrderDTO.getTrackingNumber());

        when(orderService.saveOrderByDTO(ArgumentMatchers.anyLong(), ArgumentMatchers.any(OrderDTO.class))).thenReturn(returnedOrderDTO);

        mockMvc.perform(put("/api/v1/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(paramOrderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.trackingNumber", equalTo("123ABC")));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

//    @Test
//    void getOrderById() throws Exception {
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setTrackingNumber("123");
//
//        when(orderService.getOrderById(1L)).thenReturn(orderDTO);
//
//        mockMvc.perform(get("api/v1/orders/").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

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
