package com.ovit.nswy.member.service;


import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.PageList;

import java.util.List;
import java.util.Map;

public interface NameLibraryService {

    PageInfo<Map<String,Object>> findList(Map<String,Object> params);

    List<Map<String,Object>> findConllectionList(Map<String,Object> params);

    void saveLibrary(Map<String,Object> params);

    void deleteLibrary(Map<String,Object> params);

    PageList<Map<String,Object>> findVarietyList(Map<String,Object> params);

    PageList<Map<String,Object>> findShopCurrencyCommodity(Map<String,Object> params);

}
