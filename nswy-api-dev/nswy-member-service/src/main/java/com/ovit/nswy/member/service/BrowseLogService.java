package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.BrowseLog;

public interface BrowseLogService {

	 PageInfo<BrowseLog> findBrowseLog(int pageNum, int pageSize, BrowseLog info);

	 void insertBrowseLog(BrowseLog info);

}
