package org.example.dao;


import java.util.List;

import org.example.mapper.CartsMapper;
import org.example.model.Cart;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(CartsMapper.class)
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
