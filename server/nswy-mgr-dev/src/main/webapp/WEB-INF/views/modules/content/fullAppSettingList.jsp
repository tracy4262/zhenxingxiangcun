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
            if(level===0){
                $("#full1").addClass("active");
            }else if(level===1){
                $("#full2").addClass("active");
            }else if(level===2){
                $("#full3").addClass("active");
            }else if(level===3){
                $("#full4").addClass("active");
            }
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
	<li id="full1">
		<a href="${ctx}/nswy/fullAppSetting/control?level=0">基础应用</a>
	</li>
	<li id="full2">
		<a href="${ctx}/nswy/fullAppSetting/control?level=1">通用应用</a>
	</li>
	<li id="full3">
		<a href="${ctx}/nswy/fullAppSetting/control?level=2">高级应用</a>
	</li>
    <li id="full4">
        <a href="${ctx}/nswy/fullAppSetting/control?level=3">服务应用</a>
    </li>
</ul>
<form:form id="searchForm"  modelAttribute="fullAppSettingInfo"  action="${ctx}/nswy/fullAppSetting/control?level=${level}" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<label>应用名称：</label><form:input path="appName" htmlEscape="false" maxlength="50" class="input-medium"/>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="15%">应用名称</th>
				<th width="30%">应用简介</th>
				<c:if test="${level eq 2}">
					<th width="10%">适用对象</th>
				</c:if>

				<th width="10%">是否免费</th>
				<th width="10%">是否推荐</th>
				<th width="20%">应用Logo</th>
				<%--<th width="20%">应用截图</th>--%>
				<shiro:hasPermission name="sys:menu:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td title="${item.appName}">${item.appName}</td>
				<td title="${item.applicationAbstract}">
					<c:if test="${fn:length(item.applicationAbstract) > 40 }">
						${fn:substring(item.applicationAbstract,0,40)}...
					</c:if>
					<c:if test="${fn:length(item.applicationAbstract) < 41 }">
						${item.applicationAbstract}
					</c:if>
				</td>
				<c:if test="${level eq 2}">
					<td title="${item.userTypeName}">${item.userTypeName}</td>
				</c:if>

				<td>
						<c:if test="${item.cost eq 0 or item.cost eq '' }">
							是
						</c:if>
						<c:if test="${item.cost eq 1 }">
							否
						</c:if>
				</td>
				<td>

							<c:if test="${item.recommend eq 1 or item.recommend eq '' }">
								是
							</c:if>
							<c:if test="${item.recommend eq 0 }">
								否
							</c:if>
				</td>
				<%--<td title="${item.logoName}"><a href="${item.logo}" target= _blank>${item.logoName}</a> </td>--%>
				<td title="${item.logoName}"> <img src="${item.logo}"/></td>
				<%--<td title="${item.imageName}"><img src="${item.image}"/> </td>--%>
				<shiro:hasPermission name="sys:menu:edit">
					<td nowrap>
						<a href="${ctx}/nswy/fullAppSetting/update?appSettingId=${item.appSettingId}">修改</a>
					</td>
                </shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<div class="pagination">${page}</div>
</body>
</html>