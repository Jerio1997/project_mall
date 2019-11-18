package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/18 18:02
 **/
@Data
public class CouponUserListResVo {

    private Integer total;

    private List<CouponUser> items;

}
