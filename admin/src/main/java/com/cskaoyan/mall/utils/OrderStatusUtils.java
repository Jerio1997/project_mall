package com.cskaoyan.mall.utils;

import java.util.HashMap;
import java.util.Map;

public class OrderStatusUtils {
    public static final Map<String, Integer> statusTextCodeMap = new HashMap<>();
    public static final Map<Short, String> statusCodeTextMap = new HashMap<>();
    public static final Map<Integer, Short[]> statusTypeCodeMap = new HashMap<>();
    static {
        statusTextCodeMap.put("未付款", 101);
        statusTextCodeMap.put("用户取消", 102);
        statusTextCodeMap.put("系统取消", 103);
        statusTextCodeMap.put("已付款", 201);
        statusTextCodeMap.put("申请退款", 202);
        statusTextCodeMap.put("已退款", 203);
        statusTextCodeMap.put("已发货", 301);
        statusTextCodeMap.put("用户收货", 401);
        statusTextCodeMap.put("系统收货", 402);

        statusCodeTextMap.put((short) 101, "未付款");
        statusCodeTextMap.put((short) 102,"用户取消");
        statusCodeTextMap.put((short) 103, "系统取消");
        statusCodeTextMap.put((short) 201, "已付款");
        statusCodeTextMap.put((short) 202, "申请退款");
        statusCodeTextMap.put((short) 203, "已退款");
        statusCodeTextMap.put((short) 301, "已发货");
        statusCodeTextMap.put((short) 401, "用户收货");
        statusCodeTextMap.put((short) 402, "系统收货");

        statusTypeCodeMap.put(0, new Short[0]);
        statusTypeCodeMap.put(1, new Short[]{101});
        statusTypeCodeMap.put(2, new Short[]{201, 202});
        statusTypeCodeMap.put(3, new Short[]{301, 102, 103});
        statusTypeCodeMap.put(4, new Short[]{401, 402});
    }

    public static Integer getCodeByTest(String text) {
        Integer code = statusTextCodeMap.get(text);
        return code;
    }

    public static String getTextByCode(Short code) {
        String text = statusCodeTextMap.get(code);
        return text;
    }

    public static Short[] getCodeByType(Integer type) {
        Short[] Codes = statusTypeCodeMap.get(type);
        return Codes;
    }
}
