package com.haifan.forum.config;


import com.haifan.forum.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
            "/js/**",
            "/dist/**",
            "/image/**",
            "/**.ico",
            "/swagger*/**",
            "/v3/**",
            "/sign-in.html",
            "/sign-up.html",
            "/user/login",
            "/user/register",
            "/user/logout"
    );


    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATHS);
    }
}
