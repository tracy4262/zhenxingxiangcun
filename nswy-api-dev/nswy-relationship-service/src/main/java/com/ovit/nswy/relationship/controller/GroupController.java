package com.ovit.nswy.relationship.controller;

import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.relationship.model.Relationship.FriendList;
import com.ovit.nswy.relationship.model.Relationship.RelationshipGroup;
import com.ovit.nswy.relationship.model.Relationship.RelationshipGroupResponse;
import com.ovit.nswy.relationship.model.request.RelationshipGroupListQuery;
import com.ovit.nswy.relationship.service.GroupService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * 关系圈分组
 *
 * @author haoWen
 * @create 2018-03-05 14:20
 **/
@RestController
@RequestMapping("/new/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    private Logger logger = LogManager.getLogger(this.getClass());

    @RequestMapping(value = "/relationshipGroupList")
    public Response relationshipGroupList(@RequestBody RelationshipGroupListQuery relationshipGroupListQuery){
        logger.info("查询关系圈分组数组，参数：{}",relationshipGroupListQuery);
        //查询一级分组数据
        relationshipGroupListQuery.setParentId(0);
        List<RelationshipGroup> parentGroupList = groupService.queryGroupList(relationshipGroupListQuery);
        //一级分组和好友的集合
        List<RelationshipGroupResponse> firstGroupList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(parentGroupList)) {
            //遍历查询所有一级分组
            for (RelationshipGroup firstGroup : parentGroupList) {
                RelationshipGroupResponse firstGroupResponse = new RelationshipGroupResponse();
                String firstGroupId = firstGroup.getGroupId();
                firstGroupResponse.setGroupId(firstGroupId);
                firstGroupResponse.setParentId(firstGroup.getParentId());
                firstGroupResponse.setGroupName(firstGroup.getGroupName());
                firstGroupResponse.setGroupType(firstGroup.getGroupType());
                firstGroupResponse.setDeleteStatus(firstGroup.getDeleteStatus());
                //通过ID查询好友
                List<FriendList> firstFriendList = groupService.queryFriendList(firstGroup.getGroupId());
                //查询该ID的所有子集
                relationshipGroupListQuery.setParentId(Integer.parseInt(firstGroupId));
                List<RelationshipGroup> firstChildrenList = groupService.queryGroupList(relationshipGroupListQuery);
                //二级分组和好友的集合
                List<RelationshipGroupResponse> secondGroupList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(firstChildrenList)) {
                    for (RelationshipGroup secondGroup : firstChildrenList) {
                        RelationshipGroupResponse secondGroupResponse = new RelationshipGroupResponse();
                        String secondGroupId = secondGroup.getGroupId();
                        secondGroupResponse.setGroupId(secondGroupId);
                        secondGroupResponse.setParentId(secondGroup.getParentId());
                        secondGroupResponse.setGroupName(secondGroup.getGroupName());
                        secondGroupResponse.setGroupType(secondGroup.getGroupType());
                        secondGroupResponse.setDeleteStatus(secondGroup.getDeleteStatus());
                        relationshipGroupListQuery.setParentId(Integer.parseInt(secondGroupId));
                        List<FriendList> secondFriendList = groupService.queryFriendList(secondGroup.getGroupId());
                        List<RelationshipGroup> secondChildrenList = groupService.queryGroupList(relationshipGroupListQuery);
                        //三级级分组和好友的集合
                        List<RelationshipGroupResponse> thirdGroupList = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(secondChildrenList)) {
                            for (RelationshipGroup thirdGroup :secondChildrenList) {
                                RelationshipGroupResponse thirdGroupResponse = new RelationshipGroupResponse();
                                String thirdGroupId = thirdGroup.getGroupId();
                                thirdGroupResponse.setGroupId(thirdGroupId);
                                thirdGroupResponse.setParentId(thirdGroup.getParentId());
                                thirdGroupResponse.setGroupName(thirdGroup.getGroupName());
                                thirdGroupResponse.setGroupType(thirdGroup.getGroupType());
                                thirdGroupResponse.setDeleteStatus(thirdGroup.getDeleteStatus());
                                relationshipGroupListQuery.setParentId(Integer.parseInt(thirdGroupId));
                                List<FriendList> thirdFriendList = groupService.queryFriendList(thirdGroup.getGroupId());
                                List<RelationshipGroup> thirdChildrenList = groupService.queryGroupList(relationshipGroupListQuery);
                                //四级分组和好友
                                List<RelationshipGroupResponse> fourthGroupList = new ArrayList<>();
                                if (CollectionUtils.isNotEmpty(thirdChildrenList)) {
                                    for (RelationshipGroup fourthGroup : thirdChildrenList) {
                                        RelationshipGroupResponse fourthGroupResponse = new RelationshipGroupResponse();
                                        String fourthGroupId = fourthGroup.getGroupId();
                                        fourthGroupResponse.setGroupId(fourthGroupId);
                                        fourthGroupResponse.setParentId(fourthGroup.getParentId());
                                        fourthGroupResponse.setGroupName(fourthGroup.getGroupName());
                                        fourthGroupResponse.setGroupType(fourthGroup.getGroupType());
                                        fourthGroupResponse.setDeleteStatus(fourthGroup.getDeleteStatus());
                                        relationshipGroupListQuery.setParentId(Integer.parseInt(fourthGroupId));
                                        List<FriendList> fourthFriendList = groupService.queryFriendList(fourthGroup.getGroupId());
                                        List<RelationshipGroup> fourthChildrenList = groupService.queryGroupList(relationshipGroupListQuery);
                                        //五级分组和好友
                                        List<RelationshipGroupResponse> fifthGroupList = new ArrayList<>();
                                        if (CollectionUtils.isNotEmpty(fourthChildrenList)) {
                                            for (RelationshipGroup fifthGroup : fourthChildrenList) {
                                                RelationshipGroupResponse fifthGroupResponse = new RelationshipGroupResponse();
                                                fifthGroupResponse.setGroupId(fifthGroup.getGroupId());
                                                fifthGroupResponse.setParentId(fifthGroup.getParentId());
                                                fifthGroupResponse.setGroupName(fifthGroup.getGroupName());
                                                fifthGroupResponse.setGroupType(fifthGroup.getGroupType());
                                                fifthGroupResponse.setDeleteStatus(fifthGroup.getDeleteStatus());
                                                List<FriendList> fifthFriendList = groupService.queryFriendList(fifthGroup.getGroupId());
                                                fourthFriendList.addAll(fifthFriendList);
                                                fifthGroupResponse.setFriendList(fifthFriendList);
                                                fifthGroupList.add(fifthGroupResponse);
                                            }
                                        }
                                        //每一个父级分组要包含其子分组的好友
                                        thirdFriendList.addAll(fourthFriendList);
                                        fourthGroupResponse.setFriendList(fourthFriendList);
                                        //将第五级数据设置为第四级的子集
                                        fourthGroupResponse.setChildrenGroupList(fifthGroupList);
                                        fourthGroupList.add(fourthGroupResponse);
                                    }
                                }
                                secondFriendList.addAll(thirdFriendList);
                                thirdGroupResponse.setFriendList(thirdFriendList);
                                thirdGroupResponse.setChildrenGroupList(fourthGroupList);
                                thirdGroupList.add(thirdGroupResponse);
                            }
                        }
                        firstFriendList.addAll(secondFriendList);
                        secondGroupResponse.setFriendList(secondFriendList);
                        secondGroupResponse.setChildrenGroupList(thirdGroupList);
                        secondGroupList.add(secondGroupResponse);
                    }
                }
                firstGroupResponse.setFriendList(firstFriendList);
                firstGroupResponse.setChildrenGroupList(secondGroupList);
                firstGroupList.add(firstGroupResponse);
            }
        }
        return Response.newSuccessResult(firstGroupList);
    }
}
