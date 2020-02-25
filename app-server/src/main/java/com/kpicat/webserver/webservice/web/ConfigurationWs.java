package com.kpicat.webserver.webservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kpicat.webserver.dao.ConfigurationDao;
import com.kpicat.webserver.dao.UserDao;
import com.kpicat.webserver.model.Configuration;
import com.kpicat.webserver.service.Constants;

@RestController
@RequestMapping("/ws/web/configuration")
public class ConfigurationWs {

    @Autowired
    ConfigurationDao configDao;
    
    @Autowired
    UserDao userDao;
    
    @RequestMapping(value="/one", method = RequestMethod.GET)
    public Configuration getConfig(@CookieValue(Constants.SESSION_KEY) String sessionKey) {
        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        return configDao.fetchConfiguration(accountKey);
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @Transactional
    public void saveUser(@CookieValue(Constants.SESSION_KEY) String sessionKey,
                         @RequestBody Configuration config) {
        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        configDao.updateConfiguration(accountKey, config);
    }
}
