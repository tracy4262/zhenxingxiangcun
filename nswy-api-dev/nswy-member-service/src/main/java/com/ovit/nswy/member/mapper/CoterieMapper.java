package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.Coterie;

import java.util.List;
import java.util.Map;

public interface CoterieMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Coterie record);

    Coterie selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Coterie record);

    List<Coterie> searchCoterie(Map<String, String> map);
}