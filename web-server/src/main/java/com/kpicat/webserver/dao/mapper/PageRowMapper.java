package com.kpicat.webserver.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kpicat.webserver.model.Row;

public class PageRowMapper implements RowMapper<Row> {
    
    @Override
    public Row mapRow(ResultSet rs, int i) throws SQLException {
        Row r = new Row();
        r.setRowId(rs.getString("row_id"));
        r.setSequence(rs.getInt("sequence"));
        r.setColumns(rs.getInt("col_num"));
        return r;
    }
}
