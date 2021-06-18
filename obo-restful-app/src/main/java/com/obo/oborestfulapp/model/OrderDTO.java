package com.obo.oborestfulapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private String Carrier;
    private String trackingNumber;

    @JsonProperty("order_url")
    private String orderUrl;

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }
}
