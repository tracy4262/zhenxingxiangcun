package com.ovit.nswy.member.service;


import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ProductMediaLibraryDetailService {

    Integer saveMediaLibraryDetail(Map<String, Object> params);

    Integer deleteMediaLibraryDetail(Map<String, Object> params);

    Integer updateMediaLibraryDetail(Map<String, Object> params);

    List<Map<String,Object>> query(Map<String, Object> params);

    List<Map<String,Object>> queryByProduct(Map<String, Object> params);

    PageInfo<List<Map<String,Object>>> queryList(Map<String, Object> params);

    void deleteMediaLibraryDetailByProductId(Map<String, Object> params);
}
