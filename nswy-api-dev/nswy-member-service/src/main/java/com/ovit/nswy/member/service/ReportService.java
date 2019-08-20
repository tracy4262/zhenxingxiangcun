package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.*;

import java.util.List;
import java.util.Map;

public interface ReportService {

    void insert(JuBao report);

    JuBao findReportByLinkAndAccount(JuBao report);

    MyNoTitle findMyTitle(MyNoTitle info);

    Integer findMyFriend(MyFriend info);

    List<MyFavorite> findCollect(MyFavorite info);

    PageInfo<Map<String,Object>> findCollects(Map<String, Object> params);

    String saveCollect(MyFavorite info);

    String saveFollow(MyFollow info);

    void delFavorite(MyFavorite info);

    void updateMyFavorite(MyFavorite record);

    void delFollow(Map<String, Object> params);

    boolean isExist(MyFavorite record);

    void updateCollect(MyFavorite myFavorite);
}
