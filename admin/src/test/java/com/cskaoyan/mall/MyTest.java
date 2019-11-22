package com.cskaoyan.mall;

import com.cskaoyan.mall.utils.Md5Util;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

public class MyTest {
    @Test
    public void test1() throws NoSuchAlgorithmException {
        String a = Md5Util.getMd5("123");
        System.out.println(a);
    }
}
