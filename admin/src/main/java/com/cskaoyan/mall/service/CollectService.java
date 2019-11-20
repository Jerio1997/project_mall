package com.cskaoyan.mall.service;

import java.util.Map;

public interface CollectService {

    Map<String, Object> getCollectList(Integer page, Integer type, Integer size);

    Map<String, Object> addordeleteCollect(Byte type, Integer valueId);
}

