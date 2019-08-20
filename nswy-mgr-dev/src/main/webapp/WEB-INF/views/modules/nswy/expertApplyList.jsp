<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家审核</title>
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
<form:form id="searchForm"  modelAttribute="nswyExpert"  action="${ctx}/nswy/getExpertApplylist" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	专家姓名：<input type="text" name="expertName" maxlength="8" />
	<label class="control-label">审核状态:</label>
	<form:select path="approveStatus" class="input-medium">
		<form:option value="">全部</form:option>
		<form:option value="0">待审核</form:option>
		<form:option value="1">审核通过</form:option>
		<form:option value="2">审核拒绝</form:option>
	</form:select>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<th>专家姓名</th>
			<%--<th>用户类型</th>--%>
			<th>专家类型</th>
			<th>擅长物种</th>
			<th>审核状态</th>
			<shiro:hasPermission name="sys:role:edit">
				<th>操作</th>
			</shiro:hasPermission>
		</tr>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td>${item.expertName}</td>

					<%--<c:choose>
						<c:when test="${item.userType eq 0}">
							<td>个人</td>
						</c:when>
						<c:when test="${item.userType eq 1}">
							<td>企业</td>
						</c:when>
						<c:when test="${item.userType eq 2}">
							<td>种养户</td>
						</c:when>
						<c:when test="${item.userType eq 3}">
							<td>机关</td>
						</c:when>
						<c:when test="${item.userType eq 4}">
							<td>专家</td>
						</c:when>
						<c:when test="${item.userType eq 5}">
							<td>乡村</td>
						</c:when>
						<c:when test="${item.userType eq 10}">
							<td>管理员</td>
						</c:when>
					</c:choose>--%>

				<td>${item.expertType}</td>
				<td><c:if test="${item.adeptSpecies!=null&&item.adeptSpecies!=' '}">
					${item.adeptSpecies}
					</c:if>
					<c:if test="${item.adeptSpecies==''}">
							暂无数据
					</c:if>
				 </td>

				<c:choose>
					<c:when test="${item.approveStatus eq 0}">
					<td>待审核</td>
					</c:when>
					<c:when test="${item.approveStatus eq 1}">
					<td>审核通过</td>
					</c:when>
					<c:when test="${item.approveStatus eq 2}">
					<td>审核拒绝</td>
					</c:when>
				</c:choose>

				<td>
					<!--<a href="${ctx}/nswy/expertApplyForm?applyId=${item.expertId}">修改</a>-->
					<a href="${ctx}/nswy/deleteExpertApply?applyId=${item.expertId}" onclick="return confirm('确认要删除该角色吗？')">删除</a>
					<c:if test="${item.approveStatus eq 0}">
						<a href="${ctx}/nswy/expertApprove?applyId=${item.expertId}">审核</a>
					</c:if>

				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>