package com.ovit.manager.modules.nswy.web.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryUnit;
import com.ovit.manager.modules.nswy.service.dictionaryManagement.DictionaryUnitService;
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
@RequestMapping("${adminPath}/nswy/dictionaryUnit")
public class DictionaryUnitController extends BaseController {

    @Autowired
    private DictionaryUnitService dictionaryUnitService;

    @RequestMapping("/control")
    public String findList(DictionaryUnit dictionaryUnit, Model model, HttpServletRequest request, HttpServletResponse response){
       //Page<DictionaryUnit> page = DictionaryUnit.findList(DictionaryUnit,request,response);
        /*List<DictionaryUnit> page = dictionaryUnitService.findList(dictionaryUnit);
       model.addAttribute("list",page);
       return "modules/content/dictionaryManagement/dictionaryUnitList";*/

        List<DictionaryUnit> unit = dictionaryUnitService.findUnit(dictionaryUnit);
        List<DictionaryUnit> topUnit = dictionaryUnitService.findTopUnit(dictionaryUnit);

        for(DictionaryUnit units: unit){
            if(units.getLevel() > 2){
                Integer pid = Integer.parseInt(units.getPid())+dictionaryUnitService.findMaxUnitType();
                units.setPid(String.valueOf(pid));
            }
        }
        unit.addAll(topUnit);
        model.addAttribute("list",unit);

        return "modules/content/dictionaryManagement/dictionaryUnitList";

    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam(value = "id") String id,@RequestParam(value = "level") Integer level, Model model){
        List<Map<String,Object>> list = dictionaryUnitService.findUnitType();
        model.addAttribute("list",list);

        model.addAttribute("pid",id);
        model.addAttribute("level",level+1);
        return "modules/content/dictionaryManagement/dictionaryUnitAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam(value = "pid") String pid,@RequestParam(value = "level") Integer level,Model model){
        if(pid!="0") {
            List<Map<String, Object>> list = dictionaryUnitService.findUnitType();
            model.addAttribute("list", list);
        }
        model.addAttribute("pid",pid);
        model.addAttribute("level",level);
        return "modules/content/dictionaryManagement/dictionaryUnitAdd";
    }

    @RequestMapping(value = "/save")
    public String saveUnit( HttpServletRequest request,DictionaryUnit dictionaryUnit){

        Integer maxType = dictionaryUnitService.findMaxUnitType();
        try {
            String pid = request.getParameter("pid");

            String name = request.getParameter("name");
            String value = request.getParameter("value");
            String remark = request.getParameter("remark");
            Integer level = Integer.parseInt(request.getParameter("level"));
            Integer unitType;
            dictionaryUnit.setRemark(remark);
            dictionaryUnit.setName(name);
            dictionaryUnit.setValue(value);
            dictionaryUnit.setLevel(level);

            if("0".equals(pid)){
                unitType = maxType;
                dictionaryUnit.setUnitType(unitType+1);
                dictionaryUnitService.saveTopUnit(dictionaryUnit);
            }else{
                if(Integer.parseInt(pid)- maxType>0) {
                    unitType = Integer.parseInt(pid) - maxType;
                }else {
                    unitType = Integer.parseInt(pid);
                }
                String calculateUnit = request.getParameter("calculateUnit");
                String modulus = request.getParameter("modulus");

                dictionaryUnit.setUnitType(unitType);
                dictionaryUnit.setCalculateUnit(calculateUnit);
                dictionaryUnit.setModulus(modulus);
                dictionaryUnitService.saveUnit(dictionaryUnit);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryUnit/control";
    }

    @RequestMapping("/update")
    public String update(@RequestParam(value = "id") String id,@RequestParam(value = "level") String level, Model model){
        DictionaryUnit dictionaryUnit = new DictionaryUnit();
        dictionaryUnit.setId(id);
        dictionaryUnit.setLevel(Integer.parseInt(level));

        List<DictionaryUnit> dictionaryUnits = dictionaryUnitService.findList(dictionaryUnit);
        model.addAttribute("dictionaryUnit",dictionaryUnits.get(0));

        List<Map<String,Object>> list = dictionaryUnitService.findUnitType();
        model.addAttribute("list",list);

        return "modules/content/dictionaryManagement/dictionaryUnitFrom";
    }

    @RequestMapping(value = "/updateUnit")
    public String updateUnit( HttpServletRequest request,DictionaryUnit dictionaryUnit){
        try {

            String name = request.getParameter("name");
            Integer level = Integer.parseInt(request.getParameter("level"));

            dictionaryUnit.setName(name);

            if(level == 1 ){
                String id = request.getParameter("id");
                dictionaryUnit.setId(id);
                dictionaryUnitService.updateTopUnit(dictionaryUnit);
            }else{
                String id = request.getParameter("id");
                Integer num = Integer.parseInt(id)-dictionaryUnitService.findMaxUnitType();

                Integer unitType = Integer.parseInt(request.getParameter("unitType"));
                String calculateUnit = request.getParameter("calculateUnit");
                String modulus = request.getParameter("modulus");
                String value = request.getParameter("value");
                String remark = request.getParameter("remark");

                dictionaryUnit.setId(String.valueOf(num));
                dictionaryUnit.setUnitType(unitType);
                dictionaryUnit.setCalculateUnit(calculateUnit);
                dictionaryUnit.setModulus(modulus);
                dictionaryUnitService.updateUnit(dictionaryUnit);
                dictionaryUnit.setValue(value);
                dictionaryUnit.setRemark(remark);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryUnit/control";
    }

    @RequestMapping(value = "/delete")
    public String deleteTown(@RequestParam(value = "id") String id,@RequestParam(value = "level")String level){
        if("1".equals(level)){
            dictionaryUnitService.deleteTopUnit(id);
        }else{
            Integer num = Integer.parseInt(id)-dictionaryUnitService.findMaxUnitType();
            id = String.valueOf(num);
            dictionaryUnitService.deleteUnit(id);
        }
        return "redirect:" + adminPath +"/nswy/dictionaryUnit/control";
    }



}
