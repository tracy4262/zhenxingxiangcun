<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>会员管理</title>
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
                    url : '${ctx}/nswy/member/update-home',
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
                url : '${ctx}/nswy/member/set-order-num',
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
<form:form id="searchForm" action="${ctx}/nswy/member/control" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    会员名称：<input type="text" name="memberName" maxlength="8" value="${memberName}"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th width="10%">会员名称</th>
        <th width="20%">会员类别</th>
        <th width="10%">相关物种</th>
        <th width="20%">相关行业</th>
        <th width="20%">相关产品</th>
        <th width="20%">相关服务</th>
        <th >无忧首页是否显示</th>
        <th >排序</th>
    </tr>
    <c:forEach items="${page.list}" var="item">
        <tr>
            <td title="${item.memberName}">${item.memberName}</td>
            <td title="${item.memberClass}">${item.memberClass}</td>
            <td title="${item.speciesName}">${item.speciesName}</td>
            <td title="${item.industryName}">${item.industryName}</td>
            <td title="${item.productName}">${item.productName}</td>
            <td title="${item.serviceName}">${item.serviceName}</td>
            <td >
                <input type="checkbox" id="isHomeDisplay${item.memberId}" onclick="isHomeDisplay('${item.memberId}')" value=""${item.isToPage eq '0' ? 'checked' : ''} />
            </td>
            <td >
                <input type="text" id="orderNum${item.memberId}" class="orderNum" onchange="setOrderNum('${item.memberId}')" value="${item.orderNum}" />
            </td>
        </tr>
    </c:forEach>
</table>
<div class="pagination">${page}</div>
</body>
</html>