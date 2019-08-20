<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物种审核管理</title>
	<meta name="decorator" content="default"/>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
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
<form:form id="searchForm"  modelAttribute="speciesInfo"  action="${ctx}/nswy/getSpeciesList" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<label>物种名称：</label><form:input path="fname" htmlEscape="false" maxlength="50" class="input-medium"/>
	<label class="control-label">审核状态:</label>
	<form:select path="auditstatus" class="input-medium">
		<form:option value="6">全部</form:option>
		<form:option value="0">更新待审核</form:option>
		<form:option value="1">审核通过</form:option>
		<form:option value="2">新增待审核</form:option>
		<form:option value="3">删除待审核</form:option>
		<form:option value="4">未审核通过</form:option>

	</form:select>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<tr><th>物种名称</th><th>汉语拼音</th><th>审核状态</th><th>更新时间</th><th>创建者</th><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr>
	<c:forEach items="${page.list}" var="item">
		<tr>
			<td><a href="${ctx}/nswy/speciesForm?indexid=${item.indexid}">${item.fname}</a></td>
			<td><a href="${ctx}/nswy/speciesForm?indexid=${item.indexid}">${item.fpinyin}</a></td>
			<c:choose>
              <c:when test="${item.auditstatus eq 0}">
                <td>更新待审核</td>
              </c:when>
              <c:when test="${item.auditstatus eq 1}">
                <td>审核通过</td>
              </c:when>
               <c:when test="${item.auditstatus eq 2}">
                <td>新增待审核</td>
              </c:when>
				<c:when test="${item.auditstatus eq 3}">
					<td>删除待审核</td>
				</c:when>
				<c:when test="${item.auditstatus eq 4}">
					<td>未审核通过</td>
				</c:when>
            </c:choose>
			<td>${item.updatetime}</td>
			<td>${item.displayName}</td>
			<td>
				<a href="${ctx}/nswy/speciesForm?indexid=${item.indexid}">查看</a>
			</td>
		</tr>
	</c:forEach>
</table>
<div class="pagination">${page}</div>
</body>

</html>