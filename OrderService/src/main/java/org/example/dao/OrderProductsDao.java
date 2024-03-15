package org.example.dao;


import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.example.mapper.CartsMapper;
import org.example.model.Cart;
import org.example.model.OrderProduct;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;


public interface OrderProductsDao {

    @SqlQuery("select * from orderproducts;")
    public List<OrderProduct> getOrderProducts();

    @SqlUpdate("insert into orderproducts(orderId, productId, quantity, price) values(:orderId, :productId, :quantity, :price)")
    void createCart(@BindBean final OrderProduct orderProduct);

    @SqlUpdate("delete from orderproducts where orderId = :id")
    int deleteOrderProduct(@Bind("id") final long id);

}
