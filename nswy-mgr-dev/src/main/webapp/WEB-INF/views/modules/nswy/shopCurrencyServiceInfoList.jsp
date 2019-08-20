<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>通用服务名审核</title>
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
<form:form id="searchForm"  modelAttribute="shopCurrencyServiceInfo"  action="${ctx}/nswy/shopCurrencyService/findShopCurrencyServiceInfoList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    通用服务名：<input type="text" name="commonProductName" maxlength="8" value="${commonProductName}"/>
    <label class="control-label">审核状态:</label>
    <form:select path="approveStatus" class="input-medium">
        <form:option value="">全部</form:option>
        <form:option value="0">待审核</form:option>
        <form:option value="1">已审核</form:option>
        <form:option value="2">审核拒绝</form:option>
    </form:select>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th>通用服务名</th>
        <th>通用服务名拼音</th>
        <th>行业分类</th>
        <th>产品分类</th>
        <th>物种名称</th>
        <th>发布人</th>
        <th>服务分类</th>
        <th>相关服务</th>
        <th>服务人</th>
        <th>审核意见</th>
        <th>备注</th>
        <th>更新时间</th>
        <th>审核状态</th>
        <shiro:hasPermission name="sys:role:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    <c:forEach items="${page.list}" var="item">
        <tr>
            <td title="${item.commonProductName}" width="50px">
                <c:if test="${fn:length(item.commonProductName) > 20 }">
                    ${fn:substring(item.commonProductName,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.commonProductName) < 21 }">
                    ${item.commonProductName}
                </c:if>
            </td>
            <td width="40px">${item.commonProductPinyin}</td>

            <td title="${item.relatedIndustry}" width="50px">
                <c:if test="${fn:length(item.relatedIndustry) > 20 }">
                    ${fn:substring(item.relatedIndustry,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.relatedIndustry) < 21 }">
                    ${item.relatedIndustry}
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
            <td title="${item.relatedSpeciesName}" width="50px">
                <c:if test="${fn:length(item.relatedSpeciesName) > 20 }">
                    ${fn:substring(item.relatedSpeciesName,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.relatedSpeciesName) < 21 }">
                    ${item.relatedSpeciesName}
                </c:if>
            </td>
            <td>${item.displayName}</td>

            <td>${item.serviceClassId}</td>
            <td>${item.relatedServiceName}</td>
            <td>${item.servicePeopleName}</td>

            <td title="${item.refuseReason}" width="50px">
                <c:if test="${fn:length(item.refuseReason) > 20 }">
                    ${fn:substring(item.refuseReason,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.refuseReason) < 21 }">
                    ${item.refuseReason}
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

            <td>
                <fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <c:if test="${item.approveStatus == '0'}">
                <td>待审核</td>
            </c:if>
            <c:if test="${item.approveStatus == '1'}">
                <td>审核通过</td>
            </c:if>
            <c:if test="${item.approveStatus == '2'}">
                <td>审核拒绝</td>
            </c:if>
            <td>
                <c:if test="${item.approveStatus eq '0'}">
                    <%--<a href="javascript:void(0);" onclick="adopt('${item.shopId}','1')">审核通过</a>--%>
                    <a href="${ctx}/nswy/shopCurrencyService/shopCurrencyServiceInfoFrom?shopId=${item.shopId}">审核</a>
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

    function adopt(id,approveStatus) {
        var  confirmStatus = confirm("确定通过审核吗？")
        if (confirmStatus) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/shopCurrencyService/currencyAdopt',
                data : {
                    shopId : id,
                    approveStatus: approveStatus

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
</body>
</html>