package com.ovit.nswy.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.bean.BeanByRefMapper;
import com.github.xphsc.collect.Lists;
import com.github.xphsc.collect.Maps;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.common.enums.AppStartFlagEnum;
import com.ovit.nswy.member.feigen.RelationshipFeign;
import com.ovit.nswy.member.mapper.UserMapper;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.request.GetUserQuery;
import com.ovit.nswy.member.model.request.ListFollowQuery;
import com.ovit.nswy.member.model.response.FansDTO;
import com.ovit.nswy.member.model.response.ListAppDTO;
import com.ovit.nswy.member.model.response.MyAppDTO;
import com.ovit.nswy.member.model.response.UserDTO;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.service.PerfectInformationService;
import com.ovit.nswy.member.service.ProxyReversionService;
import com.ovit.nswy.member.service.UserService;
import com.ovit.nswy.member.util.PageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-12-5.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RelationshipFeign relationshipFeign;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private ProxyReversionService proxyReversionService;

    @Autowired
    private PerfectInformationService perfectInformationService;

    @Value("${register.image}")
    private String image;

    @Override
    public PageInfo<UserDTO> listUser(Map<String,Object> params) {
        logger.info("/user/listUser，查询参数：{}",params);
        List<UserDTO> list = null;
        try {
            PageUtils.initPage(params);
            list = getListQuery(params);
            logger.debug("/user/listUser，查询结果：{}",list);
        } catch (Exception e) {
            logger.error("/user/listUser，查询失败：{}",e);
        }
        return new PageInfo<>(list);
    }


    @Override
    public UserDTO getUserByQuery(GetUserQuery getUserQuery) {
        Map params= Maps.newHashMap();
        if(Integers.isNotEmpty(getUserQuery.getQueryType())){
            if(getUserQuery.getQueryType()==1){
                params.put("queryType",getUserQuery.getQueryType()) ;
                params.put("account",getUserQuery.getAccount()) ;
            }
            if(getUserQuery.getQueryType()==2){
                params.put("queryType",getUserQuery.getQueryType()) ;
                params.put("Id",getUserQuery.getId()) ;
            }
        }
        LoginUser loginUser = userMapper.getUserByQuery(params);
        UserDTO userDTO = BeanByRefMapper.copyByRefMapper(loginUser, UserDTO.class);
        return userDTO;
    }

    @Override
    public UserDTO getUserMemberName(GetUserQuery getUserQuery) {
        Map params= Maps.newHashMap();
        if(Integers.isNotEmpty(getUserQuery.getQueryType())){
            if(getUserQuery.getQueryType()==1){
                params.put("queryType",getUserQuery.getQueryType()) ;
                params.put("account",getUserQuery.getAccount()) ;
            }
            if(getUserQuery.getQueryType()==2){
                params.put("queryType",getUserQuery.getQueryType()) ;
                params.put("Id",getUserQuery.getId()) ;
            }
        }
        LoginUser loginUser = userMapper.getUserByQuery(params);
        Map<String,Object> map = userMapper.findMemberName(params);
        loginUser.setDisplayName(MapUtils.getString(map,"memberName"));

        String account = MapUtils.getString(params, "account");
        //LoginUser user = loginUserService.findByLoginName(account);
        Map<String,Object> useTemplate = loginUserService.findUseTemplate(account);
        //查询用户最大的年度文件id
        if (MapUtils.isNotEmpty(useTemplate)) {
            Map<String,Object> yearInfo = loginUserService.findYearInfo(account);
            useTemplate.put("yearId", MapUtils.getString(yearInfo, "id"));
            Map<String,Object> privacyInfo = perfectInformationService.findPrivacyInfo(useTemplate);

            String images = MapUtils.getString(privacyInfo, "image");
            if(StringUtils.isNotBlank(images)){
                loginUser.setAvatar(images);
            }else{
                loginUser.setAvatar(image);

            }

        }

        UserDTO userDTO = BeanByRefMapper.copyByRefMapper(loginUser, UserDTO.class);
        return userDTO;
    }

    @Override
    public Integer updateStartFlag(Integer startFlag, Integer id) {
        Integer result = userMapper.updateStartFlag(startFlag, id);
        return result == null ? 0 : result;
    }

    @Override
    public ListAppDTO getAppStartFlag(String account) {
            List<MyAppDTO> myAppDTOList = userMapper.listApp(account);
            ListAppDTO listAppDTO = new ListAppDTO();
            if (Lists.isNotEmpty(myAppDTOList)) {
            for (MyAppDTO myAppDTO : myAppDTOList) {
                if (myAppDTO.getAppid().equals("2")) {
                    listAppDTO.setAccount(myAppDTO.getAccount());
                    listAppDTO.setId(myAppDTO.getId());
                    listAppDTO.setStartFlag(myAppDTO.getStartFlag());
                    listAppDTO.getStartFlagInfo().put("id", myAppDTO.getStartFlag());
                    listAppDTO.getStartFlagInfo().put("val", AppStartFlagEnum.appStartFlagEscape(myAppDTO.getStartFlag()));
            }
         }
    }
        return listAppDTO;
    }
    @Override
   public List<FansDTO> listfollow(ListFollowQuery listFollowQuery){
       Map params= Maps.newHashMap();
       List<FansDTO> fansDTOList=null;
       if(Integers.isNotEmpty(listFollowQuery.getFollowType())){
           if(listFollowQuery.getFollowType().equals(1)){
               params.put("followType",listFollowQuery.getFollowType()) ;
               if(StringUtil.isNotBlank(listFollowQuery.getAccount())){
                   params.put("account",listFollowQuery.getAccount()) ;
               }
               fansDTOList= userMapper.listfollowByMap(params);
           }
           if(listFollowQuery.getFollowType().equals(2)){
               params.put("followType",listFollowQuery.getFollowType()) ;
               if(StringUtil.isNotBlank(listFollowQuery.getAccount())){
                   params.put("account",listFollowQuery.getAccount()) ;
                   if(StringUtil.isNotBlank(listFollowQuery.getFromAccount())){
                       params.put("fromAccount",listFollowQuery.getFromAccount());
                   }
               }
               fansDTOList= userMapper.listfollowByMap(params);

           }
       }
       return fansDTOList;
   }

    @Override
    public Integer updateStatusByaccount(String account,String toAccount){
        GetUserQuery getUserQuery= new GetUserQuery();
        getUserQuery.setQueryType(1);
        getUserQuery.setAccount(toAccount);
        UserDTO userDTO=getUserByQuery(getUserQuery);
        Integer result= userMapper.updateStatusById(account,userDTO.getId());
        logger.info("更改好友状态：{}"+result);
        return result==null?0:result;
    }

    /**
     * 查询登录账号名称以及关注状态
     * 个人、专家--实名，企业--企业名称，机关、乡村--机关名称
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> queryRealNameAndStatus(Map<String, Object> map) {
        String account = MapUtils.getString(map,"account");
        String fromAccount = MapUtils.getString(map,"fromAccount");
        //查询好友名称，
        Map<String,Object> realNameMap = userMapper.getRealName(fromAccount);
        //查询是否关注了该好友
        List<Map<String, Object>> userMap = userMapper.queryStatus(fromAccount,account);
        if (CollectionUtils.isNotEmpty(userMap)) {
            realNameMap.put("status","1");
            //如果已关注、查询该好友是否关注当前登录账号
            List<Map<String, Object>> formAccount = userMapper.queryStatus(account,fromAccount);
            if (CollectionUtils.isNotEmpty(formAccount)) {
                //相互关注
                realNameMap.put("status","2");
            }
        } else  {
            realNameMap.put("status","3");
        }
        return realNameMap;
    }

    @Override
    public Map<String, Object> getDisplayNameByAccount(String account) {
        return userMapper.getRealName(account);
    }

    private PageInfo<UserDTO> getUserDTO(PageInfo<UserDTO> pageInfo, String account) {
        List<String> fansAccountList = Lists.newArrayList();
        List<FansDTO> fansDTOs = userMapper.listfollow(account);
        for (FansDTO fansDTO : fansDTOs) {
            fansAccountList.add(fansDTO.getAccount());
        }
        for (UserDTO userDTO : pageInfo.getList()) {
            if (fansAccountList.contains(userDTO.getLoginAccount())) {
                userDTO.getFollowInfo().put("id", 1);
                userDTO.getFollowInfo().put("val", "已关注");
            } else {
                userDTO.getFollowInfo().put("id", 2);
                userDTO.getFollowInfo().put("val", "未关注");
            }
        }
        return pageInfo;
    }

    private List<UserDTO> getListQuery(Map<String,Object> params){
        String industry = MapUtils.getString(params,"industrys");
        if (StringUtils.isNotEmpty(industry)) {
            String[] industryArray = industry.split(" ");
            params.put("industryArray",industryArray);
        }
        String species = MapUtils.getString(params,"species");
        if (StringUtils.isNotEmpty(species)) {
            String[] speciesArray = species.split(" ");
            params.put("speciesArray",speciesArray);
        }
        String service = MapUtils.getString(params,"services");
        if (StringUtils.isNotEmpty(service)) {
            String[] serviceArray = service.split(" ");
            params.put("serviceArray",serviceArray);
        }
        //用户类型：0个人，1企业，3机关，4专家，5乡村
        String userType = MapUtils.getString(params,"userType");
        //邀请好友跟添加关注的标示 1 邀请好友，2 添加关注
        String status = MapUtils.getString(params,"status");
        if ("1".equals(status)) {
            String account = MapUtils.getString(params,"account");
            List<String> list = getFriendByAccount(account);
            params.put("accountList",list);
        }
        List<UserDTO> userDTOList;
        switch (userType) {
            case "0"://0查询个人和专家信息
                userDTOList = userMapper.queryUserAndExpert(params);
                break;
            case "1":
                userDTOList = userMapper.queryCorpInfo(params);
                break;
            case "3":
                //查询机关
                userDTOList = userMapper.queryGovInfo(params);
                break;
            default:
                //默认查询所有
                userDTOList = userMapper.queryAllUser(params);
                break;

        }
        return userDTOList;
    }

    private List<String> getFriendByAccount(String account) {
        Object object = relationshipFeign.getFriendByAccount(account);
        JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(object.toString());
        Object data = jsonObject.get("data");
        List<String>  friendList = new ArrayList<>();
        if (null != data) {
            friendList.addAll((List<String> )jsonObject.get("data"));
        }
        return friendList;
    }
}
