package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class BaseReqVo<T> {
    T data;
    String errmsg;
    int errno;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
