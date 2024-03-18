package com.assignment.db;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserMap implements org.jdbi.v3.core.mapper.RowMapper<UserEntity>{

    @Override
    public UserEntity map(ResultSet resultSet, StatementContext ctx) throws SQLException {
        return new UserEntity(resultSet.getLong("id"), resultSet.getString("name"),resultSet.getString("email"),resultSet.getString("password"),resultSet.getString("city"), resultSet.getDate("dob").toLocalDate(),resultSet.getString("address"),resultSet.getInt("pincode"),resultSet.getString("phoneno") );

    }


}
