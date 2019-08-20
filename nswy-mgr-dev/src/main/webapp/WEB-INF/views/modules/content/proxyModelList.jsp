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
            var level = ${level};

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
		<a href="${ctx}/nswy/proxyModel/control">模版列表</a>
	</li>
	<li>
		<a href="${ctx}/nswy/proxyModel/add">模版上传</a>
	</li>

</ul>
<form:form id="searchForm"  modelAttribute="proxyModelInfo"  action="${ctx}/nswy/proxyModel/control" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize"x type="hidden" value="${page.pageSize}"/>
	<label>模版名称：</label><form:input path="fileName" htmlEscape="false" maxlength="50" class="input-medium"/>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="50%">模版名称</th>
				<th width="30%">模版类型</th>

				<shiro:hasPermission name="sys:menu:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>

				<td title="${item.fileName}"><a href="${item.filePdf}" target= _blank>${item.fileName}</a> </td>
				<td title="${item.fileTypeName}">${item.fileTypeName}</td>

				<shiro:hasPermission name="sys:menu:edit">
					<td nowrap>
						<a href="${ctx}/nswy/proxyModel/update?fileId=${item.fileId}">修改</a>
						<a href="${ctx}/nswy/proxyModel/delete?fileId=${item.fileId}">删除</a>
					</td>

                </shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<div class="pagination">${page}</div>
</body>
</html>