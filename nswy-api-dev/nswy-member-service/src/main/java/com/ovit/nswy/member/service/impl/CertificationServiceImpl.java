package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.CertificationMapper;
import com.ovit.nswy.member.mapper.LoginUserMapper;
import com.ovit.nswy.member.mapper.UserFullInfoMapper;
import com.ovit.nswy.member.mapper.UserInfoMapper;
import com.ovit.nswy.member.model.Certification;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.UserFullInfo;
import com.ovit.nswy.member.model.UserInfo;
import com.ovit.nswy.member.service.CertificationService;
import com.ovit.nswy.member.service.PerfectInformationService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CertificationServiceImpl implements CertificationService {

    @Autowired
    private CertificationMapper certificationDao;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserFullInfoMapper userFullInfoMapper;

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Autowired
    private PerfectInformationService perfectInformationService;

    @Override
    public int insert(Certification record) {
        return certificationDao.insert(record);
    }

    @Override
    public Certification selectByAccount(String account) {
        return certificationDao.selectByAccount(account);
    }

    @Override
    public void saveOrUpdate(Map<String, Object> params, LoginUser loginUser) {
        String account = loginUser.getLoginAccount();
        String name = MapUtils.getString(params,"name");
        String idCard = MapUtils.getString(params,"idcard");
        String phone = MapUtils.getString(params,"phone");
        String city = MapUtils.getString(params,"city");
        String locationId = MapUtils.getString(params,"locationId");
        String addrDetail = MapUtils.getString(params,"addrDetail");
        params.put("account",account);
        Certification certification = new Certification();
        certification.setUserAccount(account);
        certification.setRealname(name);
        certification.setIdCard(idCard);
        certification.setMobile(phone);
        certification.setCity(city);
        certification.setLocationId(locationId);
        certification.setUserAccount(loginUser.getLoginAccount());
        certification.setAddrDetail(addrDetail);
        //查询是否存在
        Certification isExist = certificationDao.selectByAccount(account);
        if (null != isExist) {
            //replace更新数据，在存在时设置ID
           certification.setId(isExist.getId());
        }
        certificationDao.insert(certification);
        UserInfo userInfo = userInfoMapper.findUserInfo(account);
        if (userInfo != null) {
            userInfo.setRealName(name);
            userInfo.setAddr(city);
            userInfo.setPhone(phone);
            userInfoMapper.updateUserInfo(userInfo);
        } else {
            userInfo = new UserInfo();
            userInfo.setAccount(account);
            userInfo.setRealName(name);
            userInfo.setAddr(city);
            userInfo.setPhone(phone);
            userInfoMapper.insert(userInfo);
        }
        loginUser.setDisplayName(name);
        loginUser.setIsRealIdentity("Y");
        loginUserMapper.updateByAccount(loginUser);
        //查询种养户表，如为种养户，更改种养户身份证
        List<Map<String,Object>> list =   certificationDao.findFarmFamily(params);
        if(list!=null && list.size()>0){
            certificationDao.updateFarmFamily(params);
        }
        //实名认证、实名认证修改后后修改隐私信息的描述信息
        updateFullInfo(account,name);
        //如果认证第一步的手机号、常驻地修改时，需先判断隐私信息表中是否含有该账号的信息，如有则进行更新
        Map<String, Object> privacyInfo = perfectInformationService.getPrivacyInfo(account);
        if (MapUtils.isNotEmpty(privacyInfo)) {
            Map<String, Object> privacy = new HashMap<>();
            privacy.put("account", account);
            privacy.put("phoneModel", phone);
            privacy.put("residentModel", city);
            perfectInformationService.updatePrivacyInfo(privacy);
        }


    }

    /**
     * 认证第五步修改隐私信息时，同步更新certification表中的手机号、常驻地两个信息
     * @param params
     */
    @Override
    public void UpdatePrivacyInfo(Map<String, Object> params) {

        certificationDao.UpdatePrivacyInfo(params);
    }

    /**
     * 在实名认证之后
     * 如果存在隐私信息则替换隐私信息中的姓名
     * @param account
     * @param realName
     */
    private void updateFullInfo(String account,String realName) {
        UserFullInfo user = new UserFullInfo();
        user.setAccount(account);
        user = userFullInfoMapper.findUserFullInfo(user);
        if (null != user) {
            String contract = user.getContract();
            int index = contract.indexOf("姓名：");
            if (StringUtils.isNotEmpty(contract) && index != -1) {
                String name = contract.substring(index+3,contract.indexOf("，"));
                String newContract = contract.replace(name,realName);
                user.setContract(newContract);
            }
            String contract1 = user.getContract1();
            int index1 = contract1.indexOf("姓名：");
            if (StringUtils.isNotEmpty(contract1) && index1 != -1) {
                String name = contract1.substring(index+3,contract1.indexOf("，"));
                String newContract1 = contract1.replace(name,realName);
                user.setContract1(newContract1);
            }
            userFullInfoMapper.updateUserFullInfo(user);
        }
    }
}
