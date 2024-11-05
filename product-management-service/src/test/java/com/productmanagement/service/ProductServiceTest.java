package com.productmanagement.service;

import com.productmanagement.model.Product;
import com.productmanagement.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnAllProducts() {
        // Arrange
        List<Product> expectedProducts = Arrays.asList(
            new Product(1L, "Product 1", "Description 1", new BigDecimal("10.00"), 100),
            new Product(2L, "Product 2", "Description 2", new BigDecimal("20.00"), 200)
        );
        when(productRepository.findAll()).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = productService.findAll();

        // Assert
        assertEquals(expectedProducts, actualProducts);
        verify(productRepository).findAll();
    }

    @Test
    void findById_ExistingId_ShouldReturnProduct() {
        // Arrange
        Long id = 1L;
        Product expectedProduct = new Product(id, "Product 1", "Description 1", new BigDecimal("10.00"), 100);
        when(productRepository.findById(id)).thenReturn(Optional.of(expectedProduct));

        // Act
        Product actualProduct = productService.findById(id);

        // Assert
        assertEquals(expectedProduct, actualProduct);
        verify(productRepository).findById(id);
    }

    @Test
    void findById_NonExistingId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productService.findById(id));
        verify(productRepository).findById(id);
    }

    @Test
    void create_NewProduct_ShouldSaveAndReturnProduct() {
        // Arrange
        Product newProduct = new Product(null, "New Product", "New Description", new BigDecimal("30.00"), 300);
        Product savedProduct = new Product(1L, "New Product", "New Description", new BigDecimal("30.00"), 300);
        when(productRepository.existsByNameIgnoreCase(newProduct.getName())).thenReturn(false);
        when(productRepository.save(newProduct)).thenReturn(savedProduct);

        // Act
        Product createdProduct = productService.create(newProduct);

        // Assert
        assertEquals(savedProduct, createdProduct);
        verify(productRepository).existsByNameIgnoreCase(newProduct.getName());
        verify(productRepository).save(newProduct);
    }

    @Test
    void create_DuplicateProductName_ShouldThrowDuplicateKeyException() {
        // Arrange
        Product newProduct = new Product(null, "Existing Product", "Description", new BigDecimal("10.00"), 100);
        when(productRepository.existsByNameIgnoreCase(newProduct.getName())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateKeyException.class, () -> productService.create(newProduct));
        verify(productRepository).existsByNameIgnoreCase(newProduct.getName());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void update_ExistingProduct_ShouldUpdateAndReturnProduct() {
        // Arrange
        Long id = 1L;
        Product existingProduct = new Product(id, "Existing Product", "Old Description", new BigDecimal("10.00"), 100);
        Product updatedProduct = new Product(id, "Updated Product", "New Description", new BigDecimal("20.00"), 200);
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        Product result = productService.update(id, updatedProduct);

        // Assert
        assertEquals(updatedProduct, result);
        verify(productRepository).findById(id);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void update_NonExistingProduct_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long id = 1L;
        Product updatedProduct = new Product(id, "Updated Product", "New Description", new BigDecimal("20.00"), 200);
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productService.update(id, updatedProduct));
        verify(productRepository).findById(id);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void delete_ExistingProduct_ShouldDeleteProduct() {
        // Arrange
        Long id = 1L;
        Product existingProduct = new Product(id, "Existing Product", "Description", new BigDecimal("10.00"), 100);
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));

        // Act
        productService.delete(id);

        // Assert
        verify(productRepository).findById(id);
        verify(productRepository).delete(existingProduct);
    }

    @Test
    void delete_NonExistingProduct_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productService.delete(id));
        verify(productRepository).findById(id);
        verify(productRepository, never()).delete(any(Product.class));
    }
}
