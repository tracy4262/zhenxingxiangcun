package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.ProductMediaLibraryMapper;
import com.ovit.nswy.member.model.request.MediaLibraryListQuery;
import com.ovit.nswy.member.service.ProductMediaLibraryService;
import com.ovit.nswy.member.util.PageUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生产基地相册
 *
 * @author haoWen
 * @create 2017-12-22 17:05
 **/
@Service
public class ProductMediaLibraryServiceImpl implements ProductMediaLibraryService{
    @Autowired
    private ProductMediaLibraryMapper productMediaLibraryMapper;
    @Override
    public int saveProductMediaLibrary(Map<String, Object> params) {
        return productMediaLibraryMapper.saveProductMediaLibrary(params);
    }

    @Override
    public Integer setMediaLibraryCover(Map<String, Object> params) {
        return productMediaLibraryMapper.setMediaLibraryCover(params);
    }

    @Override
    public Integer updateMediaLibrary(Map<String, Object> params) {
        return productMediaLibraryMapper.updateMediaLibrary(params);
    }

    @Override
    public Integer deleteMediaLibrary(Map<String, Object> params) {
        return productMediaLibraryMapper.deleteMediaLibrary(params);
    }

    @Override
    public PageInfo<List<Map<String, Object>>> queryAllMediaLibrary(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(String.valueOf(params.get("pageNum"))),Integer.parseInt(String.valueOf(params.get("pageSize"))));
        List<Map<String,Object>> mediaLibrary = productMediaLibraryMapper.queryAllMediaLibrary(params);
        return new PageInfo(mediaLibrary);
    }

    @Override
    public Map<String, Object>  queryByMediaName(Map<String, Object> params) {
        return productMediaLibraryMapper.queryByMediaName(params);
    }

    @Override
    public List<Map<String, Object>> queryMediaLibrary(Map<String, Object> params) {
        return productMediaLibraryMapper.queryMediaLibrary(params);
    }

    @Override
    public PageInfo<Map<String, Object>> specialMediaLibraryList(MediaLibraryListQuery mediaLibraryListQuery) {
        PageHelper.startPage(0,10);
        List<Map<String,Object>> list = productMediaLibraryMapper.specialMediaLibraryList(mediaLibraryListQuery);
        for (Map<String,Object> map : list){
            map.put("cover", productMediaLibraryMapper.queryMediaLibraryCover(MapUtils.getString(map, "mediaId")));
            map.put("countDetail",productMediaLibraryMapper.queryTotal(MapUtils.getString(map, "mediaId")));
        }
        return new PageInfo<>(list);
    }
}
