package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.FavoriteInformation;

public interface FavoriteInformationService {

	int insert(FavoriteInformation record);

	FavoriteInformation selectByAccount(String account);

}
