package com.ovit.nswy.member.certification.service.impl;

import com.ovit.nswy.member.certification.mapper.CollectMapper;
import com.ovit.nswy.member.certification.service.CollectService;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.util.TreeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private TemplateConfigService templateConfigService;

    private Logger logger = LogManager.getLogger(CollectServiceImpl.class);

    @Override
    public List<Map<String,Object>> findCollectList(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        map.put("login_account", MapUtils.getString(params,"account"));
        map.put("templateId",MapUtils.getString(params,"templateId"));
        List<Map<String,Object>> circleList = collectMapper.findCollectDictList(new HashMap<>());

        List<Map<String,Object>> groupList = collectMapper.findCollectList(params);
        if(groupList.size() ==0 || groupList == null){
            for(Map<String,Object> circle :circleList){
                map.put("pid", MapUtils.getString(circle,"pid"));
                map.put("group_name",MapUtils.getString(circle,"groupName"));
                map.put("remark","");
                collectMapper.add(map);
            }
        }
        groupList = collectMapper.findCollectList(params);

        return new TreeUtil().treeNodeList(groupList);
        
    }

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> params) {
        List<Map<String, Object>> list = null;
        try {
            list = collectMapper.queryList(params);
        } catch (Exception e) {
            logger.error("异常信息：{}", e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryNext(Map<String, Object> params) {
        List<Map<String, Object>> list = null;
        try {
            Integer id = Integer.parseInt(String.valueOf(params.get("id")));
            params.put("pid", id);
            list = collectMapper.queryNext(params);
        } catch (Exception e) {
            logger.error("异常信息：{}", e);
        }
        return list;
    }

    @Override
    public void rename(Map<String, Object> params) {
        try {
            collectMapper.rename(params);
        } catch (Exception e) {
            logger.error("异常信息：{}", e);
        }
    }

    @Override
    public void renameRemark(Map<String, Object> params) {
        try {
            collectMapper.renameRemark(params);
        } catch (Exception e) {
            logger.error("异常信息：{}", e);
        }
    }

    @Override
    public boolean del(Map<String, Object> params) {
        boolean res = true;
        try {
            Integer count = collectMapper.being(params);
            if (count > 0) {
                res = false;
            } else {
                collectMapper.del(params);
            }
        } catch (Exception e) {
            logger.error("异常信息：{}", e);
        }
        return res;
    }

    @Override
    public void add(Map<String, Object> params) {
         collectMapper.add(params);
    }

    @Override
    public void addUser(Map<String, Object> params) {
        params.put("account", MapUtils.getString(params,"login_account"));
        List<Map<String,Object>> adminList = collectMapper.findUserQueryList(params);
        if(adminList.isEmpty()){
            String templateId = MapUtils.getString(params,"templateId");
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", templateId);
            List<Map<String,Object>> list  = templateConfigService.find(historyInfo);
            if(list.size()>0){
                historyInfo.put("account", MapUtils.getString(list.get(0),"account"));
            }
            else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
            }

            historyInfo.put("templateId", templateId);
            //获取管理员配置的数据
            adminList = collectMapper.findUserQueryList(historyInfo);

            for(Map<String,Object> map:adminList){
                map.put("checked",false);
            }

            TreeUtil treeUtil = new TreeUtil();
            List<Map<String,Object>>  admin  = treeUtil.recursionNodeList(adminList);

            for(Map<String,Object> map:admin){
                map.put("login_account",MapUtils.getString(params,"login_account"));
                if(MapUtils.getBoolean(map,"checked")){
                    //查询最后一条数据的id
                    String pid = collectMapper.findMaxId();
                    map.put("pid",pid);
                }
                collectMapper.add(map);
            }
        }
        collectMapper.add(params);
    }

    /**
     * 收藏管理-> 收藏内容管理-> 选择收藏夹
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> queryFavorite(Map<String, Object> accountInfo) {
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String,Object>> nodeList = new ArrayList<>();
        Map<String,Object> resultTemp = null;
        // 首先得到pid 为-1的根节点
        List<Map<String,Object>> roots= collectMapper.queryListByPidAndAccount(accountInfo);
        logger.info("roots--{}",roots);
        if(CollectionUtils.isNotEmpty(roots))  {
            for (Map<String,Object> rootMap: roots) {
                resultTemp = new HashMap<>();
                resultTemp.put("value",rootMap.get("id"));
                resultTemp.put("label",rootMap.get("group_name"));
                if(!collectMapper.queryChildNode(Integer.parseInt(rootMap.get("id").toString())).isEmpty()) {
                    resultTemp.put("children", getChildCollect(Integer.parseInt(rootMap.get("id").toString())));
                }
                nodeList.add(resultTemp);
            }
        } else {
            nodeList = new ArrayList<>();
        }
        return nodeList;
    }

    @Override
    public List<Map<String, Object>> isExistData(Map<String, Object> params) {
        return collectMapper.isExistData(params);
    }


    /**
     * 按照account查找目录结构
     * @param
     * @return
     */
    @Override
    public Map<String, Object> queryAll(Map<String,Object> accountInfo) {
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String,Object>> nodeList = new ArrayList<>();
        Map<String,Object> resultTemp = null;
        // 首先得到pid 为-1的根节点
        List<Map<String,Object>> roots= collectMapper.queryListByPidAndAccount(accountInfo);
        logger.info("roots--{}",roots);
        if(roots.isEmpty()){
            resultMap.put("tree","");
            return resultMap;
        }
        if(!roots.isEmpty())  {
            for (Map rootMap: roots) {
                resultTemp = new HashMap<>();
                resultTemp.put("id",rootMap.get("id"));
                resultTemp.put("title",rootMap.get("group_name"));
                resultTemp.put("remark",rootMap.get("remark"));
                resultTemp.put("pid",rootMap.get("pid"));
                resultTemp.put("expand",true);
                if(collectMapper.queryChildNode(Integer.parseInt(rootMap.get("id").toString())).isEmpty()){
                    String[] childrenInit = new String[0];
                    resultTemp.put("children",childrenInit);
                }else {
                    resultTemp.put("children",getChildNode(Integer.parseInt(rootMap.get("id").toString())));
                }
                nodeList.add(resultTemp);
            }
            resultMap.put("tree",nodeList);
        }
        return resultMap;
    }

    @Override
    public boolean isExist(Map<String, Object> params) {
        List<Map<String, Object>> list = collectMapper.selectByAccountAndPidAndTitle(params);
        if(list.isEmpty())
            return false;
        return true;
    }

    @Override
    public void updata(Map<String, Object> params) {
        collectMapper.update(params);
    }

    @Override
    public List<Map<String, Object>> findByName(Map<String, Object> params) {
        return collectMapper.findByName( params);
    }

    List<Map<String, Object>> getChildNode(int id) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> tempList = collectMapper.queryChildNode(id);
        logger.info("id--{}--tempList--{}", id, tempList);
        Map<String, Object> map = null;
        if (!tempList.isEmpty()) {
            for (Map tempMap : tempList) {
                map = new HashMap<>();
                map.put("id",tempMap.get("id"));
                map.put("title",tempMap.get("group_name"));
                map.put("remark",tempMap.get("remark"));
                map.put("pid",tempMap.get("pid"));
                map.put("expand",true);
                if (collectMapper.queryChildNode(Integer.parseInt(tempMap.get("id").toString())).isEmpty()) {
                    String[] childrenInit = new String[0];
                    map.put("children", childrenInit);
                } else {
                    map.put("children", getChildNode(Integer.parseInt(tempMap.get("id").toString())));
                }
                result.add(map);
            }
        }
        return result;
    }

    List<Map<String, Object>> getChildCollect(int id) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> tempList = collectMapper.queryChildNode(id);
        logger.info("id--{}--tempList--{}", id, tempList);
        Map<String, Object> map = null;
        if (!tempList.isEmpty()) {
            for (Map tempMap : tempList) {
                map = new HashMap<>();
                map.put("value",tempMap.get("id"));
                map.put("label",tempMap.get("group_name"));
                if (!collectMapper.queryChildNode(Integer.parseInt(tempMap.get("id").toString())).isEmpty()) {
                    map.put("children", getChildCollect(Integer.parseInt(tempMap.get("id").toString())));
                }
                result.add(map);
            }
        }
        return result;
    }

}