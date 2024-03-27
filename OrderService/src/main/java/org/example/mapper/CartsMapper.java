package org.example.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.resultsetmapper.ResultSetMapper;
import org.example.model.Cart;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;



public class CartsMapper implements RowMapper<Cart> {
    private static final String ID = "cartId";
    private static final String PRODUCT_ID = "productId";
    private static final String QUANTITY = "quantity";
    private static final String PRICE = "price";
    private static final String USER_ID = "userId";


    public Cart map(ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        return new Cart(resultSet.getLong(ID), resultSet.getLong(PRODUCT_ID), resultSet.getLong(USER_ID), resultSet.getInt(QUANTITY),
                resultSet.getLong(PRICE));
    }
}