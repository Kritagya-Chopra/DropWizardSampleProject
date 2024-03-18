package org.example.service;

import org.example.dao.OrderProductsDao;
import org.example.model.Cart;
import org.example.model.OrderProduct;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

public abstract class OrderProductsService {
    @CreateSqlObject
    abstract OrderProductsDao orderProductsDao();

    public OrderProduct createOrderProduct(OrderProduct orderProduct) {
        orderProductsDao().createCart(orderProduct);
        return orderProduct;
    }

    public List<OrderProduct> getAll() {
        return orderProductsDao().getOrderProducts();
    }

    public String deleteOrderProduct(final int orderId) {
        int result =orderProductsDao().deleteOrderProduct(orderId);
        switch (result) {
            case 1:
                return "Success.....";
            case 0:
                throw new WebApplicationException("Entry not found in db", Response.Status.NOT_FOUND);
            default:
                throw new WebApplicationException("Unexpected error occurred", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
