package com.ovit.nswy.member.web;


import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.UserContact;
import com.ovit.nswy.member.model.UserWebsite;
import com.ovit.nswy.member.service.UserContactService;
import com.ovit.nswy.member.service.UserWebsiteService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/website")
public class UserWebsiteController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private UserWebsiteService userWebsiteService;
    @Autowired
    private UserContactService userContactService;

    /**
     * 保存网页设置
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insert(HttpServletRequest request, @RequestBody(required = false) Map<String, Object> params) {
        Result result = new Result();
        try {
            HttpSession session = request.getSession();
            LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
            logger.info("网站设置：{}",params);
            userWebsiteService.saveOrUpdateWebsite(params,loginUser,session);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 通过用户名查询栏目
     * @return
     */
    @RequestMapping(value = "/find/{account}", method = RequestMethod.GET)
    public Result findByName(@PathVariable String account) {
        Result result = new Result();
        try {
            UserWebsite userWebsite=userWebsiteService.selectByAccount(account);
            /*UserContact contact = userContactService.selectByAccount(account);
            String domain=contact.getDomain();
            String moduar=userWebsite.getModular();
            if(moduar !=null){
                String[] modulars=moduar.split(",");
                Map<String,String> map=new HashMap<>();
                for (String s:modulars){
                    map.put(s,domain);
                }
                Map<String,Object> mapResult=new HashMap<>();
                mapResult.put("web",userWebsite);
                mapResult.put("modular",map);
                result.setData(mapResult);
            }else{
                result.setData(null);
            }
            */
            result.setData(userWebsite);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }



    /**
     * 通过用户名查询栏目
     *
     * @return
     */
    @RequestMapping(value = "/findByAccount/{account}", method = RequestMethod.GET)
    public Result findAccount(@PathVariable String account) {
        Result result = new Result();
        try {
            UserWebsite userWebsite=userWebsiteService.selectByAccount(account);
            if(userWebsite !=null){
                result.setData(userWebsite);
            }else{
                result.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


}
