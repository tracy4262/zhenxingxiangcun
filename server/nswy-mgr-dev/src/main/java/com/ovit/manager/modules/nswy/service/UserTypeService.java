package com.ovit.manager.modules.nswy.service;


import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.service.TreeService;
import com.ovit.manager.modules.nswy.dao.ShopCurrencyCommodityDao;
import com.ovit.manager.modules.nswy.dao.SysUserInfoDao;
import com.ovit.manager.modules.nswy.dao.UserTypeInfoDao;
import com.ovit.manager.modules.nswy.entity.UserTypeInfo;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class UserTypeService extends CrudService<UserTypeInfoDao,UserTypeInfo> {


    @Autowired
    private UserTypeInfoDao userTypeInfoDao;



    /**
     * 查询sys_user_info
     * @param
     * @param request
     * @param response
     * @return
     */
    public Page<UserTypeInfo> findList(UserTypeInfo userTypeInfo, HttpServletRequest request, HttpServletResponse response){
        Page<UserTypeInfo> page = this.findPage(new Page<UserTypeInfo>(request, response), userTypeInfo);
        logger.info("用户类型列表查询：{}"+page.getList());
        return page;
    }


    public List<Map<String,Object>> findUserType(Map<String,Object> params){
        return userTypeInfoDao.findUserType(params);
    }


    public void insertUserTypeInfo(UserTypeInfo userTypeInfo){
        String loginName = userTypeInfo.getAccount();
        if(loginName.equals("thinkgem")){
            userTypeInfo.setAccount("administrator");
        }else{
            userTypeInfo.setAccount(loginName);
        }

        Map<String,Object> map = userTypeInfoDao.findMaxInfoId();
        if(MapUtils.isEmpty(map)){
            userTypeInfo.setUserType("0");
        }else{
            Integer userType = MapUtils.getInteger(map,"user_type")+1;
            userTypeInfo.setUserType(String.valueOf(userType));
        }

        userTypeInfoDao.insertUserTypeInfo(userTypeInfo);
    }

    public void deleteUserType(Integer infoId){
        userTypeInfoDao.deleteUserType(infoId);
    }



}
