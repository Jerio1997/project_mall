package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Mall;

public interface ConfigService {


    Mall selectMallInfo();

    int updateMallInfo(Mall mall);
}
