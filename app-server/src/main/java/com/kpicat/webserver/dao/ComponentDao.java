package com.kpicat.webserver.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ComponentDao {

    @Autowired
    JdbcTemplate jt;
    
    public String getData(String componentId, String accountKey) {
        String sql = "SELECT data FROM COMPONENT WHERE account_key=? AND component_id=?";
        String data = "";
        try {
            data = jt.queryForObject(sql, new Object[] { accountKey, componentId }, String.class);
        } catch (EmptyResultDataAccessException e) { }
        return data;
    }
}
