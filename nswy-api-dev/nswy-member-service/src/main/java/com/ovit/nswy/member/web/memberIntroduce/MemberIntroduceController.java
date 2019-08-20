package com.ovit.nswy.member.web.memberIntroduce;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.date.DateFormat;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.util.JsonToStringUtils;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/memberIntroduce")
public class MemberIntroduceController {

    /**
     *
     */
    private static final Logger logger = LogManager.getLogger(MemberIntroduceController.class);

    @Autowired
    private MemberIntroduceService  memberIntroduceService;

    @Autowired
    private VideoImgService videoImgService;

    @Autowired
    private InformationBookService informationBookService;

    @Autowired
    private InformationBookDetailService informationBookDetailService;



    /**
     * 查询得到农事无忧id等信息
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findNswyInfo", method = RequestMethod.POST)
    public Result findNswyInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try{
            Map<String,Object> list = memberIntroduceService.findNswyInfo(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询账号信息失败");
        }

        return result;
    }

    /**
     * 发布会员介绍
     */

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public Result publish(@RequestBody Map<String, Object> params) {

        MemberIntroduceDetail detail = new MemberIntroduceDetail();
        Result result = new Result();
        try {

            memberIntroduceService.findIntroduceDelete(params);

            detail.setTitle(params.get("title").toString());
            detail.setCreateTime(new Date());
            detail.setAccount(params.get("account").toString());
            detail.setSource(params.get("source").toString());
            detail.setEditer(params.get("author").toString());
            detail.setContent(params.get("content").toString());
            detail.setLabel(params.get("custom").toString());
            detail.setYcAuthor(params.get("ycauthor").toString());
            detail.setSpecies(params.get("speciesId").toString());
            detail.setProduct(params.get("goodsId").toString());
            detail.setService(params.get("serviceId").toString());
            detail.setIndustry(params.get("industryId").toString());
            detail.setNetwork(params.get("network").toString());

            String typeList  = MapUtils.getString(params,"typeList");
            if(!"null".equals(typeList)&& StringUtil.isNotBlank(typeList)){
                detail.setTypeList(typeList);

            }
            String atlasList  = MapUtils.getString(params,"atlasList");
            if(!"null".equals(atlasList)&& StringUtil.isNotBlank(atlasList)){
                detail.setAtlasList(atlasList);

            }
            memberIntroduceService.insertIntroduceDetail(detail);
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
                    videoImg.setMusicSize((String)map.get("musicSize"));
                    videoImgs.add(videoImg);
                }
                videoImgService.insert(videoImgs);
            }

            MemberIntroduce memberIntroduce = new MemberIntroduce();
            memberIntroduce.setMemberIntroduceDetailId(detail.getId());
            String doctype=(String)params.get("leixin");
            String content=(String)params.get("content");

            if("图册".equals(doctype)){
                memberIntroduce.setImageAdd(content.substring(0,content.indexOf(">")+1));
            }
            if("视频".equals(doctype)){
                memberIntroduce.setImageAdd(videoImgs.get(0).getAddr());
            }
            memberIntroduce.setColumnType(doctype);
            memberIntroduce.setLabel(params.get("custom").toString());
            memberIntroduce.setTitle(params.get("title").toString()); //咨询标题
            memberIntroduce.setCreateTime(detail.getCreateTime());
            memberIntroduce.setCommentNum(detail.getBrowseNumber()); //评论次数
            memberIntroduce.setLabel(params.get("custom").toString()); //标签 关键字
            memberIntroduce.setDocType(params.get("category").toString());
            if(StringUtils.hasText(params.get("species").toString())){
                memberIntroduce.setSpeciesName(params.get("species").toString());
            }
            if(StringUtils.hasText(params.get("speciesId").toString()))// 物种名称
                memberIntroduce.setSpeciesId(params.get("speciesId").toString());
            if(StringUtils.hasText((String)params.get("goodsname"))){
                memberIntroduce.setProduct((String)params.get("goodsname"));
            }
            if(StringUtils.hasText((String)params.get("servicename"))){
                memberIntroduce.setService((String)params.get("servicename"));
            }
            if(StringUtils.hasText((String)params.get("industryName"))){
                memberIntroduce.setIndustrialId((String)params.get("industryName"));
            }
            memberIntroduce.setAllRange((String)params.get("district"));
            memberIntroduce.setFriendReminding((String)params.get("tixing"));
            memberIntroduce.setRemindingContent((String)params.get("con"));
            memberIntroduce.setIsSms("是".equals((String)params.get("sms")) ? 0 : 1 );
            memberIntroduce.setIsPublish((Integer) params.get("security"));
            memberIntroduce.setComponsation((String)params.get("money"));
            memberIntroduce.setImageAdd((String)params.get("imageAdd"));
            Object abstracts = params.get("summary");
            if (null != abstracts) {
                memberIntroduce.setAbstracts(abstracts.toString());
            }
            Object classifiedId = params.get("type");
            if (null != classifiedId) {
                memberIntroduce.setClassifiedId(classifiedId.toString());
            }
            String xianshi = params.get("xianshi").toString();
            if (!"是".equals(xianshi)) {
                memberIntroduce.setIsToPage(1);
            }else{
                memberIntroduce.setIsToPage(0);
            }
            memberIntroduce.setAuthor(params.get("account").toString());
            memberIntroduceService.insertInfo(memberIntroduce);
            //若发布的为资讯-图书 图书单独存储至information_book_info
            if (MapUtils.getString(params, "isBook") != null) {
                Map<String, Object> bookMap = new HashMap<>();
                bookMap.put("memberIntroduceDetailId", detail.getId());
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

                informationBookService.insertMemberIntroduceBook(bookMap);

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
                        saveChapterInfo.put("member_introduce_detail_id",detail.getId());
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


                            //保存图书信息
                            Map<String,Object> savePartInfo = new HashMap<>();
                            savePartInfo.put("member_introduce_detail_id",detail.getId());
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
                    informationBookDetailService.batchMemberIntroduceInsert(saveBookDetailList, detail.getId(),book_info_id);
                    result.setMsg("数据新增成功");
                }
            }
        } catch (Exception e) {
            logger.error("异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 查询图书列表标题
     * @param params
     * @return
     */
    @RequestMapping(value = "/findMediaLibraryInfo", method = RequestMethod.POST)
    public Result findMediaLibraryInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = memberIntroduceService.findMediaLibraryInfo(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询图书列表标题信息失败");
        }

        return result;
    }

    /**
     * 图书详情
     * @param params
     * @return
     */
    @RequestMapping(value = "/findMediaBookDetail", method = RequestMethod.POST)
    public Result findMediaBookDetail(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = memberIntroduceService.findMediaBookDetail(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询图书列表标题信息失败");
        }

        return result;
    }

    /**
     * 会员介绍详情
     * @param params
     * @return
     */
    @RequestMapping(value = "/findMemberIntroduceInfoById", method = RequestMethod.POST)
    public Result findMemberIntroduceInfoById(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try{
            Map<String,Object> list = memberIntroduceService.findMemberIntroduceInfoById(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询会员介绍详情信息失败");
        }

        return result;
    }


    /**
     * 查询会员介绍
     * @param params
     * @return
     */
    @RequestMapping(value = "/findMemberIntroduceInfo", method = RequestMethod.POST)
    public Result findMemberIntroduceInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try{
            Map<String,Object> list = memberIntroduceService.findMemberIntroduceInfo(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询会员介绍信息失败");
        }

        return result;
    }




}
