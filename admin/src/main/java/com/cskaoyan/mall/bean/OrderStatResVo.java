package com.cskaoyan.mall.bean;

import java.util.List;

public class OrderStatResVo {

    /**
     * errno : 0
     * data : {"columns":["day","orders","customers","amount","pcr"],"rows":[{"amount":9622,"orders":8,"customers":1,"day":"2019-08-20","pcr":9622},{"amount":5218,"orders":13,"customers":1,"day":"2019-08-21","pcr":5218},{"amount":46212,"orders":71,"customers":1,"day":"2019-08-22","pcr":46212},{"amount":54494.6,"orders":52,"customers":1,"day":"2019-08-23","pcr":54494.6},{"amount":-182644.2,"orders":63,"customers":1,"day":"2019-10-04","pcr":-182644.2},{"amount":5406.6,"orders":58,"customers":2,"day":"2019-10-05","pcr":2703.3},{"amount":-87137.4,"orders":5,"customers":1,"day":"2019-10-06","pcr":-87137.4},{"amount":20486,"orders":68,"customers":3,"day":"2019-10-07","pcr":6828.67},{"amount":54655.1,"orders":44,"customers":3,"day":"2019-10-08","pcr":18218.37}]}
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
             * amount : 9622.0
             * orders : 8
             * customers : 1
             * day : 2019-08-20
             * pcr : 9622.0
             */

            private double amount;
            private int orders;
            private int customers;
            private String day;
            private double pcr;

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

            public int getCustomers() {
                return customers;
            }

            public void setCustomers(int customers) {
                this.customers = customers;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public double getPcr() {
                return pcr;
            }

            public void setPcr(double pcr) {
                this.pcr = pcr;
            }
        }
    }
}
