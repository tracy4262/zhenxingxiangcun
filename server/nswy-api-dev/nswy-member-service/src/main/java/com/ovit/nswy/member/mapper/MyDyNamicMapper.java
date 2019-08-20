package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.MyDyNamic;

import java.util.List;
import java.util.Map;

public interface MyDyNamicMapper {

    void insert(MyDyNamic record);

    List<MyDyNamic> findMyDyNamic(Map<String, String> map);

    List<MyDyNamic> findMyDyNamicAsc(Map<String, String> map);

    MyDyNamic findThumbUpNum(String id);

    void updateMyDyNamic(MyDyNamic record);

    void updateThumbUpNum(MyDyNamic record);

}