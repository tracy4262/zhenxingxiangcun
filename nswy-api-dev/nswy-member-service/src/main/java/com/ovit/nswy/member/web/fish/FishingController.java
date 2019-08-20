package com.ovit.nswy.member.web.fish;

import com.ovit.nswy.member.mapper.FishingServiceMapper;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.service.FishingServicesService;
import com.ovit.nswy.member.service.SpeciesOfFishService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.service.FishingProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/fishing")
public class FishingController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private SpeciesOfFishService speciesOfFishService;

    @Autowired
    private FishingProductService fishingProductService;

    @Autowired
    private FishingServicesService fishingServicesService;

    @RequestMapping(value = "/getSpeciesInfo", method = RequestMethod.POST)
    public Result getSpeciesInfo(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {
            List<Map<String, Object>> speciesInfo = speciesOfFishService.getSpeciesOfFish(params);
            result.setData(speciesInfo);
            result.setMsg("查询物种信息成功");
            //type：fish表示垂钓，pick表示采摘
            logger.debug("根据用户名：{},查询到对应{}, 物种信息为：{}", MapUtils.getString(params,"account"), MapUtils.getString(params,"type"), speciesInfo);

        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询物种信息失败");
        }
        return result;
    }

    @RequestMapping(value = "/insertSpeciesInfo", method = RequestMethod.POST)
    public Result insertSpeciesInfo(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {
            String account = MapUtils.getString(params,"account");
            String type = MapUtils.getString(params,"type");
            List<Map<String, Object>> speciesList = (List<Map<String, Object>>) params.get("speciesInfo");
            //获取历史数据，便于去重
            List<Map<String, Object>> historyList = speciesOfFishService.getSpeciesOfFish(params);
            List<Map<String, Object>> saveList = new ArrayList<>();
            //已存在的物种个数
            int existSize = 0;
            StringBuilder sb = new StringBuilder();
            if (historyList == null || historyList.size() == 0) {
                for (Map<String, Object> species : speciesList) {
                    species.put("account", account);
                    species.put("type", type);
                    saveList.add(species);
                }
            } else {
                for (Map<String, Object> species : speciesList) {
                    String speciesId = MapUtils.getString(species, "speciesId");
                    boolean isExists = false;
                    for (Map<String, Object> history : historyList) {
                        if (speciesId.equals(MapUtils.getString(history, "species_id"))){
                            isExists = true;
                            break;
                        }
                    }
                    if (!isExists) {
                        species.put("account", account);
                        species.put("type", type);
                        saveList.add(species);
                    } else {
                        existSize++;
                        sb.append(MapUtils.getString(species, "speciesName")).append(",");
                        logger.debug("该物种已存在，不重复添加！");
                    }
                }
            }
            if (saveList.size() > 0) {
                speciesOfFishService.insertSpeciesOfFish(saveList);
            }
            //前端传过来的添加物种的个数
            int speciesSize = speciesList.size();
            if (speciesSize == existSize) {
                //前端传过来的所有物种已存在
                result.setCode(201);
                result.setMsg("所有物种都已存在");
            } else if (speciesSize > existSize && existSize > 0) {
                //部分物种已存在
                result.setCode(202);
                String existSpeciesName = sb.toString().substring(0, sb.toString().length() - 1);
                result.setMsg("部分物种已存在,包括：" + existSpeciesName);
            } else if (existSize == 0) {
                //无重复物种
                result.setMsg("所有物种添加成功");
            }
            logger.debug("根据用户名：{},插入到对应{},物种信息为：{}", account, type, params);

        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("插入物种信息失败");
        }
        return result;
    }

    @RequestMapping(value = "/deleteSpeciesInfo", method = RequestMethod.POST)
    public Result deleteSpeciesInfo(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {

            List<Map<String, Object>> productInfo = fishingProductService.findProductInfoBySpeciesId(params);
            if (productInfo == null || productInfo.size() == 0) {
                speciesOfFishService.deleteSpeciesOfFish(params);
                result.setMsg("删除物种信息成功");
                logger.debug("根据用户名：{},删除到对应{},物种信息为：{}", MapUtils.getString(params,"account"), MapUtils.getString(params,"type"), params);

            } else {
                result.setCode(ResultCode.FAIL);
                result.setMsg("有产品关联此物种，无法删除！");
            }

        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("删除物种信息失败");
        }
        return result;
    }

    @RequestMapping(value = "/findFishingProduct", method = RequestMethod.POST)
    public Result findFishingProduct(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
          PageInfo<Map<String,Object>> pageInfo =  fishingProductService.findFishingProduct(params);
          result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/insertFishingProduct", method = RequestMethod.POST)
    public Result insertFishingProduct(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingProductService.insertFishingProduct(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updateFishingProduct", method = RequestMethod.POST)
    public Result updateFishingProduct(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingProductService.updateFishingProduct(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteFishingProduct", method = RequestMethod.POST)
    public Result deleteFishingProduct(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingProductService.deleteFishingProduct(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/findFishingService", method = RequestMethod.POST)
    public Result findFishingService(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> pageInfo =  fishingServicesService.findFishingService(params);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/insertFishingService", method = RequestMethod.POST)
    public Result insertFishingService(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String id  = fishingServicesService.insertFishingService(params);
            result.setData(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updateFishingService", method = RequestMethod.POST)
    public Result updateFishingService(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingServicesService.updateFishingService(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteFishingService", method = RequestMethod.POST)
    public Result deleteFishingService(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingServicesService.deleteFishingService(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 第三步，景区
     * @param params
     * @return
     */
    @RequestMapping(value = "/findProductService", method = RequestMethod.POST)
    public Result findProductService(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list =  fishingServicesService.findProductService(params);
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
    public Result saveProduct(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingServicesService.saveProduct(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteProductManagementService", method = RequestMethod.POST)
    public Result deleteProductManagementService(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingServicesService.deleteProductManagementService(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/findProductServiceList", method = RequestMethod.POST)
    public Result findProductServiceList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageList pageInfo  = fishingServicesService.findProductServiceList(params);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询已关联，未关联
     * @param params
     * @return
     */
    @RequestMapping(value = "/findJoinServiceList", method = RequestMethod.POST)
    public Result findJoinServiceList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageList pageInfo  = fishingServicesService.findJoinServiceList(params);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/saveJoinServiceInfo", method = RequestMethod.POST)
    public Result saveJoinServiceInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingServicesService.saveJoinServiceInfo(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/deleteJoinServiceInfo", method = RequestMethod.POST)
    public Result deleteJoinServiceInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingServicesService.deleteJoinServiceInfo(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    //查询评论
    @RequestMapping(value = "/findProductServiceById", method = RequestMethod.POST)
    public Result findProductServiceById(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> map  = fishingServicesService.findProductServiceById(params);
            result.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/saveComment", method = RequestMethod.POST)
    public Result saveComment(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingServicesService.saveComment(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    //查询订单
    @RequestMapping(value = "/findOrderList", method = RequestMethod.POST)
    public Result findOrderList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> map  = fishingServicesService.findOrderList(params);
            result.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    public Result saveOrder(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingServicesService.saveOrder(params);
        } catch (Exception e) {
            logger.error("保存服务订单异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updateOrderStatus", method = RequestMethod.POST)
    public Result updateOrderStatus(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingServicesService.updateOrderStatus(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/updateOrderRefund", method = RequestMethod.POST)
    public Result updateOrderRefund(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            fishingServicesService.updateOrderRefund(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/findAvatar", method = RequestMethod.POST)
    public Result findAvatar(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            Map<String,Object> map  = fishingServicesService.findAvatar(params);
            result.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
