package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.CommentReplyService;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("admin/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    CommentReplyService commentReplyService;

    @RequestMapping("list")
    @RequiresPermissions(value={"admin:order:list"},logical = Logical.OR)
    public BaseReqVo listOrder(int page, int limit, Short[] orderStatusArray, Integer userId, String orderSn, String sort, String order) {
        Map<String, Object> data = orderService.getOrderList(page, limit, orderStatusArray, userId, orderSn, sort, order);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(data);
        return baseReqVo;
    }

    @RequestMapping("detail")
    @RequiresPermissions(value={"admin:order:detail"},logical = Logical.OR)
    public BaseReqVo orderDetail(int id) { // 此id为 orderId
        List<OrderGoods> orderGoodsByOrderId = orderService.getOrderGoodsByOrderId(id);
        Order order = orderService.getOrderById(id);
        User user = userService.getUserById(order.getUserId());
        User user1 = new User(); //防止个人隐私暴露
        user1.setAvatar(user.getAvatar());
        user1.setNickname(user.getNickname());
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        Map<String, Object> data = new HashMap<>();
        data.put("orderGoods", orderGoodsByOrderId);
        data.put("user", user1);
        data.put("order", order);
        baseReqVo.setData(data);
        return baseReqVo;
    }

    @PostMapping("reply")
    @RequiresPermissions(value={"admin:order:reply"},logical = Logical.OR)
    public BaseReqVo replyComment(@RequestBody CommentReplyResVo commentReplyResVo ){
        BaseReqVo baseReqVo = new BaseReqVo();
        int commentId = commentReplyResVo.getCommentId();
        String content = commentReplyResVo.getContent();
        int i = commentReplyService.addCommentReply(commentId, content);
        if(i == 0){
            baseReqVo.setErrno(622);
            baseReqVo.setErrmsg("订单商品已回复！");
        }else if (i == 1){
            baseReqVo.setErrno(0);
            baseReqVo.setErrmsg("成功");
        }

        return baseReqVo;
    }
}
