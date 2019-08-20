package com.ovit.nswy.member.web;

import com.ovit.nswy.member.service.BaiduMapService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation.ANONYMOUS.required;

/**
 * 地图导航
 *
 * @author chenqim
 * @create 2018-01-05 11:31
 **/
@RestController
@RequestMapping("/map-navigation")
public class BaiduMapController {
    private Logger logger = LogManager.getLogger(this.getClass());
    // 生产基地基本信息
    @Autowired
    private BaiduMapService baiduMapService;

    // 地球半径（单位千米）
    private static double EARTH_RADIUS = 6378.137;

    @Value("${baidu_map.range}")
    private String range;

    /**
     * 查询坐标点附近生产基地、企业、机关、专家
     * @param params
     * @return
     */
    @RequestMapping(value = "/query-nearby",method = RequestMethod.POST)
    public Result queryNearby (@RequestBody(required=false) Map<String, Object> params) {
        Result result = null;
        try {
            result = new Result();
            logger.debug("要查询的中心点坐标，参数：{}",params);
            /*if (StringUtils.isBlank(MapUtils.getString(params, "name"))&&StringUtils.isBlank(MapUtils.getString(params, "label"))) {
                //暂时不从前台传数据，数据写死，中心点定为武汉光谷信息技术股份有限公司，坐标为114.549804,30.486622
                params = new HashMap<>();
                params.put("coordinate","114.549804,30.486622");
            }*/

            //获取企业、专家、机关、生产基地的相关数据
            List<Map<String, Object>> list = baiduMapService.selectAll(params);
            List<Map<String, Object>> lists = new ArrayList<>();
            for(Map<String, Object> map :list){
                Map<String,Object> centers = new HashMap<>();
                Map<String,Object> center = new HashMap<>();
                //过滤出指定范围内的数据
                String centerCoordinate = MapUtils.getString(map,"coordinate");
                if(StringUtils.isNotBlank(centerCoordinate)){
                    String[] centerCoordinateArray = centerCoordinate.split(",");
                    // 中心点经度
                    Double centerLongitude = Double.parseDouble(centerCoordinateArray[0]);
                    // 中心点纬度
                    Double centerLatitude = Double.parseDouble(centerCoordinateArray[1]);
                    center.put("lng",centerLongitude);
                    center.put("lat",centerLatitude);
                    centers.put("point",center);
                }else{
                    centers.put("point",center);
                }
                String position  = MapUtils.getString(map,"position");
                String endCity;
                if(StringUtils.isNotBlank(position)){
                    int i =  position.indexOf("/");
                    if(i>0){
                        String[] positions =  position.split("/");
                        if(positions.length>3){
                            endCity = positions[2];
                        }else{
                            endCity = positions[1];
                        }
                        centers.put("endCity",endCity);

                    }else{
                        centers.put("endCity","");
                    }

                }else{
                    centers.put("endCity","");
                }
                centers.put("name",MapUtils.getString(map,"detailName"));
                centers.put("end",position);
                centers.put("checked",false);
                centers.put("description",MapUtils.getString(map,"description"));
                centers.put("account",MapUtils.getString(map,"account"));
                lists.add(centers);
            }
            if (StringUtils.isNotBlank(MapUtils.getString(params, "name"))) {
                //搜索查询的相关的企业、专家、机关、生产基地数据
                logger.info("搜索的内的企业、专家、机关、生产基地：{}", lists);
                result.setData(lists);
            }else if(StringUtils.isNotBlank(MapUtils.getString(params, "label"))){
                //搜索查询的相关的企业、专家、机关、生产基地数据
                logger.info("搜索的内的企业、专家、机关、生产基地：{}", lists);
                result.setData(lists);
            } else {
                //过滤出指定范围内的数据
                String centerCoordinate = MapUtils.getString(params,"coordinate");
                String[] centerCoordinateArray = centerCoordinate.split(",");
                // 中心点经度
                Double centerLongitude = Double.parseDouble(centerCoordinateArray[0]);
                // 中心点纬度
                Double centerLatitude = Double.parseDouble(centerCoordinateArray[1]);
                List<Map<String, Object>> dataList = getPositionInRange(lists, centerLatitude, centerLongitude);
                logger.info("指定范围内的企业、专家、机关、生产基地：{}", dataList);
                result.setData(dataList);
            }

        } catch (Exception e) {
            logger.error("查询生产基地基本信息失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    public static List<Map<String,String>> addressResolution(String address){
        String regex="(?<province>)(?<city>[^市/]+市|.+自治州)(?<county>[^县/]+县|.+区)?(?<town>[^区/]+区|.+镇)?(?<village>.*)";
        Matcher m=Pattern.compile(regex).matcher(address);
        String province=null,city=null,county=null,town=null,village=null;
        List<Map<String,String>> table=new ArrayList<Map<String,String>>();
        Map<String,String> row=null;
        while(m.find()){
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
            row.put("province", province==null?"":province.trim());
            city=m.group("city");
            row.put("city", city==null?"":city.trim());
            county=m.group("county");
            row.put("county", county==null?"":county.trim());
            town=m.group("town");
            row.put("town", town==null?"":town.trim());
            village=m.group("village");
            row.put("village", village==null?"":village.trim());
            table.add(row);
        }
        return table;
    }



    /**
     * 查询指定坐标指定范围内的企业、专家、机关、生产基地信息
     * @param dataList
     * @param centerLatitude
     * @param centerLongitude
     * @return
     */
    private List<Map<String, Object>> getPositionInRange(List<Map<String, Object>> dataList, Double centerLatitude, Double centerLongitude) {
        List<Map<String, Object>> filterData = new ArrayList<>();
        for (Map<String, Object> map : dataList) {
            Map<String,Object> coordinate = MapUtils.getMap(map,"point");
            if (coordinate.size()>0) {
                //(企业、专家、机关、生产基地)经度
                Double longitude = Double.parseDouble(MapUtils.getString(coordinate,"lng"));
                //(企业、专家、机关、生产基地)纬度
                Double latitude = Double.parseDouble(MapUtils.getString(coordinate,"lat"));
                logger.debug("查询出的经度：{}, 纬度：{}", longitude, latitude);
                Double r = getDistance(latitude, longitude, centerLatitude, centerLongitude);
                if (r < Double.parseDouble(range)) {
                    filterData.add(map);
                } else {
                    logger.debug("不在{}的指定范围内", range);
                }
            } else {
                logger.info("坐标数据为空：{}", coordinate);
            }
        }
        return filterData;
    }

    private static double getRadian(double degree) {
        return degree * Math.PI / 180.0;
    }


    /**
     * 根据经纬度计算两点之间的距离
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = getRadian(lat1);
        double radLat2 = getRadian(lat2);
        double a = radLat1 - radLat2;// 两点纬度差
        double b = getRadian(lng1) - getRadian(lng2);// 两点的经度差
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s;
    }

}
