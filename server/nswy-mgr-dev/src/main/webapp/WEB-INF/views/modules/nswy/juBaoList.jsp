<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>举报管理</title>
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
<form:form id="searchForm" action="${ctx}/nswy/getJuBaolist" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<%--&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;--%>
</div>
</form:form>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>举报人</th><th>举报类型</th><th>举报链接</th><th>描述</th><th>举报时间</th><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td><a href="juBaoForm?juBaoId=${item.juBaoId}">${item.creator}</a></td>
				<td>${item.type}</td>
				<td><a href="${item.link}">${item.link}</a></td>
				<td>${item.description}</td>
				<td>${item.time}</td>



				<%--<shiro:hasPermission name="sys:role:edit"><td>
					<a href="${ctx}/sys/role/assign?id=${role.id}">分配</a>
					<c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
						<a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
					</c:if>
					<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
				</td></shiro:hasPermission>	--%>
				<%--<shiro:hasPermission name="nswy:corpInfo:edit">--%>
				<td>
					<a href="${ctx}/nswy/juBaoForm?juBaoId=${item.juBaoId}">修改</a>
					<a href="${ctx}/nswy/deleteJuBao?juBaoId=${item.juBaoId}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
				</td>
				<%--</shiro:hasPermission>--%>
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>