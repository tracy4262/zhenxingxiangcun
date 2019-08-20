package com.ovit.nswy.converter.controller;

import com.ovit.nswy.converter.utils.OKHttpUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
* DESCRIPTION:
* 根据文件资源url返回base64串接口
* @author zouyan
* @create 2018/3/12-下午3:43
* created by fuck~
**/
@RestController
@RequestMapping("/base64")
public class Base64Controller {

    private static final Logger logger = LogManager.getLogger(Base64Controller.class);

    /**
     * 根据文件资源url返回base64串
     * 请求参数：
     * {
     *     "url" : "http://192.168.7.61/00/72/wKgHPVqrHjeAS7QOAAL2BMGZzaI658.pdf"
     * }
     * 返回数据示例:
     * {
     "data": {
     "base64": "iVBORw0AAAAASUVORK5CYII="
     },
     "code": "0",
     "msg": ""
     }
     * @param params
     * @return
     */
    @RequestMapping(value="/converter")
    public Map<String, Object> converter(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        Map<String, Object> data =  new HashMap<String, Object>();
        result.put("data", data);
        try {
            String url = params.get("url").toString();
            byte[] resBytes = OKHttpUtil.getInstance().excuteHttpGet(url);
            String resBase64Str = Base64.encodeBase64String(resBytes);
            result.put("code", "0");
            result.put("msg", "");
            data.put("base64", resBase64Str);
            logger.debug("根据资源文件url转换文件base64串成功，url:{}", url);
        } catch (Exception e) {
            logger.error("根据资源文件url转换文件base64串失败！ url:{}", params.get("url"), e);
            result.put("code", "1");
            result.put("msg", "根据资源文件url转换文件base64串失败！\n失败原因:" + e.getMessage());
        }
        return result;
    }
}