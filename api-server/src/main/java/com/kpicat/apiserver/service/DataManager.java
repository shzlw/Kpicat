package com.kpicat.apiserver.service;

import com.kpicat.apiserver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
public class DataManager {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public User getUserByApiKey(String apiKey) {
        String sql = "SELECT account_key, membership FROM USER WHERE api_key=?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{apiKey}, new UserMapper());
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User u = new User();
            u.setAccountKey(rs.getString("account_key"));
            u.setMembership(rs.getString("membership"));
            return u;
        }
    }


    public int updateComponent(String accountKey, String componentId, String type, long timestamp, String data) {
        String sql = "UPDATE COMPONENT SET data=?, last_update=?, type=? WHERE component_id=? AND account_key=?";
        return jdbcTemplate.update(sql, new Object[]{data, timestamp, type, componentId, accountKey});
    }

    public long countApiCall(String accountKey, String currentDay) {
        String sql = "SELECT api_count FROM API_METRIC WHERE account_key=? AND current_day=?";
        try {
            long apiCount = jdbcTemplate.queryForObject(sql, new Object[]{accountKey, currentDay}, long.class);
            return apiCount;
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public int createNewApiMetric(String accountKey) {
        String sql = "INSERT INTO API_METRIC(account_key, current_day, api_count) values(?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[]{accountKey, getCurrentDay(), 1});
    }

    public int updateApiMetric(String accountKey) {
        String sql = "UPDATE API_METRIC SET api_count = api_count + 1 WHERE account_key=? and current_day=?";
        return jdbcTemplate.update(sql, new Object[]{accountKey, getCurrentDay()});
    }

    public static String getCurrentDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    public String findComponentIdByNotification(String accountKey, String pageName) {
        String sql = "SELECT DISTINCT(p.page_id) FROM PAGE p, PAGE_ROLE r, USER u"
                    + " WHERE u.account_key=?"
                    + " AND u.role_id=r.role_id"
                    + " AND r.page_id=p.page_id"
                    + " AND p.name=?";
        try {
            String componentId = jdbcTemplate.queryForObject(sql, new Object[]{accountKey, pageName}, String.class);
            return componentId;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
