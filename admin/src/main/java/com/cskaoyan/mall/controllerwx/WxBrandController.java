package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.BrandListResVo_Wx;
import com.cskaoyan.mall.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
    public BaseReqVo<BrandListResVo_Wx> getBrandList(Integer page, Integer size){
        Map<String, Object> map = brandService.getBrandList(page, size, null, null, "add_time", "desc");
        Long total = (Long) map.get("total");
        Integer totalPages = Math.toIntExact((total / size) + 1);
        List<Brand> brandList = (List<Brand>) map.get("items");
        BrandListResVo_Wx brandListResVo_wx = new BrandListResVo_Wx();
        brandListResVo_wx.setBrandList(brandList);
        brandListResVo_wx.setTotalPages(totalPages);
        BaseReqVo<BrandListResVo_Wx> baseReqVo = new BaseReqVo<>();
        baseReqVo.setData(brandListResVo_wx);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 查询品牌的具体信息
     * @param id
     * @return
     */
    @GetMapping("detail")
    public BaseReqVo getBrandDetail(Integer id){
        Map<String, Object> map = brandService.getBrandList(1, 10, id, null, "add_time", "desc");
        List<Brand> brandList = (List<Brand>) map.get("items");
        Brand brand = brandList.get(0);
        BaseReqVo baseReqVo = new BaseReqVo();
        HashMap<String, Object> brandResVo = new HashMap<>();
        brandResVo.put("brand",brand);
        baseReqVo.setData(brandResVo);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }
}
