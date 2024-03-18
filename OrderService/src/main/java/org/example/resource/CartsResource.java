package org.example.resource;

import com.codahale.metrics.annotation.Timed;
import org.eclipse.jetty.http.HttpStatus;
import org.example.Representation;
import org.example.model.Cart;
import org.example.service.CartsService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/cart")
@Produces(MediaType.APPLICATION_JSON)
public class CartsResource {
    private final CartsService cartsService;

    public CartsResource(CartsService cartsService) {
        this.cartsService = cartsService;
    }

    @GET
    @Timed
    public Representation<List<Cart>> getCarts() {
        return new Representation<List<Cart>>(HttpStatus.OK_200, cartsService.getAll());
    }

    // Add product to the cart
    @POST
    @Timed
    public Representation<Cart> addProductToCart(@NotNull @Valid final Cart cart) {
        System.out.println("Debugging");
        return new Representation<Cart>(HttpStatus.OK_200, cartsService.createCart(cart));
    }

    // Remove product from the cart
    @DELETE
    @Timed
    @Path("{id}")
    public Representation<String> deletePart(@PathParam("id") final int productId) {
        return new Representation<String>(HttpStatus.OK_200, cartsService.deletePart(productId));
    }
}
