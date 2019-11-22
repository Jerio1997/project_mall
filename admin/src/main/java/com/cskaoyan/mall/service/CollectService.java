package com.cskaoyan.mall.service;


import com.cskaoyan.mall.bean.Collect;

import java.util.HashMap;
import java.util.Map;

public interface CollectService {

    Map<String, Object> getCollectList(Integer page, Integer type, Integer size);

    HashMap<String, Object> addordeleteCollect(Collect collect,Integer userId);

    //List<Collect> collectList(Integer page, Integer type, Integer size);

    //String addordeleteCollect(Integer valueId);

    //String addordeleteCollect(Integer userId, Integer valueId, Collect collect);

}

