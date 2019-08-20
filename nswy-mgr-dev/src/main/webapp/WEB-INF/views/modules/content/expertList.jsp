<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>专家审核管理</title>
    <meta name="decorator" content="default"/>
    <style>
        #contentTable tr td {
            text-overflow: ellipsis; /* for IE */
            -moz-text-overflow: ellipsis; /* for Firefox,mozilla */
            overflow: hidden;
            white-space: nowrap;
            text-align: left;
        }
    </style>
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
            var status = $("#isToPage" + id).is(":checked") ? '0' : '1';
            var text = status == '0' ? "是否设置该数据到无忧首页显示？" : "是否取消该数据到无忧首页显示？"
            var  confirmStatus = confirm(text)
            if (confirmStatus == true) {
                $.ajax({
                    type : 'POST',
                    url : '${ctx}/nswy/expert/update-home',
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
                    url : '${ctx}/nswy/expert/update-nav-display',
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
                url : '${ctx}/nswy/expert/set-order-num',
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
<form:form id="searchForm"  modelAttribute="loginUser"  action="${ctx}/nswy/expert/control" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    专家名称：<input type="text" name="displayName" maxlength="8" value="${displayName}"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th>专家名称</th>
        <th>专家分类</th>
        <th>擅长物种</th>
        <th>擅长领域</th>
        <th>相关行业</th>
        <th>相关服务</th>
        <th width="5%">无忧首页是否显示</th>
        <th width="5%">无忧导航是否显示</th>
        <th width="5%">排序</th>
        <shiro:hasPermission name="sys:role:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    <c:forEach items="${page.list}" var="item">
        <tr>
            <td>${item.displayName}</td>
            <td>${item.expertType}</td>
            <td>${item.adeptSpecies}</td>
            <td title="${item.adeptField}" width="50px">
            <c:if test="${fn:length(item.adeptField) > 20 }">
                ${fn:substring(item.adeptField,0,20)}...
            </c:if>
            <c:if test="${fn:length(item.adeptField) < 21 }">
                ${item.adeptField}
            </c:if>
            </td>
            <td>${item.relatedIndustry}</td>
            <td>${item.relatedService}</td>
            <td>
                <input type="checkbox" id="isToPage${item.id}" onclick="isHomeDisplay('${item.id}','${item.loginAccount}')" value="" ${item.isToPage eq '0' ? 'checked' : ''} />
            </td>
            <td>
                <input type="checkbox" id="isNavDisplay${item.id}" onclick="isNavDisplay('${item.id}')" value="" ${item.isNavDisplay eq '0' ? 'checked' : ''} />
            </td>
            <td>
                <input type="text" id="orderNum${item.id}" class="orderNum" onchange="setOrderNum('${item.id}')" value="${item.orderNum}" />
            </td>
            <td>
                <a href="${ctx}/nswy/expertDelete?id=${item.id}" onclick="return del()">删除</a>

            </td>
        </tr>
    </c:forEach>
</table>
<div class="pagination">${page}</div>
</body>
</html>