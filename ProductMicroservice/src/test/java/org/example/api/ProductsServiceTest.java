package org.example.api;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.example.DTO.OrderEntry;
import org.example.db.ProductsDao;
import org.example.entity.Product;
import org.example.service.ProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {

    @Mock
    private ProductsDao productsDao;

    @InjectMocks
    private ProductsService productsService;

    @Test
    public void testGetProducts_ReturnsListOfProducts() {
        // Arrange
        List<Product> expectedProducts = Collections.singletonList(new Product());
        when(productsDao.getProducts()).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = productsService.getProducts();

        // Assert
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testGetProducts_WithId_ReturnsListOfProduct() {
        // Arrange
        when(productsDao.getProduct(anyInt())).thenReturn(new Product());
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        // Act
        List<Product> p = productsService.getProducts(ids);

        // Assert
        assertNotNull(p);
        assertEquals(ids.size(), p.size());
    }

    @Test
    public void testGetProducts_WithNonExistingIds_ThrowsWebApplicationException() {
        // Arrange
        when(productsDao.getProduct(anyInt())).thenReturn(null);
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        String expectedMessage = "Product id 1 not found.";

        // Act & Assert
        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            productsService.getProducts(ids);
        });
        assertEquals(Status.NOT_FOUND.getStatusCode(), exception.getResponse().getStatus());
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testCreateProduct_CreatesProductSuccessfully() {
        // Arrange
        Product product = new Product();
        doNothing().when(productsDao).createProduct(product);
        when(productsDao.lastInsertId()).thenReturn(1);
        when(productsDao.getProduct(anyInt())).thenReturn(product);

        // Act
        Product createdProduct = productsService.createProduct(product);

        // Assert
        assertNotNull(createdProduct);
        assertEquals(product, createdProduct);
    }

    @Test
    public void testEditProduct_EditsProductSuccessfully() {
        // Arrange
        Product product = new Product();
        product.setProductID(1);
        when(productsDao.getProduct(product.getProductID())).thenReturn(product);

        // Act
        Product editedProduct = productsService.editProduct(product);

        // Assert
        assertNotNull(editedProduct);
        assertEquals(product, editedProduct);
    }

    @Test
    public void testEditProduct_WithNonExistingProduct_ThrowsWebApplicationException() {
        // Arrange
        Product product = new Product();
        product.setProductID(1);
        when(productsDao.getProduct(product.getProductID())).thenReturn(null);

        // Act & Assert
        assertThrows(WebApplicationException.class, () -> {
            productsService.editProduct(product);
        });
    }

    @Test
    public void testDeleteProduct_DeletesProductSuccessfully() {
        // Arrange
        int productId = 1;
        when(productsDao.deleteProduct(productId)).thenReturn(1);

        // Act
        String result = productsService.deleteProduct(productId);

        // Assert
        assertEquals("Success...", result);
    }

    @Test
    public void testDeleteProduct_WithNonExistingProduct_ThrowsWebApplicationException() {
        // Arrange
        int productId = 1;
        when(productsDao.deleteProduct(productId)).thenReturn(0);

        // Act & Assert
        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            productsService.deleteProduct(productId);
        });
        assertEquals(Status.NOT_FOUND.getStatusCode(), exception.getResponse().getStatus());
        assertEquals(String.format("Product id %d not found.", productId), exception.getMessage());
    }

    @Test
    public void testDeleteProduct_WithUnexpectedError_ThrowsWebApplicationException() {
        // Arrange
        int productId = 1;
        when(productsDao.deleteProduct(productId)).thenReturn(-1);

        // Act & Assert
        assertThrows(WebApplicationException.class, () -> {
            productsService.deleteProduct(productId);
        });
    }

    @Test
    public void testPlaceOrder_UpdatesQuantitySuccessfully() {
        // Arrange
        OrderEntry entry = new OrderEntry();
        entry.setId(1);
        entry.setQuantity(10);
        doNothing().when(productsDao).updateQuantity(entry.getId(), entry.getQuantity());

        // Act
        String result = productsService.placeOrder(Collections.singletonList(entry));

        // Assert
        assertEquals("Updated Successfully", result);
    }

    @Test
    public void testPlaceOrder_WithInsufficientStock_ThrowsWebApplicationException() {
        // Arrange
        OrderEntry entry = new OrderEntry();
        entry.setId(1);
        entry.setQuantity(10);
        doThrow(new RuntimeException("Check constraint 'products_chk_1' is violated")).when(productsDao).updateQuantity(entry.getId(), entry.getQuantity());

        // Act & Assert
        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            productsService.placeOrder(Collections.singletonList(entry));
        });
        assertEquals(Status.NOT_FOUND.getStatusCode(), exception.getResponse().getStatus());
    }

    @Test
    public void testPlaceOrder_WithNonExistingProduct_ThrowsWebApplicationException() {
        // Arrange
        OrderEntry entry = new OrderEntry();
        entry.setId(1);
        entry.setQuantity(10);
        doThrow(new WebApplicationException()).when(productsDao).updateQuantity(entry.getId(), entry.getQuantity());
        String expectedMessage = "Product id 1 not found.";

        // Act & Assert
        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            productsService.placeOrder(Collections.singletonList(entry));
        });
        assertEquals(Status.NOT_FOUND.getStatusCode(), exception.getResponse().getStatus());
        assertEquals(expectedMessage, exception.getMessage());
    }
}
