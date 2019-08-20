package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.service.MyLabelService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MyLabelServiceImpl implements MyLabelService {
    @Autowired
    private SqlSession sqlsession;

    @Override
    public int insert(List<Map<String, Object>> map) {
        int num = sqlsession.insert("MyLabelMapper.insert", map);
        return num;
    }

    @Override
    public List<String> selectByAccount(String account) {
        return sqlsession.selectList("MyLabelMapper.selectByAccount1", account);
    }

    @Override
    public void deleteByAccount(String account) {
        sqlsession.delete("MyLabelMapper.deleteByAccount", account);
    }


}
