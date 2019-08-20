package com.ovit.nswy.relationship.service;

import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.relationship.model.request.RelationshipGroupListQuery;
import com.ovit.nswy.relationship.model.request.RelationshipGroupQuery;
import com.ovit.nswy.relationship.model.response.RelationshipDTO;
import com.ovit.nswy.relationship.model.response.RelationshipGroupDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-22.
 */
public interface RelationshipGroupService {

    public Integer initRelationshipGroup(String account) throws ParamValidException;
    public RelationshipDTO listRelationshipGroup(RelationshipGroupListQuery relationshipGroupListQuery) throws ParamValidException;
    public Integer saveOrUpdate(RelationshipGroupQuery relationshipGroupQuery) throws ParamValidException;
    public RelationshipGroupDTO getRelationshipGroup(Integer gruopId);
    public Integer deleteRelationshipGroup(Integer gruopId) throws ParamValidException;

}
