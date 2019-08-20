package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.ThumbComment;
import com.ovit.nswy.member.model.ThumbDetail;
import com.ovit.nswy.member.service.ThumbService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wxn
 * @Description:
 * @Date :17:04
 */
@RestController
@RequestMapping("/thumb")
public class ThumbController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ThumbService thumbService ;


    /**
     * 详情页点赞
     */
    @RequestMapping(value = "/detailThumbAdd" ,method = RequestMethod.POST)
    public Result detailThumbAdd(@RequestBody ThumbDetail thumbDetail){
        // account detailId type
        // type 分为 information policy knowledge
        Result result = new Result();
        try {
            ThumbDetail thumbDetail1 = thumbService.findThumbDetail(thumbDetail);
            // 判断其是否存在
            logger.info("detailThumbAdd-->{}",thumbDetail1);
            if (null == thumbDetail1) {
                thumbService.addThumbDetail(thumbDetail);
                result.setCode(ResultCode.SUCCESS);
                result.setData("ok");// 保存成功返回ok
            } else {
                result.setCode(ResultCode.SUCCESS);
                result.setData("exist");//已存在返回exist
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCode.FAIL);
        }
        return result;
    }
    /**
     * 详情页点赞数目
     */
    @RequestMapping(value = "/detailThumbCount",method = RequestMethod.POST)
    public Result detailThumbCount(@RequestBody ThumbDetail thumbDetail){
        // detailId type
        Result result = new Result();
        try {
            int count = thumbService.findThumbDetailCount(thumbDetail);
            result.setCode(ResultCode.SUCCESS);
            result.setData(count);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCode.FAIL);
        }
        return result;
    }

    /**
     * 评论页点赞
     */
    @RequestMapping(value = "/detailThumbCommentAdd" ,method = RequestMethod.POST)
    public Result detailThumbCommentAdd(@RequestBody ThumbComment thumbComment){
        // account detailId type
        // type 分为 information policy knowledge
        Result result = new Result();
        try {
            ThumbComment thumbComment1 = thumbService.findThumbComment(thumbComment);
            // 判断其是否存在
            logger.info("detailThumbAdd-->{}",thumbComment1);
            if (null == thumbComment1) {
                thumbService.addThumbComment(thumbComment);
                result.setCode(ResultCode.SUCCESS);
                result.setData("ok");// 保存成功返回ok
            } else {
                result.setCode(ResultCode.SUCCESS);
                result.setData("exist");//已存在返回exist
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCode.FAIL);
        }
        return result;
    }
    /**
     * 评论页点赞数目
     */
    @RequestMapping(value = "/detailThumbCommentCount",method = RequestMethod.POST)
    public Result detailThumbCommentCount(@RequestBody ThumbComment thumbComment){
        // detailId type
        Result result = new Result();
        try {
            int count = thumbService.findThumbCommentCount(thumbComment);
            result.setCode(ResultCode.SUCCESS);
            result.setData(count);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCode.FAIL);
        }
        return result;
    }

}
