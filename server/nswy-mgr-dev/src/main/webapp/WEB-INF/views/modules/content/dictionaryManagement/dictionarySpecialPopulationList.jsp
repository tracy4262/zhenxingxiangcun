<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理-特殊人群</title>
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
		<a href="${ctx}/nswy/dictionarySpecialPopulation/control">特殊人群</a>
	</li>


</ul>
<form:form id="searchForm"  modelAttribute="dictionarySpecialPopulation"  action="${ctx}/nswy/dictionarySpecialPopulation/control" method="post" class="breadcrumb form-search">
    <label>类别名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    <%--<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/nswy/dictionary/control'"/>--%>
    <a href="${ctx}/nswy/dictionary/control"><input id="btnCancel" class="btn" type="button" value="返 回"/></a>
</form:form>
<table id="treeTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th width="20%">类别名称</th>
		<th width="15%">分类级别</th>
		<th width="15%">编码</th>
		<th width="30%">说明</th>
		<shiro:hasPermission name="sys:role:edit">
			<th>操作</th>
		</shiro:hasPermission>
	</tr>
	</thead>
	<tbody id="treeTableList"></tbody>
</table>
<script type="text/template" id="treeTableTpl">
	<tr id="{{row.id}}" pId="{{pid}}">
		<td title="{{row.name}}">{{row.name}}</td>
		<td title="{{row.level}}">{{row.level}}</td>
		<td title="{{row.value}}">{{row.value}}</td>
		<td title="{{row.remark}}">{{row.remark}}</td>

		<shiro:hasPermission name="sys:menu:edit">
			<td nowrap>
				<a href="${ctx}/nswy/dictionarySpecialPopulation/addPid?pid={{row.pid}}&level={{row.level}}">增加同级</a>
				<a href="${ctx}/nswy/dictionarySpecialPopulation/add?id={{row.id}}&level={{row.level}}">增加下级</a>
				<a href="${ctx}/nswy/dictionarySpecialPopulation/update?id={{row.id}}">修改</a>
				<a href="${ctx}/nswy/dictionarySpecialPopulation/delete?id={{row.id}}" onclick="return confirmx('确定删除吗？', this.href)">删除</a>
			</td>
		</shiro:hasPermission>
	</tr>
</script>
</body>
</html>

