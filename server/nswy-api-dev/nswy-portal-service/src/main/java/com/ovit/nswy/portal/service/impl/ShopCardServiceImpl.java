package com.ovit.nswy.portal.service.impl;

import com.github.xphsc.mutable.Integers;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.frame.common.util.ContextHolderUtil;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.portal.mapper.ShopCardMapper;
import com.ovit.nswy.portal.mapper.ShopOrderProductMapper;
import com.ovit.nswy.portal.model.ShopCard;
import com.ovit.nswy.portal.model.ShopCard;
import com.ovit.nswy.portal.model.response.ShopCardDTO;
import com.ovit.nswy.portal.model.response.ShopProductDTO;
import com.ovit.nswy.portal.service.ShopCardService;
import com.ovit.nswy.portal.service.ShopCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxy
 */
@Service
public class ShopCardServiceImpl extends BaseService<ShopCard> implements ShopCardService {


    @Autowired
    private ShopCardMapper shopCardMapper;


    @Override
    public Integer add(ShopCard entity) {
       // LoginUser loginUser = (LoginUser) ContextHolderUtil.getRequest().getSession().getAttribute("currentUser");
       // entity.setAccount(loginUser.getLoginAccount());//当前登录人信息
        //先查看是否以有该商品，有修改，没有添加
        Map<String, Object> param=new HashMap<>();
        param.put("account",entity.getAccount());
        param.put("commodityId",entity.getCommodityTypeInfoId());
        ShopCard oldEntity=shopCardMapper.findList(param);
        if(oldEntity==null) {
            Integer result = saveSelective(entity);
            return Integers.isEmpty(result) ? 0 : result;
        }else{
            //不等于空的情况下，判断数量是否长出
            int allCount=oldEntity.getNum()+entity.getNum();
            int pCount=shopCardMapper.findProductCount(oldEntity.getCommodityTypeInfoId());
            if(pCount<=allCount) {
                oldEntity.setNum(allCount);
                Integer result = updateSelective(oldEntity);
                return Integers.isEmpty(result) ? 0 : result;
            }else{
                return -1;
            }
        }
    }


    @Override
    public List<ShopCardDTO> list(String account) {
      //  Example example = new Example(ShopCard.class);
       // LoginUser loginUser = (LoginUser) ContextHolderUtil.getRequest().getSession().getAttribute("currentUser");
       // example.createCriteria().andEqualTo("account", loginUser.getLoginAccount());
        List<ShopCardDTO> list = shopCardMapper.findAllList(account);
        return list;
    }

    @Override
    public ShopProductDTO findProduct(Integer commodityTypeInfoId) {
        return  shopCardMapper.findProductById(commodityTypeInfoId);
    }
    @Override
    public Integer clear(String account) {
       // LoginUser loginUser = (LoginUser) ContextHolderUtil.getRequest().getSession().getAttribute("currentUser");
        Integer result=deleteByProperty(ShopCard.class,"account",account);
        return Integers.isEmpty(result)?0:result;
    }

    @Override
    public Integer delete(List<Object> idList) {
        Integer result=deleteByIds(ShopCard.class,"id",idList);
        return Integers.isEmpty(result)?0:result;
    }

    @Override
    public Integer update(List<ShopCard> entitys) {
         Integer result=0;
        for(ShopCard item:entitys){
          int i= updateSelective(item);
          if(i>0){
              result++;
          }
        }

        return Integers.isEmpty(result)?0:result;
    }
}
