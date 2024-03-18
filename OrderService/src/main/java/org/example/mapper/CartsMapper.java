package org.example.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.model.Cart;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;


public class CartsMapper implements ResultSetMapper<Cart> {
    private static final String ID = "cartId";
    private static final String PRODUCT_ID = "productId";
    private static final String QUANTITY = "quantity";
    private static final String PRICE = "price";
    private static final String USER_ID = "userId";


    public Cart map(int i, ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        return new Cart(resultSet.getLong(ID), resultSet.getLong(PRODUCT_ID), resultSet.getLong(USER_ID), resultSet.getInt(QUANTITY),
                resultSet.getLong(PRICE));
    }
}