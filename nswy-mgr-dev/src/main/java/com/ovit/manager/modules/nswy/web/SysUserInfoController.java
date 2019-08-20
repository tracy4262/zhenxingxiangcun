package com.ovit.manager.modules.nswy.web;


import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;

import com.ovit.manager.modules.nswy.entity.ProxyGovInfo;
import com.ovit.manager.modules.nswy.entity.SysUserInfo;
import com.ovit.manager.modules.nswy.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by ${wangrongf}
 */
@Component
@EnableScheduling
@Controller
@RequestMapping("${adminPath}/nswy")
public class SysUserInfoController extends BaseController {

    @Autowired
    private SysUserInfoService sysUserInfoService;

   /* @Scheduled(cron= "0 0/2 * * * ?")
    public void importUserInfo(){
        sysUserInfoService.updateLoginOfUser();
    }
*/

    @RequestMapping("/findList")
    public String findList(SysUserInfo sysUserInfo,Model model, HttpServletRequest request, HttpServletResponse response){
        Page<SysUserInfo> page =sysUserInfoService.findList(sysUserInfo, request, response);
        model.addAttribute("page",page);
        return "modules/content/sysUserInfo";
    }

    @ResponseBody
    @RequestMapping(value = "/updateSysUserInfo" ,method = RequestMethod.POST)
    public String updateSysUserInfo(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("user_status",request.getParameter("user_status"));
        map.put("info_id",request.getParameter("infoId"));
        map.put("INFO_NAME",request.getParameter("INFO_NAME"));
        sysUserInfoService.updateUserInfo(map);
        return "success";
    }


}
