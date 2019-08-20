package com.ovit.nswy.converter.controller;

import com.ovit.nswy.converter.service.UpLoadService;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/pdf/upload")
public class UploadController {

    private static final Logger logger = LogManager.getLogger(UploadController.class);

    @Resource
    private UpLoadService upLoadService;

    /**
     * 上传文件转pdf接口
     注：
     jpg、png、pdf不转换文件格式直接返回存储地址
     doc、docx上传转换成pdf文件格式返回地址
     * 返回数据示例：
     {
         "code": 0 //0表示成功，其它表示失败
         ,"msg": "" //失败信息
         ,"data": {
         "src": "http://xxx/00/70/xxxx.pdf" //pdf文件url
         ,"name": "test.doc" //文件名
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
            //获取文件名
            String fileName = uploadFile.getOriginalFilename().toLowerCase();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf(".")+1);
            if (!("doc".equals(suffixName) || "docx".equals(suffixName) || "pdf".equals(suffixName)
                    || "jpg".equals(suffixName) || "png".equals(suffixName)
                    || "xlsx".equals(suffixName) || "xls".equals(suffixName))) {
                result.put("code", "1");
                result.put("msg", "上传文件格式仅支持doc、docx、pdf、jpg、png！");
                return result;
            }
            Map<String,Object> fileMap = upLoadService.uploadFile(uploadFile);
            if (MapUtils.isNotEmpty(fileMap)) {
                data.put("src", MapUtils.getString(fileMap, "pdfFileUrl"));
                data.put("origin", MapUtils.getString(fileMap, "fileUrl"));
            }
            result.put("code", "0");
            result.put("msg", "");
            //data.put("src", fileUrl);
            data.put("name", uploadFile.getOriginalFilename());
            logger.debug("上传文件成功，result:{}", result);
        } catch (Exception e) {
            logger.error("文件上传失败！ 文件名:{}", uploadFile.getOriginalFilename(), e);
            result.put("code", "1");
            result.put("msg", "文件上传失败！请重新上传！\n失败原因:" + e.getMessage());
        }
        return result;
    }
}