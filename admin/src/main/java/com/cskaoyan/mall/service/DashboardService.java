package com.cskaoyan.mall.service;

public interface DashboardService {
    Long selectUserCount();
    Long selectGoodsCount();
    Long selectProductsCount();
    Long selectOrdersCount();
}
