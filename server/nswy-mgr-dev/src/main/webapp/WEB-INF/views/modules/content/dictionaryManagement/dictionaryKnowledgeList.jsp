<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内容控制-知识管理</title>
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
		<a href="${ctx}/nswy/dictionaryKnowledge/control">知识</a>
	</li>


</ul>
<form:form id="searchForm"  modelAttribute="dictionaryKnowledge"  action="${ctx}/nswy/dictionaryKnowledge/control" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>知识名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    <%--<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/nswy/dictionary/control'"/>--%>
    <a href="${ctx}/nswy/dictionary/control"><input id="btnCancel" class="btn" type="button" value="返 回"/></a>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<tr>
		<th width="10%">序号</th>
		<th width="20%">类别名称</th>
		<th width="15%">分类级别</th>
		<th width="15%">编码</th>
		<th width="30%">说明</th>
		<shiro:hasPermission name="sys:role:edit">
			<th>操作</th>
		</shiro:hasPermission>
	</tr>
	<c:forEach items="${page.list}" var="item" varStatus = "i">
		<tr>
			<td >${i.count+(page.pageNo-1)*page.pageSize}</td>
			<td title="${item.name}">${item.name}</td>
			<td title="${item.level}">${item.level}</td>
			<td title="${item.value}">${item.value}</td>
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
					<a href="${ctx}/nswy/dictionaryKnowledge/addPid?pid=${item.pid}&level=${item.level}">增加同级</a>
					<a href="${ctx}/nswy/dictionaryKnowledge/add?id=${item.id}&level=${item.level}">增加下级</a>
					<a href="${ctx}/nswy/dictionaryKnowledge/update?id=${item.id}">修改</a>
					<a href="${ctx}/nswy/dictionaryKnowledge/delete?id=${item.id}" onclick="return confirmx('确定删除吗？', this.href)">删除</a>
				</td>
			</shiro:hasPermission>
		</tr>
	</c:forEach>
</table>
<div class="pagination">${page}</div>
</body>
</html>

