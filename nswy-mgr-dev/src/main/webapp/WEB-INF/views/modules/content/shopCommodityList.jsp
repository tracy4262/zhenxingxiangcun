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
<form:form id="searchForm"  modelAttribute="shopCommityInfo"  action="${ctx}/nswy/shopCommodity/findShopCommodityList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    商品名：<input type="text" name="commodityName" maxlength="8" value="${commodityName}"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
    <tr>
        <th>通用商品名</th>
        <th>商品名</th>
        <th>行业分类</th>
        <th>产品分类</th>
        <th>物种名称</th>
        <th>品种名称</th>
        <th>价格</th>
        <th>发布人</th>
        <th>发布时间</th>
        <th>无忧首页是否显示</th>
        <th>排序</th>
    </tr>
    <c:forEach items="${page.list}" var="item">
        <tr>
            <td title="${item.productName}" width="50px">
                    ${item.productName}
            </td>
            <td title="${item.commodityName}" width="50px">
                    ${item.commodityName}
            </td>
            <td title="${item.industryType}" width="50px">
                    ${item.industryType}
            </td>
            <td title="${item.productType}" width="50px">
                    ${item.productType}
            </td>
            <td title="${item.species}" width="50px">
                    ${item.species}
            </td>
            <td title="${item.variety}" width="50px">
                    ${item.variety}
            </td>

            <td title="" width="50px">

            </td>

            <td title="${item.account}" width="50px">
                    ${item.account}
            </td>

            <td>
                <fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <input type="checkbox" id="isHomeDisplay${item.id}" onclick="isHomeDisplay('${item.id}')" value="" ${item.isHomeDisplay eq '1' ? 'checked' : ''} />
            </td>
            <td>
                <input type="text" id="orderNum${item.id}" class="orderNum" onchange="setOrderNum('${item.id}')" value="${item.orderNum}" />
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

    //设置是否在无忧首页显示
    function isHomeDisplay(id) {
        debugger
        var status = $("#isHomeDisplay" + id).is(":checked") ? '1' : '0';
        var text = status == '1' ? "是否设置该数据到无忧首页显示？" : "是否取消该数据到无忧首页显示？"
        var  confirmStatus = confirm(text)
        if (confirmStatus == true) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/shopCommodity/updateHome',
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