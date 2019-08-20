package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.HistoryEvolutionService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
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
 * 历史沿革
 */
@RestController
@RequestMapping("/historyEvolution")
public class HistoryEvolutionController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private HistoryEvolutionService historyEvolutionService;



    /**
     *点击历史沿革-历史沿革信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findHistoryEvolution",method = RequestMethod.POST)
    public Result findHistoryEvolution(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = historyEvolutionService.findHistoryEvolution(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-历史沿革信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateHistoryEvolutionName",method = RequestMethod.POST)
    public Result updateHistoryEvolutionName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            historyEvolutionService.updateHistoryEvolutionName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-历史沿革信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveHistoryEvolution",method = RequestMethod.POST)
    public Result saveHistoryEvolution(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            historyEvolutionService.saveHistoryEvolution(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-文字预览
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTextPreview",method = RequestMethod.POST)
    public Result saveTextPreview(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            historyEvolutionService.saveTextPreview(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *删除-历史沿革
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteHistoryEvolution",method = RequestMethod.POST)
    public Result deleteHistoryEvolution(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            historyEvolutionService.deleteHistoryEvolution(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
