<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>乡村机构管理</title>
	<meta name="decorator" content="default"/>
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
<form:form id="searchForm" modelAttribute="nswyGovInfo" action="${ctx}/nswy/getGovInfolist" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<label>机关名称：</label><form:input path="govName" htmlEscape="false" maxlength="50" class="input-medium"/>
	<label class="control-label">审核状态:</label>
	<form:select path="approveStatus" class="input-medium">
		<form:option value="3">全部</form:option>
		<form:option value="0">待审核</form:option>
		<form:option value="1">已审核</form:option>
		<form:option value="2">审核拒绝</form:option>
	</form:select>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</div>
</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>机关名称</th><th>法人姓名</th><th>注册地址</th><th>注册时间</th><th>审核状态</th><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td><a href="govInfoForm?govInfoId=${item.govInfoId}">${item.govName}</a></td>
		<td><a href="govInfoForm?govInfoId=${item.govInfoId}">${item.userName}</a></td>
				<td>${item.location}</td>
				<td>${item.time}</td>
				<c:choose>
					<c:when test="${item.approveStatus eq 0}">
						<td>待审核</td>
					</c:when>
					<c:when test="${item.approveStatus eq 1}">
						<td>已审核</td>
					</c:when>
					<c:when test="${item.approveStatus eq 2}">
						<td>审核拒绝</td>
					</c:when>
				</c:choose>
				<td>
					<c:if test="${item.approveStatus eq 0}">
						<a href="${ctx}/nswy/govInfoAuditForm?govInfoId=${item.govInfoId}">审核</a>
					</c:if>

				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>