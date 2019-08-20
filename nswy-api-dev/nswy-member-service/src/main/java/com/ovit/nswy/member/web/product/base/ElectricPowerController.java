package com.ovit.nswy.member.web.product.base;

import com.ovit.nswy.member.service.ElectricPowerService;
import com.ovit.nswy.member.service.ProductionBaseService;
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
 * 电力条件
 *
 * @author haoWen
 * @create 2017-12-27 10:55
 **/
@RestController
@RequestMapping("/product-electric-power/")
public class ElectricPowerController {
    private Logger logger = LogManager.getLogger(this.getClass());
    //全局的错误码
    private static final int ERROR_CODE = 500;

    @Autowired
    private ElectricPowerService electricPowerService;

    //生产基地基本信息
    @Autowired
    private ProductionBaseService productionBaseService;

    /**
     * 电力条件查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST )
    public Result queryElectricPower(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("电力条件查询，参数：{}",params);
            result = new Result();
            Map<String, Object> electricPowerMap = electricPowerService.query(params);
            if (MapUtils.isNotEmpty(electricPowerMap)) {
                Map<String, Object> map = productionBaseService.queryByProductId(params);
                if (MapUtils.isNotEmpty(map)) {
                    StringBuffer describe = new StringBuffer(MapUtils.getString(map,"baseName"));
                    describe.append(MapUtils.getString(electricPowerMap,"describe"));
                    electricPowerMap.put("describe",describe);
                }
            }
            result.setData(electricPowerMap);
            logger.info("电力条件查询成功：{}",electricPowerMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("电力条件查询失败：{}",e);
        }
        return result;
    }

    /**
     * 电力条件新增/修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST )
    public Result saveElectricPower(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("电力条件修改，参数：{}",params);
            result = new Result();
            Map<String, Object> map = MapUtils.getMap(params,"data");
            map.put("productId",params.get("productId"));
            electricPowerService.save(map);
            logger.info("电力条件修改成功！");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("电力条件修改失败：{}",e);
        }
        return result;
    }
}
