package com.ovit.nswy.im.service.impl;

import com.ovit.nswy.im.fastdfs.FastDFSClient;
import com.ovit.nswy.im.service.UpLoadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DESCRIPTION:
 * 文件上传接口
 * @author zouyan
 * @create 2018-01-29 下午4:32
 * created by fuck~
 **/
@Service
public class UpLoadServiceImpl implements UpLoadService {

    private static final Logger logger = LogManager.getLogger(UpLoadServiceImpl.class);

    @Value("${fdfs.http.uploadIp}")
    private String uploadIp;

    @Value("${fdfs.conf}")
    private String conf;

    /**
     * 文件上传至fastdfs，返回fastdfs文件访问地址
     * @param uploadFile
     * @return
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile uploadFile) throws Exception {
        //获取文件名
        String fileName = uploadFile.getOriginalFilename().toLowerCase();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf(".")+1);
        FastDFSClient client = new FastDFSClient(conf);
        String uploadUrl = client.uploadFile(uploadFile.getBytes(), suffixName);
        String fileUrl = new StringBuffer("http://").append(uploadIp).append("/").append(uploadUrl.substring(11, uploadUrl.length())).toString();
        logger.debug("上传文件:{}, 上传后的文件地址:{}", fileName, fileUrl);
        return fileUrl;
    }
}
