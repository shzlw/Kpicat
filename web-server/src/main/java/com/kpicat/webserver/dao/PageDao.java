package com.kpicat.webserver.dao;

import com.kpicat.webserver.dao.mapper.PageRowMapper;
import com.kpicat.webserver.model.Component;
import com.kpicat.webserver.model.Page;
import com.kpicat.webserver.model.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PageDao {

    @Autowired
    JdbcTemplate jt;

    public int createPage(String pageId, String accountKey, String name, String description, String titleColor, String bgColor) {
        String sql = "INSERT INTO PAGE(page_id, account_key, name, description, title_color, bg_color) values(?, ?, ?, ?, ?, ?)";
        return jt.update(sql, new Object[]{pageId, accountKey, name, description, titleColor, bgColor});
    }

    public int updatePage(String pageId, String name, String description, String titleColor, String bgColor) {
        String sql = "UPDATE PAGE SET name=?, description=?, title_color=?, bg_color=? WHERE page_id=?";
        return jt.update(sql, new Object[]{name, description, titleColor, bgColor, pageId});
    }

    public int createRow(String pageId, String rowId, int sequence, int columns) {
        String sql = "INSERT INTO PAGE_ROW(page_id, row_id, sequence, col_num) values(?, ?, ?, ?)";
        return jt.update(sql, new Object[]{pageId, rowId, sequence, columns});
    }

    public int createComponent(String rowId, String accountKey, String componentId, int column) {
        String sql = "INSERT INTO COMPONENT(row_id, account_key, component_id, col) values(?, ?, ?, ?)";
        return jt.update(sql, new Object[]{rowId, accountKey, componentId, column});
    }

    public int updateRow(String rowId, int sequence) {
        String sql = "UPDATE PAGE_ROW SET sequence=? WHERE row_id=?";
        return jt.update(sql, new Object[]{ sequence, rowId });
    }

    public int deleteRow(String rowId) {
        String sql = "DELETE FROM PAGE_ROW WHERE row_id=?";
        return jt.update(sql, new Object[]{ rowId });
    }

    public int deleteComponentsByRow(String rowId) {
        String sql = "DELETE FROM COMPONENT WHERE row_id=?";
        return jt.update(sql, new Object[]{rowId});
    }

    public int deletePage(String pageId) {
        String sql = "DELETE FROM PAGE WHERE page_id=?";
        return jt.update(sql, new Object[]{ pageId });
    }

    public Page fetchPage(String pageId, String accountKey) {
        String sql = "SELECT page_id, name, description, title_color, bg_color FROM PAGE WHERE page_id=? AND account_key=?";
        try {
            Page page = (Page) jt.queryForObject(
                    sql, new Object[]{pageId, accountKey}, new PageMapper());
            return page;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Page> fetchPages(String accountKey) {
        String sql = "SELECT page_id, name, description, title_color, bg_color FROM PAGE WHERE account_key=? ORDER BY name ASC";
        List<Page> pages = jt.query(sql, new Object[] { accountKey }, new PageMapper());
        return pages;
    }

    public List<Component> fetchComponentsByRow(String rowId) {
        String sql = "SELECT component_id, col, type, data, last_update FROM COMPONENT WHERE row_id=? ORDER BY col ASC";
        List<Component> components = jt.query(sql, new Object[] { rowId }, new ComponentMapper());
        return components;
    }

    public List<Row> fetchRowsByPage(String pageId) {
        String sql = "SELECT row_id, sequence, col_num FROM PAGE_ROW WHERE page_id=? ORDER BY sequence ASC";
        List<Row> rows = jt.query(sql, new Object[] { pageId }, new PageRowMapper());
        return rows;
    }

    // mobile ***********************

    public List<Page> fetchPageForMobile(String mobileKey) {
        String sql = "SELECT p.page_id, p.name, p.description, p.title_color, p.bg_color FROM PAGE p, PAGE_ROLE r, USER u"
                + " WHERE u.mobile_key=?"
                + " AND u.role_id=r.role_id"
                + " AND r.page_id=p.page_id"
                + " ORDER BY p.name ASC";
        List<Page> pages = jt.query(sql, new Object[] { mobileKey }, new PageMapper());
        return pages;
    }
    
    public List<String> fetchPagesByRole(String roleId) {
        String sql = "SELECT page_id FROM PAGE_ROLE WHERE role_id=?";
        List<String> pageIds = jt.queryForList(sql, new Object[] { roleId }, String.class);
        return pageIds;
    }




    // **************************************
    private class ComponentMapper implements RowMapper<Component> {
        @Override
        public Component mapRow(ResultSet rs, int i) throws SQLException {
            Component c = new Component();
            c.setComponentId(rs.getString("component_id"));
            c.setColumn(rs.getInt("col"));
            c.setType(rs.getString("type"));
            c.setData(rs.getString("data"));
            c.setLastUpdate(rs.getLong("last_update"));
            return c;
        }
    }

    private class PageMapper implements RowMapper<Page> {
        @Override
        public Page mapRow(ResultSet rs, int i) throws SQLException {
            Page p = new Page();
            p.setPageId(rs.getString("page_id"));
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setTitleColor(rs.getString("title_color"));
            p.setBgColor(rs.getString("bg_color"));
            return p;
        }
    }

}
