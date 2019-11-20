package com.cskaoyan.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan(basePackages = "com.cskaoyan.mall.mapper")
@EnableAspectJAutoProxy
public class SsmXhlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsmXhlApplication.class, args);
    }

}
