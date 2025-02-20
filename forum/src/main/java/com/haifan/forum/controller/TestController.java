package com.haifan.forum.controller;


import com.haifan.forum.common.AppResult;
import com.haifan.forum.exception.ApplicationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "测试类")
@RestController
public class TestController {

    @ApiOperation("test api 1")
    @GetMapping("/test")
    public String test(){
        return "hello world";
    }

    @ApiOperation("test api 4 : send name and show")
    @GetMapping("/helloName")
    public String helloByName(@ApiParam(value = "name") @RequestParam("name")String name) {
        return "hello : " + name;
    }

    @ApiOperation("test api 2 : show exception")
    @RequestMapping("/exception")
    public String exception() throws Exception{

        throw new Exception("Exception");
    }

    @ApiOperation(("test api 3 : show applicationException"))
    @RequestMapping("/application")
    public String application() {
        throw new ApplicationException("application");
    }
}
