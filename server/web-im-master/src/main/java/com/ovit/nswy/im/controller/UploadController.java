package com.ovit.nswy.im.controller;

import com.ovit.nswy.im.service.UpLoadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* DESCRIPTION:
* im图片、文件上传接口
* @author zouyan
* @create 2018/1/30-上午9:22
* created by fuck~
**/
@RestController
@RequestMapping("/im/upload")
public class UploadController {

    private static final Logger logger = LogManager.getLogger(UploadController.class);

    @Resource
    private UpLoadService upLoadService;

    /**
     * im上传图片接口
     * 返回数据示例：
     {
         "code": 0 //0表示成功，其它表示失败
         ,"msg": "" //失败信息
         ,"data": {
            "src": "http://cdn.xxx.com/upload/images/a.jpg" //图片url
         }
     }
     * @param uploadImg
     * @return
     */
    @RequestMapping(value="/img")
    public Map<String, Object> uploadImg(@RequestParam("file") MultipartFile uploadImg) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        Map<String, Object> data =  new HashMap<String, Object>();
        result.put("data", data);
        try {
            String fileUrl = upLoadService.uploadFile(uploadImg);
            result.put("code", "0");
            result.put("msg", "");
            data.put("src", fileUrl);
            logger.debug("上传图片成功，result:{}", result);
        } catch (Exception e) {
            logger.error("图片上传失败！ 文件名:{}", uploadImg.getOriginalFilename().toLowerCase(), e);
            result.put("code", "1");
            result.put("msg", "图片上传失败！请重新上传！\n失败原因:" + e.getMessage());
        }
        return result;
    }

    /**
     * im上传文件接口
     * 返回数据示例：
     {
         "code": 0 //0表示成功，其它表示失败
         ,"msg": "" //失败信息
         ,"data": {
         "src": "http://cdn.xxx.com/upload/file/LayIM.zip" //文件url
         ,"name": "LayIM.zip" //文件名
         }
     }
     * @param uploadFile
     * @return
     */
    @RequestMapping(value="/file")
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile uploadFile) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        Map<String, Object> data =  new HashMap<String, Object>();
        result.put("data", data);
        try {
            String fileUrl = upLoadService.uploadFile(uploadFile);
            result.put("code", "0");
            result.put("msg", "");
            data.put("src", fileUrl);
            //data.put("name", uploadFile.getOriginalFilename());
            data.put("name", uploadFile.getOriginalFilename().getBytes("utf-8"));
            logger.debug("上传文件成功，result:{}", result);
        } catch (Exception e) {
            logger.error("文件上传失败！ 文件名:{}", uploadFile.getOriginalFilename(), e);
            result.put("code", "1");
            result.put("msg", "文件上传失败！请重新上传！\n失败原因:" + e.getMessage());
        }
        return result;
    }
}