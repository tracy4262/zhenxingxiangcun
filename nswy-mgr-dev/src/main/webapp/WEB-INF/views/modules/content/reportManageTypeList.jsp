<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>


    <style>
        #treeTable tr td {
            text-overflow: ellipsis; /* for IE */
            -moz-text-overflow: ellipsis; /* for Firefox,mozilla */
            overflow: hidden;
            white-space: nowrap;
            text-align: left;
        }
    </style>
	<script type="text/javascript">
        $(document).ready(function() {
            $("#treeTable").treeTable({expandLevel : 3}).show();
        });
        function updateSort() {
            loading('正在提交，请稍等...');
            $("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
            $("#listForm").submit();
        }

	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/nswy/reportManage/control">报表列表</a></li>
	<li><a href="${ctx}/nswy/reportManage/add">报表添加</a></li>
	<li class="active">
		<a href="${ctx}/nswy/reportManageType/control">报表类型列表</a>
	</li>
	<li>
		<a href="${ctx}/nswy/reportManageType/add">报表类型添加</a>
	</li>

</ul>
<form:form id="searchForm"  modelAttribute="reportManageTypeInfo"  action="${ctx}/nswy/reportManageType/control" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<label>报表类型：</label><form:input path="reportTypeName" htmlEscape="false" maxlength="50" class="input-medium"/>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="20%">报表类型</th>
				<th width="20%">报表类型名称</th>
				<shiro:hasPermission name="sys:menu:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td title="${item.reportType}">${item.reportType}</td>

				<td title="${item.reportTypeName}">${item.reportTypeName}</td>


				<shiro:hasPermission name="sys:menu:edit">
					<td nowrap>
						<a href="${ctx}/nswy/reportManageType/update?reportId=${item.reportId}">修改</a>
						<a href="${ctx}/nswy/reportManageType/delete?reportId=${item.reportId}"
						   onclick="return confirm('是否删除？')">删除</a>
					</td>
                </shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</form>
</body>
</html>