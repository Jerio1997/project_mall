/*package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("list")
    public BaseReqVo list(Integer page,Integer limit){
        List<User> users = userService.queryUsers(page, limit);

        BaseReqVo<List<User>> listBaseReqVo = new BaseReqVo<>();
        listBaseReqVo.setData(users);
        return listBaseReqVo;
    }
}*/
package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    //会员管理1
    @RequestMapping("user/list")
    public BaseReqVo listUser(Integer page,Integer limit,String username,String mobile,String sort,String order,User user){
        Map<String,Object> data = userService.getUserlist(page,limit,username,mobile,sort,order,user);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
    //收货地址1
    @RequestMapping("address/list")
    public BaseReqVo addressUser(Integer page,Integer limit,Integer userId,String name,String sort,String order){
        Map<String,Object> data = userService.getAddresslist(page,limit,userId,name,sort,order);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
    //会员收藏1
    @RequestMapping("collect/list")
    public BaseReqVo collectUser(Integer page, Integer limit, Integer userId, Integer valueId, String sort, String order, Collect collect){
        Map<String,Object> data = userService.getCollectlist(page,limit,userId,valueId,sort,order,collect);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
    //会员足迹1
    @RequestMapping("footprint/list")
    public BaseReqVo cartUser(Integer page, Integer limit, Integer userId, Integer goodsId, String sort, String order, Footprint footprint){
        Map<String,Object> data = userService.getFootlist(page,limit,userId,goodsId,sort,order,footprint);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
    //搜索历史
    @RequestMapping("history/list")
    public BaseReqVo searchHistory(Integer page, Integer limit, Integer userId, String keyword, String sort, String order, SearchHistory searchHistory){
        Map<String,Object> data = userService.getSearchHistorylist(page,limit,userId,keyword,sort,order,searchHistory);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
    //意见反馈
    @RequestMapping("feedback/list")
    public BaseReqVo feedBack(Integer page,Integer limit,Integer id,String username,String sort,String order,Feedback feedback) {
        Map<String, Object> data = userService.getFeetBacklist(page, limit, id, username, sort, order,feedback);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
}

