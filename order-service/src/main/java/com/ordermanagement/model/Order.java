package com.ordermanagement.model;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Relation;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@MappedEntity("orders")
public class Order {
    @Id
    private Long id;

    private String buyerId;

    private OrderStatus status;

    private BigDecimal totalAmount;

    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "order")
    private List<OrderItem> items;

    @DateCreated
    private LocalDateTime createdAt;

    @DateUpdated
    private LocalDateTime updatedAt;
}
