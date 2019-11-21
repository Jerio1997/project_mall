/*package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.User;

import java.util.List;

public interface UserService {


    List<User> queryUsers(Integer page, Integer limit);

    User getUserById(Integer userId);
}*/
package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;

import java.util.List;
import java.util.Map;

public interface UserService {

    Long queryUsers();

    User getUserById(Integer userId);

    Map<String, Object> getUserlist(Integer page, Integer limit, String username,String mobile, String sort, String order,User user);

    Map<String, Object> getAddresslist(Integer page, Integer limit, Integer userId, String name, String sort, String order);

    Map<String, Object> getCollectlist(Integer page, Integer limit, Integer userId, Integer valueId, String sort, String order, Collect collect);

    Map<String, Object> getFootlist(Integer page, Integer limit, Integer userId, Integer goodsId, String sort, String order, Footprint footprint);

    Map<String, Object> getSearchHistorylist(Integer page, Integer limit, Integer userId, String keyword, String sort, String order, SearchHistory searchHistory);

    Map<String, Object> getFeetBacklist(Integer page, Integer limit, Integer id, String username, String sort, String order, Feedback feedback);
    User getUserByUsername(String username);
    List<User> authUser(User user);

    void register(WxRegister wxRegister);
}
