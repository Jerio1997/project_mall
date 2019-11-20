package com.cskaoyan.mall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentResVo_Wx {
    private int count;
    private int currentPage;
    private List<DataBean> data;

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

            private UserInfoBean userInfo;
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            private Date addTime;
            private String content;
            private String [] picList;

            public UserInfoBean getUserInfo() {
                return userInfo;
            }

            public void setUserInfo(UserInfoBean userInfo) {
                this.userInfo = userInfo;
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

            public static class UserInfoBean {
                /**
                 * nickName : dr lan
                 * avatarUrl :
                 */

                private String nickName;
                private String avatarUrl;

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public String getAvatarUrl() {
                    return avatarUrl;
                }

                public void setAvatarUrl(String avatarUrl) {
                    this.avatarUrl = avatarUrl;
                }
            }
        }
    }
