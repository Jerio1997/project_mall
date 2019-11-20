package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Storage;
import com.cskaoyan.mall.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("wx/storage")
public class WxStorageController {

    @Autowired
    SystemService systemService;

    @RequestMapping("upload")
    public BaseReqVo<Storage> uploadStorage(MultipartFile file, HttpServletRequest request) {
        // 不需要 fileupload 组件
        // 拼接 url 前缀与后缀，并且已经在 yml 文件中添加 url 前缀映射路径
        String urlPrefix = "http://" + request.getServerName() + ":" + request.getServerPort() + "/wx/storage/fetch/";
        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String urlSuffix = split[split.length - 1];
        // 生成随机文件名
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replace("-", "");
        String key = s + "." + urlSuffix;
        // 存储文件
        File filePath = new File("admin\\target\\classes\\static", key);
        //        修改为文件系统路径
//        File filePath = new File("D:/picture");
        String absolutePath = filePath.getAbsolutePath();
        try {
            file.transferTo(new File(absolutePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 封装数据
        Storage storage = new Storage();
        storage.setKey(key);
        storage.setName(file.getOriginalFilename());
        storage.setType(file.getContentType());
        storage.setSize(file.getSize());
        storage.setUrl(urlPrefix + key);
        storage.setAddTime(new Date());
        storage.setUpdateTime(new Date());
        storage.setDeleted(false);
        BaseReqVo<Storage> baseReqVo = new BaseReqVo<>();
        Storage createdStorage = systemService.createStorage(storage);
        baseReqVo.setData(createdStorage);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

}
