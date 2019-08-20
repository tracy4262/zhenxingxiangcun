package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.FinanceService;
import com.ovit.nswy.member.perfectInfo.service.WorkExperienceService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 财务系统
 */
@RestController
@RequestMapping("/finance")
public class FinanceController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private FinanceService financeService;



    /**
     *点击财务系统-银行账户信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findBankAccountInfo",method = RequestMethod.POST)
    public Result findBankAccountInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = financeService.findBankAccountInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



    /**
     *插入/更新-银行账户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveBankAccountInfo",method = RequestMethod.POST)
    public Result saveBankAccountInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            financeService.saveBankAccountInfo(params);

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
            financeService.saveTextPreview(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteBankAccountInfo",method = RequestMethod.POST)
    public Result deleteBankAccountInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            financeService.deleteBankAccountInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *点击财务系统-会计核算科目表获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findAccountingSubjectsInfo",method = RequestMethod.POST)
    public Result findAccountingSubjectsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = financeService.findAccountingSubjectsInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



    /**
     *插入/更新-会计核算科目表
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveAccountingSubjectsInfo",method = RequestMethod.POST)
    public Result saveAccountingSubjectsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            financeService.saveAccountingSubjectsInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteAccountingSubjectsInfo",method = RequestMethod.POST)
    public Result deleteAccountingSubjectsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
           String code  =  financeService.deleteAccountingSubjectsInfo(params);
           result.setCode(Integer.valueOf(code));

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *插入/更新-表头表尾
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveLiabilitiesTableInfo",method = RequestMethod.POST)
    public Result saveLiabilitiesTableInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            financeService.saveLiabilitiesTableInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *点击财务系统-收益及收益分配表获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findIncomeDistributionInfo",method = RequestMethod.POST)
    public Result findIncomeDistributionInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = financeService.findIncomeDistributionInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



    /**
     *插入/更新-编辑收益及收益分配表内容
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveIncomeDistributionInfo",method = RequestMethod.POST)
    public Result saveIncomeDistributionInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            financeService.saveIncomeDistributionInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
