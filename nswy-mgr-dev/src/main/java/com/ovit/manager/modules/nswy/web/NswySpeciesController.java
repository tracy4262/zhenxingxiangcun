package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;

import com.ovit.manager.modules.nswy.entity.SpeciesInfo;
import com.ovit.manager.modules.nswy.service.NswySpeciesService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-10-18.
 */
@Controller
@RequestMapping("${adminPath}/nswy")
public class NswySpeciesController extends BaseController {

    @Autowired
    private NswySpeciesService nswySpeciesService;

    @ModelAttribute
    public SpeciesInfo get(@RequestParam(required=false) String indexid) {
        SpeciesInfo speciesInfo = new SpeciesInfo();
        if (indexid !=null){
            Map<String,Object> map = nswySpeciesService.getSpeciesById(indexid);
            speciesInfo.setFname(String.valueOf(map.get("fname")));
            speciesInfo.setAuditstatus(String.valueOf(map.get("auditstatus")));
            return speciesInfo;
        }else{
            return new SpeciesInfo();
        }

    }

    //物种审核
    @RequestMapping(value = "/getSpeciesList")
    public String getSpeciesList(@RequestParam Map<String,Object> map,  Model model, HttpServletRequest request, HttpServletResponse response){
        SpeciesInfo speciesInfo = new SpeciesInfo();
        if(map==null||map.size()==0){
            map.put("auditstatus","6");

        }else{
            if (map.get("fname") != null && map.get("fname") != ""){
                speciesInfo.setFname(String.valueOf(map.get("fname")));
            }
            if (map.get("auditstatus") != null && map.get("auditstatus") != ""){
                speciesInfo.setAuditstatus(String.valueOf(map.get("auditstatus")));
            }
        }
        Page<Map<String,Object>> page = nswySpeciesService.getSpeciesList(map);
        model.addAttribute(page);
        model.addAttribute("speciesInfo",speciesInfo);
        return "modules/nswy/speciesList";
    }

    @RequestMapping(value = "/speciesForm")
    public String expertApplyForm(@RequestParam String indexid, Model model) {
        Map<String,Object> map = nswySpeciesService.getSpeciesById(indexid);
        model.addAttribute("species",map.get("species"));
        model.addAttribute("pestList",map.get("pestList"));
        model.addAttribute("diseaseList",map.get("diseaseList"));
        model.addAttribute("varieteyList",map.get("varieteyList"));
        return "modules/nswy/speciesForm";
    }


    @RequestMapping(value = "/updateSpecies")
    public String updateSpecies(@RequestParam Map<String,Object> params){
        nswySpeciesService.updateSpecies(params);
        return "redirect:" + adminPath +"/nswy/getSpeciesList/";
    }

}
