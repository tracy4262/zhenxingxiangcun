<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内容控制-单位管理</title>
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
		<a href="${ctx}/nswy/unit/control">单位列表</a>
	</li>
	<li>
		<a href="${ctx}/nswy/unit/add">单位添加</a>
	</li>

</ul>
<form:form id="searchForm"  modelAttribute="unitInfo"  action="${ctx}/nswy/unit/control" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<label>单位名称：</label><form:input path="unitName" htmlEscape="false" maxlength="50" class="input-medium"/>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<tr>
		<th width="20%">单位名称</th>
		<th width="20%">单位类型</th>
		<th width="10%">计量单位</th>
		<th width="10%">系数</th>
		<shiro:hasPermission name="sys:role:edit">
			<th>操作</th>
		</shiro:hasPermission>
	</tr>
	<c:forEach items="${page.list}" var="item">
		<tr>
			<td title="${item.unitName}">${item.unitName}</td>
			<td title="${item.unitTypeName}">${item.unitTypeName}</td>

			<td title="${item.calculateUnit}">${item.calculateUnit}</td>
			<td title="${item.modulus}">${item.modulus}</td>

			<shiro:hasPermission name="sys:menu:edit">
				<td nowrap>
					<a href="${ctx}/nswy/unit/update?unitId=${item.unitId}">修改</a>
					<a href="${ctx}/nswy/unit/delete?unitId=${item.unitId}">删除</a>
				</td>
			</shiro:hasPermission>
		</tr>
	</c:forEach>
</table>
<div class="pagination">${page}</div>
</body>
</html>

