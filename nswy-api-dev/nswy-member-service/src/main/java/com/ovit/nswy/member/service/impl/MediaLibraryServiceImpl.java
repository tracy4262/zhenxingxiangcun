package com.ovit.nswy.member.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.bean.BeanByRefMapper;
import com.github.xphsc.collect.Lists;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.ObjectUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.frame.common.util.ContextHolderUtil;
import com.ovit.nswy.frame.common.util.PageInfoHelper;
import com.ovit.nswy.member.common.enums.MediaTypeEnum;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.MediaLibrary;
import com.ovit.nswy.member.model.request.MediaLibraryListQuery;
import com.ovit.nswy.member.model.request.MediaLibraryQuery;
import com.ovit.nswy.member.model.response.MediaLibraryDTO;
import com.ovit.nswy.member.service.MediaLibraryDetailService;
import com.ovit.nswy.member.service.MediaLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MediaLibraryServiceImpl extends BaseService<MediaLibrary> implements MediaLibraryService {

    @Autowired
    private MediaLibraryDetailService mediaLibraryDetailService;

    @Override
    public PageInfo<MediaLibraryDTO> listMediaLibrary(MediaLibraryListQuery mediaLibraryListQuery) {
        Example example = new Example(MediaLibrary.class);
        example.setOrderByClause("create_time DESC");
        if (StringUtil.isNotBlank(mediaLibraryListQuery.getAccount())) {
            if (Integers.isNotEmpty(mediaLibraryListQuery.getMediaType())) {
                example.createCriteria().andEqualTo("account", mediaLibraryListQuery.getAccount()).andEqualTo("mediaType", mediaLibraryListQuery.getMediaType());
            }
        }

        PageHelper.startPage(mediaLibraryListQuery.getPageNum(), mediaLibraryListQuery.getPageSize());
        List<MediaLibrary> mediaLibraries = this.findByExample(example);
        PageInfo<MediaLibrary> mediaLibraryPageInfo = new PageInfo(mediaLibraries);
        List<MediaLibraryDTO> list = BeanByRefMapper.copyByRefListMapper(mediaLibraries, MediaLibraryDTO.class);
        PageInfo<MediaLibraryDTO> pageInfo = new PageInfo(list);
        if(Lists.isNotEmpty(pageInfo.getList())){
            pageInfo.getList().stream().forEach(mediaLibraryDTO->{
                mediaLibraryDTO.getMediaTypeInfo().put("id", mediaLibraryDTO.getMediaType());
                mediaLibraryDTO.getMediaTypeInfo().put("val", MediaTypeEnum.mediaTypeEscape(mediaLibraryDTO.getMediaType()));
                Integer count = mediaLibraryDetailService.countMediaLibraryDetail(mediaLibraryDTO.getMediaId());
                mediaLibraryDTO.setCountDetail(count);
            });
        }

        pageInfo = PageInfoHelper.getPageInfo(mediaLibraryPageInfo, pageInfo);
        return pageInfo;

    }

    @Override
    public Integer saveMediaLibrary(MediaLibraryQuery mediaLibraryQuery) throws ParamValidException {
        LoginUser loginUser = (LoginUser) ContextHolderUtil.getRequest().getSession().getAttribute("currentUser");
        MediaLibrary mediaLibrary = BeanByRefMapper.copyByRefMapper(mediaLibraryQuery, MediaLibrary.class);
        mediaLibrary.setCreateTime(new Date());
        if (null != loginUser) {
            String account = loginUser.getLoginAccount();
            mediaLibrary.setAccount(account);
        }
        if (StringUtil.isNotBlank(mediaLibraryQuery.getMediaName())) {
            if (existsName(mediaLibraryQuery.getMediaName(),mediaLibrary.getAccount())) {
                throw new ParamValidException("媒体库名称已存在");
            }

        }
        if(mediaLibraryQuery.getMediaType().equals("1")){
            mediaLibrary.setPhotoAddress(mediaLibraryQuery.getPhotoAddress());
        }
        Integer rows = this.save(mediaLibrary);
        return rows;
    }

    @Override
    public MediaLibraryDTO getMediaLibrary(Integer mediaId) {
        MediaLibraryDTO mediaLibraryDTO=null;
        if(Integers.isNotEmpty(mediaId)){
            MediaLibrary mediaLibrary = this.findById(mediaId);
             mediaLibraryDTO = BeanByRefMapper.copyByRefMapper(mediaLibrary, MediaLibraryDTO.class);
        }

        return mediaLibraryDTO;
    }

    @Override
    public Integer deleteMediaLibrary(Integer mediaId) {
        Integer rows=null;
        if(Integers.isNotEmpty(mediaId)){
             rows = this.deleteById(mediaId);
        }
        return Integers.isEmpty(rows)? 0:rows;
    }

    @Override
    public Integer updateMediaLibrary(MediaLibraryQuery mediaLibraryQuery) {
        MediaLibrary mediaLibrary = BeanByRefMapper.copyByRefMapper(mediaLibraryQuery, MediaLibrary.class);
        mediaLibrary.setUpdateTime(new Date());
        Integer rows = this.updateSelective(mediaLibrary);
        return Integers.isEmpty(rows)? 0:rows;
    }

    private Boolean existsName(String name,String account) {
        Example example = new Example(MediaLibrary.class);
        example.createCriteria().andEqualTo("mediaName", name).andEqualTo("account",account);
        List<MediaLibrary> list = this.findByExample(example);
        if (Lists.isNotEmpty(list)) {
            return true;
        } else {
            return false;
        }
    }

}
