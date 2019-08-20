<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内容控制-服务分类</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        function page(n,s){
            if(n) $("#pageNo").val(n);
            if(s) $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        $(document).ready(function() {

            $("#treeTable").treeTable({expandLevel : 3}).show();

        });


        function refresh() {
            $("#btnSubmit").click();
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
		<a href="${ctx}/nswy/serviceType/control">服务列表</a>
	</li>
	<li>
		<a href="${ctx}/nswy/serviceType/add">服务添加</a>
	</li>

</ul>
<form:form id="searchForm"  modelAttribute="serviceTypeInfo"  action="${ctx}/nswy/serviceType/control" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<label>服务名称：</label><form:input path="dictName" htmlEscape="false" maxlength="50" class="input-medium"/>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<tr>
		<th width="40%">服务名称</th>

		<shiro:hasPermission name="sys:role:edit">
			<th>操作</th>
		</shiro:hasPermission>
	</tr>
	<c:forEach items="${page.list}" var="item">
		<tr>
			<td title="${item.dictName}">${item.dictName}</td>


			<shiro:hasPermission name="sys:menu:edit">
				<td nowrap>
					<a href="${ctx}/nswy/serviceType/update?id=${item.id}">修改</a>
					<a href="${ctx}/nswy/serviceType/delete?id=${item.id}">删除</a>
				</td>
			</shiro:hasPermission>
		</tr>
	</c:forEach>
</table>
<div class="pagination">${page}</div>
</body>
</html>

