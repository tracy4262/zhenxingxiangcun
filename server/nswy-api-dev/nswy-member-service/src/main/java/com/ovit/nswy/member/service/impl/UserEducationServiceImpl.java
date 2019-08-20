package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.service.UserEducationService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class UserEducationServiceImpl implements UserEducationService {
    @Autowired
    private SqlSession sqlSession;
    @Override
    public void insert(Map<String, Object> map) {
        sqlSession.insert("UserEducationMapper.insert",map);
    }

    @Override
    public Map<String, Object> selectByAccount(Map<String, Object> map) {
        return sqlSession.selectOne("UserEducationMapper.selectByAccount",map);
    }

    @Override
    public void deleteByAccount(Map<String, Object> map) {
        sqlSession.delete("UserEducationMapper.deleteByAccount",map);
    }
}
