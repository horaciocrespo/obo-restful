package com.obo.oborestfulapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends RepresentationModel<OrderDTO> {

    private Long id;
    private String Carrier;
    private String trackingNumber;

}
