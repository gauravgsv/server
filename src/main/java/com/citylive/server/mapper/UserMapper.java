package com.citylive.server.mapper;

import com.citylive.server.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int arg1) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setUserName(rs.getString("userName"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}
