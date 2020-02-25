package com.kpicat.webserver.webservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kpicat.webserver.dao.ComponentDao;
import com.kpicat.webserver.dao.UserDao;
import com.kpicat.webserver.service.Constants;

@RestController
@RequestMapping("/ws/web/component")
public class ComponentWs {

    @Autowired
    ComponentDao componentDao;
    
    @Autowired
    UserDao userDao;
    
    @RequestMapping(value="/data", method = RequestMethod.GET)
    public String getData(@CookieValue(Constants.SESSION_KEY) String sessionKey,
                          @RequestParam("componentId") String componentId) {
        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        return componentDao.getData(componentId, accountKey);
    }
}
