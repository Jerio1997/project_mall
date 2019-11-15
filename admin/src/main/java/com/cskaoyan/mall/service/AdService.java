package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Ad;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/15 21:35
 **/

public interface AdService {

    int queryAdCounts();

    List<Ad> queryAd(Integer page, Integer limit, String name, String content, String sort, String order);

}
