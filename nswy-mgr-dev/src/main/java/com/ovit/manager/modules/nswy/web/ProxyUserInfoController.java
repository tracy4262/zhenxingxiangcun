package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.ProxyUserInfo;
import com.ovit.manager.modules.nswy.service.ProxyUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ${huipei.x} on 2018-2-7.
 */
@Controller
@RequestMapping("${adminPath}/nswy")
public class ProxyUserInfoController extends BaseController {

    @Autowired
    private ProxyUserInfoService proxyUserInfoService;

    @ModelAttribute
    public ProxyUserInfo get(@RequestParam(required=false) Integer proxyUserInfoId) {
        if (proxyUserInfoId !=null){
            return proxyUserInfoService.getProxyUserInfoById(proxyUserInfoId);
        }else{
            return new ProxyUserInfo();
        }
    }

    @RequestMapping("/getProxyUserInfolist")
    public String getProxyUserInfolist(ProxyUserInfo proxyUserInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<ProxyUserInfo> page =proxyUserInfoService.findList(proxyUserInfo, request, response);
        model.addAttribute("page",page);
        return "modules/nswy/proxyUserInfoList";
    }

    @RequestMapping("/updateProxyUserInfo")
    public String updateProxyUserInfo(ProxyUserInfo proxyUserInfo, RedirectAttributes redirectAttributes){
        Integer rows=proxyUserInfoService.updateProxyUserInfo(proxyUserInfo);
        if(rows.equals(0)){
            addMessage(redirectAttributes, "审核失败");
            return "redirect:" + adminPath +"/nswy/proxyUserInfoAuditFrom?proxyUserInfoId="+proxyUserInfo.getProxyUserInfoId();
        }else{
            addMessage(redirectAttributes, "审核成功");
            return "redirect:" + adminPath +"/nswy/getProxyUserInfolist";
        }
    }

    @RequestMapping(value = "proxyUserInfoAuditFrom")
    public String proxyUserInfoAuditFrom(ProxyUserInfo proxyUserInfo, Model model) {
        model.addAttribute("proxyUserInfo",proxyUserInfo);
        return "modules/nswy/proxyUserInfoAuditFrom";
    }

    @RequestMapping("/deleteProxyUserInfo")
    public String deleteProxyUserInfo(Integer proxyUserInfoId){
        proxyUserInfoService.deleteProxyUserInfo(proxyUserInfoId);
        return "redirect:" + adminPath +"/nswy/getProxyUserInfolist";
    }

}
