package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.member.model.request.MediaLibraryListQuery;
import com.ovit.nswy.member.model.request.MediaLibraryQuery;
import com.ovit.nswy.member.model.response.MediaLibraryDTO;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
public interface MediaLibraryService {
    PageInfo<MediaLibraryDTO> listMediaLibrary(MediaLibraryListQuery mediaLibraryListQuery);

    Integer saveMediaLibrary(MediaLibraryQuery mediaLibraryQuery) throws ParamValidException;

    Integer deleteMediaLibrary(Integer mediaId);

    Integer updateMediaLibrary(MediaLibraryQuery mediaLibraryQuery);

    MediaLibraryDTO getMediaLibrary(Integer mediaId);
}
