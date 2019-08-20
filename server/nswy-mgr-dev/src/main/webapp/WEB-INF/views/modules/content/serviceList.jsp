<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>内容控制——服务</title>
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
<form:form id="searchForm"  modelAttribute="nswyServiceInfo"  action="${ctx}/nswy/nswyService/control" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>服务名称：</label><form:input path="serviceName" htmlEscape="false" maxlength="50" class="input-medium"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th>服务名称</th>
        <th>服务分类</th>
        <th>联系人</th>
        <th>联系电话</th>
        <th>发布时间</th>
        <th>无忧首页是否显示</th>
        <th>排序</th>
        <shiro:hasPermission name="sys:role:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    <c:forEach items="${page.list}" var="item">
        <tr>
            <td title="${item.serviceName}" width="50px">
                <c:if test="${fn:length(item.serviceName) > 20 }">
                    ${fn:substring(item.serviceName,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.serviceName) < 21 }">
                    ${item.serviceName}
                </c:if>
            </td>
            <td title="${item.serviceClassId}" width="50px">
                <c:if test="${fn:length(item.serviceClassId) > 20 }">
                    ${fn:substring(item.serviceClassId,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.serviceClassId) < 21 }">
                    ${item.serviceClassId}
                </c:if>
            </td>
            <td>${item.contactName}</td>
            <td>${item.phone}</td>
            <td>${item.createTime}</td>
            <td width="50px">
                       <input type="checkbox" id="isHomeDisplay${item.id}" onclick="isHomeDisplay('${item.id}')" value="" ${item.isToPage eq '0' ? 'checked' : ''} />
                    </td>

                    <td width="50px">
                        <input type="text" id="orderNum${item.id}" class="orderNum" onchange="setOrderNum('${item.id}')" value="${item.orderNum}" />
                    </td>
            <td>
                <a href="javascript:void(0);" onclick="del('${item.id}')">删除</a>

            </td>
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
    function del(id) {
        var  confirmStatus = confirm("确定删除吗？")
        if (confirmStatus) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/nswyService/delete',
                data : {
                    id : id
                },
                success : function () {
                    refresh();
                }
            });
        }
    }

    //设置是否在无忧首页显示
    function isHomeDisplay(id) {
        debugger
        var status = $("#isHomeDisplay" + id).is(":checked") ? '0' : '1';
        var text = status == '0' ? "是否设置该数据到无忧首页显示？" : "是否取消该数据到无忧首页显示？"
        var  confirmStatus = confirm(text)
        if (confirmStatus == true) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/nswyService/update-home',
                data : {
                    status : status,
                    id : id
                },
                success : function () {
                    refresh();
                }
            })
        }
    }

    //设置是否在无忧导航显示
    function isNavDisplay(id) {
        var status = $("#isNavDisplay" + id).is(":checked") ? '0' : '1';
        var text = status == '0' ? "是否设置该数据到无忧导航显示？" : "是否取消该数据到无忧导航显示？"
        var  confirmStatus = confirm(text)
        if (confirmStatus == true) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/nswyService/update-nav-display',
                data : {
                    status : status,
                    id : id
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
            url : '${ctx}/nswy/nswyService/set-order-num',
            data : {
                orderNum : orderNum,
                id : id
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