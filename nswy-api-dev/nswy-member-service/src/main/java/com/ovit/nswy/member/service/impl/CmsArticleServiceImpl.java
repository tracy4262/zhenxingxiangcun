package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.CmsArticleMapper;
import com.ovit.nswy.member.model.CmsArticle;
import com.ovit.nswy.member.service.CmsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsArticleServiceImpl implements CmsArticleService{
	@Autowired
	private CmsArticleMapper cmsArticleMapper;

	@Override
	public List<CmsArticle> findArticle()  {
		CmsArticle cms = new CmsArticle();
		return cmsArticleMapper.findArticle(cms);
	}
}
