package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ExpertApply;
import com.ovit.manager.modules.nswy.entity.NswyCorpInfo;

import java.util.Map;

/**
 * Created by ${zhanlei} on 2017-10-18.
 */
@MyBatisDao
public interface ExpertApplyDao  extends CrudDao<ExpertApply> {

	int insert(ExpertApply record);

	ExpertApply selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(ExpertApply record);

	int deleteByPrimaryKey(Integer id);

	void saveWikiLibraryInfo(Map<String,Object> params);

	void updateStatus(ExpertApply nswyExpertApply);

	void deleteWikiLibraryInfo(Map<String,Object> params);

	String queryDisplayNameByAccount(String fcreatorid);

	void saveVarietyLibraryInfo(Map<String,Object> params);

	void updateVarietyLibraryInfo(Map<String,Object> params);

	void deleteVarietyLibraryInfo(Map<String,Object> params);

	void updateWikiLibraryInfo(Map<String,Object> params);

	void saveDiseaseLibraryInfo(Map<String,Object> params);

	void deleteDiseaseLibraryInfo(Map<String,Object> params);

	void updateDiseaseLibraryInfo(Map<String,Object> params);

	void updatePestLibraryInfo(Map<String,Object> params);




	void savePestLibraryInfo(Map<String,Object> params);

	void deletePestLibraryInfo(Map<String,Object> params);

	Map<String,Object> findWikiConllectionList(Map<String,Object> params);

	Map<String,Object> findVarietyList(Map<String,Object> params);

	Map<String,Object> findDiseaseList(Map<String,Object> params);

	Map<String,Object> findPestList(Map<String,Object> params);





}