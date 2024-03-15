package org.example.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.http.HttpStatus;

import com.codahale.metrics.annotation.Timed;
import org.example.DTO.ResponseDTO;
import org.example.DTO.OrderEntry;
import org.example.entity.Product;
import org.example.service.ProductsService;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductsResource {
    private final ProductsService productsService;

    public ProductsResource(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GET
    @Timed
    public ResponseDTO<List<Product>> getProducts(@QueryParam("ids") final List<Integer> ids) {
        if (ids.isEmpty())
            return new ResponseDTO<List<Product>>(HttpStatus.OK_200, productsService.getProducts());
        return new ResponseDTO<>(HttpStatus.OK_200, productsService.getProducts(ids));
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseDTO<Product> createProduct(@NotNull final Product product) {
        return new ResponseDTO<Product>(HttpStatus.OK_200, productsService.createProduct(product));
    }

    @PUT
    @Timed
    @Path("{id}")
    public ResponseDTO<Product> editProduct(@NotNull final Product product,
                                            @PathParam("id") final int id) {
        product.setProductID(id);
        return new ResponseDTO<Product>(HttpStatus.OK_200, productsService.editProduct(product));
    }

    @DELETE
    @Timed
    @Path("{id}")
    public ResponseDTO<String> deleteProduct(@PathParam("id") final int id) {
        return new ResponseDTO<String>(HttpStatus.OK_200, productsService.deleteProduct(id));
    }

    @PUT
    @Timed
    @Path("/order")
    public ResponseDTO<String> placeOrder(@NotNull @Valid final List<OrderEntry> orderEntries)
    {
        return new ResponseDTO<>(HttpStatus.OK_200,productsService.placeOrder(orderEntries));
    }
}