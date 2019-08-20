package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.ProductMediaLibraryDetailMapper;
import com.ovit.nswy.member.service.ProductMediaLibraryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 生产基地照片
 *
 * @author haoWen
 * @create 2017-12-22 17:06
 **/
@Service
public class ProductMediaLibraryDetailServiceImpl implements ProductMediaLibraryDetailService{
    @Autowired
    private ProductMediaLibraryDetailMapper productMediaLibraryDetailMapper;
    @Override
    public Integer saveMediaLibraryDetail(Map<String, Object> params) {
        return productMediaLibraryDetailMapper.saveMediaLibraryDetail(params);
    }

    @Override
    public Integer deleteMediaLibraryDetail(Map<String, Object> params) {
        return productMediaLibraryDetailMapper.deleteMediaLibraryDetail(params);
    }

    @Override
    public Integer updateMediaLibraryDetail(Map<String, Object> params) {
        return productMediaLibraryDetailMapper.updateMediaLibraryDetail(params);
    }

    @Override
    public List<Map<String, Object>> query(Map<String, Object> params) {
        List<Map<String, Object>> map = productMediaLibraryDetailMapper.query(params);
        return map;
    }

    @Override
    public List<Map<String,Object>> queryByProduct(Map<String, Object> params) {
        return productMediaLibraryDetailMapper.queryByProductId(params);
    }

    @Override
    public PageInfo<List<Map<String, Object>>> queryList(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(String.valueOf(params.get("pageNum"))),Integer.parseInt(String.valueOf(params.get("pageSize"))));
        List<Map<String, Object>> map = productMediaLibraryDetailMapper.queryList(params);
        return new PageInfo(map);
    }

    @Override
    public void deleteMediaLibraryDetailByProductId(Map<String, Object> params) {
        productMediaLibraryDetailMapper.deleteMediaLibraryDetailByProductId(params);
    }
}
