package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.CertificationMapper;
import com.ovit.nswy.member.mapper.LoginUserMapper;
import com.ovit.nswy.member.mapper.UserContactMapper;
import com.ovit.nswy.member.mapper.UserInfoMapper;
import com.ovit.nswy.member.model.Certification;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.UserContact;
import com.ovit.nswy.member.model.UserInfo;
import com.ovit.nswy.member.service.UserInfoService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserContactMapper userContactMapper;

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Autowired
    private CertificationMapper certificationDao;

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userInfoMapper.updateUserInfo(userInfo);
    }

    @Override
    public UserInfo findUserInfo(String account) {
        return userInfoMapper.findUserInfo(account);
    }

    @Override
    public void insert(Map<String, Object> params, HttpSession session, LoginUser loginUser) {
        String account = loginUser.getLoginAccount();
        //将map转换为基本信息bean
        UserInfo userInfo = mapToUserInfo(params, account);
        //校验是否存在
        UserInfo userInfoIsExist = userInfoMapper.findUserInfo(account);
        if (null != userInfoIsExist) {
            userInfoMapper.updateUserInfo(userInfo);
        } else {
            userInfoMapper.insert(userInfo);
        }
        //将map转换为用户联系方式的bean
        UserContact userContact = mapToUserContact(params, account);
        //校验是否存在
        UserContact userContactIsExist = userContactMapper.selectByAccount(account);
        if (null != userContactIsExist) {
            userContactMapper.updateMobileAndPostalCodeByAccount(userContact);
        } else {
            userContactMapper.insertMobileAndPostalCode(userContact);
        }
        // 如果修改成功则修改用户表里面的相关信息
        loginUser.setAvatar((String)params.get("avatar"));
        if (StringUtils.hasText(loginUser.getLoginPasswd())) {
            loginUser.setLoginPasswd(loginUser.getLoginPasswd());
        }
        loginUserMapper.updateByAccount(loginUser);

        //注册信息更新时同步更新实名认证中的信息
        Certification certification = getCertificationBean(params,account);
        certificationDao.updateCertification(certification);
        String step = (String)params.get("step");
        if (StringUtils.hasText(step)) {
            String str = step.substring(12, 16);
            if (!str.equals("prog")) {
                params.put("account", account);
                params.put("flag", step);
                loginUserMapper.updateIdentityFlag(params);
            }
        }
    }

    @Override
    public void insert(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    /**
     * 将map转换为 UserInfo
     * @param params
     * @param account
     * @return
     */
    private UserInfo mapToUserInfo(Map<String,Object> params, String account){
        UserInfo userInfo = new UserInfo();
        userInfo.setAccount(account);
        userInfo.setBirdhday(MapUtils.getString(params,"birth"));
        userInfo.setStatusB(MapUtils.getBoolean(params,"statusB") ? 1 : 0);
        userInfo.setRealName(MapUtils.getString(params,"name"));
        userInfo.setStatusN(MapUtils.getBoolean(params,"statusN") ? 1 : 0);
        userInfo.setRece(MapUtils.getString(params,"family"));
        userInfo.setStatusR(MapUtils.getBoolean(params,"statusR") ? 1 : 0);
        userInfo.setJob(MapUtils.getString(params,"profession"));
        userInfo.setStatusJ(MapUtils.getBoolean(params,"statusJ") ? 1 : 0);
        userInfo.setSpecies(MapUtils.getString(params,"specy"));
        userInfo.setStatusS(MapUtils.getBoolean(params,"statusS") ? 1 : 0);
        userInfo.setPhone(MapUtils.getString(params,"phone"));
        userInfo.setAcaTitle(MapUtils.getString(params,"zhicheng"));
        userInfo.setStatusA(MapUtils.getBoolean(params,"statusA") ? 1 : 0);
        userInfo.setSex(MapUtils.getString(params,"sex"));
        userInfo.setStatusE(MapUtils.getBoolean(params,"statusE") ? 1 : 0);
        userInfo.setAvatar(MapUtils.getString(params,"avatar"));
        userInfo.setStatusAddr(MapUtils.getBoolean(params,"statusAddr") ? 1 : 0);
        userInfo.setAddr(MapUtils.getString(params,"addr"));
        userInfo.setStatusPhone(MapUtils.getBoolean(params,"statusPhone") ? 1 : 0);
        userInfo.setStatusCoord(MapUtils.getBoolean(params,"statusCoord") ? 1 : 0);
        userInfo.setCoordinatePoint(MapUtils.getString(params,"point"));
        return userInfo;
    }

    /**
     * 将map转换为UserContact
     * @param params
     * @param account
     * @return
     */
    private UserContact mapToUserContact(Map<String,Object> params,String account) {
        UserContact userContact = new UserContact();
        userContact.setMobile(MapUtils.getString(params, "mobile"));
        userContact.setPostalcode(MapUtils.getString(params, "postalcode"));
        userContact.setMobileStatus(MapUtils.getBoolean(params, "mobileStatus") ? 1 : 0);
        userContact.setPostalcodeStatus(MapUtils.getBoolean(params, "postalcodeStatus") ? 1 : 0);
        userContact.setAccount(account);
        return userContact;
    }

    /**
     * 将map转换为Certification
     * @param params
     * @param account
     * @return
     */
    private Certification getCertificationBean(Map<String,Object> params,String account){
        Certification certification = new Certification();
        certification.setUserAccount(account);
        certification.setCity(MapUtils.getString(params,"addr"));
        certification.setMobile(MapUtils.getString(params,"phone"));
        certification.setLocationId(MapUtils.getString(params,"locationId"));
        return certification;
    }
}
