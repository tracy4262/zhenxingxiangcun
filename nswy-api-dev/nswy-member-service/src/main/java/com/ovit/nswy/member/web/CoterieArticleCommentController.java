package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.CoterieArticleComment;
import com.ovit.nswy.member.service.CoterieArticleCommentService;
import com.ovit.nswy.member.web.base.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cAComment")
public class CoterieArticleCommentController {
	/**
	 * 
	 */
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private CoterieArticleCommentService coterieArticleCommentService;

	@RequestMapping("/")
	public Result list() {
		List<CoterieArticleComment> list = coterieArticleCommentService.findAll();
		Result result = new Result();
		result.setData(list);
		return result;
	}

	@RequestMapping("/query")
	public Result query() {
		PageInfo<CoterieArticleComment> page = coterieArticleCommentService.query(0, 10);
		Result result = new Result();
		result.setData(page);
		return result;
	}
}