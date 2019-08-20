package com.ovit.nswy.member.web;


import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private MemberService memberService;

    /**
     * 根据名字查找
     * @param page
     * @return
     */
    @RequestMapping(value = "/find/{page}", method = RequestMethod.POST)
    public Result findByName(@PathVariable String page,@RequestBody Map<String,Object>  map) {
        Result result = new Result();
        try{
            logger.info("查询第{}页，参数：{}",page,map);
            String service = MapUtils.getString(map,"service");
            String product = MapUtils.getString(map,"product");
            String industry = MapUtils.getString(map,"industry");
            String species = MapUtils.getString(map,"species");
            String address = MapUtils.getString(map,"address");
            String memberType = MapUtils.getString(map,"memberType");
            String expertType = MapUtils.getString(map,"expertType");
            String adeptField = MapUtils.getString(map,"adeptField");


            if (StringUtils.isNotEmpty(address)) {
                map.put("address",address);
            }
            if (StringUtils.isNotEmpty(memberType)) {
                memberType = memberType.replaceAll("/",";");
                map.put("memberType",memberType);
            }
            if (StringUtils.isNotEmpty(adeptField)) {
                map.put("adeptField",adeptField);
            }
            if (StringUtils.isNotEmpty(expertType)) {
                String[] expertTypeArray = expertType.split(" ");
                map.put("expertTypeArray",expertTypeArray);
            }
            if (StringUtils.isNotEmpty(service)) {
                String[] serviceNameArray = service.split(" ");
                map.put("serviceNameArray",serviceNameArray);
            }
            if (StringUtils.isNotEmpty(product)) {
                String[] productArray = product.split(" ");
                map.put("productArray",productArray);
            }
            if (StringUtils.isNotEmpty(industry)) {
                String[] industryArray = industry.split(" ");
                map.put("industryArray",industryArray);
            }
            if (StringUtils.isNotEmpty(species)) {
                String[] speciesArray = species.split(" ");
                map.put("speciesArray",speciesArray);
            }

            map.put("pageNum",page);
            map.put("pageSize",12);
            PageInfo<Map<String, Object>> list = memberService.selectByName(map);
            result.setData(list);
            logger.info("查询成功 {} ",list.getList());
        }catch (Exception e){
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            logger.error("查询失败 {} 原因" ,e);
        }
        return result;
    }

    /**
     * 查询会员类别
     * @param params
     * @return
     */
    @RequestMapping(value = "/getMemberType",method = RequestMethod.POST)
    public Result getMemberType(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> list = memberService.findMemberType(params);
            result.setData(list);
            result.setMsg("查询成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



}
