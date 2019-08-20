<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>运营管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		    var business_license_picture_list = '${proxyAgency.business_license_picture_list}';
		    if(business_license_picture_list){
		        var array = business_license_picture_list.split(',');
		        var el = "";
		        $(array).each(function(){
		            el+="<img src='http:"+this+"'/><br>";
		        });
		        $("#business_license_picture_list").html(el);
		    }

			var agent_agreement_picture_list = '${proxyAgency.agent_agreement_picture_list}';
		    if(agent_agreement_picture_list){
		        var array = agent_agreement_picture_list.split(',');
		        var el = "";
		        $(array).each(function(){
		            el+="<img src='http:"+this+"'/><br>";
		        });
		        $("#agent_agreement_picture_list").html(el);
		    }

			var agent_identity_card_picture_list = '${proxyAgency.agent_identity_card_picture_list}';
		    if(agent_identity_card_picture_list){
		        var array = agent_identity_card_picture_list.split(',');
		        var el = "";
		        $(array).each(function(){
		            el+="<img src='http:"+this+"'/><br>";
		        });
		        $("#agent_identity_card_picture_list").html(el);
		    }

			var legal_identity_card_picture_list = '${proxyAgency.legal_identity_card_picture_list}';
		    if(legal_identity_card_picture_list){
		        var array = legal_identity_card_picture_list.split(',');
		        var el = "";
		        $(array).each(function(){
		            el+="<img src='http:"+this+"'/><br>";
		        });
		        $("#legal_identity_card_picture_list").html(el);
		    }
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					/* loading('正在提交，请稍等...'); */
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/nswy/getProxySettinglist">运营列表</a></li>
	</ul><br/>
	<form:form id="inputForm" action="${ctx}/nswy/proxySettingSave" method="post" class="form-horizontal">
		<input type="hidden" name="proxyId" value=${proxyAgency.id}>
		<div class="control-group">
			<label class="control-label">类型:</label>
			<div class="controls">${proxyAgency.type}</div>
		</div>
		<div class="control-group">
			<label class="control-label">被代理账号:</label>
			<div class="controls">${proxyAgency.account}</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业名称:</label>
			<div class="controls">${proxyAgency.name}</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人:</label>
			<div class="controls">${proxyAgency.legal_name}</div>
		</div>
		<div class="control-group">
            <label class="control-label">统一社会信用码:</label>
            <div class="controls">${proxyAgency.credit_code}</div>
        </div>
        <div class="control-group">
            <label class="control-label">创建时间:</label>
            <div class="controls">${proxyAgency.create_time}</div>
        </div>
        <div class="control-group">
            <label class="control-label">代理账号:</label>
            <div class="controls">${proxyAgency.agencyer}</div>
        </div>
        <div class="control-group">
            <label class="control-label">电话:</label>
            <div class="controls">${proxyAgency.phone}</div>
        </div>
         <div class="control-group">
            <label class="control-label">机构注册地址:</label>
            <div class="controls">${proxyAgency.register_address}</div>
        </div>
         <div class="control-group">
            <label class="control-label">代理协议图片:</label>
            <div class="controls" id="agent_agreement_picture_list"></div>
         </div>
         <div class="control-group">
            <label class="control-label">营业执照图片:</label>
            <div class="controls" id="business_license_picture_list"></div>
         </div>
         <div class="control-group">
            <label class="control-label">代理人身份证:</label>
            <div class="controls" id="agent_identity_card_picture_list"></div>
         </div>
         <div class="control-group">
            <label class="control-label">法人代表身份证:</label>
            <div class="controls" id="legal_identity_card_picture_list"></div>
         </div>
         <c:if test="${proxyAgency.audit_status eq '未审核'}">
            <div class="control-group">
                <label class="control-label">审核状态:</label>
                <div class="controls">
                    <input type="radio" checked name="examStatus" value="1">通过
                    <input type="radio" name="examStatus" value="2">拒绝
                    <span class="help-inline"></span>
                </div>
            </div>
         </c:if>
		 <div class="form-actions">
		    <c:if test="${proxyAgency.audit_status eq '未审核'}">
			    <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return confirm('确认要审核吗？')"/>&nbsp;
			 </c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		 </div>
	</form:form>
</body>
</html>