package com.ordermanagement.repository;

import com.ordermanagement.model.Order;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
