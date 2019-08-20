package com.ovit.nswy.member.certification.service.impl;

import com.ovit.nswy.member.certification.mapper.FriendGroupMapper;
import com.ovit.nswy.member.certification.model.FriendGroup;
import com.ovit.nswy.member.certification.service.FriendGroupService;
import org.apache.commons.collections.MapUtils;
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
    public List<FriendGroup> selectByAccount(Map<String,Object> accountInfo) {
        return friendGroup.selectByAccount(accountInfo);
    }

    @Override
    public int deleteByAccount(Map<String,Object> accountInfo) {
        return friendGroup.deleteByAccount(accountInfo);
    }

    @Override
    @Transactional
    public int addFriendGroup(List<Map<String,Object>> friendGroupList, Map<String,Object> accountInfo) {
        friendGroup.deleteByAccount(accountInfo);
        logger.info("删除用户：{}原有的好友分组", accountInfo);
        List<FriendGroup> list = new ArrayList<FriendGroup>();
        for (Map<String,Object> map : friendGroupList) {
            FriendGroup friendGroup = new FriendGroup();
            friendGroup.setLoginAccount(MapUtils.getString(accountInfo, "account"));
            friendGroup.setGroupName(map.get("groupName").toString());
            friendGroup.setAuthority(map.get("authority").toString());
            friendGroup.setTemplateId(MapUtils.getString(accountInfo, "templateId"));
            list.add(friendGroup);
        }
        int addNum = friendGroup.insert(list);
        logger.info("保存好友分组数据成功，共{}条", addNum);
        return addNum;
    }
}