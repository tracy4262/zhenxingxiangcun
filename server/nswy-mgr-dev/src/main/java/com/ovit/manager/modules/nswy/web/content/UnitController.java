package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.utils.FastDFSClient;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.FullAppSettingInfo;
import com.ovit.manager.modules.nswy.entity.UnitInfo;
import com.ovit.manager.modules.nswy.service.FullAppSettingService;
import com.ovit.manager.modules.nswy.service.UnitService;
import com.ovit.manager.modules.nswy.service.UserTypeService;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单位管理
 *
 * @author wangrongf
 * @create 2018-02-09 10:34
 **/
@Controller
@RequestMapping("${adminPath}/nswy")
public class UnitController extends BaseController{
    @Autowired
    private UnitService unitService;


    @RequestMapping(value = "/unit/control")
    public String pictureList(UnitInfo unitInfo, Model model, HttpServletRequest request, HttpServletResponse response){

        Page<UnitInfo> page = unitService.findFullAppSetting(unitInfo, request, response);

        model.addAttribute("page",page);

        return "modules/content/unitList";

    }

    @RequestMapping(value = "/unit/add")
    public String add(Model model){
        List<Map<String,Object>> list = unitService.findUnitType();
        model.addAttribute("list",list);

        return "modules/content/unitAdd";
    }

    @RequestMapping(value = "/unit/saveUnit")
    public String saveUnit( HttpServletRequest request,UnitInfo unitInfo){

        try {

            String unitType = request.getParameter("unitType");

            String unitName = request.getParameter("unitName");

            String calculateUnit = request.getParameter("calculateUnit");

            String modulus = request.getParameter("modulus");


            unitInfo.setUnitType(unitType);

            unitInfo.setUnitTypeName(unitName);

            unitInfo.setCalculateUnit(calculateUnit);

            unitInfo.setModulus(modulus);

            unitService.saveUnit(unitInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/unit/control/";
    }



    @RequestMapping(value = "/unit/update")
    public String update(@RequestParam String unitId,Model model){
        UnitInfo unitInfo = new UnitInfo();
        unitInfo.setUnitId(Integer.valueOf(unitId));
        List<UnitInfo> unitInfos = unitService.findUnitList(unitInfo);
        model.addAttribute("unitInfo",unitInfos.get(0));

        List<Map<String,Object>> list = unitService.findUnitType();
        model.addAttribute("list",list);

        return "modules/content/unitFrom";
    }

    @RequestMapping(value = "/unit/updateUnit")
    public String updateUnit( HttpServletRequest request,UnitInfo unitInfo){

        try {

            String unitType = request.getParameter("unitType");

            unitInfo.setUnitType(unitType);

            unitService.updateUnit(unitInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/unit/control/";
    }


    @RequestMapping(value = "/unit/delete")
    public String deleteUnit(@RequestParam String unitId){

        unitService.deleteUnit(Integer.valueOf(unitId));


        return "redirect:" + adminPath +"/nswy/unit/control/";
    }


}
