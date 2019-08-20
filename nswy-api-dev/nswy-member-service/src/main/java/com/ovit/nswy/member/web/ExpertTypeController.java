package com.ovit.nswy.member.web;

import com.ovit.nswy.member.service.ExpertTypeService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/expertType")
public class ExpertTypeController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ExpertTypeService expertTypeService;

    /**
     * 查询专家字典表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/findExpertType", method = RequestMethod.POST)
    public Result findExpertType(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = expertTypeService.findExpertType(params);
            result.setData(list);

        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("专家分类查询失败");
        }
        return result;
    }


}
