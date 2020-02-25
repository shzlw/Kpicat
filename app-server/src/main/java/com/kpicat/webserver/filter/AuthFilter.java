package com.kpicat.webserver.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.kpicat.webserver.dao.UserDao;
import com.kpicat.webserver.model.User;
import com.kpicat.webserver.service.Constants;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class AuthFilter implements Filter {

    @Autowired
    UserDao userDao;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String path = httpRequest.getServletPath();

        boolean isAuthed = false;

        if (path.startsWith("/ws/web")
                || path.startsWith("/app.html")
                ) {
            
            isAuthed = isUserValid(httpRequest);
        } else {
            isAuthed = true;
        }
        
        if (isAuthed) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/error/401.html");
        }
    }
    
    public boolean isUserValid(HttpServletRequest httpRequest) {
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                String name = cookies[i].getName();
                String value = cookies[i].getValue();

                if (name.equals(Constants.SESSION_KEY)) {
                    User user = userDao.fetchAccount(value);
                    if (user != null) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }


    @Override
    public void destroy() {

    }
}
