<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <style>
        #treeTable tr td {
            text-overflow: ellipsis; /* for IE */
            -moz-text-overflow: ellipsis; /* for Firefox,mozilla */
            overflow: hidden;
            white-space: nowrap;
            text-align: left;
        }
    </style>
	<script type="text/javascript">
        $(document).ready(function() {
            $("#treeTable").treeTable({expandLevel : 3}).show();
        });
        function updateSort() {
            loading('正在提交，请稍等...');
            $("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
            $("#listForm").submit();
        }

        function pictureUseHome(pictureId) {
            var status = $("#pictureUseHome"+pictureId).is(":checked") ? '1' : '0';
            var text = status == '1' ? "是否设置图片到农业要闻显示？" : "是否取消图片到农业要闻显示？"
            var  confirmStatus = confirm(text)
            if (confirmStatus == true) {
                debugger
                $.ajax({
                    type : 'POST',
                    url : '${ctx}/nswy/pictureUseHome',
                    data : {
                        status : status,
                        pictureId : pictureId
                    },
                    success : function () {
                        //window.location.href="${ctx}/nswy/picture/control";
                        refresh();
                    }
                });
            }
        }

        function pictureUseInformation(pictureId) {
            var status = $("#pictureUseInformation"+pictureId).is(":checked") ? '1' : '0';
            var text = status == '1' ? "是否设置图片到资讯中心显示？" : "是否取消图片到资讯中心显示？"
            var  confirmStatus = confirm(text)
            if (confirmStatus == true) {
                debugger
                $.ajax({
                    type : 'POST',
                    url : '${ctx}/nswy/pictureUseInformation',
                    data : {
                        status : status,
                        pictureId : pictureId
                    },
                    success : function () {
                        //window.location.href="${ctx}/nswy/picture/control";
                        refresh();
                    }
                });
            }
        }

        function pictureProduct(pictureId) {
            var status = $("#pictureUseInformation"+pictureId).is(":checked") ? '1' : '0';
            var text = status == '1' ? "是否设置图片到商城首页显示？" : "是否取消图片到商城首页显示？"
            var  confirmStatus = confirm(text)
            if (confirmStatus == true) {
                debugger
                $.ajax({
                    type : 'POST',
                    url : '${ctx}/nswy/pictureProduct',
                    data : {
                        status : status,
                        pictureId : pictureId
                    },
                    success : function () {
                        //window.location.href="${ctx}/nswy/picture/control";
                        refresh();
                    }
                });
            }
        }
        function refresh() {
            $("#btnSubmit").click;
        }
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active">
		<a href="${ctx}/nswy/picture/control">图片列表</a>
	</li>
	<shiro:hasPermission name="sys:menu:edit">
		<li>
			<a href="${ctx}/nswy/picture/add">图片添加</a>
		</li>
	</shiro:hasPermission>
</ul>
<sys:message content="${message}"/>
<form id="listForm" method="post">
	<table id="treeTable" class="table table-striped table-bordered table-condensed hide">
		<thead>
			<tr>
				<th width="20%">图片名称</th>
				<th width="30%">描述</th>
                <th>应用到农业要闻</th>
                <th>应用到资讯中心</th>
				<th>应用到商城首页</th>
				<shiro:hasPermission name="sys:menu:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td title="${item.pictureName}">${fn:substringBefore(item.pictureName, '.')}</td>
				<td title="${item.remark}">
					<c:if test="${fn:length(item.remark) > 40 }">
						${fn:substring(item.remark,0,40)}...
					</c:if>
					<c:if test="${fn:length(item.remark) < 41 }">
						${item.remark}
					</c:if>
                </td>
                <td>
                    <input type="checkbox" id="pictureUseHome${item.pictureId}" value="" ${item.pictureUseHome eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.pictureId}')"/>
                </td>
                <td>
                    <input type="checkbox" id="pictureUseInformation${item.pictureId}" value="" ${item.pictureUseInformation eq '1' ? 'checked' : ''} onchange="pictureUseInformation('${item.pictureId}')"/>
                </td>
				<td>
					<input type="checkbox" id="pictureUseInformation${item.pictureId}" value="" ${item.pictureProduct eq '1' ? 'checked' : ''} onchange="pictureProduct('${item.pictureId}')"/>
				</td>
				<shiro:hasPermission name="sys:menu:edit">
					<td nowrap>
						<a href="${ctx}/nswy/picture/delete?pictureId=${item.pictureId}"
						   onclick="return confirm('是否删除？')">删除</a>
					</td>
                </shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</form>
</body>
</html>