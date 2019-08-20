package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryCommodity;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryCommodityService;
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
@RequestMapping("${adminPath}/nswy/dictionaryCommodity")
public class DictionaryCommodityController extends BaseController {

    @Autowired
    private DictionaryCommodityService dictionaryCommodityService;

    @RequestMapping("/control")
    public String findList(DictionaryCommodity dictionaryCommodity, Model model, HttpServletRequest request, HttpServletResponse response){
        List<DictionaryCommodity> page = dictionaryCommodityService.findList(dictionaryCommodity);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryCommodityList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);

        List<Map<String,Object>> industrylist = dictionaryCommodityService.findIndustry();
        model.addAttribute("industrylist",industrylist);

        List<Map<String,Object>> productTypeList = dictionaryCommodityService.findProductType();
        model.addAttribute("productTypeList",productTypeList);

        List<Map<String,Object>> specieslist = dictionaryCommodityService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);

        return "modules/content/dictionaryManagement/dictionaryCommodityAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);

        List<Map<String,Object>> industrylist = dictionaryCommodityService.findIndustry();
        model.addAttribute("industrylist",industrylist);


        List<Map<String,Object>> specieslist = dictionaryCommodityService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);

        List<Map<String,Object>> productTypeList = dictionaryCommodityService.findProductType();
        model.addAttribute("productTypeList",productTypeList);

        return "modules/content/dictionaryManagement/dictionaryCommodityAdd";
    }


    @RequestMapping(value = "/save")
    public String saveCommodity( HttpServletRequest request,DictionaryCommodity dictionaryCommodity){

        try {

            String pid = request.getParameter("pid");
            String name = request.getParameter("name");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));
            String type = request.getParameter("type");
            String account = "administrator";
            String status = "1";

            dictionaryCommodity.setStatus(status);
            dictionaryCommodity.setAccount(account);
            dictionaryCommodity.setName(name);
            dictionaryCommodity.setType(type);
            dictionaryCommodity.setValue(value);
            dictionaryCommodity.setRemark(remark);
            dictionaryCommodity.setPid(pid);
            dictionaryCommodity.setLevel(level);

            dictionaryCommodityService.saveCommodity(dictionaryCommodity);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryCommodity/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryCommodity dictionaryCommodity = new DictionaryCommodity();
        dictionaryCommodity.setId(id);
        List<DictionaryCommodity> dictionaryCommoditys = dictionaryCommodityService.findCommodityList(dictionaryCommodity);
        model.addAttribute("dictionaryCommodity",dictionaryCommoditys.get(0));

        List<Map<String,Object>> industrylist = dictionaryCommodityService.findIndustry();
        model.addAttribute("industrylist",industrylist);

        List<Map<String,Object>> productTypeList = dictionaryCommodityService.findProductType();
        model.addAttribute("productTypeList",productTypeList);

        List<Map<String,Object>> specieslist = dictionaryCommodityService.findSpeciesList();
        model.addAttribute("specieslist",specieslist);

        return "modules/content/dictionaryManagement/dictionaryCommodityFrom";
    }

    @RequestMapping(value = "/updateCommodity")
    public String updateCommodity( HttpServletRequest request,DictionaryCommodity dictionaryCommodity){

        try {
            String industryTypeId = request.getParameter("industryTypeId");
            String relatedSpeciesId = request.getParameter("relatedSpeciesId");
            String productTypeId = request.getParameter("productTypeId");

            dictionaryCommodity.setProductTypeId(productTypeId);
            dictionaryCommodity.setIndustryTypeId(industryTypeId);
            dictionaryCommodity.setRelatedSpeciesId(relatedSpeciesId);

            dictionaryCommodityService.updateCommodity(dictionaryCommodity);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryCommodity/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteCommodity(@RequestParam String id){

        dictionaryCommodityService.deleteCommodity(id);

        return "redirect:" + adminPath +"/nswy/dictionaryCommodity/control/";
    }
}
