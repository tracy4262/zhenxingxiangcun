package com.ovit.nswy.member.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.ColumnSettingsMapper;
import com.ovit.nswy.member.mapper.InformationDetailMapper;
import com.ovit.nswy.member.mapper.KnowledgeDetailMapper;
import com.ovit.nswy.member.mapper.PolicyDetailMapper;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.model.request.ColumnSettingsUtils;
import com.ovit.nswy.member.model.request.UserSettingsRequest;
import com.ovit.nswy.member.service.ColumnSettingService;
import com.ovit.nswy.member.service.VideoImgService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ColumnSettingServiceImpl implements ColumnSettingService {
    @Autowired
    private ColumnSettingsMapper ColumnSettingDao;
    @Autowired
    private InformationDetailMapper informationDetailMapper;
    @Autowired
    private PolicyDetailMapper policyDetailMapper;
    @Autowired
    private KnowledgeDetailMapper knowledgeDetailMapper;

    @Autowired
    private VideoImgService videoImgService;

    @Override
    public int insert(List<ColumnSettings> record) {
        return ColumnSettingDao.insert(record);
    }

    @Override
    public List<ColumnSettings> findColumnSetting(ColumnSettings record) {
        return ColumnSettingDao.findColumnSetting(record);
    }

	@Override
	public List<UserSettingsRequest> findColumnSet(ColumnSettings record) {
        //查询是否存在该客户数据
		List<ColumnSettings> columnList = ColumnSettingDao.findColumnSet(record);
		if(columnList != null && columnList.size() > 0){
            ColumnSettings userSettings = columnList.get(0);
            if(userSettings != null){
                String label = userSettings.getLabel();
                if(StringUtils.isNotBlank(label)){
                    //存在记录
                    return ColumnSettingsUtils.json2List(label);

                }else{
                    //返回默认数据
                    return ColumnSettingsUtils.CreateDefaultUserSettingsColumn();
                }
            }
        }else{
            return ColumnSettingsUtils.CreateDefaultUserSettingsColumn();
        }
		return null;
	}

    @Override
    public PageInfo<PolicyDetail> findColumnPolicy(int pageNum, PolicyDetail record) {
        PageHelper.startPage(pageNum, 10);
        List<PolicyDetail> list = policyDetailMapper.findColumnPolicy(record);
        return new PageInfo<PolicyDetail>(list);
    }

    @Override
    public PageInfo<PolicyDetail> findPolicyDetailLeftPolicy(int pageNum, PolicyDetail record) {
        PageHelper.startPage(pageNum, 10);
        List<PolicyDetail> list = policyDetailMapper.findPolicyDetailLeftPolicy(record);
        for(PolicyDetail polic :list){
            polic.setDataType("政策");
            Map<String,Object> vmap=new HashMap<>();
            vmap.put("column","政策");
            vmap.put("relativeId",polic.getId());
            List<VideoImg> videoImgs=videoImgService.selectByRelativeId(vmap);
            if(!videoImgs.isEmpty()){
                polic.setVideoImgs(videoImgs);
            }
        }
        return new PageInfo<PolicyDetail>(list);
    }

    @Override
    public List<PolicyDetail> findPolicyDetailLeftPolicys(PolicyDetail record) {

        List<PolicyDetail> list = policyDetailMapper.findPolicyDetailLeftPolicy(record);
        for(PolicyDetail polic :list){
            polic.setDataType("政策");
            Map<String,Object> vmap=new HashMap<>();
            vmap.put("column","政策");
            vmap.put("relativeId",polic.getId());
            List<VideoImg> videoImgs=videoImgService.selectByRelativeId(vmap);
            if(!videoImgs.isEmpty()){
                polic.setVideoImgs(videoImgs);
            }
        }
        return list;
    }

    @Override
    public PageInfo<KnowledgeDetail> findColumnKonwledge(int pageNum, KnowledgeDetail record) {
        PageHelper.startPage(pageNum, 10);
        List<KnowledgeDetail> list = knowledgeDetailMapper.findColumnKnow(record);
        return new PageInfo<KnowledgeDetail>(list);
    }

    @Override
    public PageInfo<KnowledgeDetail> findKnowledgeDetailLeftKnowledge(int pageNum, KnowledgeDetail record) {
        PageHelper.startPage(pageNum, 10);
        List<KnowledgeDetail> list = knowledgeDetailMapper.findKnowledgeDetailLeftKnowledge(record);
        for(KnowledgeDetail know :list){
            know.setDataType("知识");
            Map<String,Object> map=new HashMap<>();
            map.put("column","知识");
            map.put("relativeId",know.getId());
            List<VideoImg> videoImgs=videoImgService.selectByRelativeId(map);
            if(!videoImgs.isEmpty()){
                know.setVideoImgs(videoImgs);
            }
        }
        return new PageInfo<KnowledgeDetail>(list);
    }

    @Override
    public List<KnowledgeDetail> findKnowledgeDetailLeftKnowledges(KnowledgeDetail record) {

        List<KnowledgeDetail> list = knowledgeDetailMapper.findKnowledgeDetailLeftKnowledge(record);
        for(KnowledgeDetail know :list){
            know.setDataType("知识");
            Map<String,Object> map=new HashMap<>();
            map.put("column","知识");
            map.put("relativeId",know.getId());
            List<VideoImg> videoImgs=videoImgService.selectByRelativeId(map);
            if(!videoImgs.isEmpty()){
                know.setVideoImgs(videoImgs);
            }
        }
        return list;
    }


    @Override
    public void updateLabel(ColumnSettings record) {
        ColumnSettingDao.updateLabel(record);
    }

    @Override
    public List<ColumnSettings> findByAccount(String account) {
        return ColumnSettingDao.findByAccount(account);
    }

    @Override
    public void updateByColumn(List<ColumnSettings> record) {
        ColumnSettingDao.updateByColumn(record);
    }

    @Override
    public void insertByNewVersion(ColumnSettings record) {
        ColumnSettingDao.insertByNewVersion(record);
    }

    /**
     * 会员中心：添加联系人
     * @param params
     */
    @Override
    public void saveContact(Map<String, Object> params) {

        ColumnSettingDao.saveContact(params);

    }

    /**
     * 会员中心---联系人查询
     * @param params
     */
    @Override
    public List<Map<String, Object>> findContact(Map<String, Object> params) {

        List<Map<String, Object>> contactList = ColumnSettingDao.findContact(params);
        if (CollectionUtils.isNotEmpty(contactList)) {

            for (Map<String,Object> contact : contactList) {
                String safeFormData = MapUtils.getString(contact, "safeFormData");
                if (org.apache.commons.lang3.StringUtils.isNotBlank(safeFormData)) {
                    JSONArray dataArray = JSONArray.parseArray(safeFormData);
                    contact.put("safeFormData", dataArray);
                } else {
                    contact.put("safeFormData", new String[0]);
                }
            }
        } else {
            contactList = new ArrayList<>();
        }

        return contactList;
    }

    /**
     * 会员中心---删除联系人
     * @param params
     */
    @Override
    public void deleteContact(Map<String, Object> params) {

        ColumnSettingDao.deleteContact(params);

    }

    @Override
	public void deleteByAccount(String account) {
		ColumnSettingDao.deleteByAccount(account);
	}

    @Override
    public void deleteByColumn(ColumnSettings record) {
        ColumnSettingDao.deleteByColumn(record);
    }

    @Override
    public ColumnSettings findColumns(ColumnSettings record) {
        return ColumnSettingDao.findColumns(record);
    }

    @Override
    public PageInfo<InformationDetail> findColumnInformation(int pageNum, Map<String, Object> map) {
        PageHelper.startPage(pageNum, 10);
        List<InformationDetail> list = informationDetailMapper.findColumnInforMation(map);
        return new PageInfo<InformationDetail>(list);
    }

    @Override
    public PageInfo<InformationDetail> findInforMationDetailLeftInforMation(int pageNum, Map<String, Object> map) {
        PageHelper.startPage(pageNum, 10);
        List<InformationDetail> list = informationDetailMapper.findInforMationDetailLeftInforMation(map);
        for(InformationDetail infor :list){
            infor.setDataType("动态");
            Map<String,Object> vmap=new HashMap<>();
            vmap.put("column","动态");
            vmap.put("relativeId",infor.getId());
            List<VideoImg> videoImgs=videoImgService.selectByRelativeId(vmap);
            if(!videoImgs.isEmpty()){
                infor.setVideoImgs(videoImgs);
            }
        }
        return new PageInfo<InformationDetail>(list);
    }


    @Override
    public List<InformationDetail> findInforMationDetailLeftInforMations( Map<String, Object> map) {

        List<InformationDetail> list = informationDetailMapper.findInforMationDetailLeftInforMation(map);
        for(InformationDetail infor :list){
            infor.setDataType("动态");
            Map<String,Object> vmap=new HashMap<>();
            vmap.put("column","动态");
            vmap.put("relativeId",infor.getId());
            List<VideoImg> videoImgs=videoImgService.selectByRelativeId(vmap);
            if(!videoImgs.isEmpty()){
                infor.setVideoImgs(videoImgs);
            }
        }
        return list;
    }
}
