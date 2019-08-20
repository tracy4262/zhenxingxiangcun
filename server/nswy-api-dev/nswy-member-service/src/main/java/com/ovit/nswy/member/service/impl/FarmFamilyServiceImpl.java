package com.ovit.nswy.member.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.member.mapper.FarmFamilyMapper;
import com.ovit.nswy.member.service.FarmFamilyService;
import com.ovit.nswy.member.util.UuidUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FarmFamilyServiceImpl implements FarmFamilyService {

    private Logger logger = LogManager.getLogger(FarmFamilyServiceImpl.class);


    @Autowired
    private FarmFamilyMapper farmFamilyMapper;

    /**
     * 添加分组
     * @param params
     */
    @Override
    public String insertGroup(Map<String,Object> params){
        List<Map<String,Object>> list = farmFamilyMapper.findGroup(params);
        if(list!=null && list.size()>0){
            return "error";
        }else{
            farmFamilyMapper.insertGroup(params);
            return "success";
        }

    }


    /**
     * 添加种养合作社名称
     * @param params
     */
    @Override
    public String insertTitle(Map<String,Object> params){
        List<Map<String,Object>> list = farmFamilyMapper.findTitle(params);
        if(list!=null && list.size()>0){
            return "error";
        }else{
            farmFamilyMapper.insertTitle(params);
            return "success";
        }

    }

    @Override
    public void updateTitle(Map<String,Object> params){
        farmFamilyMapper.updateTitle(params);
    }

    @Override
    public void updateGroup(Map<String,Object> params){
        farmFamilyMapper.updateGroup(params);
    }

    @Override
    public List<Map<String,Object>> findGroup(Map<String,Object> params){
        return farmFamilyMapper.findGroup(params);
    }

    @Override
    public List<Map<String,Object>> findTitle(Map<String,Object> params){
        return farmFamilyMapper.findTitle(params);
    }

    /**
     * 添加成员
     * @param params
     */
    @Override
    public String insertFarm(Map<String,Object> params){
        String farmType = MapUtils.getString(params,"farm_type");
        // 0 添加新账号
        if("0".equals(farmType)){
            params.put("unique_id",UuidUtil.get32UUID());
            String farmValue = MapUtils.getString(params,"idCard");
            String password=farmValue.substring(farmValue.length()-6,farmValue.length());
            password = new SimpleHash("SHA-1", Constants.SESSION_SALT, password).toString();
            params.put("login_passwd",password);
            params.put("user_type","2");//种养户
            Map<String,Object> param = new HashMap<>();
            param.put("farm_account",MapUtils.getString(params,"farm_account"));
            List<Map<String,Object>> list = farmFamilyMapper.findLogin(param);
            if(list!=null && list.size()>0){
                return "error";
            }else {
                farmFamilyMapper.insertLogin(params);
                farmFamilyMapper.insertCertification(params);
                farmFamilyMapper.insertFarm(params);
                return "success";
            }
        }else{
            String farmValue = new SimpleHash("SHA-1", Constants.SESSION_SALT, MapUtils.getString(params,"password")).toString();
            params.put("login_passwd",farmValue);
            List<Map<String,Object>> list = farmFamilyMapper.findLogin(params);
            if(list!=null && list.size()>0){
                /*Map<String,Object> farmMap = new HashMap<>();
                farmMap.put("group_id",MapUtils.getString(params,"group_id"));
                List<Map<String,Object>> maps = farmFamilyMapper.findGroup(farmMap);
                params.put("group_name",maps.get(0).get("group_name"));*/
                List<Map<String,Object>> farmList = farmFamilyMapper.findFarmFamily(params);
                if(farmList!=null && farmList.size()>0){
                   return "error1";
                }else{
                    farmFamilyMapper.insertFarm(params);
                    return "success";
                }

            }else {
                return "errors";
            }
        }

    }


    @Override
    public PageInfo<Map<String,Object>> findFarmFamilyList(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), 10);
        List<Map<String,Object>> list = farmFamilyMapper.findFarmFamilyList(params);
        return new PageInfo<>(list);

    }

    @Override
    @Transactional
    public void deleteFarmFamily(Map<String,Object> params){
        Integer farm_family_id = MapUtils.getInteger(params,"farm_family_id");
        farmFamilyMapper.deleteFarmFamily(farm_family_id);
    }

    /**
     * 查询总人数
     * @param params
     * @return
     */
    @Override
    public Integer findCountFarm(Map<String,Object> params){
        Integer farmFamilyCount = farmFamilyMapper.findCountFarmFamily(params);
        Integer familyCount = farmFamilyMapper.findCountFamilyMember(params);

        return familyCount+farmFamilyCount;
    }


}
