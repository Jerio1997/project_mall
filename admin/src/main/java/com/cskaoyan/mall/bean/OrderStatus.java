package com.cskaoyan.mall.bean;

public class OrderStatus {

    public static String getOrderStatusText(int orderStatus) {
        String orderStatusText;
        switch (orderStatus) {
            case 101:
                orderStatusText = "未付款";
                break;
            case 102:
                orderStatusText = "用户取消";
                break;
            case 103:
                orderStatusText = "系统取消";
                break;
            case 201:
                orderStatusText = "已付款";
                break;
            case 202:
                orderStatusText = "申请退款";
                break;
            case 203:
                orderStatusText = "已退款";
                break;
            case 301:
                orderStatusText = "已发货";
                break;
            case 401:
                orderStatusText = "用户收货";
                break;
            case 402:
                orderStatusText = "已收货";
                break;
            default:
                orderStatusText = "订单异常";
                break;
        }
        return orderStatusText;
    }

    public static boolean canCancel(int orderStatus) {
        return orderStatus == 101;
    }

    public static boolean canDelete(int orderStatus) {
        switch (orderStatus) {
            case 102:
            case 103:
            case 203:
            case 402:
                return true;
            default:
                return false;
        }
    }

    public static boolean canPay(int orderStatus) {
        return orderStatus == 101;
    }

    public static boolean canConfirm(int orderStatus) {
        return orderStatus == 301;
    }

    public static boolean canComment(Order order) {
        return order.getComments() != null;
    }

    public static boolean canRefund(int orderStatus) {
        switch (orderStatus) {
            case 201:
            case 301:
            case 401:
            case 402:
                return true;
            default:
                return false;
        }
    }

    public static Boolean canReBuy(Short status) {
        return status == 402;
    }
}
