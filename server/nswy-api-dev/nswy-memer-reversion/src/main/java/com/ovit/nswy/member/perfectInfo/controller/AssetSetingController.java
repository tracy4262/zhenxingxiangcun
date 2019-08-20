package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.AssetSetingService;
import com.ovit.nswy.member.perfectInfo.service.EducationLiveService;
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
 * 资产设置
 */
@RestController
@RequestMapping("/assetSeting")
public class AssetSetingController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private AssetSetingService assetSetingService;




    /**
     *点击资产设置-土地使用权限信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findLandUseRightInfo",method = RequestMethod.POST)
    public Result findLandUseRightInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = assetSetingService.findLandUseRightInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-土地使用权限信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateLandUseRightInfoName",method = RequestMethod.POST)
    public Result updateLandUseRightInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.updateLandUseRightInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-土地使用权限信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveLandUseRightInfo",method = RequestMethod.POST)
    public Result saveLandUseRightInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.saveLandUseRightInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-文字预览
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTextPreview",method = RequestMethod.POST)
    public Result saveTextPreview(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.saveTextPreview(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteLandUseRightInfo",method = RequestMethod.POST)
    public Result deleteLandUseRightInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.deleteLandUseRightInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }





    /**
     *点击资产设置-房屋使用权信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findRightToUseHousingInfo",method = RequestMethod.POST)
    public Result findRightToUseHousingInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = assetSetingService.findRightToUseHousingInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-房屋使用权信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateRightToUseHousingInfoName",method = RequestMethod.POST)
    public Result updateRightToUseHousingInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.updateRightToUseHousingInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-房屋使用权信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveRightToUseHousingInfo",method = RequestMethod.POST)
    public Result saveRightToUseHousingInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.saveRightToUseHousingInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteRightToUseHousingInfo",method = RequestMethod.POST)
    public Result deleteRightToUseHousingInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.deleteRightToUseHousingInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *点击资产设置-生产类机械设备信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findProductionMachineInfo",method = RequestMethod.POST)
    public Result findProductionMachineInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = assetSetingService.findProductionMachineInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-生产类机械设备信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateProductionMachineInfoName",method = RequestMethod.POST)
    public Result updateProductionMachineInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.updateProductionMachineInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-生产类机械设备信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveProductionMachineInfo",method = RequestMethod.POST)
    public Result saveProductionMachineInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.saveProductionMachineInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteProductionMachineInfo",method = RequestMethod.POST)
    public Result deleteProductionMachineInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.deleteProductionMachineInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *点击资产设置-交通运输类工具信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findTransportationToolsInfo",method = RequestMethod.POST)
    public Result findTransportationToolsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = assetSetingService.findTransportationToolsInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-交通运输类工具信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateTransportationToolsInfoName",method = RequestMethod.POST)
    public Result updateTransportationToolsInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.updateTransportationToolsInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-交通运输类工具信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTransportationToolsInfo",method = RequestMethod.POST)
    public Result saveTransportationToolsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.saveTransportationToolsInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteTransportationToolsInfo",method = RequestMethod.POST)
    public Result deleteTransportationToolsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.deleteTransportationToolsInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *点击资产设置-家用资产信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findHouseholdAssetsInfo",method = RequestMethod.POST)
    public Result findHouseholdAssetsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = assetSetingService.findHouseholdAssetsInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-家用资产信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateHouseholdAssetsInfoName",method = RequestMethod.POST)
    public Result updateHouseholdAssetsInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.updateHouseholdAssetsInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-家用资产信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveHouseholdAssetsInfo",method = RequestMethod.POST)
    public Result saveHouseholdAssetsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.saveHouseholdAssetsInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteHouseholdAssetsInfo",method = RequestMethod.POST)
    public Result deleteHouseholdAssetsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.deleteHouseholdAssetsInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *点击资产设置-设施农业信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findFacilityAgricultureInfo",method = RequestMethod.POST)
    public Result findFacilityAgricultureInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = assetSetingService.findFacilityAgricultureInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-设施农业信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateFacilityAgricultureInfoName",method = RequestMethod.POST)
    public Result updateFacilityAgricultureInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.updateFacilityAgricultureInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-设施农业信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveFacilityAgricultureInfo",method = RequestMethod.POST)
    public Result saveFacilityAgricultureInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.saveFacilityAgricultureInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteFacilityAgricultureInfo",method = RequestMethod.POST)
    public Result deleteFacilityAgricultureInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            assetSetingService.deleteFacilityAgricultureInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
