package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.ReportService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
	/**
	 * 
	 */
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private ReportService reportService;

	@RequestMapping(value = "/saveReport", method = RequestMethod.POST)
	public Result saveReport(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
		JuBao report = new JuBao();
		if (null != loginUser) {
			report.setCreator(loginUser.getLoginAccount());
		}
		report.setType(params.get("type").toString());
		report.setLink(params.get("link").toString());
		report.setDescription(params.get("description").toString());
		report.setImgUrl(params.get("reportPicList").toString());
		Result result = new Result();
		try {
			reportService.insert(report);
			result.setCode(ResultCode.SUCCESS);
			result.setData(1);
		} catch (Exception e) {
			logger.error(e);
			result.setCode(ResultCode.FAIL);
			result.setMsg(e.getMessage());
		}
		return result;
	}

	/**
	 * 该用户是否已经举报了
	 * @return
	 */
	@RequestMapping(value = "/reportIsExist", method = RequestMethod.POST)
	public Result reportIsExist(@RequestBody Map<String,Object> map) {
		Result result = new Result();
		logger.info("文章 link--->{},accunt-->{}", map.get("link"), map.get("account"));
		try {
			JuBao report = new JuBao();
			report.setCreator(map.get("account").toString());
			report.setLink(map.get("link").toString());
			JuBao temp = reportService.findReportByLinkAndAccount(report);
			logger.info("文章 report--->{}", temp);
			if (null != temp) {
				// 如果查询出来有数据说明有举报
				result.setData(1);
			} else {
				//查询没有说明没有举报
				result.setData(0);
			}
			result.setCode(ResultCode.SUCCESS);
		} catch (Exception e) {
			logger.error(e);
			result.setCode(ResultCode.FAIL);
			result.setMsg(e.getMessage());
			return result;
		}
		return result;
	}
	/**
	 * 查询我的无忧TITLE
	 * 
	 * @param
	 * @return @
	 */
	@RequestMapping(value = "/findMyTitle", method = RequestMethod.GET)
	public Result findMyTitle(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
		if (null == loginUser) {
			return null;
		}
		MyNoTitle myNoTitle = new MyNoTitle();
		myNoTitle.setEditer(loginUser.getLoginAccount());
		MyFriend myFriend = new MyFriend();
		myFriend.setAccount(loginUser.getLoginAccount());
		MyNoTitle list = reportService.findMyTitle(myNoTitle);
		Integer counts = reportService.findMyFriend(myFriend);
		map.put("list", list);
		map.put("counts", counts);
		Result result = new Result();
		result.setData(map);
		return result;
	}

	/**
	 * 查询收藏
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findCollect", method = RequestMethod.POST)
	public Result findCollect(@RequestBody Map<String, Object> params) {
		Result result = new Result();
		try {
			PageInfo<Map<String,Object>> list = reportService.findCollects(params);
			Map<String,Object> map = new HashMap<>();
			map.put("list", list);
			map.put("flag", "1");
			result.setData(map);
			result.setCode(ResultCode.SUCCESS);
		} catch (Exception e) {
			logger.error(e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMsg(e.getMessage());
			return result;
		}
		return result;
	}

	/**
	 * 收藏
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveCollect", method = RequestMethod.POST)
	public Result saveCollect(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		//LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
//		if (loginUser == null) {
//			return null;
//		}
		MyFavorite report = new MyFavorite();
		if (params.get("account") != null){
			report.setAccount(String.valueOf(params.get("account")));
		}
		//report.setAccount(loginUser.getLoginAccount());
		report.setType(params.get("type").toString());
		//String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//		url.substring(0, url.length() - 1);
//		url += params.get("link").toString();
		report.setPath(params.get("link").toString());
		report.setTitle(params.get("title").toString());
		report.setCollectId(Integer.parseInt(String.valueOf(params.get("collectId"))));
		Result result = new Result();
		try {
			String string = reportService.saveCollect(report);
			if (string.equals("success")) {
				result.setData(1);
			} else {
				result.setData(2);
			}
		} catch (Exception e) {
			logger.error(e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMsg(e.getMessage());
		}
		return result;
	}

	/**
	 * 收藏
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveFollow", method = RequestMethod.POST)
	public Result saveFollow(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
		if (loginUser == null) {
			return null;
		}
		MyFollow report = new MyFollow();
		report.setAccount(loginUser.getLoginAccount());
		report.setType(params.get("type").toString());
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		url.substring(0, url.length() - 1);
		url += params.get("link").toString();
		report.setPath(url);
		report.setLabel(params.get("label").toString());
		Result result = new Result();
		try {
			String string = reportService.saveFollow(report);
			if (string.equals("success")) {
				result.setData(1);
			} else {
				result.setData(2);
			}
		} catch (Exception e) {
			logger.error(e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMsg(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/delFollow",method = RequestMethod.POST)
	public Result delFollow(@RequestBody Map<String,Object> params){
		Result result = new Result();
		reportService.delFollow(params);
		return result;
	}

	@RequestMapping(value = "/isExist", method = RequestMethod.POST)
	public Result isExist(@RequestBody Map<String, Object> params, HttpServletRequest request) {

		Result result = new Result();
		try {
			MyFavorite report = new MyFavorite();
			report.setPath(params.get("link").toString());
			report.setAccount(String.valueOf(params.get("account")));
			boolean exist = reportService.isExist(report);
			if(exist) result.setData(1);
			else result.setData(0);
			result.setCode(ResultCode.SUCCESS);
		} catch (Exception e) {
			logger.error(e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMsg(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/updateCollect", method = RequestMethod.POST)
	public Result isExist(@RequestBody Map<String, Object> params) {

		Result result = new Result();
		try {
			MyFavorite report = new MyFavorite();
			report.setId(Integer.parseInt(params.get("id").toString()));
			report.setCollectId(Integer.parseInt(params.get("collectId").toString()));
			 reportService.updateCollect(report);
			result.setCode(ResultCode.SUCCESS);
		} catch (Exception e) {
			logger.error(e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMsg(e.getMessage());
		}
		return result;
	}

}
