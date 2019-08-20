package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.CorpCertificationMapper;
import com.ovit.nswy.member.mapper.CorpInfoMapper;
import com.ovit.nswy.member.model.CorpInfo;
import com.ovit.nswy.member.service.CorpCertificationService;
import com.ovit.nswy.member.service.CorpInfoService;
import com.ovit.nswy.member.util.PageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CorpCertificationServiceImpl implements CorpCertificationService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private CorpCertificationMapper corpCertificationMapper;

    /**
     * 通过用户名查询企业的完善信息
     * @param account
     * @return
     */
    @Override
    public Map<String, Object> findCertificationByAccount(String account) {
        Map<String,Object> corpCertificationInfo = corpCertificationMapper.findCertificationByAccount(account);
        logger.info("根据用户名{}，查询的企业完善信息内容为：{}", account, corpCertificationInfo);
        return corpCertificationInfo;
    }

    /**
     * 保存企业完善数据
     * @param perfectInfo
     */
    @Override
    public void saveCertification(Map<String, Object> perfectInfo) {
        logger.info("需保存的企业完善信息内容为：{}", perfectInfo);
        corpCertificationMapper.saveCertification(perfectInfo);
    }

    /**
     * 更新企业完善数据
     * @param perfectInfo
     */
    @Override
    public void updateCertification(Map<String, Object> perfectInfo) {
        logger.info("需更新的企业完善信息内容为：{}", perfectInfo);
        corpCertificationMapper.updateCertification(perfectInfo);
    }

}
