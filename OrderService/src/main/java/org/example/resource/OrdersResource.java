package org.example.resource;

import com.codahale.metrics.annotation.Timed;
import org.eclipse.jetty.http.HttpStatus;
import org.example.Dto.OrderDto;
import org.example.Representation;
import org.example.model.Cart;
import org.example.model.Order;
import org.example.model.OrderProduct;
import org.example.model.Product;
import org.example.service.CartsService;
import org.example.service.OrderProductsService;
import org.example.service.OrdersService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {
    private final OrdersService ordersService;
    private final OrderProductsService orderProductsService;
    private final CartsService cartsService;

    /* Dummy data for products, as this will be replaced by List of products
     coming from product microservice */
    private final Map<Long, Product> productsMap = new HashMap<>();

    public OrdersResource(OrdersService ordersService, OrderProductsService orderProductsService, CartsService cartsService) {
        this.ordersService = ordersService;
        this.orderProductsService = orderProductsService;
        this.cartsService = cartsService;
        productsMap.put(1L, new Product(1L, 10, 1000L));
        productsMap.put(4L, new Product(4L, 40, 500L));
        productsMap.put(3L, new Product(6L, 30, 800L));
    }

    @GET
    @Timed
    public Representation<List<Order>> getOrders() {
        return new Representation<List<Order>>(HttpStatus.OK_200, ordersService.getAllOrders());
    }

    @GET
    @Timed
    @Path("{orderId}")
    public Representation<Order> getOrderById(@PathParam("orderId") final long orderId) {
        return new Representation<Order>(HttpStatus.OK_200, ordersService.getOrderById(orderId));
    }

    @POST
    @Timed
    public Representation<Order> placeOrder(@NotNull @Valid final OrderDto orderDto) {
        List<Cart> items = cartsService.getProductIds(orderDto.getCartId());
        List<OrderProduct> orderProductsList = new ArrayList<>();

        for (Cart item : items) {
            System.out.println(item.toString());
        }

        for (Cart item : items) {
            Product product = productsMap.get(item.getProductId());
            if (item.getQuantity() <= product.getQuantity()) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setPrice(item.getPrice());
                orderProduct.setQuantity(item.getQuantity());
                orderProduct.setProductId(item.getProductId());
                orderProductsList.add(orderProduct);
            }
            else {
                throw new WebApplicationException("Product out of stock", Response.Status.INTERNAL_SERVER_ERROR);
            }
        }

        long totalCost = orderProductsList.stream()
                .mapToLong(orderProduct -> orderProduct.getPrice() * orderProduct.getQuantity())
                .sum();

        Order order = new Order();
        order.setProducts(items.stream().map(Cart::getProductId).collect(Collectors.toList()));
        order.setCreatedAt(LocalDateTime.now());
        order.setUserId(orderDto.getUserId());
        order.setTotalCost(totalCost);
        System.out.println("Debugging");
        Order orderRepresentation = ordersService.createOrder(order);
        Long orderId = ordersService.getLastInsertedId();
        System.out.println(orderId);

        for (OrderProduct orderProduct : orderProductsList) {
            orderProduct.setOrderId(orderId);
            orderProductsService.createOrderProduct(orderProduct);
        }
        orderRepresentation.setOrderId(orderId);
        return new Representation<Order>(HttpStatus.OK_200, orderRepresentation);
    }

    @DELETE
    @Timed
    @Path("{id}")
    public Representation<String> deleteOrder(@PathParam("id") final long orderId) {
        return new Representation<String>(HttpStatus.OK_200, ordersService.deleteOrder(orderId));
    }


}
