<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>机关审核管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function page(n,s){
            if(n) $("#pageNo").val(n);
            if(s) $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        function del() {
            return confirm("确定删除吗？")
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
                    url : '${ctx}/nswy/government/update-home',
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
                    url : '${ctx}/nswy/government/update-nav-display',
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
                url : '${ctx}/nswy/government/set-order-num',
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
</head>
<body>
<form:form id="searchForm"  modelAttribute="nswyGovInfo"  action="${ctx}/nswy/government/control" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    机关名称：<input type="text" name="govName" maxlength="8" value="${govName}"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th>机关名称</th>
        <th>机构代码证号</th>
        <th>机构类型</th>
        <th>机构级别</th>
        <th>机关地址</th>
        <th>无忧首页是否显示</th>
        <th>无忧导航是否显示</th>
        <th>排序</th>
        <shiro:hasPermission name="sys:role:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    <c:forEach items="${page.list}" var="item">
        <tr>
            <td>${item.govName}</td>
            <td>${item.organizationCode}</td>
            <td>${item.govType}</td>
            <td>${item.govLevel}</td>
            <td>${item.location}</td>
           <%-- <td>${item.isHomeDisplay}</td>
            <td>${item.isNavDisplay}</td>
            <td>${item.orderNum}</td>--%>
            <td>
                <input type="checkbox" id="isHomeDisplay${item.govInfoId}" onclick="isHomeDisplay('${item.govInfoId}')" value="" ${item.isToPage eq '0' ? 'checked' : ''} />
            </td>
            <td>
                <input type="checkbox" id="isNavDisplay${item.govInfoId}" onclick="isNavDisplay('${item.govInfoId}')" value="" ${item.isNavDisplay eq '0' ? 'checked' : ''} />
            </td>
            <td>
                <input type="text" id="orderNum${item.govInfoId}" class="orderNum" onchange="setOrderNum('${item.govInfoId}')" value="${item.orderNum}" />
            </td>
            <td>
                <a href="${ctx}/nswy/governmentDelete?govInfoId=${item.govInfoId}" onclick="return del()">删除</a>

            </td>
        </tr>
    </c:forEach>
</table>
<div class="pagination">${page}</div>
</body>
</html>