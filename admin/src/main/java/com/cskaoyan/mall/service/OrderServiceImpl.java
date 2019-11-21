package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.*;
import com.cskaoyan.mall.utils.OrderStatusUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderGoodsMapper orderGoodsMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Autowired
    GrouponMapper grouponMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private RegionMapper regionMapper;


    @Override
    public Map<String, Object> getOrderList(int page, int limit, Short[] orderStatusArray, Integer userId, String orderSn, String sort, String order) {
        PageHelper.startPage(page,limit);
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (orderSn != null) {
            criteria.andOrderSnEqualTo(orderSn);
        }
        if (orderStatusArray != null) {
            criteria.andOrderStatusIn(Arrays.asList(orderStatusArray));
        }
        example.setOrderByClause(sort + " " + order);
        List<Order> orderList = orderMapper.selectByExample(example);
        PageInfo<Order> orderPageInfo = new PageInfo<>(orderList);
        long total = orderPageInfo.getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", orderList);
        return data;
    }

    @Override
    public List<OrderGoods> getOrderGoodsByOrderId(int orderId) {
        OrderGoodsExample example = new OrderGoodsExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(example);
        return orderGoods;
    }

    @Override
    public Order getOrderById(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        return order;
    }

    @Override
    public int InsertOrders(List<Order> orderList) {
        for (Order order : orderList) {
            int i = orderMapper.insertSelective(order);
        }
        return orderList.size();
    }

    @Override
    public OrderReqVo getOrderListByUsernameAndCodes(int page, int size, Short[] codeByType, String username) {
        OrderReqVo orderReqVo = new OrderReqVo();
        OrderExample orderExample = new OrderExample();
        // 查询用户的id
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            return null;
        }
        PageHelper.startPage(page, size);
        // 用户id
        OrderExample.Criteria criteria = orderExample.createCriteria().andUserIdEqualTo(users.get(0).getId());
        // 判断要查看的订单订单状态
        if (codeByType.length != 0) {
            criteria.andOrderStatusIn(Arrays.asList(codeByType));
        }
        List<Order> orders = orderMapper.selectByExample(orderExample);
        for (Order order : orders) {
            order.setOrderStatusText(OrderStatusUtils.getTextByCode(order.getOrderStatus()));
            GrouponExample grouponExample = new GrouponExample();
            grouponExample.createCriteria().andOrderIdEqualTo(order.getId());
            List<Groupon> groupons = grouponMapper.selectByExample(grouponExample);
            if (groupons.size() > 0) {
                order.setIsGroupin(true);
            } else {
                order.setIsGroupin(false);
            }
            OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
            orderGoodsExample.createCriteria().andOrderIdEqualTo(order.getId());
            List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
            order.setGoodsList(orderGoods);
            HandleOption status1Option = HandleOption.getStatus1Option();
            order.setHandleOption(status1Option);
        }

        orderReqVo.setData(orders);
        long l = orderMapper.countByExample(new OrderExample());
        orderReqVo.setCount((int) l);
        orderReqVo.setTotalPages((int) (l/size));
        return orderReqVo;
    }

    @Override
    public List<Order> selectOrderByUserIdAndStatus(Integer id, String orderStatus) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUserIdEqualTo(id).andOrderSnEqualTo(orderStatus);
        List<Order> orderList = orderMapper.selectByExample(orderExample);
        return orderList;
    }
        public List<OrderGoods> selectOrderGoodsByOrderId (Integer orderId){
            OrderGoodsExample example = new OrderGoodsExample();
            example.createCriteria().andOrderIdEqualTo(orderId);
            List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(example);
            return orderGoods;
        }

        @Override
        public HashMap<String, Object> selectOrderInfoById (Integer orderId){
            Order order = orderMapper.selectByPrimaryKey(orderId);
            // 获取详细地址
            String detailAddress = order.getAddress();
            AddressExample addressExample = new AddressExample();
            addressExample.createCriteria().andAddressEqualTo(detailAddress);
            List<Address> addresses = addressMapper.selectByExample(addressExample);
            // 获取区地址
            Integer areaId = addresses.get(0).getAreaId();
            RegionExample regionExample = new RegionExample();
            regionExample.createCriteria().andCodeEqualTo(areaId);
            List<Region> regions = regionMapper.selectByExample(regionExample);
            Region region = regions.get(0);
            String areaName = region.getName();
            // 获取市
            Integer cityId = region.getPid();
            Region city = regionMapper.selectByPrimaryKey(cityId);
            String cityName = city.getName();
            // 获取省份
            Integer pid = city.getPid();
            Region province = regionMapper.selectByPrimaryKey(pid);
            String provinceName = province.getName();

            HashMap<String, Object> map = new HashMap<>();
            map.put("address", provinceName + cityName + areaName + detailAddress);
            map.put("addTime", order.getAddTime());
            map.put("actualPrice", order.getActualPrice());
            map.put("consignee", order.getConsignee());
            map.put("couponPrice", order.getCouponPrice());
            map.put("freightPrice", order.getFreightPrice());
            map.put("goodsPrice", order.getGoodsPrice());
            map.put("id", order.getId());
            map.put("mobile", order.getMobile());
            map.put("orderSn", order.getOrderSn());

            Short orderStatus = order.getOrderStatus();
            map.put("orderStatusText", OrderStatus.getOrderStatusText(orderStatus));
            map.put("handleOption", new HandleOption(order));
            return map;
        }

        @Override
        public void deleteOrder (Integer orderId){
            orderMapper.deleteByPrimaryKey(orderId);
        }

    }
