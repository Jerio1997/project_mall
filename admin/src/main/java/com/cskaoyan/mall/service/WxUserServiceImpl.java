package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.UserIndex;
import com.cskaoyan.mall.mapper.UserIndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxUserServiceImpl implements WxUserService {
    @Autowired
    UserIndexMapper userIndexMapper;
    @Override
    public Map<String, Object> userIndex() {
        UserIndex userIndex = new UserIndex();
        List<UserIndex> userIndexList = userIndexMapper.selectIndex();
        Map<String, Object> data = new HashMap<>();
        data.put("order", userIndexList);
        return data;
    }
}
