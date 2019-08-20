<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>虫害字典管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <script type="text/javascript">
        $(document).ready(function() {
            debugger
            var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
            var data = ${fns:toJson(list)}, rootId = "0";
            addRow("#treeTableList", tpl, data, rootId, true);
            $("#treeTable").treeTable({expandLevel : 5});
        });
        function addRow(list, tpl, data, pid, root){
            for (var i=0; i<data.length; i++){
                var row = data[i];
                if ((${fns:jsGetVal('row.pid')}) == pid){
                    $(list).append(Mustache.render(tpl, {
                        dict: {
                            type: getDictLabel(${fns:toJson(fns:getDictList('sys_area_type'))}, row.type)
                        }, pid: (root?0:pid), row: row
                    }));
                    addRow(list, tpl, data, row.id);
                }
            }
        }
    </script>
</head>
<body>

<ul class="nav nav-tabs">
	<li class="active">
		<a href="${ctx}/nswy/pestQuery/findPestList">虫害列表</a>
	</li>

</ul>

<form:form id="searchForm" modelAttribute="speciesInfo" action="${ctx}/nswy/pestQuery/findPestList" method="post" class="breadcrumb form-search">
	<label>虫害名称：</label><form:input path="fname" htmlEscape="false" maxlength="50" class="input-medium"/>
	<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	<a href="${ctx}/nswy/dictionary/control"><input id="btnCancel" class="btn" type="button" value="返 回"/></a>

</form:form>

<table id="treeTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>类别名称</th>
        <th>分类级别</th>
        <th>编码</th>
        <th>说明</th>
        <th>关联物种</th>
        <shiro:hasPermission name="sys:role:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody id="treeTableList"></tbody>
</table>
<script type="text/template" id="treeTableTpl">
    <tr id="{{row.id}}" pId="{{pid}}">
        <td title="{{row.fname}}">{{row.fname}}</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <shiro:hasPermission name="sys:menu:edit">
            <td nowrap>
                <a href="${ctx}/nswy/pestQuery/addPid?pid={{row.pid}}">增加同级</a>
                <a href="${ctx}/nswy/pestQuery/add?id={{row.id}}">增加下级</a>
                <a href="${ctx}/nswy/pestQuery/update?indexid={{row.indexid}}">修改</a>
                <a href="${ctx}/nswy/pestQuery/delete?indexid={{row.indexid}}" onclick="return confirmx('确定删除吗？', this.href)">删除</a>
            </td>
        </shiro:hasPermission>
    </tr>
</script>
</body>
</html>