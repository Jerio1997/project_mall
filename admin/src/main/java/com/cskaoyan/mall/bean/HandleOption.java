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
        this.cancel = false;//问题1(order.getOrderStatus() == 1)
        this.delete = order.getDeleted();
        this.pay = false;//问题2
        this.comment = false;//问题3
        this.confirm = (order.getConfirmTime() != null);
        this.refund = false;//问题4
        this.rebuy = false;//问题5
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
