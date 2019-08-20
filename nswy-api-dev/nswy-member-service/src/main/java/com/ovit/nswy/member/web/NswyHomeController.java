package com.ovit.nswy.member.web;

import com.ovit.nswy.member.service.NswyHomeService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

/**
 * 无忧首页
 *
 * @author haoWen
 * @create 2018-02-12 09:33
 **/
@RestController
@RequestMapping("/nswy/home")
public class NswyHomeController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private NswyHomeService nswyHomeService;


    /**
     * 资讯中心，农业要闻的图片展示
     * @param params
     * @return
     */
    @RequestMapping(value = "/pictureForBanner",method = RequestMethod.POST)
    public Result pictureForBanner(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询banner图片：{}",params);
            List<Map<String,Object>> maps = nswyHomeService.queryPictureForBanner(params);
            logger.info("查询banner图片成功：{}",maps);
            result.setData(maps);
        } catch (Exception e) {
            logger.error("查询banner图片失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 阅读排行
     * @return
     */
    @RequestMapping(value = "reading-top",method = RequestMethod.POST)
    public Result readingTop(){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询阅读排");
            List<Map<String,Object>> readingTopList = nswyHomeService.queryReadingTop();
            logger.info("阅读排行查询成功：{}",readingTopList);
            result.setData(readingTopList);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("阅读排行查询失败：{}",e);
        }
        return result;
    }

    /**
     * 查询农业要闻
     * @param
     * @return
     */
    @RequestMapping(value = "/readingImp",method = RequestMethod.POST)
    public Result readingImp(){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询农业要闻：{}");
            List<Map<String,Object>> maps = nswyHomeService.readingImp();
            logger.info("查询农业要闻成功：{}",maps);
            result.setData(maps);
        } catch (Exception e) {
            logger.error("查询农业要闻失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
