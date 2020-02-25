package com.kpicat.webserver.webservice.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kpicat.webserver.dao.UserDao;
import com.kpicat.webserver.model.ApiUsage;
import com.kpicat.webserver.model.User;
import com.kpicat.webserver.service.Common;
import com.kpicat.webserver.service.Constants;

@RestController
@RequestMapping("/ws/web/user")
public class UserWs {
    
    @Autowired
    UserDao userDao;

    @RequestMapping(value="/all", method = RequestMethod.GET)
    public List<User> getUsers(@CookieValue(Constants.SESSION_KEY) String sessionKey) {
        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        List<User> users = userDao.fetchUsers(accountKey);
        return users;
    }

    @RequestMapping(value="/one", method = RequestMethod.GET)
    public User getUser(@CookieValue(Constants.SESSION_KEY) String sessionKey,
                        @RequestParam("userId") String userId) {
        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        return userDao.fetchUser(userId, accountKey);
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @Transactional
    public User saveUser(@CookieValue(Constants.SESSION_KEY) String sessionKey,
                         @RequestBody User user) {

        if (Common.isEmpty(user.getUsername()) || Common.isEmpty(user.getPassword())) {
            return null;
        }
        
        User accountUser = userDao.fetchAccount(sessionKey);
        String userId = user.getUserId();
        
        if (Common.isEmpty(userId)) {

            // Check user limit
            int userCount = userDao.countUsers(accountUser.getAccountKey());
            int userLimit = Constants.MEMBERSHIP_MAP.get(accountUser.getMembership()).getUser();
            if (userCount >= userLimit) {
                return null;
            }
            
            String enPassword = Common.getMd5Hash(user.getPassword());
            userId = "use_" + Common.getUniqueId();
            
            userDao.createUser(userId, user.getUsername(), enPassword, accountUser.getCorpName(), user.getCorpRole(), accountUser.getAccountKey());
        } else {
            User oldUser = userDao.fetchUser(userId, accountUser.getAccountKey());
            if (oldUser != null) {
                if (!Common.isEmpty(user.getPassword())) {
                    String enPassword = Common.getMd5Hash(user.getPassword());
                    userDao.updatePassword(enPassword, userId);
                }
                userDao.updateUser(user.getUsername(), user.getCorpRole(), userId);
            }
        }
        
        return getUser(sessionKey, userId);
    }

    @RequestMapping(value="/delete", method = RequestMethod.GET)
    @Transactional
    public void deleteUser(@CookieValue(Constants.SESSION_KEY) String sessionKey,
                           @RequestParam("userId") String userId) {
        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        User user = userDao.fetchUser(userId, accountKey);
        if (user != null) {
            userDao.deleteUser(userId);
        }
    }
    
    @RequestMapping(value="/account", method = RequestMethod.GET)
    public User getAccount(@CookieValue(Constants.SESSION_KEY) String sessionKey) {
        return userDao.fetchAccount(sessionKey);
    }
    
    @RequestMapping(value="/account/save", method = RequestMethod.POST)
    @Transactional
    public void updateAccount(@CookieValue(Constants.SESSION_KEY) String sessionKey,
                              @RequestBody User user) {
        User accountUser = userDao.fetchAccount(sessionKey);
        String userId = accountUser.getUserId();
        
        if (!Common.isEmpty(user.getPassword())) {
            String enPassword = Common.getMd5Hash(user.getPassword());
            userDao.updatePassword(enPassword, userId);
        }
        userDao.updateUser(user.getUsername(), user.getCorpName(), user.getCorpRole(), userId);
    }
    
    @RequestMapping(value="/reset-apikey", method = RequestMethod.POST)
    @Transactional
    public String resetApiKey(@CookieValue(Constants.SESSION_KEY) String sessionKey) {
        String apiKey = "api_" + Common.getUniqueId();
        userDao.updateApiKey(sessionKey, apiKey);
        return apiKey;
    }
    
    @RequestMapping(value="/api-usage", method = RequestMethod.GET)
    public List<ApiUsage> getApiUsage(@CookieValue(Constants.SESSION_KEY) String sessionKey) {
        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        return userDao.fetchApiUsge(accountKey);
    }
}
