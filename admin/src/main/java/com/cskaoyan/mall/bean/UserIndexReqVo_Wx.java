package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class UserIndexReqVo_Wx {
    private OrderBean order;
        @Data
        public static class OrderBean {
            /**
             * unrecv : 0
             * uncomment : 0
             * unpaid : 17
             * unship : 0
             */

            private int unrecv;     //order_status 301
            private int uncomment;  //comments
            private int unpaid;     // order_status 101
            private int unship;     //待发货order_status 201
        }
    }

