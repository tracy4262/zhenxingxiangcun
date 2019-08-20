package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.date.DateFormat;
import com.github.xphsc.json.JSONHelper;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.InformationBookDetailService;
import com.ovit.nswy.member.service.InformationBookService;
import com.ovit.nswy.member.service.InformationService;
import com.ovit.nswy.member.service.VideoImgService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import com.qq.connect.utils.json.JSONString;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/inforMation")
public class InformationController {

    /**
     *
     */
    private static final Logger logger = LogManager.getLogger(InformationController.class);
    @Autowired
    private InformationService informationService;

    @Autowired
    private VideoImgService videoImgService;

    @Autowired
    private InformationBookService informationBookService;

    @Autowired
    private InformationBookDetailService informationBookDetailService;

    /**
     * 资讯查询（分页）
     *
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findInforMation/{page}", method = RequestMethod.GET)
    public Result findInforMation(@PathVariable String page, HttpServletRequest request) throws Exception {
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
        PageInfo<Information> lists = informationService.findInForMation(currentPage, pageSize);
        List<Information> list = lists.getList();
        /*for (Information info : list) {
            long newTime = (new Date().getTime()) - (info.getCreateTime().getTime());
            info.setDay(DateUtil.getDay(newTime));
        }*/
        lists.setList(list);
        Result result = new Result();
        result.setData(lists);
        return result;
    }

    /**
     * 最新资讯
     */
    @RequestMapping(value = "/newinfo", method = RequestMethod.GET)
    public Result newinfo() {
        List<InformationDetail> list = informationService.newinfo();
        Result result = new Result();
        result.setData(list);
        return result;
    }

    /**
     * 根据ID查询详细信息
     *
     * @param
     */
    @RequestMapping(value = "/findInforMationDetail", method = RequestMethod.GET)
    public Response findInforMationDetail(HttpServletRequest request) {
        Response result=new Response();
            String id = request.getParameter("id");
            if (!StringUtils.isEmpty(id)) {
                List<Map<String,Object>> informationDetails = informationService.findInForMationDetail(id);
                Map<String,Object> informationDetail= informationDetails.get(0);
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
                vmap.put("column","动态");
                vmap.put("relativeId",id);
                List<VideoImg> videoImgs=videoImgService.selectByRelativeId(vmap);
                if(!videoImgs.isEmpty()){
                    informationDetail.put("videoImgs",videoImgs);
                }
                //result.setData(informationDetail);
                result= Response.successResult(
                        informationDetail,
                        DateFormat.DATE_FORMAT_SEC);
            }
           return result;
    }

    /**
     * 展示咨询
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public Result findInformation(HttpServletRequest request) {
        String docType = request.getParameter("type");
        List<Information> information = informationService.find(docType);
        Result result = new Result();
        result.setData(information);
        return result;
    }

    /**
     * 搜索查询分页
     *
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findInforMationTitle/{page}", method = RequestMethod.GET)
    public Result findInformationTitle(@PathVariable String page, HttpServletRequest request) {
        Result result = null;
        try {
            Information info = new Information();
            result = new Result();
            info.setTitle(request.getParameter("title"));
            info.setClassifiedId(request.getParameter("type"));
            info.setColumnType(request.getParameter("leixin"));
            info.setService(request.getParameter("servicename"));
            info.setProduct(request.getParameter("goodname"));
            info.setIndustrialId(request.getParameter("industry"));
            info.setSpeciesId(request.getParameter("species"));
            info.setAllRange(request.getParameter("district"));
            info.setDocType(request.getParameter("docType"));
            logger.info("/findInforMationTitle，查询参数：{}",info);
            int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
            PageInfo<Information> lists = informationService.findInformationTitle(currentPage, 10, info);
            logger.debug("/findInforMationTitle，查询结果：{}",lists.getList());
            result.setData(lists);
        } catch (NumberFormatException e) {
            logger.error("/findInforMationTitle，查询失败：{}",e);
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/findInfoComment/{page}", method = RequestMethod.GET)
    public Result findInfoComment(@PathVariable String page, HttpServletRequest request) {
        String sid = request.getParameter("id");
        Result result = new Result();
        if (!StringUtils.isEmpty(sid)) {
            Integer id = Integer.valueOf(sid);
            int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
            int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
            PageInfo<InfoComment> list = informationService.findInfoComment(currentPage, pageSize, id);
            Integer tote = informationService.findInfoCommentCount(id);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("list", list);
            map.put("tote", tote);
            result.setData(map);
        }
        return result;
    }


    @RequestMapping(value = "/findMyInfoComment/{page}", method = RequestMethod.GET)
    public Result findMyInfoComment(@PathVariable String page, HttpServletRequest request) {
        Result result = new Result();
        try {
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            String sid = request.getParameter("id");
            Map<String, Object> map = new HashMap<>();
            if (!StringUtils.isEmpty(sid)) {
                Integer id = Integer.valueOf(sid);
                map.put("informationDetailId", id);
                map.put("userAccount", loginUser.getLoginAccount());
                int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
                int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
                PageInfo<InfoComment> list = informationService.findMyInfoComment(currentPage, pageSize, map);
                Map<String, Object> map1 = new HashMap<>();
                map1.put("list", list);
                result.setData(map1);
            }
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/findKnowLedgeComments", method = RequestMethod.GET)
    public Result findInfoComments(HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        Map<String, Object> map1 = new HashMap<>();
        String label = request.getParameter("label");
        Integer id = Integer.parseInt(request.getParameter("id"));
        map1.put("account", loginUser.getLoginAccount());
        map1.put("label", label);
        map1.put("id", id);
        List<InfoComment> list = informationService.findInfoComments(map1);
        // Integer tote = knowLedgeService.findKnowledgeCommentCount(id);
        Result result = new Result();
        result.setData(list);
        // map.put("tote", tote);
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
        InfoComment report = new InfoComment();
        Integer id = Integer.valueOf(request.getParameter("id").toString());
        report.setId(id);
        Result result = new Result();
        try {
            InfoComment infoComment = informationService.update(report);
            Map<String, Integer> map = new HashMap<>();
            map.put("flag", 1);
            map.put("thumbUpNum", infoComment.getThumbUpNum());
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
        //LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        InfoComment report = new InfoComment();
        Object postid = params.get("postId");
        if (null != postid) {
            report.setPostId(Integer.valueOf(postid.toString()));
        } else {
            report.setPostId(0);
        }
        String dyid = params.get("dynamicId").toString();
        if (StringUtils.hasText(dyid)) {
            report.setInformationDetailId(Integer.valueOf(dyid));
        } else {
            report.setInformationDetailId(0);
        }
        report.setCreateTime(new Date());report.setContent(params.get("comment").toString());
        report.setUserAccount(String.valueOf(params.get("account")));
        report.setUserName(String.valueOf(params.get("authorName")));
        report.setThumbUpNum(0);
        Result result = new Result();
        try {
            informationService.insert(report);
            result.setData(1);
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
    public Result replylist(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        try {
            //String account = ((LoginUser) request.getSession().getAttribute("currentUser")).getLoginAccount();
            String account = params.get("account").toString();
            String page = params.get("currentPage").toString();
            int currentPage = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
            Map<String, String> map = new HashMap<>();
            map.put("userAccount", account);
            map.put("informationDetailId", params.get("replyid").toString());
            PageInfo<InfoComment> pageInfo = informationService.replylist(currentPage, 10, map);
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
    public Result replyChildrenlist(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        try {
            String account = ((LoginUser) request.getSession().getAttribute("currentUser")).getLoginAccount();
            String page = params.get("currentPage").toString();
            int currentPage = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
            Integer postid = Integer.parseInt(params.get("postId").toString());
            PageInfo<InfoComment> pageInfo = informationService.replyToOther(currentPage, 10, postid);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/findMyComment/{page}", method = RequestMethod.GET)
    public Result findMyComment(@PathVariable String page, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        Result result = new Result();
        if (null == loginUser) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            return result;
        }
        Integer id = Integer.valueOf(request.getParameter("id"));
        InfoComment report = new InfoComment();
        report.setUserAccount(loginUser.getLoginAccount());
        report.setInformationDetailId(id);
        if (!StringUtils.isEmpty(id)) {
            int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
            int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
            List<InfoComment> list = informationService.findMyComment(currentPage, pageSize, report);
            result.setData(list);
        }
        return result;
    }

    @RequestMapping(value = "/upvote", method = RequestMethod.GET)
    public Result upvote(HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Result result = new Result();
        try {
            Integer id = Integer.valueOf(request.getParameter("id"));
            InformationDetail infoComment = informationService.upvote(id);
            map.put("flag", 1);
            map.put("thumbUpNum", infoComment.getThumbUpNum());
            result.setData(map);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/listComments", method = RequestMethod.GET)
    public Result listComments(HttpServletRequest request) {
        String label = request.getParameter("label");
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        Map<String, Object> maplabel = new HashMap<>();
        maplabel.put("label", label);
        maplabel.put("editer", loginUser.getDisplayName());
        List<InformationDetail> list = informationService.findColumnInforMation(maplabel);
        Map<String, Object> map = new HashMap<>();
        map.put("label", label);
        map.put("lists", list);
        List<InfoComment> comments = informationService.listComment(map);
        Result result = new Result();
        result.setData(comments);
        return result;
    }

    /**
     * 资讯发布
     */
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public Result publish(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        InformationDetail detail = new InformationDetail();
        Result result = new Result();
        try {

            detail.setTitle(params.get("title").toString());
            detail.setCreateTime(new Date());
            detail.setAccount(params.get("account").toString());
            detail.setSource(params.get("source").toString());
            detail.setEditer(params.get("author").toString());
            detail.setContent(params.get("content").toString());
            detail.setLabel(params.get("custom").toString());
            detail.setYcAuthor(params.get("ycauthor").toString());
            informationService.insertInformationDetail(detail);
            //保存视频、音频
            List<Map<String, Object>> object = (List<Map<String, Object>>) params.get("video");
            List<VideoImg> videoImgs=new ArrayList<>();
            if(!object.isEmpty()){
                for(Map<String, Object> map : object){
                    VideoImg videoImg=new VideoImg();
                    videoImg.setAccount(params.get("account").toString());
                    videoImg.setAddr((String)map.get("url"));
                    videoImg.setType((Integer) map.get("type"));
                    videoImg.setColumn((String)map.get("column"));
                    if(StringUtils.hasText((String)map.get("describe"))){
                        videoImg.setDescribe((String)map.get("describe"));
                    }
                    videoImg.setMediaName((String)map.get("musicName"));
                    videoImg.setRelativeId(detail.getId());
                    videoImgs.add(videoImg);
                }
                videoImgService.insert(videoImgs);
            }

            Information information = new Information();
            information.setInformationDetailId(detail.getId());
            String doctype=(String)params.get("leixin");
            String content=(String)params.get("content");
            /*if("文章".equals(doctype)){
                String img=getImgStr(content);
                information.setImageAdd(img);
            }*/
            if("图册".equals(doctype)){
                information.setImageAdd(content.substring(0,content.indexOf(">")+1));
            }
            if("视频".equals(doctype)){
                information.setImageAdd(videoImgs.get(0).getAddr());
            }
            information.setColumnType(doctype);
            information.setLabel(params.get("custom").toString());
            information.setTitle(params.get("title").toString()); //咨询标题
            information.setCreateTime(detail.getCreateTime());
            information.setCommentNum(detail.getBrowseNumber()); //评论次数
            information.setLabel(params.get("custom").toString()); //标签 关键字
            information.setDocType(params.get("category").toString());
            if(StringUtils.hasText(params.get("species").toString())){
                information.setSpeciesName(params.get("species").toString());
            }
            if(StringUtils.hasText(params.get("speciesId").toString()))// 物种名称
                information.setSpeciesId(params.get("speciesId").toString());
            if(StringUtils.hasText((String)params.get("goodsname"))){
                information.setProduct((String)params.get("goodsname"));
            }
            if(StringUtils.hasText((String)params.get("servicename"))){
                information.setService((String)params.get("servicename"));
            }
            if(StringUtils.hasText((String)params.get("industry"))){
                information.setIndustrialId((String)params.get("industry"));
            }
            information.setAllRange((String)params.get("district"));
            information.setFriendReminding((String)params.get("tixing"));
            information.setRemindingContent((String)params.get("con"));
            information.setIsSms("是".equals((String)params.get("sms")) ? 0 : 1 );
            information.setIsPublish((Integer) params.get("security"));
            information.setComponsation((String)params.get("money"));
            information.setImageAdd((String)params.get("imageAdd"));
            Object abstracts = params.get("summary");
            if (null != abstracts) {
                information.setAbstracts(abstracts.toString());
            }
            Object classifiedId = params.get("type");
            if (null != classifiedId) {
                information.setClassifiedId(classifiedId.toString());
            }
            String xianshi = params.get("xianshi").toString();
            if (!"是".equals(xianshi)) {
                information.setIsToPage(1);
            }else{
                information.setIsToPage(0);
            }
            information.setAuthor(params.get("account").toString());
            informationService.insertInfo(information);
            //若发布的为资讯-图书 图书单独存储至information_book_info
            if (MapUtils.getString(params, "isBook") != null) {
                Map<String, Object> bookMap = new HashMap<>();
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
                bookMap.put("bookType", "information");
                informationBookService.insert(bookMap);
                //System.out.println(bookMap.get("ID"));
                logger.debug("新增资讯-图书发布，成功插入数据至bookInfo, id:{}", bookMap.get("ID"));

                //存储图书详情
                int book_info_id = Integer.parseInt(String.valueOf(bookMap.get("ID")));
                List<Map<Object,Object>> bookDetails = JSONHelper.parseObject(params.get("book_data").toString(), List.class);

                int count = 0;
                if (bookDetails != null && bookDetails.size()>0) {
                    //图书的章节数量
                    int ChapterSize = bookDetails.size();
                    List<Map<String,Object>> saveBookDetailList = new ArrayList<>();

                    for (int i = 0; i< ChapterSize ; i++ ) {
                        //每章图书的信息
                        Map<Object,Object> chapterInfo = bookDetails.get(i);

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
                        Map<String,Object> saveChapterInfo = new HashMap<>();
                        saveChapterInfo.put("information_detail_id",detail.getId());
                        saveChapterInfo.put("book_info_id",book_info_id);
                        saveChapterInfo.put("title",title);
                        saveChapterInfo.put("edit",edit);
                        saveChapterInfo.put("checked",checked);
                        saveChapterInfo.put("expand",expand);
                        saveChapterInfo.put("node_id",node_id);
                        saveChapterInfo.put("parent_id",parent_id);
                        saveChapterInfo.put("order_id",order_id);
                        saveChapterInfo.put("content","");
                        saveChapterInfo.put("file","");
                        saveChapterInfo.put("file_name","");
                        saveChapterInfo.put("bookType", "information");
                        saveBookDetailList.add(saveChapterInfo);

                        //章节的node_id
                        int chapter_id = count;
                        //每章的小节数量
                        int partSize = jsonArray.size();
                        for (int j=0; j<partSize; j++) {

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
                            order_id = j+1;
                            bookContent =  String.valueOf(partInfo.get("content"));
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
                            Map<String,Object> savePartInfo = new HashMap<>();
                            savePartInfo.put("information_detail_id",detail.getId());
                            savePartInfo.put("book_info_id",book_info_id);
                            savePartInfo.put("title",title);
                            savePartInfo.put("edit",edit);
                            savePartInfo.put("checked",checked);
                            savePartInfo.put("expand",expand);
                            savePartInfo.put("node_id",node_id);
                            savePartInfo.put("parent_id",parent_id);
                            savePartInfo.put("order_id",order_id);
                            savePartInfo.put("content",bookContent.getBytes());
                            savePartInfo.put("file",file_url);
                            savePartInfo.put("file_name",file_name);
                            savePartInfo.put("bookType", "information");
                            saveBookDetailList.add(savePartInfo);

                        }
                    }
                    //批量插入图书信息,先删除，后插入
                    informationBookDetailService.batchInsert(saveBookDetailList, detail.getId(), "information");
                    result.setMsg("数据新增成功");
                }
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }



    /**
     * 得到网页中图片的地址
     * @param
     */
    public  String getImgStr(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        Boolean b=m_image.find();
        if(b){
            img = m_image.group();

        }

        return img;
    }

    /**
     * 资讯的上一页和下一页查询
     */
    @RequestMapping(value = "/upAndNextPage/{id}",method = RequestMethod.GET)
    public Result upAndNextPage(@PathVariable Integer id) {
        logger.info("文章id-->{}", id);
        Result result = new Result();
        try {
            Map<String, Object> map = informationService.findUpAndNextPage(id);
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

    /**
     *  无忧导航-资讯-简讯
     */
    @RequestMapping(value = "/brief-news",method = RequestMethod.POST)
    public Result getBriefNews() {
        Result result = new Result();
        try {
            List<Map<String, Object>> list = informationService.getBriefNews();
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
     * 资讯右边相关专家和相关企业
     */
    @RequestMapping(value = "/infoRelation/{id}/{type}",method = RequestMethod.GET)
    public Result relationWithInformation(@PathVariable Integer id,@PathVariable Integer type){
        logger.info("查询的id-->{}",id);
        Result result = new Result();
        try {
            Map<String, Object> map = informationService.relationWithInformation(id,type);
            result.setData(map);
            result.setCode(ResultCode.SUCCESS);
            logger.info("相关专家相关企业--{}", map);
        }catch (Exception e){
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            logger.error("{}",e.getMessage());
            return result;
        }
        return  result ;
    }

    /**
     * 根据information的id查询所有相关信息
     */
    @RequestMapping(value = "/findInFormationBookInfo", method = RequestMethod.POST)
    public Result findInFormationBookInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            String ids = String.valueOf(params.get("id"));
            int information_id = Integer.parseInt(ids);
            String bookType =  String.valueOf(params.get("book_type"));
            String flag =  String.valueOf(params.get("flag"));
            //根据information_id、book_type获取information_detail_id
            Map<String,Object> informationMap = new HashMap<>();
            informationMap.put("information_id", information_id);
            informationMap.put("bookType", bookType);
            informationMap.put("flag", flag);
            Map<String,Object> information = informationBookDetailService.getInformation(informationMap);

            if (MapUtils.isNotEmpty(information)) {
                int informationDatailId =  Integer.parseInt(String.valueOf(information.get("information_detail_id")));
                //根据information_detail_id获取information_detail表相关信息
                Map<String,Object> informationDetailMap = new HashMap<>();
                informationDetailMap.put("information_detail_id", informationDatailId);
                informationDetailMap.put("bookType", bookType);
                Map<String,Object> informationDetail = informationBookDetailService.getInformationDetail(informationDetailMap);

                //根据information_detail_id获取information_book_info表相关信息
                Map<String,Object> informationBookInfo = informationBookDetailService.getInformationBookInfo(informationDetailMap);

                //根据information_detail_id获取information_book_detail表相关信息
                List<Map<String,Object>> informationBookDetail = informationBookDetailService.getInformationBookDetail(informationDetailMap);

                List<Map<String,Object>> bookDetailDealInfo = new ArrayList<>();
                for(int i=0; i< informationBookDetail.size(); i++) {
                    Map<String,Object> ibd = informationBookDetail.get(i);
                    String parentId = String.valueOf(ibd.get("parent_id"));
                    List<Map<String,Object>> children = new ArrayList<>();
                    if ("0".equals(String.valueOf(ibd.get("parent_id")))) {
                        //若parent_id为0,则说明是一个章节节点
                        String nodeId = String.valueOf(ibd.get("node_id"));
                        for (int j=i+1; j< informationBookDetail.size(); j++) {
                            if (nodeId.equals(String.valueOf(informationBookDetail.get(j).get("parent_id")))) {
                                //若该节点的parent_id与上面数据的node_id一样，则说明是上面章节节点的子节点，即为图书小节数据
                                Map<String,Object> childrenMap = informationBookDetail.get(j);
                                if (!StringUtils.isEmpty(childrenMap.get("content"))) {
                                    childrenMap.put("content", new String((byte[])childrenMap.get("content"), "utf-8"));
                                }
                                children.add(informationBookDetail.get(j));
                            }
                        }
                        ibd.put("children", children);
                        bookDetailDealInfo.add(ibd);
                    }

                }
                Map<String,Object> allInfoData = new HashMap<>();
                allInfoData.put("infomation_data", information);
                allInfoData.put("detail_data", informationDetail);
                allInfoData.put("book_info_data", informationBookInfo);
                //allInfoData.put("book_detail_data", informationBookDetail);
                allInfoData.put("book_detail_data", bookDetailDealInfo);

                result.setData(allInfoData);
                result.setCode(ResultCode.SUCCESS);

            } else {
                result.setCode(ResultCode.FAIL);
                result.setMsg("未找到相关信息");
            }

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 根据information的id删除所有相关信息
     */
    @RequestMapping(value = "/deleteInFormationBookInfo", method = RequestMethod.POST)
    public Result deleteInFormationBookInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            int information_id = Integer.parseInt(String.valueOf(params.get("id")));
            String bookType =  String.valueOf(params.get("book_type"));
            //根据information_id获取information_detail_id
            Map<String,Object> informationMap = new HashMap<>();
            informationMap.put("information_id", information_id);
            informationMap.put("bookType", bookType);
            Map<String,Object> information = informationBookDetailService.getInformation(informationMap);

            if (MapUtils.isNotEmpty(information)) {
                int informationDatailId =  Integer.parseInt(String.valueOf(information.get("information_detail_id")));

                //根据information_id删除information表相关信息
                informationBookDetailService.deleteInformation(informationMap);

                //根据information_detail_id删除information_detail表相关信息
                Map<String,Object> informationDeleteMap = new HashMap<>();
                informationDeleteMap.put("information_detail_id", informationDatailId);
                informationDeleteMap.put("book_type", bookType);
                informationBookDetailService.deleteInformationDetail(informationDeleteMap);

                //根据information_detail_id删除information_book_info表相关信息
                informationBookDetailService.deleteInformationBookInfo(informationDeleteMap);

                //根据information_detail_id删除information_book_detail表相关信息
                informationBookDetailService.deleteInformationBookDetail(informationDeleteMap);

                result.setMsg("数据删除成功");

            } else {
                result.setCode(ResultCode.FAIL);
                result.setMsg("未找到相关信息");
            }

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 根据information_detail_id和node_id查询图书小节信息
     */
    @RequestMapping(value = "/getBookPartInFormation", method = RequestMethod.POST)
    public Result getBookPartInFormation(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            int information_detail_id = Integer.parseInt(String.valueOf(params.get("information_detail_id")));
            int node_id = Integer.parseInt(String.valueOf(params.get("node_id")));
            //图书类型
            String bookType =  String.valueOf(params.get("book_type"));
            //根据information_detail_id和node_id查询图书小节信息
            Map<String,Object> bookPartInfo = new HashMap<>();
            bookPartInfo.put("information_detail_id",information_detail_id);
            bookPartInfo.put("node_id",node_id);
            bookPartInfo.put("book_type",bookType);
            Map<String,Object> partInfo = informationBookDetailService.getBookPartInfo(bookPartInfo);
            logger.info("图书小节信息：{}",partInfo);
            if (MapUtils.isNotEmpty(partInfo)) {
                partInfo.put("content", new String((byte[]) partInfo.get("content"), "utf-8"));
                result.setData(partInfo);
                result.setCode(ResultCode.SUCCESS);

            } else {

                result.setCode(ResultCode.FAIL);
                result.setMsg("未找到相关信息");
            }

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 根据information_detail_id查询information信息
     */
    @RequestMapping(value = "/findInformationByDetailID", method = RequestMethod.POST)
    public Result findInformationByDetailID(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            int information_detail_id = Integer.parseInt(String.valueOf(params.get("information_detail_id")));
            //图书类型
            String bookType =  String.valueOf(params.get("book_type"));

            //根据information_detail_id查询information信息
            Map<String,Object> detailMap = new HashMap<>();
            detailMap.put("information_detail_id", information_detail_id);
            detailMap.put("bookType", bookType);
            Map<String,Object> infomation = informationBookDetailService.findInformationByDetailID(detailMap);

            logger.info("根据information_detail_id:{},查询到的资讯信息为：{}",information_detail_id, infomation);

            if (MapUtils.isNotEmpty(infomation)) {

                result.setData(infomation);
                result.setCode(ResultCode.SUCCESS);

            } else {

                result.setCode(ResultCode.FAIL);
                result.setMsg("未找到相关信息");
            }

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 获取门户中动态轮播图
     * @param params
     * @return
     */
    @RequestMapping(value = "/dynamicCoverList", method = RequestMethod.POST)
    public Result dynamicCoverList(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            String account = MapUtils.getString(params, "account");
            List<Map<String, Object>> list = informationService.dynamicCoverList(account);
            if (CollectionUtils.isEmpty(list)) {
                list = new ArrayList<>();
            }
            result.setData(list);
            result.setMsg("动态轮播图查询成功");

        } catch (Exception e) {
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询异常");
        }
        return result;
    }

}
