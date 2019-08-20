package com.ovit.nswy.relationship.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.bean.BeanByRefMapper;
import com.github.xphsc.collect.Lists;
import com.github.xphsc.date.DateFormat;
import com.github.xphsc.date.DateTimeUtil;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.frame.common.util.PageInfoHelper;
import com.ovit.nswy.relationship.feign.UserFeign;
import com.ovit.nswy.relationship.mapper.RelationshipFriendMapper;
import com.ovit.nswy.relationship.model.RelationshipFriend;
import com.ovit.nswy.relationship.model.request.DeleteFriendQuery;
import com.ovit.nswy.relationship.model.request.GetFriendRequestQuery;
import com.ovit.nswy.relationship.model.request.RelationshipFriendQuery;
import com.ovit.nswy.relationship.model.response.GetFriendRequestDTO;
import com.ovit.nswy.relationship.service.RelationshipFriendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RelationshipFriendServiceImpl extends BaseService<RelationshipFriend> implements RelationshipFriendService {
    @Autowired
    private UserFeign userFeign;

    @Autowired
    private RelationshipFriendMapper relationshipFriendMapper;

    @Override
    public Integer saveOrUpddate(RelationshipFriendQuery relationshipFriendQuery) throws ParamValidException {
        RelationshipFriend relationshipFriend = new RelationshipFriend();
        Integer result;
        if(Integers.isNotEmpty(relationshipFriendQuery.getFriendId())&&!relationshipFriendQuery.getFriendId().equals(0)){
            if(relationshipFriendQuery.getAgreeType().equals(2)){
                userFeign.updateStatusByaccount(relationshipFriendQuery.getAccount(),relationshipFriendQuery.getFromAccount());
            }
            relationshipFriend= BeanByRefMapper.copyByRefMapper(relationshipFriendQuery, RelationshipFriend.class);
            relationshipFriend.setUpdateTime(new Date());
            result=  this.updateSelective(relationshipFriend);
        }else{
            result=getAddValue(relationshipFriendQuery);
        }

         return result;
    }



    @Override
    public PageInfo<GetFriendRequestDTO>  listRelationshipFriendRequest(GetFriendRequestQuery getFriendRequestQuery) {
        Example example = new Example(RelationshipFriend.class);
        example.setOrderByClause("create_time DESC");
        PageInfo<GetFriendRequestDTO> pageInfo = null;
        if (Integers.isEmpty(getFriendRequestQuery.getLatelyType())) {
            if (StringUtil.isNotBlank(getFriendRequestQuery.getAccount())) {
                example.createCriteria().andEqualTo("fromAccount", getFriendRequestQuery.getAccount()).andEqualTo("isRead", 1).andEqualTo("agreeType", 0);
                PageHelper.startPage(getFriendRequestQuery.getPageNum(), getFriendRequestQuery.getPageSize());
                List<RelationshipFriend> rowsList = this.findByExample(example);
                PageInfo<RelationshipFriend> origPageInfo = new PageInfo(rowsList);
                List<GetFriendRequestDTO> list = getDisplayName(BeanByRefMapper.copyByRefListMapper(rowsList, GetFriendRequestDTO.class),"");
                pageInfo = new PageInfo(list);
                pageInfo = PageInfoHelper.getPageInfo(origPageInfo, pageInfo);
            }

        } else {
            if (getFriendRequestQuery.getLatelyType().equals(1)) {
                String time = DateTimeUtil.dateTimeToString(DateTimeUtil.dateTimePlusDays(DateTimeUtil.dateTime(), -30), DateFormat.DATE_FORMAT_DAY);
                Example exampleOne = new Example(RelationshipFriend.class);
                exampleOne.setOrderByClause("create_time DESC");
                exampleOne.createCriteria().andEqualTo("account", getFriendRequestQuery.getAccount()).andEqualTo("isRead", 1).andGreaterThan("createTime", time);
                PageHelper.startPage(getFriendRequestQuery.getPageNum(), getFriendRequestQuery.getPageSize());
                List<RelationshipFriend> rowsList = this.findByExample(exampleOne);
                PageInfo<RelationshipFriend> origPageInfo = new PageInfo(rowsList);
                List<GetFriendRequestDTO> list = getDisplayName(BeanByRefMapper.copyByRefListMapper(rowsList, GetFriendRequestDTO.class),"1");
                pageInfo = new PageInfo(list);
                pageInfo = PageInfoHelper.getPageInfo(origPageInfo, pageInfo);
            }
        }

        return pageInfo;
    }

    private List<GetFriendRequestDTO> getDisplayName(List<GetFriendRequestDTO> list,String latelyType) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (GetFriendRequestDTO getFriendRequestDTO : list) {
                String account;
                //如果latelyType为空查询好友邀请数据，实名通过account查询
                //如果不为空则根据fromAccount
                if (StringUtils.isEmpty(latelyType)) {
                    account = getFriendRequestDTO.getAccount();
                } else {
                    account = getFriendRequestDTO.getFromAccount();
                }
                Object obj = userFeign.getDisplayNameByAccount(account);
                JSONObject jsonObject = JSONObject.parseObject(obj.toString());
                Map<String,Object> userInfo = (Map<String,Object>)jsonObject.get("data");
                String displayName = MapUtils.getString(userInfo,"realName");
                getFriendRequestDTO.setDisplayName(displayName);
            }
        }
        return list;
    }


    @Override
    public GetFriendRequestDTO getFriendRequest(Integer gruopId){
        RelationshipFriend relationshipFriend= this.findById(gruopId);
        GetFriendRequestDTO getFriendRequestDTO=new GetFriendRequestDTO();
        getFriendRequestDTO=BeanByRefMapper.copyByRefMapper(relationshipFriend, GetFriendRequestDTO.class);
        return getFriendRequestDTO;
    }

    @Override
    public Integer deleteRelationshipFriend(Integer friendId){
    Integer rows=  this.deleteById(friendId);
    return rows;
    }

    @Override
    public Integer deleteFriend(DeleteFriendQuery deleteFriendQuery){
        Integer rows=null;
        if(Integers.isNotEmpty(deleteFriendQuery.getFriendId())){
            rows=  this.deleteById(deleteFriendQuery.getFriendId());
        }
        if(Lists.isNotEmpty(deleteFriendQuery.getFriendIds())){
           rows=  this.deleteByIds(RelationshipFriend.class,"friendId",deleteFriendQuery.getFriendIds());
        }
        return rows;
    }

    @Override
    public Integer countFriendRequest(String account) {
        Integer result = null;
        Example example = new Example(RelationshipFriend.class);
        if (StringUtil.isNotBlank(account)) {
            example.createCriteria().andEqualTo("fromAccount", account).andEqualTo("agreeType", 0);
            result = this.count(example);
        }
        return result == null ? 0 : result;
    }


    private Boolean existsNmae(String account,String fromAccount,Integer groupId){
        Boolean result=false;
        Example example = new Example(RelationshipFriend.class);
        if (StringUtil.isNotBlank(fromAccount)) {
            example.createCriteria().andEqualTo("account", account).andEqualTo("fromAccount", fromAccount).andEqualTo("groupId",groupId);
            List<RelationshipFriend>  relationshipFriendList= this.findByExample(example);
            if(Lists.isNotEmpty(relationshipFriendList)){
                result= true;
            }else{
                result=false;
            }
        }
        return result;
    }

    @Override
    public Boolean getExistsFriend(String account,String fromAccount){
        Boolean result=false;
        Example example = new Example(RelationshipFriend.class);
        if (StringUtil.isNotBlank(fromAccount)) {
            example.createCriteria().andEqualTo("account", account).andEqualTo("fromAccount", fromAccount).andEqualTo("agreeType", 2);
            List<RelationshipFriend>  relationshipFriendList= this.findByExample(example);
            if(Lists.isNotEmpty(relationshipFriendList)){
                result= true;
            }else{
                result=false;
            }
        }
        return result;
    }

    @Override
    public List<String> queryFriendByAccount(String account) {
        return relationshipFriendMapper.queryFriendByAccount(account);
    }

    @Override
    public int deleteFriendByAccount(String account, String fromAccount) {
        return relationshipFriendMapper.deleteFriendByAccount(account,fromAccount);
    }

    private Integer getAddValue(RelationshipFriendQuery relationshipFriendQuery){
    Integer result ;
    RelationshipFriend relationshipFriend = new RelationshipFriend();
    List<RelationshipFriend> relationshipFriendList=null;
    Boolean rows=existsNmae(relationshipFriendQuery.getAccount(),relationshipFriendQuery.getFromAccount(),relationshipFriendQuery.getGroupId());
    if(rows){
        throw new ParamValidException("用户已存在：不能重复添加");
    }
    if(StringUtil.isNotBlank(relationshipFriendQuery.getFrindList())){
        List<RelationshipFriendQuery>  relationshipFriendQueryList= JSONHelper.parseArray(relationshipFriendQuery.getFrindList(),RelationshipFriendQuery.class);
        relationshipFriendList=BeanByRefMapper.copyByRefListMapper(relationshipFriendQueryList,RelationshipFriend.class);
        for(RelationshipFriend friend:relationshipFriendList){
            friend.setCreateTime(new Date());
            friend.setAgreeType(0);
            friend.setIsRead(1);
            friend.setAccount(relationshipFriendQuery.getAccount());
            friend.setGroupId(relationshipFriendQuery.getGroupId());
            friend.setRequestMessage(relationshipFriendQuery.getRequestMessage());
        }
        result=this.save(relationshipFriendList);
    }else{
        relationshipFriend = BeanByRefMapper.copyByRefMapper(relationshipFriendQuery, RelationshipFriend.class);
        relationshipFriend.setCreateTime(new Date());
        if(Integers.isNotEmpty(relationshipFriendQuery.getAgreeType())){
            relationshipFriend.setAgreeType(relationshipFriendQuery.getAgreeType());
        }else{
            relationshipFriend.setAgreeType(0);
        }
        relationshipFriend.setIsRead(1);
        result= this.save(relationshipFriend);
    }
    return result;

  }
}

