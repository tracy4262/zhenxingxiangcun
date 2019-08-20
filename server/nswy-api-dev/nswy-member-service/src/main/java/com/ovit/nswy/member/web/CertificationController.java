package com.ovit.nswy.member.web;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ovit.nswy.member.common.enums.CertificationStepEnum;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ovit.nswy.member.util.SmsUtil;
import com.ovit.nswy.member.web.base.Result;

@RestController
@RequestMapping("/Certification")
public class CertificationController {
    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private CertificationService certificationService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SmsUtil smsUtil;
    @Autowired
    private LoginUserService loginUserService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insert(@RequestBody Map<String, Object> params,HttpServletRequest request) {
        Result result = null;
        try {
            result = new Result();
            String phone = MapUtils.getString(params,"phone");
            String code = MapUtils.getString(params,"code");
            String randCode = redisTemplate.opsForValue().get(phone);
            String account = MapUtils.getString(params, "account");
            LoginUser loginUser = new LoginUser();
            loginUser.setLoginAccount(account);
            if (null == loginUser) {
                logger.error("回话超时");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                result.setMsg("登录超时，请重新登录");
            } else {
                if (StringUtils.isNotEmpty(randCode) && code.equals(randCode)) {
                    redisTemplate.delete(phone);
                    certificationService.saveOrUpdate(params,loginUser);
                    //保存认证步骤到登陆表中
                    LoginUser loginInfo = loginUserService.findByLoginName(account);
                    int step = Integer.parseInt(loginInfo.getIsIdentityVerification());
                    if (step < 1) {
                        loginUserService.updateIdentityFlag(account, String.valueOf(CertificationStepEnum.STEP_AUTHENTICATION.getStepNum()));
                    }
                } else {
                    result.setData(9);
                }
            }
        } catch (Exception e) {
            logger.error("实名认证失败：{}",e);
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    // 发送验证码
    @RequestMapping(value = "/code", method = RequestMethod.POST)
    public Result code(@RequestBody Map<String, Object> params, HttpServletRequest request) throws IOException {
        String phone = (String) params.get("phone");
        // 随机生成验证码
        String randCode = RandomStringUtils.randomNumeric(6);
        //System.out.println(randCode);
        // 五分钟有效
        redisTemplate.opsForValue().set(phone, randCode, 5, TimeUnit.MINUTES);
        // 发送短信
        Result result  = smsUtil.sendSms(phone, randCode);
        return result;
    }


    @RequestMapping(value = "/find")
    public Result findByAccount(HttpServletRequest request) {
        Result result = new Result();
        try {
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            if (null == loginUser) {
                result.setMsg("登录超时，请重新登录");
                result.setCode(ResultCode.UNAUTHORIZED);
            } else {
                String account = loginUser.getLoginAccount();
                Certification certification = certificationService.selectByAccount(account);
                result.setData(certification);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}