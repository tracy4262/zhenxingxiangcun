package com.ovit.nswy.member.service.impl;

import com.github.xphsc.util.PinyinUtil;
import com.ovit.nswy.member.mapper.LoginUserMapper;
import com.ovit.nswy.member.mapper.UserWebsiteMapper;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.UserWebsite;
import com.ovit.nswy.member.service.UserWebsiteService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserWebsiteServiceImpl implements UserWebsiteService {

    @Autowired
    private UserWebsiteMapper userWebsiteMapper;

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Override
    public int deleteByAccount(String account) {
        return userWebsiteMapper.deleteByAccount(account);
    }

    @Override
    public int insert(UserWebsite record) {
        return userWebsiteMapper.insert(record);
    }

    @Override
    public int updateByAccount(UserWebsite record) {
        return userWebsiteMapper.updateByAccount(record);
    }

    @Override
    public UserWebsite selectByAccount(String account) {
        return userWebsiteMapper.selectByAccount(account);
    }

    @Override
    public String chineseToPinyin(String chinese) {
        return PinyinUtil.getCamelPinYin(chinese);
    }

    @Override
    public void saveOrUpdateWebsite(Map<String, Object> params, LoginUser loginUser, HttpSession session) {
        UserWebsite website = new UserWebsite();
        String account = loginUser.getLoginAccount();
        Integer type = (Integer)params.get("type");
        if (type != 0) {
            Map<String,Object> map = new HashMap<>();
            map.put("type",type);
            map.put("account",account);
            loginUserMapper.updateType(map);
        }
        website.setAccount(account);
        String websiteStep = MapUtils.getString(params,"websiteStep");
        website.setCreateTime(new Date());
        website.setBanner(getDefaultString(params,"banner",websiteStep));
        website.setIntroduce(getDefaultString(params,"introduce"));
        website.setLogo(getDefaultString(params,"logo",websiteStep));
        //无论有几个首页，只取第一个
        String modular = MapUtils.getString(params,"modular");
        int index = modular.lastIndexOf("首页,");
        if (index != -1) {
            modular = modular.substring(index,modular.length());
        }
        website.setModular(modular);
        website.setName(getDefaultString(params,"name"));
        website.setPosition(getDefaultString(params,"position"));
        website.setStatus(getDefaultString(params,"status"));
        website.setSummary(getDefaultString(params,"summary"));
        website.setTemplate(getDefaultString(params,"template"));
        website.setCurrentType(getDefaultInt(params,"currentType"));
        UserWebsite userWebsite=userWebsiteMapper.selectByAccount(account);
        //userWebsiteService.deleteByAccount(username);
        if (null == userWebsite) {
            userWebsiteMapper.insert(website);
        } else {
            userWebsiteMapper.updateByAccount(website);
        }
        String step = params.get("step").toString();
        if (StringUtils.isNotEmpty(step)){
            String str =step.substring(12,16);
            if(!str.equals("prog")){
                Map<String, Object> map = new HashMap<>();
                map.put("account", account);
                map.put("flag", step);
                loginUserMapper.updateIdentityFlag(map);
            }
            //更新session里面的步骤
            loginUser.setIsIdentityVerification(params.get("step").toString());
            session.setAttribute("currentUser", loginUser);
        }
    }


    /**
     * 获取默认值
     * @param map
     * @param key
     * @return
     */
    private String getDefaultString(Map<String,Object> map,String key) {
        String value = MapUtils.getString(map,key);
        if (StringUtils.isNotEmpty(value)) {
            return value;
        } else {
            return null;
        }
    }

    /**
     * 获取默认String
     * @param map
     * @param key
     * @param websiteStep 网站设置步数
     * @return
     */
    private String getDefaultString(Map<String,Object> map,String key,String websiteStep) {
        String value = MapUtils.getString(map,key);
        if (StringUtils.isNotEmpty(value)) {
            return value;
        } else {
            if (StringUtils.isNotEmpty(websiteStep)) {
                //第一步返回""，其余返回null 以适应SQL中的判断条件
                return "";
            } else {
                return null;
            }
        }
    }

    /**
     * 默认int值
     * @param map
     * @param key
     * @return
     */
    private int getDefaultInt(Map<String,Object> map,String key) {
        String value = MapUtils.getString(map,key);
        if (StringUtils.isNotEmpty(value)) {
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }
}
