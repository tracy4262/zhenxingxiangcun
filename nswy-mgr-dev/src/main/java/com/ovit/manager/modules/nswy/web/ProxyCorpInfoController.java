package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.AgencyAudit;
import com.ovit.manager.modules.nswy.entity.ProxyCropInfo;
import com.ovit.manager.modules.nswy.service.AgencyAuditService;
import com.ovit.manager.modules.nswy.service.ProxyCorpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-2-7.
 */
@Controller
@RequestMapping("${adminPath}/nswy")
public class ProxyCorpInfoController extends BaseController {

    @Autowired
    private ProxyCorpInfoService proxyCorpInfoService;

    @ModelAttribute
    public ProxyCropInfo get(@RequestParam(required=false) Integer proxyCorpInfoId) {
        if (proxyCorpInfoId !=null){
            return proxyCorpInfoService.getProxyCropInfoById(proxyCorpInfoId);
        }else{
            return new ProxyCropInfo();
        }
    }

    @RequestMapping(value = "proxyCorpInfoForm")
    public String proxyCorpInfoForm(ProxyCropInfo proxyCorpInfo, Model model) {
        model.addAttribute("proxyCorpInfo",proxyCorpInfo);
        return "modules/nswy/proxyCorpInfoForm";
    }

    @RequestMapping(value = "proxyCorpInfoAuditFrom")
    public String proxyCorpInfoAuditFrom(ProxyCropInfo proxyCorpInfo, Model model) {
        model.addAttribute("proxyCorpInfo",proxyCorpInfo);
        return "modules/nswy/proxyCorpInfoAuditFrom";
    }


    @RequestMapping("/getProxyCorpInfolist")
    public String getProxyCorpInfolist(ProxyCropInfo proxyCropInfo,Model model,HttpServletRequest request, HttpServletResponse response){
        Page<ProxyCropInfo> page =proxyCorpInfoService.findList(proxyCropInfo, request, response);
        model.addAttribute("page",page);
        return "modules/nswy/proxyCorpInfoList";
    }

    @RequestMapping("/updateProxyCorpInfo")
    public String updateProxyCorpInfo(ProxyCropInfo proxyCropInfo, RedirectAttributes redirectAttributes){
        Integer rows=proxyCorpInfoService.updateProxyCropInfo(proxyCropInfo);
        if(rows.equals(0)){
            addMessage(redirectAttributes, "审核失败");
            return "redirect:" + adminPath +"/nswy/proxyCorpInfoAuditFrom?id="+proxyCropInfo.getId();
        }else{
            addMessage(redirectAttributes, "审核成功");
            return "redirect:" + adminPath +"/nswy/getProxyCorpInfolist";
        }

    }
    @RequestMapping("/deleteProxyCorpInfo")
    public String deleteProxyCorpInfo(Integer proxyCorpInfoId){
        proxyCorpInfoService.deleteProxyCropInfo(proxyCorpInfoId);
        return "redirect:" + adminPath +"/nswy/getProxyCorpInfolist";
    }

}
