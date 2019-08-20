package com.ovit.nswy.portal.mapper;

import java.util.List;
import java.util.Map;

public interface StandardMapper {
    List<Map<String,Object>> queryStandardList(Map<String, Object> params);

    List<Map<String, Object>> findResidueStandard(Map<String, Object> params);

    List<Map<String, Object>> findCurrencyCommodity(Map<String, Object> params);

    List<Map<String, Object>> findUnitStandard(Map<String, Object> params);

    List<Map<String, Object>> findUnit(String parentId);

    List<Map<String, Object>> findBasicUnit(Map<String, Object> params);

    List<Map<String, Object>> findProductClassification(Map<String, Object> params);

    List<Map<String, Object>> findMallClassification(Map<String, Object> params);

    List<Map<String, Object>> isExist(Map<String, Object> params);

}
