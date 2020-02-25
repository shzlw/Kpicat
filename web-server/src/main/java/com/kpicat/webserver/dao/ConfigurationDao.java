package com.kpicat.webserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kpicat.webserver.dao.mapper.ConfigurationMapper;
import com.kpicat.webserver.model.Configuration;

@Repository
public class ConfigurationDao {

    @Autowired
    JdbcTemplate jt;
    
    public int createConfiguration(String accountKey, Configuration config) {
        String sql = "INSERT INTO ACCOUNT_CONFIG(account_key, sidebar_bg_color, sidebar_font_color, toolbar_bg_color, toolbar_font_color,"
                  + " splash_bg_color, splash_font_color, splash_image, splash_text)"
                  + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jt.update(sql, new Object[]{ accountKey, config.getSidebarBgColor(), 
                config.getSidebarFontColor(), config.getToolbarBgColor(),
                config.getToolbarFontColor(), config.getSplashBgColor(),
                config.getSplashFontColor(), config.getSplashImage(), config.getSplashText() });
    }
    
    public int updateConfiguration(String accountKey, Configuration config) {
        String sql = "UPDATE ACCOUNT_CONFIG SET sidebar_bg_color=?, sidebar_font_color=?, toolbar_bg_color=?, toolbar_font_color=?,"
                  + " splash_bg_color=?, splash_font_color=?, splash_image=?, splash_text=? WHERE account_key=?";
        return jt.update(sql, new Object[]{ config.getSidebarBgColor(), 
                config.getSidebarFontColor(), config.getToolbarBgColor(),
                config.getToolbarFontColor(), config.getSplashBgColor(),
                config.getSplashFontColor(), config.getSplashImage(), config.getSplashText(), accountKey });
    }

    public Configuration fetchConfiguration(String accountKey) {
        String sql = "SELECT sidebar_bg_color, sidebar_font_color, toolbar_bg_color, toolbar_font_color,"
                   + " splash_bg_color, splash_font_color, splash_image, splash_text FROM ACCOUNT_CONFIG WHERE account_key=?";
        try {
            Configuration configuration = (Configuration) jt.queryForObject(
                    sql, new Object[]{ accountKey }, new ConfigurationMapper());
            return configuration;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
