package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.BrandListReqVo_Wx;
import com.cskaoyan.mall.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/brand")
public class WxBrandController {
    @Autowired
    BrandService brandService;

    /**
     * 查询所有品牌
     * @param page
     * @param size
     * @return
     */
    @GetMapping("list")
    public BaseReqVo<BrandListReqVo_Wx> getBrandList(Integer page, Integer size){
        Map<String, Object> map = brandService.getBrandList(page, size, null, null, "add_time", "desc");
        Long total = (Long) map.get("total");
        Integer totalPages = Math.toIntExact((total / size) + 1);
        List<Brand> brandList = (List<Brand>) map.get("items");
        BrandListReqVo_Wx brandListReqVo_wx = new BrandListReqVo_Wx();
        brandListReqVo_wx.setBrandList(brandList);
        brandListReqVo_wx.setTotalPages(totalPages);
        BaseReqVo<BrandListReqVo_Wx> baseReqVo = new BaseReqVo<>();
        baseReqVo.setData(brandListReqVo_wx);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }
}
