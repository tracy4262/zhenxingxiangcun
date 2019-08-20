package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.github.xphsc.json.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.service.FarmFamilyDetailService;
import com.ovit.nswy.member.service.PerfectInformationService;
import com.ovit.nswy.member.util.JsonToStringUtils;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/farmFamilyDetail")
public class FarmFamilyDeatilController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private FarmFamilyDetailService farmFamilyDetailService;

    @Autowired
    private PerfectInformationService perfectInformationService;


    /**
     * 修改详情
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateFarmFamilyDetail", method = RequestMethod.POST)
    public Result updateFarmFamilyDetail(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{

            //家庭成员
            farmFamilyDetailService.insertFamilyMember(params);
            //产出产品
            farmFamilyDetailService.insertOutputProduct(params);
            //提供服务
            farmFamilyDetailService.insertProvideServices(params);
            //求购意向
            farmFamilyDetailService.insertPurchaseIntention(params);

            farmFamilyDetailService.insertHouseSituation(params);

            farmFamilyDetailService.insertFamilySituation(params);

            //设施资产
            perfectInformationService.insertFacilityAssets(params);

            //无形资产
            perfectInformationService.insertIntangibleAssets(params);

            //经营场所
            perfectInformationService.insertPlaceOfBusiness(params);


        }catch (Exception e){
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("种养户详情修改 失败：{}",e);
            return result;
        }

        return result;
    }

    /**
     *查询种养户详情
     * @param params
     * @return
     */
    @RequestMapping(value = "/findFarmFamilyDetail", method = RequestMethod.POST)
    public Result findFarmFamilyDetail(@RequestBody Map<String,Object> params){
        Result result = new Result();
        Map<String,Object> perfectInformation = new HashMap<>();
        //家庭成员
        List<Map<String,Object>> family_meber  = farmFamilyDetailService.findFamilyMember(params);

        perfectInformation.put("family_Member",family_meber);

        //产出产品
        List<Map<String,Object>>   output_product= farmFamilyDetailService.findOutputProduct(params);

        perfectInformation.put("output_product",output_product);

        //提供服务
        List<Map<String,Object>> provide_services  = farmFamilyDetailService.findProvideServices(params);

        perfectInformation.put("provide_services",provide_services);

        //求购意向
        List<Map<String,Object>> purchase_intention  = farmFamilyDetailService.findPurchaseIntention(params);

        perfectInformation.put("purchase_intention",purchase_intention);


        //房屋生活情况
        List<Map<String,Object>> house_situation  = farmFamilyDetailService.findHouseSituation(params);
        perfectInformation.put("house_situation",house_situation);

        // 家庭现代化情况
        List<Map<String,Object>> family_situation  = farmFamilyDetailService.findFamilySituation(params);
        perfectInformation.put("family_situation",family_situation);


        //设施资产
        List<Map<String, Object>> facilityAssets = perfectInformationService.findFacilityAssets(params);
        perfectInformation.put("facilityAssets", facilityAssets);

        //无形资产
        List<Map<String, Object>> intangibleAssets = perfectInformationService.findIntangibleAssets(params);
        perfectInformation.put("intangibleAssets", intangibleAssets);

        //经营场所
        List<Map<String, Object>> placeOfBusiness = perfectInformationService.findPlaceOfBusiness(params);
        perfectInformation.put("placeOfBusiness", placeOfBusiness);

        result.setData(perfectInformation);
        return result;
    }



}
