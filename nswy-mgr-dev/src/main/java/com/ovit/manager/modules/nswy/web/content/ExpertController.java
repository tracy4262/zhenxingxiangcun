package com.ovit.manager.modules.nswy.web.content;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.LoginUser;
import com.ovit.manager.modules.nswy.service.ExpertService;
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

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* DESCRIPTION:
* 内容控制 专家 user_type = 4的是专家
* @author zouyan
* @create 2018/2/8-上午11:22
* created by fuck~
**/
@Controller
@RequestMapping("${adminPath}/nswy")
public class ExpertController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(ExpertController.class);

    @Autowired
    private ExpertService expertService;
    @Autowired
    private NswyCorpInfoService nswyCorpInfoService;

    @RequestMapping("/expert/control")
    public String enterpriseList(LoginUser loginUser, Model model, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("加载专家列表");
        loginUser.setUserType("4");
        Page<LoginUser> page = expertService.findList(loginUser, request, response);
        logger.trace("专家列表page:{}", page);
        model.addAttribute("page",page);
        if (StringUtils.isNotEmpty(loginUser.getDisplayName())) {
            model.addAttribute("displayName", loginUser.getDisplayName());
        }
        return "modules/content/expertList";
    }

    @RequestMapping("/expertDelete")
    public String expertDelete(@RequestParam Integer id){
        logger.debug("通过专家id删除记录, id：{}", id);
        expertService.deleteByPrimaryKey(id);
        return "redirect:" + adminPath +"/nswy/expert/control/";
    }

    /**
     * 设置该条内容是否在无忧首页显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/expert/update-home",method = RequestMethod.POST)
    public String expertUpdateHome(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        expertService.expertUpdateHome(map);
        return "success";
    }

    /**
     * 设置该条内容是否在无忧导航显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/expert/update-nav-display",method = RequestMethod.POST)
    public String expertUpdateNavDisplay(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        expertService.expertUpdateNavDisplay(map);
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/expert/set-order-num",method = RequestMethod.POST)
    public String expertSetOrderNum(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",request.getParameter("orderNum"));
        map.put("id",request.getParameter("id"));
        expertService.expertSetOrderNum(map);
        return "success";
    }
}
