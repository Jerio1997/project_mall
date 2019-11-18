package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WxIndexController {
    @Autowired
    GoodsService goodsService;


}
