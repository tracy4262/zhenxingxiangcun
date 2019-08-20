package com.ovit.nswy.member.service;

import java.util.Map;

public interface WeatherConditionsService {
    Map<String,Object> queryWeatherConditionsByProductId(Map<String, Object> params);

    Integer saveWeatherConditions(Map<String, Object> params);
}
