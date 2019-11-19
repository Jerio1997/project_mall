package com.cskaoyan.mall;

import com.cskaoyan.mall.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SsmXhlApplicationTests {

    @Autowired
    OrderService orderService;

    @Test
    void contextLoads() {
    }



}
