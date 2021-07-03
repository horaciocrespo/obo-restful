package com.obo.oborestfulapp.controllers;

import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.domain.OrderStatus;
import com.obo.oborestfulapp.model.OrderDTO;
import com.obo.oborestfulapp.model.OrderDTOAssemblerSupport;
import com.obo.oborestfulapp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
// We could also read this from a properties file
@RequestMapping(OrderController.BASE_URL)
@RequiredArgsConstructor
public class OrderController {

    public static final String BASE_URL = "/api/v1/orders";

    private final OrderService orderService;

    // DEVNOTE this is needed for pagination
    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;
    private final OrderDTOAssemblerSupport orderDTOAssemblerSupport;

    // DEVNOTE http://localhost:8080/api/v1/orders?page=1&size=2&sort=title,desc
    @GetMapping
    public ResponseEntity<PagedModel<OrderDTO>> getAll(Pageable pageable) {
        Page<Order> orderPage = orderService.findAllWithPagination(pageable);
        PagedModel<OrderDTO> collModel = pagedResourcesAssembler
                // Page<T>, RepresentationModelAssembler<T, R>
                .toModel(orderPage, orderDTOAssemblerSupport);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(collModel);
    }

//    @GetMapping
//    public ResponseEntity<OrderListDTO> getAllOrders(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "3") int size,
//            @RequestParam(defaultValue = "id,desc") String[] sort) {
//
//        List<Sort.Order> sortOrders = new ArrayList<>();
//
//        if (sort[0].contains(",")) {
//            for (String sortOrder : sort) {
//                String[] _sort = sortOrder.split(",");
//                sortOrders.add(new Sort.Order(Sort.Direction.fromString(_sort[1]), _sort[0]));
//            }
//        } else {
//            // sort=[field, direction]
//            sortOrders.add(new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]));
//        }
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrders));
//        OrderListDTO orderListDTO = orderService.findAll(pageable);
//        return new ResponseEntity<>(orderListDTO, HttpStatus.OK);
//    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {

//        return new ResponseEntity<>(EntityModel.of(orderService.getOrderById(id),
//                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrderById(id)),
//                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getAllOrders()).withRel("orders")
//        ), HttpStatus.OK);

        Order order = orderService.getOrderById(id);

        // https://spring.io/guides/tutorials/rest/
//        return new ResponseEntity<>(EntityModel.of(orderService.getOrderById(id),
//                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrderById(id)).withSelfRel()),
//                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getAll(null)).withRel("orders"),
//                HttpStatus.OK);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderDTOAssemblerSupport.toModel(order));
    }

//    @GetMapping("{trackingNumber}")
//    public ResponseEntity<OrderDTO> getOrderByTrackingNumber(@PathVariable String trackingNumber) {
//        return new ResponseEntity<OrderDTO>(orderService.getOrderByTrackingNumber(trackingNumber), HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<OrderDTO> createNewOrder(@RequestBody OrderDTO orderDTO) {

        Order order = orderService.createNewOrder(orderDTO);

        OrderDTO savedOrderDTO = orderDTOAssemblerSupport.toModel(order);

        return ResponseEntity
                .created(savedOrderDTO.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(savedOrderDTO);


//        return new ResponseEntity<>(orderService.createNewOrder(orderDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        Order order = orderService.updateOrderByDTO(id, orderDTO);
        OrderDTO savedOrderDTO = orderDTOAssemblerSupport.toModel(order);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
//                .created(savedOrderDTO.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(savedOrderDTO);

//        return new ResponseEntity<>(orderService.saveOrderByDTO(id, orderDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDTO> patchOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.patchOrder(id, orderDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order.getStatus() == OrderStatus.IN_PROGRESS) {
            order.setStatus(OrderStatus.CANCELLED);
            OrderDTO orderDTO = orderDTOAssemblerSupport.toModel(order);
            Order savedOrder = orderService.saveOrderByDTO(id, orderDTO);
            return ResponseEntity.ok(orderDTOAssemblerSupport.toModel(savedOrder));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status")
                );
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order.getStatus() == OrderStatus.IN_PROGRESS) {
            order.setStatus(OrderStatus.COMPLETED);
            OrderDTO orderDTO = orderDTOAssemblerSupport.toModel(order);
            Order savedOrder = orderService.saveOrderByDTO(id, orderDTO);
            return ResponseEntity.ok(orderDTOAssemblerSupport.toModel(savedOrder));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status")
                );
    }
}
