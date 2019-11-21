/*package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.UserExample;
import com.cskaoyan.mall.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryUsers(Integer page, Integer limit) {
        //完成分页的查询
        PageHelper.startPage(page,limit);

        List<User> users = userMapper.selectByExample(new UserExample());
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        long total = userPageInfo.getTotal();

        return users;
    }

    @Override
    public User getUserById(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }
}*/
package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    FootprintMapper footprintMapper;
    @Autowired
    SearchHistoryMapper searchHistoryMapper;
    @Autowired
    FeedbackMapper feedbackMapper;
    @Autowired
    OrderMapper orderMapper;

    //会员管理1
    @Override
    public Map<String, Object> getUserlist(Integer page, Integer limit, String username, String mobile, String sort, String order, User user) {
        PageHelper.startPage(page, limit, sort + " " + order);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (mobile != null) {
            criteria.andMobileLike("%" + mobile + "%");
        }
        if (username != null) {
            criteria.andUsernameLike("%" + username + "%");
        }
        user.setBirthday(new Date());
        List<User> userList = userMapper.selectByExample(example);
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        long total = userPageInfo.getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", userList);
        return data;
    }

    //收货地址
    @Override
    public Map<String, Object> getAddresslist(Integer page, Integer limit, Integer userId, String name, String sort, String order) {
        PageHelper.startPage(page, limit, sort + " " + order);
//        AddressExample example = new AddressExample();
//        AddressExample.Criteria criteria = example.createCriteria();
//        if (userId != null){
//            criteria.andUserIdEqualTo(userId);
//        }
//        if (name != null){
//            criteria.andNameLike("%" + name + "%");
//        }
        List<Address> addresses = addressMapper.queryAddress(userId, name);
        for (Address address : addresses) {
            String province = addressMapper.queryProvinceByPid(address.getProvinceId());
            String city = addressMapper.queryProvinceByPid(address.getCityId());
            String area = addressMapper.queryProvinceByPid(address.getAreaId());
            address.setProvince(province);
            address.setCity(city);
            address.setArea(area);
        }
        PageInfo<Address> addressPageInfo = new PageInfo<>(addresses);
        long total = addressPageInfo.getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", addresses);
        return data;
    }

    //会员收藏
    @Override
    public Map<String, Object> getCollectlist(Integer page, Integer limit, Integer userId, Integer valueId, String sort, String order, Collect collect) {
        PageHelper.startPage(page, limit, sort + " " + order);
        CollectExample example = new CollectExample();
        CollectExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (valueId != null) {
            criteria.andValueIdEqualTo(valueId);
        }
        collect.setAddTime(new Date());
        List<Collect> collectList = collectMapper.selectByExample(example);
        PageInfo<Collect> collectPageInfo = new PageInfo<>(collectList);
        long total = collectPageInfo.getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", collectList);
        return data;
    }

    //会员足迹
    @Override
    public Map<String, Object> getFootlist(Integer page, Integer limit, Integer userId, Integer goodsId, String sort, String order, Footprint footprint) {
        PageHelper.startPage(page, limit, sort + " " + order);
        FootprintExample example = new FootprintExample();
        FootprintExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (goodsId != null) {
            criteria.andGoodsIdEqualTo(goodsId);
        }
        footprint.setAddTime(new Date());
        List<Footprint> footprintList = footprintMapper.selectByExample(example);
        PageInfo<Footprint> cartPageInfo = new PageInfo<>(footprintList);
        long total = cartPageInfo.getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", footprintList);
        return data;
    }

    //搜索历史
    @Override
    public Map<String, Object> getSearchHistorylist(Integer page, Integer limit, Integer userId, String keyword, String sort, String order, SearchHistory searchHistory) {
        PageHelper.startPage(page, limit, sort + " " + order);
        SearchHistoryExample example = new SearchHistoryExample();
        SearchHistoryExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (keyword != null) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        searchHistory.setAddTime(new Date());
        List<SearchHistory> searchHistoryList = searchHistoryMapper.selectByExample(example);
        PageInfo<SearchHistory> searchHistoryPageInfo = new PageInfo<>(searchHistoryList);
        long total = searchHistoryPageInfo.getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", searchHistoryList);
        return data;
    }

    //意见反馈
    @Override
    public Map<String, Object> getFeetBacklist(Integer page, Integer limit, Integer id, String username, String sort, String order, Feedback feedback) {
        PageHelper.startPage(page, limit, sort + " " + order);
        FeedbackExample example = new FeedbackExample();
        FeedbackExample.Criteria criteria = example.createCriteria();
        if (id != null) {
            criteria.andIdEqualTo(id);
        }
        if (username != null) {
            criteria.andUsernameLike("%" + username + "%");
        }
        feedback.setUpdateTime(new Date());
        List<Feedback> feedbackList = feedbackMapper.selectByExample(example);
        PageInfo<Feedback> feedbackPageInfo = new PageInfo<>(feedbackList);
        long total = feedbackPageInfo.getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", feedbackList);
        return data;
    }

    @Override
    public List<User> authUser(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
        return userMapper.selectByExample(example);
    }

    @Override

    public void register(WxRegister wxRegister) {
        String username = wxRegister.getUsername();
        String password = wxRegister.getPassword();
        String mobile = wxRegister.getMobile();
        userMapper.insertUser(username, password, mobile);
    }

    public UserIndexReqVo_Wx queryUserIndexByUserId(Integer id) {
        UserIndexReqVo_Wx userIndexReqVo_wx = new UserIndexReqVo_Wx();
        UserIndexReqVo_Wx.OrderBean orderBean = new UserIndexReqVo_Wx.OrderBean();
        OrderExample orderExampleR = new OrderExample();
        orderExampleR.createCriteria().andOrderStatusEqualTo((short) 301).andUserIdEqualTo(id).andDeletedNotEqualTo(true);
        long l = orderMapper.countByExample(orderExampleR);
        OrderExample orderExampleC = new OrderExample();
        orderExampleC.createCriteria().andCommentsGreaterThan((short) 0).andUserIdEqualTo(id).andDeletedNotEqualTo(true);
        long l1 = orderMapper.countByExample(orderExampleC);
        OrderExample orderExampleP = new OrderExample();
        orderExampleP.createCriteria().andOrderStatusEqualTo((short) 101).andUserIdEqualTo(id).andDeletedNotEqualTo(true);
        long l2 = orderMapper.countByExample(orderExampleP);
        OrderExample orderExampleS = new OrderExample();
        orderExampleS.createCriteria().andOrderStatusEqualTo((short) 201).andUserIdEqualTo(id).andDeletedNotEqualTo(true);
        long l3 = orderMapper.countByExample(orderExampleS);
        orderBean.setUnrecv((int) l);
        orderBean.setUncomment((int) l1);
        orderBean.setUnpaid((int) l2);
        orderBean.setUnship((int) l3);
        userIndexReqVo_wx.setOrder(orderBean);
        return userIndexReqVo_wx;


    }

    @Override
    public Long queryUsers() {
        Long users = userMapper.countByExample(new UserExample());
        return users;
    }

    @Override
    public User getUserById(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (users == null || users.size() == 0) {
            return null;
        }
        return users.get(0);
    }
}

