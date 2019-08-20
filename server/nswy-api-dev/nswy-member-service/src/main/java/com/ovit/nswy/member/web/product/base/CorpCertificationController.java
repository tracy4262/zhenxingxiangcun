package com.ovit.nswy.member.web.product.base;

import com.alibaba.fastjson.JSON;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/corpCertification")
public class CorpCertificationController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private CorpCertificationService corpCertificationService;

    @Autowired
    private LoginUserService loginUserService;


    /**
     * 保存企业完善信息步骤，存在则更新，
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveOrUpdatePerfectInfo", method = RequestMethod.POST)
    public Result saveOrUpdatePerfectInfo(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String perfectInfo = JSON.toJSONString(params.get("data"));
            logger.info("企业完善信息转为json：{}", perfectInfo);
            String account = loginUser.getLoginAccount();

            Map<String,Object> perfectMap=new HashMap<>();
            perfectMap.put("account",account);
            perfectMap.put("detail_info",perfectInfo);

            Map<String,Object> historyMap = corpCertificationService.findCertificationByAccount(account);
            if (MapUtils.isEmpty(historyMap)) {
                //无历史数据，则新增数据
                corpCertificationService.saveCertification(perfectMap);

            } else {
                //有历史数据，则更新数据
                corpCertificationService.updateCertification(perfectMap);
            }

            //保存认证步骤到登陆表中
            loginUserService.updateIdentityFlag(account, "5");
            logger.info("企业完善信息保存成功");

        } catch (Exception e) {
            logger.error("企业完善信息保存失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存企业完善信息失败");
        }
        return result;
    }

    /**
     * 根据账户名account查询企业完善信息
     */
    @RequestMapping(value = "/findCorpPerfectInfo", method = RequestMethod.GET)
    public Result findCorpPerfectInfo(HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            Map<String,Object> perfectInfo = corpCertificationService.findCertificationByAccount(account);
            logger.info("根据账户名:{},查询到的企业完善信息为：{}",account, perfectInfo);
            result.setCode(ResultCode.SUCCESS);
            if (MapUtils.isNotEmpty(perfectInfo)) {
                result.setData(perfectInfo);
            } else {
                result.setMsg("暂无数据");
            }

        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询企业完善信息失败");
        }
        return result;
    }
}
