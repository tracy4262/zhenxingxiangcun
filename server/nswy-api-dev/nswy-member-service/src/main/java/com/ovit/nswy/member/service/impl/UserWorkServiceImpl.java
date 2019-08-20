package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.service.UserWorkService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class UserWorkServiceImpl implements UserWorkService {

    @Autowired
    private SqlSession sqlSession;
    @Override
    public void insert(Map<String, Object> map) {
        sqlSession.insert("WorkMapper.insert",map);
    }

    @Override
    public Map<String, Object> selectByAccount(Map<String, Object> map) {
        return sqlSession.selectOne("WorkMapper.selectByAccount",map);
    }

    @Override
    public void deleteByAccount(Map<String, Object> map) {
        sqlSession.delete("WorkMapper.deleteByAccount",map);
    }
}
