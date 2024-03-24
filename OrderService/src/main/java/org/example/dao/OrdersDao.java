package org.example.dao;


import org.example.mapper.CartsMapper;
import org.example.mapper.OrdersMapper;
import org.example.model.Order;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;


public interface OrdersDao {

    @SqlQuery("select * from orders;")
    public List<Order> getAllOrders();

    @SqlQuery("select * from orders where orderId = :orderId")
    public Order getOrder(@Bind("orderId") final long cartId);

    @SqlUpdate("insert into orders(userId,  totalCost) values(:userId, :totalCost)")
    void createOrder(@BindBean final Order order);


    @SqlUpdate("delete from orders where orderID = :id")
    int deleteOrder(@Bind("id") final long id);

    @SqlQuery("select last_insert_id();")
    public int lastInsertId();
}
