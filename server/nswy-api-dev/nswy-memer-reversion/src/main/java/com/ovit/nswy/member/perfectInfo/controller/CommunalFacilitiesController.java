package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.CommunalFacilitiesService;
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
 * 公共设施
 */
@RestController
@RequestMapping("/communalFacilities")
public class CommunalFacilitiesController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private CommunalFacilitiesService communalFacilitiesService;




    /**
     *点击公共设施-交通设施及用户信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findTrafficOfUserInfo",method = RequestMethod.POST)
    public Result findTrafficOfUserInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = communalFacilitiesService.findTrafficOfUserInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-交通设施及用户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateTrafficOfUserInfoName",method = RequestMethod.POST)
    public Result updateTrafficOfUserInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.updateTrafficOfUserInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-交通设施及用户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTrafficOfUserInfo",method = RequestMethod.POST)
    public Result saveTrafficOfUserInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveTrafficOfUserInfo(params);

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
            communalFacilitiesService.saveTextPreview(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/saveTextPreviewTwo",method = RequestMethod.POST)
    public Result saveTextPreviewTwo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveTextPreviewTwo(params);

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
    @RequestMapping(value = "/deleteTrafficOfUserInfo",method = RequestMethod.POST)
    public Result deleteTrafficOfUserInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteTrafficOfUserInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



    /**
     *修改名称-交通信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateTrafficInfoName",method = RequestMethod.POST)
    public Result updateTrafficInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.updateTrafficInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-交通信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTrafficInfo",method = RequestMethod.POST)
    public Result saveTrafficInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveTrafficInfo(params);

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
    @RequestMapping(value = "/deleteTrafficInfo",method = RequestMethod.POST)
    public Result deleteTrafficInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteTrafficInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



    /**
     *点击公共设施-通信设施及用户信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findCommunicationFacilitiesInfo",method = RequestMethod.POST)
    public Result findCommunicationFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = communalFacilitiesService.findCommunicationFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-通信设施及用户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateCommunicationFacilitiesInfoName",method = RequestMethod.POST)
    public Result updateCommunicationFacilitiesInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.updateCommunicationFacilitiesInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-通信设施及用户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveCommunicationFacilitiesInfo",method = RequestMethod.POST)
    public Result saveCommunicationFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveCommunicationFacilitiesInfo(params);

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
    @RequestMapping(value = "/deleteCommunicationFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteCommunicationFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteCommunicationFacilitiesInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *点击公共设施-自来水设施及用户信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findTapWaternFacilitiesInfo",method = RequestMethod.POST)
    public Result findTapWaternFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = communalFacilitiesService.findTapWaternFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-自来水设施及用户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateTapWaternFacilitiesInfoName",method = RequestMethod.POST)
    public Result updateTapWaternFacilitiesInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.updateTapWaternFacilitiesInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-自来水设施及用户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTapWaternFacilitiesInfo",method = RequestMethod.POST)
    public Result saveTapWaternFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveTapWaternFacilitiesInfo(params);

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
    @RequestMapping(value = "/deleteTapWaternFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteTapWaternFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteTapWaternFacilitiesInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *点击公共设施-水利设施信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findWaterConservancyFacilitiesInfo",method = RequestMethod.POST)
    public Result findWaterConservancyFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = communalFacilitiesService.findWaterConservancyFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-水利设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateWaterConservancyFacilitiesInfoName",method = RequestMethod.POST)
    public Result updateWaterConservancyFacilitiesInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.updateWaterConservancyFacilitiesInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-水利设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveWaterConservancyFacilitiesInfo",method = RequestMethod.POST)
    public Result saveWaterConservancyFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveWaterConservancyFacilitiesInfo(params);

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
    @RequestMapping(value = "/deleteWaterConservancyFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteWaterConservancyFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteWaterConservancyFacilitiesInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *点击公共设施-垃圾处理设施信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findGarbageDisposaFacilitiesInfo",method = RequestMethod.POST)
    public Result findGarbageDisposaFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = communalFacilitiesService.findGarbageDisposaFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-垃圾处理设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateGarbageDisposaFacilitiesInfoName",method = RequestMethod.POST)
    public Result updateGarbageDisposaFacilitiesInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.updateGarbageDisposaFacilitiesInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-垃圾处理设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveGarbageDisposaFacilitiesInfo",method = RequestMethod.POST)
    public Result saveGarbageDisposaFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveGarbageDisposaFacilitiesInfo(params);

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
    @RequestMapping(value = "/deleteGarbageDisposaFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteGarbageDisposaFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteGarbageDisposaFacilitiesInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *点击公共设施-安保设施信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findSecurityFacilitiesInfo",method = RequestMethod.POST)
    public Result findSecurityFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = communalFacilitiesService.findSecurityFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-安保设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateSecurityFacilitiesInfoName",method = RequestMethod.POST)
    public Result updateSecurityFacilitiesInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.updateSecurityFacilitiesInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-安保设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveSecurityFacilitiesInfo",method = RequestMethod.POST)
    public Result saveSecurityFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveSecurityFacilitiesInfo(params);

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
    @RequestMapping(value = "/deleteSecurityFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteSecurityFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteSecurityFacilitiesInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *点击公共设施-教育设施信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findEducationFacilitiesInfo",method = RequestMethod.POST)
    public Result findEducationFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = communalFacilitiesService.findEducationFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-教育设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateEducationFacilitiesInfoName",method = RequestMethod.POST)
    public Result updateEducationFacilitiesInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.updateEducationFacilitiesInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-教育设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveEducationFacilitiesInfo",method = RequestMethod.POST)
    public Result saveEducationFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveEducationFacilitiesInfo(params);

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
    @RequestMapping(value = "/deleteEducationFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteEducationFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteEducationFacilitiesInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



    /**
     *点击公共设施-文化设施信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findCultureFacilitiesInfo",method = RequestMethod.POST)
    public Result findCultureFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = communalFacilitiesService.findCultureFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-文化设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateCultureFacilitiesInfoName",method = RequestMethod.POST)
    public Result updateCultureFacilitiesInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.updateCultureFacilitiesInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-文化设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveCultureFacilitiesInfo",method = RequestMethod.POST)
    public Result saveCultureFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveCultureFacilitiesInfo(params);

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
    @RequestMapping(value = "/deleteCultureFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteCultureFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteCultureFacilitiesInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



    /**
     *点击公共设施-体育设施信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findSportsFacilitiesInfo",method = RequestMethod.POST)
    public Result findSportsFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = communalFacilitiesService.findSportsFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-体育设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateSportsFacilitiesInfoName",method = RequestMethod.POST)
    public Result updateSportsFacilitiesInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.updateSportsFacilitiesInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-体育设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveSportsFacilitiesInfo",method = RequestMethod.POST)
    public Result saveSportsFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveSportsFacilitiesInfo(params);

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
    @RequestMapping(value = "/deleteSportsFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteSportsFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteSportsFacilitiesInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



    /**
     *点击公共设施-养老福利设施信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findOldAgeWelfareFacilitiesInfo",method = RequestMethod.POST)
    public Result findOldAgeWelfareFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = communalFacilitiesService.findOldAgeWelfareFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-养老福利设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateOldAgeWelfareFacilitiesInfoName",method = RequestMethod.POST)
    public Result updateOldAgeWelfareFacilitiesInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.updateOldAgeWelfareFacilitiesInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-养老福利设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOldAgeWelfareFacilitiesInfo",method = RequestMethod.POST)
    public Result saveOldAgeWelfareFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveOldAgeWelfareFacilitiesInfo(params);

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
    @RequestMapping(value = "/deleteOldAgeWelfareFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteOldAgeWelfareFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteOldAgeWelfareFacilitiesInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *点击公共设施-其它便民设施信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findOtherFacilitiesInfo",method = RequestMethod.POST)
    public Result findOtherFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = communalFacilitiesService.findOtherFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-其它便民设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateOtherFacilitiesInfoName",method = RequestMethod.POST)
    public Result updateOtherFacilitiesInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.updateOtherFacilitiesInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-其它便民设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOtherFacilitiesInfo",method = RequestMethod.POST)
    public Result saveOtherFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveOtherFacilitiesInfo(params);

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
    @RequestMapping(value = "/deleteOtherFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteOtherFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteOtherFacilitiesInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-物联设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveIotDeviceInfo",method = RequestMethod.POST)
    public Result saveIotDeviceInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.saveIotDeviceInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-物联设施信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * account
     * @param params
     * @return
     */
    @RequestMapping(value = "/findIotDeviceInfo",method = RequestMethod.POST)
    public Result findIotDeviceInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = communalFacilitiesService.findIotDeviceInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除物联设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteIotDeviceInfo",method = RequestMethod.POST)
    public Result deleteIotDeviceInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            communalFacilitiesService.deleteIotDeviceInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


}
