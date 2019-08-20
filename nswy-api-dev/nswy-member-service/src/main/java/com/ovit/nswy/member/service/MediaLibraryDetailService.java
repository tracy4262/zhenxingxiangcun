package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.request.MediaLibraryDetailListQuery;
import com.ovit.nswy.member.model.request.MediaLibraryDetailQuery;
import com.ovit.nswy.member.model.response.MediaLibraryDetaiDTO;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
public interface MediaLibraryDetailService {
    Integer countMediaLibraryDetail(Integer mediaId);

    Integer saveMediaLibraryDetail(MediaLibraryDetailQuery mediaLibraryDetailQuery);

    PageInfo<MediaLibraryDetaiDTO> listMediaLibraryDetail(MediaLibraryDetailListQuery mediaLibraryDetailListQuery);

    Integer deleteMediaLibraryDetail(Integer id);

    Integer updateMediaLibraryDetail(MediaLibraryDetailQuery mediaLibraryDetailQuery);

    MediaLibraryDetaiDTO gettMediaDetail(Integer id);

    void saveBookInfo(Map<String,Object> params);

    PageInfo<Map<String,Object>> findMediaBookList(Map<String,Object> params);

    void deleteMediaBook(Integer id);

}
