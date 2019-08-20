package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.StandardComment;
import com.ovit.nswy.member.service.StandardService;
import com.ovit.nswy.member.util.JsonToStringUtils;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标准
 *
 * @author haoWen
 * @create 2018-03-12 15:36
 **/

@RequestMapping("/standard")
@RestController
public class StandardController {
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private StandardService standardService;

    /**
     * 发布标准，
     * 先插入详情，再插入基本信息
     * 设置事物
     * @param params
     * @return
     */
    @RequestMapping(value = "/release" ,method = RequestMethod.POST)
    public Result releaseStandard(@RequestBody Map<String,Object> params){
        logger.debug("发布标准，参数：{}",params);
        Result result = null;
        try {
            result = new Result();
            //将数组转换为字符串
            Map<String,Object> map = JsonToStringUtils.convertByKeyArray(params, Arrays.asList(
                    "standardType"
            ));
            //以"/"分割地区
            map.put("district",JsonToStringUtils.initJson2(map,"district"));
            //先插入详情表，同时返回详情主键(standardDetailId)
            standardService.insertStandard(map);
            logger.info("发布标准成功！");
        } catch (Exception e) {
            logger.error("发布标准失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 会员中心显示已发布的标准
     * @param params
     * @return
     */
    @RequestMapping(value = "/getForMemberCenter" ,method = RequestMethod.POST)
    public Result getForMemberCenter(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("标准在会员中心显示查询，参数：{}",params);
            PageInfo<Map<String,Object>> standardList = standardService.queryForMemberCenter(params);
            logger.info("标准在会员中心显示查询成功：{}",standardList.getList());
            result.setData(standardList);
        } catch (Exception e) {
            logger.error("标准在会员中心显示查询失败：{}",e);
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *无忧首页标准
     * @param params
     * @return
     */
    @RequestMapping(value = "/getForNswyHome" ,method = RequestMethod.POST)
    public Result getForNswyHome(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("标准在农事无忧首页显示，参数：{}",params);
            String industry = MapUtils.getString(params,"industry");
            if (StringUtils.isNotEmpty(industry)) {
                String[] industryArray = industry.split(" ");
                params.put("industryArray",industryArray);
            }
            String species = MapUtils.getString(params,"species");
            if (StringUtils.isNotEmpty(species)) {
                String[] speciesArray = species.split(" ");
                params.put("speciesArray",speciesArray);
            }
            String goodName = MapUtils.getString(params,"goodname");
            if (StringUtils.isNotEmpty(goodName)) {
                String[] goodNameArray = goodName.split(" ");
                params.put("goodNameArray",goodNameArray);
            }
            String serviceName = MapUtils.getString(params,"servicename");
            if (StringUtils.isNotEmpty(serviceName)) {
                String[] serviceArray = serviceName.split(" ");
                params.put("serviceArray",serviceArray);
            }
            String publishDate = MapUtils.getString(params,"publishDate");
            if (StringUtils.isNotEmpty(publishDate)) {
                List<String> list = JSON.parseArray(publishDate,String.class);
                if (list.size() == 2) {
                    params.put("startTime",list.get(0));
                    params.put("endTime",list.get(1));
                }
            }
            PageInfo<Map<String,Object>> standardList = standardService.queryForNswyHome(params);
            logger.info("标准在农事无忧首页显示，查询成功：{}",standardList.getList());
            result.setData(standardList);
        } catch (Exception e) {
            logger.error("标准在农事无忧首页显示，查询失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     *无忧导航标准
     * @param params
     * @return
     */
    @RequestMapping(value = "/getNswyNavigation" ,method = RequestMethod.POST)
    public Result getForNswyNavigation(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("标准在农事无忧导航显示，参数：{}",params);
            PageInfo<Map<String,Object>> standardList = standardService.queryForNswyNavigation(params);
            logger.info("标准在农事无忧导航显示，查询成功：{}",standardList.getList());
            result.setData(standardList);
        } catch (Exception e) {
            logger.error("标准在农事无忧导航显示，查询失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 标准详情
     * @param params
     * @return
     */
    @RequestMapping(value = "/getStandardDetail" ,method = RequestMethod.POST)
    public Result getStandardDetail(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("标准详情页查询，参数：{}",params);
            Map<String,Object> detailMap = standardService.queryStandardDetail(params);
            int thumbNum = standardService.queryThumbNumByDetailId(params);
            detailMap.put("thumbNum",thumbNum);
            logger.info("标准详情页查询成功：{}",detailMap);
            //没查询一次更新一次浏览次数
            standardService.updateBrowseNumber(params);
            result.setData(detailMap);
        } catch (Exception e) {
            logger.error("标准详情页查询失败：{}",e);
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询评论以及评论总数
     * @param params
     * @return
     */
    @RequestMapping(value = "/getStandardComment" ,method = RequestMethod.POST)
    public Result getStandardComment(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> map = new HashMap<>();
            logger.debug("标准详情评论查询，参数：{}",params);
            //int standardId = standardService.queryIdByDetailId(params);
            //params.put("standardId",standardId);
            PageInfo<Map<String,Object>> commentList = standardService.queryStandardComment(params);
            map.put("commentList",commentList);
            logger.info("标准详情评论查询成功：{}",commentList);
            result.setData(commentList);
        } catch (Exception e) {
            logger.error("标准详情评论查询失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteMyComment", method = RequestMethod.POST)
    public Result deleteMyComment(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try{
            standardService.deleteMyComment(params);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return  result;
    }


    /**
     * 查询评论的回复
     * @param params
     * @return
     */
    @RequestMapping(value = "/query-detail-comment-reply" ,method = RequestMethod.POST)
    public Result  queryDetailCommentReply(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询评论的回复：{}",params);
            PageInfo<Map<String,Object>> detailCommentReplyList = standardService.queryDetailCommentReply(params);
            result.setData(detailCommentReplyList);
            logger.debug("查询评论的回复成功：{}",detailCommentReplyList);
        } catch (Exception e) {
            logger.error("查询评论的回复失败：{}",e);
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 发布评论
     * @param params
     * @return
     */
    @RequestMapping(value = "/publish-comment" ,method = RequestMethod.POST)
    public Result  publishComment(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            String commentId = MapUtils.getString(params,"commentId");
            //如果commentId不为空则为回复其他评论，为空则为评论该标准
            if (StringUtils.isNotEmpty(commentId)) {
                params.put("postId",commentId);
            } else {
                params.put("postId","0");
            }
            standardService.publishComment(params);
            logger.info("评论成功！");
        } catch (Exception e) {
            logger.error("评论失败：{}",e);
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
