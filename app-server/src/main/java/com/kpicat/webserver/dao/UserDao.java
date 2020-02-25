package com.kpicat.webserver.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kpicat.webserver.dao.mapper.RoleMapper;
import com.kpicat.webserver.dao.mapper.UserMapper;
import com.kpicat.webserver.model.ApiUsage;
import com.kpicat.webserver.model.Role;
import com.kpicat.webserver.model.User;
import com.kpicat.webserver.service.Constants;

@Repository
public class UserDao {

    @Autowired
    JdbcTemplate jt;
    
    public String fetchAccountKeyBySessionKey(String sessionKey) {
        String sql = "SELECT account_key FROM USER WHERE session_key=?";
        try {
            String accountKey = jt.queryForObject(sql, new Object[]{sessionKey}, String.class);
            return accountKey;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public List<ApiUsage> fetchApiUsge(String accountKey) {
        String sql = "SELECT current_day, api_count FROM API_METRIC WHERE account_key=? AND current_day >= DATE(NOW()) - INTERVAL 7 DAY"
                  + " ORDER BY current_day DESC";
        List<ApiUsage> usages = jt.query(sql, new Object[] { accountKey }, new RowMapper<ApiUsage>() {
            @Override
            public ApiUsage mapRow(ResultSet rs, int i) throws SQLException {
                ApiUsage u = new ApiUsage();
                u.setCurrDay(rs.getDate("current_day"));
                u.setCount(rs.getLong("api_count"));
                return u;
            }
            
        });
        return usages;
    }
    
    public User fetchAccount(String sessionKey) {
        String sql = "SELECT user_id, email, username, account_key, api_key, corp_name, role_id, membership FROM USER WHERE session_key=?";
        try {
            User user = (User) jt.queryForObject(sql, new Object[]{ sessionKey }, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User u = new User();
                    u.setUserId(rs.getString("user_id"));
                    u.setEmail(rs.getString("email"));
                    u.setUsername(rs.getString("username"));
                    u.setAccountKey(rs.getString("account_key"));
                    u.setApiKey(rs.getString("api_key"));
                    u.setCorpName(rs.getString("corp_name"));
                    u.setCorpRole(rs.getString("role_id"));
                    u.setMembership(rs.getString("membership"));
                    return u;
                }
            });
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public List<User> fetchUsers(String accountKey) {
        // Exclude the sys admin.
        String sql = "SELECT user_id, username, role_id FROM USER WHERE account_key=? AND sys_role!=? ORDER BY username ASC";
        List<User> users = jt.query(sql, new Object[] { accountKey, Constants.ADMIN }, new UserMapper());
        return users;
    }
    
    public int countUsers(String accountKey) {
        String sql = "SELECT count(*) FROM USER WHERE account_key=? AND sys_role!=?";
        return jt.queryForObject(sql, new Object[] { accountKey, Constants.ADMIN }, Integer.class);
    }
    
    public User fetchUser(String userId, String accountKey) {
        String sql = "SELECT user_id, username, role_id FROM USER WHERE account_key=? AND user_id=?";
        try {
            User user = (User) jt.queryForObject(sql, new Object[]{ accountKey, userId }, new UserMapper());
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public int createUser(String userId, String username, String password, String corpName, String corpRole, String accountKey) {
        String sql = "INSERT INTO USER(user_id, username, password, corp_name, role_id, account_key, sys_role) values(?, ?, ?, ?, ?, ?, ?)";
        return jt.update(sql, new Object[]{userId, username, password, corpName, corpRole, accountKey, Constants.VIEWER});
    }
    
    public int updateUser(String username, String corpRole, String userId) {
        String sql = "UPDATE USER SET username=?, role_id=? WHERE user_id=?";
        return jt.update(sql, new Object[]{username, corpRole, userId});
    }
    
    public int updateUser(String username, String corpName, String corpRole, String userId) {
        String sql = "UPDATE USER SET username=?, corp_name=?, role_id=? WHERE user_id=?";
        return jt.update(sql, new Object[]{username, corpName, corpRole, userId});
    }
    
    public int updateApiKey(String sessionKey, String apiKey) {
        String sql = "UPDATE USER SET api_key=? WHERE session_key=?";
        return jt.update(sql, new Object[]{ apiKey, sessionKey });
    }
    
    public int updatePassword(String password, String userId) {
        String sql = "UPDATE USER SET password=? WHERE user_id=?";
        return jt.update(sql, new Object[]{password, userId});
    }
    
    public int deleteUser(String userId) {
        String sql = "DELETE FROM USER WHERE user_id=?";
        return jt.update(sql, new Object[]{ userId });
    }
    
    public int updateUserRole(String roleId) {
        String sql = "UPDATE USER SET role_id=? WHERE role_id=?";
        return jt.update(sql, new Object[]{ null, roleId });
    }

    public String isUserExist(String mobileKey) {
        String sql = "SELECT username FROM USER WHERE mobile_key=?";
        try {
            String rt = jt.queryForObject(sql, new Object[]{mobileKey}, String.class);
            return rt;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int updateMobileKey(String oldMobileKey, String newMobileKey) {
        String sql = "UPDATE USER SET mobile_key=? WHERE mobile_key=?";
        return jt.update(sql, new Object[]{ newMobileKey, oldMobileKey });
    }
    

    public String isCorpNameExist(String corpName) {
        String sql = "SELECT DISTINCT(corp_name) FROM USER WHERE corp_name=?";
        try {
            String rt = jt.queryForObject(sql, new Object[]{corpName}, String.class);
            return rt;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String isEmailExist(String email) {
        String sql = "SELECT email FROM USER WHERE email=?";
        try {
            String rt = jt.queryForObject(sql, new Object[]{email}, String.class);
            return rt;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String isUserExist(String email, String password) {
        String sql = "SELECT email FROM USER WHERE email=? AND password=? AND sys_role=?";
        try {
            String rt = jt.queryForObject(sql, new Object[]{email, password, Constants.ADMIN}, String.class);
            return rt;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String isUserExist(String username, String password, String corpName) {
        String sql = "SELECT username FROM USER WHERE username=? AND password=? AND corp_name=?";
        try {
            String rt = jt.queryForObject(sql, new Object[]{username, password, corpName}, String.class);
            return rt;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int updateMobileKey(String mobileKey, String username, String password, String corpName) {
        String sql = "UPDATE USER SET mobile_key=? WHERE username=? AND password=? AND corp_name=?";
        return jt.update(sql, new Object[]{ mobileKey, username, password, corpName});
    }

    public int updateSessionKey(String sessionKey, String email, String password) {
        String sql = "UPDATE USER SET session_key=? WHERE email=? AND password=?";
        return jt.update(sql, new Object[]{sessionKey, email, password});
    }
    
    public int updateSessionKey(String oldSessionKey, String newSessionKey) {
        String sql = "UPDATE USER SET session_key=? WHERE session_key=?";
        return jt.update(sql, new Object[]{newSessionKey, oldSessionKey});
    }

    public int createSysUser(String userId, String email, String username, String password, String sysRole,
                             String corpName, String apiKey, String accountKey, String membership) {
        String sql = "INSERT INTO USER(user_id, email, username, password, sys_role, corp_name, api_key, account_key, membership)"
                   + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jt.update(sql, new Object[]{userId, email, username, password, sysRole, corpName, apiKey, accountKey, membership});
    }

    public String fetchAccountKeyByMoibleKey(String mobileKey) {
        String sql = "SELECT account_key FROM USER WHERE mobile_key=?";
        try {
            String accountKey = jt.queryForObject(sql, new Object[]{ mobileKey }, String.class);
            return accountKey;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public String fetchResetPassword(String resetPassCode) {
        return "";
    }
    
    public int updateResetPassCode(String resetPassCode, String email) {
        String sql = "UPDATE USER SET reset_pass_code=? WHERE email=?";
        return jt.update(sql, new Object[]{resetPassCode, email});
    }
}
