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
<form:form id="searchForm"  modelAttribute="shopCommityInfo"  action="${ctx}/nswy/shopCommodity/findShopCommodityInfoList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    审核状态：<select  style="width: 100px" id="approveStatus" name="approveStatus" >
    <option value="">全部</option>
    <option value="0" ${approveStatus eq '0' }>待审核</option>
    <option value="2" ${approveStatus eq '1' }>审核通过</option>
    <option value="3" ${approveStatus eq '2'}>审核拒绝</option>
    </select>
    商品名：<input type="text" name="commodityName" maxlength="8" value="${commodityName}"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th>通用商品名</th>
        <th>商品名</th>
        <th>销售市场</th>
        <th>行业分类</th>
        <th>产品分类</th>
        <th>物种名称</th>
        <th>品种名称</th>
        <th>价格</th>
        <th>发布人</th>
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
            <td title="${item.commodityName}" width="50px">
                <c:if test="${fn:length(item.commodityName) > 20 }">
                    ${fn:substring(item.commodityName,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.commodityName) < 21 }">
                    ${item.commodityName}
                </c:if>
            </td>

            <td title="${item.salesMarket}" width="50px">
                <c:if test="${fn:length(item.salesMarket) > 20 }">
                    ${fn:substring(item.salesMarket,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.salesMarket) < 21 }">
                    ${item.salesMarket}
                </c:if>
            </td>
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
            <td title="${item.breedName}" width="50px">
                <c:if test="${fn:length(item.breedName) > 20 }">
                    ${fn:substring(item.breedName,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.breedName) < 21 }">
                    ${item.breedName}
                </c:if>
            </td>
            <td>${item.timePrice}</td>
            <td>${item.displayName}</td>

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
                    <a href="javascript:void(0);" onclick="adopt('${item.shopId}','1')">审核通过</a>
                    <a href="javascript:void(0);" onclick="refuse('${item.shopId}','2')">拒绝</a>
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
        debugger
        var  confirmStatus = confirm("确定通过审核吗？")
        if (confirmStatus) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/shopCommodity/adopt',
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
    function refuse(id,approveStatus) {
        var  confirmStatus = confirm("确定拒绝吗？")
        if (confirmStatus) {
            $.ajax({
                type : 'POST',
                url : '${ctx}/nswy/shopCommodity/adopt',
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