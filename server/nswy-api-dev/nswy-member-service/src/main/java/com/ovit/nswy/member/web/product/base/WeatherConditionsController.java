package com.ovit.nswy.member.web.product.base;

import com.ovit.nswy.member.service.WeatherConditionsService;
import com.ovit.nswy.member.web.base.Result;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * 气候条件信息
 *
 * @author haoWen
 * @create 2017-12-25 16:35
 **/
@RestController
@RequestMapping("/product-weather-conditions")
public class WeatherConditionsController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private WeatherConditionsService weatherConditionsService;

    private static final int ERROR_CODE = 500;

    /**
     * 查询气候条件信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST )
    public Result queryWeatherConditions(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            result = new Result();
            Map<String,Object> weatherConditionsMap =  weatherConditionsService.queryWeatherConditionsByProductId(params);
            result.setData(weatherConditionsMap);
            logger.info("气候条件查询结果：{}",weatherConditionsMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("气候条件查询失败：{}",e);
        }
        return result;
    }

    /**
     * 气候条件信息修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST )
    public Result saveWeatherConditions(@RequestBody Map<String, Object> params){
        logger.debug("修改气候条件信息：{}",params);
        Result result = null;
        try {
            result = new Result();
            Map<String,Object> map = MapUtils.getMap(params,"data");
            map.put("productId", MapUtils.getString(params,"productId"));
            weatherConditionsService.saveWeatherConditions(map);
            logger.info("修改气候条件信息成功！");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("修改气候条件失败：{}",e);
        }
        return result;
    }
}
