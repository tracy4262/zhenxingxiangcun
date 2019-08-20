<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>资讯审核</title>
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
<form:form id="searchForm"  modelAttribute="informationInfo"  action="${ctx}/nswy/information/examine" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label class="control-label">审核状态:</label>
    <form:select path="approveStatus" class="input-medium">
        <form:option value=" ">全部</form:option>
        <form:option value="0">待审核</form:option>
        <form:option value="2">已审核</form:option>
        <form:option value="3">审核拒绝</form:option>
    </form:select>
    <label>标题：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th>标题</th>
        <th>摘要</th>
        <th>资讯分类</th>
        <th>审核状态</th>
        <th>发布时间</th>
        <%--<th>无忧首页是否显示</th>
        <th>无忧导航是否显示</th>
        <th>排序</th>--%>
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
                <c:if test="${fn:length(item.abstracts) > 20 }">
                    ${fn:substring(item.abstracts,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.abstracts) < 21 }">
                    ${item.abstracts}
                </c:if>
            </td>
            <td>${item.docType}</td>
            <td>${item.approveStatus}</td>
            <td>${item.createTime}</td>
                <%-- <td>
                   <input type="checkbox" id="isHomeDisplay${item.id}" onclick="isHomeDisplay('${item.id}')" value="" ${item.isToPage eq '0' ? 'checked' : ''} />
                </td>
                <td>
                    <input type="checkbox" id="isNavDisplay${item.id}" onclick="isNavDisplay('${item.id}')" value="" ${item.isNavDisplay eq '0' ? 'checked' : ''} />
                </td>
                <td>
                    <input type="text" id="orderNum${item.id}" class="orderNum" onchange="setOrderNum('${item.id}')" value="${item.orderNum}" />
                </td>--%>
            <td>
                <a href="javascript:void(0);" onclick="del('${item.id}')">删除</a>
                <c:if test="${item.approveStatus eq '待审核'}">
                    <a href="javascript:void(0);" onclick="adopt('${item.id}')">审核通过</a>
                    <a href="javascript:void(0);" onclick="refuse('${item.id}')">拒绝</a>
                </c:if>
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
                url : '${ctx}/nswy/information/delete',
                data : {
                    informationInfoId : id
                },
                success : function () {
                    refresh();
                }
            });
        }
    }
    function adopt(id) {
        debugger
        var  confirmStatus = confirm("确定通过审核吗？")
        if (confirmStatus) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/information/adopt',
                data : {
                    informationInfoId : id
                },
                success : function () {
                    refresh();
                }
            });
        }
    }
    function refuse(id) {
        var  confirmStatus = confirm("确定拒绝吗？")
        if (confirmStatus) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/information/refuse',
                data : {
                    informationInfoId : id
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
                url : '${ctx}/nswy/information/update-home',
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
                url : '${ctx}/nswy/information/update-nav-display',
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
            url : '${ctx}/nswy/information/set-order-num',
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