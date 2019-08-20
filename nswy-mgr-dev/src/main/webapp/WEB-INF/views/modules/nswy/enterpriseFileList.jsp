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
	<form:form id="searchForm" modelAttribute="nswyCorpInfo" action="${ctx}/nswy/getEnterpriseList" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>企业名称：</label><form:input path="corpName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>法人：</label><form:input path="legalPerson" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="clearfix"></li>
			<li><label>工商注册号：</label><form:input path="corpCode" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>身份证号：</label><form:input path="identificationCard" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>企业名称</th><th>法人姓名</th><th>工商执照注册号</th><th>身份证号</th><th>创建时间</th><!-- <th>审核人</th><th>审核状态</th><th>审核时间时间</th> --><%-- <shiro:hasPermission name="sys:role:edit"> --%><th>操作</th><%-- </shiro:hasPermission> --%></tr>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td><a href="${ctx}/nswy/enterpriseForm?corpInfoId=${item.corpInfoId}">${item.corpName}</a></td>
				<td><a href="${ctx}/nswy/enterpriseForm?corpInfoId=${item.corpInfoId}">${item.legalPerson}</a></td>
				<td>${item.corpCode}</td>
				<td>${item.identificationCard}</td>
				<td>${item.time}</td>
<%-- 				<td>${item.approveAccount}</td>
				<td>${item.statusDesc}</td>
				<td>${item.atime}</td> --%>
				<td>
					<a href="${ctx}/nswy/enterpriseForm?corpInfoId=${item.corpInfoId}">资质审核</a>
					<%-- <a href="${ctx}/nswy/deleteCorpInfo?corpInfoId=${item.corpInfoId}" onclick="return confirmx('确认要删除该条记录吗？', this.href)">删除</a> --%>
				</td>
			</tr>
		</c:forEach>
	</table>
    <div class="pagination">${page}</div>
 </body>
</html>