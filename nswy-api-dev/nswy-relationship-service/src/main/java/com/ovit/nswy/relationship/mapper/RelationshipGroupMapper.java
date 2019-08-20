package com.ovit.nswy.relationship.mapper;

import com.ovit.nswy.frame.base.BaseMapper;
import com.ovit.nswy.relationship.model.RelationshipGroup;
import com.ovit.nswy.relationship.model.request.RelationshipGroupQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface RelationshipGroupMapper extends BaseMapper<RelationshipGroup> {


    @Select(" SELECT rg1.gruop_id FROM relationship_group as rg INNER JOIN relationship_group rg1 on rg.gruop_id=rg1.parent_id where rg.account=#{account} AND rg.gruop_id=#{groupId}")
    List<Integer> findIds(@Param("account")String account,@Param("groupId")Integer groupId);

    Map<String,Object> queryIsExist(Integer groupId);

    int updateRelationshipGroup(RelationshipGroupQuery relationshipGroupQuery);

    Map<String,Object> queryIsDefault(Integer gruopId);

    List<Map<String,Object>> nameIsExist(RelationshipGroupQuery relationshipGroupQuery);
}