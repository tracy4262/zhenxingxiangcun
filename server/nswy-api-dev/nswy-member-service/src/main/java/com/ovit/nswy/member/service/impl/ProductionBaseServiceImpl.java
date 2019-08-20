package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.ProductionBaseMapper;
import com.ovit.nswy.member.service.ProductionBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 生产基地管理service
 *
 * @author haoWen
 * @create 2017-12-19 11:44
 **/
@Service
public class ProductionBaseServiceImpl implements ProductionBaseService{
    @Autowired
    private ProductionBaseMapper productionBaseMapper;

    @Override
    public int insertProductionBase(Map<String, Object> params) {
        return productionBaseMapper.insertProductionBase(params);
    }


    @Override
    public PageInfo<List<Map<String, Object>>> selectAll(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(String.valueOf(params.get("pageNum"))),Integer.parseInt(String.valueOf(params.get("pageSize"))));
        List<Map<String, Object>> listAll = productionBaseMapper.selectAll(params);
        return new PageInfo(listAll);
    }

    @Override
    public Map<String, Object> selectDetailByProductId(Map<String, Object> params) {
        return productionBaseMapper.selectDetailByProductId(params);
    }

    @Override
    public Integer updateProductBase(Map<String, Object> params) {
        return productionBaseMapper.updateProductBase(params);
    }

    @Override
    public Map<String, Object> queryByProductId(Map<String, Object> params) {
        return productionBaseMapper.queryByProductId(params);
    }

    @Override
    public void delete(Map<String, Object> params) {
        productionBaseMapper.delete(params);
    }

    @Override
    public List<Map<String, Object>> queryByAccount(Map<String, Object> params) {
        return productionBaseMapper.queryByAccount(params);
    }

    @Override
    public void update(Map<String, Object> params) {
        productionBaseMapper.update(params);
    }

    @Override
    public List<Map<String, Object>> queryIsExist(Map<String, Object> params) {
        return productionBaseMapper.queryIsExist(params);
    }
}
