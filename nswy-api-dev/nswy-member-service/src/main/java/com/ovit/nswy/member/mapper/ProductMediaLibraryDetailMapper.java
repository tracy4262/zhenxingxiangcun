package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface ProductMediaLibraryDetailMapper {
    Integer saveMediaLibraryDetail(Map<String, Object> params);

    Integer deleteMediaLibraryDetail(Map<String, Object> params);

    Integer updateMediaLibraryDetail(Map<String, Object> params);

    List<Map<String,Object>> query(Map<String, Object> params);

    List<Map<String,Object>> queryByProductId(Map<String, Object> params);

    List<Map<String,Object>> queryList(Map<String, Object> params);

    void deleteMediaLibraryDetailByProductId(Map<String, Object> params);
}
