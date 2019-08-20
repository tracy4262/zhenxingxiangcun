package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.NswyGovInfo;
import com.ovit.manager.modules.nswy.service.GovInfoService;
import com.ovit.manager.modules.nswy.service.NswyCorpInfoService;
import com.ovit.manager.modules.nswy.service.NswyGovInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
* DESCRIPTION:
* 机关
* @author zouyan
* @create 2018/2/8-上午11:22
* created by fuck~
**/
@Controller
@RequestMapping("${adminPath}/nswy")
public class GovernmentController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(GovernmentController.class);

    @Autowired
    private GovInfoService govInfoService;

    @Autowired
    private NswyCorpInfoService nswyCorpInfoService;

    @RequestMapping("/government/control")
    public String enterpriseList(NswyGovInfo nswyGovInfo, Model model, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("加载机关列表");
        Page<NswyGovInfo> page = govInfoService.findList(nswyGovInfo, request, response);
        logger.trace("机关列表page:{}", page);
        model.addAttribute("page",page);
        if (StringUtils.isNotEmpty(nswyGovInfo.getGovName())) {
            model.addAttribute("govName", nswyGovInfo.getGovName());
        }
        return "modules/content/govList";
    }

    @RequestMapping("/governmentDelete")
    public String deleteEnterprise(@RequestParam Integer govInfoId){
        logger.debug("通过机关id删除记录, govInfoId：{}", govInfoId);
        govInfoService.deleteGovInfo(govInfoId);
        return "redirect:" + adminPath +"/nswy/government/control/";
    }

    /**
     * 设置该条内容是否在无忧首页显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/government/update-home",method = RequestMethod.POST)
    public String corpInfoUpdateHome(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        govInfoService.govInfoUpdateHome(map);
        return "success";
    }

    /**
     * 设置该条内容是否在无忧导航显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/government/update-nav-display",method = RequestMethod.POST)
    public String govInfoUpdateNavDisplay(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        govInfoService.govInfoUpdateNavDisplay(map);
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/government/set-order-num",method = RequestMethod.POST)
    public String govInfoSetOrderNum(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",request.getParameter("orderNum"));
        map.put("id",request.getParameter("id"));
        govInfoService.govInfoSetOrderNum(map);
        return "success";
    }
}
