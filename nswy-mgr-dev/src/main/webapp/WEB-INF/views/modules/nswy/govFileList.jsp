<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业资质文件审核管理</title>
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
	<form:form id="searchForm" modelAttribute="nswyGovInfo" action="${ctx}/nswy/getGovFilelist" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>企业名称：</label><form:input path="govName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>法人：</label><form:input path="legalPerson" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="clearfix"></li>
			<li><label>注册号：</label><form:input path="govCode" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>机关名称</th><th>法人姓名</th><th>注册号</th><th>注册时间</th><%-- <shiro:hasPermission name="sys:role:edit"> --%><th>操作</th><%-- </shiro:hasPermission> --%></tr>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td><a href="${ctx}/nswy/govFileForm?govInfoId=${item.govInfoId}">${item.govName}</a></td>
				<td><a href="${ctx}/nswy/govFileForm?govInfoId=${item.govInfoId}">${item.legalPerson}</a></td>
				<td>${item.govCode}</td>
				<td>${item.time}</td>
				<td>
					<a href="${ctx}/nswy/govFileForm?govInfoId=${item.govInfoId}">资质审核</a>
					<%-- <a href="${ctx}/nswy/deleteGovInfo?govInfoId=${item.govInfoId}" onclick="return confirmx('确认要删除该条记录吗？', this.href)">删除</a> --%>
				</td>
			</tr>
		</c:forEach>
	</table>
    <div class="pagination">${page}</div>
 </body>
</html>