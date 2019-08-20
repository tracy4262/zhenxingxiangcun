<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>内容控制 -标准</title>
    <meta name="decorator" content="default"/>
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
            var status = $("#isHomeDisplay" + id).is(":checked") ? '0' : '1';
            var text = status == '0' ? "是否设置该数据到无忧首页显示？" : "是否取消该数据到无忧首页显示？"
            var  confirmStatus = confirm(text)
            if (confirmStatus == true) {
                $.ajax({
                    type : 'POST',
                    url : '${ctx}/nswy/standard/update-home',
                    data : {
                        status : status,
                        id : id
                    },
                    success : function () {
                        refresh();
                    }
                });
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
                    url : '${ctx}/nswy/standard/update-nav-display',
                    data : {
                        status : status,
                        id : id
                    },
                    success : function () {
                        refresh();
                    }
                });
            }
        }
        function setOrderNum(id) {
            debugger
            var orderNum = $("#orderNum"+id).val();
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/standard/set-order-num',
                data : {
                    orderNum : orderNum,
                    id : id
                },
                success : function () {
                    refresh();
                }
            });
        }
        function del(id) {
            var  confirmStatus = confirm("确定删除吗？")
            if (confirmStatus) {
                $.ajax({
                    type : 'POST',
                    url : '${ctx}/nswy/standard/delete',
                    data : {
                        standardId : id
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
                    url : '${ctx}/nswy/standard/adopt',
                    data : {
                        standardId : id
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
                    url : '${ctx}/nswy/standard/refuse',
                    data : {
                        standardId : id
                    },
                    success : function () {
                        refresh();
                    }
                });
            }
        }
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
<form:form id="searchForm"  modelAttribute="standardInfo"  action="${ctx}/nswy/standard/control" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

    <label>标题：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th width="10%">标题</th>
        <th>摘要</th>
        <th width="10%">标准来源</th>
        <th width="10%">发布人</th>
        <th>原创作者</th>
        <th>发布单位</th>
        <th>发布时间</th>
        <th>无忧首页是否显示</th>
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
                <c:if test="${fn:length(item.abstracts) > 20 }">
                    ${fn:substring(item.abstracts,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.abstracts) < 21 }">
                    ${item.abstracts}
                </c:if>
            </td>
            <td >${item.source}</td>
            <td >${item.author}</td>
            <td >${item.ycauthor}</td>
            <td title="${item.releaseUnit}" width="50px">
                <c:if test="${fn:length(item.releaseUnit) > 20 }">
                    ${fn:substring(item.releaseUnit,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.releaseUnit) < 21 }">
                    ${item.releaseUnit}
                </c:if>
            </td>
            <td>${item.createTime}</td>
            <td>
                <input type="checkbox"  id="isHomeDisplay${item.standardId}" onclick="isHomeDisplay('${item.standardId}')" value="" ${item.isToPage eq '0' ? 'checked' : ''} />
            </td>
            <td>
                <input type="checkbox" id="isNavDisplay${item.standardId}" value="" ${item.isNavDisplay eq '0' ? 'checked' : ''} onclick="isNavDisplay('${item.standardId}')"/>
            </td>
            <td>
                <input type="text" id="orderNum${item.standardId}" class="orderNum" onchange="setOrderNum('${item.standardId}')" value="${item.orderNum}" />
            </td>
            <td>
                <a href="javascript:void(0);" onclick="del('${item.standardId}')">删除</a>
                <c:if test="${item.approveStatus eq '待审核'}">
                    <a href="javascript:void(0);" onclick="adopt('${item.standardId}')">审核通过</a>
                    <a href="javascript:void(0);" onclick="refuse('${item.standardId}')">拒绝</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="pagination">${page}</div>
</body>
</html>