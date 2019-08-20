package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.InformationService;
import com.ovit.nswy.member.service.MydynamicService;
import com.ovit.nswy.member.service.SpecieService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mydynamic")
public class MydynamicController {

    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private MydynamicService mydynamicService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private SpecieService service;

    //我的动态
    @RequestMapping(value = "/findMydynamic/{page}", method = RequestMethod.GET)
    public Result findMydynamic(@PathVariable String page, HttpServletRequest request) {
        String account = request.getParameter("uid");
        if (!StringUtils.hasText(account)) {
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            account = loginUser.getLoginAccount();
        }
        String label = request.getParameter("label");
        Map<String, String> map1 = new HashMap<String, String>();
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
        map1.put("account", account);
        map1.put("label", label);
        PageInfo<MyDyNamic> list = mydynamicService.findMyDyNamic(currentPage, pageSize, map1);
        Result result = new Result();
        result.setData(list);
        return result;
    }

    /**
     * 关注动态
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "/followMydynamic/{page}", method = RequestMethod.GET)
    public Result followMydynamic(@PathVariable String page, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        List<Species> speciesList = service.findFollowedSpecies(loginUser.getLoginAccount());
        String label = request.getParameter("label");
        Map<String, String> map1 = new HashMap<String, String>();
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
        map1.put("account", loginUser.getLoginAccount());
        map1.put("label", label);
        PageInfo<MyDyNamic> list = mydynamicService.findMyDyNamic(currentPage, pageSize, map1);
        Result result = new Result();
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/findMydynamicAsc/{page}", method = RequestMethod.GET)
    public Result findMydynamicAsc(@PathVariable String page, HttpServletRequest request) throws Exception {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        String label = request.getParameter("label");
        Map<String, String> map1 = new HashMap<String, String>();
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
        map1.put("account", loginUser.getDisplayName());
        map1.put("label", label);
        PageInfo<MyDyNamic> list = mydynamicService.findMyDyNamicAsc(currentPage, pageSize, map1);
        Result result = new Result();
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/saveMydynamic", method = RequestMethod.POST)
    public Result saveMydynamic(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        MyDyNamic myDynamic = new MyDyNamic();
        myDynamic.setAccount(loginUser.getLoginAccount());
        myDynamic.setAuthor(loginUser.getDisplayName());
        myDynamic.setTitle(params.get("title").toString());
        myDynamic.setType(params.get("type").toString());
        myDynamic.setCategory(params.get("category").toString());
        myDynamic.setSource(params.get("source").toString());
        myDynamic.setDistrict(params.get("district").toString());
        myDynamic.setGoodsname(params.get("goodsname").toString());
        myDynamic.setServicename(params.get("servicename").toString());
        myDynamic.setSpecies(params.get("species").toString());
        myDynamic.setSummary(params.get("summary").toString());
        myDynamic.setIndustry(params.get("industry").toString());
        myDynamic.setContent(params.get("content").toString());
        myDynamic.setLabel(params.get("custom").toString());
        myDynamic.setSecurity(params.get("security").toString());
        myDynamic.setThumbUpNum(0);
        Result result = new Result();
        try {
            mydynamicService.saveMyDyNamic(myDynamic);
            result.setData(1);
            // TODO
            params.put("creator", loginUser.getDisplayName());
            params.put("creatorId", loginUser.getLoginAccount());
            publishToInfomation(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 点赞
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveZan", method = RequestMethod.GET)
    public Result saveZan(HttpServletRequest request) {
        Result result = new Result();
        try {
            Integer id = Integer.valueOf(request.getParameter("id").toString());
            Integer thumbUpNum = Integer.valueOf(request.getParameter("thumbUpNum").toString());
            mydynamicService.updateMyDyNamic(id, thumbUpNum);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 回复
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveHuiFu", method = RequestMethod.POST)
    public Result saveHuiFu(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        MydynamicComment report = new MydynamicComment();
        report.setAccount(loginUser.getLoginAccount());
        report.setDynamicId(Integer.valueOf(params.get("dynamicId").toString()));
        report.setComment(params.get("comment").toString());
        Result result = new Result();
        try {
            mydynamicService.saveMyDyNamicComment(report);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/findFollow/{page}", method = RequestMethod.GET)
    public Result findFollow(@PathVariable String page, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        Map<String, Object> map = new HashMap<String, Object>();
        int currentPage = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(request.getParameter("pageSize"));
        PageInfo<MyFollow> inforMation = mydynamicService.findFollowInforMation(currentPage, pageSize,
                loginUser.getLoginAccount());
        PageInfo<MyFollow> knowLedge = mydynamicService.findFollowKnowLedge(currentPage, pageSize,
                loginUser.getLoginAccount());
        PageInfo<MyFollow> policy = mydynamicService.findFollowPolicy(currentPage, pageSize,
                loginUser.getLoginAccount());
        map.put("inforMation", inforMation);
        map.put("knowLedge", knowLedge);
        map.put("policy", policy);
        Result result = new Result();
        result.setData(map);
        return result;
    }

    private void publishToInfomation(Map<String, Object> params) {
        String type = params.get("type").toString();
        String category = params.get("category").toString();
        String source = params.get("source").toString();
        String district = params.get("district").toString();
        String goods = params.get("goodsname").toString();
        String service = params.get("servicename").toString();
        String species = params.get("species").toString();
        String summary = params.get("summary").toString();
        String industry = params.get("industry").toString();
        String content = params.get("content").toString();
        String label = params.get("custom").toString();
        String creator = params.get("creator") == null ? "" : params.get("creator").toString();
        String title = params.get("title").toString();
        InformationDetail informationDetail = new InformationDetail();
        informationDetail.setTitle(title);
        informationDetail.setCreateTime(new Date());
        informationDetail.setSource(source); //来源
        informationDetail.setBrowseNumber(0); //评论次数
        informationDetail.setContent(content); //文章内容
        informationDetail.setEditer(creator); //创建者 编辑
        informationDetail.setLabel(label);
        informationService.insertInformationDetail(informationDetail);

        Information information = new Information();
        information.setInformationDetailId(informationDetail.getId());
        information.setTitle(title); //咨询标题
//        information.setImageAdd(article.getImage());
        information.setCreateTime(informationDetail.getCreateTime());
        information.setCommentNum(informationDetail.getBrowseNumber()); //评论次数
        information.setLabel(label); //标签 关键字
//        information.setBusinessInformation();	//商务咨询
//        information.setAllRange(article.getAllRange());	//所有范围
//        information.setBusinessNature(article.getBusinessNature());	//行业性质
        information.setAllBusiness(industry);    //所有行业
//        information.setIndustrialId(article.getIndustrialClassified().getFid());
        information.setDocType(category);

        Object abstracts = params.get("summary");
        if (null != abstracts) {
            information.setAbstracts(abstracts.toString());
        }
        Object classifiedId = params.get("category");
        if (null != classifiedId) {
            information.setClassifiedId(classifiedId.toString());
        }
        String xianshi = params.get("xianshi").toString();
        if (!"是".equals(xianshi)) {
            information.setIsToPage(1);
        }
        information.setAuthor((String) params.get("creatorId"));
        informationService.insertInfo(information);
    }

}