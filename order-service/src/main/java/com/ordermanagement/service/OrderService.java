package com.ordermanagement.service;

import com.ordermanagement.model.Order;
import com.ordermanagement.model.OrderStatus;
import com.ordermanagement.repository.OrderRepository;
import jakarta.inject.Singleton;
import com.ordermanagement.client.ProductClient;
import com.ordermanagement.model.OrderItem;
import com.ordermanagement.model.Product;
import io.micronaut.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;

@Singleton
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    @Transactional
    public Order createOrder(Order order) {
        validateProducts(order.getItems());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Optional<Order> updateOrderStatus(Long id, OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            return Optional.of(orderRepository.update(order));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean cancelOrder(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.update(order);
            return true;
        }
        return false;
    }

    private void validateProducts(List<OrderItem> items) {
        for (OrderItem item : items) {
            HttpResponse<Product> response = productClient.getProduct(item.getProductId());
            if (response.getStatus().getCode() != 200) {
                throw new RuntimeException("Product not found: " + item.getProductId());
            }
            Product product = response.body();
            if (product != null && !product.getPrice().equals(item.getPrice())) {
                throw new RuntimeException("Price mismatch for product: " + item.getProductId());
            }
        }
    }
}
