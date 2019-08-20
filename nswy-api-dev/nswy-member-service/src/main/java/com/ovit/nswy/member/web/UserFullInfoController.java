package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userFullInfo")
public class UserFullInfoController {

    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private UserFullInfoService userFullInfoService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserContactService userContactService;
    @Autowired
    private UserWorkExperienceService userWorkExperience;
    @Autowired
    private UserFarmInfoService userFarmInfoService;
    @Autowired
    private UserPoliticalService userPoliticalService;
    @Autowired
    private UserHonorService userHonorService;
    @Autowired
    private UserReligionService userReligionService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private SpecieService specieService;
    @Autowired
    private UserWorkService userWorkService;
    @Autowired
    private UserEducationService userEducationService;

    @RequestMapping(value = "/findUserFullInfo", method = RequestMethod.GET)
    public Result findUserFullInfo(HttpServletRequest request) {
        UserFullInfo userFullInfo = new UserFullInfo();
        String account = request.getParameter("uid");
        if (!StringUtils.hasText(account)) {
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            account = loginUser.getLoginAccount();
        }
        if (account != null) {
            userFullInfo.setAccount(account);
        }
        UserFullInfo user = userFullInfoService.findUserFullInfo(userFullInfo);
        Result result = new Result();
        result.setData(user);
        return result;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insertFullInfo(@RequestBody(required=false) Map<String, Object> params, HttpServletRequest request) {
        Result result = null;
        try {
            result = new Result();
            int num;
            logger.info("完善信息：{}",params);
            UserFullInfo userFullInfo = getBeanByMap(params);
            //session中获取当前登录用户，若为空登录超时，结束当前操作
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            if (null != loginUser) {
                userFullInfo.setAccount(loginUser.getLoginAccount());
                UserFullInfo user = userFullInfoService.findUserFullInfo(userFullInfo);
                if (user != null) {
                    //存在则修改
                    num = userFullInfoService.updateUserFullInfo(userFullInfo);
                } else {
                    num = userFullInfoService.insert(userFullInfo);
                }
                result.setData(num);
            } else {
                result.setCode(ResultCode.FAIL);
                result.setMsg("登录超时，请重新登录");
            }
        } catch (Exception e) {
            logger.error("insert error:{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 根据map构造一个UserFullInfo
     * @param params
     * @return
     */
    private UserFullInfo  getBeanByMap(Map<String, Object> params){
        //标识当前为完善信息的哪一步
        String step = MapUtils.getString(params,"perfect_info_step");
        UserFullInfo userFullInfo = new UserFullInfo();
        userFullInfo.setBasic(MapUtils.getString(params,"content"));
        userFullInfo.setBasic1(MapUtils.getString(params,"con"));
        userFullInfo.setContract(MapUtils.getString(params,"contract"));
        userFullInfo.setContract1(MapUtils.getString(params,"contract1"));
        userFullInfo.setEducation(getStringFromList(params.get("education"),"content","education",step));
        userFullInfo.setWork(getStringFromList(params.get("work"),"content","work",step));
        userFullInfo.setFarmlan(getStringFromList(params.get("plant"),"content","plant",step));
        userFullInfo.setFarmlan1(getStringFromList(params.get("plant"),"content1","plant",step));
        userFullInfo.setHonor(getStringFromList(params.get("honor"),"content","honor",step));
        userFullInfo.setHonor1(getStringFromList(params.get("honor"),"content1","honor",step));
        userFullInfo.setPolicial(MapUtils.getString(params,"political"));
        userFullInfo.setPolicial1(MapUtils.getString(params,"political1"));
        userFullInfo.setReligion(getDefaultString(MapUtils.getString(params,"religion"),"religion",step));
        userFullInfo.setReligion1(getDefaultString(MapUtils.getString(params,"religion1"),"religion",step));
        return userFullInfo;
    }

    /**
     *
     * @param objList 参数集合
     * @param key map的key
     * @param thisWord 当前参数名
     * @param step 当前步骤
     * @return
     */
    private String getStringFromList(Object objList,String key,String thisWord, String step){
        StringBuffer stringBuffer = new StringBuffer();
        if (!StringUtils.isEmpty(objList)) {
            List<Map<String, String>> list = (List<Map<String, String>>) objList;
            if (CollectionUtils.isNotEmpty(list)) {
                for (Map<String, String> map : list) {
                    stringBuffer.append(MapUtils.getString(map,key)).append(";");
                }
            }
        }
        //若当前获取的值与当前步骤一致则默认参数为""
        //数据数据设置null不修改；""可修改，
        //当前步只能新增或者修改当前步骤的数据
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(step) && thisWord.equals(step)) {
            return org.apache.commons.lang3.StringUtils.isEmpty(stringBuffer) ? "" : stringBuffer.toString();
        }
        return org.apache.commons.lang3.StringUtils.isEmpty(stringBuffer) ? null : stringBuffer.toString();
    }



    private String getDefaultString(String str,String thisWord,String step){
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(step) && thisWord.equals(step)) {
            return org.apache.commons.lang3.StringUtils.isEmpty(str) ? "" : str;
        }
        return org.apache.commons.lang3.StringUtils.isEmpty(str) ? null : str;
    }

    //保存注册信息详情
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public Result insertInfo(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = null;
        try {
            logger.debug("保存注册信息详情，参数：{}", params);
            result = new Result();
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            if (null == loginUser) {
                result.setMsg("登录超时！请重新登录");
                result.setCode(ResultCode.FAIL);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", loginUser);
                userInfoService.insert(params,session,loginUser);
            }
            logger.info("注册信息保存成功！");
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
            logger.error("注册信息保存失败：{}",e);
        }
        return result;
    }


    //查询注册信息
    @RequestMapping(value = "/findUserInfo", method = RequestMethod.GET)
    public Result findUserInfo(HttpServletRequest request) {
        Result result = new Result();
        try {
            String account = request.getParameter("account");
            if (org.apache.commons.lang3.StringUtils.isEmpty(account)) {
                LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
                if (null != loginUser) {
                    account = loginUser.getLoginAccount();
                } else {
                    result.setMsg("登录超时，请重新登录！");
                    result.setCode(ResultCode.FAIL);
                    return result;
                }
            }
            UserInfo userInfo = userInfoService.findUserInfo(account);
            String speciesId=userInfo.getSpecies();
            String relatedSpecies=specieService.getSpecies(speciesId);
            userInfo.setSpecies(relatedSpecies);
            userInfo.setSpeciesId(speciesId);
            result.setData(userInfo);
            logger.info("查询注册信息 {}", result);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    //查询身份信息
    @RequestMapping(value = "/findContact", method = RequestMethod.GET)
    public Result findContact(HttpServletRequest request) {
        Result result = new Result();
        try {
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            String account = loginUser.getLoginAccount();
            UserContact contact = userContactService.selectByAccount(account);
            result.setData(contact);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    //查询政策信息
    @RequestMapping(value = "/findPolitical", method = RequestMethod.GET)
    public Result findPolitical(HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            UserPolitical political = userPoliticalService.findByAccount(account);
            result.setData(political);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    //查询宗教信息
    @RequestMapping(value = "/findReligion", method = RequestMethod.GET)
    public Result findReligion(HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            UserReligion userReligion = userReligionService.findByAccount(account);
            result.setData(userReligion);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    //查询荣誉信息
    @RequestMapping(value = "/findhonner", method = RequestMethod.GET)
    public Result findHonor(HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            List<UserHonor> list = userHonorService.selectByAccount(account);
            result.setData(list);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 保存网络信息
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertPhone", method = RequestMethod.POST)
    public Result insertPhone(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        UserContact contact = new UserContact();
        Result result = new Result();

        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            contact.setAccount(account);
            contact.setNswyId((String)params.get("id"));
            contact.setStatus2(params.get("status2").equals(true) ? 1 : 0);
            contact.setQq((String)params.get("qq"));
            contact.setEmail((String)params.get("email"));
            contact.setDomain((String) params.get("domain"));
            UserContact old = userContactService.selectByAccount(account);
            if (old != null) {
                userContactService.updateByAccount(contact);
            } else {
                userContactService.insert(contact);
            }
            String step = params.get("step").toString();
            if (StringUtils.hasText(step)) {
                String str = step.substring(12, 16);
                if (!str.equals("prog")) {
                    loginUserService.updateIdentityFlag(account, step);
                }
            }
            result.setData(contact);
        } catch (Exception e) {
            logger.error("保存网络身份信息失败=>{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 用户联系更新
     *
     * @param userContact
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateUserContact", method = RequestMethod.POST)
    public Result updateUserContact(@RequestBody UserContact userContact, HttpServletRequest request) {
        Result result = new Result();
        try {
            userContactService.updateByPrimaryKeySelective(userContact);
            result.setMsg("ok");
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }



    //保存教育信息单条信息
    @RequestMapping(value = "/insertEdu", method = RequestMethod.POST)
    public Result insertEdu(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            Map<String,Object> map1=new HashMap<>();
            map1.put("account",account);
            Map<String,Object> old =userEducationService.selectByAccount(map1);
            if(old !=null ){
                userEducationService.deleteByAccount(map1);
            }
            Object obj1 = params.get("edu");
            map1.put("education",obj1.toString());
            userEducationService.insert(map1);
            String step = params.get("step").toString();
            if (StringUtils.hasText(step)) {
                String str = step.substring(12, 16);
                if (!str.equals("prog")) {
                    loginUserService.updateIdentityFlag(account, step);
                }
            }
            logger.info("教育信息单条数据保存失败=={}",result);
        } catch (Exception e) {
            logger.debug("教育经历信息单条数据保存失败=={}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    //查询教育信息单条信息
    @RequestMapping(value = "/findEdu", method = RequestMethod.POST)
    public Result findEdu(HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            Map<String,Object> map1=new HashMap<>();
            map1.put("account",account);
            map1 = userEducationService.selectByAccount(map1);
            if(map1 !=null){
                result.setCode(ResultCode.SUCCESS) ;
                result.setData(map1.get("education"));
            }else {
                result.setCode(ResultCode.SUCCESS) ;
                result.setData("");
            }
            logger.info("查询教育经历信息",result.toString());
        } catch (Exception e) {
            logger.debug("查询教育经历信息失败",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    //保存工作信息单条信息
    @RequestMapping(value = "/insertWork", method = RequestMethod.POST)
    public Result insertWork(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            Map<String,Object> map1=new HashMap<>();
            map1.put("account",account);
          Map<String,Object> old =userWorkService.selectByAccount(map1);
            if(old !=null ){
                userWorkService.deleteByAccount(map1);
            }
           userWorkExperience.deleteByAccount(account);
            Object obj1 = params.get("work");
            map1.put("work",obj1.toString());
            userWorkService.insert(map1);
            String step = params.get("step").toString();
            if (StringUtils.hasText(step)) {
                String str = step.substring(12, 16);
                if (!str.equals("prog")) {
                    loginUserService.updateIdentityFlag(account, step);
                }
            }
            logger.info("工作经历信息单条数据保存失败=={}",result);
        } catch (Exception e) {
            logger.debug("工作经历信息单条数据保存失败=={}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    //查询工作信息单条信息
    @RequestMapping(value = "/findWork", method = RequestMethod.POST)
    public Result findWork(HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            Map<String,Object> map1=new HashMap<>();
            map1.put("account",account);
            map1 = userWorkService.selectByAccount(map1);
            if(map1 !=null){
               result.setCode(ResultCode.SUCCESS) ;
               result.setData(map1.get("work"));
            }else {
                result.setCode(ResultCode.SUCCESS) ;
                result.setData("");
            }
            logger.info("查询工作经历信息",result.toString());
        } catch (Exception e) {
            logger.debug("查询工作经历信息失败={}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    //保存种养信息单条信息
    @RequestMapping(value = "/savePlant", method = RequestMethod.POST)
    public Result savePlant(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        List<UserFarmInfo> list = new ArrayList<>();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            userFarmInfoService.deleteByAccount(account);
            Object obj2 = params.get("plant");
            if (obj2 != null) {
                List<Map<String, Object>> plants = (List<Map<String, Object>>) obj2;
                for (int i = 0; i < plants.size(); i++) {
                    Map<String, Object> m = plants.get(i);
                    UserFarmInfo userFarmInfo = new UserFarmInfo();
                    userFarmInfo.setAccount(account);
                    userFarmInfo.setFarmlan(MapUtils.getString(m,"space"));
                    userFarmInfo.setSpecies((String)m.get("species"));
                    userFarmInfo.setStatus(m.get("switch1").equals(true) ? 1 : 0);
                    list.add(userFarmInfo);
                }
                userFarmInfoService.insert(list);
                String step = params.get("step").toString();
                if (StringUtils.hasText(step)) {
                    String str = step.substring(12, 16);
                    if (!str.equals("prog")) {
                        loginUserService.updateIdentityFlag(account, step);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    //保存荣誉信息单条信息
    @RequestMapping(value = "/saveHonor", method = RequestMethod.POST)
    public Result saveHonor(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        List<UserHonor> list = new ArrayList<>();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            userHonorService.deleteByAccount(account);
            Object obj3 = params.get("honor");
            if (!StringUtils.isEmpty(obj3)) {
                List<Map<String, Object>> honors = (List<Map<String, Object>>) obj3;
                if (CollectionUtils.isNotEmpty(honors) && honors.size() > 0) {
                    for (int i = 0; i < honors.size(); i++) {
                        Map<String, Object> m = honors.get(i);
                        UserHonor honor = new UserHonor();
                        honor.setAccount(account);
                        honor.setHonor((String)m.get("honor"));
                        honor.setTime((String)m.get("time"));
                        honor.setStatus(m.get("switch1").equals(true) ? 1 : 0);
                        list.add(honor);
                    }
                    userHonorService.insert(list);
                }
            }
            String step = params.get("step").toString();
            if (StringUtils.hasText(step)) {
                String str = step.substring(12, 16);
                if (!str.equals("prog")) {
                    loginUserService.updateIdentityFlag(account, step);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    //保存政治信息单条信息
    @RequestMapping(value = "/savePoli", method = RequestMethod.POST)
    public Result savePolitic(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            userPoliticalService.deleteByAccount(account);
            UserPolitical userPolitical = new UserPolitical();
            userPolitical.setAccount(account);
            userPolitical.setPolitical((String) params.get("poli").toString());
            userPolitical.setTime((String)params.get("time").toString());
            userPolitical.setStatus(params.get("status").equals(true) ? 1 : 0);
            userPoliticalService.insert(userPolitical);
            String step = params.get("step").toString();
            if (StringUtils.hasText(step)) {
                String str = step.substring(12, 16);
                if (!str.equals("prog")) {
                    loginUserService.updateIdentityFlag(account, step);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    //保存宗教信息单条信息
    @RequestMapping(value = "/saveReli", method = RequestMethod.POST)
    public Result saveReligion(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        try {
            String account = loginUser.getLoginAccount();
            userReligionService.deleteByAccount(account);
            UserReligion religion = new UserReligion();
            religion.setAccount(account);
            religion.setReligion((String)params.get("religion"));
            religion.setStatus(params.get("status").equals(true) ? 1 : 0);
            userReligionService.insert(religion);
            String step = params.get("step").toString();
            if (StringUtils.hasText(step)) {
                String str = step.substring(12, 16);
                if (!str.equals("prog")) {
                    loginUserService.updateIdentityFlag(account, step);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 根据用户名查找信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/findcontact/{account}", method = RequestMethod.GET)
    public Result findByName(HttpServletRequest request, @PathVariable("account") String account) {
        Result result = new Result();
        Map<String, String> map = new HashMap<>();
        try {
            UserContact contact = userContactService.selectByAccount(account);
            map.put("nswyId", contact.getNswyId());
            map.put("account", contact.getAccount());
            map.put("qq", contact.getQq());
            map.put("email", contact.getEmail());
            map.put("mobile", contact.getMobile());
            map.put("postcode", contact.getPostalcode());
            map.put("domain", contact.getDomain());
            result.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
