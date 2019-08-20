package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.FishingProductMapper;
import com.ovit.nswy.member.service.FishingProductService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FishingProductServiceImpl implements FishingProductService {

    @Autowired
    private FishingProductMapper fishingProductMapper;

    @Override
    public PageInfo<Map<String,Object>> findFishingProduct(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), 10);
        List<Map<String,Object>> list = fishingProductMapper.findFishingProduct(params);

        return new PageInfo<>(list);
    }

    @Override
    public void insertFishingProduct(Map<String,Object> params){
        fishingProductMapper.insertFishingProduct(params);
    }

    @Override
    public void updateFishingProduct(Map<String,Object> params){
        fishingProductMapper.updateFishingProduct(params);
    }

    @Override
    public void deleteFishingProduct(Map<String,Object> params){
        fishingProductMapper.deleteFishingProduct(params);
    }

    @Override
    public List<Map<String, Object>> findProductInfoBySpeciesId(Map<String, Object> speciesInfo) {
        return fishingProductMapper.findProductInfoBySpeciesId(speciesInfo);
    }

}
