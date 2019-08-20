package com.ovit.nswy.member.service;



import com.ovit.nswy.member.model.PageList;

import java.util.List;
import java.util.Map;


public interface ExpertTypeService {
    List<Map<String,Object>> findExpertType(Map<String,Object> params);

}
