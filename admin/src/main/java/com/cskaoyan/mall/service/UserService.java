package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.User;

import java.util.List;

public interface UserService {


    List<User> queryUsers(Integer page, Integer limit);
}
