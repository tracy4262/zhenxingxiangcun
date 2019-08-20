<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>通用商品服务</title>
    <meta name="decorator" content="default"/>
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
        <a href="${ctx}/nswy/commodity/control">商品列表</a>
    </li>
    <li>
        <a href="${ctx}/nswy/commodity/add">添加商品</a>
    </li>

</ul>

<form:form id="searchForm"  modelAttribute="commodityInfo"  action="${ctx}/nswy/commodity/control" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>商品名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
    <tr>
        <th width="15%">通用商品名</th>
        <th width="10%">商品别名</th>
        <th width="20%">行业分类</th>
        <th width="20%">产品分类</th>
        <th width="15%">备注</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${page.list}" var="item">
        <tr>
            <td title="${item.name}" width="50px">
                <c:if test="${fn:length(item.name) > 20 }">
                    ${fn:substring(item.name,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.name) < 21 }">
                    ${item.name}
                </c:if>
            </td>

            <td title="${item.alias}" width="50px">
                <c:if test="${fn:length(item.alias) > 20 }">
                    ${fn:substring(item.alias,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.alias) < 21 }">
                    ${item.alias}
                </c:if>
            </td>

            <td title="${item.industryTypeName}" width="50px">
                <c:if test="${fn:length(item.industryTypeName) > 20 }">
                    ${fn:substring(item.industryTypeName,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.industryTypeName) < 21 }">
                    ${item.industryTypeName}
                </c:if>
            </td>

            <td title="${item.productTypeName}" width="50px">
                <c:if test="${fn:length(item.productTypeName) > 20 }">
                    ${fn:substring(item.productTypeName,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.productTypeName) < 21 }">
                    ${item.productTypeName}
                </c:if>
            </td>

            <td title="${item.remark}" width="50px">
                <c:if test="${fn:length(item.remark) > 20 }">
                    ${fn:substring(item.remark,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.remark) < 21 }">
                    ${item.remark}
                </c:if>
            </td>

            <shiro:hasPermission name="sys:menu:edit"><td>
                <a href="${ctx}/nswy/commodity/update?id=${item.commodityId}">修改</a>
                <a href="${ctx}/nswy/commodity/delete?id=${item.commodityId}" onclick="return confirmx('是否删除商品？', this.href)">删除</a>
                </td></shiro:hasPermission>
        </tr>
    </c:forEach>
</table>
<div class="pagination">${page}</div>
<script type="text/javascript">
    function page(n,s){
        if(n) $("#pageNo").val(n);
        if(s) $("#pageSize").val(s);
        $("#searchForm").submit();
        return false;
    }

</script>
</body>
</html>