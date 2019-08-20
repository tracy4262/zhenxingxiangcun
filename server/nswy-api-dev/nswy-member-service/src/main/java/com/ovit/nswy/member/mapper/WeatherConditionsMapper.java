package com.ovit.nswy.member.mapper;

import java.util.Map;

public interface WeatherConditionsMapper {
    Map<String,Object> queryWeatherConditionsByProductId(Map<String, Object> params);

    Integer saveWeatherConditions(Map<String, Object> params);
}
