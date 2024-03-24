package org.example.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.example.model.Order;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;


public class OrdersMapper implements RowMapper<Order> {
    private static final String ID = "orderId";
    private static final String USER_ID = "userId";
    private static final String CREATED_AT = "createdAt";
    private static final String TOTAL_COST = "totalCost";



    public Order map(ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        return new Order(resultSet.getLong(ID), resultSet.getLong(USER_ID), resultSet.getTimestamp(CREATED_AT).toLocalDateTime(),
                resultSet.getLong(TOTAL_COST), new ArrayList<>()
                );
    }
}