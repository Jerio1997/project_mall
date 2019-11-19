package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class GoodsDetailResVo_Wx {
    private Integer userHasCollect;     //得到用户id  查询 collect
    private String shareImage;
    private CommentBean comment;
    private Brand brand;
    private Goods info;                 //Goods
    private List<SpecificationListBean> specificationList;
    private List<GrouponRules> groupon;
    private List<Issue> issue;
    private List<GoodsAttribute> attribute;
    private List<GoodsProduct> productList;
    public static class CommentBean {
        /**
         * data : []
         * count : 0
         */

        private int count;
        private List<Comment> data;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<Comment> getData() {
            return data;
        }

        public void setData(List<Comment> data) {
            this.data = data;
        }
    }
    public static class SpecificationListBean {
        private String name;
        private List<GoodsSpecification> valueList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<GoodsSpecification> getValueList() {
            return valueList;
        }

        public void setValueList(List<GoodsSpecification> valueList) {
            this.valueList = valueList;
        }
    }
}
