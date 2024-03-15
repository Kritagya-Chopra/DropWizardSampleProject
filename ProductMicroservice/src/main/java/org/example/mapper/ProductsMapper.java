package org.example.mapper;

import org.example.entity.Product;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;



public class ProductsMapper implements RowMapper<Product> {
    private static final String ID = "productID";
    private static final String NAME = "Name";
    private static final String QUANTITY = "Quantity";
    private static final String RATING = "Rating";
    private static final String CODE = "Code";
    private static final String PRICE = "Price";
    private static final String DESCRIPTION = "Description";
    private static final String CREATED_AT = "CreatedAt";
    private static final String UPDATED_AT = "UpdatedAt";
    private static final String CREATED_BY = "CreatedBy";
    private static final String UPDATED_BY = "UpdatedBy";

    @Override
    public Product map(ResultSet resultSet, StatementContext ctx) throws SQLException {
        return new Product(resultSet.getInt(ID), resultSet.getString(NAME),resultSet.getInt(QUANTITY),resultSet.getFloat(RATING),resultSet.getString(CODE),
                resultSet.getDouble(PRICE),resultSet.getString(DESCRIPTION),resultSet.getTimestamp(CREATED_AT).toLocalDateTime() ,resultSet.getTimestamp(UPDATED_AT).toLocalDateTime(), resultSet.getInt(CREATED_BY),resultSet.getInt(UPDATED_BY));
    }
}
