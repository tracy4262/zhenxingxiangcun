package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.CollectMapper;
import com.ovit.nswy.member.service.CollectService;
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
    private Logger logger = LogManager.getLogger(CollectServiceImpl.class);

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> params) {
        List<Map<String, Object>> list = null;
        try {
            list = collectMapper.queryList(params);
        } catch (Exception e) {
            logger.error(e);
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
            logger.error(e);
        }
        return list;
    }

    @Override
    public void rename(Map<String, Object> params) {
        try {
            collectMapper.rename(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void renameRemark(Map<String, Object> params) {
        try {
            collectMapper.renameRemark(params);
        } catch (Exception e) {
            logger.error(e);
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
            logger.error(e);
        }
        return res;
    }

    @Override
    public void add(Map<String, Object> params) {
         collectMapper.add(params);
    }

    /**
     * 按照account查找目录结构
     * @param account
     * @return
     */
    @Override
    public Map<String, Object> queryAll(String account) {
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String,Object>> nodeList = new ArrayList<>();
        Map<String,Object> resultTemp =null;
        // 首先得到pid 为-1 的根节点
        List<Map<String,Object>> roots= collectMapper.queryListByPidAndAccount(account);
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
        collectMapper.updata(params);
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
}