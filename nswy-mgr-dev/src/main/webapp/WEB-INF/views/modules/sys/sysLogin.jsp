<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
      html,body,table{background:url(${ctxStatic}/images/banner.png) no-repeat;background-size: 100% 65%; /*background-color:#f5f5f5;*/width:100%;text-align:center;}
	  .form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
      .form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
        	-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow: 0 2px 24px 0 rgba(28,94,155,0.20);}
      .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
      .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
      .form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
      .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
      .header{height:140px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
      label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
	  .ivu-input{
		  border: 1px solid rgb(216, 215, 215);
	  }
	  .ivu-input:hover{
		  border-color: rgba(82,168,236,0.8);
		  box-shadow: inset 0 1px 1px rgba(0,0,0,0.075), 0 0 8px rgba(82,168,236,0.6);
	  }
		.footer{
			position: absolute;
			bottom: 20px;
			text-align: center;
			width: 100%;
		}
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>
		</div>
	</div>

	<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
		<div style="margin-top: 28px;" align="center">
			<img src="${ctxStatic}/images/logo.png" alt="" style="margin-right:5px;">

		</div>


		<div class="ivu-input" style="margin-top: 26px;">
			<div style="display: inline-block; padding: 5px 10px; border-right: 1px solid rgb(216, 215, 215);">
				<img src="${ctxStatic}/images/login.png" alt="" style="margin-right:5px;margin-top:-3px;">账号
			</div>
			<input type="text" id="username" name="username" value="${username}" style="border: none;  margin-top: 6px;box-shadow: none;">
		</div>

		<div class="ivu-input" style="margin-top: 28px;">
			<div style="display: inline-block; padding: 5px 10px; border-right: 1px solid rgb(216, 215, 215);">
				<img src="${ctxStatic}/images/pwd.png" alt="" style="margin-right:5px;margin-top:-3px;">密码
			</div>
			<input type="password" id="password" name="password"  style="border: none;margin-top: 6px;box-shadow: none;">
		</div>

		<div style="margin-top: 22px;">
			<label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住密码</label>
		</div>

		<div  style="margin-top: 26px;">
			<input type="submit" value="登 录" style="background: #2FA4E7;box-shadow: 0px 5px 18px 0px rgba(1,81,152,0.25);width: 260px;height: 46px;margin-left: 25px;color: white; border: none;"/>
		</div>

	</form>
	<div class="footer">
		Copyright &copy; 2012-${fns:getConfig('copyrightYear')} <a target="_blank" href="http://www.hbaas.com">湖北省农业科学院</a> - Powered By <a href="http://www.cnovit.com" target="_blank">光谷信息</a>
	</div>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
</body>
</html>