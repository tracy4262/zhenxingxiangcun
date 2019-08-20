package com.ovit.nswy.member.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface KeywordMapper {

    @Select("select  words from  filter_words")
    List<String> listKeyWords();
}