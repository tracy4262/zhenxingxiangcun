package com.ovit.shop.pushTemplate.service;

import java.util.List;
import java.util.Map;

public interface MallClassificationService {

    List<Map<String,Object>> findMallClassification(Map<String,Object> params);

}
