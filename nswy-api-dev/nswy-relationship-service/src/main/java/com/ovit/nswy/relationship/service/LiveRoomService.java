package com.ovit.nswy.relationship.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.relationship.model.request.ListLiveQuery;
import com.ovit.nswy.relationship.model.request.LiveCategoryQuery;
import com.ovit.nswy.relationship.model.request.LiveQuery;
import com.ovit.nswy.relationship.model.response.LiveRoomDTO;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-10.
 */
public interface LiveRoomService {

    public PageInfo<LiveRoomDTO> listlive(ListLiveQuery listLiveQuery);
    public Integer saveOrUpdateCategory(LiveCategoryQuery liveCategoryQuery);
    public Integer saveOrUpdateLive(LiveQuery liveQuery);
    public LiveRoomDTO getLivebyliveId(Integer liveId);

    Map<String,Object> findLiveListByAccount(ListLiveQuery listLiveQuery);
}
