package com.ovit.manager.modules.nswy.web;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.SpeciesInfo;
import com.ovit.manager.modules.nswy.service.NswySpeciesDiseaseService;
import com.ovit.manager.modules.nswy.service.NswySpeciesPestService;
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
 * 虫害的审核
 * Created by ${huipei.x} on 2017-10-18.
 */
@Controller
@RequestMapping("${adminPath}/nswy")
public class NswySpeciesPestController extends BaseController {

    @Autowired
    private NswySpeciesPestService nswySpeciesPestService;

    @ModelAttribute
    public SpeciesInfo get(@RequestParam(required=false) String indexid) {
        SpeciesInfo speciesInfo = new SpeciesInfo();
        if (indexid !=null){
            Map<String,Object> map = nswySpeciesPestService.getSpeciesPestById(indexid);
            speciesInfo.setFname(String.valueOf(map.get("fname")));
            speciesInfo.setAuditstatus(String.valueOf(map.get("auditstatus")));
            return speciesInfo;
        }else{
            return new SpeciesInfo();
        }

    }

    //物种审核
    @RequestMapping(value = "/getSpeciesPestList")
    public String getSpeciesPestList(@RequestParam Map<String,Object> map,  Model model, HttpServletRequest request, HttpServletResponse response){
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
        Page<Map<String,Object>> page = nswySpeciesPestService.listSpeciesPest(map);
        model.addAttribute(page);
        model.addAttribute("speciesInfo",speciesInfo);
        return "modules/nswy/speciesPestList";
    }

    @RequestMapping(value = "/speciesPestForm")
    public String expertApplyForm(@RequestParam String indexid, Model model) {
        Map<String,Object> map = nswySpeciesPestService.getSpeciesPestById(indexid);
        model.addAttribute("species",map);

        return "modules/nswy/speciesPestForm";
    }


    @RequestMapping(value = "/updateSpeciesPest")
    public String updateSpeciesPest(@RequestParam Map<String,Object> params){
        nswySpeciesPestService.updateSpeciesPest(params);
        return "redirect:" + adminPath +"/nswy/getSpeciesPestList/";
    }

}
