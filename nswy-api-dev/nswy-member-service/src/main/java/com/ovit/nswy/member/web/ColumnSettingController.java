package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.date.DateFormat;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.member.common.enums.AuthenticationStep;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.model.request.ColumnSettingsUtils;
import com.ovit.nswy.member.model.request.UserSettingsRequest;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.util.JsonToStringUtils;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/columnSettings")
public class ColumnSettingController {
	/**
	 *
	 */
	private Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private ColumnSettingService columnSettingService;
	@Autowired
	private InformationService informationService;
	@Autowired
	private KnowledgeService knowService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private MyLabelService mylabel;
    @Autowired
    private VideoImgService videoImgService;

	@Autowired
	private LoginUserService loginUserService;

	@Autowired
    private SpecieService specieService;

    /**
     * 新版认证-栏目设置
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveColumnSettings", method = RequestMethod.POST)
    public Result saveColumnSettings(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String jsonParam  = JsonToStringUtils.Obj2JsonString(params.get("data"));
        logger.debug("jsonParam = " + jsonParam);
        Result result = new Result();
        try{
            String username = loginUser.getLoginAccount();
            ColumnSettings model = new ColumnSettings();
            model.setUserAccount(username);
            model.setColumnValue(jsonParam);
            //查询当前用户是否已存在栏目设置记录
            List<ColumnSettings> isExist = columnSettingService.findByAccount(username);
            if(isExist != null && !isExist.isEmpty()){
                //存在记录直接删除，后面直接做插入操作。
                columnSettingService.deleteByAccount(username);
            }
            columnSettingService.insertByNewVersion(model);
            //--栏目操作逻辑结束，更新当前认证步骤逻辑开始.
            LoginUser loginInfo = loginUserService.findByLoginName(loginUser.getLoginAccount());
            int step = Integer.parseInt(loginInfo.getIsIdentityVerification());
            if (step < 2) {
                loginUserService.updateIdentityFlag(username, AuthenticationStep.STEP_COLUMNSETTINGS);
            }

        }catch(Exception e){
            e.printStackTrace();
            result.setCode(500);
        }

        return result;
    }


    @RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Result insert(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
		List<ColumnSettings> list = new ArrayList<>();
		String status=(String) params.get("status");
        Result result = new Result();
		try{
            String username = loginUser.getLoginAccount();
            List<ColumnSettings> old=columnSettingService.findByAccount(username);
            List<Map<Object, Object>> object = (List<Map<Object, Object>>) params.get("base");
            if (!object.isEmpty()) {
                for (int i = 0; i < object.size(); i++) {
                    Map<Object, Object> map = object.get(i);
                    ColumnSettings columnSettings = new ColumnSettings();
                    columnSettings.setColumnName((String) map.get("name"));
                    columnSettings.setSecurity(Integer.parseInt(((map.get("authority")).toString())));
                    columnSettings.setUserAccount(username);
                    columnSettings.setLabel("全部");
                    columnSettings.setStatus("true".equals(String.valueOf(map.get("status")))?0:1);
                    list.add(columnSettings);
                }
                if(!old.isEmpty()){
                    columnSettingService.updateByColumn(list);
                }else{
                    columnSettingService.insert(list);
                }

            }

            String step = params.get("step").toString();
            if(StringUtils.hasText(step)){
                if(!"1".equals(step)){
                    String str =step.substring(12,16);
                    if(!str.equals("prog")){
                        loginUserService.updateIdentityFlag(username, step);
                    }
                }
            }
        }catch(Exception e){
		    e.printStackTrace();
		    result.setCode(500);
        }

		return result;
	}




    // 保存用户的栏目设置
    @RequestMapping(value = "savecolumn", method = RequestMethod.POST)
    public Object savecolumn(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        try{
            String account = (String)params.get("account");
            String label = (String) params.get("label");
            ColumnSettings column = new ColumnSettings();
            column.setUserAccount(account);
            ColumnSettings isExits = columnSettingService.findColumns(column);
            if(isExits != null){
                isExits.setLabel(label);
                columnSettingService.updateLabel(isExits);
            }else{
                column.setLabel(label);
                columnSettingService.insertByNewVersion(column);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/findColumnSetting", method = RequestMethod.GET)
	public Result findColumnSetting(HttpServletRequest request) {
		Result result = new Result();
		String account = request.getParameter("uid");
		if(!StringUtils.hasText(account)){
			LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
			account = loginUser.getLoginAccount();
		}
		ColumnSettings column = new ColumnSettings();
		column.setUserAccount(account);
		List<ColumnSettings> list = columnSettingService.findColumnSetting(column);
		if(list != null && !list.isEmpty()){
		    //历史数据问题处理逻辑
            if(null != list.get(0)){
                JSONArray jsonMap =  JSONArray.parseArray(list.get(0).getColumnValue().toString());
                result.setData(jsonMap);
            }else{
                result.setData(ColumnSettingsUtils.CreateDefaultColumn());
            }
        }else{
            result.setData(ColumnSettingsUtils.CreateDefaultColumn());
        }
		return result;
	}

	@RequestMapping(value = "/findColumnLabel", method = RequestMethod.GET)
	public Result findColumnLabel(HttpServletRequest request) {
		String columnName = request.getParameter("columnName");
        String account = request.getParameter("account");
		/*HttpSession session = request.getSession();
        String account = request.getParameter("uid");
        if(!StringUtils.hasText(account)){
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            account = loginUser.getLoginAccount();
        }*/
		ColumnSettings column = new ColumnSettings();
		if(StringUtils.hasText(columnName)){
            column.setColumnName(columnName);
        }
		column.setUserAccount(account);
        Object userSettings = columnSettingService.findColumnSet(column);
		Result result = new Result();
		result.setData(userSettings);
		return result;
	}

    @RequestMapping(value = "/findColumnByUserSettings", method = RequestMethod.GET)
    public Result findColumnByUserSettings(@RequestParam("account") String account) {
        //原有逻辑，不改动。
        ColumnSettings column = new ColumnSettings();
        column.setUserAccount(account);
        List<UserSettingsRequest> userSettings = columnSettingService.findColumnSet(column);
        Result result = new Result();
        result.setData(userSettings);
        return result;
    }

    //我的动态的显示
	@RequestMapping(value = "/findColumnList", method = RequestMethod.GET)
    public Response findColumnList(HttpServletRequest request) {
        Response result = new Response();
		String label = request.getParameter("label");
		String columnId = request.getParameter("columnId");
		String page = request.getParameter("currentPage").toString();
        String account = request.getParameter("account");
        String docType="";
        if(StringUtil.isNotBlank(request.getParameter("docType"))){
            docType= request.getParameter("docType");
        }

        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));

		int pageNum = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
        Map<String,Object> params = new HashMap<>();
        params.put("pageNum",pageNum);
        params.put("pageSize",pageSize);
        //动态
        List<InformationDetail> dynamicList = new ArrayList<>();
        //政策
        List<PolicyDetail> policyList = new ArrayList<>();
        //知识
        List<KnowledgeDetail> knowledgeList = new ArrayList<>();

		if (columnId.equals("动态")) {
			InformationDetail info = new InformationDetail();
			info.setLabel(label);
            dynamicList = listComments(label, account,docType);
            result = Response.successResult(PageList.pageInfoList(params,dynamicList), DateFormat.DATE_FORMAT_SEC);

		} else if (columnId.equals("政策")) {
			PolicyDetail policyDetail = new PolicyDetail();
			policyDetail.setLabel(label);
            policyList = policyComments(label, account,docType);
            result = Response.successResult(PageList.pageInfoList(params,policyList), DateFormat.DATE_FORMAT_SEC);

		} else if (columnId.equals("知识")) {
			KnowledgeDetail knowledgeDetail = new KnowledgeDetail();
			knowledgeDetail.setLabel(label);
            knowledgeList = knowComments( label, account,docType);
            result = Response.successResult(PageList.pageInfoList(params,knowledgeList), DateFormat.DATE_FORMAT_SEC);

		} else if (columnId.equals("全部")) {
            InformationDetail info = new InformationDetail();
            info.setLabel(label);
            dynamicList = listComments(label, account,docType);

            PolicyDetail policyDetail = new PolicyDetail();
            policyDetail.setLabel(label);
            policyList = policyComments( label, account,docType);

            KnowledgeDetail knowledgeDetail = new KnowledgeDetail();
            knowledgeDetail.setLabel(label);
            knowledgeList = knowComments(label, account,docType);

		    List<Object> allList = new ArrayList<>();
            allList.addAll(dynamicList);
            allList.addAll(policyList);
            allList.addAll(knowledgeList);

            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Collections.sort(allList, new Comparator<Object>(){
                @Override
                public int compare(Object arg0, Object arg1) {
                    InformationDetail info0 = new InformationDetail();
                    InformationDetail info1 = new InformationDetail();

                    try {
                        BeanUtils.copyProperties(info0, arg0);
                        BeanUtils.copyProperties(info1, arg1);
                        Date dt1 = info0.getCreateTime();
                        Date dt2 = info1.getCreateTime();
                        if (dt1.getTime() < dt2.getTime()) {
                            return 1;
                        } else if (dt1.getTime() > dt2.getTime()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    } catch (Exception e) {
                        logger.error("日期转换异常", e);
                        e.printStackTrace();
                    }
                    return 0;
                }
            });



            //PageList.pageObjctList(params,allList);



            result = Response.successResult(PageList.pageInfoList(params,allList), DateFormat.DATE_FORMAT_SEC);
        }

		return result;
	}

    /**
     * 关注动态的默认显示
     * @param request
     * @return
     */
    @RequestMapping(value = "/findColumnListDany", method = RequestMethod.POST)
    public Response findColumnListDany(@RequestBody Map<String,Object> params) {
        Response result=new Response();
        try {
            String columnId = MapUtils.getString(params,"columnId");
            String pageNum = MapUtils.getString(params,"pageNum");
            String pageSize = MapUtils.getString(params,"pageSize");
            String account = MapUtils.getString(params,"account");
            String key = MapUtils.getString(params,"key");
            int num = StringUtils.isEmpty(pageNum) ? 1 : Integer.parseInt(pageNum);
            int size = StringUtils.isEmpty(pageSize) ? 10 : Integer.parseInt(pageSize);
			FavoriteSpecies favoriteSpecies = specieService.findSpecByAccount(account);
            String specName = null;
            if (null != favoriteSpecies && org.apache.commons.lang3.StringUtils.isNotEmpty(favoriteSpecies.getSpeciesName())) {
                specName = favoriteSpecies.getSpeciesName();
            }
			List<String> list = new ArrayList<>();
            Map<String,Object> map = new HashMap<>();
            map.put("list", list);
            map.put("key", key);
			if(!StringUtils.isEmpty(specName)) {
                JSONArray jsonArray = JSON.parseArray(specName);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    list.add(obj.get("name").toString());
                }
            }
            Map<String, Object> commentMap = new HashMap<String, Object>();
			if (CollectionUtils.isNotEmpty(list)) {
				if (columnId.equals("动态")) {
					PageInfo<InformationDetail> informationDetailPageInfo = informationService.findByLabel(map, num, size);
                    if(CollectionUtils.isNotEmpty(informationDetailPageInfo.getList())) {
                        //查询总评论数
                        commentMap.put("lists", informationDetailPageInfo.getList());
                        commentMap.put("TABLE_NAME", "info_comment");
                        informationService.listCommentCounts(commentMap);
                    }
                    result = Response.successResult(informationDetailPageInfo, DateFormat.DATE_FORMAT_SEC);

				} else if (columnId.equals("政策")) {
					PageInfo<PolicyDetail> policyDetailPageInfo = policyService.findBySpeciesName(map, num, size);
                    if(CollectionUtils.isNotEmpty(policyDetailPageInfo.getList())) {
                        //查询总评论数
                        commentMap.put("lists", policyDetailPageInfo.getList());
                        commentMap.put("TABLE_NAME", "policy_comment");
                        policyService.listCommentCounts(commentMap);
                    }
                    result = Response.successResult(policyDetailPageInfo, DateFormat.DATE_FORMAT_SEC);

				} else if (columnId.equals("知识")) {
					PageInfo<KnowledgeDetail> knowledgeDetailPageInfo = knowService.findBySpeciesName(map, num, size);
                    if(CollectionUtils.isNotEmpty(knowledgeDetailPageInfo.getList())) {
                        //查询总评论数
                        commentMap.put("lists", knowledgeDetailPageInfo.getList());
                        commentMap.put("TABLE_NAME", "knowledge_comment");
                        knowService.listCommentCounts(commentMap);
                    }
                    result = Response.successResult(knowledgeDetailPageInfo, DateFormat.DATE_FORMAT_SEC);

				}
			}
        } catch (Exception e) {
            logger.error("查询异常: {}",e);
            result.setCode(400);
            result.setMsg("查询失败");
        }
        return result;
    }

    /**
     * 查询评论数据
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/findComments", method = RequestMethod.POST)
    public Result findComments(@RequestBody Map<String, Object> params, HttpServletRequest request){
        HttpSession session = request.getSession();
        Result result = new Result();
        try{
            //遍历评论
            PageInfo<Map<String,Object>>  list  =   informationService.findComment(params);
            result.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @RequestMapping(value = "/deleteMyComment", method = RequestMethod.POST)
    public Result deleteMyComment(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try{
            informationService.deleteMyComment(params);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return  result;
    }

	// 咨询展示
	private List<InformationDetail> listComments(int pageNum, String label, String account,String docType) {

        LoginUser avatar = loginUserService.findByLoginName(account);
        Map<String, Object> maplabel = new HashMap<String, Object>();
        maplabel.put("label",label);
        maplabel.put("account",account);
        maplabel.put("docType",docType);
        PageInfo<InformationDetail> page = columnSettingService.findInforMationDetailLeftInforMation(pageNum,maplabel);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("lists", page.getList());
        map.put("TABLE_NAME","info_comment");
		if(!page.getList().isEmpty()){
		    //查询总评论数
            informationService.listCommentCounts(map);

            for (InformationDetail detail : page.getList()) {
                detail.setAvatar(avatar.getAvatar());
            }
        }

		return page.getList();
	}

	// 知识展示
	private List<KnowledgeDetail> knowComments(int pageNum,String label, String account,String docType) {

		KnowledgeDetail knowledgeDetail = new KnowledgeDetail();
		knowledgeDetail.setLabel(label);
		knowledgeDetail.setAccount(account);
		knowledgeDetail.setDocType(docType);
		PageInfo<KnowledgeDetail> page = columnSettingService.findKnowledgeDetailLeftKnowledge(pageNum,knowledgeDetail);
		Map<String, Object> map = new HashMap<>();
		map.put("account", account);
		map.put("lists", page.getList());
        map.put("TABLE_NAME","knowledge_comment");
		if(!page.getList().isEmpty()){
            LoginUser avatar=loginUserService.findByLoginName(account);
            for (KnowledgeDetail detail : page.getList()) {
                detail.setAvatar(avatar.getAvatar());
            }
            //查询总评论数
            knowService.listCommentCounts(map);
            /*List<KnowledgeComment> comments = knowService.listComment(map);
            Map<Integer, List<KnowledgeComment>> mapcom = new HashMap<>();
            if(!comments.isEmpty()){
                for (KnowledgeComment info : comments) {
                    if (mapcom.containsKey(info.getInformationDetailId())) {
                        mapcom.get(info.getInformationDetailId()).add(info);
                    } else {
                        List<KnowledgeComment> listComment = new ArrayList<>();
                        listComment.add(info);
                        mapcom.put(info.getInformationDetailId(), listComment);
                    }
                }
            }
            if(!mapcom.isEmpty()){
                for (Map.Entry<Integer, List<KnowledgeComment>> entry : mapcom.entrySet()) {
                    int id = entry.getKey();
                    for (KnowledgeDetail detail : page.getList()) {
                        if (id == detail.getId().intValue()) {
                            detail.setList(mapcom.get(id));
                        }
                    }
                }
            }*/
        }
		return page.getList();
	}

	// 政策展示
	private List<PolicyDetail> policyComments(int pageNum,String label, String account,String docType) {

		PolicyDetail policyDetail = new PolicyDetail();
		policyDetail.setLabel(label);
		policyDetail.setAccount(account);
		policyDetail.setDocType(docType);
		PageInfo<PolicyDetail> page = columnSettingService.findPolicyDetailLeftPolicy(pageNum,policyDetail);
		List<PolicyDetail> list=page.getList();
		Map<String, Object> map = new HashMap<>();
		map.put("account", account);
        map.put("TABLE_NAME","policy_comment");
		if (!list.isEmpty()) {
			map.put("lists", list);
		} else {
			map.put("lists", new ArrayList<PolicyDetail>());
		}
		if(!list.isEmpty()){
            LoginUser avatar=loginUserService.findByLoginName(account);
            for (PolicyDetail detail : page.getList()) {
                detail.setAvatar(avatar.getAvatar());
            }
            //查询总评论数
            policyService.listCommentCounts(map);
            /*List<PolicyComment> comments = policyService.listComment(map);
            Map<Integer, List<PolicyComment>> mapcom = new HashMap<>();
            for (PolicyComment info : comments) {
                if (mapcom.containsKey(info.getInformationDetailId())) {
                    mapcom.get(info.getInformationDetailId()).add(info);
                } else {
                    List<PolicyComment> listComment = new ArrayList<>();
                    listComment.add(info);
                    mapcom.put(info.getInformationDetailId(), listComment);
                }
            }
            for (Map.Entry<Integer, List<PolicyComment>> entry : mapcom.entrySet()) {
                int id = entry.getKey();
                for (PolicyDetail detail : list) {
                    if (id == detail.getId().intValue()) {
                        detail.setList(mapcom.get(id));
                    }
                }
            }*/
        }

		return list;
	}


    // 咨询展示
    private List<InformationDetail> listComments(String label, String account,String docType) {

        LoginUser avatar = loginUserService.findByLoginName(account);
        Map<String, Object> maplabel = new HashMap<String, Object>();
        maplabel.put("label",label);
        maplabel.put("account",account);
        maplabel.put("docType",docType);
        List<InformationDetail> list = columnSettingService.findInforMationDetailLeftInforMations(maplabel);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("account", account);
        map.put("lists", list);
        map.put("TABLE_NAME","info_comment");
        if(!list.isEmpty()){
            //查询总评论数
            informationService.listCommentCounts(map);

            for (InformationDetail detail : list) {
                detail.setAvatar(avatar.getAvatar());
            }
        }

        return list;
    }

    // 知识展示
    private List<KnowledgeDetail> knowComments(String label, String account,String docType) {

        KnowledgeDetail knowledgeDetail = new KnowledgeDetail();
        knowledgeDetail.setLabel(label);
        knowledgeDetail.setAccount(account);
        knowledgeDetail.setDocType(docType);
        List<KnowledgeDetail> list = columnSettingService.findKnowledgeDetailLeftKnowledges(knowledgeDetail);
        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("lists", list);
        map.put("TABLE_NAME","knowledge_comment");
        if(!list.isEmpty()){
            LoginUser avatar=loginUserService.findByLoginName(account);
            for (KnowledgeDetail detail : list) {
                detail.setAvatar(avatar.getAvatar());
            }
            //查询总评论数
            knowService.listCommentCounts(map);

        }
        return list;
    }

    // 政策展示
    private List<PolicyDetail> policyComments(String label, String account,String docType) {

        PolicyDetail policyDetail = new PolicyDetail();
        policyDetail.setLabel(label);
        policyDetail.setAccount(account);
        policyDetail.setDocType(docType);
        List<PolicyDetail> list = columnSettingService.findPolicyDetailLeftPolicys(policyDetail);

        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("TABLE_NAME","policy_comment");
        if (!list.isEmpty()) {
            map.put("lists", list);
        } else {
            map.put("lists", new ArrayList<PolicyDetail>());
        }
        if(!list.isEmpty()){
            LoginUser avatar=loginUserService.findByLoginName(account);
            for (PolicyDetail detail : list) {
                detail.setAvatar(avatar.getAvatar());
            }
            //查询总评论数
            policyService.listCommentCounts(map);

        }

        return list;
    }


	// 显示用户的栏目设置
	@RequestMapping(value = "listlabel", method = RequestMethod.GET)
	public Object listlabel(HttpServletRequest request) {
		String account = request.getParameter("uid");
		if(!StringUtils.hasText(account)){
			LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
			account = loginUser.getLoginAccount();
		}
		List<String> list = mylabel.selectByAccount(account);
		Result result = new Result();
		result.setData(list);
		return result;
	}

    /**
     * 会员中心---添加、编辑联系人
     * @param params
     * @return
     */
    @RequestMapping(value = "saveContact", method = RequestMethod.POST)
    public Result saveContact(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String safetyInfo = JSON.toJSONString(params.get("safeFormData"));
            params.put("safeFormData", safetyInfo);
            columnSettingService.saveContact(params);
        } catch (Exception e) {
            logger.error("添加联系人失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("添加联系人失败");
        }
        return result;
    }

    /**
     * 会员中心---联系人查询
     * @param params
     */
    @RequestMapping(value = "/findContact", method = RequestMethod.POST)
    public Result findContact(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            //联系人查询
            List<Map<String,Object>> contactList = columnSettingService.findContact(params);
            result.setData(contactList);
            result.setMsg("联系人查询成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("联系人查询失败");
        }
        logger.info("联系人查询信息为： {}", result.getData());
        return result;
    }

    /**
     * 会员中心---删除联系人
     * @param params
     */
    @RequestMapping(value = "/deleteContact", method = RequestMethod.POST)
    public Result deleteContact(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            //联系人删除
            columnSettingService.deleteContact(params);
            result.setMsg("联系人删除成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("联系人删除失败");
        }
        return result;
    }

	private static List<String> listToStr(List<String> list, List<String> list1) {
		Set<String> set = new HashSet<String>();
		set.addAll(list);
		Set<String> set1 = new HashSet<String>();
		set1.addAll(list1);
		set.removeAll(set1);
		List<String> result = new ArrayList<String>();
		result.addAll(set);
		return result;
	}
}
