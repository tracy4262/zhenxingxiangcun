package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.*;
import com.ovit.nswy.member.perfectInfo.service.EducationLiveService;
import com.ovit.nswy.member.perfectInfo.service.FinanceService;
import com.ovit.nswy.member.util.ImageUtil;
import com.ovit.nswy.member.util.TreeUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 财务系统
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceMapper financeMapper;

    @Autowired
    private CommunalFacilitiesMapper communalFacilitiesMapper;


    @Autowired
    private TemplateConfigService templateConfigService;


    /**
     * 银行账号信息
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findBankAccountInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //银行账号信息
        List<Map<String,Object>> list = financeMapper.findBankAccountInfo(params);
        if(list.size()>0){

            map.put("bankAccount",list);


        }else{

            map.put("bankAccount",new String[]{});

        }

        Map<String,Object> faninceStatus = financeMapper.findFaninceStatusInfo(params);
        if(MapUtils.isNotEmpty(faninceStatus)){
            map.put("faninceStatusId",MapUtils.getString(faninceStatus,"faninceStatusId"));
            map.put("status","1".equals(MapUtils.getString(faninceStatus,"status"))? true : false);
        }else{
            map.put("status",true);
        }


        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("propertyName", "");
        } else {
            map.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parentId",MapUtils.getString(params,"parentId"));
            if(templatelist.size()>0){
                historyInfo.put("account",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =  communalFacilitiesMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }






    @Override
    public void saveBankAccountInfo(Map<String,Object> params){
        Map<String,Object> bank = (Map<String, Object>) params.get("bankAccount");
        String user_id = MapUtils.getString(params,"account");
        bank.put("account",user_id);

        bank.put("yearId",MapUtils.getString(params,"yearId"));
        bank.put("templateId",MapUtils.getString(params,"templateId"));

        financeMapper.saveBankAccountInfo(bank);

        financeMapper.saveFaninceStatusInfo(params);


    }


    @Override
    public void deleteBankAccountInfo(Map<String,Object> params){
        financeMapper.deleteBankAccountInfo(params);
    }

    @Override
    public void saveTextPreview(Map<String,Object> params){
        Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");

        if(MapUtils.isNotEmpty(textPreview)){

            communalFacilitiesMapper.saveTextPreview(textPreview);
        }
    }

    /**
     * 会计核算科目表
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findAccountingSubjectsInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //会计核算科目表
        List<Map<String,Object>> list = financeMapper.findAccountingSubjectsInfo(params);

        TreeUtil treeUtil = new TreeUtil();

        if(list.size()>0){

            map.put("accountingSubjects",treeUtil.recursionNodeList(list));


        }else{

            map.put("accountingSubjects",new ArrayList<>());

        }

        Map<String,Object> faninceStatus = financeMapper.findFaninceStatusInfo(params);
        if(MapUtils.isNotEmpty(faninceStatus)){
            map.put("faninceStatusId",MapUtils.getString(faninceStatus,"faninceStatusId"));
            map.put("status","1".equals(MapUtils.getString(faninceStatus,"status"))? true : false);
        }else{
            map.put("status",true);
        }


        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("propertyName", "");
        } else {
            map.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }


        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }






    @Override
    public void saveAccountingSubjectsInfo(Map<String,Object> params){
        Map<String,Object> subject = (Map<String, Object>) params.get("accountingSubjects");
        String user_id = MapUtils.getString(params,"account");
        subject.put("account",user_id);

        subject.put("yearId",MapUtils.getString(params,"yearId"));

        financeMapper.saveAccountingSubjectsInfo(subject);

        financeMapper.saveFaninceStatusInfo(params);


    }


    @Override
    public String deleteAccountingSubjectsInfo(Map<String,Object> params){

        params.put("pid",MapUtils.getString(params,"id"));
        List<Map<String,Object>> list = financeMapper.findAccountingSubjectsInfo(params);
        if(list.size()>0){
            return "300";
        }else{
            financeMapper.deleteAccountingSubjectsInfo(params);
            return "200";
        }


    }



    /**
     * 收益及收益分配表
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> findIncomeDistributionInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        params.put("parent_id",MapUtils.getString(params,"parentId"));
        Map<String,Object> textPreview =  communalFacilitiesMapper.findTextPreview(params);
        //收益及收益分配表
        List<Map<String,Object>> list = financeMapper.findIncomeDistributionInfo(params);


        if(list.size()>0){
            for(Map<String,Object> income:list){

                JSONArray subject = JSONArray.parseArray(MapUtils.getString(income,"subject"));
                income.put("subject",subject);


                JSONArray upperYearOption = JSONArray.parseArray(MapUtils.getString(income,"upperYearOption"));
                income.put("upperYearOption",upperYearOption);

                String upperYearSel = MapUtils.getString(income,"upperYearSel");
                if(StringUtil.isNotEmpty(upperYearSel)){
                    String[] members = upperYearSel.split(";");
                    income.put("upperYearSel",members);
                }else{
                    income.put("upperYearSel", new String[]{});
                }

                String upperYearMoney = MapUtils.getString(income,"upperYearMoney");
                if(StringUtil.isNotEmpty(upperYearMoney)){
                    String[] members = upperYearMoney.split(";");
                    income.put("upperYearMoney",members);
                }else{
                    income.put("upperYearMoney", new String[]{});
                }

                JSONArray thisYearOption = JSONArray.parseArray(MapUtils.getString(income,"thisYearOption"));
                income.put("thisYearOption",thisYearOption);

                String thisYearSel = MapUtils.getString(income,"thisYearSel");
                if(StringUtil.isNotEmpty(thisYearSel)){
                    String[] members = thisYearSel.split(";");
                    income.put("thisYearSel",members);
                }else{
                    income.put("thisYearSel", new String[]{});
                }

                String thisYearMoney = MapUtils.getString(income,"thisYearMoney");
                if(StringUtil.isNotEmpty(thisYearMoney)){
                    String[] members = thisYearMoney.split(";");
                    income.put("thisYearMoney",members);
                }else{
                    income.put("thisYearMoney", new String[]{});
                }
            }

            map.put("incomeData",list);


        }else{

            map.put("incomeData",new ArrayList<>());

        }
        //头部
        Map<String,Object> tableHead = financeMapper.findLiabilitiesTableHeadInfo(params);

        if(MapUtils.isNotEmpty(tableHead)){
            JSONArray theadData = JSONArray.parseArray(MapUtils.getString(tableHead,"theadData"));

            map.put("theadData",theadData);

        }else{
            map.put("theadData",new HashMap<>());
        }

        //尾部
        Map<String,Object> tableSign = financeMapper.findLiabilitiesTableSignInfo(params);

        if(MapUtils.isNotEmpty(tableSign)){
            JSONArray signData = JSONArray.parseArray(MapUtils.getString(tableSign,"signData"));
            map.put("signData",signData);

        }else{
            map.put("signData",new HashMap<>());
        }

        Map<String,Object> faninceStatus = financeMapper.findFaninceStatusInfo(params);
        if(MapUtils.isNotEmpty(faninceStatus)){
            map.put("faninceStatusId",MapUtils.getString(faninceStatus,"faninceStatusId"));
            map.put("status","1".equals(MapUtils.getString(faninceStatus,"status"))? true : false);
        }else{
            map.put("status",true);
        }


        Map<String,Object> propertyInfo = communalFacilitiesMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("propertyName", "");
        } else {
            map.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }


        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("textPreview","");
            text.put("parentId","");
            text.put("account","");
            text.put("yearId","");
            text.put("isComplete",0);
            map.put("textPreview",text);
        }else{

            map.put("textPreview",textPreview);
        }



        return map;
    }


    @Override
    public void saveIncomeDistributionInfo(Map<String,Object> params){
        Map<String,Object> income = (Map<String, Object>) params.get("incomeData");
        String user_id = MapUtils.getString(params,"account");
        income.put("account",user_id);

        income.put("yearId",MapUtils.getString(params,"yearId"));


        String subject  = JSON.toJSONString(income.get("subject"));
        income.put("subject",subject);


        String upperYearOption  = JSON.toJSONString(income.get("upperYearOption"));
        income.put("upperYearOption",upperYearOption);


        String upperYearSel = MapUtils.getString(income,"upperYearSel");
        if(StringUtil.isNotEmpty(upperYearSel)) {
            JSONArray subject_model = JSONArray.parseArray(upperYearSel);
            if (subject_model.size() == 0) {
                income.put("upperYearSel", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(upperYearSel);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                income.put("upperYearSel", memberMerge);
            }
        }else{
            income.put("upperYearSel", "");
        }

        String upperYearMoney = MapUtils.getString(income,"upperYearMoney");
        if(StringUtil.isNotEmpty(upperYearMoney)) {
            JSONArray subject_model = JSONArray.parseArray(upperYearMoney);
            if (subject_model.size() == 0) {
                income.put("upperYearMoney", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(upperYearMoney);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                income.put("upperYearMoney", memberMerge);
            }
        }else{
            income.put("upperYearMoney", "");
        }


        String thisYearOption  = JSON.toJSONString(income.get("thisYearOption"));
        income.put("thisYearOption",thisYearOption);

        String thisYearSel = MapUtils.getString(income,"thisYearSel");
        if(StringUtil.isNotEmpty(thisYearSel)) {
            JSONArray subject_model = JSONArray.parseArray(thisYearSel);
            if (subject_model.size() == 0) {
                income.put("thisYearSel", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(thisYearSel);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                income.put("thisYearSel", memberMerge);
            }
        }else{
            income.put("thisYearSel", "");
        }

        String thisYearMoney = MapUtils.getString(income,"thisYearMoney");
        if(StringUtil.isNotEmpty(thisYearMoney)) {
            JSONArray subject_model = JSONArray.parseArray(thisYearMoney);
            if (subject_model.size() == 0) {
                income.put("thisYearMoney", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(thisYearMoney);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                income.put("thisYearMoney", memberMerge);
            }
        }else{
            income.put("thisYearMoney", "");
        }

        //编辑收益及收益分配表内容
        financeMapper.saveIncomeDistributionInfo(income);


    }

    /**
     * 保存表头表尾
     * @param params
     */
    @Override
    public void saveLiabilitiesTableInfo(Map<String,Object> params){


        Map<String,Object> theadHead = (Map<String, Object>) params.get("theadData");

        String theadData  = JSON.toJSONString(theadHead.get("theadData"));
        theadHead.put("theadData",theadData);

        financeMapper.saveLiabilitiesTableHeadInfo(theadHead);

        Map<String,Object> tableSign = (Map<String, Object>) params.get("signData");

        String signData  = JSON.toJSONString(tableSign.get("signData"));
        tableSign.put("signData",signData);

        financeMapper.saveLiabilitiesTableSignInfo(tableSign);


        financeMapper.saveFaninceStatusInfo(params);


    }



}
