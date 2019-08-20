package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.BrowseLog;

import java.util.List;

public interface BrowseLogMapper {

    int insert(BrowseLog record);

    List<BrowseLog> findBrowseLog(BrowseLog record);

    int updateBrowseLog(BrowseLog record);
}