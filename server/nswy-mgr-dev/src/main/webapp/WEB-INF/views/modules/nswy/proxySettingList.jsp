<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>运营管理</title>
	<meta name="decorator" content="default"/>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/nswy/getProxySettinglist">运营列表</a></li>
	</ul>
    <form:form id="searchForm" modelAttribute="proxyAgency" action="${ctx}/nswy/getProxySettinglist" method="post" class="breadcrumb form-search ">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
        <ul class="ul-form">
            <li><label>企业名称：</label><form:input path="corpName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
            <li><label>法人：</label><form:input path="legalMan" htmlEscape="false" maxlength="50" class="input-medium"/></li>
            <li class="clearfix"></li>
            <li><label>被代理账号：</label><form:input path="account" htmlEscape="false" maxlength="50" class="input-medium"/></li>
            <li><label>代理账号：</label><form:input path="myProxy" htmlEscape="false" maxlength="50" class="input-medium"/></li>
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
            <li class="clearfix"></li>
        </ul>
    </form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>机构类型</th><th>机构全称</th><th>机构账号</th><th>法人</th><th>社会统一信用码</th><th>创建时间</th><th>代理人账号</th><th>审核状态</th><th>操作</th></tr>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td>${item.type}</td>
				<td>${item.name}</td>
				<td><a href="${ctx}/nswy/proxySettingForm?proxyId=${item.id}">${item.account}</a></td>
				<td>${item.legal_name}</td>
				<td>${item.credit_code}</td>
				<td>${item.create_time}</td>
				<td>${item.agencyer}</td>
				<td>${item.audit_status}</td>
				<td>
					<a href="${ctx}/nswy/proxySettingForm?proxyId=${item.id}">查看</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
	</body>
</html>