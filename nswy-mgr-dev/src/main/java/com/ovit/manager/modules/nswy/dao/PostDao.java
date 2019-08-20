package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.PostInfo;
import com.ovit.manager.modules.nswy.entity.UnitInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface PostDao extends CrudDao<PostInfo> {

   List<Map<String,Object>> findUnitType();

   void updatePost(PostInfo postInfo);

   void savePost(PostInfo postInfo);

   void deletePost(String id);






}
