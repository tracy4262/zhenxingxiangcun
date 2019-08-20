package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.util.ServiceUtils;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/expertInfo")
public class ExpertInfoController {
    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private ExpertInfoService expertInfoService;
    @Autowired
    private VideoImgService videoImgService;
    @Autowired
    private SpecieService specieService;

    @Autowired
    private SystemDictService systemDictService;


    @RequestMapping(value = "/saveExpert", method = RequestMethod.POST)
    public Result saveCorp(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        try {
            logger.info("专业信息更新，参数：{}",params);
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            if (null == loginUser) {
                result.setCode(ResultCode.FAIL);
                result.setMsg("登录超时，请重新登录");
                logger.error("登录超时，请重新登录");
            } else {
                expertInfoService.saveOrUpdate(loginUser,params);
            }
            result.setData(1);
        } catch (Exception e) {
            logger.error("保存专家信息失败：{}",e);
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



    @RequestMapping(value = "/getApproveStatus", method = RequestMethod.GET)
    public Result getApproveStatus(HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        String loginAccount = loginUser.getLoginAccount();
        Result result = new Result();
        Integer status = expertInfoService.getApproveStatus(loginAccount);
        Map<String, Integer> map = new HashMap<>();
        map.put("approveStatus", status);
        result.setData(map);
        return result;
    }

    /**
     * 按照用户名查询专家信息
     *
     * @return
     */
    @RequestMapping(value = "/getExpertInfo/{account}", method = RequestMethod.GET)
    public Result getExpertInfo(@PathVariable("account") String account ) {
        Result result = new Result();
        try {
            ExpertInfo expertInfo = expertInfoService.getExpertInfo(account);
            String speciesId=expertInfo.getRelatedSpecies();
            String adeptId=expertInfo.getAdeptSpecies();
            expertInfo.setAdeptId(adeptId);
            expertInfo.setSpeciesId(speciesId);
            expertInfo.setServiceId(expertInfo.getRelatedService());
            expertInfo.setIndustryId(expertInfo.getRelatedIndustry());
            expertInfo.setProductId(expertInfo.getRelatedProduct());
            String relatedSpecies=specieService.getSpecies(expertInfo.getRelatedSpecies());
            String adeptSpecies=specieService.getSpecies(expertInfo.getAdeptSpecies());

            //相关行业
            String relativeIns=null;
            String relatedIndustryId = expertInfo.getRelatedIndustry();
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(relatedIndustryId)) {
                String [] relatedIndustryIdArray = org.apache.commons.lang3.StringUtils.splitByWholeSeparator(relatedIndustryId,"");
                List<Map<String,Object>> relatedIndustryName = systemDictService.queryByDictValue(Arrays.asList(relatedIndustryIdArray));
                StringBuffer name = new StringBuffer();
                for (Map<String,Object> map : relatedIndustryName) {
                    name.append(MapUtils.getString(map,"dictName")).append(" ");
                }

                relativeIns = name.toString().trim();
            }
            //相关服务
            String relativeService= ServiceUtils.getServiceNameByIdArray(expertInfo.getRelatedService());
            expertInfo.setRelatedSpecies(relatedSpecies);
            expertInfo.setRelatedIndustry(relativeIns);
            expertInfo.setRelatedService(relativeService);
            expertInfo.setAdeptSpecies(adeptSpecies);
            //获得个人、资质、荣誉数据
            VideoImg videoImgPic=new VideoImg();
            videoImgPic.setColumn("个人");
            videoImgPic.setAccount(account);
            VideoImg videoImgHonner=new VideoImg();
            videoImgHonner.setColumn("荣誉");
            videoImgHonner.setAccount(account);
            VideoImg videoImgCer=new VideoImg();
            videoImgCer.setColumn("资质");
            videoImgCer.setAccount(account);
            List<String> listPic=videoImgService.selectByAccount(videoImgPic);
            List<String> listHonner=videoImgService.selectByAccount(videoImgHonner);
            List<String> listCer=videoImgService.selectByAccount(videoImgCer);
            expertInfo.setListCertificate(listCer);
            expertInfo.setListPic(listPic);
            expertInfo.setListHonor(listHonner);
            result.setData(expertInfo);
            result.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            return result;
        }
        return result;
    }


    /**
     *无忧首页专家组合条件查询并分页
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findExpertTitle/{page}", method = RequestMethod.POST)
    public Result findExpertTitle(@PathVariable String page,@RequestBody Map<String,Object>  params) {
        Result result = new Result();
        try{
            logger.info("查询专家：{}",params);
            //将多选的查询条件转换为数组
            String serviceName = MapUtils.getString(params,"servicename");
            String goodName = MapUtils.getString(params,"goodname");
            String industry = MapUtils.getString(params,"industry");
            String species = MapUtils.getString(params,"species");
            String adeptField = MapUtils.getString(params,"adeptField");
            if (StringUtils.isNotEmpty(serviceName)) {
                String[] serviceNameArray = serviceName.split(" ");
                params.put("serviceNameArray", serviceNameArray);//相关服务
            }
            if (StringUtils.isNotEmpty(goodName)) {
                String[] goodNameArray = goodName.split(" ");
                params.put("goodNameArray",goodNameArray);//相关产品
            }
            if (StringUtils.isNotEmpty(industry)) {
                String[] industryArray = industry.split(" ");
                params.put("industryArray",industryArray);//相关行业
            }
            if (StringUtils.isNotEmpty(species)) {
                String[] speciesArray = species.split(" ");
                params.put("speciesArray",speciesArray);//相关物种
            }
            if (StringUtils.isNotEmpty(adeptField)) {
                String[] adeptFieldArray = adeptField.split(" ");
                params.put("adeptFieldArray",adeptFieldArray);//擅长领域
            }
            params.put("pageNum",page);
            params.put("pageSize",12);
            PageInfo<Map<String, Object>> list = expertInfoService.findExpertTitle(params);

            List<Map<String,Object>> pageList = list.getList();
            if (CollectionUtils.isNotEmpty(pageList)) {
                for (Map<String, Object> pageMap : pageList) {
                    pageMap.put("adeptSpecies",specieService.getSpecies(MapUtils.getString(pageMap,"adeptSpecies")));
                }
            }
            result.setData(list);
            logger.info("专家查询成功：{} ",list.getList());
        }catch (Exception e){
            result.setCode(ResultCode.FAIL);
            logger.error("专家查询失败：{}",e);
        }
        return result;
    }


    /**
     * 更新专家信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateExpertInfo", method = RequestMethod.POST)
    public Result updateExpertInfo(HttpServletRequest request, @RequestBody ExpertInfo expertInfo) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            if (expertInfo != null) {
                expertInfo.setExpertName(loginUser.getRealName());
                expertInfoService.updateByPrimaryKeySelective(expertInfo);
                result.setData("OK");
                result.setCode(ResultCode.SUCCESS);
            } else {
                result.setCode(ResultCode.FAIL);
                result.setMsg("传递参数为空");
            }
        } catch (Exception e) {
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            return result;
        }
        return result;
    }
}