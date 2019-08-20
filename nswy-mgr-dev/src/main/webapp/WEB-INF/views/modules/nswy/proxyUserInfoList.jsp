<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理审核</title>
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
<form:form id="searchForm"  modelAttribute="proxyUserInfo" action="${ctx}/nswy/getProxyUserInfolist" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
	<label>被代理账户名称：</label><form:input path="account" htmlEscape="false" maxlength="50" class="input-medium"/>
	<label class="control-label">审核状态:</label>
	<form:select path="status" class="input-medium">
		<form:option value="">全部</form:option>
		<%--<form:option value="0">完善信息中</form:option>--%>
		<form:option value="1">通过</form:option>
		<form:option value="2">待审</form:option>
        <form:option value="3">拒绝</form:option>
        <%--<form:option value="4">暂不代理</form:option>--%>
	</form:select>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</div>
</form:form>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<th  width="20%">被代理账户名称</th>
			<th>代理人</th>
			<th>审核类型</th>
			<th>模板</th>
			<th>协议</th>
			<th>审核状态</th>
			<shiro:hasPermission name="sys:role:edit">
				<th>操作</th>
			</shiro:hasPermission></tr>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td>${item.account}</td>
				<td>${item.proxyAccount}</td>

					<c:choose>
						<c:when test="${item.type eq 0}">
							<td>取消代理审核</td>
							<td title="${item.cancelReason}">${item.cancelReason} </td>
							<td title="${item.otherReason}">${item.otherReason}</td>
							<%--<td>取消代理模板</td>--%>
                            <%--<td>${item.cacelUploadProtocol}</td>--%>
						</c:when>
						<c:when test="${item.type eq 1}">
							<td>代理审核</td>
							<td title="${item.proxyTemplate}"><a href="${item.proxyTemplate}" target= _blank>申请代理模板</a> </td>
							<td title="${item.uploadProtocol}"><a href="${item.uploadPdf}" target= _blank>${item.uploadProtocol}</a> </td>
							<%--<td>申请代理模板</td>--%>
                            <%--<td>${item.uploadProtocol}</td>--%>
						</c:when>
					</c:choose>


				<c:choose>
					<%--<c:when test="${item.status eq 0}">--%>
						<%--<td>完善信息中</td>--%>
					<%--</c:when>--%>
					<c:when test="${item.status eq 1}">
						<td>通过</td>
					</c:when>
					<c:when test="${item.status eq 2}">
						<td>待审</td>
					</c:when>
					<c:when test="${item.status eq 3}">
						<td>拒绝</td>
					</c:when>
					<%--<c:when test="${item.status eq 4}">--%>
						<%--<td>暂不代理</td>--%>
					<%--</c:when>--%>
				</c:choose>

				<td>
                    <%--<a href="${ctx}/nswy/proxyCorpInfoForm?id=${item.id}">修改</a>--%>
                    <a href="${ctx}/nswy/deleteProxyUserInfo?proxyUserInfoId=${item.proxyUserInfoId}" onclick="return confirm('确认要删除该代理吗？')">删除</a>
					<c:if test="${item.status eq 2}">
						<a href="${ctx}/nswy/proxyUserInfoAuditFrom?proxyUserInfoId=${item.proxyUserInfoId}">审核</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>