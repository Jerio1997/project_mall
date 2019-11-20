package com.cskaoyan.mall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
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
        private List<DataBean> data;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * userInfo : {"nickName":"dr lan","avatarUrl":""}
             * addTime : 2019-11-20 01:04:19
             * picList : ["http://192.168.2.100:8081/wx/storage/fetch/mlr2rohwtorehgnxykvv.png"]
             * content : 生存战略
             */
            private Integer id;

            private String nickName;

            private String avatar;

            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            private Date addTime;

            private String content;

            private String [] picList;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public Date getAddTime() {
                return addTime;
            }

            public void setAddTime(Date addTime) {
                this.addTime = addTime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String[] getPicList() {
                return picList;
            }

            public void setPicList(String[] picList) {
                this.picList = picList;
            }
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
