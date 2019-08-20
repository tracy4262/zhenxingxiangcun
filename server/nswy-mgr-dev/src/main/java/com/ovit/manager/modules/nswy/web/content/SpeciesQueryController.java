package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.service.SpeciesQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${adminPath}/nswy/speciesQuery")
public class SpeciesQueryController extends BaseController {

    @Autowired
    private SpeciesQueryService speciesQueryService;

    //物种
    @RequestMapping(value = "/findSpeciesList")
    public String findSpeciesList(@RequestParam Map<String,Object> map, Model model, HttpServletRequest request, HttpServletResponse response){

        List<Map<String,Object>> list = speciesQueryService.findSpeciesList(map);
        model.addAttribute("list",list);

        return "modules/content/speciesQueryList";
    }

    @RequestMapping(value = "/add")
    public String add(@RequestParam String id, Model model){
        model.addAttribute("pid",id);
        return "modules/content/speciesQueryAdd";
    }

    @RequestMapping(value = "/addPid")
    public String addPid(@RequestParam String pid, Model model){
        model.addAttribute("pid",pid);
        return "modules/content/speciesQueryAdd";
    }


    @RequestMapping(value = "/save")
    public String saveUnit( HttpServletRequest request){

        try {
            String speciesName = request.getParameter("speciesName");
            String speciesCode = request.getParameter("speciesCode");
            String pid = request.getParameter("pid");

            Map<String,Object> map = new HashMap<>();

            map.put("pid",pid);
            map.put("speciesName",speciesName);
            map.put("speciesCode",speciesCode);
            map.put("pinyin",request.getParameter("pinyin"));
            map.put("speciesVulgo",request.getParameter("speciesVulgo"));
            map.put("otherClassify",request.getParameter("otherClassify"));
            map.put("fclassifiedid",request.getParameter("fclassifiedid"));
            map.put("findustriaclassifiedid",request.getParameter("findustriaclassifiedid"));
            map.put("fisprotection",request.getParameter("fisprotection"));
            map.put("majorProduct",request.getParameter("majorProduct"));
            map.put("fshapefeatureid",request.getParameter("fshapefeatureid"));
            map.put("remark",request.getParameter("remark"));

            if(pid.equals("1")||pid.equals("0")||pid.equals("")){
                speciesQueryService.saveSpeciesClass(map);
            }else{
                speciesQueryService.saveSpecies(map);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/speciesQuery/findSpeciesList";
    }

    @RequestMapping(value = "/form")
    public String update(Model model,@RequestParam String speciesid){
        List<Map<String,Object>> list = speciesQueryService.getSpeciesById(speciesid);
        model.addAttribute("species",list.get(0));

        return "modules/content/speciesQueryFrom";
    }

    @RequestMapping(value = "/updateSpecies")
    public String updateSpecies( HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();

        map.put("indexid",request.getParameter("indexid"));
        map.put("speciesName",request.getParameter("speciesName"));
        map.put("speciesCode",request.getParameter("speciesName"));
        map.put("pinyin",request.getParameter("pinyin"));
        map.put("speciesVulgo",request.getParameter("speciesVulgo"));
        map.put("otherClassify",request.getParameter("otherClassify"));
        map.put("fclassifiedid",request.getParameter("fclassifiedid"));
        map.put("findustriaclassifiedid",request.getParameter("findustriaclassifiedid"));
        map.put("fisprotection",request.getParameter("fisprotection"));
        map.put("majorProduct",request.getParameter("majorProduct"));
        map.put("fshapefeatureid",request.getParameter("fshapefeatureid"));
        map.put("remark",request.getParameter("remark"));

        String pid = request.getParameter("pid");
        if(pid.equals("1")||pid.equals("0")||pid.equals("")){

        }else{
            speciesQueryService.updateSpecies(map);
        }

        return "redirect:" + adminPath +"/nswy/speciesQuery/findSpeciesList";
    }

    @RequestMapping(value = "/delete")
    public String deleteSpecies(@RequestParam String indexid,@RequestParam String pid){

        if(pid.equals("1")||pid.equals("0")||pid.equals("")){
            speciesQueryService.deleteSpeciesClass(indexid);
        }else{
            speciesQueryService.deleteSpecies(indexid);
        }


        return "redirect:" + adminPath +"/nswy/speciesQuery/findSpeciesList/";
    }
}
