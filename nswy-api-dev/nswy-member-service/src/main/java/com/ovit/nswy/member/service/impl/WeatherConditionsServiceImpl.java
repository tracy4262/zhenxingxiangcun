package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.WeatherConditionsMapper;
import com.ovit.nswy.member.service.WeatherConditionsService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 气候条件信息
 *
 * @author haoWen
 * @create 2017-12-25 16:36
 **/
@Service
public class WeatherConditionsServiceImpl implements WeatherConditionsService {
    @Autowired
    private WeatherConditionsMapper weatherConditionsMapper;
    @Override
    public Map<String, Object> queryWeatherConditionsByProductId(Map<String, Object> params) {
        Map<String, Object> weatherConditionsMap = weatherConditionsMapper.queryWeatherConditionsByProductId(params);
        if (MapUtils.isNotEmpty(weatherConditionsMap)) {
            String climaticZone = MapUtils.getString(weatherConditionsMap,"climaticZone");
            String totalAnnualRadiation = MapUtils.getString(weatherConditionsMap,"totalAnnualRadiation");
            String avgSunshineTime = MapUtils.getString(weatherConditionsMap,"avgSunshineTime");
            String avgSunshineTimeFoot = MapUtils.getString(weatherConditionsMap,"avgSunshineTimeFoot");
            String yearAvgTemperature = MapUtils.getString(weatherConditionsMap,"yearAvgTemperature");
            String yearAvgTemperatureFoot = MapUtils.getString(weatherConditionsMap,"yearAvgTemperatureFoot");
            String accumulatedTemperature = MapUtils.getString(weatherConditionsMap,"accumulatedTemperature");
            String accumulatedTemperatureFoot = MapUtils.getString(weatherConditionsMap,"accumulatedTemperatureFoot");
            String dailyTemperatureDifference = MapUtils.getString(weatherConditionsMap,"dailyTemperatureDifference");
            String dailyTemperatureDifferenceFoot = MapUtils.getString(weatherConditionsMap,"dailyTemperatureDifferenceFoot");
            String maxTemperature = MapUtils.getString(weatherConditionsMap,"maxTemperature");
            String maxTemperatureFoot = MapUtils.getString(weatherConditionsMap,"maxTemperatureFoot");
            String minTemperature = MapUtils.getString(weatherConditionsMap,"minTemperature");
            String minTemperatureFoot = MapUtils.getString(weatherConditionsMap,"minTemperatureFoot");
            String avgMaxTemperature = MapUtils.getString(weatherConditionsMap,"avgMaxTemperature");
            String avgMaxTemperatureFoot = MapUtils.getString(weatherConditionsMap,"avgMaxTemperatureFoot");
            String avgMinTemperature = MapUtils.getString(weatherConditionsMap,"avgMinTemperature");
            String avgMinTemperatureFoot = MapUtils.getString(weatherConditionsMap,"avgMinTemperatureFoot");
            String frostFreeSeason = MapUtils.getString(weatherConditionsMap,"frostFreeSeason");
            String frostFreeSeasonFoot = MapUtils.getString(weatherConditionsMap,"frostFreeSeasonFoot");
            String avgAnnualPrecipitation = MapUtils.getString(weatherConditionsMap,"avgAnnualPrecipitation");
            String avgAnnualPrecipitationFoot = MapUtils.getString(weatherConditionsMap,"avgAnnualPrecipitationFoot");
            String avgEvaporationCapacity = MapUtils.getString(weatherConditionsMap,"avgEvaporationCapacity");
            String avgEvaporationCapacityFoot = MapUtils.getString(weatherConditionsMap,"avgEvaporationCapacityFoot");
            String avgPrecipitationDay = MapUtils.getString(weatherConditionsMap,"avgPrecipitationDay");
            String avgPrecipitationDayFoot = MapUtils.getString(weatherConditionsMap,"avgPrecipitationDayFoot");
            String avgDryness = MapUtils.getString(weatherConditionsMap,"avgDryness");
            String avgWettability = MapUtils.getString(weatherConditionsMap,"avgWettability");
            String precipitationConcentration = MapUtils.getString(weatherConditionsMap,"precipitationConcentration");
            String precipitationConcentrationFoot = MapUtils.getString(weatherConditionsMap,"precipitationConcentrationFoot");
            String naturalHazard = MapUtils.getString(weatherConditionsMap,"naturalHazard");
            StringBuffer describe = new StringBuffer();

            if (StringUtils.isNotEmpty(climaticZone)) {
                describe.append("基地属于").append(climaticZone);
            }
            if (StringUtils.isNotEmpty(totalAnnualRadiation)) {
                describe.append("，全年总辐射量为").append(totalAnnualRadiation).append("千卡/平方厘米");
            }
            if (StringUtils.isNotEmpty(avgSunshineTime) && StringUtils.isNotEmpty(avgSunshineTimeFoot)) {
                describe.append("，全年平均日照总时数为").append(avgSunshineTime).append("到").append(avgSunshineTimeFoot).append("小时");
            }
            if (StringUtils.isNotEmpty(yearAvgTemperature) && StringUtils.isNotEmpty(yearAvgTemperatureFoot)) {
                describe.append("，该地区的年平均气温为").append(yearAvgTemperature).append("到").append(yearAvgTemperatureFoot).append("℃");
            }
            if (StringUtils.isNotEmpty(accumulatedTemperature) && StringUtils.isNotEmpty(accumulatedTemperatureFoot)) {
                describe.append("，≥10℃年积温为").append(accumulatedTemperature).append("到").append(accumulatedTemperatureFoot).append("℃之间");
            }
            if (StringUtils.isNotEmpty(dailyTemperatureDifference) && StringUtils.isNotEmpty(dailyTemperatureDifferenceFoot)) {
                describe.append("，日温差为").append(dailyTemperatureDifference).append("到").append(dailyTemperatureDifferenceFoot).append("℃之间");
            }
            if (StringUtils.isNotEmpty(maxTemperature) && StringUtils.isNotEmpty(maxTemperatureFoot)) {
                describe.append("，极端最高气温为").append(maxTemperature).append("℃，维持的日数为").append(maxTemperatureFoot).append("天");
            }
            if (StringUtils.isNotEmpty(minTemperature) && StringUtils.isNotEmpty(minTemperatureFoot)) {
                describe.append("，极端最低气温为").append(minTemperature).append("℃，维持的日数为").append(minTemperatureFoot).append("天");
            }
            if (StringUtils.isNotEmpty(avgMaxTemperature) && StringUtils.isNotEmpty(avgMaxTemperatureFoot)) {
                describe.append("，极端最高气温多年平均值为").append(avgMaxTemperature).append("℃，维持的日数为").append(avgMaxTemperatureFoot).append("天");
            }
            if (StringUtils.isNotEmpty(avgMinTemperature) && StringUtils.isNotEmpty(avgMinTemperatureFoot)) {
                describe.append("，极端最低气温多年平均值为").append(avgMinTemperature).append("℃，维持的日数为").append(avgMinTemperatureFoot).append("天");
            }
            if (StringUtils.isNotEmpty(frostFreeSeason) && StringUtils.isNotEmpty(frostFreeSeasonFoot)) {
                describe.append("，无霜期为").append(frostFreeSeason).append("到").append(frostFreeSeasonFoot).append("天");
            }
            if (StringUtils.isNotEmpty(avgAnnualPrecipitation) && StringUtils.isNotEmpty(avgAnnualPrecipitationFoot)) {
                describe.append("，该地区的年平均降水量为").append(avgAnnualPrecipitation).append("到").append(avgAnnualPrecipitationFoot).append("mm之间");
            }
            if (StringUtils.isNotEmpty(avgEvaporationCapacity) && StringUtils.isNotEmpty(avgEvaporationCapacityFoot)) {
                describe.append("，年平均蒸发量为").append(avgEvaporationCapacity).append("到").append(avgEvaporationCapacityFoot).append("mm之间");
            }
            if (StringUtils.isNotEmpty(avgPrecipitationDay) && StringUtils.isNotEmpty(avgPrecipitationDayFoot)) {
                describe.append("，年平均降水日数为").append(avgPrecipitationDay).append("到").append(avgPrecipitationDayFoot).append("天");
            }
            if (StringUtils.isNotEmpty(avgDryness)) {
                describe.append("，多年平均干燥度为").append(avgDryness);
            }
            if (StringUtils.isNotEmpty(avgWettability)) {
                describe.append("，多年平均湿润度为").append(avgWettability);
            }
            if (StringUtils.isNotEmpty(precipitationConcentration) && StringUtils.isNotEmpty(precipitationConcentrationFoot)) {
                describe.append("，降水量最集中的时期为").append(precipitationConcentration).append("到").append(precipitationConcentrationFoot).append("月");
            }
            if (StringUtils.isNotEmpty(naturalHazard)) {
                describe.append("，自然灾害主要为").append(naturalHazard);
            }
            weatherConditionsMap.put("describe",describe);
        }
        return weatherConditionsMap;
    }

    @Override
    public Integer saveWeatherConditions(Map<String, Object> params) {
        return weatherConditionsMapper.saveWeatherConditions(params);
    }
}
