package org.example.service;

import org.example.dao.CartsDao;
import org.example.dao.OrderProductsDao;
import org.example.dao.OrdersDao;
import org.example.model.Order;
import org.example.model.OrderProduct;
import org.jdbi.v3.sqlobject.CreateSqlObject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

public  class OrdersService {

    private static final String ORDER_NOT_FOUND = "Order id %s not found.";

    private OrderProductsDao orderProductsDao;
    private OrdersDao ordersDao;

    public OrdersService(OrderProductsDao orderProductsDao, OrdersDao ordersDao) {
        this.orderProductsDao = orderProductsDao;
        this.ordersDao = ordersDao;
    }

    public Order createOrder(Order order) {
        ordersDao.createOrder(order);
        return order;
    }

    public Long getLastInsertedId() {
        return (long) ordersDao.lastInsertId();
    }

    public List<Order> getAllOrders() {
        return ordersDao.getAllOrders();
    }

    public Order getOrderById(Long orderId) {
        return ordersDao.getOrder(orderId);
    }
    public String deleteOrder(final long id) {
        int result = ordersDao.deleteOrder(id);
        switch (result) {
            case 1: {
                orderProductsDao.deleteOrderProduct(id);
                return "Success......";
            }
            case 0:
                throw new WebApplicationException(String.format(ORDER_NOT_FOUND, id), Response.Status.NOT_FOUND);
            default:
                throw new WebApplicationException("Unexpected error occurred", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


}
