package com.ovit.nswy.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.bean.BeanByRefMapper;
import com.github.xphsc.collect.Lists;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.ObjectUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.frame.common.util.ContextHolderUtil;
import com.ovit.nswy.frame.common.util.PageInfoHelper;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.portal.mapper.ShopAddressMapper;
import com.ovit.nswy.portal.mapper.ShopOrderMapper;
import com.ovit.nswy.portal.model.ShopAddress;
import com.ovit.nswy.portal.service.ShopAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxy
 */
@Service
public class ShopAddressServiceImpl extends BaseService<ShopAddress> implements ShopAddressService {


    @Autowired
    private ShopAddressMapper shopAddressMapper;

    @Override
    public Integer add(ShopAddress entity) {
        //LoginUser loginUser = (LoginUser) ContextHolderUtil.getRequest().getSession().getAttribute("currentUser");
        // entity.setAccount(loginUser.getLoginAccount());//当前登录人信息
        if (entity.getIsDefault()) {
            ShopAddress old = new ShopAddress();
            old.setAccount(entity.getAccount());
            old.setIsDefault(false);
            shopAddressMapper.clearDefault(old);
        }

        Integer result = saveSelective(entity);
        return Integers.isEmpty(result) ? 0 : result;
    }


    @Override
    public List<ShopAddress> list(String account,Integer order) {
        Example example = new Example(ShopAddress.class);
        if(order==1){
            example.setOrderByClause("is_default DESC,id DESC");//按默认降序，创建时间
        }else{
            example.setOrderByClause("id ASC");//按默认降序，创建时间
        }

        example.createCriteria().andEqualTo("account", account);
        List<ShopAddress> list = this.findByExample(example);
        return list;
    }


    @Override
    public ShopAddress selectOne(Integer id) {
        ShopAddress entity = null;
        if (Integers.isNotEmpty(id)) {
            entity = this.findById(id);
        }
        return entity;
    }

    @Override
    public ShopAddress selectDefault(String account) {
        ShopAddress query = new ShopAddress();
        query.setAccount(account);
        query.setIsDefault(true);
        ShopAddress entity = this.findOne(query);
        return entity;
    }

    @Override
    public Integer delete(Integer id) {
        Integer result = null;
        if (Integers.isNotEmpty(id)) {
            result = deleteById(id);
        }
        return Integers.isEmpty(result) ? 0 : result;
    }

    @Override
    public Integer update(ShopAddress entity) {
        if (entity.getIsDefault()) {
            ShopAddress old = new ShopAddress();
            old.setAccount(entity.getAccount());
            old.setIsDefault(false);
            shopAddressMapper.clearDefault(old);
        }
        entity.setUpdateTime(new Date());
        Integer result = updateSelective(entity);
        return Integers.isEmpty(result) ? 0 : result;
    }

    @Override
    public Integer updateDefault(ShopAddress entity) {
        //所有地址默认为空
        ShopAddress old = new ShopAddress();
        old.setAccount(entity.getAccount());
        old.setIsDefault(false);
        shopAddressMapper.clearDefault(old);

        //更新现有默认地址
        entity.setUpdateTime(new Date());
        entity.setIsDefault(true);
        Integer result = updateSelective(entity);
        return Integers.isEmpty(result) ? 0 : result;
    }
}
