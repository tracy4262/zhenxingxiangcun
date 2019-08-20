package com.ovit.nswy.member.web.product.base;

import com.alibaba.fastjson.JSON;
import com.ovit.nswy.member.service.ProductPositionService;
import com.ovit.nswy.member.web.base.Result;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
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
 * 生产基地地理位置
 *
 * @author haoWen
 * @create 2017-12-25 15:13
 **/
@RestController
@RequestMapping("/product-geographical-position")
public class ProductPositionController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ProductPositionService productPositionService;

    private static final int ERROR_CODE = 500;

    /**
     * 查询地理位置信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST )
    public Result queryGeographicalPosition(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("查询地理位置信息，参数：{}",params);
            result = new Result();
            Map<String,Object> geographicalPositionMap = productPositionService.queryGeographicalPosition(params);
            result.setData(geographicalPositionMap);
            logger.info("地理位置查询成功：{}",geographicalPositionMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("查询地理位置失败：{}",e);
        }
        return result;
    }

    /**
     * 修改地理位置信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST )
    public Result updateGeographicalPosition(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("修改地理位置，参数：{}",params);
            result = new Result();
            List list = JSON.parseArray(MapUtils.getString(params,"locationId"));
            String locationId = StringUtils.join(list.toArray(),",");
            params.put("locationId",locationId);
            productPositionService.saveGeographicalPosition(params);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("修改地理位置失败：{}",e);
        }
        return result;
    }

}
