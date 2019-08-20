package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.CmsArticle;

import java.util.List;

public interface CmsArticleService {
	 List<CmsArticle> findArticle() throws Exception;
}
