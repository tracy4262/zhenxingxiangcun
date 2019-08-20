package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.request.MediaLibraryListQuery;

import java.util.List;
import java.util.Map;

public interface ProductMediaLibraryMapper {
    int saveProductMediaLibrary(Map<String, Object> params);

    Integer setMediaLibraryCover(Map<String, Object> params);

    Integer updateMediaLibrary(Map<String, Object> params);

    Integer deleteMediaLibrary(Map<String, Object> params);

    List<Map<String,Object>> queryAllMediaLibrary(Map<String, Object> params);

    Map<String, Object>  queryByMediaName(Map<String, Object> params);

    List<Map<String,Object>> queryMediaLibrary(Map<String, Object> params);

    List<Map<String,Object>> specialMediaLibraryList(MediaLibraryListQuery mediaLibraryListQuery);

    String queryMediaLibraryCover(String mediaId);

    int queryTotal(String mediaId);
}
