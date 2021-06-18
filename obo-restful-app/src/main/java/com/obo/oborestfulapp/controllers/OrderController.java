package com.obo.oborestfulapp.controllers;

import com.obo.oborestfulapp.model.OrderDTO;
import com.obo.oborestfulapp.model.OrderListDTO;
import com.obo.oborestfulapp.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
// We could also read this from a properties file
@RequestMapping(OrderController.BASE_URL)
public class OrderController {

    public static final String BASE_URL = "/api/v1/orders";

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<OrderListDTO> getAllOrders() {
        return new ResponseEntity<>(new OrderListDTO(orderService.getAllOrders()), HttpStatus.OK);
    }

    @GetMapping("{trackingNumber}")
    public ResponseEntity<OrderDTO> getOrderByTrackingNumber(@PathVariable String trackingNumber) {
        return new ResponseEntity<OrderDTO>(orderService.getOrderByTrackingNumber(trackingNumber), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createNewOrder(@RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<OrderDTO>(orderService.createNewOrder(orderDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<OrderDTO>(orderService.saveOrderByDTO(id, orderDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDTO> patchOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<OrderDTO>(orderService.patchOrder(id, orderDTO), HttpStatus.OK);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable Long id) {
//        return new ResponseEntity<OrderDTO>(orderService.deleteOrder(id), HttpStatus.OK);
//    }
}
