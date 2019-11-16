package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Storage;
import com.cskaoyan.mall.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("admin/storage")
public class StorageController {

    @Autowired
    StorageService storageService;

    /**
     * 文件上传， 上传至target/classes/static 文件夹下
     * 不需要fileupload组件
     * 可不写 spring.resources.static-locations=classpath:/...
     * @param file
     * @return
     */
    @RequestMapping("create")
    public BaseReqVo createStorage(@RequestParam("file") MultipartFile file) {
        //通过此文件获取static文件夹的绝对路径
        File utilFile = new File("admin\\target\\classes\\static");
        //访问路径
        String urlPrefix = "http://localhost:8080/wx/storage/fetch/";
        UUID uuid = UUID.randomUUID();
        // split中的参数为正则表达式， 不能直接写"."
        String[] split = file.getOriginalFilename().split("\\.");
        String suffix = split[split.length-1];
        String newFilename = uuid.toString().replace("-", "") + "." + suffix;
        File myfile = new File(utilFile.getAbsolutePath() + "\\" + newFilename);
//        System.out.println(myfile.getAbsolutePath());
        try {
            file.transferTo(myfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Storage storage = new Storage();
        storage.setAddTime(new Date());
        storage.setKey(newFilename);
        storage.setUpdateTime(new Date());
        storage.setName(file.getOriginalFilename());
        storage.setSize((int) file.getSize());
        storage.setUrl(urlPrefix + newFilename);
        storage.setType(file.getContentType());
        int status = storageService.addStorage(storage);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(storage);
        return baseReqVo;
    }
}
