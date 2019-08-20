package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.ServiceTypeInfo;
import com.ovit.manager.modules.nswy.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("${adminPath}/nswy")
public class ServiceTypeController extends BaseController {
    @Autowired
    private ServiceTypeService serviceTypeService;

    @RequestMapping(value = "/serviceType/control")
    public String pictureList(ServiceTypeInfo serviceTypetInfo, Model model, HttpServletRequest request, HttpServletResponse response){

        Page<ServiceTypeInfo> page = serviceTypeService.findFullAppSetting(serviceTypetInfo, request, response);
        model.addAttribute("page",page);

        return "modules/content/serviceTypeList";
    }

    @RequestMapping(value = "/serviceType/save")
    public String saveIndustry(HttpServletRequest request,ServiceTypeInfo serviceTypeInfo){
        try {
            String dictName = request.getParameter("dictName");
            String dictType = "d2389207-0661-11e8-ab8c-005056bc3817";
            //String dictValue = "123456";
            serviceTypeInfo.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            serviceTypeInfo.setDictName(dictName);
            serviceTypeInfo.setDictType(dictType);
            //serviceTypeInfo.setDictValue(dictValue);

            serviceTypeService.saveServiceType(serviceTypeInfo);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/serviceType/control/";
    }

    @RequestMapping(value = "/serviceType/add")
    public String add(Model model){

        List<Map<String,Object>> list = serviceTypeService.findServiceType();
        model.addAttribute("list",list);
        return "modules/content/serviceTypeAdd";
    }

    @RequestMapping(value = "/serviceType/update")
    public String update(@RequestParam String id, Model model){
        ServiceTypeInfo serviceTypeInfo = new ServiceTypeInfo();
        serviceTypeInfo.setId(id);
        List<ServiceTypeInfo> serviceTypeInfos = serviceTypeService.findServiceTypeList(serviceTypeInfo);
        model.addAttribute("serviceTypeInfo",serviceTypeInfos.get(0));

        List<Map<String,Object>> list = serviceTypeService.findServiceType();
        model.addAttribute("list",list);

        return "modules/content/serviceTypeFrom";
    }

    @RequestMapping(value = "/serviceType/updateServiceType")
    public String updateUnit( HttpServletRequest request,ServiceTypeInfo serviceTypeInfo){

        try {

            String dictName = request.getParameter("dictName");

            serviceTypeInfo.setDictName(dictName);
            serviceTypeService.updateServiceType(serviceTypeInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/serviceType/control/";
    }

    @RequestMapping(value = "/serviceType/delete")
    public String deleteServiceType(@RequestParam String id){
        serviceTypeService.deleteServiceType(id);
        return "redirect:" + adminPath +"/nswy/serviceType/control/";
    }
}


