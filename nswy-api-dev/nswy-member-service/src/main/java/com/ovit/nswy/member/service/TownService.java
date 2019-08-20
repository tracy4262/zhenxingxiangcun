package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.Town;

import java.util.List;

public interface TownService {

    List<Town> findTownByParentId(String parentId);

    List<Town> findAllTown();
}
