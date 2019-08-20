package com.ovit.nswy.portal.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.core.Result;
import com.ovit.nswy.portal.core.ResultCode;
import com.ovit.nswy.portal.service.DynamicService;
import com.ovit.nswy.portal.util.ConstantPool;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

/**
 * 动态
 *
 * @author haoWen
 * @create 2018-01-15 12:39
 **/
@RestController
@RequestMapping("/dynamic")
public class DynamicController implements ConstantPool{
    @Autowired
    private DynamicService dynamicService;

    private Logger logger = LogManager.getLogger(this.getClass());

    /**
     * 动态信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/getDynamicInfo",method = RequestMethod.POST)
    public Result getDynamicInfo(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("动态查询：{}",params);
            if ("全部".equals(MapUtils.getString(params,"label"))) {
                params.remove("label");
            }
            PageInfo<List<Map<String,Object>>> info = dynamicService.queryDynamicInfo(params);
            logger.info("查询成功：{}",info.getList());
            result.setData(info);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("动态查询失败：{}",e);
        }
        return result;
    }
}
