package org.example.dao;


import java.util.List;

import org.example.mapper.CartsMapper;
import org.example.model.Cart;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface CartsDao {

    @SqlQuery("select * from cart;")
    public List<Cart> getAll();

    @SqlQuery("select * from cart where cartId = :cartId")
    public List<Cart> getProductIds(@Bind("cartId") final long cartId);

    @SqlQuery("select * from cart where cartId = :cartId")
    public Cart getCart(@Bind("cartId") final int cartId);

    @SqlUpdate("insert into cart(cartId, productId, userId, quantity, price) values(:cartId, :productId, :userId, :quantity, :price)")
    void createCart(@BindBean final Cart cart);

    @SqlUpdate("delete from cart where productId = :id")
    int deleteCart(@Bind("id") final int id);

}
