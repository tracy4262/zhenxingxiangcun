<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户类型管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

        function page(n,s){
            if(n) $("#pageNo").val(n);
            if(s) $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/sys/user/list");
            $("#searchForm").submit();
            return false;
        }
	</script>
</head>
<body>

<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/nswy/userType/findList">用户类型列表</a></li>

	<shiro:hasPermission name="sys:user:edit">
		<li><a href="${ctx}/nswy/userType/findFrom">用户类型添加</a></li>
	</shiro:hasPermission>

</ul>
<form:form id="searchForm" modelAttribute="userTypeInfo" action="${ctx}/nswy/userType/findList" method="post" class="breadcrumb form-search ">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<label>用户类型名称：</label><form:input path="userTypeName" htmlEscape="false" maxlength="50" class="input-medium"/>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>用户类型名称</th>
		<th>用户类型</th>
		<th>创建人</th>
		<%--<th>是否显示</th>--%>
		<th>创建时间</th>
		<shiro:hasPermission name="sys:area:edit">
			<th>操作</th>
		</shiro:hasPermission>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="user">
		<tr>
			<td>${user.userTypeName}</td>
			<td>${user.userType}</td>

			<td>${user.agencyName}</td>
			<%--<td>
				<input type="text" id="orderNum${user.infoId}" class="orderNum" onchange="setOrderNum('${user.infoId}')" value="${user.isDisplay}" />
			</td>--%>
			<td>
				<fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd"/>
			</td>

			<shiro:hasPermission name="sys:area:edit"><td>

				<a href="${ctx}/nswy/userType/delete?infoId=${user.infoId}" onclick="return confirmx('是否删除该用户类型信息？', this.href)">删除</a>

			</td></shiro:hasPermission>

		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>