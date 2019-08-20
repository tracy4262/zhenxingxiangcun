package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.json.JSONHelper;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.InformationBookDetailService;
import com.ovit.nswy.member.service.InformationBookService;
import com.ovit.nswy.member.service.PolicyService;
import com.ovit.nswy.member.service.VideoImgService;
import com.ovit.nswy.member.util.DateUtil;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/policy")
public class PolicyController {

    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private PolicyService policyService;
    @Autowired
    private VideoImgService videoImgService;
    @Autowired
    private InformationBookService informationBookService;

    @Autowired
    private InformationBookDetailService informationBookDetailService;



    /**
     * 最新知识
     */
    @RequestMapping(value = "/newpolicy", method = RequestMethod.GET)
    public Result newknowledge() {
        List<PolicyDetail> list = policyService.newpolicy();
        Result result = new Result();
        result.setData(list);
        return result;
    }

    /**
     * 政策查询分页
     *
     * @param page
     * @return @
     */
    @RequestMapping(value = "/findPolicy/{page}", method = RequestMethod.GET)
    public Result findPolicy(@PathVariable String page, HttpServletRequest request) {
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
        PageInfo<Policy> lists = policyService.findPolicy(currentPage, pageSize);
        List<Policy> list = lists.getList();
        lists.setList(list);
        Result result = new Result();
        result.setData(lists);
        return result;
    }

    /**
     * 根据ID查询详细信息
     *
     * @param
     */
    @RequestMapping(value = "/findPolicyDetail", method = RequestMethod.GET)
    public Result findPolicyDetail(HttpServletRequest request) {
        Result result = new Result();
        try{
            String id = request.getParameter("id");
            if (!StringUtils.isEmpty(id)) {
                List<Map<String,Object>> policyDetails = policyService.findPolicyDetail(id);
                Map<String,Object> policyDetail = policyDetails.get(0);
                //遍历视频、音频
                HttpSession session = request.getSession();
                LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
                Map<String,Object> vmap=new HashMap<>();
                /*if(loginUser !=null){
                    String account = loginUser.getLoginAccount();
                    vmap.put("account",account);
                }else{
                    vmap.put("account",null);
                }*/
                vmap.put("column","政策");
                vmap.put("relativeId",id);
                List<VideoImg> videoImgs=videoImgService.selectByRelativeId(vmap);
                if(!videoImgs.isEmpty()){
                    policyDetail.put("videoImgs",videoImgs);
                }
                result.setData(policyDetail);
            }
        }catch(Exception e){
            logger.info("政策详情查询出错 -->{}" +e.getMessage());
            result.setCode(500);
        }
        return result;
    }

    /**
     * 搜索查询分页
     *
     * @param page
     * @return @
     */
    @RequestMapping(value = "/findPolicyTitle/{page}", method = RequestMethod.GET)
    public Result findPolicyTitle(@PathVariable String page, HttpServletRequest request) {
        Result result = null;
        try {
            result = new Result();
            Policy info = new Policy();
            info.setTitle(request.getParameter("title"));
            info.setClassifiedId(request.getParameter("type"));
            info.setColumnType(request.getParameter("leixin"));
            info.setService(request.getParameter("servicename"));
            info.setProduct(request.getParameter("goodname"));
            info.setIndustrialId(request.getParameter("industry"));
            info.setSpeciesId(request.getParameter("species"));
            info.setAllRange(request.getParameter("district"));
            info.setDocType(request.getParameter("docType"));
            logger.info("/findPolicyTitle，查询参数：{}",info);
            int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
            PageInfo<Policy> lists = policyService.findPolicyTitle(currentPage, info, 10);
            logger.debug("/findPolicyTitle：{}，查询结果：{}",lists.getList());
            result.setData(lists);
        } catch (NumberFormatException e) {
            logger.error("/findPolicyTitle，查询失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/findPolicyComment/{page}", method = RequestMethod.GET)
    public Result findPolicyComment(@PathVariable String page, HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        Map<String, Object> map = new HashMap<String, Object>();
        Result result = new Result();
        if (!StringUtils.isEmpty(id)) {
            int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
            PageInfo<PolicyComment> list = policyService.findPolicyComment(currentPage, 10, id);
            Integer tote = policyService.findPolicyCommentCount(id);
            map.put("list", list);
            map.put("tote", tote);
            result.setData(map);
        }
        return result;
    }

    @RequestMapping(value = "/findKnowLedgeComments", method = RequestMethod.GET)
    public Result findPolicyComments(HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        String label = request.getParameter("label");
        Integer id = Integer.parseInt(request.getParameter("id"));
        map1.put("account", loginUser.getLoginAccount());
        map1.put("label", label);
        map1.put("id", id);
        List<PolicyComment> list = policyService.findPolicyComments(map1);
        map.put("list", list);
        Result result = new Result();
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/findMyComment/{page}", method = RequestMethod.GET)
    public Result findMyComment(@PathVariable String page, HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        Result result = new Result();
        if (loginUser == null) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            return result;
        }
        PolicyComment report = new PolicyComment();
        report.setUserAccount(loginUser.getLoginAccount());
        report.setInformationDetailId(id);
        if (!StringUtils.isEmpty(id)) {
            int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
            PageInfo<PolicyComment> list = policyService.findMyComment(currentPage, 10, report);
            result.setData(list);
        }
        return result;
    }

    /**
     * 评论下面的点赞
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveZan", method = RequestMethod.GET)
    public Result saveZan(HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        PolicyComment report = new PolicyComment();
        Integer id = Integer.valueOf(request.getParameter("id").toString());
        report.setId(id);
        Result result = new Result();
        try {
            PolicyComment policyComment = policyService.update(report);
            map.put("flag", 1);
            map.put("thumbUpNum", policyComment.getThumbUpNum());
            result.setData(map);
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
        PolicyComment report = new PolicyComment();
        Object postid = params.get("postId");
        if (null != postid) {
            report.setPostId(Integer.valueOf(postid.toString()));
        } else {
            report.setPostId(0);
        }
        String dyid=params.get("dynamicId").toString();
        if(StringUtils.hasText(dyid)){
            report.setInformationDetailId(Integer.valueOf(dyid));
        }else {
            report.setInformationDetailId(0);
        }
        report.setContent(params.get("comment").toString());
        report.setUserAccount(String.valueOf(params.get("account")));
        report.setUserName(String.valueOf(params.get("authorName")));
        report.setThumbUpNum(0);
        report.setCreateTime(new Date());
        Result result = new Result();
        try {
            policyService.insert(report);
            result.setData(1);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    //点赞
    @RequestMapping(value = "/upvote", method = RequestMethod.GET)
    public Result upvote(HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Result result = new Result();
        try {
            Integer id = Integer.valueOf(request.getParameter("id").toString());
            PolicyDetail policyDetail = policyService.upvote(id);
            map.put("flag", 1);
            map.put("thumbUpNum", policyDetail.getThumbUpNum());
            result.setData(map);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/findIndexPolicy/{page}", method = RequestMethod.GET)
    public Result findIndexPolicy(@PathVariable String page, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
        // 政策法规
        PageInfo<Policy> regulations = policyService.findIndexPolicy(currentPage, 12, "030201");
        // 政策解读
        PageInfo<Policy> interpretation = policyService.findIndexPolicy(currentPage, pageSize, "030202");
        // 通知公告
        PageInfo<Policy> notice = policyService.findIndexPolicy(currentPage, pageSize, "030203");
        // 党务动态
        PageInfo<Policy> partyworkdynamic = policyService.findIndexPolicy(currentPage, pageSize, "030209");
        // 政务动态
        PageInfo<Policy> groverworkdynamic = policyService.findIndexPolicy(currentPage, pageSize, "030210");

        map.put("regulations", regulations);
        map.put("interpretation", interpretation);
        map.put("notice", notice);
        map.put("partyworkdynamic", partyworkdynamic);
        map.put("groverworkdynamic", groverworkdynamic);
        Result result = new Result();
        result.setData(map);
        return result;
    }

    /**
     * 政策发布
     */
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public Result publish(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        //LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        Result result = new Result();
        try {
            PolicyDetail detail = new PolicyDetail();
            detail.setTitle(params.get("title").toString());
            detail.setAccount(params.get("account").toString());
            detail.setCreateTime(new Date());
            detail.setSource(params.get("source").toString());
            detail.setEditer(params.get("author").toString());
            detail.setContent(params.get("content").toString());
            detail.setLabel(params.get("custom").toString());
            detail.setYcAuthor(params.get("ycauthor").toString());
            int num = policyService.insertPolicyDetail(detail);

            //保存视频、音频
            List<Map<String, Object>> object = (List<Map<String, Object>>) params.get("video");
            List<VideoImg> videoImgs=new ArrayList<>();
            if(!object.isEmpty()){
                for(Map<String, Object> map : object){
                    VideoImg videoImg=new VideoImg();
                    videoImg.setAccount(params.get("account").toString());
                    videoImg.setAddr((String) map.get("url"));
                    videoImg.setType((Integer)map.get("type"));
                    videoImg.setColumn((String)map.get("column"));
                    if(StringUtils.hasText((String)map.get("describe"))){
                        videoImg.setDescribe((String)map.get("describe"));
                    }
                    videoImg.setRelativeId(detail.getId());
                    videoImg.setMediaName((String)map.get("musicName"));
                    videoImgs.add(videoImg);
                }
                videoImgService.insert(videoImgs);
            }

            Policy policy = new Policy();
            String doctype=(String)params.get("leixin");
            String content=(String)params.get("content");
            if("图册".equals(doctype)){
                policy.setImageAdd(content.substring(0,content.indexOf(">")+1));
            }
            if("视频".equals(doctype)) {
                policy.setImageAdd(videoImgs.get(0).getAddr());
            }
            policy.setColumnType(doctype);
            policy.setLabel(params.get("custom").toString());
            policy.setInformationDetailId(detail.getId());
            policy.setTitle(params.get("title").toString()); //咨询标题
            policy.setCreateTime(detail.getCreateTime());
            policy.setCommentNum(detail.getBrowseNumber()); //评论次数
            policy.setLabel(params.get("custom").toString()); //标签 关键字
            policy.setDocType(params.get("category").toString());
            policy.setExpert((String)params.get("expert"));
            policy.setCompany((String)params.get("company"));
            policy.setImageAdd((String)params.get("imageAdd"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(StringUtils.hasText((String)params.get("promulgation"))){
                policy.setPublishTime(sdf.parse((String)params.get("promulgation")));
            }
            policy.setIssuedNo((String)params.get("font"));

            if(StringUtils.hasText((String)params.get("species"))){
                policy.setSpeciesName((String)params.get("species"));
            }
            if(StringUtils.hasText(params.get("speciesId").toString()))// 物种名称
                policy.setSpeciesId(params.get("speciesId").toString());
            if(StringUtils.hasText((String)params.get("goodsname"))){
                policy.setProduct((String)params.get("goodsname"));
            }
            if(StringUtils.hasText((String)params.get("servicename"))){
                policy.setService((String)params.get("servicename"));
            }
            if(StringUtils.hasText((String)params.get("industry"))){
                policy.setIndustrialId((String)params.get("industry"));//行业分类
            }
            policy.setAllRange((String)params.get("district"));
            policy.setFriendReminding((String)params.get("tixing"));
            policy.setRemindingContent((String)params.get("con"));
            policy.setIsSms("是".equals((String)params.get("sms")) ? 0 : 1 );
            policy.setIsPublish((Integer) params.get("security"));
            policy.setComponsation((String)params.get("money"));

            Object abstracts = params.get("summary");
            if(null != abstracts){
                policy.setAbstracts(abstracts.toString());
            }
            Object classifiedId = params.get("type");
            if(null != classifiedId){
                policy.setClassifiedId(classifiedId.toString());
            }
            String xianshi = params.get("xianshi").toString();
            if(!"是".equals(xianshi)){
                policy.setIsToPage(1);
            }else{
                policy.setIsToPage(0);
            }
            policy.setAuthor(params.get("account").toString());
            policyService.insertPolicy(policy);


            //若发布的为资讯-图书 图书单独存储至information_book_info
            if (MapUtils.getString(params, "isBook") != null) {
                Map<String, Object> bookMap = new HashMap<String, Object>();
                bookMap.put("informationDetailId", detail.getId());
                bookMap.put("coverPhoto", MapUtils.getString(params, "book_cover_photo"));
                bookMap.put("bookFullPhoto", MapUtils.getString(params, "book_full_photo"));
                bookMap.put("author", MapUtils.getString(params, "book_author"));
                bookMap.put("edition", MapUtils.getString(params, "book_edition"));
                bookMap.put("publish", MapUtils.getString(params, "book_publish"));
                bookMap.put("sheet", MapUtils.getString(params, "book_sheet"));
                bookMap.put("distribution", MapUtils.getString(params, "book_distribution"));
                bookMap.put("broadsheet", MapUtils.getString(params, "book_broadsheet"));
                bookMap.put("printTime", MapUtils.getString(params, "book_print_time"));
                bookMap.put("wordCount", MapUtils.getString(params, "book_word_count"));
                bookMap.put("pubDate", MapUtils.getString(params, "book_pub_date"));
                bookMap.put("paper", MapUtils.getString(params, "book_paper"));
                bookMap.put("label", MapUtils.getString(params, "book_label"));
                bookMap.put("bookType", "policy");
                informationBookService.insert(bookMap);
                //System.out.println(bookMap.get("ID"));
                logger.debug("新增资讯-图书发布，成功插入数据至bookInfo, id:{}", bookMap.get("ID"));

                //存储图书详情
                int book_info_id = Integer.parseInt(String.valueOf(bookMap.get("ID")));
                List<Map<Object, Object>> bookDetails = JSONHelper.parseObject(params.get("book_data").toString(), List.class);

                int count = 0;
                if (bookDetails != null && bookDetails.size()>0) {
                    //图书的章节数量
                    int ChapterSize = bookDetails.size();
                    List<Map<String, Object>> saveBookDetailList = new ArrayList<>();

                    for (int i = 0; i < ChapterSize; i++) {
                        //每章图书的信息
                        Map<Object, Object> chapterInfo = bookDetails.get(i);

                        JSONArray jsonArray = (JSONArray) chapterInfo.get("children");

                        //添加每章的信息
                        String expand = String.valueOf(chapterInfo.get("expand"));
                        String title = String.valueOf(chapterInfo.get("title"));
                        String edit = String.valueOf(chapterInfo.get("edit"));
                        String checked = String.valueOf(chapterInfo.get("checked"));
                        int node_id = ++count;
                        int order_id = 0;
                        int parent_id = 0;

                        //保存图书每章的信息
                        Map<String, Object> saveChapterInfo = new HashMap<>();
                        saveChapterInfo.put("information_detail_id", detail.getId());
                        saveChapterInfo.put("book_info_id", book_info_id);
                        saveChapterInfo.put("title", title);
                        saveChapterInfo.put("edit", edit);
                        saveChapterInfo.put("checked", checked);
                        saveChapterInfo.put("expand", expand);
                        saveChapterInfo.put("node_id", node_id);
                        saveChapterInfo.put("parent_id", parent_id);
                        saveChapterInfo.put("order_id", order_id);
                        saveChapterInfo.put("content", "");
                        saveChapterInfo.put("file", "");
                        saveChapterInfo.put("file_name","");
                        saveChapterInfo.put("bookType", "policy");
                        saveBookDetailList.add(saveChapterInfo);

                        //章节的node_id
                        int chapter_id = count;
                        //每章的小节数量
                        int partSize = jsonArray.size();
                        for (int j = 0; j < partSize; j++) {

                            //每章图书的信息
                            JSONObject partInfo = (JSONObject) jsonArray.get(j);

                            String bookContent = "";
                            String file = "";

                            //图书每节的信息
                            title = String.valueOf(partInfo.get("title"));
                            edit = String.valueOf(partInfo.get("edit"));
                            checked = String.valueOf(partInfo.get("checked"));
                            node_id = ++count;
                            parent_id = chapter_id;
                            order_id = j + 1;
                            bookContent = String.valueOf(partInfo.get("content"));
                            String file_name = "";
                            String file_url = "";
                            JSONArray bookFile = (JSONArray) partInfo.get("file");
                            if (bookFile.size() > 0) {
                                JSONObject jsonObject = (JSONObject) bookFile.get(0);
                                file_name = jsonObject.getString("name");
                                file_url = jsonObject.getString("src");
                            }
                            //List<String> fileList = bookFile.toJavaList(String.class);
                            //file = org.apache.commons.lang.StringUtils.join(fileList.toArray(), ';');

                            //保存图书信息
                            Map<String, Object> savePartInfo = new HashMap<>();
                            savePartInfo.put("information_detail_id", detail.getId());
                            savePartInfo.put("book_info_id", book_info_id);
                            savePartInfo.put("title", title);
                            savePartInfo.put("edit", edit);
                            savePartInfo.put("checked", checked);
                            savePartInfo.put("expand", expand);
                            savePartInfo.put("node_id", node_id);
                            savePartInfo.put("parent_id", parent_id);
                            savePartInfo.put("order_id", order_id);
                            savePartInfo.put("content", bookContent.getBytes());
                            savePartInfo.put("file",file_url);
                            savePartInfo.put("file_name",file_name);
                            savePartInfo.put("bookType", "policy");
                            saveBookDetailList.add(savePartInfo);

                        }
                    }
                    //批量插入图书信息,先删除，后插入
                    informationBookDetailService.batchInsert(saveBookDetailList, detail.getId(), "policy");
                    result.setMsg("数据新增成功");
                }
            }

            result.setData(num);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 回复成功后的回调
     */
    @RequestMapping(value = "/replylist", method = RequestMethod.POST)
    public Result replyList(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        try {
            String account = ((LoginUser) request.getSession().getAttribute("currentUser")).getLoginAccount();
            String page = params.get("currentPage").toString();
            int currentPage = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
            Map<String, String> map = new HashMap<>();
            map.put("userAccount", account);
            map.put("informationDetailId", params.get("replyid").toString());
            PageInfo<PolicyComment> pageInfo = policyService.replylist(currentPage, 10, map);
            result.setData(pageInfo.getList());
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 回复他人的评论
     */
    @RequestMapping(value = "/replyChildrenlist", method = RequestMethod.POST)
    public Result replyChildrenList(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        try {
            String account = ((LoginUser) request.getSession().getAttribute("currentUser")).getLoginAccount();
            String page = params.get("currentPage").toString();
            int currentPage = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
            Integer postid=Integer.parseInt(params.get("postId").toString());
            PageInfo<PolicyComment> pageInfo = policyService.replyToOther(currentPage,10,postid);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     *  无忧导航-政策-简讯
     */
    @RequestMapping(value = "/brief-news",method = RequestMethod.POST)
    public Result getBriefNews() {
        Result result = new Result();
        try {
            List<Map<String, Object>> list = policyService.getBriefNews();
            result.setData(list);
            result.setCode(ResultCode.SUCCESS);
            logger.info("查询成功{}", list);
        } catch (Exception e) {
            logger.error("查询失败：{}", e);
            result.setCode(ResultCode.FAIL);
            return result;
        }
        return result;
    }
    /**
     * 政策的上一页下一页
     *
     */
    @RequestMapping(value = "/upAndNextPage/{id}",method = RequestMethod.GET)
    public Result upAndNextPage(@PathVariable Integer id) {
        logger.info("政策id-->{}", id);
        Result result = new Result();
        try {
            Map<String, Object> map = policyService.findUpAndNextPage(id);
            result.setData(map);
            result.setCode(ResultCode.SUCCESS);
            logger.info("上一页-下一页--{}", map);
        } catch (Exception e) {
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            return result;
        }
        return result;
    }



}
