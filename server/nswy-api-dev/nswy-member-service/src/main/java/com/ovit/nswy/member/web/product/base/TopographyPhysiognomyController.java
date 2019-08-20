package com.ovit.nswy.member.web.product.base;

import com.ovit.nswy.member.service.TopographyPhysiognomyService;
import com.ovit.nswy.member.web.base.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * 地形地貌信息
 *
 * @author haoWen
 * @create 2017-12-25 16:03
 **/
@RestController
@RequestMapping("/product-topography-physiognomy")
public class TopographyPhysiognomyController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private TopographyPhysiognomyService topographyPhysiognomyService;

    private static final int ERROR_CODE = 500;

    /**
     * 地形地貌信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST )
    public Result queryTopographyPhysiognomy(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("查询地形地貌信息，参数：{}",params);
            result = new Result();
            Map<String,Object> topographyPhysiognomyMap = topographyPhysiognomyService.queryTopographyPhysiognomy(params);
            result.setData(topographyPhysiognomyMap);
            logger.info("查询地形地貌信息成功：{}",topographyPhysiognomyMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("生产基地地形地貌查询失败：{}",e);
        }
        return result;
    }

    /**
     * 地形地貌信息修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST )
    public Result updateTopographyPhysiognomy(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("地形地貌信息修改，参数：{}",params);
            result = new Result();
            topographyPhysiognomyService.saveTopographyPhysiognomy(params);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("地形地貌信息修改失败：{}",e);
        }
        return result;
    }
}
