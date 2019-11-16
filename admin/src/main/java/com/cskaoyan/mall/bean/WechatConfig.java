package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class WechatConfig {
    private String litemall_wx_index_new;//新品首发栏目商品显示数量
    private String litemall_wx_catlog_goods;//分类栏目商品显示数量
    private String litemall_wx_catlog_list;//分类栏目显示数量
    private String litemall_wx_share;//商品分享功能
    private String litemall_wx_index_brand;//品牌制造商直供栏目品牌商显示数量
    private String litemall_wx_index_hot;//人气推荐栏目商品显示数量
    private String litemall_wx_index_topic;//专题精选栏目显示数量

}
