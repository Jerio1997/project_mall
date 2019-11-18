package com.cskaoyan.mall.bean;

import java.util.List;

public class GoodsStatVo {
    /**
     * errno : 0
     * data : {"columns":["day","orders","products","amount"],"rows":[{"amount":65017,"orders":26,"day":"2019-08-20","products":152},{"amount":36110,"orders":42,"day":"2019-08-21","products":56},{"amount":56686,"orders":84,"day":"2019-08-22","products":212},{"amount":69070.5,"orders":63,"day":"2019-08-23","products":220},{"amount":28473.5,"orders":77,"day":"2019-10-04","products":138},{"amount":36875.8,"orders":68,"day":"2019-10-05","products":301},{"amount":6136.6,"orders":7,"day":"2019-10-06","products":20},{"amount":51191.9,"orders":79,"day":"2019-10-07","products":151},{"amount":67088.1,"orders":52,"day":"2019-10-08","products":131}]}
     * errmsg : 成功
     */

    private int errno;
    private DataBean data;
    private String errmsg;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public static class DataBean {
        private List<String> columns;
        private List<RowsBean> rows;

        public List<String> getColumns() {
            return columns;
        }

        public void setColumns(List<String> columns) {
            this.columns = columns;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * amount : 65017.0
             * orders : 26
             * day : 2019-08-20
             * products : 152
             */

            private double amount;
            private int orders;
            private String day;
            private int products;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public int getOrders() {
                return orders;
            }

            public void setOrders(int orders) {
                this.orders = orders;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public int getProducts() {
                return products;
            }

            public void setProducts(int products) {
                this.products = products;
            }
        }
    }
}
