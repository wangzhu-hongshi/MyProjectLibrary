package com.mmall.service.Impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.utils.FTPUtil;
import com.mmall.utils.PropertiesUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;
import org.slf4j.Logger;
@Service("ifileService")
@Slf4j
public class IFileServiceImpl implements IFileService {
//    private Logger logger = (Logger) LoggerFactory.getLogger(IFileServiceImpl.class);

    /**
     * 上传文件
     * @param file
     * @return
     */
    public String upload(MultipartFile file, String prefix){

        UploadManager uploadManager = new UploadManager(new Configuration(Zone.zone1()));
        Auth auth = Auth.create(PropertiesUtil.getProperty("assess_key"), PropertiesUtil.getProperty("secret_key"));
        String token = auth.uploadToken(PropertiesUtil.getProperty("bucket"));
        Response response;
        //定义唯一文件名
        String fileName = prefix + "/" + UUID.randomUUID().toString();
        try {
            //获取文件的字节流
            InputStream inputStream = file.getInputStream();
            //将字节流转换为字节数组
            byte[] bytes = IOUtils.toByteArray(inputStream);
            //上传文件  把文件的字节数组 文件名（key） token权限 传进去
            response = uploadManager.put(bytes, fileName, token);
            //判断是否成功
            if (response.statusCode == 200) {
                return fileName;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public String upload(byte[] bytes, String fileName){
        UploadManager uploadManager = new UploadManager(new Configuration(Zone.zone1()));
        Auth auth = Auth.create(PropertiesUtil.getProperty("assess_key"), PropertiesUtil.getProperty("secret_key"));
        String token = auth.uploadToken(PropertiesUtil.getProperty("bucket"));
        Response response;
        try {
            response = uploadManager.put(bytes, fileName, token);
            if (response.statusCode == 200) {
                return fileName;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
