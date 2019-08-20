package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.member.mapper.PlantingGroupManagerMapper;
import com.ovit.nswy.member.service.PlantingGroupManagerService;
import com.ovit.nswy.member.util.BufferedImageLuminanceSource;
import com.ovit.nswy.member.util.UuidUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class PlantingGroupManagerServiceImpl implements PlantingGroupManagerService {

    @Autowired
    private PlantingGroupManagerMapper plantingGroupManagerMapper;
    private Logger logger = LogManager.getLogger(PlantingGroupManagerServiceImpl.class);

    @Override
    @Transactional
    public String addUser(Map<String, Object> params) {
        String user_account = null;
        String display_name = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String unique_id = UuidUtil.get32UUID();
        params.put("unique_id", unique_id);
        String pwd = "111111";
        String pwdMD5 = new SimpleHash("SHA-1", Constants.SESSION_SALT, pwd).toString();
        params.put("login_passwd", pwdMD5);
        try {
            String login_account = BufferedImageLuminanceSource.PinyinUtil.getCamelPinYin(String.valueOf(params.get("display_name")));
            params.put("login_account", login_account);
            params.put("register_time", LocalDateTime.now().format(formatter));
            Integer count = plantingGroupManagerMapper.doesInclude(params);
            if (count != null) {
                long timelong = new Date().getTime();
                params.put("login_account", login_account + String.valueOf(timelong));
            }
            plantingGroupManagerMapper.addLoginUser(params);
            //添加进分组
            plantingGroupManagerMapper.addGroup(params);
            plantingGroupManagerMapper.addUserFullInfo(params);
            plantingGroupManagerMapper.addUserContact(params);
            plantingGroupManagerMapper.addContactInfo(params);
            plantingGroupManagerMapper.addUserInfo(params);
            plantingGroupManagerMapper.addCertification(params);
            plantingGroupManagerMapper.addFocus(params);
            if (params.get("display_name") != null) {
                display_name = String.valueOf(params.get("display_name"));
            }
            user_account = plantingGroupManagerMapper.queryUserAccount(display_name);
        } catch (Exception e) {
            logger.error(e);
        }
        return user_account;
    }

    @Override
    public void addEducation(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.addEducation(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void addWork(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.addWork(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void addPolicial(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.addPolicial(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void addRreligion(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.addRreligion(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void addHonor(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.addHonor(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public Map<String, Object> queryGroups(Map<String, Object> params) {
        Map<String, Object> resule = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        try {
            list = plantingGroupManagerMapper.getGroups(params);
            if (list.size() > 0) {
                for (Map<String, Object> group : list) {
                    Map<String, Object> rs = new HashMap<String, Object>();
                    List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
                    String group_name = String.valueOf(group.get("group_name"));
                    String group_level = String.valueOf(group.get("group_level"));
                    maps = plantingGroupManagerMapper.queryGroups(group);
                    rs.put("list", maps);
                    rs.put("group_level", group_level);
                    resule.put(group_name, rs);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return resule;
    }

    @Override
    public void removeUser(Integer id) {
        try {
            plantingGroupManagerMapper.removeUser(id);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public Map<String, Object> queryUserById(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = plantingGroupManagerMapper.queryUserById(params);
        } catch (Exception e) {
            logger.error(e);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> queryMembersFamilyById(Map<String, Object> params) {
        List<Map<String, Object>> list = null;
        try {
            list = plantingGroupManagerMapper.queryMembersFamilyById(params);
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryProductOutputById(Map<String, Object> params) {
        List<Map<String, Object>> list = null;
        try {
            list = plantingGroupManagerMapper.queryProductOutputById(params);
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryProvideServicesById(Map<String, Object> params) {
        List<Map<String, Object>> list = null;
        try {
            list = plantingGroupManagerMapper.queryProvideServicesById(params);
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryIntentionById(Map<String, Object> params) {
        List<Map<String, Object>> list = null;
        try {
            list = plantingGroupManagerMapper.queryIntentionById(params);
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    @Override
    public void addMembersFamily(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.addMembersFamily(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void delMembersFamily(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.delMembersFamily(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void updateMembersFamily(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.updateMembersFamily(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void addProductOutput(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.addProductOutput(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void delProductOutput(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.delProductOutput(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void updateProductOutput(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.updateProductOutput(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void addProvideServices(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.addProvideServices(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void delProvideServices(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.delProvideServices(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void updateProvideServices(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.updateProvideServices(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void addIntention(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.addIntention(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void delIntention(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.delIntention(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void updateIntention(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.updateIntention(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void updateUserInfo(Map<String, Object> params) {
        try {
            plantingGroupManagerMapper.updateUserContact(params);
            plantingGroupManagerMapper.updateContactInfo(params);
            plantingGroupManagerMapper.updateUserInfo(params);
            plantingGroupManagerMapper.updateCertification(params);
            plantingGroupManagerMapper.updateFocus(params);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public PageInfo<Map<String, Object>> queryDetail(Map<String, Object> params) {
        PageHelper.offsetPage(Integer.parseInt(String.valueOf(params.get("pageNum"))), Integer.parseInt(String.valueOf(params.get("pageSize"))));
        List<Map<String, Object>> list = null;
        try {
            list = plantingGroupManagerMapper.getGroupUsers(params);
            list.removeAll(Collections.singleton(null));
        } catch (Exception e) {
            logger.error(e);
        }
        return new PageInfo<Map<String, Object>>(list);
    }

    @Override
    public List<Map<String, Object>> getSelectGroup(Map<String, Object> params) {
        List<Map<String, Object>> list = null;
        try {
            list = plantingGroupManagerMapper.getSelectGroup(params);
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    @Override
    public void delUser(Map<String, Object> params) {
        plantingGroupManagerMapper.delUser(params);
    }
}