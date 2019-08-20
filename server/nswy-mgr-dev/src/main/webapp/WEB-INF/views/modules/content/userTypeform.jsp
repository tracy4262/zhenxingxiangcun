<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户类型管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
            $("#no").focus();
            $("#inputForm").validate({
                rules: {
                    loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
                },
                messages: {
                    loginName: {remote: "用户登录名已存在"},
                    confirmNewPassword: {equalTo: "输入与上面相同的密码"}
                },
                submitHandler: function(form){
                    //loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });


        function pictureUseHome(value,infoId,name) {

            var text = value == '0' ? "是否设置用户类型到完善信息显示？" : "是否取消"+userType+"到完善信息显示？"
            var status = 0;
            if(value=='1'){
                status = 0;
            }else{
                status = 1;
            }
            var  confirmStatus = confirm(text)
            if (confirmStatus == true) {
                $.ajax({
                    type : 'POST',
                    url : '${ctx}/nswy/updateSysUserInfo',
                    data : {
                        user_status : status,
                        infoId : infoId,
                        INFO_NAME:name
                    },
                    success : function () {
                        //window.location.href="${ctx}/nswy/picture/control";
                        refresh();
                    }
                });
            }
        }

        function refresh() {
            $("#btnSubmit").click;
        }
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<%--<shiro:hasPermission name="sys:user:list"><li><a href="${ctx}/nswy/userType/findList">用户类型列表</a></li></shiro:hasPermission>--%>

	<li><a href="${ctx}/nswy/userType/findList">用户类型列表</a></li>
	<li class="active"><a href="${ctx}/nswy/userType/form">用户类型添加</a></li>

</ul><br/>
<form:form id="inputForm" modelAttribute="userTypeInfo" action="${ctx}/nswy/userType/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>

	<div class="control-group">
		<label class="control-label">用户类型名称:</label>
		<div class="controls">
			<form:input path="userTypeName" htmlEscape="false" maxlength="50" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>

<%--
	<div class="control-group">
		<label class="control-label">是否显示:</label>
		<div class="controls">

			<input type="checkbox"  value="" ${userTypeInfo.isDisplay eq '1' ? 'checked' : ''} onchange="pictureUseHome('${userTypeInfo.isDisplay}','${userTypeInfo.infoId}')"/>

			<span class="help-inline"><font color="red">*</font> “是”代表此用户类型显示，“否”则表示此此用户类型不显示</span>
		</div>
	</div>
--%>

	<div class="form-actions">
		<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>