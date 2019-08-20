<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理-字典目录</title>
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
		<a href="${ctx}/nswy/dictionary/control">字典目录</a>
	</li>

</ul>
<form:form id="searchForm"  modelAttribute="dictionaryManageInfo"  action="${ctx}/nswy/dictionary/control" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<label>字典名称：</label><form:input path="dictName" htmlEscape="false" maxlength="50" class="input-medium"/>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<tr>
		<th width="20%">字典名称</th>
		<th width="40%">说明</th>

		<shiro:hasPermission name="sys:role:edit">
			<th>操作</th>
		</shiro:hasPermission>
	</tr>
	<c:forEach items="${page.list}" var="item">
		<tr>
			<td title="${item.dictName}">${item.dictName}</td>
			<td title="${item.remark}">
				<c:if test="${fn:length(item.remark) > 40 }">
					${fn:substring(item.remark,0,40)}...
				</c:if>
				<c:if test="${fn:length(item.remark) < 41 }">
					${item.remark}
				</c:if>
			</td>


			<shiro:hasPermission name="sys:menu:edit">
				<td nowrap>
					<a href="${ctx}/nswy/dictionary/update?dictId=${item.dictId}">修改</a>
					<a href="${ctx}/nswy/dictionary/delete?dictId=${item.dictId}" onclick="return confirmx('确定删除吗？', this.href)">删除</a>
					<a href="${ctx}/${item.url}">字典明细</a>
				</td>
			</shiro:hasPermission>
		</tr>
	</c:forEach>
</table>
<div class="pagination">${page}</div>
</body>
</html>

