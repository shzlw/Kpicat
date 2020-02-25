package com.kpicat.webserver.dao;

import com.kpicat.webserver.dao.mapper.RoleMapper;
import com.kpicat.webserver.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDao {

    @Autowired
    JdbcTemplate jt;

    public int createRole(String roleId, String accountKey, String name) {
        String sql = "INSERT INTO USER_ROLE(role_id, account_key, name) values(?, ?, ?)";
        return jt.update(sql, new Object[]{roleId, accountKey, name});
    }

    public int updateRole(String roleId, String name) {
        String sql = "UPDATE USER_ROLE SET name=? WHERE role_id=?";
        return jt.update(sql, new Object[]{name, roleId});
    }

    public int createPageRole(String pageId, String roleId) {
        String sql = "INSERT INTO PAGE_ROLE(page_id, role_id) values(?, ?)";
        return jt.update(sql, new Object[]{pageId, roleId});
    }

    public List<Role> fetchRoles(String accountKey) {
        String sql = "SELECT role_id, name FROM USER_ROLE WHERE account_key=? ORDER BY name ASC";
        List<Role> roles = jt.query(sql, new Object[] { accountKey }, new RoleMapper());
        return roles;
    }

    public Role fetchRole(String roleId, String accountKey) {
        String sql = "SELECT role_id, name FROM USER_ROLE WHERE role_id=? AND account_key=?";
        try {
            Role role = (Role) jt.queryForObject(sql, new Object[]{ roleId, accountKey }, new RoleMapper());
            return role;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int deleteRole(String roleId) { 
        String sql = "DELETE FROM USER_ROLE WHERE role_id=?";
        return jt.update(sql, new Object[]{ roleId });
    }

    public int deletePageRoleByPage(String pageId) {
        String sql = "DELETE FROM PAGE_ROLE WHERE page_id=?";
        return jt.update(sql, new Object[]{ pageId });
    }

    public int deletePageRoleByRole(String roleId) {
        String sql = "DELETE FROM PAGE_ROLE WHERE role_id=?";
        return jt.update(sql, new Object[]{ roleId });
    }
}
