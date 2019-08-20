package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.PlantingGroupMapper;
import com.ovit.nswy.member.service.PlantingGroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlantingGroupServiceImpl implements PlantingGroupService {

    @Autowired
    private PlantingGroupMapper plantingGroupMapper;
    private Logger logger = LogManager.getLogger(PlantingGroupServiceImpl.class);

    @Override
    public void add(Map<String, Object> params) {
        try {
            Integer maxLevel = plantingGroupMapper.queryMaxLevel(params);
            if (maxLevel == null) {
                params.put("level", 0);
            } else {
                params.put("level", maxLevel + 1);
            }
            plantingGroupMapper.add(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void del(int id) {
        try {
            plantingGroupMapper.del(id);
            plantingGroupMapper.delUser(id);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void rename(Map<String, Object> params) {
        try {
            plantingGroupMapper.rename(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void sorting(List<Map<String, Object>> list) {
        try {
            for (Map<String, Object> map : list) {
                plantingGroupMapper.updateLevel(map);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public List<Map<String, Object>> getList(Map<String, Object> params) {
        List<Map<String, Object>> list = null;
        try {
            list = plantingGroupMapper.getList(params);
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }
}