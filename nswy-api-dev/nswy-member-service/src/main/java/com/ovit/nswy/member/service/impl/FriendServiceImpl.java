package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.FriendMapper;
import com.ovit.nswy.member.model.Friend;
import com.ovit.nswy.member.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return friendDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Friend record) {
        return friendDao.insert(record);
    }

    @Override
    public List<Friend> findByName(String name) {
        return friendDao.findByName(name);
    }

    @Override
    public Friend selectByPrimaryKey(Integer id) {
        return friendDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Friend> findAll() {
        return friendDao.findAll();
    }

    @Override
    public int updateByPrimaryKey(Friend record) {
        return friendDao.updateByPrimaryKey(record);
    }

}
