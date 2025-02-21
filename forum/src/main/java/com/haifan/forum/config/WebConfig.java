package com.haifan.forum.config;


import com.haifan.forum.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
            "/**/*.html",
            "/css/**",
            "/js/**",
            "/pic/**",
            "/dist/**",
            "/image/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/**",
            "/v3/**",
            "/swagger-ui.html/**",
            "/swagger-ui/**",  // 添加这一行
            "/swagger-ui/index.html",  // 添加这一行
            "/user/login"
    );


    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATHS);
    }

}
