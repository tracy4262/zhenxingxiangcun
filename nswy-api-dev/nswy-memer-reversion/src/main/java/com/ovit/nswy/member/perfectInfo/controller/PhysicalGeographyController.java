package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.PhysicalGeographyService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
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
 * 自然地理
 */
@RestController
@RequestMapping("/physicalGeography")
public class PhysicalGeographyController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PhysicalGeographyService physicalGeographyService;



    /**
     *点击地理位置信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findGeographicalPosition",method = RequestMethod.POST)
    public Result findGeographicalPosition(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = physicalGeographyService.findGeographicalPosition(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-会员所在地
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateMemberLocationName",method = RequestMethod.POST)
    public Result updateMemberLocationName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.updateMemberLocationName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-会员所在地经纬度
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateMemberLatitudeLongitudeName",method = RequestMethod.POST)
    public Result updateMemberLatitudeLongitudeName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.updateMemberLatitudeLongitudeName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-会员四邻
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateMemberNeighborName",method = RequestMethod.POST)
    public Result updateMemberNeighborName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.updateMemberNeighborName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-查看实况地址
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateMemberLiveAddressName",method = RequestMethod.POST)
    public Result updateMemberLiveAddressName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.updateMemberLiveAddressName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *插入/更新-地理位置信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveGeographicalPosition",method = RequestMethod.POST)
    public Result saveGeographicalPosition(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.saveGeographicalPosition(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/deleteMemberNeighbor",method = RequestMethod.POST)
    public Result deleteMemberNeighbor(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.deleteMemberNeighbor(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/deleteMemberLiveAddress",method = RequestMethod.POST)
    public Result deleteMemberLiveAddress(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.deleteMemberLiveAddress(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *点击国土面积信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findLandAreaInfo",method = RequestMethod.POST)
    public Result findLandAreaInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = physicalGeographyService.findLandAreaInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-国土面积信息
     * @param params
     * @return
             */
    @RequestMapping(value = "/updateLandAreaInfoName",method = RequestMethod.POST)
    public Result updateLandAreaInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.updateLandAreaInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-国土面积信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveLandAreaInfo",method = RequestMethod.POST)
    public Result saveLandAreaInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.saveLandAreaInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/deleteLandAreaInfo",method = RequestMethod.POST)
    public Result deleteLandAreaInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.deleteLandAreaInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *点击地形地貌信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findTopographicFeature",method = RequestMethod.POST)
    public Result findTopographicFeature(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = physicalGeographyService.findTopographicFeature(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-地形地貌信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateTopographicFeatureName",method = RequestMethod.POST)
    public Result updateTopographicFeatureName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.updateTopographicFeatureName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-地形地貌信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTopographicFeature",method = RequestMethod.POST)
    public Result saveTopographicFeature(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.saveTopographicFeature(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *点击气候信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findClimateInfo",method = RequestMethod.POST)
    public Result findClimateInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = physicalGeographyService.findClimateInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-气候信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateClimateInfoName",method = RequestMethod.POST)
    public Result updateClimateInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.updateClimateInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-气候信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveClimateInfo",method = RequestMethod.POST)
    public Result saveClimateInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.saveClimateInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *点击生物资源信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findBiologicalResources",method = RequestMethod.POST)
    public Result findBiologicalResources(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = physicalGeographyService.findBiologicalResources(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-生物资源信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateBiologicalResourcesName",method = RequestMethod.POST)
    public Result updateBiologicalResourcesName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.updateBiologicalResourcesName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-生物资源信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveBiologicalResources",method = RequestMethod.POST)
    public Result saveBiologicalResources(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.saveBiologicalResources(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/deleteBiologicalResources",method = RequestMethod.POST)
    public Result deleteBiologicalResources(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.deleteBiologicalResources(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *点击地质矿产信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findMineralsInfo",method = RequestMethod.POST)
    public Result findMineralsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = physicalGeographyService.findMineralsInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-地质矿产信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateMineralsInfoName",method = RequestMethod.POST)
    public Result updateMineralsInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.updateMineralsInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-地质矿产信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveMineralsInfo",method = RequestMethod.POST)
    public Result saveMineralsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            physicalGeographyService.saveMineralsInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/findSysMineralsDictParent",method = RequestMethod.POST)
    public Result findSysMineralsDictParent(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> list = physicalGeographyService.findSysMineralsDictParent(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    @RequestMapping(value = "/findSysMineralsDict",method = RequestMethod.POST)
    public Result findSysMineralsDict(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> list = physicalGeographyService.findSysMineralsDict(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
