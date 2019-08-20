<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
	</script>
    <style>
        #contentTable tr td {
            text-overflow: ellipsis; /* for IE */
            -moz-text-overflow: ellipsis; /* for Firefox,mozilla */
            overflow: hidden;
            white-space: nowrap;
            text-align: left;
        }
        .orderNum{
            width: 30px;
        }
    </style>
</head>
<body>
<form:form id="searchForm"  modelAttribute="proxyGovInfo" action="${ctx}/nswy/getProxyGovInfolist" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
	<label>被代理单位名称：</label><form:input path="govName" htmlEscape="false" maxlength="50" class="input-medium"/>
	<label class="control-label">审核状态:</label>
	<form:select path="approveStatus" class="input-medium">
		<form:option value="">全部</form:option>
		<form:option value="0">待审核</form:option>
		<form:option value="1">已审核</form:option>
		<form:option value="2">审核拒绝</form:option>
	</form:select>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</div>
</form:form>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<th  width="20%">被代理单位名称</th>
			<th>法定代表人姓名</th>
			<th>代理人</th>
			<th>审核状态</th>
			<shiro:hasPermission name="sys:role:edit">
				<th>操作</th>
			</shiro:hasPermission></tr>
		<c:forEach items="${page.list}" var="item">
			<tr>
                <td title="${item.govName}" width="50px">
                    <c:if test="${fn:length(item.govName) > 20 }">
                        ${fn:substring(item.govName,0,20)}...
                    </c:if>
                    <c:if test="${fn:length(item.govName) < 21 }">
                        ${item.govName}
                    </c:if>
                </td>
				<td>${item.legalPerson}</td>
				<td>${item.agencyName}</td>
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
					<c:when test="${item.approveStatus eq 3}">
						<td>已审核</td>
					</c:when>
				</c:choose>
				<td>
                    <%--<a href="${ctx}/nswy/proxyGovInfoForm?id=${item.id}">修改</a>--%>
                    <a href="${ctx}/nswy/deleteProxyGovInfo?proxyGovInfoId=${item.proxyGovInfoId}" onclick="return confirm('确认要删除该代理吗？')">删除</a>
					<c:if test="${item.approveStatus eq 0}">
						<a href="${ctx}/nswy/proxyGovInfoAuditFrom?proxyGovInfoId=${item.proxyGovInfoId}">审核</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>