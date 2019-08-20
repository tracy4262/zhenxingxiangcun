package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.CommodityInfo;
import com.ovit.manager.modules.nswy.service.CommodityService;
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
@RequestMapping("${adminPath}/nswy")
public class CommodityController extends BaseController {
    @Autowired
    CommodityService commodityService;

    @RequestMapping("/commodity/control")
    public String findList(CommodityInfo commodityInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        Page<CommodityInfo> page = commodityService.findList(commodityInfo,request,response);
        model.addAttribute("page",page);
        return "modules/content/commodityList";
    }

    @RequestMapping(value = "/commodity/update")
    public String update(@RequestParam Integer id, Model model){
        CommodityInfo commodityInfo = new CommodityInfo();
        commodityInfo.setCommodityId(id);
        List<CommodityInfo> commodityInfos = commodityService.findCommodityList(commodityInfo);
        model.addAttribute("commodityInfo",commodityInfos.get(0));

        List<Map<String,Object>> list = commodityService.findCommodity();
        model.addAttribute("list",list);

        return "modules/content/commodityFrom";
    }

    @RequestMapping("/commodity/updateCommodity")
    public String updateCommodity(HttpServletRequest request,CommodityInfo commodityInfo){
        String name = request.getParameter("name");
        String alias = request.getParameter("alias");
        String remark = request.getParameter("remark");

        commodityInfo.setName(name);
        commodityInfo.setAlias(alias);
        commodityInfo.setRemark(remark);

        commodityService.updateCommodity(commodityInfo);
        return "redirect:" + adminPath +"/nswy/commodity/control";
    }

    @RequestMapping(value = "/commodity/add")
    public String add(Model model){
        List<Map<String,Object>> list = commodityService.findCommodity();
        model.addAttribute("list",list);
        return "modules/content/commodityAdd";
    }

    @RequestMapping(value = "/commodity/save")
    public String saveCommodity(HttpServletRequest request,CommodityInfo commodityInfo){
        try {
            String name = request.getParameter("name");
            String alias = request.getParameter("alias");
            String remark = request.getParameter("remark");
            Integer type = Integer.parseInt(request.getParameter("type"));

            commodityInfo.setName(name);
            commodityInfo.setAlias(alias);
            commodityInfo.setRemark(remark);
            commodityInfo.setType(type);
            commodityService.saveCommodity(commodityInfo);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:" + adminPath +"/nswy/commodity/control";
    }

    @RequestMapping("/commodity/delete")
    public String deleteCommodity(@RequestParam Integer id){
        commodityService.deleteByPrimaryKey(id);
        return "redirect:" + adminPath +"/nswy/commodity/control";
    }
}
