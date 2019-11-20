//package com.cskaoyan.mall;
//
//import com.cskaoyan.mall.bean.BaseReqVo;
//import com.cskaoyan.mall.bean.OrderReqVo;
//import com.cskaoyan.mall.service.OrderService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import jdk.internal.org.objectweb.asm.TypeReference;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class SsmXhlApplicationTests {
//
//    @Autowired
//    OrderService orderService;
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    public void insertIntoOrder() {
//        String str = "";
//        Gson gson = new Gson();
//        InsertOrderBean insertOrderBean = gson.fromJson(str, InsertOrderBean.class);
//        OrderReqVo orderReqVo = insertOrderBean.getData();
//        int i = orderService.InsertOrders(orderReqVo.getItems());
//        System.out.println(i);
//    }
//
//
//}
