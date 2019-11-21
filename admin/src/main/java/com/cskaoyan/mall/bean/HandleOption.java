package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class HandleOption {
    private Boolean cancel;
    private Boolean delete;
    private Boolean pay;
    private Boolean comment;
    private Boolean confirm;
    private Boolean refund;
    private Boolean rebuy;

    public HandleOption() {
    }

    public HandleOption(Order order){
        Short status = order.getOrderStatus();
        this.cancel = OrderStatus.canCancel(status);     // 是否可取消
        this.delete = OrderStatus.canDelete(status);   // 是否可删除
        this.pay = OrderStatus.canPay(status);
        this.comment = OrderStatus.canComment(order);
        this.confirm = OrderStatus.canConfirm(status);  // 是否可确认收款
        this.refund = OrderStatus.canRefund(status);    // 是否可退款
        this.rebuy = true;  // 是否可重买
    }

    public HandleOption(Boolean cancel, Boolean delete, Boolean pay, Boolean comment, Boolean confirm, Boolean refund, Boolean rebuy) {
        this.cancel = cancel;
        this.delete = delete;
        this.pay = pay;
        this.comment = comment;
        this.confirm = confirm;
        this.refund = refund;
        this.rebuy = rebuy;
    }

    public static HandleOption getStatus1Option() {
        HandleOption handleOption = new HandleOption();
        handleOption.setCancel(true);
        handleOption.setDelete(false);
        handleOption.setPay(true);
        handleOption.setComment(false);
        handleOption.setConfirm(false);
        handleOption.setRefund(false);
        handleOption.setRebuy(false);
        return handleOption;
    }

}
