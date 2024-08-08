package com.rainbow;

import com.rainbow.exception.BaseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = test.class)
public class test {

    /**
     * 测试异常
     * @return
     */
    @Test
    public void baseException() {
        throw new BaseException("这是一个自定义异常");
    }
}
