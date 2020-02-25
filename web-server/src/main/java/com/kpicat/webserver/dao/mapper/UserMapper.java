package com.kpicat.webserver.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kpicat.webserver.model.User;

public class UserMapper implements RowMapper<User> {
    
    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User u = new User();
        u.setUserId(rs.getString("user_id"));
        u.setUsername(rs.getString("username"));
        u.setCorpRole(rs.getString("role_id"));
        return u;
    }
}
