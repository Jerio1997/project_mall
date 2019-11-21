package com.cskaoyan.mall.service;

import java.util.Map;

public interface FootPrintService {
    Map<String, Object> listFootPrint(Integer userId,Integer page, Integer size);

    int addFootPrint(Integer userId, Integer id);
}
