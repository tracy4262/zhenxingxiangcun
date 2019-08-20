package com.ovit.nswy.member.mapper;

import java.util.List;

public interface IndividuationMapper {
    List<String> selectByLeib(String list);

    String selectByName(String fieldName);
}
