package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.FriendGroupMapper;
import com.ovit.nswy.member.model.FriendGroup;
import com.ovit.nswy.member.service.FriendGroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FriendGroupServiceImpl implements FriendGroupService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private FriendGroupMapper friendGroup;

    @Override
    public int insert(List<FriendGroup> record) {
        return friendGroup.insert(record);
    }

    @Override
    public List<FriendGroup> selectByAccount(String account) {
        return friendGroup.selectByAccount(account);
    }

    @Override
    public int deleteByAccount(String account) {
        return friendGroup.deleteByAccount(account);
    }

    @Override
    @Transactional
    public int addFriendGroup(List<Map<String,Object>> friendGroupList, String account) {
        friendGroup.deleteByAccount(account);
        logger.info("删除用户：{}原有的好友分组", account);
        List<FriendGroup> list = new ArrayList<FriendGroup>();
        for (Map<String,Object> map : friendGroupList) {
            FriendGroup friendGroup = new FriendGroup();
            friendGroup.setLoginAccount(account);
            friendGroup.setGroupName(map.get("groupName").toString());
            friendGroup.setAuthority(map.get("authority").toString());
            list.add(friendGroup);
        }
        int addNum = friendGroup.insert(list);
        logger.info("保存好友分组数据成功，共{}条", addNum);
        return addNum;
    }
}