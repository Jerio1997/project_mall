package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wx/goods")
public class WxGoodsController {
    @Autowired
    GoodsService goodsService;

    @RequestMapping("count")
    public BaseReqVo goodsCount() {
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        int i = goodsService.queryGoodsCounts(null, null);
        baseReqVo.setData(i);
        return baseReqVo;
    }

}
