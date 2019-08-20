package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.util.QRCodeUtil;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 百度百科相关信息查询
 */
@RestController
@RequestMapping("/speciesWiki")
public class SpeciesWikiRelevantInfoController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private SpeciesWikiRelevantInfoService speciesWikiRelevantInfoService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private ColumnSettingService columnSettingService;

    @Autowired
    private KnowledgeService knowService;


    /**
     *
     * @param params 物种名称
     * @return 获取相关专家、企业、产品的相关信息
     * @throws Exception
     */
    @RequestMapping(value = "/getRelevantInfo", method = RequestMethod.POST)
    public Result getRelevantInfo(@RequestBody Map<String, Object> params) throws Exception {
        Result result = new Result();
        try{
            String speciesName = MapUtils.getString(params, "speciesName");
            //相关专家信息
            List<Map<String,Object>> relevantExpertInfo = speciesWikiRelevantInfoService.relevantExpert(speciesName);
            //相关产品信息
            List<Map<String,Object>> relevantProductInfo = speciesWikiRelevantInfoService.relevantProduct(speciesName);
            //相关企业信息
            //List<Map<String,Object>> relevantCorpInfo = new ArrayList<>();

            /*for (Map<String,Object> species : relevantProductInfo) {
                String account = MapUtils.getString(species, "account");
                Map<String, Object> corpInfo = speciesWikiRelevantInfoService.relevantCorp(account);
                relevantCorpInfo.add(corpInfo);
            }*/
            Map<String, Object> relevantInfo = new HashMap<>();
            relevantInfo.put("relevantExpertInfo", relevantExpertInfo);
            relevantInfo.put("relevantProductInfo", relevantProductInfo);
            //relevantInfo.put("relevantCorpInfo", relevantCorpInfo);

            result.setMsg("查询物种相关信息成功");
            result.setData(relevantInfo);

        } catch (Exception e) {
            logger.error("查询物种相关信息失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询物种相关信息失败");
        }

        return result;
    }

    /**
     *
     * @param params 物种名称、类型
     * params:
     * {
     *     "type" : "information",  //查询类型（information:相关资讯、knowledge:相关知识、policy:相关政策）
     *     "speciesId" ： "d20fdbf2a01340099e4711dcc8427588"   //物种的speciesId
     *     "pageSize" : 5   //查询5条数据
     * }
     * @return 获取物种相关知识、政策、资讯
     * @throws Exception
     */
    @RequestMapping(value = "/information", method = RequestMethod.POST)
    public Result information(@RequestBody Map<String, Object> params) throws Exception {
        Result result = new Result();
        try{

            String type = MapUtils.getString(params, "type");
            String speciesId = MapUtils.getString(params, "speciesId");
            Integer num = MapUtils.getInteger(params, "pageNum");
            Integer size = MapUtils.getInteger(params, "pageSize");
            int pageSize = (size == null) ? 10 : size;
            int pageNum = (num == null) ? 1 : num;
            Map<String,Object> map = new HashMap<>();
            map.put("speciesId", speciesId);
            Map<String, Object> commentMap = new HashMap<String, Object>();
            if ("information".equals(type)) {
                //查询相关资讯
                PageInfo<InformationDetail> list = informationService.relevantInformation(map, pageNum, pageSize);
                if(CollectionUtils.isNotEmpty(list.getList())) {
                    //查询总评论数
                    commentMap.put("lists", list.getList());
                    commentMap.put("TABLE_NAME", "info_comment");
                    informationService.listCommentCounts(commentMap);
                }
                result.setMsg("查询物种相关资讯信息成功");
                result.setData(list);
            } else if ("knowledge".equals(type)) {
                //查询相关知识
                PageInfo<KnowledgeDetail> list = knowService.relevantKnowledge(map, pageNum, pageSize);
                if(CollectionUtils.isNotEmpty(list.getList())) {
                    //查询总评论数
                    commentMap.put("lists", list.getList());
                    commentMap.put("TABLE_NAME", "knowledge_comment");
                    knowService.listCommentCounts(commentMap);
                }
                result.setMsg("查询物种相关知识信息成功");
                result.setData(list);
            } else if ("policy".equals(type)) {
                //查询相关政策
                PageInfo<PolicyDetail> list = policyService.relevantPolicy(map, pageNum, pageSize);
                if(CollectionUtils.isNotEmpty(list.getList())) {
                    //查询总评论数
                    commentMap.put("lists", list.getList());
                    commentMap.put("TABLE_NAME", "policy_comment");
                    policyService.listCommentCounts(commentMap);
                }
                result.setMsg("查询物种相关政策信息成功");
                result.setData(list);
            } else {
                result.setCode(ResultCode.FAIL);
                result.setMsg("暂无此类型数据");
            }

        } catch (Exception e) {
            logger.error("查询物种相关信息失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询物种相关信息失败");
        }

        return result;
    }

}
