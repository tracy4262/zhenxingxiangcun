<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>内容审核 - 简讯</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function page(n,s){
            if(n) $("#pageNo").val(n);
            if(s) $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
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
<form:form id="searchForm"  modelAttribute="newsletterInfo"  action="${ctx}/nswy/newsletterList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    标题：<input type="text" name="title" maxlength="8" value="${title}"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th width="20%">标题</th>
        <th width="40%">摘要</th>
        <th>无忧导航是否显示</th>
        <th>排序</th>
        <shiro:hasPermission name="sys:role:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    <c:forEach items="${page.list}" var="item">
        <tr>
            <td title="${item.title}" width="50px">
                <c:if test="${fn:length(item.title) > 20 }">
                    ${fn:substring(item.title,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.title) < 21 }">
                    ${item.title}
                </c:if>
            </td>
            <td title="${item.abstracts}" width="50px">
                <c:if test="${fn:length(item.abstracts) > 40 }">
                    ${fn:substring(item.abstracts,0,40)}...
                </c:if>
                <c:if test="${fn:length(item.abstracts) < 41 }">
                    ${item.abstracts}
                </c:if>
            </td>
            <td>
                <input type="checkbox" id="isNavDisplay${item.id}" value="" ${item.isNavDisplay eq '0' ? 'checked' : ''} onclick="isNavDisplay('${item.id}','${item.type}')"/>
            </td>
            <td>
                <input type="text" id="orderNum${item.id}" class="orderNum" onchange="setOrderNum('${item.id}','${item.type}')" value="${item.orderNum}" />
            </td>
            <td>
                <a href="${ctx}/nswy/deleteNewsletter?id=${item.id}&type=${item.type}" onclick="return del()">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="pagination">${page}</div>
<script>
    //设置是否在无忧导航显示
    function isNavDisplay(id,type) {
        var status = $("#isNavDisplay" + id).is(":checked") ? '0' : '1';
        var text = status == '0' ? "是否设置该数据到无忧导航显示？" : "是否取消该数据到无忧导航显示？"
        var  confirmStatus = confirm(text)
        if (confirmStatus == true) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/newsletterUpdateNavDisplay',
                data : {
                    status : status,
                    id : id,
                    type : type
                },
                success : function () {
                    <%--window.location.href="${ctx}/nswy/newsletterList";--%>
                    refresh();
                }
            });
        }
    }

    function del() {
        return confirm("确定删除吗？")
    }
    function setOrderNum(id,type) {
        debugger
        var orderNum = $("#orderNum"+id).val();
        $.ajax({
            type : 'POST',
            url : '${ctx}/nswy/newsletterSetOrderNum',
            data : {
                orderNum : orderNum,
                id : id,
                type : type
            },
            success : function () {
                <%--window.location.href="${ctx}/nswy/newsletterList";--%>
                refresh();
            }
        });
    }

    function refresh() {
        $("#btnSubmit").click;
    }
</script>
</body>
</html>