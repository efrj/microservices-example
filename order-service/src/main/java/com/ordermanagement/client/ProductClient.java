package com.ordermanagement.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.HttpResponse;
import com.ordermanagement.model.Product;

@Client("http://localhost:8080")
public interface ProductClient {
    @Get("/api/products/{id}")
    HttpResponse<Product> getProduct(Long id);
}
