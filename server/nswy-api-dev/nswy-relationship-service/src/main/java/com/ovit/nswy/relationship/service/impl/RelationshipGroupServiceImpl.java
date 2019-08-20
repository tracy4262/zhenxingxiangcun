package com.ovit.nswy.relationship.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.bean.BeanByRefMapper;
import com.github.xphsc.collect.Lists;
import com.github.xphsc.collect.Sorts;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.relationship.enums.GroupTypeEnum;
import com.ovit.nswy.relationship.feign.UserFeign;
import com.ovit.nswy.relationship.manager.FriendRequestManager;
import com.ovit.nswy.relationship.mapper.RelationshipFriendMapper;
import com.ovit.nswy.relationship.mapper.RelationshipGroupMapper;
import com.ovit.nswy.relationship.model.RelationshipFriend;
import com.ovit.nswy.relationship.model.RelationshipGroup;
import com.ovit.nswy.relationship.model.request.ListFollowQuery;
import com.ovit.nswy.relationship.model.request.RelationshipGroupListQuery;
import com.ovit.nswy.relationship.model.request.RelationshipGroupQuery;
import com.ovit.nswy.relationship.model.response.*;
import com.ovit.nswy.relationship.service.RelationshipGroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


/**
 * Created by ${huipei.x} on 2017-11-22.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RelationshipGroupServiceImpl extends BaseService<RelationshipGroup> implements RelationshipGroupService {

    private static final Logger logger = LogManager.getLogger(RelationshipGroupServiceImpl.class);

    @Autowired
    private RelationshipFriendMapper relationshipFriendMapper;
    @Autowired
    private FriendRequestManager friendRequestManager;
    @Autowired
    private RelationshipGroupMapper relationshipGroupMapper;
    @Autowired
    private UserFeign userFeign;
    @Override
    public RelationshipDTO listRelationshipGroup(RelationshipGroupListQuery relationshipGroupListQuery) throws ParamValidException {

        RelationshipDTO relationshipDTO=new RelationshipDTO();
        List<RelationshipGroupDTO> relationshipGroupDTOList;
        List<GroupParentTypeDTO> groupParentDTOList;
        Example example = new Example(RelationshipGroup.class);
        Example exampleOne = new Example(RelationshipGroup.class);
       if (StringUtil.isNotBlank(relationshipGroupListQuery.getAccount())) {
           if(!existsInitGroup(relationshipGroupListQuery.getAccount())){
               initRelationshipGroup(relationshipGroupListQuery.getAccount());
           }else{
               example.createCriteria().andEqualTo("account", relationshipGroupListQuery.getAccount()).andEqualTo("parentId", 0);
               List<RelationshipGroup> groupParentList = this.findByExample(example);
               groupParentDTOList = BeanByRefMapper.copyByRefListMapper(groupParentList, GroupParentTypeDTO.class);
               if (Integers.isNotEmpty(relationshipGroupListQuery.getParentId())) {
                   exampleOne.createCriteria().andEqualTo("account", relationshipGroupListQuery.getAccount()).andNotIn("parentId", Lists.newArrayList(0)).andEqualTo("parentId", relationshipGroupListQuery.getParentId());
                   List<RelationshipGroup> grouptList = this.findByExample(exampleOne);
                   relationshipGroupDTOList = BeanByRefMapper.copyByRefListMapper(grouptList, RelationshipGroupDTO.class);
                   if(relationshipGroupListQuery.getParentId().equals(0)){
                       relationshipDTO.setGroupParentList(groupParentDTOList);
                     for(GroupParentTypeDTO groupParentTypeDTO:groupParentDTOList){
                           List<GetFriendRequestDTO>  getFriendRequestDTOList=getFriendRequestDTOList(groupParentTypeDTO,relationshipGroupListQuery);
                         groupParentTypeDTO.setFriendList(getFriendRequestDTOList);
                       }

                   }else{
                       relationshipDTO.setGrouptList(relationshipGroupDTOList);
                   }

               }else{
                   getRelationshipDTO(groupParentDTOList, relationshipGroupListQuery, relationshipDTO);
               }

           }
       }
        return relationshipDTO;

    }

    /**
     * 获取用户关注列表
     * 用于在用户关系圈好友初始化好友时
     * @param account
     * @return
     */
    private List<String> getUserFollow(String account) {
        //通过用户查询用户的所有关注的好友
        ListFollowQuery listFollowQuery = new ListFollowQuery();
        listFollowQuery.setAccount(account);
        listFollowQuery.setFollowType(1);
        Object followData = userFeign.listfollow(listFollowQuery);
        JSONObject followJsonObject = JSONObject.parseObject(followData.toString());
        List<FansDTO> followList = JSONHelper.parseArray(followJsonObject.get("data").toString(), FansDTO.class);
        logger.trace("通过account:{} 查询该用户关注的好友详情 followList:{}", account, followList);
        //所有关注的好友集合
        List<String> followAccountList = new ArrayList<String>();
        //遍历获取所有关注的好友account
        for (FansDTO fansDTO : followList) {
            String fansAccount = fansDTO.getAccount();
            if (StringUtils.isNotEmpty(fansAccount)) {
                followAccountList.add(fansAccount);
            }
        }
        logger.debug("通过account:{} 查询该用户关注的好友account数据 followAccountList:{}", account, followAccountList);
        return followAccountList;
    }

    /**
     * 设置用户对其好友的关注、未关注、互相关注状态
     * 先标识出用户好友中的哪些是用户关注的 即用户为好友的粉丝
     * 再通过用户关注的好友判断是否互为粉丝 即互相关注
     * 设置状态值
     * 1：已关注
     * 2：互相关注
     * 3：未关注
     * friendList: 用户好友集合
     * account：用户account
     * followAccountList：用户关注的account列表
     * @param friendList
     * @param account
     * @param followAccountList
     */
    private void setUserFollow(List<GetFriendRequestDTO> friendList, String account, List<String> followAccountList) {
        if (CollectionUtils.isNotEmpty(friendList)) {
            for (GetFriendRequestDTO getFriendRequestDTO : friendList) {
                //判断用户好友account是否存在于用户关注的好友account集合
                //注：该GetFriendRequestDTO model下用户好友account字段为fromAccount
                String fromAccount = getFriendRequestDTO.getFromAccount();
                if (followAccountList.contains(fromAccount)) {
                    //标识该好友为用户所关注的
                    /**
                     * 设置状态值
                     * 1：已关注
                     * 2：互相关注
                     * 3：未关注
                     */
                    getFriendRequestDTO.getFollowInfo().put("follow", "1");
                    //用户已关注的 继续判断该用户关注的用户是否也关注了该用户 即判断是否互为粉丝
                    ListFollowQuery followQuery=new ListFollowQuery();
                    //设置该用户为account 该用户关注的用户为fans_account
                    followQuery.setAccount(fromAccount);//fans_account
                    followQuery.setFromAccount(account);//account
                    followQuery.setFollowType(2);
                    Object mutualConcernData = userFeign.listfollow(followQuery);
                    JSONObject mutualConcernJsonObject = JSONObject.parseObject(mutualConcernData.toString());
                    List<FansDTO> mutualConcernList = JSONHelper.parseArray(mutualConcernJsonObject.get("data").toString(), FansDTO.class);
                    if (CollectionUtils.isNotEmpty(mutualConcernList)) {
                        //互相关注状态
                        getFriendRequestDTO.getFollowInfo().put("follow", "2");
                    }
                } else {
                    //未关注状态
                    getFriendRequestDTO.getFollowInfo().put("follow", "3");
                }
            }
        }
    }



    @Override
    public Integer saveOrUpdate(RelationshipGroupQuery relationshipGroupQuery) throws ParamValidException {
        int result ;
        Map<String,Object> isDefault = relationshipGroupMapper.queryIsDefault(relationshipGroupQuery.getGruopId());
        //校验是否为已存在的默认分组
        if (MapUtils.isNotEmpty(isDefault)) {
            throw new ParamValidException("默认分组：不能被修改");
        } else {
            //不是默认分组时校验是否已存在
            Map<String,Object> isExist = relationshipGroupMapper.queryIsExist(relationshipGroupQuery.getGruopId());
            if (MapUtils.isNotEmpty(isExist)) {
                //已存在，则修改，修改只修改名称
                List<Map<String,Object>> nameIsExist = relationshipGroupMapper.nameIsExist(relationshipGroupQuery);
                if (CollectionUtils.isNotEmpty(nameIsExist)) {
                    throw new ParamValidException("groupName：分组名称以存在");
                }
                result = relationshipGroupMapper.updateRelationshipGroup(relationshipGroupQuery);
            } else {
                result = getSaveGroup(relationshipGroupQuery);
            }
        }
        return result;
    }

    @Override
    public RelationshipGroupDTO getRelationshipGroup(Integer gruopId){
        RelationshipGroup relationshipGroup= this.findById(gruopId);
        RelationshipGroupDTO relationshipGroupDTO=new RelationshipGroupDTO();
        relationshipGroupDTO=BeanByRefMapper.copyByRefMapper(relationshipGroup, RelationshipGroupDTO.class);
        return relationshipGroupDTO;
    }


    @Override
    public Integer deleteRelationshipGroup(Integer gruopId) throws ParamValidException {
        Integer rows=null;
        Boolean valuep= exitsInitByPid(gruopId, 0);
        if(valuep){
            throw new ParamValidException("默认分组：不能被删除");
        }else{
            Boolean value= exitsInitByid(gruopId);
            if(value){
                throw new ParamValidException("默认分组：不能被删除");
            }else{
                rows = this.deleteById(gruopId);
            }
        }



        return rows;
    }

    @Override
    public Integer initRelationshipGroup(String account) throws ParamValidException {
        Integer rows=null;
        if(!existsInitGroup(account)){
             rows = addInit(account);
        }
        return rows==null?0:rows;
    }


    private Integer addInit(String account) {
        List<RelationshipGroup> RelationshipGroupList = Lists.newArrayList();
        List<RelationshipGroup> RelationshipGroupParentList = Lists.newArrayList();
        List<GroupTypeDTO> ParentGroupTypeDTOs = Lists.newArrayList(
                new GroupTypeDTO(1, GroupTypeEnum.WORK_TYPE.getName()),
                new GroupTypeDTO(2, GroupTypeEnum.FRIEND_TYPE.getName()),
                new GroupTypeDTO(3, GroupTypeEnum.PUBLIC_TYPE.getName())

        );

        List<GroupTypeDTO> workGroupTypeDTOs = Lists.newArrayList(
                new GroupTypeDTO(1, GroupTypeEnum.WorkGroupTypeEnum.WORK_GROUP__ONE.getName()),
                new GroupTypeDTO(2, GroupTypeEnum.WorkGroupTypeEnum.WORK_GROUP_TWO.getName()),
                new GroupTypeDTO(3, GroupTypeEnum.WorkGroupTypeEnum.WORK_GROUP_THREE.getName()),
                new GroupTypeDTO(4, GroupTypeEnum.WorkGroupTypeEnum.WORK_GROUP_FOUR.getName()),
                new GroupTypeDTO(5, GroupTypeEnum.WorkGroupTypeEnum.WORK_GROUP_FIVE.getName())
        );
        List<GroupTypeDTO> friendGroupTypeDTOS = Lists.newArrayList(
                new GroupTypeDTO(1, GroupTypeEnum.FriendGroupTypeEnum.FRIEND_GROUP__ONE.getName()),
                new GroupTypeDTO(2, GroupTypeEnum.FriendGroupTypeEnum.FRIEND_GROUP_TWO.getName()),
                new GroupTypeDTO(3, GroupTypeEnum.FriendGroupTypeEnum.FRIEND_GROUP_THREE.getName()),
                new GroupTypeDTO(4, GroupTypeEnum.FriendGroupTypeEnum.FRIEND_GROUP_FOUR.getName()),
                new GroupTypeDTO(5, GroupTypeEnum.FriendGroupTypeEnum.FRIEND_GROUP_FIVE.getName()),
                new GroupTypeDTO(6, GroupTypeEnum.FriendGroupTypeEnum.FRIEND_GROUP_SEX.getName())
        );

        for (GroupTypeDTO ParentGroupTypeDTO : ParentGroupTypeDTOs) {
            RelationshipGroup relationshipGroupParent = new RelationshipGroup();
            relationshipGroupParent.setGroupType(ParentGroupTypeDTO.getGroupType());
            relationshipGroupParent.setGruopName(ParentGroupTypeDTO.getGruopName());
            relationshipGroupParent.setCreateTime(new Date());
            relationshipGroupParent.setParentId(0);
            relationshipGroupParent.setAccount(account);
            relationshipGroupParent.setGroupGrade(0);
            RelationshipGroupParentList.add(relationshipGroupParent);
        }
        this.save(RelationshipGroupParentList);
        Example example = new Example(RelationshipGroup.class);
        Example exampleOne = new Example(RelationshipGroup.class);
        Example exampleTwo = new Example(RelationshipGroup.class);
        if (StringUtil.isNotBlank(account)) {
            example.createCriteria().andEqualTo("account", account).andEqualTo("groupType", 1).andEqualTo("parentId", 0);
            List<RelationshipGroup> groupParentOneList = this.findByExample(example);
            Integer goupParentOne = groupParentOneList.get(0).getGruopId();
            exampleOne.createCriteria().andEqualTo("account", account).andEqualTo("groupType", 2).andEqualTo("parentId", 0);
            List<RelationshipGroup> groupParentTwoList = this.findByExample(exampleOne);
            Integer goupParentTWO = groupParentTwoList.get(0).getGruopId();
            exampleTwo.createCriteria().andEqualTo("account", account).andEqualTo("groupType", 3).andEqualTo("parentId", 0);
            List<RelationshipGroup> groupParentThreeList = this.findByExample(exampleOne);
            Integer goupParentThree = groupParentThreeList.get(0).getGruopId();
            for (GroupTypeDTO workGroupTypeDTO : workGroupTypeDTOs) {
                RelationshipGroup relationshipGroupWork = new RelationshipGroup();
                relationshipGroupWork.setGroupType(workGroupTypeDTO.getGroupType());
                relationshipGroupWork.setGruopName(workGroupTypeDTO.getGruopName());
                relationshipGroupWork.setParentId(goupParentOne);
                relationshipGroupWork.setCreateTime(new Date());
                relationshipGroupWork.setAccount(account);
                relationshipGroupWork.setGroupGrade(1);
                RelationshipGroupList.add(relationshipGroupWork);
            }
            for (GroupTypeDTO friendGroupTypeDTO : friendGroupTypeDTOS) {
                RelationshipGroup relationshipGroupfriend = new RelationshipGroup();
                relationshipGroupfriend.setGroupType(friendGroupTypeDTO.getGroupType());
                relationshipGroupfriend.setGruopName(friendGroupTypeDTO.getGruopName());
                relationshipGroupfriend.setParentId(goupParentTWO);
                relationshipGroupfriend.setCreateTime(new Date());
                relationshipGroupfriend.setAccount(account);
                relationshipGroupfriend.setGroupGrade(1);
                RelationshipGroupList.add(relationshipGroupfriend);
            }
        }
        return this.save(RelationshipGroupList);
    }

    public Integer countRelationshipGroup(String account, Integer parentId) {
        Example example = new Example(RelationshipGroup.class);
        if (StringUtil.isNotBlank(account) && Integers.isNotEmpty(parentId)) {
            example.createCriteria().andEqualTo("account", account).andEqualTo("parentId", parentId);
        }
        return this.count(example);
    }


    public Integer countGroupTypeMax(String account, Integer parentId) {
        List<RelationshipGroup> relationshipGroupList = Lists.newArrayList();
        Example example = new Example(RelationshipGroup.class);
        if (StringUtil.isNotBlank(account) && Integers.isNotEmpty(parentId)) {
            example.createCriteria().andEqualTo("account", account).andEqualTo("parentId", parentId);
          relationshipGroupList = this.findByExample(example);
           Sorts.sortList(relationshipGroupList, "groupType", "desc");
        }
        Integer rows=null;
        if(Lists.isNotEmpty(relationshipGroupList)){
                rows=relationshipGroupList.get(0).getGroupType();
        }
        return rows==null?0:rows;
    }

    public Integer countGroupGradeMax(String account, Integer parentId) {
        List<RelationshipGroup> relationshipGroupList = Lists.newArrayList();
        Example example = new Example(RelationshipGroup.class);
        if (StringUtil.isNotBlank(account) && Integers.isNotEmpty(parentId)) {
            example.createCriteria().andEqualTo("account", account).andEqualTo("parentId", parentId);
            relationshipGroupList = this.findByExample(example);
            Sorts.sortList(relationshipGroupList, "groupGrade", "desc");
        }
        Integer rows=null;
        if(Lists.isNotEmpty(relationshipGroupList)){
            rows=relationshipGroupList.get(0).getGroupType();
        }
        return rows==null?0:rows;
    }


    private Boolean existsInitGroup(String account){
        List<RelationshipGroup> relationshipGroupList = Lists.newArrayList();
        Example example = new Example(RelationshipGroup.class);
        if (StringUtil.isNotBlank(account) ) {
            example.createCriteria().andEqualTo("account", account);
            relationshipGroupList = this.findByExample(example);
        }
        if(Lists.isNotEmpty(relationshipGroupList)){
            return true;
        }else{
            return false;
        }
    }


    private Boolean existsName(String account,String gruupName,Integer parentId){
        List<RelationshipGroup> relationshipGroupList;
        Example example = new Example(RelationshipGroup.class);
        if (StringUtil.isNotBlank(account)&&Integers.isEmpty(parentId)) {
            example.createCriteria().andEqualTo("account", account).andEqualTo("gruopName", gruupName);
            relationshipGroupList = this.findByExample(example);
        }else{
            example.createCriteria().andEqualTo("account", account).andEqualTo("gruopName", gruupName).andEqualTo("parentId", parentId);
            relationshipGroupList = this.findByExample(example);
        }
        if(Lists.isNotEmpty(relationshipGroupList)){
            return true;
        }else{
            return false;
        }
    }

    private Boolean exitsInitByPid(Integer gruopId,Integer parentId){
        List<RelationshipGroup> relationshipGroupList = Lists.newArrayList();
        List<Integer> groupTypeList = Lists.newArrayList(1,2,3);
        Example example = new Example(RelationshipGroup.class);
        if (Integers.isNotEmpty(gruopId)&&parentId.equals(0)) {
            example.createCriteria().andEqualTo("gruopId", gruopId).andEqualTo("parentId", 0).andIn("groupType", groupTypeList);
            relationshipGroupList = this.findByExample(example);
        }

        if(Lists.isNotEmpty(relationshipGroupList)){
            return true;
        }else{
            return false;
        }
    }

    private Boolean exitsInitByid(Integer gruopId){
        List<RelationshipGroup> relationshipGroupList = Lists.newArrayList();
        List<Integer> groupTypeList= Lists.newArrayList(1,2,3,4,5);
        List<Integer> groupParentIdList= Lists.newArrayList(0);
        Example example = new Example(RelationshipGroup.class);
        if (Integers.isNotEmpty(gruopId)) {
                example.createCriteria().andEqualTo("gruopId", gruopId).andNotIn("parentId", groupParentIdList).andIn("groupType", groupTypeList);
                relationshipGroupList = this.findByExample(example);
        }
        if(Lists.isNotEmpty(relationshipGroupList)){
            return true;
        }else{
            return false;
        }
    }

    private RelationshipDTO getRelationshipDTO(List<GroupParentTypeDTO> groupParentDTOList,RelationshipGroupListQuery relationshipGroupListQuery,RelationshipDTO relationshipDTO){
        //获取用户关注列表
        List<String> followAccountList = getUserFollow(relationshipGroupListQuery.getAccount());
        List<RelationshipGroup> grouptList;
        List<RelationshipGroupDTO> shipGroupDTOList;
        List<RelationshipGroupOneDTO> shipGroupDTOOneList;
        List<RelationshipGroupTwoDTO> shipGroupDTOTwoList;
        List<RelationshipGroupThreeDTO> shipGroupDTOThreeList;
        List<GroupParentTypeDTO> groupParentTypeDTOs=Lists.newArrayList();
        List<GetFriendRequestDTO> friendRequestDTOList;
        for(GroupParentTypeDTO groupParentTypeDTO:groupParentDTOList){
            Example exampleTwo = new Example(RelationshipGroup.class);
            exampleTwo.createCriteria().andEqualTo("account", relationshipGroupListQuery.getAccount()).andEqualTo("parentId", groupParentTypeDTO.getGruopId());
            grouptList = this.findByExample(exampleTwo);
            shipGroupDTOList = BeanByRefMapper.copyByRefListMapper(grouptList, RelationshipGroupDTO.class);
            for(RelationshipGroupDTO relationshipGroupDTO:shipGroupDTOList){
                if(relationshipGroupDTO.getGroupGrade().equals(1)){
                    friendRequestDTOList=getFriendRequestDTOList(relationshipGroupDTO, relationshipGroupListQuery);
                    friendRequestDTOList=friendRequestManager.getFriendRequest(friendRequestDTOList, 1);
                    friendRequestDTOList=friendRequestManager.getFriendRequest(friendRequestDTOList, 2);
                    friendRequestDTOList=friendRequestManager.getFriendRequest(friendRequestDTOList, 3);
                    //设置用户关注、未关注、互相关注状态
                    setUserFollow(friendRequestDTOList, relationshipGroupListQuery.getAccount(), followAccountList);
                    relationshipGroupDTO.setFriendList(friendRequestDTOList);
                    shipGroupDTOOneList=getRelationshipGroupList(relationshipGroupDTO,relationshipGroupListQuery);
                    relationshipGroupDTO.setGroupList(shipGroupDTOOneList);
                    for(RelationshipGroupOneDTO relationshipGroupOneDTO:shipGroupDTOOneList){
                        shipGroupDTOTwoList=getRelationshipGroupList(relationshipGroupOneDTO,relationshipGroupListQuery);
                        relationshipGroupOneDTO.setGroupList(shipGroupDTOTwoList);
                        friendRequestDTOList=getFriendRequestDTOList(relationshipGroupOneDTO, relationshipGroupListQuery);
                        friendRequestDTOList = friendRequestManager.getFriendRequest(friendRequestDTOList, 1);
                        friendRequestDTOList=friendRequestManager.getFriendRequest(friendRequestDTOList, 2);
                        friendRequestDTOList=friendRequestManager.getFriendRequest(friendRequestDTOList, 3);
                        //设置用户关注、未关注、互相关注状态
                        setUserFollow(friendRequestDTOList, relationshipGroupListQuery.getAccount(), followAccountList);
                        relationshipGroupOneDTO.setFriendList(friendRequestDTOList);
                        for(RelationshipGroupTwoDTO relationshipGroupTwoDTO:shipGroupDTOTwoList){
                            shipGroupDTOThreeList=getRelationshipGroupList(relationshipGroupTwoDTO,relationshipGroupListQuery);
                            relationshipGroupTwoDTO.setGroupList(shipGroupDTOThreeList);
                            friendRequestDTOList=getFriendRequestDTOList(relationshipGroupTwoDTO, relationshipGroupListQuery);
                            friendRequestDTOList = friendRequestManager.getFriendRequest(friendRequestDTOList, 1);
                            friendRequestDTOList=friendRequestManager.getFriendRequest(friendRequestDTOList, 2);
                            friendRequestDTOList=friendRequestManager.getFriendRequest(friendRequestDTOList, 3);
                            //设置用户关注、未关注、互相关注状态
                            setUserFollow(friendRequestDTOList, relationshipGroupListQuery.getAccount(), followAccountList);
                            relationshipGroupTwoDTO.setFriendList(friendRequestDTOList);
                        }
                    }
                }


            }
            friendRequestDTOList=getFriendRequestDTOList(groupParentTypeDTO, relationshipGroupListQuery);
            friendRequestDTOList=friendRequestManager.getFriendRequest(friendRequestDTOList, 1);
            friendRequestDTOList=friendRequestManager.getFriendRequest(friendRequestDTOList, 2);
            friendRequestDTOList=friendRequestManager.getFriendRequest(friendRequestDTOList, 3);
            //设置用户关注、未关注、互相关注状态
            setUserFollow(friendRequestDTOList, relationshipGroupListQuery.getAccount(), followAccountList);
            groupParentTypeDTO.setFriendList(friendRequestDTOList);
            groupParentTypeDTO.setGroupList(shipGroupDTOList);
            groupParentTypeDTOs.add(groupParentTypeDTO);

        }
        relationshipDTO.setGroupParentList(groupParentTypeDTOs);
        return relationshipDTO;
    }


    private List<GetFriendRequestDTO>  getFriendRequestDTOList(Object clazz,RelationshipGroupListQuery relationshipGroupListQuery){
        List<GetFriendRequestDTO> getFriendRequestDTOs=Lists.newArrayList();
        List<RelationshipFriend> relationshipFriendList;
        Example exampleThree = new Example(RelationshipFriend.class);
        //exampleThree.selectProperties("fromAccount").setDistinct(true);
        exampleThree.setDistinct(true);
        if (clazz instanceof GroupParentTypeDTO) {
            List<Integer> groupIdlist=relationshipGroupMapper.findIds(relationshipGroupListQuery.getAccount(), ((GroupParentTypeDTO) clazz).getGruopId());
            groupIdlist.add(((GroupParentTypeDTO) clazz).getGruopId());
            exampleThree.createCriteria().andEqualTo("account", relationshipGroupListQuery.getAccount()).andEqualTo("agreeType", 2).andIn("groupId", groupIdlist);
            relationshipFriendList = relationshipFriendMapper.selectByExample(exampleThree);
            getRelationshipFriendList(relationshipFriendList);
            getFriendRequestDTOs = BeanByRefMapper.copyByRefListMapper(getRelationshipFriendList(relationshipFriendList), GetFriendRequestDTO.class);
        }
        if(clazz instanceof RelationshipGroupDTO){
            exampleThree.createCriteria().andEqualTo("account", relationshipGroupListQuery.getAccount()).andEqualTo("groupId", ((RelationshipGroupDTO) clazz).getGruopId()).andEqualTo("agreeType", 2);
            relationshipFriendList= relationshipFriendMapper.selectByExample(exampleThree);
            getFriendRequestDTOs = BeanByRefMapper.copyByRefListMapper(relationshipFriendList, GetFriendRequestDTO.class);
        }
        if(clazz instanceof RelationshipGroupOneDTO){
            exampleThree.createCriteria().andEqualTo("account", relationshipGroupListQuery.getAccount()).andEqualTo("groupId", ((RelationshipGroupOneDTO) clazz).getGruopId()).andEqualTo("agreeType", 2);
            relationshipFriendList= relationshipFriendMapper.selectByExample(exampleThree);
            getFriendRequestDTOs = BeanByRefMapper.copyByRefListMapper(relationshipFriendList, GetFriendRequestDTO.class);
        }
        if(clazz instanceof RelationshipGroupTwoDTO){
            exampleThree.createCriteria().andEqualTo("account", relationshipGroupListQuery.getAccount()).andEqualTo("groupId", ((RelationshipGroupTwoDTO) clazz).getGruopId()).andEqualTo("agreeType", 2);
            relationshipFriendList= relationshipFriendMapper.selectByExample(exampleThree);
            getFriendRequestDTOs = BeanByRefMapper.copyByRefListMapper(relationshipFriendList, GetFriendRequestDTO.class);
        }
      return getFriendRequestDTOs;

    }


    private  List getRelationshipGroupList(Object entity,RelationshipGroupListQuery relationshipGroupListQuery) {
        List list=null;
         Example exampleFore = new Example(RelationshipGroup.class);
        List<RelationshipGroup> groupList;
        if (entity instanceof RelationshipGroupDTO) {

            exampleFore.createCriteria().andEqualTo("account", relationshipGroupListQuery.getAccount()).andEqualTo("parentId", ((RelationshipGroupDTO) entity).getGruopId());
                groupList = this.findByExample(exampleFore);
               list = BeanByRefMapper.copyByRefListMapper(groupList, RelationshipGroupOneDTO.class);
            }

        if (entity instanceof RelationshipGroupOneDTO) {
            exampleFore.createCriteria().andEqualTo("account", relationshipGroupListQuery.getAccount()).andEqualTo("parentId", ((RelationshipGroupOneDTO) entity).getGruopId());
            groupList = this.findByExample(exampleFore);
            list = BeanByRefMapper.copyByRefListMapper(groupList, RelationshipGroupTwoDTO.class);
        }
        if (entity instanceof RelationshipGroupTwoDTO) {
            exampleFore.createCriteria().andEqualTo("account", relationshipGroupListQuery.getAccount()).andEqualTo("parentId", ((RelationshipGroupTwoDTO) entity).getGruopId());
            groupList = this.findByExample(exampleFore);
            list = BeanByRefMapper.copyByRefListMapper(groupList, RelationshipGroupThreeDTO.class);
        }
        return list;
    }

    private Integer getSaveGroup(RelationshipGroupQuery relationshipGroupQuery){
        Integer result;
        Boolean value=existsName(relationshipGroupQuery.getAccount(), relationshipGroupQuery.getGruopName(), relationshipGroupQuery.getParentId());
        if(value){
            throw new ParamValidException("gruopName：分组名称以存在");
        }
        Integer groupType= countGroupTypeMax(relationshipGroupQuery.getAccount(),relationshipGroupQuery.getParentId());
        RelationshipGroup relationshipGroup = BeanByRefMapper.copyByRefMapper(relationshipGroupQuery, RelationshipGroup.class);
        Integer groupGradeValue= countGroupGradeMax(relationshipGroupQuery.getAccount(),relationshipGroupQuery.getParentId());
        if(relationshipGroupQuery.getParentId().equals(0)){
            relationshipGroup.setCreateTime(new Date());
            relationshipGroup.setGroupType(0);
            relationshipGroup.setGroupGrade(0);
        }else{
            if(groupType.equals(0)){
                relationshipGroup.setCreateTime(new Date());
                relationshipGroup.setGroupType(groupType + 6);
                relationshipGroup.setGroupGrade(groupGradeValue + 1);
            }else{
                relationshipGroup.setCreateTime(new Date());
                relationshipGroup.setGroupType(groupType + 1);
                relationshipGroup.setGroupGrade(groupGradeValue+1);
            }
        }
        result=this.save(relationshipGroup);
        return Integers.isEmpty(result)?0: result;
    }

    private List<RelationshipFriend> getRelationshipFriendList(List<RelationshipFriend> list){
        List<RelationshipFriend> newList =Lists.newArrayList();for (RelationshipFriend friend : list) {
            if (Collections.frequency(newList, friend)<1) {
                newList.add(friend);
            }
        }

        return newList;
    }

}