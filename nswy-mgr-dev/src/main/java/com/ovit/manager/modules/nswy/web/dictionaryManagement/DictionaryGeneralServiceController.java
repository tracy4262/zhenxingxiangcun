package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.github.xphsc.util.PinyinUtil;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryGeneralService;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryGeneralServiceService;
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
@RequestMapping("${adminPath}/nswy/dictionaryGeneralService")
public class DictionaryGeneralServiceController extends BaseController {

    @Autowired
    private DictionaryGeneralServiceService dictionaryGeneralServiceService;

    @RequestMapping("/control")
    public String findList(DictionaryGeneralService dictionaryGeneralService, Model model, HttpServletRequest request, HttpServletResponse response){
//       Page<DictionaryGeneralService> page = dictionaryGeneralServiceService.findList(dictionaryGeneralService,request,response);
        List<DictionaryGeneralService> page = dictionaryGeneralServiceService.findList(dictionaryGeneralService);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryGeneralServiceList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);

        List<Map<String,Object>> industryList = dictionaryGeneralServiceService.findIndustry();
        model.addAttribute("industryList",industryList);

        List<Map<String,Object>> serviceTypeList = dictionaryGeneralServiceService.findServiceType();
        model.addAttribute("serviceTypeList",serviceTypeList);

        List<Map<String,Object>> speciesList = dictionaryGeneralServiceService.findSpeciesList();
        model.addAttribute("speciesList",speciesList);

        List<Map<String,Object>> serviceList = dictionaryGeneralServiceService.findService();
        model.addAttribute("serviceList",serviceList);

        List<Map<String,Object>> productList = dictionaryGeneralServiceService.findProduct();
        model.addAttribute("productList",productList);

        return "modules/content/dictionaryManagement/dictionaryGeneralServiceAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);

        List<Map<String,Object>> industryList = dictionaryGeneralServiceService.findIndustry();
        model.addAttribute("industryList",industryList);

        List<Map<String,Object>> serviceTypeList = dictionaryGeneralServiceService.findServiceType();
        model.addAttribute("serviceTypeList",serviceTypeList);

        List<Map<String,Object>> speciesList = dictionaryGeneralServiceService.findSpeciesList();
        model.addAttribute("speciesList",speciesList);

        List<Map<String,Object>> serviceList = dictionaryGeneralServiceService.findService();
        model.addAttribute("serviceList",serviceList);

        List<Map<String,Object>> productList = dictionaryGeneralServiceService.findProduct();
        model.addAttribute("productList",productList);

        return "modules/content/dictionaryManagement/dictionaryGeneralServiceAdd";
    }


    @RequestMapping(value = "/save")
    public String saveGeneralService( HttpServletRequest request,DictionaryGeneralService dictionaryGeneralService){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));
            String type = "2";
            String account = "administrator";
            String status = "1";
            String servicePeopleName = request.getParameter("servicePeopleName");

            dictionaryGeneralService.setServicePeopleName(servicePeopleName);
            dictionaryGeneralService.setAccount(account);
            dictionaryGeneralService.setStatus(status);
            dictionaryGeneralService.setType(type);
            dictionaryGeneralService.setName(name);
            dictionaryGeneralService.setValue(value);
            dictionaryGeneralService.setRemark(remark);
            dictionaryGeneralService.setPid(pid);
            dictionaryGeneralService.setLevel(level);

            dictionaryGeneralServiceService.saveGeneralService(dictionaryGeneralService);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryGeneralService/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryGeneralService dictionaryGeneralService = new DictionaryGeneralService();
        dictionaryGeneralService.setId(id);
        List<DictionaryGeneralService> dictionaryGeneralServices = dictionaryGeneralServiceService.findGeneralServiceList(dictionaryGeneralService);
        model.addAttribute("dictionaryGeneralService",dictionaryGeneralServices.get(0));

        List<Map<String,Object>> industryList = dictionaryGeneralServiceService.findIndustry();
        model.addAttribute("industryList",industryList);

        List<Map<String,Object>> serviceTypeList = dictionaryGeneralServiceService.findServiceType();
        model.addAttribute("serviceTypeList",serviceTypeList);

        List<Map<String,Object>> speciesList = dictionaryGeneralServiceService.findSpeciesList();
        model.addAttribute("speciesList",speciesList);

        List<Map<String,Object>> serviceList = dictionaryGeneralServiceService.findService();
        model.addAttribute("serviceList",serviceList);

        List<Map<String,Object>> productList = dictionaryGeneralServiceService.findProduct();
        model.addAttribute("productList",productList);

        return "modules/content/dictionaryManagement/dictionaryGeneralServiceFrom";
    }

    @RequestMapping(value = "/updateGeneralService")
    public String updateGeneralService( HttpServletRequest request,DictionaryGeneralService dictionaryGeneralService){

        try {
            String industryTypeId = request.getParameter("industryTypeId");
            String relatedSpeciesId = request.getParameter("relatedSpeciesId");
            String serviceId = request.getParameter("serviceId");
            String productId = request.getParameter("productId");

            dictionaryGeneralService.setIndustryTypeId(industryTypeId);
            dictionaryGeneralService.setRelatedSpeciesId(relatedSpeciesId);
            dictionaryGeneralService.setServiceId(serviceId);
            dictionaryGeneralService.setProductId(productId);

            dictionaryGeneralServiceService.updateGeneralService(dictionaryGeneralService);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryGeneralService/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteGeneralService(@RequestParam String id){

        dictionaryGeneralServiceService.deleteGeneralService(id);

        return "redirect:" + adminPath +"/nswy/dictionaryGeneralService/control/";
    }
}
