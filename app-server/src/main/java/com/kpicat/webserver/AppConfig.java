package com.kpicat.webserver;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.kpicat.webserver.filter.AuthFilter;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter{

    @Autowired
    MailProperties mailProperties;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/home.html");
    }

    @Bean
    public FilterRegistrationBean authFilterRegistry() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("authFilter");
        registration.setFilter(new AuthFilter());
        registration.addUrlPatterns("/*");
        registration.setAsyncSupported(Boolean.TRUE);
        registration.setEnabled(Boolean.TRUE);
        return registration;
    }
    
    @Bean
    public MailProperties mailProperties() {
        return new MailProperties();
    }


    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        try {
            javaMailSender.setHost(mailProperties.getHost());
            javaMailSender.setPort(mailProperties.getPort());
            javaMailSender.setUsername(mailProperties.getUsername());
            javaMailSender.setPassword(mailProperties.getPassword());
            javaMailSender.setProtocol(mailProperties.getProtocol());

            Properties props = new Properties();
            for (Map.Entry<String, String> entry : mailProperties.getProperties().entrySet()) {
                props.put(entry.getKey(), entry.getValue());
            }

            javaMailSender.setJavaMailProperties(props);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return javaMailSender;
    }

}
