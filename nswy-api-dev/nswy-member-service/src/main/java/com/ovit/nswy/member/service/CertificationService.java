package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.Certification;
import com.ovit.nswy.member.model.LoginUser;

import java.util.Map;

public interface CertificationService {
	int insert(Certification record);

	Certification selectByAccount(String account);

	void saveOrUpdate(Map<String, Object> params, LoginUser loginUser);

	void UpdatePrivacyInfo(Map<String, Object> params);
}
