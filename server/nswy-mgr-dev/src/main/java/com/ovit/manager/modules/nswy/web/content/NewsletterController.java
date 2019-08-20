package com.ovit.manager.modules.nswy.web.content;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.nswy.entity.NewsletterInfo;
import com.ovit.manager.modules.nswy.service.NewsletterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 简讯
 *
 * @author haoWen
 * @create 2018-02-08 10:47
 **/
@Controller
@RequestMapping("${adminPath}/nswy")
public class NewsletterController extends BaseController{
    @Autowired
    private NewsletterService newsletterService;

    /**
     * 简讯列表查询
     * @param newsletterInfo
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/newsletterList")
    public String newsletterList(NewsletterInfo newsletterInfo, Model model, HttpServletRequest request, HttpServletResponse response) {
        Page<NewsletterInfo> page = newsletterService.findKnowledgeList(newsletterInfo, request, response);
        model.addAttribute("page",page);
        if (StringUtils.isNotEmpty(newsletterInfo.getTitle())) {
            model.addAttribute("title", newsletterInfo.getTitle());
        }
        return "modules/content/newsletterList";
    }

    /**
     * 删除简讯
     * @return
     */
    @RequestMapping(value = "/deleteNewsletter",method = RequestMethod.GET)
    public String deleteNewsletter(int id,String type){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("TABLE_NAME",getTableName(type));
        newsletterService.deleteNewsletter(map);
        return "redirect:" + adminPath +"/nswy/newsletterList/";
    }

    /**
     * 设置简讯是否在无忧导航显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "newsletterUpdateNavDisplay",method = RequestMethod.POST)
    public String newsletterUpdateNavDisplay(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("status",request.getParameter("status"));
        map.put("id",request.getParameter("id"));
        //根据type判断所操作的表
        String type = request.getParameter("type");
        map.put("TABLE_NAME",getTableName(type));
        newsletterService.newsletterUpdateNavDisplay(map);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/newsletterSetOrderNum",method = RequestMethod.POST)
    public String newsletterSetOrderNum(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",request.getParameter("orderNum"));
        map.put("id",request.getParameter("id"));
        String type = request.getParameter("type");
        map.put("TABLE_NAME",getTableName(type));
        newsletterService.newsletterSetOrderNum(map);
        return "success";
    }

    private String getTableName(String type){
        String tableName = null;
        if ("知识".equals(type)) {
            tableName = "knowledge";
        } else if ("资讯".equals(type)) {
            tableName = "information";
        } else if ("政策".equals(type)) {
            tableName = "policy";
        }
        return tableName;
    }
}
