package com.ovit.nswy.member.service;

import java.util.List;

public interface IndividuationService {
    List<String> selectByLeib(String list);

    String selectByName(String fieldName);
}
