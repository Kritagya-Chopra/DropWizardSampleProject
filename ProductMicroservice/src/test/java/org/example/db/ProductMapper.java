package org.example.db;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.example.entity.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Product(
                rs.getInt("productID"),
                rs.getString("name"),
                rs.getInt("quantity"),
                rs.getFloat("rating"),
                rs.getString("code"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getObject("createdAt", LocalDateTime.class),
                rs.getObject("updatedAt", LocalDateTime.class),
                rs.getInt("createdBy"),
                rs.getInt("updatedBy")
        );
    }
}
