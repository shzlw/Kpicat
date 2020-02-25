package com.kpicat.webserver.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kpicat.webserver.model.Role;

public class RoleMapper implements RowMapper<Role> {
    
    @Override
    public Role mapRow(ResultSet rs, int i) throws SQLException {
        Role r = new Role();
        r.setRoleId(rs.getString("role_id"));
        r.setName(rs.getString("name"));
        return r;
    }
}
