package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.model.request.UserSettingsRequest;

import java.util.List;
import java.util.Map;

public interface ColumnSettingService {
	int insert(List<ColumnSettings> record);

	void deleteByAccount(String account);

	void deleteByColumn(ColumnSettings record);

	ColumnSettings findColumns(ColumnSettings record);

	List<ColumnSettings> findColumnSetting(ColumnSettings record);

	List<UserSettingsRequest> findColumnSet(ColumnSettings record);

    PageInfo<InformationDetail> findColumnInformation(int pageNum, Map<String, Object> map);

	PageInfo<InformationDetail> findInforMationDetailLeftInforMation(int pageNum, Map<String, Object> map);

	List<InformationDetail> findInforMationDetailLeftInforMations( Map<String, Object> map);

	PageInfo<PolicyDetail> findColumnPolicy(int pageNum,PolicyDetail record);

	PageInfo<PolicyDetail> findPolicyDetailLeftPolicy(int pageNum,PolicyDetail record);

	List<PolicyDetail> findPolicyDetailLeftPolicys(PolicyDetail record);

	PageInfo<KnowledgeDetail> findColumnKonwledge(int pageNum, KnowledgeDetail record);

	PageInfo<KnowledgeDetail> findKnowledgeDetailLeftKnowledge(int pageNum, KnowledgeDetail record);

	List<KnowledgeDetail> findKnowledgeDetailLeftKnowledges(KnowledgeDetail record);

    void updateLabel(ColumnSettings record);

    List<ColumnSettings> findByAccount(String account);

    void updateByColumn(List<ColumnSettings> record);

	void insertByNewVersion(ColumnSettings record);

	/**
	 * 会员中心：添加联系人
	 * @param params
	 */
	void saveContact(Map<String,Object> params);

	/**
	 * 会员中心---联系人查询
	 * @param params
	 */
	List<Map<String,Object>> findContact(Map<String,Object> params);

	/**
	 * 会员中心---删除联系人
	 * @param params
	 */
	void deleteContact(Map<String,Object> params);


}
