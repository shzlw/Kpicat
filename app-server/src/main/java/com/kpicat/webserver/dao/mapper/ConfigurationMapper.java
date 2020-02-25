package com.kpicat.webserver.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kpicat.webserver.model.Configuration;

public class ConfigurationMapper implements RowMapper<Configuration> {
    
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