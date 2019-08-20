]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户页面管理</title>
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
<form:form id="searchForm" modelAttribute="nswyUser" action="${ctx}/nswy/getUserlist" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	登录账号：<input name="loginAccount" type="text" />
	姓名：<input name="displayName" type="text" />
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</div>
</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>登陆账号</th><th>昵称</th><th>用户类型</th><th>注册时间</th><th>操作</th></tr>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td><a href="form?userId=${item.userId}">${item.loginAccount}</a></td>
				<td><a href="form?userId=${item.userId}">${item.displayName}</a></td>
				<c:choose>
                    <c:when test="${empty item.userType or item.userType eq 0}">
                        <td>个人用户</td>
                    </c:when>
                    <c:when test="${item.userType eq 1}">
                        <td>企业</td>
                    </c:when>
                    <c:when test="${item.userType eq 2}">
                        <td>政府</td>
                    </c:when>
                    <c:when test="${item.userType eq 4}">
                        <td>专家</td>
                    </c:when>
				</c:choose>
				<td>${item.time}</td>
				<td>
					<a href="${ctx}/nswy/form?userId=${item.userId}">查看</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>