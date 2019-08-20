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
</head>
<body>
<form:form id="searchForm" action="${ctx}/nswy/getExpertlist" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
</div>
</form:form>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>专家姓名</th><th>行业</th><th>擅长领域</th><th>擅长物种</th><th>关联物种</th><th>相关行业</th><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td><a href="expertForm?expertId=${item.expertId}">${item.expertName}</a></td>
				<td><a href="expertForm?expertId=${item.expertId}">${item.expertType}</a></td>
				<td>${item.adeptField}</td>
				<td>${item.adeptSpecies}</td>
				<td>${item.relatedSpecies}</td>
				<td>${item.relatedIndustry}</td>
				<td>
					<a href="${ctx}/nswy/expertForm?expertId=${item.expertId}">修改</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>