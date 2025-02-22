package com.haifan.forum.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.haifan.forum.common.AppResult;
import lombok.SneakyThrows;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

@ControllerAdvice(basePackages = "com.haifan.forum.controller")
public class ResponseAdvice implements ResponseBodyAdvice {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof AppResult) {
            return body;
        }

        if (body instanceof String) {
            return objectMapper.writeValueAsString(AppResult.success(body));
        }


        return AppResult.success(body);
    }
}
