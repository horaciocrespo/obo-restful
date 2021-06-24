package com.obo.oborestfulapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDTO {

    private long totalItems;
    List<OrderDTO> orders;
    private int totalPages;
    private int currentPage;
}
