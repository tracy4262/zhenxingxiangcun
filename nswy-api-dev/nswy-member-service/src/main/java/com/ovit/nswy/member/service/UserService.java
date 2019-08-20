package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.request.GetUserQuery;
import com.ovit.nswy.member.model.request.ListFollowQuery;
import com.ovit.nswy.member.model.request.UserQuery;
import com.ovit.nswy.member.model.response.FansDTO;
import com.ovit.nswy.member.model.response.ListAppDTO;
import com.ovit.nswy.member.model.response.UserDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-12-5.
 */
public interface UserService {

    PageInfo<UserDTO> listUser(Map<String,Object> params);

    UserDTO getUserByQuery(GetUserQuery getUserQuery);

    UserDTO getUserMemberName(GetUserQuery getUserQuery);

    Integer updateStartFlag(Integer startFlag, Integer id);

    ListAppDTO getAppStartFlag(String account);

    List<FansDTO> listfollow(ListFollowQuery listFollowQuery);
    Integer updateStatusByaccount(String account,String toAccount);

    Map<String,Object> queryRealNameAndStatus(Map<String, Object> map);

    Map<String,Object> getDisplayNameByAccount(String account);
}
