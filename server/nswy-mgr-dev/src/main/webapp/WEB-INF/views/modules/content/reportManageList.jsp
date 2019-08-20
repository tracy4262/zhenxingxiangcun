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
		<a href="${ctx}/nswy/reportManage/control">报表列表</a>
	</li>
	<li>
		<a href="${ctx}/nswy/reportManage/add">报表添加</a>
	</li>

	<li><a href="${ctx}/nswy/reportManageType/control">报表类型列表</a></li>
	<li><a href="${ctx}/nswy/reportManageType/add">报表类型添加</a></li>
</ul>
<form:form id="searchForm"  modelAttribute="reportManageInfo"  action="${ctx}/nswy/reportManage/control" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<label>报表类型：</label><form:input path="reportTypeName" htmlEscape="false" maxlength="50" class="input-medium"/>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="20%">报表类型</th>
				<th width="30%">适用用户</th>
				<th width="20%">报表名称</th>
				<th width="30%">报表描述</th>
				<th width="30%">文件预览</th>
				<shiro:hasPermission name="sys:menu:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td title="${item.reportTypeName}">${item.reportTypeName}</td>
				<td title="${item.userTypeName}">${item.userTypeName}</td>
				<td title="${item.reportName}">${item.reportName}</td>
				<td title="${item.reportRemark}">
					<c:if test="${fn:length(item.reportRemark) > 40 }">
						${fn:substring(item.reportRemark,0,40)}...
					</c:if>
					<c:if test="${fn:length(item.reportRemark) < 41 }">
						${item.reportRemark}
					</c:if>
                </td>
				<td title="${item.reportFilePdf}"><a href="${item.reportFilePdf}" target= _blank>${item.reportFileName}</a> </td>
				<shiro:hasPermission name="sys:menu:edit">
					<td nowrap>
						<a href="${ctx}/nswy/reportManage/update?reportId=${item.reportId}">修改</a>
						<a href="${ctx}/nswy/reportManage/delete?reportId=${item.reportId}"
						   onclick="return confirm('是否删除？')">删除</a>
					</td>
                </shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

</body>
</html>