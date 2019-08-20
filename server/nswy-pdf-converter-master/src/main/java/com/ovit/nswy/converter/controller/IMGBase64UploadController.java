package com.ovit.nswy.converter.controller;

import com.ovit.nswy.converter.service.UpLoadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
* DESCRIPTION:
* 文件上传转PDF接口
* @author zouyan
* @create 2018/3/12-下午3:43
* created by fuck~
**/
@RestController
@RequestMapping("/imgbase64")
public class IMGBase64UploadController {

    private static final Logger logger = LogManager.getLogger(IMGBase64UploadController.class);

    @Resource
    private UpLoadService upLoadService;

    /**
     * 图片base64上传
     * 请求数据示例：
     * {
     "fileName" : "test.jpg",
     "fileData" : "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDA=="
     }
     * 返回数据示例：
     {
     "code": 0 //0表示成功，其它表示失败
     ,"msg": "" //失败信息
     ,"data": {
     "src": "http://xxx/00/70/xxxx.pdf" //pdf文件url
     ,"name": "test.doc" //文件名
     }
     }
     * @param params
     * @return
     */
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public Map<String, Object> uploadFile(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        Map<String, Object> data =  new HashMap<String, Object>();
        result.put("data", data);
        try {
            String fileName = params.get("fileName").toString();
            String fileData = params.get("fileData").toString();
            //若存在data uri scheme则去除
            if (fileData.startsWith("data:")) {
                fileData = fileData.substring(fileData.indexOf(",") + 1, fileData.length());
            }
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf(".")+1);
            if (!("jpg".equals(suffixName) || "png".equals(suffixName))) {
                result.put("code", "1");
                result.put("msg", "上传文件格式仅支持jpg、png！");
                return result;
            }
            String fileUrl = upLoadService.uploadBase64Img(fileName, fileData);
            result.put("code", "0");
            result.put("msg", "");
            data.put("src", fileUrl);
            data.put("name", fileName);
            logger.debug("base64图片上传成功，result:{}", result);
        } catch (Exception e) {
            logger.error("base64图片上传失败！ 文件名:{}", params.get("fileName"), e);
            result.put("code", "1");
            result.put("msg", "文件上传失败！请重新上传！\n失败原因:" + e.getMessage());
        }
        return result;
    }

    /**
     * 上传图片转base64接口
     * 返回数据示例：
     {
     "code": 0 //0表示成功，其它表示失败
     ,"msg": "" //失败信息
     ,"data": {
     "base64": "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDA==" //base64串
     ,"name": "test.jpg" //文件名
     }
     }
     * @param uploadFile
     * @return
     */
    @RequestMapping(value="/converter")
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile uploadFile) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        Map<String, Object> data =  new HashMap<String, Object>();
        result.put("data", data);
        try {
            //获取文件名
            String fileName = uploadFile.getOriginalFilename().toLowerCase();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf(".")+1);
            if (!("jpg".equals(suffixName) || "png".equals(suffixName))) {
                result.put("code", "1");
                result.put("msg", "上传文件格式仅支持jpg、png！");
                return result;
            }
            //转换base64
            String imgBase64Str = org.apache.commons.codec.binary.Base64.encodeBase64String(uploadFile.getBytes());
            result.put("code", "0");
            result.put("msg", "");
            data.put("base64", imgBase64Str);
            data.put("name", uploadFile.getOriginalFilename());
            logger.debug("上传文件转换base64成功,文件名:{}", uploadFile.getOriginalFilename());
        } catch (Exception e) {
            logger.error("文件上传转换base64失败！ 文件名:{}", uploadFile.getOriginalFilename(), e);
            result.put("code", "1");
            result.put("msg", "文件上传转换base64失败！请重新上传！\n失败原因:" + e.getMessage());
        }
        return result;
    }
}