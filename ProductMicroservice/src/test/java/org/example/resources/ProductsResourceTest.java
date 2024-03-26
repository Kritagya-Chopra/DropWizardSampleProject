package org.example.resources;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.example.DTO.OrderEntry;
import org.example.entity.Product;
import org.example.service.ProductsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ProductsResourceTest {
    private static final ProductsService service = mock(ProductsService.class);

    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new ProductsResource(service))
            .build();
    private Product product;

    @BeforeEach
    void setup() {
        product = new Product();
        product.setProductID(1);
    }

    @AfterEach
    void tearDown() {
        reset(service);
    }

    @Test
    void testGetProducts_NoIds() {
        List<Product> productList = Arrays.asList(
                new Product(1, "Product 1", 10, 0f, "P1", 10.0, "Description 1", null, null, 1, 1),
                new Product(2, "Product 2", 20, 0f, "P2", 20.0, "Description 2", null, null, 2, 2)
        );
        when(service.getProducts()).thenReturn(productList);

        Response response = EXT.target("/products").request().get();

        assertThat(response.getStatus(), equalTo(Response.Status.OK.getStatusCode()));

//        assertEquals(response.readEntity(ResponseDTO.class).getData(), productList);
    }

    @Test
    void testGetProducts_WithIds() {
        List<Product> productList = Arrays.asList(
                new Product(1, "Product 1", 10, 0f, "P1", 10.0, "Description 1", LocalDateTime.now(), LocalDateTime.now(), 1, 1),
                new Product(2, "Product 2", 20, 0f, "P2", 20.0, "Description 2", LocalDateTime.now(), LocalDateTime.now(), 2, 2));
        List<Integer> ids = Arrays.asList(1, 2);
        when(service.getProducts(ids)).thenReturn(productList);

        Response response = EXT.target("/products").queryParam("ids", "1" ).queryParam("ids" , "2").request().get();

        assertThat(response.getStatus(), equalTo(Response.Status.OK.getStatusCode()));
//        assertThat(response.readEntity(ResponseDTO.class), equalTo(productList));
    }

    @Test
    void testCreateProduct() {
        when(service.createProduct(any(Product.class))).thenReturn(product);

        Response response = EXT.target("/products").request().post(javax.ws.rs.client.Entity.json(product));

        assertThat(response.getStatus(), equalTo(Response.Status.OK.getStatusCode()));
//        assertThat(response.readEntity(ResponseDTO.class).getData(), equalTo(product));
    }

    @Test
    void testCreateProduct_InvalidInput() {
        when(service.createProduct(any(Product.class))).thenThrow(new WebApplicationException("Invalid product", Response.Status.BAD_REQUEST));

        Response response = EXT.target("/products").request().post(javax.ws.rs.client.Entity.json(new Product()));

        assertThat(response.getStatus(), equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    void testEditProduct() {
        when(service.editProduct(any(Product.class))).thenReturn(product);

        Response response = EXT.target("/products/1").request().put(javax.ws.rs.client.Entity.json(product));

        assertThat(response.getStatus(), equalTo(Response.Status.OK.getStatusCode()));
//        assertThat(response.readEntity(ResponseDTO.class).getData(), equalTo(product));
    }

    @Test
    void testEditProduct_ProductNotFound() {
        when(service.editProduct(any(Product.class))).thenThrow(new WebApplicationException("Product not found", Response.Status.NOT_FOUND));

        Response response = EXT.target("/products/1").request().put(javax.ws.rs.client.Entity.json(product));

        assertThat(response.getStatus(), equalTo(Response.Status.NOT_FOUND.getStatusCode()));
    }

    @Test
    void testDeleteProduct() {
        when(service.deleteProduct(1)).thenReturn("Success");

        Response response = EXT.target("/products/1").request().delete();

        assertThat(response.getStatus(), equalTo(Response.Status.OK.getStatusCode()));
//        assertThat(response.readEntity(ResponseDTO.class).getData(), equalTo("Success"));
    }

    @Test
    void testDeleteProduct_ProductNotFound() {
        when(service.deleteProduct(1)).thenThrow(new WebApplicationException("Product not found", Response.Status.NOT_FOUND));

        Response response = EXT.target("/products/1").request().delete();

        assertThat(response.getStatus(), equalTo(Response.Status.NOT_FOUND.getStatusCode()));
    }

    @Test
    void testPlaceOrder() {
        List<OrderEntry> orderEntries = Arrays.asList(new OrderEntry(1, 5), new OrderEntry(2, 10));
        when(service.placeOrder(orderEntries)).thenReturn("Updated Successfully");

        Response response = EXT.target("/products/order").request().put(javax.ws.rs.client.Entity.json(orderEntries));

        assertThat(response.getStatus(), equalTo(Response.Status.OK.getStatusCode()));
//        assertThat(response.readEntity(ResponseDTO.class).getData(), equalTo("Updated Successfully"));
    }

}
