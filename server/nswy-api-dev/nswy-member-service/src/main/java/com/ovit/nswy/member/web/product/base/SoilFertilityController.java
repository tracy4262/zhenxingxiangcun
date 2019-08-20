package com.ovit.nswy.member.web.product.base;

import com.ovit.nswy.member.service.SoilFertilityService;
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
 * 土壤肥力
 *
 * @author haoWen
 * @create 2017-12-27 11:32
 **/
@RestController
@RequestMapping("/product-soil-fertility")
public class SoilFertilityController {
    private Logger logger = LogManager.getLogger(this.getClass());
    //全局的错误码
    private static final int ERROR_CODE = 500;

    @Autowired
    private SoilFertilityService soilFertilityService;
    /**
     * 土壤肥力查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST )
    public Result querySoilFertility(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("土壤肥力查询，参数：{}",params);
            result = new Result();
            Map<String, Object> soilFertilityMap =  soilFertilityService.query(params);
            result.setData(soilFertilityMap);
            logger.info("土壤肥力查询成功：{}",soilFertilityMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("土壤肥力查询失败：{}",e);
        }
        return result;
    }


    /**
     * 土壤肥力修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST )
    public Result saveSoilFertility(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("土壤肥力修改，参数：{}",params);
            result = new Result();
            Map<String,Object> map = MapUtils.getMap(params,"data");
            map.put("productId", MapUtils.getString(params,"productId"));
            soilFertilityService.save(map);
            logger.info("土壤肥力修改成功！");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("土壤肥力修改失败：{}",e);
        }
        return result;
    }

}
