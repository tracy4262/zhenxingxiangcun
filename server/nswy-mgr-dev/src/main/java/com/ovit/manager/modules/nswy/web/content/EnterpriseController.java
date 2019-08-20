package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.NswyCorpInfo;
import com.ovit.manager.modules.nswy.service.CorpInfoService;
import com.ovit.manager.modules.nswy.service.NswyCorpInfoService;
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
* 企业
* @author zouyan
* @create 2018/2/8-上午11:22
* created by fuck~
**/
@Controller
@RequestMapping("${adminPath}/nswy")
public class EnterpriseController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(EnterpriseController.class);

    @Autowired
    private CorpInfoService corpInfoService;

    @Autowired
    private NswyCorpInfoService nswyCorpInfoService;


    @RequestMapping("/enterprise/control")
    public String enterpriseList(NswyCorpInfo nswyCorpInfo, Model model, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("加载企业列表");
        Page<NswyCorpInfo> page = corpInfoService.findNswyCorpInfolist(nswyCorpInfo, request, response);
        logger.trace("企业列表page:{}", page);
        model.addAttribute("page",page);
        if (StringUtils.isNotEmpty(nswyCorpInfo.getCorpName())) {
            model.addAttribute("corpName", nswyCorpInfo.getCorpName());
        }
        return "modules/content/corpList";
    }

    @RequestMapping("/enterpriseDelete")
    public String deleteEnterprise(@RequestParam Integer corpInfoId){
        logger.debug("通过企业id删除记录, corpInfoId：{}", corpInfoId);
        corpInfoService.deleteCorpInfo(corpInfoId);
        return "redirect:" + adminPath +"/nswy/enterprise/control/";
    }


    /**
     * 设置该条内容是否在无忧首页显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/enterprise/update-home",method = RequestMethod.POST)
    public String corpInfoUpdateHome(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        corpInfoService.corpInfoUpdateHome(map);
        return "success";
    }

    /**
     * 设置该条内容是否在无忧导航显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/enterprise/update-nav-display",method = RequestMethod.POST)
    public String corpInfoUpdateNavDisplay(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        corpInfoService.corpInfoUpdateNavDisplay(map);
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/enterprise/set-order-num",method = RequestMethod.POST)
    public String corpInfoSetOrderNum(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",request.getParameter("orderNum"));
        map.put("id",request.getParameter("id"));
        corpInfoService.corpInfoSetOrderNum(map);
        return "success";
    }

}
