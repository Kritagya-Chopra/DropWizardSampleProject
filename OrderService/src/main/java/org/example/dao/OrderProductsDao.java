package org.example.dao;


import java.util.List;

import org.example.mapper.CartsMapper;
import org.example.model.Cart;
import org.example.model.OrderProduct;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;


public interface OrderProductsDao {

    @SqlQuery("select * from orderproducts;")
    public List<OrderProduct> getOrderProducts();

    @SqlUpdate("insert into orderproducts(orderId, productId, quantity, price) values(:orderId, :productId, :quantity, :price)")
    void createCart(@BindBean final OrderProduct orderProduct);

    @SqlUpdate("delete from orderproducts where orderId = :id")
    int deleteOrderProduct(@Bind("id") final long id);

}
