package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.MyDyNamic;
import com.ovit.nswy.member.model.MyFollow;
import com.ovit.nswy.member.model.MydynamicComment;

import java.util.Map;

public interface MydynamicService {

    void saveMyDyNamic(MyDyNamic info);

    PageInfo<MyDyNamic> findMyDyNamic(int pageNum, int pageSize, Map<String, String> map);

    PageInfo<MyDyNamic> findMyDyNamicAsc(int pageNum, int pageSize, Map<String, String> map);

    void saveMyDyNamicComment(MydynamicComment info);

    MyDyNamic updateMyDyNamic(Integer id, int thumbUpNum);

    PageInfo<MyFollow> findFollowInforMation(int pageNum, int pageSize, String account);

    PageInfo<MyFollow> findFollowKnowLedge(int pageNum, int pageSize, String account);

    PageInfo<MyFollow> findFollowPolicy(int pageNum, int pageSize, String account);

}
