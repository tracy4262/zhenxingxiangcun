package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.MyFavorite;

import java.util.List;
import java.util.Map;

public interface MyFavoriteMapper {

    void insert(MyFavorite record);

    List<MyFavorite> findCollect(MyFavorite record);

    int findMyMessage(String userId);

    void delMyFavorite(MyFavorite record);

    void updateMyFavorite(MyFavorite record);

    void delFollow(Integer id);

    void updateCollect(MyFavorite myFavorite);

    List<Map<String,Object>> findCollectContent(Map<String,Object> params);
}