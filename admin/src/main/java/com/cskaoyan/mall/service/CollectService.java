package com.cskaoyan.mall.service;


import java.util.Map;

public interface CollectService {

    Map<String, Object> getCollectList(Integer page, Integer type, Integer size);

    String addordeleteCollect(Integer userId, Integer valueId);

    //String addordeleteCollect(Integer userId, Integer valueId, Collect collect);

}

