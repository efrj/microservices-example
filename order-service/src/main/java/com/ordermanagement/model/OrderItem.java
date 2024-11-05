package com.ordermanagement.model;

import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Id;
import lombok.Data;

import java.math.BigDecimal;

@Data
@MappedEntity("order_items")
public class OrderItem {
    @Id
    private Long id;

    private Long productId;

    private Integer quantity;

    private BigDecimal price;
}
