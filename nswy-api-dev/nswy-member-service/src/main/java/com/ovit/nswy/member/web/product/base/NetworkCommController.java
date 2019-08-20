package com.ovit.nswy.member.web.product.base;

import com.ovit.nswy.member.service.NetworkCommService;
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
 * 网络通信条件
 *
 * @author haoWen
 * @create 2017-12-27 10:18
 **/
@RestController
@RequestMapping("/product-network-communications")
public class NetworkCommController {

    @Autowired
    private NetworkCommService networkCommService;

    //生产基地基本信息
    @Autowired
    private ProductionBaseService productionBaseService;

    private Logger logger = LogManager.getLogger(this.getClass());
    //全局的错误码
    private static final int ERROR_CODE = 500;

    /**
     * 修改网络通信条件
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST )
    public Result updateNetworkComm(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("修改网络通信条件，参数：{}",params);
            result = new Result();
            Map<String,Object> map = MapUtils.getMap(params,"data");
            map.put("productId",params.get("productId"));
            networkCommService.save(map);
            logger.info("修改网络通信条件成功！");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("修改网络通信条件失败：{}",e);
        }
        return result;
    }

    /**
     * 网络通信条件查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST )
    public Result queryNetworkComm(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("查询网络通信条件，参数：{}",params);
            result = new Result();
            Map<String,Object> networkMap = networkCommService.query(params);
            if (MapUtils.isNotEmpty(networkMap)){
                Map<String,Object> map = productionBaseService.queryByProductId(params);
                if (MapUtils.isNotEmpty(map)){
                    StringBuffer describe = new StringBuffer();
                    describe.append(MapUtils.getString(networkMap,"describe"));
                    networkMap.put("describe",describe);
                }
            }
            result.setData(networkMap);
            logger.info("查询网络通信条件成功：{}",networkMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("查询网络通信条件失败：{}",e);
        }
        return result;
    }

}
