package com.kpicat.webserver.webservice;

import com.kpicat.webserver.dao.ConfigurationDao;
import com.kpicat.webserver.dao.UserDao;
import com.kpicat.webserver.model.Configuration;
import com.kpicat.webserver.model.User;
import com.kpicat.webserver.service.Common;
import com.kpicat.webserver.service.Constants;
import com.kpicat.webserver.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthWs {

    @Autowired
    UserDao userDao;
    
    @Autowired
    ConfigurationDao configDao;
    
    @Autowired
    EmailService emailService;

    @RequestMapping(value="/web/login", method = RequestMethod.POST)
    @Transactional
    public String webLogin(@RequestBody User user, HttpServletResponse response) {

        String email = user.getEmail();
        String password = user.getPassword();

        String enPassword = Common.getMd5Hash(password);

        String sessionKey = "ses_" + Common.getUniqueId();

        if (userDao.isUserExist(email, enPassword) == null) {
            return Constants.ERROR;
        } else {
            userDao.updateSessionKey(sessionKey, email, enPassword);


            Cookie sessionKeyCookie = new Cookie(Constants.SESSION_KEY, sessionKey);
            sessionKeyCookie.setMaxAge(Constants.COOKIE_TIMEOUT);
            sessionKeyCookie.setPath("/");
            response.addCookie(sessionKeyCookie);

            return Constants.GOOD;
        }
    }

    @RequestMapping(value="/logout", method= RequestMethod.GET)
    @Transactional
    public void webLogout(@CookieValue(Constants.SESSION_KEY) String sessionKey, HttpServletResponse response) throws IOException {
        User user = userDao.fetchAccount(sessionKey);
        if (user != null) {
            userDao.updateSessionKey(sessionKey, null);
            
            Cookie sessionKeyCookie = new Cookie(Constants.SESSION_KEY, "");
            sessionKeyCookie.setMaxAge(0);
            sessionKeyCookie.setPath("/");
            response.addCookie(sessionKeyCookie);
        }
        response.sendRedirect("../login.html");
    }

    public void checkCorpNameExist(@RequestParam("corp-name") String corpName) {

    }

    public void checkEmailExist(@RequestParam("email") String email) {}


    @RequestMapping(value="/register", method = RequestMethod.POST)
    @Transactional
    public String register(@RequestBody User user, HttpServletResponse response) {

        String corpName = user.getCorpName();
        String email = user.getEmail();
        String username = user.getUsername();
        String password = user.getPassword();
        
        if (userDao.isEmailExist(email) != null) {
            return "Email already exists";
        }
        
        if (userDao.isCorpNameExist(corpName) != null) {
            return "Corporation already exists";
        }

        String enPassword = Common.getMd5Hash(password);

        String apiKey = "api_" + Common.getUniqueId();
        String accountKey = "acc_" + Common.getUniqueId();
        String sysRole = Constants.ADMIN;
        String userId = "use_" + Common.getUniqueId();

        userDao.createSysUser(userId, email, username, enPassword, sysRole, corpName, apiKey, accountKey, Constants.BETA);

        Configuration config = new Configuration();
        config.setSidebarBgColor("#00a8e8");
        config.setSidebarFontColor("#FFFFFF");
        config.setToolbarBgColor("#a9a9a9");
        config.setToolbarFontColor("#FFFFFF");
        config.setSplashFontColor("#FFFFFF");
        config.setSplashImage(null);
        config.setSplashText("KpiCat");
        config.setSplashBgColor("#00a8e8");

        configDao.createConfiguration(accountKey, config);

        return webLogin(user, response);
    }

    @RequestMapping(value="/forgot-password", method = RequestMethod.POST)
    public void forgotPassword(User user) {
        String email = user.getEmail();
        
        String resetPassCode = "pas_" + Common.getUniqueId();
        userDao.updateResetPassCode(resetPassCode, email);
        // send out a code.
        String text = "";
        emailService.send("aaa", email, "Reset password", text);
    }
    

    @RequestMapping(value="/mobile/login", method = RequestMethod.POST)
    @Transactional
    public String mobileLogin(@RequestBody User user) {

        String username = user.getUsername();
        String password = user.getPassword();
        String corpName = user.getCorpName();

        String enPassword = Common.getMd5Hash(password);

        String mobileKey = "mob_" + Common.getUniqueId();

        if (userDao.isUserExist(username, enPassword, corpName) == null) {
            return "bad";
        } else {
            userDao.updateMobileKey(mobileKey, username, enPassword, corpName);
            return mobileKey;
        }
    }

    @RequestMapping(value="/mobile/login/mobile-key", method = RequestMethod.POST)
    @Transactional
    public String mobileLoginWithKey(@RequestBody User user) {

        String userMobileKey = user.getMobileKey();

        String mobileKey = "mob_" + Common.getUniqueId();

        if (userDao.isUserExist(userMobileKey) == null) {
            return "bad";
        } else {
            userDao.updateMobileKey(userMobileKey, mobileKey);
            return mobileKey;
        }
    }
}
