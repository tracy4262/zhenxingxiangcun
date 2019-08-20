package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.MyApp;
import com.ovit.nswy.member.model.SelfMenuConf;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.util.UuidUtil;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginUserController {
    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private SelfMenuConfService selfConfService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PerfectInformationService perfectInformationService;

    @Autowired
    private ProxyService proxyService;

    @Autowired
    private ProxyReversionService proxyReversionService;

    @Autowired
    private ExpertInfoService expertInfoService;
    @Autowired
    private RedisTemplate<String, List<MyApp>> redisTemplate4App;
    @Autowired
    private MyAppService appService;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${register.image}")
    private String image;

    /**
     * 和前台传的参数进行比较
     *
     * @param array
     * @param pojo
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private static SelfMenuConf evaluate(String[] array, SelfMenuConf pojo)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method[] f = SelfMenuConf.class.getDeclaredMethods();
        for (Method method : f) {
            if (method.getName().startsWith("set")) {
                for (String str : array) {
                    String news = str.substring(1, str.length() - 1);
                    if ((news).equalsIgnoreCase(method.getName().substring(3))) {
                        method.invoke(pojo, 1);
                    }
                }
            }
        }
        return pojo;
    }

    private static Date generateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);
        return calendar.getTime();
    }

    @RequestMapping("/")
    public Result findById() {
        List<LoginUser> logins = loginUserService.findAll();
        Result result = new Result();
        result.setData(logins);
        return result;
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping("/findbyid/{id}")
    public Result findById(@PathVariable int id) {
        LoginUser login = loginUserService.selectByPrimaryKey(id);
        Result result = new Result();
        result.setData(login);
        return result;
    }

    @RequestMapping("/findbyname/{name}")
    public Result findByName(@PathVariable String name) {
        Result result = new Result();
        try {
            LoginUser user = loginUserService.findByLoginName(name);
            int userType = user.getUserType();
            if (4 == userType) {
                //如果认证了专家，审核没通过时为普通用户
                int approveStatus = expertInfoService.getApproveStatus(user.getLoginAccount());
                user.setUserType(approveStatus == 1 ? 4 : 0);
            }

            result.setData(user);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/findCurrentUser")
    public Result findCurrentUser(@RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        LoginUser user = loginUserService.findByLoginName(account);
        Map<String,Object> useTemplate = loginUserService.findUseTemplate(account);
        //查询用户最大的年度文件id
        if (MapUtils.isEmpty(useTemplate)) {
            user.setDisplayName("");
            user.setAvatar("");
        } else {
            String memberName = "";
            List<Map<String,Object>> memberList = proxyReversionService.findMemberName(useTemplate);
            if (CollectionUtils.isNotEmpty(memberList)) {
                memberName = MapUtils.getString(memberList.get(0), "memberName");
            }
            user.setDisplayName(memberName);

            Map<String,Object> yearInfo = loginUserService.findYearInfo(account);
            useTemplate.put("yearId", MapUtils.getString(yearInfo, "id"));
            Map<String,Object> privacyInfo = perfectInformationService.findPrivacyInfo(useTemplate);
            //String name = MapUtils.getString(privacyInfo, "name");
            String image = MapUtils.getString(privacyInfo, "image");
            //user.setDisplayName(name);
            user.setAvatar(image);
        }
        int userType = user.getUserType();
        /*if (4 == userType) {
            //如果认证了专家，审核没通过时为普通用户
            int approveStatus = expertInfoService.getApproveStatus(user.getLoginAccount());
            user.setUserType(approveStatus == 1 ? 4 : 0);
        }*/
        Result result = new Result();
        result.setData(user);
        return result;
    }

    /**
     * 好友展示
     *
     * @param
     * @return
     */
    @RequestMapping("/friendlist/{page}")
    public Result findFriend(HttpServletRequest request, @PathVariable String page, @RequestParam(defaultValue = "null", required = false) String name) {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        Result result = new Result();
        try {
            String username = loginUser.getLoginAccount();
            loginUser.setDisplayName(name.trim());
            int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
            PageInfo<LoginUser> list = loginUserService.query(currentPage, 5, loginUser);
            result.setData(list);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 推荐好友
     *
     * @param
     * @return
     */
    @RequestMapping("/friends/{page}")
    public Result showFriends(@PathVariable String page) {
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        PageInfo<LoginUser> list = loginUserService.query(currentPage, 10);
        Result result = new Result();
        result.setData(list);
        return result;
    }

    /**
     * 注册
     *
     * @param params
     * @return @
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insert(@RequestBody Map<String, Object> params, HttpServletRequest request) throws CloneNotSupportedException {
        Result result = null;
        try {
            logger.debug("注册账号：{}",params);
            String username = params.get("username").toString();
            String pwd = params.get("pwd").toString();
            LoginUser user = new LoginUser();
            result = new Result();
            user.setLoginAccount(username);
            user.setUniqueId(UuidUtil.get32UUID());
            user.setRegisterTime(new Date());
            //user.setAvatar("http://192.168.7.61/00/68/wKgHPVqWSreATgJRAAAa36Ua8F8215.png");
            user.setAvatar(image);
            user.setTourNumber(0);
            user.setLoginPasswd(new SimpleHash("SHA-1", Constants.SESSION_SALT, pwd).toString());
            user.setUserType(0);
            String nswyId = loginUserService.generateNswyId();
            user.setNswyIdModel(nswyId);
            loginUserService.insert(user);

            params.put("nswyId", nswyId);
            loginUserService.insertCertification(params,username,user);

            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);

            //LoginUser clone=(LoginUser)user.clone();
            user.setLoginPasswd(null);
            //result.setData(user);

            List<String> accountList = new ArrayList<>();
            accountList.add(username);

            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("account", username);
            resultMap.put("key", username);
            List<Map<String,Object>> proxy = new ArrayList<>();
            for (String s : accountList) {
                LoginUser loginUser = loginUserService.findByLoginName(s);
                Map<String,Object> map = new HashMap<>();
                map.put("account", s);
                map.put("session", loginUser);
                proxy.add(map);
            }
            resultMap.put("proxy", proxy);
            //若是在代理管理里面注册的账户，则需要保存至代理表
            String proxyAccount = MapUtils.getString(params, "proxyAccount");
            if (org.apache.commons.lang3.StringUtils.isNotBlank(proxyAccount)) {
                //若传了proxyAccount，则表示是代理管理里面的操作
                Map<String,Object> proxyMap = new HashMap<>();
                proxyMap.put("account", username);
                proxyMap.put("proxyAccount", proxyAccount);
                proxyMap.put("type", 1);
                proxyReversionService.insertProxy(proxyMap);
            }
            result.setData(resultMap);

            logger.debug("注册成功！");
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
            logger.error("注册失败：{}",e);
        }
        return result;
    }

    @RequestMapping(value = "/is_exists", method = RequestMethod.POST)
    public Result isExists(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询注册用户名是否存在：{}",params);
            LoginUser users = loginUserService.findByLoginName(MapUtils.getString(params,"username"));
            logger.info("查询注册用户名是否存在结果：{}",users);
            if (null != users) {
                result.setMsg(Constants.EXISTS);
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            } else {
                result.setMsg(Constants.NOT_EXISTS);
                result.setCode(ResultCode.SUCCESS);
            }
        } catch (Exception e) {
            logger.error("查询注册用户是否存在失败");
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 登录
     *
     * @param params
     * @param request
     * @return @
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        String userName = params.get("username").toString();
        String pwd = params.get("pwd").toString();
        //String pwdMD5 = new SimpleHash("SHA-1", Constants.SESSION_SALT, pwd).toString();
        LoginUser user = loginUserService.login(userName, pwd);
        Result result = new Result();
        if (null != user) {
            HttpSession session = request.getSession();
            String token = generateToken(user);
            user.setToken(token);
            session.setAttribute("currentUser", user);
            session.setMaxInactiveInterval(Integer.MAX_VALUE);
            user.setLoginPasswd(null);
            //result.setData(user);
            Map<String, Object> shopMap = new HashMap<String, Object>();
            shopMap.put("member_id", String.valueOf(user.getId()));
            shopMap.put("name", user.getDisplayName());
            shopMap.put("gateway_is_check", "true");
            String avatar = null != user.getAvatar() ? user.getAvatar() : "";
            shopMap.put("face_image", avatar);
            // gateway_type":1,//门户类型 1：//商城 2：//博客类 3：//混合 商城+博客
            shopMap.put("gateway_type", "1");
            redisTemplate.opsForValue().set(user.getUniqueId(), JSONObject.toJSONString(shopMap), 10, TimeUnit.HOURS);
            // redisTemplate.opsForHash().putAll(user.getUniqueId(), shopMap);
            // System.out.println(redisTemplate.opsForHash().entries(user.getUniqueId()));
            // 我的应用
            List<MyApp> appList = appService.selectByAccount(user.getLoginAccount());
            String key = user.getUniqueId() + "_" + user.getLoginAccount();
            redisTemplate4App.opsForValue().set(key, appList);

            //返回用户相关信息，包括该用户代理的企业、机关等
            /*Map<String,Object> proxyMap = new HashMap<>();
            proxyMap.put("loginAccount", userName);
            proxyMap.put("approve_status", 1);
            proxyMap.put("TABLE_NAME", "proxy_gov_info");
            proxyMap.put("name", "gov_name");
            proxyMap.put("info_name", "gov_info");
            proxyMap.put("logo_url", "logo_picture_list");
            List<Map<String,Object>> govList = proxyService.queryProxyList(proxyMap);

            proxyMap.put("TABLE_NAME", "proxy_corp_info");
            proxyMap.put("name", "corp_name");
            proxyMap.put("info_name", "corp_info");
            proxyMap.put("logo_url", "logo_url");
            List<Map<String,Object>> corpList = proxyService.queryProxyList(proxyMap);
            govList.addAll(corpList);*/

            List<String> accountList = new ArrayList<>();
            accountList.add(userName);
            /*for (Map<String,Object> map : govList) {
                accountList.add(MapUtils.getString(map, "account"));
            }*/
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("account", userName);
            resultMap.put("key", userName);
            resultMap.put("userType", user.getUserType());
            List<Map<String,Object>> proxy = new ArrayList<>();
            for (String s : accountList) {
                LoginUser loginUser = loginUserService.findByLoginName(s);
                Map<String,Object> map = new HashMap<>();
                map.put("account", s);
                map.put("session", loginUser);
                proxy.add(map);
            }
            resultMap.put("proxy", proxy);
            result.setData(resultMap);

        }
        return result;
    }

    /**
     * 用户自定义设置
     *
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NumberFormatException
     */
    @RequestMapping(value = "/selfConf", method = RequestMethod.POST)
    public void CustomSet(HttpServletRequest request, @RequestBody Map<String, Object> params)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        SelfMenuConf selfs = new SelfMenuConf();
        String conf = (String) params.get("self");
        String str = conf.substring(1, conf.length() - 1);
        String[] confs = str.split(",");
        SelfMenuConf selfMenuConf = evaluate(confs, selfs);
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String username = loginUser.getLoginAccount();
        SelfMenuConf self = selfConfService.findByAccount(username);
        if (self != null) {
            selfConfService.deleteByAccount(username);
            selfMenuConf.setUserAccount(username);
            selfConfService.insert(selfMenuConf);
        } else {
            selfMenuConf.setUserAccount(username);
            selfConfService.insert(selfMenuConf);
        }
    }

    /**
     * 登出
     *
     * @param request
     * @
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("currentUser");
    }

    /**
     * 获取我的应用
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/findMyApp", method = RequestMethod.GET)
    public Result findMyApp(HttpServletRequest request) {
        Result result = null;
        try {
            result = new Result();
            HttpSession session = request.getSession();
            LoginUser user = (LoginUser) session.getAttribute("currentUser");
            String key = user.getUniqueId() + "_" + user.getLoginAccount();
            List<MyApp> appList = redisTemplate4App.opsForValue().get(key);
            result.setData(appList);
        } catch (Exception e) {
            logger.error("查询我的应用失败：{}",e);
        }
        return result;
    }

    @RequestMapping(value = "/find-my-app", method = RequestMethod.POST)
    public  Result getMyApp(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.info("/find-my-app，参数：{}",params);
            List<Map<String,Object>> list = appService.queryMyAppByAccount(params);
            int appStatus = -1;
            if (CollectionUtils.isEmpty(list)) {
                appStatus = appService.queryAppUseStatus(params);
            }
            Map<String,Object> map = new HashMap<>();
            map.put("appList",list);
            map.put("appStatus",appStatus);
            logger.info("/find-my-app，查询成功：{}",params);
            result.setData(map);
        } catch (Exception e) {
            logger.error("/find-my-app，查询失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    private String generateToken(LoginUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("account", user.getLoginAccount());
        claims.put("userName", user.getDisplayName());
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret) //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
        logger.error(token);
        return token;
    }

    /**
     * 修改签名
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/editSignature", method = RequestMethod.GET)
    public Result editSignature(HttpServletRequest request) {
        LoginUser user = new LoginUser();
        Result result = new Result();
        String account = request.getParameter("account");
        try {
            user.setLoginAccount(account);
            String signa = request.getParameter("signature");
            user.setSignaTure(signa);
            loginUserService.updateByAccount(user);
        }catch (Exception e){
            logger.error("保存签名失败:{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存签名失败");
        }
        return result;
    }


}
