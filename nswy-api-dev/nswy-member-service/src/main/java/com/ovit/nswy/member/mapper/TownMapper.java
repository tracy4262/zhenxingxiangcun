package com.ovit.nswy.member.mapper;

import java.util.List;

import com.ovit.nswy.member.model.Town;
public interface TownMapper {

    List<Town> findTownByParentId(String parentId);

    List<Town> findAllTown();
}