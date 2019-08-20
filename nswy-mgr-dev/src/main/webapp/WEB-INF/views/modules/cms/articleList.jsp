<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cms/article/?category.id=${article.category.id}">文章列表</a></li>
		<shiro:hasPermission name="cms:article:edit"><li><a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="article" action="${ctx}/cms/article/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>栏目：</label><sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
					title="栏目" url="/cms/category/treeData" module="article" notAllowSelectRoot="false" cssClass="input-small"/>
		<label>标题：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		<label>状态：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('cms_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>栏目</th><th>标题</th><!--<th>权重</th><th>点击数</th>--><th>发布者</th><th>更新时间</th><th>状态</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="article">
			<tr>
				<td><a href="javascript:" onclick="$('#categoryId').val('${article.category.id}');$('#categoryName').val('${article.category.name}');$('#searchForm').submit();return false;">${article.category.name}</a></td>
				<td><a href="${ctx}/cms/article/form?id=${article.id}" title="${article.title}">${fns:abbr(article.title,40)}</a></td>
				<!--<td>${article.weight}</td>
				<td>${article.hits}</td>-->
				<td>${article.user.name}</td>
				<td><fmt:formatDate value="${article.updateDate}" type="both"/></td>
				<c:choose>
				  <c:when test="${article.delFlag eq 0}">
					<td>待审核</td>
				  </c:when>
				  <c:when test="${article.delFlag eq 1}">
					<td>已删除</td>
				  </c:when>
				   <c:when test="${article.delFlag eq 2}">
					<td>已审核</td>
				  </c:when>
				  <c:otherwise>
					<td>审核拒绝</td>
				  </c:otherwise>
				</c:choose>
				<td>
					<shiro:hasPermission name="cms:article:edit">
	    				<a href="${ctx}/cms/article/form?id=${article.id}">查看</a>
	    				<shiro:hasPermission name="cms:article:audit">
							<a href="${ctx}/cms/article/delete?id=${article.id}${article.delFlag ne 1?'&isRe=true':''}&categoryId=${article.category.id}" onclick="return confirmx('确认要删除该文章吗？', this.href)" >删除</a>
						</shiro:hasPermission>
					</shiro:hasPermission>
					<c:if test="${article.delFlag eq 0}">
						<a href="${ctx}/cms/article/articleAuditForm?id=${article.id}" onclick="return confirmx('确认要审核该文章吗？', this.href)" >审核</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>