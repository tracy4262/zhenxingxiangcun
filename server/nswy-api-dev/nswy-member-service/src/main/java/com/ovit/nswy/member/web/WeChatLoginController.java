package com.ovit.nswy.member.web;

import com.ovit.nswy.member.web.base.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/wechatLogin")
public class WeChatLoginController {

	/**
	 * 
	 */
	private Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * 微信消息接收和token验证
	 * 
	 * @return @
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Result login() {
		return null;
	}

	/**
	 * 微信引导页进入的方法
	 * 
	 * @return
	 */
	@RequestMapping("/loginByWeiXin")
	public String loginByWeiXin(HttpServletRequest request, Map<String, Object> map) {
		// 微信接口自带 2 个参数
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		System.out.println("code = " + code + ", state = " + state);

		if (code != null && !"".equals(code)) {
			/*
			 * // 授权成功, 微信获取用户openID OAuthInfo authInfo =
			 * WeiXinUtil.getAccess_token(code); String openid =
			 * authInfo.getOpenid(); String access_token =
			 * authInfo.getAccess_token();
			 * 
			 * if (access_token == null) { // Code 使用过 异常
			 * System.out.println("Code 使用过 异常....."); return "redirect:" +
			 * WeiXinUtil.getStartURLToGetCode(); }
			 * 
			 * // 数据库中查询微信号是否绑定平台账号 SysUser sysUser =
			 * weiXinService.getUserByWeiXinID(openid); if (sysUser == null) {
			 * String randomStr = StringUtil.getRandomString(50);
			 * request.getSession().setAttribute(openid, randomStr); // 尚未绑定账号
			 * System.out.println("尚未绑定账号....."); return
			 * "redirect:/index.jsp?openid=" + openid + "&state="+ randomStr; }
			 * userController.doSomeLoginWorkToHomePage(sysUser.getMcid(), map);
			 */
			// 登录成功
			return "homePage";
		}
		/*
		 * // 未授权 return "redirect:" + WeiXinUtil.getStartURLToGetCode();
		 */
		return null;

	}

	/**
	 * 第一步：用户同意授权，获取code(引导关注者打开如下页面：) 获取 code、state
	 */
	public static String getStartURLToGetCode() {
		String takenUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		/*
		 * takenUrl = takenUrl.replace("APPID", Param.APPID); takenUrl =
		 * takenUrl.replace("REDIRECT_URI",URL.encode(Param.REDIRECT_URI)); //
		 * FIXME ： snsapi_userinfo takenUrl = takenUrl.replace("SCOPE",
		 * "snsapi_userinfo"); System.out.println(takenUrl);
		 */
		return takenUrl;
	}

	/**
	 * 获取access_token、openid 第二步：通过code获取access_token
	 * 
	 * @param code
	 *            url = "https://api.weixin.qq.com/sns/oauth2/access_token
	 *            ?appid=APPID &secret=SECRET &code=CODE
	 *            &grant_type=authorization_code"
	 */
	/*
	 * public static OAuthInfo getAccess_token(String code) { String authUrl =
	 * "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code "
	 * ; authUrl = authUrl.replace("APPID", Param.APPID); authUrl =
	 * authUrl.replace("SECRET", Param.SECRET); authUrl =
	 * authUrl.replace("CODE", code); String jsonString =
	 * HTTPRequestUtil.sendPost(authUrl, ""); System.out.println("jsonString: "
	 * + jsonString); OAuthInfo auth = null; try { auth = (OAuthInfo)
	 * JacksonUtil.parseJSONToObject(OAuthInfo.class, jsonString); } catch
	 * (Exception e) { e.printStackTrace(); } return auth; }
	 */

}
