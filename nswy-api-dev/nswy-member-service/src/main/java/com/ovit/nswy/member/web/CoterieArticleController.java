package com.ovit.nswy.member.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ovit.nswy.member.model.Coterie;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.CoterieArticle;
import com.ovit.nswy.member.service.CoterieArticleService;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.web.base.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/CoterieArticle")
public class CoterieArticleController {
	/**
	 * 
	 */
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private CoterieArticleService coterieArticleService;
	@Autowired
	private LoginUserService loginUserService;

	@RequestMapping("/")
	public Result list() {
		List<CoterieArticle> cArticles = coterieArticleService.findAll();
		Result result = new Result();
		result.setData(cArticles);
		return result;
	}

	/**
	 * 最新帖子
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/query")
	public Result query() {
		logger.info("最新帖子。。。。。。。。。。。。。");
		List<CoterieArticle> cArticles = coterieArticleService.findDesc();
		Result result = new Result();
		result.setData(cArticles);
		return result;
	}

	/**
	 * 本周热门
	 * 
	 * @return
	 */
	@RequestMapping("/weekHot")
	public Result weekHot() {
		List<CoterieArticle> cArticles = coterieArticleService.findHot();
		Result result = new Result();
		result.setData(cArticles);
		return result;
	}

	/**
	 * 今日之星
	 * 
	 * @return
	 */
	@RequestMapping("/dayStar")
	public Result dayStar() {
		List<CoterieArticle> cArticles = coterieArticleService.findDay();
		Result result = new Result();
		result.setData(cArticles);
		return result;
	}

	/**
	 * 圈体
	 * 
	 * @return
	 */
	@RequestMapping("/circle/")
	public Result circle() {
		List<Map<String, Object>> list = coterieArticleService.list();
		Map<String, List<Map<String, Object>>> circle = new HashMap<>();
		for (Map<String, Object> l : list) {
			String value = (String) l.get("category");
			if (!circle.containsKey(value)) {
				List<Map<String, Object>> tmp = new ArrayList<>();
				tmp.add(l);
				circle.put(value, tmp);
			} else {
				List<Map<String, Object>> tmp = circle.get(value);
				tmp.add(l);
			}
		}
		Result result = new Result();
		result.setData(circle);
		return result;
	}

	/**
	 * 排行榜
	 * 
	 * @return
	 */
	@RequestMapping("/rank/")
	public Result rank() {
		List<Map<String, Object>> list = coterieArticleService.rank();
		Result result = new Result();
		result.setData(list);
		return result;
	}

	/**
	 * 通过id查找圈子详情
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping("/circleDetail/{id}/{page}/{pageSize}")
	public Result showFriends(@PathVariable String id, @PathVariable String page, @PathVariable String pageSize) {
		int currentPage = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
        int size = StringUtils.isEmpty(pageSize) ? 5 : Integer.parseInt(pageSize);
		int coterieId = StringUtils.isEmpty(id) ? 0 : Integer.parseInt(id);
		PageInfo<CoterieArticle> list = coterieArticleService.findById(coterieId, currentPage, size);
		Result result = new Result();
		result.setData(list);
		return result;
	}

	/**
	 * 根据圈子主题id查询副版主信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/moderator/{id}")
	public Result findModeratorById(@PathVariable String id) {
		int coterieId = StringUtils.isEmpty(id) ? 0 : Integer.parseInt(id);
		List<Map<String, Object>> list = loginUserService.findModeratorById(coterieId);
		Result result = new Result();
		result.setData(list);
		return result;
	}

    @RequestMapping("/search/{page}")
    public Result searchCoterie(HttpServletRequest request, @PathVariable String page, @RequestParam(defaultValue ="null",required = false) String name) {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        Result result = new Result();
        Map<String,String> map=new HashMap<>();
        try {
            String username = loginUser.getLoginAccount();
            map.put("coterieName",name.trim());
            map.put("account",username);
            int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
            PageInfo<Coterie> list = coterieArticleService.searchCoterie(map,currentPage, 5);
            result.setData(list);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
