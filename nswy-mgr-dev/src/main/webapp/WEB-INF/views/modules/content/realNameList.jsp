<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>

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
<ul class="nav nav-tabs">
	<li class="active">
		<a href="${ctx}/nswy/realName/control">实名认证</a>
	</li>

</ul>
<form:form id="searchForm"  modelAttribute="realName"  action="${ctx}/nswy/realName/control" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<label>用户名称：</label><form:input path="userId" htmlEscape="false" maxlength="50" class="input-medium"/>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户</th>
                <th>会员全称</th>
                <th>会员简称</th>
				<th>认证状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>

				<td title="${item.userId}">${item.userId} </td>
                <td title="${item.memberName}">${item.memberName} </td>
                <td title="${item.memberAbbreviation}">${item.memberAbbreviation} </td>
				<td ><c:if test="${item.status eq 1}">
					官方账号
				</c:if></td>
				<shiro:hasPermission name="sys:menu:edit">
					<td nowrap>
						<c:choose>
							<c:when test="${item.status eq 1}">
								<a href="${ctx}/nswy/realName/delete?userId=${item.userId}"
								   onclick="return confirm('是否取消该官方账号？')">取消认证</a>
							</c:when>
							<c:when test="${item.status eq 0}">
								<a href="${ctx}/nswy/realName/update?userId=${item.userId}"
								   onclick="return confirm('是否将该账户认证为官方账号？')">认证</a>
							</c:when>
							<c:otherwise>
								<a href="${ctx}/nswy/realName/add?userId=${item.userId}"
								   onclick="return confirm('是否将该账户认证为官方账号？')">认证</a>
							</c:otherwise>
						</c:choose>
						<a href="${ctx}/nswy/realName/from?realId=${item.realId}">查看</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<div class="pagination">${page}</div>
</body>
</html>