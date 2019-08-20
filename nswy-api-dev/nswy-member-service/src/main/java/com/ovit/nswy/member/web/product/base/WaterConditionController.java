package com.ovit.nswy.member.web.product.base;

import com.ovit.nswy.member.service.WaterConditionService;
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
 * 水利条件
 *
 * @author haoWen
 * @create 2017-12-27 11:15
 **/
@RestController
@RequestMapping("/product-water-condition")
public class WaterConditionController {
    private Logger logger = LogManager.getLogger(this.getClass());
    //全局的错误码
    private static final int ERROR_CODE = 500;

    @Autowired
    private WaterConditionService waterConditionService;

    /**
     * 水利条件修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST )
    public Result saveWaterCondition(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("水利条件修改，参数：{}",params);
            result = new Result();
            Map<String, Object> map = MapUtils.getMap(params,"data");
            map.put("productId",params.get("productId"));
            waterConditionService.save(map);
            logger.info("水利条件修改成功！");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("水利条件修改失败：{}",e);
        }
        return result;
    }


    /**
     * 水利条件查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST )
    public Result queryWaterCondition(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("查询水利条件，参数：{}",params);
            result = new Result();
            Map<String, Object> waterConditionMap = waterConditionService.query(params);
            result.setData(waterConditionMap);
            logger.info("水利条件查询成功：{}",waterConditionMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("水利条件查询失败：{}",e);
        }
        return result;
    }
}
