package com.obo.oborestfulapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO {

    private String name;
    private String description;

    @Min(value = 1, message = "${validatedValue} is invalid. The min value is {value}")
    @Max(value = 10, message = "${validatedValue} is invalid. The max value is {value}")
    private int quantity;

    @NotBlank(message = "{order.carrier.required}")
    private String carrier;

    private Date deliveryDate;

    @NotBlank(message = "Product is mandatory")
    private String productName;

    @NotBlank
    private String shippingAddress;

    @NotBlank
    private String billingAddress;

}
