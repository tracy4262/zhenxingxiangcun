package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.GeneralServiceInfo;
import com.ovit.manager.modules.nswy.service.GeneralServiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("${adminPath}/nswy")
public class GeneralServiceController extends BaseController {
    @Autowired
    GeneralServiceInfoService generalServiceInfoService;

    @RequestMapping("/generalService/control")
    public String findList(GeneralServiceInfo generalServiceInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<GeneralServiceInfo> page = generalServiceInfoService.findList(generalServiceInfo,request,response);
        model.addAttribute("page",page);
        return "modules/content/generalServiceList";
    }

    @RequestMapping(value = "/generalService/update")
    public String update(@RequestParam Integer id, Model model){
        GeneralServiceInfo generalServiceInfo = new GeneralServiceInfo();
        generalServiceInfo.setServiceId(id);
        List<GeneralServiceInfo> generalServiceInfos = generalServiceInfoService.findGeneralServiceList(generalServiceInfo);
        model.addAttribute("generalServiceInfo",generalServiceInfos.get(0));

        List<Map<String,Object>> list = generalServiceInfoService.findGeneralService();
        model.addAttribute("list",list);

        return "modules/content/generalServiceFrom";
    }

    @RequestMapping("/generalService/updateGeneralService")
    public String updateGeneralService(HttpServletRequest request,GeneralServiceInfo generalServiceInfo){
        String name = request.getParameter("name");
        String alias = request.getParameter("alias");
        String remark = request.getParameter("ramark");

        generalServiceInfo.setName(name);
        generalServiceInfo.setAlias(alias);
        generalServiceInfo.setRemark(remark);

        generalServiceInfoService.updateGeneralService(generalServiceInfo);
        return "redirect:" + adminPath +"/nswy/generalService/control";
    }

    @RequestMapping(value = "/generalService/add")
    public String add(Model model){
        List<Map<String,Object>> list = generalServiceInfoService.findGeneralService();
        model.addAttribute("list",list);
        return "modules/content/generalServiceAdd";
    }

    @RequestMapping(value = "/generalService/save")
    public String saveGeneralService(HttpServletRequest request,GeneralServiceInfo generalServiceInfo){
        try {
            String name = request.getParameter("name");
            String alias = request.getParameter("alias");
            String remark = request.getParameter("remark");
            Integer type = 2;

            generalServiceInfo.setName(name);
            generalServiceInfo.setAlias(alias);
            generalServiceInfo.setRemark(remark);
            generalServiceInfo.setType(type);
            generalServiceInfoService.saveGeneralService(generalServiceInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/generalService/control";
    }


    @RequestMapping("/generalService/delete")
    public String deleteShop(@RequestParam Integer id){
        generalServiceInfoService.deleteService(id);
        return "redirect:" + adminPath +"/nswy/generalService/control";
    }

}
