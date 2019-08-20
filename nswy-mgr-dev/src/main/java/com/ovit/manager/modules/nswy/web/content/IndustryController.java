package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.IndustryInfo;
import com.ovit.manager.modules.nswy.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("${adminPath}/nswy")
public class IndustryController extends BaseController {
    @Autowired
    private IndustryService industryService;

    @RequestMapping(value = "/industry/control")
    public String industryList(IndustryInfo industryInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        List<IndustryInfo> page = industryService.findList(industryInfo);
        model.addAttribute("list",page);

        return "modules/content/industryList";
    }

    @RequestMapping(value = "/industry/add")
    public String add(@RequestParam String id,Model model){
        model.addAttribute("pid",id);
        return "modules/content/industryAdd";
    }

    @RequestMapping(value = "/industry/addPid")
    public String addPid(@RequestParam String pid,Model model){
        model.addAttribute("pid",pid);
        return "modules/content/industryAdd";
    }

    @RequestMapping(value = "/industry/save")
    public String saveIndustry(HttpServletRequest request,IndustryInfo industryInfo){
        try {
            /*List<IndustryInfo> industryInfos = industryService.findByPidDesc(industryInfo);
            if(industryInfos.size()>0){
                IndustryInfo info  = industryInfos.get(0);

                String dictValue = info.getDictValue();
                Integer code  = Integer.parseInt(dictValue.substring(dictValue.length()-2))+1;

                StringBuffer classification = new StringBuffer(dictValue.substring(0,dictValue.length()-2));

                dictValue = classification.append(code).toString();

                industryInfo.setDictValue(dictValue);

            }else{
                industryInfos = industryService.findByIdDesc(industryInfo);

                IndustryInfo info  = industryInfos.get(0);

                String dictValue = info.getDictValue();

                StringBuffer classification = new StringBuffer(dictValue);

                dictValue = classification.append("01").toString();

                industryInfo.setDictValue(dictValue);

            }*/

            String dictName = request.getParameter("dictName");
            String pid = request.getParameter("pid");
            String dictType = "ba4d3d9c-cc38-4ddb-a7f2-25cd1323c2f7";

            industryInfo.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            industryInfo.setDictName(dictName);
            industryInfo.setPid(pid);
            industryInfo.setDictType(dictType);
            industryService.saveIndustry(industryInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/industry/control/";
    }

    @RequestMapping(value = "/industry/form")
    public String update(@RequestParam String id,Model model){
        IndustryInfo industryInfo = new IndustryInfo();
        industryInfo.setId(id);
        List<IndustryInfo> industryInfos = industryService.findList(industryInfo);
        model.addAttribute("industryInfo",industryInfos.get(0));

        return "modules/content/industryFrom";
    }

    @RequestMapping(value = "/industry/updateIndustry")
    public String updateIndustry( HttpServletRequest request,IndustryInfo industryInfo){

        try {
            industryService.updateIndustry(industryInfo);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/industry/control/";
    }

    @RequestMapping(value = "/industry/delete")
    public String deleteIndustry(@RequestParam String id){
        industryService.deleteIndustry(id);
        return "redirect:" + adminPath +"/nswy/industry/control/";
    }
}
