package com.rainbow.controller;

import com.rainbow.exception.BaseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class test {

    @GetMapping("/exception")
    public String baseException() {
        throw new BaseException("这是一个自定义异常");
    }
}
