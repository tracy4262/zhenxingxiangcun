package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.CertificationMapper;
import com.ovit.nswy.member.mapper.LoginUserMapper;
import com.ovit.nswy.member.mapper.PerfectInformationMapper;
import com.ovit.nswy.member.model.Certification;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.LoginUserService;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    private LoginUserMapper loginUserMapper;


    @Autowired
    private CertificationMapper certificationDao;

    @Autowired
    private PerfectInformationMapper perfectInformationMapper;


    private Logger logger = LogManager.getLogger(LoginUserServiceImpl.class);

    /*
     *
     * 删除
     */
    @Override
    public void deleteByPrimaryKey(Integer id) {
        loginUserMapper.deleteByPrimaryKey(id);
    }

    // 注册
    @Transactional
    @Override
    public void insert(LoginUser user) {
        loginUserMapper.insert(user);
    }

    @Override
    public void insertCertification(Map<String, Object> params,String username,LoginUser user ){
        //插入所在区域到认证表中
        String name = MapUtils.getString(params,"name");
        String city = MapUtils.getString(params,"city");
        Certification certification = new Certification();
        certification.setUserAccount(username);
        certification.setRealname(name);
        certification.setCity(city);
        //查询是否存在
        Certification isExist = certificationDao.selectByAccount(username);
        if (null != isExist) {
            //replace更新数据，在存在时设置ID
            certification.setId(isExist.getId());
        }
        certificationDao.insert(certification);

        Map<String, Object> networkMap = new LinkedHashMap<>();
        String nswyId = MapUtils.getString(params, "nswyId");
        networkMap.put("account",username);
        networkMap.put("status", true);
        networkMap.put("ID", modelMap(nswyId,"农事无忧ID"));
        networkMap.put("QQ", modelMap("","QQ号码"));
        networkMap.put("Email", modelMap("","邮箱"));
        networkMap.put("domainName", modelMap("","申请域名"));

        perfectInformationMapper.insertNetworkInfoInfo(networkMap);

        user.setNswyIdModel(nswyId);
    }

    @Override
    public LoginUser selectByPrimaryKey(Integer id) {
        return loginUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<LoginUser> findAll() {
        return loginUserMapper.findAll();
    }

    @Override
    public void updateByPrimaryKey(LoginUser record) {
        loginUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public void updateByAccount(LoginUser record) {
        loginUserMapper.updateByAccount(record);
    }

    @Override
    public LoginUser findByLoginName(String loginName) {
        return loginUserMapper.findByLoginName(loginName);
    }

    /**
     * 登录
     *
     * @param
     * @return
     * @throws Exception
     */
    public LoginUser login(String username, String pwd) {
        logger.info("登录.....");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("login_account", username);
        map.put("login_passwd", pwd);
        LoginUser user = loginUserMapper.login(map);
        return user;
    }

    /**
     * 退出登录
     *
     * @throws Exception
     *//*
     * public void logout() throws Exception { IniSubject();
     *
     * session.removeAttribute(Const.SESSION_USER);
     *
     * currentUser.logout(); }
     */
    @Override
    public LoginUser findTourNumber() {
        return null;
    }

    @Override
    public List<LoginUser> findFriendByLoginName(LoginUser record) {
        return loginUserMapper.findFriendByLoginName(record);
    }

    @Override
    public PageInfo<LoginUser> query(int pageNum, int pageSize, LoginUser record) {
        PageHelper.startPage(pageNum, pageSize);
        List<LoginUser> list = loginUserMapper.findFriendByLoginName(record);
        return new PageInfo<LoginUser>(list);
    }

    @Override
    public void updateType(Map<String, Object> map) {
        loginUserMapper.updateType(map);
    }

    @Override
    public PageInfo<LoginUser> query(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<LoginUser> list = loginUserMapper.findAll();
        return new PageInfo<LoginUser>(list);
    }

    @Override
    public List<Map<String, Object>> findModeratorById(int id) {
        return loginUserMapper.findModeratorById(id);
    }

    @Override
    public void updateIdentityFlag(String account, String flag) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("account", account);
        param.put("flag", flag);
        loginUserMapper.updateIdentityFlag(param);
    }

    @Override
    public PageInfo<Map<String, Object>> showRecommended(Map<String, Object> param) {
        PageHelper.startPage(Integer.parseInt(String.valueOf(param.get("pageNum"))), Integer.parseInt(String.valueOf(param.get("pageSize"))));
        List<Map<String, Object>> list = loginUserMapper.showRecommended(param);
        return new PageInfo<Map<String, Object>>(list);
    }

    /**
     * 寻找所有个人用户信息
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<LoginUser> findAllPersonAccount(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<LoginUser> list = loginUserMapper.findAllPersonAccount();
        return new PageInfo<LoginUser>(list);
    }

    @Override
    public int updatePassword(Map<String, Object> params) {
        return loginUserMapper.updatePassword(params);
    }

    @Override
    public int insertProxyUser(LoginUser loginUser) {
        return loginUserMapper.insertProxyUser(loginUser);
    }



    /**
     * 生成农事无忧id
     * @return
     */
    @Override
    public String generateNswyId() {

        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int num =(int)(Math.random()*900)+100;
        String nswyId = "NSWY" + currentTime + String.valueOf(num);
        return nswyId;
    }

    @Override
    public Map<String, Object> findUseTemplate(String account) {
        return loginUserMapper.findUseTemplate(account);
    }

    @Override
    public Map<String, Object> findYearInfo(String account) {
        return loginUserMapper.findYearInfo(account);
    }

    private Map<String,Object> modelMap(String model,String name){
        Map<String,Object> maps = new HashMap<>();
        maps.put("model", model);
        maps.put("name", name);
        return maps;
    }
}