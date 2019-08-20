<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>服务</title>
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
        <a href="${ctx}/nswy/generalService/control">通用服务</a>
    </li>
    <li >
        <a href="${ctx}/nswy/generalService/add">添加服务</a>
    </li>

</ul>

<form:form id="searchForm"  modelAttribute="generalServiceInfo"  action="${ctx}/nswy/generalService/control" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>服务名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
    <tr>
        <th width="20%">服务名</th>
        <th width="20%">行业分类</th>
        <th width="20%">备注</th>
        <th width="20%">操作</th>
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

            <td title="${item.industryTypeName}" width="50px">
                <c:if test="${fn:length(item.industryTypeName) > 20 }">
                    ${fn:substring(item.industryTypeName,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.industryTypeName) < 21 }">
                    ${item.industryTypeName}
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
                <a href="${ctx}/nswy/generalService/update?id=${item.serviceId}">修改</a>

                <a href="${ctx}/nswy/generalService/delete?id=${item.serviceId}" onclick="return confirmx('是否删除？', this.href)">删除</a>
            </td></shiro:hasPermission>
        </tr>
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

    //设置是否在无忧首页显示
    function isHomeDisplay(id) {
        debugger
        var status = $("#isHomeDisplay" + id).is(":checked") ? '1' : '0';
        var text = status == '0' ? "是否设置该数据到无忧首页显示？" : "是否取消该数据到无忧首页显示？"
        var  confirmStatus = confirm(text)
        if (confirmStatus == true) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/shopCommodity/updateHome',
                data : {
                    status : status,
                    shopId : id
                },
                success : function () {
                    refresh();
                }
            })
        }
    }

    //设置是否在无忧导航显示
    function isNavDisplay(id) {
        var status = $("#isNavDisplay" + id).is(":checked") ? '1' : '0';
        var text = status == '1' ? "是否设置该数据到无忧导航显示？" : "是否取消该数据到无忧导航显示？"
        var  confirmStatus = confirm(text)
        if (confirmStatus == true) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/shopCommodity/updateNavDisplay',
                data : {
                    status : status,
                    shopId : id
                },
                success : function () {
                    refresh();
                }
            })
        }
    }

    //设置是否在商城显示
    function isShopDisplay(id) {
        var status = $("#isShopDisplay" + id).is(":checked") ? '1' : '0';
        var text = status == '1' ? "是否设置该数据到商城显示？" : "是否取消该数据到商城显示？"
        var  confirmStatus = confirm(text)
        if (confirmStatus == true) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/shopCommodity/updateShopDisplay',
                data : {
                    status : status,
                    shopId : id
                },
                success : function () {
                    refresh();
                }
            })
        }
    }

    function setOrderNum(id) {
        debugger
        var orderNum = $("#orderNum"+id).val();
        $.ajax({
            type : 'POST',
            url : '${ctx}/nswy/shopCommodity/updateOrderNum',
            data : {
                orderNum : orderNum,
                shopId : id
            },
            success : function () {
                refresh();
            }
        });
    }
    function refresh() {
        $("#btnSubmit").click();
    }
</script>
</body>
</html>