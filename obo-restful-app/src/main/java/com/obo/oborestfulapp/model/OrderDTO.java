package com.obo.oborestfulapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends RepresentationModel<OrderDTO> {

    private Long id;
    private String orderNumber;
    private String name;
    private String description;

    private String orderStatus;

    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    private int quantity;
    private double total;

    private String carrier;
    private String trackingNumber;

    private Date deliveryDate;

    private String productName;

    private String shippingAddress;
    private String billingAddress;

}
