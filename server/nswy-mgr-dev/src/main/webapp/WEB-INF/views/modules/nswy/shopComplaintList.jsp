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
<form:form id="searchForm"  modelAttribute="shopComplaint"  action="${ctx}/nswy/shopComplaint/findComplaintList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
<%--    审核状态：<select  style="width: 100px" id="approveStatus" name="approveStatus" >
    <option value="">全部</option>
    <option value="0" ${approveStatus eq '0' }>待审核</option>
    <option value="2" ${approveStatus eq '1' }>审核通过</option>
    <option value="3" ${approveStatus eq '2'}>审核拒绝</option>
    </select>--%>
    卖家：<input type="text" name="sellerName" maxlength="8" value="${sellerName}"/>
    买家：<input type="text" name="buyerName" maxlength="8" value="${buyerName}"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th>卖家</th>
        <th>买家</th>
        <th>订单编号</th>
        <th>理由</th>
        <th>说明</th>
        <th>手机</th>
        <th>图片</th>
        <th>创建时间</th>
<%--        <th>审核状态</th>--%>
<%--        <shiro:hasPermission name="sys:role:edit">
            <th>操作</th>
        </shiro:hasPermission>--%>
    </tr>
    <c:forEach items="${page.list}" var="item">
        <tr>
            <td>${item.sellerName}</td>
            <td>${item.buyerName}</td>
            <td>${item.orderCodeId}</td>
            <td title="${item.reason}" width="50px">
                <c:if test="${fn:length(item.reason) > 20 }">
                    ${fn:substring(item.reason,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.reason) < 21 }">
                    ${item.reason}
                </c:if>
            </td>
            <td title="${item.describeInfo}" width="50px">
                <c:if test="${fn:length(item.describeInfo) > 20 }">
                    ${fn:substring(item.describeInfo,0,20)}...
                </c:if>
                <c:if test="${fn:length(item.describeInfo) < 21 }">
                    ${item.describeInfo}
                </c:if>
            </td>
            <td>${item.mobile}</td>
            <td>
                <c:if test="${fn:length(item.picUrl)>0}">
                    <c:forEach items="${fn:split(item.picUrl, ',')}" var="url">
                        <img style="width: 50px;height: 30px;" src="${url}"/>
                    </c:forEach>
                </c:if>
            </td>
            <td>
                <fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
<%--            <c:if test="${item.approveStatus == '0'}">
                <td>待审核</td>
            </c:if>
            <c:if test="${item.approveStatus == '1'}">
                <td>审核通过</td>
            </c:if>
            <c:if test="${item.approveStatus == '2'}">
                <td>审核拒绝</td>
            </c:if>--%>

            <%--<td>

                <c:if test="${item.approveStatus eq '0'}">
                    <a href="javascript:void(0);" onclick="adopt('${item.complaintId}','1')">审核通过</a>
                    <a href="javascript:void(0);" onclick="refuse('${item.complaintId}','2')">拒绝</a>
                </c:if>
            </td>--%>
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
                url : '${ctx}/nswy/shopComplaint/adopt',
                data : {
                    complaintId : id,
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
                url : '${ctx}/nswy/shopComplaint/adopt',
                data : {
                    complaintId : id,
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