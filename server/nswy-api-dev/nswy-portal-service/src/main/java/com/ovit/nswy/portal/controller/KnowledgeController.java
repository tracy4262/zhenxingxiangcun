package com.ovit.nswy.portal.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.core.Result;
import com.ovit.nswy.portal.core.ResultCode;
import com.ovit.nswy.portal.service.KnowledgeService;
import com.ovit.nswy.portal.util.ConstantPool;
import org.apache.commons.collections.MapUtils;
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
 * 知识
 *
 * @author haoWen
 * @create 2018-01-15 14:54
 **/
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController implements ConstantPool{

    @Autowired
    private KnowledgeService knowledgeService;

    private Logger logger = LogManager.getLogger(this.getClass());


    /**
     * 我的门户知识查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/getKnowledge",method = RequestMethod.POST)
    public Result getKnowledge(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询知识：{}",params);
            if ("全部".equals(MapUtils.getString(params,"label"))) {
                params.remove("label");
            }
            PageInfo<List<Map<String,Object>>> info = knowledgeService.queryKnowledge(params);
            logger.info("查询知识成功：{}",info.getList());
            result.setData(info);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("查询知识失败：{}",e);
        }
        return result;
    }
}
