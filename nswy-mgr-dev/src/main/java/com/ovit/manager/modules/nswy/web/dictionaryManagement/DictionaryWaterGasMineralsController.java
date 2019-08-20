package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryWaterGasMinerals;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryWaterGasMineralsService;
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
@RequestMapping("${adminPath}/nswy/dictionaryWaterGasMinerals")
public class DictionaryWaterGasMineralsController extends BaseController {

    @Autowired
    private DictionaryWaterGasMineralsService dictionaryWaterGasMineralsService;

    @RequestMapping("/control")
    public String findList(DictionaryWaterGasMinerals dictionaryWaterGasMinerals, Model model, HttpServletRequest request, HttpServletResponse response){
       Page<DictionaryWaterGasMinerals> page = dictionaryWaterGasMineralsService.findList(dictionaryWaterGasMinerals,request,response);
       model.addAttribute("page",page);
       return "modules/content/dictionaryManagement/dictionaryWaterGasMineralsList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryWaterGasMineralsAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryWaterGasMineralsAdd";
    }


    @RequestMapping(value = "/save")
    public String saveWaterGasMinerals( HttpServletRequest request,DictionaryWaterGasMinerals dictionaryWaterGasMinerals){

        try {

            String name = request.getParameter("name");
            String pid = request.getParameter("pid");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryWaterGasMinerals.setId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            dictionaryWaterGasMinerals.setName(name);
            dictionaryWaterGasMinerals.setValue(value);
            dictionaryWaterGasMinerals.setRemark(remark);
            dictionaryWaterGasMinerals.setPid(pid);
            dictionaryWaterGasMinerals.setLevel(level);

            dictionaryWaterGasMineralsService.saveWaterGasMinerals(dictionaryWaterGasMinerals);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryWaterGasMinerals/control/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam String id,Model model){
        DictionaryWaterGasMinerals dictionaryWaterGasMinerals = new DictionaryWaterGasMinerals();
        dictionaryWaterGasMinerals.setId(id);
        List<DictionaryWaterGasMinerals> dictionaryWaterGasMineralss = dictionaryWaterGasMineralsService.findWaterGasMineralsList(dictionaryWaterGasMinerals);
        model.addAttribute("dictionaryWaterGasMinerals",dictionaryWaterGasMineralss.get(0));

        return "modules/content/dictionaryManagement/dictionaryWaterGasMineralsFrom";
    }

    @RequestMapping(value = "/updateWaterGasMinerals")
    public String updateWaterGasMinerals( HttpServletRequest request,DictionaryWaterGasMinerals dictionaryWaterGasMinerals){

        try {
            dictionaryWaterGasMineralsService.updateWaterGasMinerals(dictionaryWaterGasMinerals);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryWaterGasMinerals/control/";
    }

    @RequestMapping(value = "/delete")
    public String deleteWaterGasMinerals(@RequestParam String id){

        dictionaryWaterGasMineralsService.deleteWaterGasMinerals(id);


        return "redirect:" + adminPath +"/nswy/dictionaryWaterGasMinerals/control/";
    }
}
