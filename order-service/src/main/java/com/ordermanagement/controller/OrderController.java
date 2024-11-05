package com.ordermanagement.controller;

import com.ordermanagement.model.Order;
import com.ordermanagement.model.OrderStatus;
import com.ordermanagement.service.OrderService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;

@Controller("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Post
    public HttpResponse<Order> createOrder(@Body Order order) {
        return HttpResponse.created(orderService.createOrder(order));
    }

    @Get
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Get("/{id}")
    public HttpResponse<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Put("/{id}/status")
    public HttpResponse<Order> updateOrderStatus(@PathVariable Long id, @Body OrderStatus status) {
        return orderService.updateOrderStatus(id, status)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Delete("/{id}")
    public HttpResponse<?> cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id)
                ? HttpResponse.noContent()
                : HttpResponse.notFound();
    }
}
