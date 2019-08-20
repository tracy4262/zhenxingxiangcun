package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.InformationBookMapper;
import com.ovit.nswy.member.service.InformationBookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "informationBookService")
public class InformationBookServiceImpl implements InformationBookService {

    private static final Logger logger = LogManager.getLogger(InformationBookServiceImpl.class);

    @Autowired
    private InformationBookMapper informationBookMapper;

    @Override
    public void insert(Map<String, Object> map) {
        logger.trace("insert informationBook params:{}", map);
        informationBookMapper.insert(map);
    }

    @Override
    public Map<String, Object> getInformationBookInfoByDetailId(int detaild) {
        logger.trace("getInformationBookInfoByDetailId detaild:{}", detaild);
        return informationBookMapper.getInformationBookInfoByDetailId(detaild);
    }

    @Override
    public List<Map<String, Object>> getInformationBookInfoList() {
        return informationBookMapper.getInformationBookInfoList();
    }

    @Override
    public void insertMemberIntroduceBook(Map<String, Object> map) {

        informationBookMapper.insertMemberIntroduceBook(map);
    }


}
