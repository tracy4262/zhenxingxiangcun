package com.ovit.nswy.im.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * DESCRIPTION:
 * 文件上传接口
 * @author zouyan
 * @create 2018-01-29 下午4:31
 * created by fuck~
 **/
public interface UpLoadService {

    /**
     * 文件上传至fastdfs，返回fastdfs文件访问地址
     * @param uploadFile
     * @return
     * @throws Exception
     */
    public String uploadFile(MultipartFile uploadFile) throws Exception;
}
