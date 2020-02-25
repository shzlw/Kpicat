package com.kpicat.webserver.service;

import com.kpicat.webserver.dao.mapper.PageRowMapper;
import com.kpicat.webserver.model.Component;
import com.kpicat.webserver.model.Configuration;
import com.kpicat.webserver.model.Page;
import com.kpicat.webserver.model.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class DataManager {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static String getCurrentDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    public String isCorpNameExist(String corpName) {
        String sql = "SELECT corp_name FROM USER WHERE corp_name=?";
        try {
            String rt = jdbcTemplate.queryForObject(sql, new Object[]{corpName}, String.class);
            return rt;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String isEmailExist(String email) {
        String sql = "SELECT email FROM USER WHERE email=?";
        try {
            String rt = jdbcTemplate.queryForObject(sql, new Object[]{email}, String.class);
            return rt;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String isUserExist(String email, String password) {
        String sql = "SELECT email FROM USER WHERE email=? AND password=? AND sys_role=?";
        try {
            String rt = jdbcTemplate.queryForObject(sql, new Object[]{email, password, Constants.ADMIN}, String.class);
            return rt;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String isUserExist(String username, String password, String corpName) {
        String sql = "SELECT username FROM USER WHERE username=? AND password=? AND corp_name=?";
        try {
            String rt = jdbcTemplate.queryForObject(sql, new Object[]{username, password, corpName}, String.class);
            return rt;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int updateMobileKey(String mobileKey, String username, String password, String corpName) {
        String sql = "UPDATE USER SET mobile_key=? WHERE username=? AND password=? AND corp_name=?";
        return jdbcTemplate.update(sql, new Object[]{ mobileKey, username, password, corpName});
    }

    public int updateSessionKey(String sessionKey, String email, String password) {
        String sql = "UPDATE USER SET session_key=? WHERE email=? AND password=?";
        return jdbcTemplate.update(sql, new Object[]{sessionKey, email, password});
    }

    public int createSysUser(String userId, String email, String username, String password, String sysRole,
                             String corpName, String apiKey, String accountKey, String membership) {
        String sql = "INSERT INTO USER(user_id, email, username, password, sys_role, corp_name, api_key, account_key, membership)"
                   + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[]{userId, email, username, password, sysRole, corpName, apiKey, accountKey, membership});
    }

    public String fetchAccountKeyBySessionKey(String sessionKey) {
        String sql = "SELECT account_key FROM USER WHERE session_key=?";
        try {
            String accountKey = jdbcTemplate.queryForObject(sql, new Object[]{sessionKey}, String.class);
            return accountKey;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String fetchAccountKeyByMoibleKey(String mobileKey) {
        String sql = "SELECT account_key FROM USER WHERE mobile_key=?";
        try {
            String accountKey = jdbcTemplate.queryForObject(sql, new Object[]{ mobileKey }, String.class);
            return accountKey;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    // **********************************************


    public int createPage(String pageId, String accountKey, String name, String description) {
        String sql = "INSERT INTO PAGE(page_id, account_key, name, description) values(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[]{pageId, accountKey, name, description});
    }

    public int createRow(String pageId, String rowId, int sequence, int columns) {
        String sql = "INSERT INTO PAGE_ROW(page_id, row_id, sequence, col_num) values(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[]{pageId, rowId, sequence, columns});
    }

    public int createComponent(String rowId, String accountKey, String componentId, int column) {
        String sql = "INSERT INTO COMPONENT(row_id, account_key, component_id, col) values(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[]{rowId, accountKey, componentId, column});
    }

    public int updatePage(String pageId, String accountKey) {
        return 0;
    }

    public int updateRow(String rowId, int sequence) {
        String sql = "UPDATE PAGE_ROW SET sequence=? WHERE row_id=?";
        return jdbcTemplate.update(sql, new Object[]{ sequence, rowId });
    }

    public int deleteRow(String rowId) {
        String sql = "DELETE FROM PAGE_ROW WHERE row_id=?";
        return jdbcTemplate.update(sql, new Object[]{ rowId });
    }

    public int deleteComponentsByRow(String rowId) {
        String sql = "DELETE FROM COMPONENT WHERE row_id=?";
        return jdbcTemplate.update(sql, new Object[]{rowId});
    }

    public int deletePage(String pageId) {
        String sql = "DELETE FROM PAGE WHERE page_id=?";
        return jdbcTemplate.update(sql, new Object[]{ pageId });
    }

    public Page fetchPage(String pageId, String accountKey) {
        String sql = "SELECT page_id, name, description FROM PAGE WHERE page_id=? AND account_key=?";
        try {
            Page page = (Page) jdbcTemplate.queryForObject(
                    sql, new Object[]{pageId, accountKey}, new PageMapper());
            return page;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Page> fetchPages(String accountKey) {
        String sql = "SELECT page_id, name, description FROM PAGE WHERE account_key=? ORDER BY name ASC";
        List<Page> pages = jdbcTemplate.query(sql, new Object[] { accountKey }, new PageMapper());
        return pages;
    }

    public List<Component> fetchComponentsByRow(String rowId) {
        String sql = "SELECT component_id, col, type, data FROM COMPONENT WHERE row_id=? ORDER BY col ASC";
        List<Component> components = jdbcTemplate.query(sql, new Object[] { rowId }, new ComponentMapper());
        return components;
    }

    public List<Row> fetchRowsByPage(String pageId) {
        String sql = "SELECT row_id, sequence, col_num FROM PAGE_ROW WHERE page_id=? ORDER BY sequence ASC";
        List<Row> rows = jdbcTemplate.query(sql, new Object[] { pageId }, new PageRowMapper());
        return rows;
    }

    public int saveConfiguration(String accountKey, Configuration config) {
        String sql = "INSERT INTO ACCOUNT_CONFIG(account_key, sidebar_bg_color, sidebar_font_color, toolbar_bg_color, toolbar_font_color,"
                  + " splash_bg_color, splash_font_color, splash_image, splash_text)"
                  + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[]{ accountKey, config.getSidebarBgColor(), config.getSidebarFontColor(), config.getToolbarBgColor(),
                config.getToolbarFontColor(), config.getSplashBgColor(), config.getSplashFontColor(), config.getSplashImage(), config.getSplashText() });
    }

    public Configuration fetchConfiguration(String accountKey) {
        String sql = "SELECT sidebar_bg_color, sidebar_font_color, toolbar_bg_color, toolbar_font_color,"
                   + " splash_bg_color, splash_font_color, splash_image, splash_text FROM ACCOUNT_CONFIG WHERE account_key=?";
        try {
            Configuration configuration = (Configuration) jdbcTemplate.queryForObject(
                    sql, new Object[]{ accountKey }, new ConfigurationMapper());
            return configuration;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // mobile ***********************

    public List<Page> fetchPageForMobile(String mobileKey) {
        String sql = "SELECT p.page_id, p.name, p.description FROM PAGE p, PAGE_ROLE r, USER u"
                + " WHERE u.mobile_key=?"
                + " AND u.role_id=r.role_id"
                + " AND r.page_id=p.page_id"
                + " ORDER BY p.name ASC";
        List<Page> pages = jdbcTemplate.query(sql, new Object[] { mobileKey }, new PageMapper());
        return pages;
    }


    // **************************************

    private class ConfigurationMapper implements RowMapper<Configuration> {
        @Override
        public Configuration mapRow(ResultSet rs, int i) throws SQLException {
            Configuration config = new Configuration();
            config.setSidebarBgColor(rs.getString("sidebar_bg_color"));
            config.setSidebarFontColor(rs.getString("sidebar_font_color"));
            config.setToolbarBgColor(rs.getString("toolbar_bg_color"));
            config.setToolbarFontColor(rs.getString("toolbar_font_color"));
            config.setSplashFontColor(rs.getString("splash_font_color"));
            config.setSplashImage(rs.getString("splash_image"));
            config.setSplashText(rs.getString("splash_text"));
            config.setSplashBgColor(rs.getString("splash_bg_color"));
            return config;
        }
    }


    private class ComponentMapper implements RowMapper<Component> {
        @Override
        public Component mapRow(ResultSet rs, int i) throws SQLException {
            Component c = new Component();
            c.setComponentId(rs.getString("component_id"));
            c.setColumn(rs.getInt("col"));
            c.setType(rs.getString("type"));
            c.setData(rs.getString("data"));
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
            return p;
        }
    }

}
