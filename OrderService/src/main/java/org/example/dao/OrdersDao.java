package org.example.dao;


import org.example.mapper.CartsMapper;
import org.example.mapper.OrdersMapper;
import org.example.model.Order;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(OrdersMapper.class)

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
