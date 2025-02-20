package com.haifan.forum.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置Mybatis的扫描路径
 * @Author HaiFan
 */
@Configuration
@MapperScan("com/haifan/forum/dao")
public class MybatisConfig {
}
