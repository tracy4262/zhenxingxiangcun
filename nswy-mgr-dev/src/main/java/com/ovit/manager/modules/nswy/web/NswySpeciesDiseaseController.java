package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.SpeciesInfo;
import com.ovit.manager.modules.nswy.service.NswySpeciesDiseaseService;
import com.ovit.manager.modules.nswy.service.NswySpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 病害的审核
 * Created by ${huipei.x} on 2017-10-18.
 */
@Controller
@RequestMapping("${adminPath}/nswy")
public class NswySpeciesDiseaseController extends BaseController {

    @Autowired
    private NswySpeciesDiseaseService nswySpeciesDiseaseService;

    @ModelAttribute
    public SpeciesInfo get(@RequestParam(required=false) String indexid) {
        SpeciesInfo speciesInfo = new SpeciesInfo();
        if (indexid !=null){
            Map<String,Object> map = nswySpeciesDiseaseService.getSpeciesDiseaseById(indexid);
            speciesInfo.setFname(String.valueOf(map.get("fname")));
            speciesInfo.setAuditstatus(String.valueOf(map.get("auditstatus")));
            return speciesInfo;
        }else{
            return new SpeciesInfo();
        }

    }

    //虫害审核
    @RequestMapping(value = "/getSpeciesDiseaseList")
    public String getSpeciesDiseaseList(@RequestParam Map<String,Object> map,  Model model, HttpServletRequest request, HttpServletResponse response){
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
        Page<Map<String,Object>> page = nswySpeciesDiseaseService.listSpeciesDisease(map);
        model.addAttribute(page);
        model.addAttribute("speciesInfo",speciesInfo);
        return "modules/nswy/speciesDiseaseList";
    }

    @RequestMapping(value = "/speciesDiseaseForm")
    public String expertApplyForm(@RequestParam String indexid, Model model) {
        Map<String,Object> map = nswySpeciesDiseaseService.getSpeciesDiseaseById(indexid);
        model.addAttribute("species",map);

        return "modules/nswy/speciesDiseaseForm";
    }


    @RequestMapping(value = "/updateSpeciesDisease")
    public String updateSpeciesDisease(@RequestParam Map<String,Object> params){
        nswySpeciesDiseaseService.updateSpeciesDisease(params);
        return "redirect:" + adminPath +"/nswy/getSpeciesDiseaseList/";
    }

}
