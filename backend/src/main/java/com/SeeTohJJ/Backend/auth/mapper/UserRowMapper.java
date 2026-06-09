package com.SeeTohJJ.Backend.auth.mapper;

import com.SeeTohJJ.Backend.auth.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {

        User user = new User();

        user.setUserId(rs.getLong("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(
                User.Role.valueOf(
                        rs.getString("role")
                )
        );

        return user;
    }
}
