package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Ad;
import com.cskaoyan.mall.bean.AdExample;
import com.cskaoyan.mall.bean.AdListResVo;
import com.cskaoyan.mall.mapper.AdMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/15 21:36
 **/
@Service
public class AdServiceImpl implements AdService {

    @Autowired
    AdMapper adMapper;

    @Override
    public int queryAdCounts() {
        AdExample adExample = new AdExample();
        long total = adMapper.countByExample(adExample);
        return (int)total;
    }

    /*@Override
    public List<Ad> queryAd(Integer page, Integer limit, String name, String content, String sort, String order) {
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        AdExample adExample = new AdExample();
        adExample.setOrderByClause(orderByClause);
        AdExample.Criteria criteria = adExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        List<Ad> ads = adMapper.selectByExample(adExample);
        return ads;
    }*/

    @Override
    public int updateAd(Ad ad) {
        int result = adMapper.updateByPrimaryKey(ad);//更改成功的条数
        return result;
    }

    @Override
    public int createAd(Ad ad) {
        AdExample example = new AdExample();
        example.setOrderByClause("id desc");
        List<Ad> adList = adMapper.selectByExample(example);
        Integer id;
        if(adList == null || adList.size() == 0){
            id = 0;
        } else {
            id = adList.get(0).getId();
        }
        ad.setId(++id);
        ad.setDeleted(false);
        int result = adMapper.insert(ad);//插入成功的条数
        return result;
    }

    @Override
    public int deleteAd(Ad ad) {
        ad.setDeleted(true);
        ad.setUpdateTime(new Date());
        int result = adMapper.updateByPrimaryKey(ad);
        return result;
    }

    @Override
    public AdListResVo queryListAd(Integer page, Integer limit, String name, String content, String sort, String order) {
        AdListResVo adListResVo = new AdListResVo();
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        AdExample adExample = new AdExample();
        adExample.setOrderByClause(orderByClause);
        AdExample.Criteria criteria = adExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        List<Ad> ads = adMapper.selectByExample(adExample);
        adListResVo.setTotal(ads.size());
        adListResVo.setItems(ads);
        return adListResVo;
    }
}
