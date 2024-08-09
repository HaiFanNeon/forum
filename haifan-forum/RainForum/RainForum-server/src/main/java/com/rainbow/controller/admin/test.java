package com.rainbow.controller.admin;

import com.rainbow.exception.BaseException;

import com.rainbow.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Api(tags = "测试接口")
public class test {

    @GetMapping("/exception")
    public Result<String> baseException() {
        throw new BaseException("这是一个自定义异常");
    }

    @GetMapping("/hello")
    @ApiOperation(value = "测试一下", tags = {"获取信息"}, notes = "注意")
    public Result<String> hello(@ApiParam(name = "name") String name) {
        return Result.error(name);
    }
}
