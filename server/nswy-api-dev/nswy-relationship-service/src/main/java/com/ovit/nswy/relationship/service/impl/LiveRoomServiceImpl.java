package com.ovit.nswy.relationship.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.bean.BeanByRefMapper;
import com.github.xphsc.collect.Lists;
import com.github.xphsc.collect.Maps;
import com.github.xphsc.http.HttpUtil;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.ObjectUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.frame.common.util.PageInfoHelper;
import com.ovit.nswy.relationship.config.HttpsUtil;
import com.ovit.nswy.relationship.manager.UserManager;
import com.ovit.nswy.relationship.mapper.LiveCategoryMapper;
import com.ovit.nswy.relationship.model.JsonUtil;
import com.ovit.nswy.relationship.model.LiveCategory;
import com.ovit.nswy.relationship.model.LiveRoom;
import com.ovit.nswy.relationship.model.request.GetUserQuery;
import com.ovit.nswy.relationship.model.request.ListLiveQuery;
import com.ovit.nswy.relationship.model.request.LiveCategoryQuery;
import com.ovit.nswy.relationship.model.request.LiveQuery;
import com.ovit.nswy.relationship.model.response.*;
import com.ovit.nswy.relationship.service.LiveRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ${huipei.x} on 2018-1-10.
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class LiveRoomServiceImpl extends BaseService<LiveRoom> implements LiveRoomService {

  @Autowired
  private LiveCategoryMapper liveCategoryMapper;
  @Autowired
  private UserManager userManager;

  @Value("${http.live.url}")
  private String url;


  @Override
   public  PageInfo<LiveRoomDTO> listlive(ListLiveQuery listLiveQuery){
     PageInfo<LiveRoomDTO> pageInfo = null;
     PageHelper.startPage(listLiveQuery.getPageNum(), listLiveQuery.getPageSize());
    Example example = new Example(LiveRoom.class);
      if(Integers.isNotEmpty(listLiveQuery.getSortType())) {
          if (listLiveQuery.getSortType().equals(1)) {
              example.setOrderByClause("create_time ASC");
          }
          if (listLiveQuery.getSortType().equals(2)) {
              example.setOrderByClause("create_time DESC");
          }
      }
      if(StringUtil.isNotBlank(listLiveQuery.getAccount())){
          example.createCriteria().andEqualTo("account", listLiveQuery.getAccount());
      }
      //添加时间条件 只显示最近一个星期的直播间
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
      Date beforeSevenDay = calendar.getTime();
      String beforeSevenDarStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beforeSevenDay);
      example.createCriteria().andGreaterThan("createTime", beforeSevenDarStr);

     List<LiveRoom> liveRoomList=this.findByExample(example);
    /*for(LiveRoom liveRoom:liveRoomList){
      LiveCategory liveCategory= liveCategoryMapper.selectByPrimaryKey(liveRoom.getLiveCategoryId());
      if(liveCategory.getLiveArea().contains("{")){
         liveRoom.setLiveName(String.valueOf(JSONHelper.parseArray(liveCategory.getLiveArea())));
          JSONObject object = JSONHelper.parseObject(liveCategory.getLiveArea());
          liveRoom.setLiveName(object.getString("provinceName"));
      }



    }*/
    PageInfo<LiveRoom> origPageInfo = new PageInfo(liveRoomList);
    List<LiveRoomDTO> list = Lists.newArrayList();
    list = BeanByRefMapper.copyByRefListMapper(liveRoomList, LiveRoomDTO.class);
    getLiveRoomDTO(list);
    pageInfo = new PageInfo(list);
    pageInfo = PageInfoHelper.getPageInfo(origPageInfo, pageInfo);
    return pageInfo;
    }


    @Override
    public  Map<String,Object> findLiveListByAccount(ListLiveQuery listLiveQuery){

        Map<String,Object> map = new HashMap<>();

        Example example = new Example(LiveRoom.class);
        example.setOrderByClause("create_time DESC");
        if(StringUtil.isNotBlank(listLiveQuery.getAccount())){
            example.createCriteria().andEqualTo("account", listLiveQuery.getAccount());
        }
        //添加时间条件 只显示最近一个星期的直播间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
        Date beforeSevenDay = calendar.getTime();
        String beforeSevenDarStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beforeSevenDay);
        example.createCriteria().andGreaterThan("createTime", beforeSevenDarStr);

        List<LiveRoom> liveRoomList=this.findByExample(example);
        LiveCategory liveCategory= liveCategoryMapper.selectByPrimaryKey(liveRoomList.get(0).getLiveCategoryId());
        if(liveCategory.getLiveArea().contains("{")){
            map.put("liveArea",JSONObject.parse(liveCategory.getLiveArea()));

        }
        if(liveCategory.getLiveSpecies().contains("{")){

            map.put("liveSpecies", JSONObject.parse(liveCategory.getLiveSpecies()));

        }
        if(liveCategory.getLiveIndustry().contains("{")){
            map.put("liveIndustry",JSONObject.parse(liveCategory.getLiveIndustry()));

        }
        PageInfo<LiveRoom> origPageInfo = new PageInfo(liveRoomList);
        List<LiveRoomDTO> list = Lists.newArrayList();
        list = BeanByRefMapper.copyByRefListMapper(liveRoomList, LiveRoomDTO.class);
        getLiveRoomDTO(list);

        map.put("liveList",list.get(0));


        return map;
    }



  @Override
  public Integer saveOrUpdateCategory(LiveCategoryQuery liveCategoryQuery){
    LiveCategory liveCategory=new LiveCategory();
    liveCategory=BeanByRefMapper.copyByRefMapper(liveCategoryQuery,LiveCategory.class);
    liveCategory=getLiveCategory(liveCategory, liveCategoryQuery);
    liveCategory.setCreateTime(new Date());
    liveCategoryMapper.insert(liveCategory);
    return liveCategory.getLiveCategoryId();
  }

  @Override
  public Integer saveOrUpdateLive(LiveQuery liveQuery){
    LiveRoom liveRoom=new LiveRoom();
    liveRoom=BeanByRefMapper.copyByRefMapper(liveQuery,LiveRoom.class);
    liveRoom.setCreateTime(new Date());
      this.save(liveRoom);
    return liveRoom.getLiveId();
  }


    @Override
    public LiveRoomDTO getLivebyliveId(Integer liveId){
        LiveRoomDTO liveRoomDTO=new LiveRoomDTO();
        if(Integers.isNotEmpty(liveId)){
            LiveRoom live=this.findById(liveId);
            liveRoomDTO=BeanByRefMapper.copyByRefMapper(live,LiveRoomDTO.class);
            if(StringUtil.isNotBlank(liveRoomDTO.getAccount())){
                GetUserQuery getUserQuery=new GetUserQuery();
                getUserQuery.setAccount(liveRoomDTO.getAccount());
                getUserQuery.setQueryType(1);
                UserDTO userDTO= userManager.getUserDTO(getUserQuery);
                liveRoomDTO.setUserName(userDTO.getDisplayName());
            }
            liveRoomDTO.getLiveStatusInfo().put("id",1);
            liveRoomDTO.getLiveStatusInfo().put("val","直播中");
        }

        return liveRoomDTO;
    }


    private List<LiveRoomDTO> getLiveRoomDTO(List<LiveRoomDTO> list){
        String result= null;
       List<String> userIdlist=Lists.newArrayList();
    List<Map> mapList=Lists.newArrayList();
        try {
            result = HttpsUtil.get(url + "/rooms");
            Map<String, Object> map = JSONHelper.parseObject(result, Map.class);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Map mapObject= Maps.newHashMap();
                String roomId = entry.getKey();
                mapObject.put("roomId",roomId);
                String userIds = JSONHelper.toJSONString(entry.getValue());
                userIdlist = JSONHelper.parseArray(userIds, String.class);
                mapObject.put("userId",userIdlist);
                mapList.add(mapObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(LiveRoomDTO liveRoomDTO:list){
            if(StringUtil.isNotBlank(liveRoomDTO.getAccount())){
                GetUserQuery getUserQuery=new GetUserQuery();
                getUserQuery.setAccount(liveRoomDTO.getAccount());
                getUserQuery.setQueryType(1);
                UserDTO userDTO= userManager.getUserDTO(getUserQuery);
                liveRoomDTO.setUserName(userDTO.getDisplayName());
            }

            for( Map map: mapList){
                if(Maps.getInteger(map,"roomId").equals(liveRoomDTO.getLiveId())){
                    List<String> uerIds= (List<String>) map.get("userId");
                    if(uerIds.contains(liveRoomDTO.getAccount())){
                        liveRoomDTO.getLiveStatusInfo().put("id",1);
                        liveRoomDTO.getLiveStatusInfo().put("val","直播中");
                    }
                }
            }

        }

        return list;
    }


    private LiveCategory getLiveCategory(LiveCategory liveCategory,LiveCategoryQuery liveCategoryQuery){
        if(StringUtil.isNotBlank(liveCategoryQuery.getLiveArea())){
           AreaDTO areaDTO= JSONObject.parseObject(liveCategoryQuery.getLiveArea(),AreaDTO.class);
         if(ObjectUtil.isNotEmpty(areaDTO)){
             liveCategory.setProvinceId(areaDTO.getProvinceId());
                 liveCategory.setCityId(areaDTO.getCityId());
                 liveCategory.setCountyId(areaDTO.getCountyId());
                 liveCategory.setTownId(areaDTO.getTownId());
         }
        }
        if(StringUtil.isNotBlank(liveCategoryQuery.getLiveIndustry())){
           CorrelationDTO correlationDTO= JSONObject.parseObject(liveCategoryQuery.getLiveIndustry(), CorrelationDTO.class);
                liveCategory.setIndustryId(correlationDTO.getIndustryId());
        }
        if(StringUtil.isNotBlank(liveCategoryQuery.getLiveSpecies())){
           CorrelationDTO correlationDTO=JSONObject.parseObject(liveCategoryQuery.getLiveSpecies(), CorrelationDTO.class);
                liveCategory.setIndustryId(correlationDTO.getSpeciesId());
        }

        return liveCategory;
    }

}
