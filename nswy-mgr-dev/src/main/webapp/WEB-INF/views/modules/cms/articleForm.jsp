<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            if($("#link").val()){
                $('#linkBody').show();
                $('#url').attr("checked", true);
            }
			$("#title").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
                    if ($("#categoryId").val()==""){
                        $("#categoryName").focus();
                        top.$.jBox.tip('请选择归属栏目','warning');
                    }else if (CKEDITOR.instances.content.getData()=="" && $("#link").val().trim()==""){
                        top.$.jBox.tip('请填写正文','warning');
                    }else{
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/article/?category.id=${article.category.id}">文章列表</a></li>
		<li class="active"><a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章<shiro:hasPermission name="cms:article:edit">${not empty article.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:article:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="article" action="${ctx}/cms/article/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">归属栏目:</label>
			<div class="controls">
                <sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
					title="栏目" url="/cms/category/treeData" module="article" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="true" cssClass="required"/>&nbsp;
                <span>
                    <input id="url" type="checkbox" onclick="if(this.checked){$('#linkBody').show()}else{$('#linkBody').hide()}$('#link').val()"><label for="url">外部链接</label>
                </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行业分类:</label>
			<div class="controls">
                <sys:treeselect id="industrialClassified" name="industrialClassified.fid" value="${article.industrialClassified.fid}" labelName="industrialClassified.fname" labelValue="${article.industrialClassified.fname}"
					title="行业分类" url="/cms/industrialclassified/treeData?fcategory=${article.category.id}" module="article" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="true" cssClass="required"/>&nbsp;
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-xxlarge measure-input required"/>
				&nbsp;<!-- <label>颜色:</label>
				<form:select path="color" class="input-mini">
					<form:option value="" label="默认"/>
					<form:options items="${fns:getDictList('color')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select> -->
			</div>
		</div>
        <div id="linkBody" class="control-group" style="display:none">
            <label class="control-label">外部链接:</label>
            <div class="controls">
                <form:input path="link" htmlEscape="false" maxlength="200" class="input-xlarge"/>
                <span class="help-inline">绝对或相对地址。</span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">关键字:</label>
			<div class="controls">
				<form:input path="keywords" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<span class="help-inline">多个关键字，用空格分隔。</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">动态分类:</label>
				<div class="controls">
				<form:select path="docType" class="input-mini" cssStyle="width:200px;">
					<form:option value="" label="默认(空)"/>
					<form:options items="${fns:getDictList('dongtaifenlei')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">商务咨询:</label>
			<form:select path="color" class="input-mini">
			<form:option value="" label="默认"/>
			</form:select>&lt;%&ndash;	<select  id="fanwei" style="width:200px;" ></select>

		</div>--%>
		<c:if test="${article.category.name=='资讯'}" >
			<div class="control-group">
				<label class="control-label">商务资讯:</label>
				<div class="controls">
				<form:select path="businessInformation" class="input-mini" cssStyle="width:200px;">
					<form:option value="" label="默认(空)"/>
					<form:options items="${fns:getDictList('shangwuzixu')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				</div>
			</div>
		    <div class="control-group">
				<label class="control-label">所有范围:</label>
			<div class="controls">
				<form:select path="allRange" class="input-mini" cssStyle="width:200px;">
					<form:option value="" label="默认(空)"/>
					<form:options items="${fns:getDictList('shuoyoufanwei')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
			</div>
			<div class="control-group">
				<label class="control-label">行业性质:</label>
				<div class="controls">
				<form:select path="businessNature" class="input-mini" cssStyle="width:200px;">
					<form:option value="" label="默认(空)"/>
					<form:options items="${fns:getDictList('hangyexingzi')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
			</div>
			<div class="control-group">
				<label class="control-label">所有行业:</label>
				<div class="controls">
				<form:select path="allBusiness" class="input-mini" cssStyle="width:200px;">
				<form:option value="" label="默认(空)"/>
				<form:options items="${fns:getDictList('suoyouhangye')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				</div>
			</div>
		</c:if>
		<c:if test="${article.category.name=='政策'}" >
			<div class="control-group">
				<label class="control-label">党政资讯:</label>
				<div class="controls">
				<form:select path="businessInformation" class="input-mini" cssStyle="width:200px;">
					<form:option value="" label="默认(空)"/>
					<form:options items="${fns:getDictList('shangwuzixu')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				</div>
			</div>
		    <div class="control-group">
				<label class="control-label">所有范围:</label>
			<div class="controls">
				<form:select path="allRange" class="input-mini" cssStyle="width:200px;">
					<form:option value="" label="默认(空)"/>
					<form:options items="${fns:getDictList('shuoyoufanwei')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
			</div>
			<div class="control-group">
				<label class="control-label">政务性质:</label>
				<div class="controls">
				<form:select path="businessNature" class="input-mini" cssStyle="width:200px;">
					<form:option value="" label="默认(空)"/>
					<form:options items="${fns:getDictList('hangyexingzi')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
			</div>
			<div class="control-group">
				<label class="control-label">所有分类:</label>
				<div class="controls">
				<form:select path="allBusiness" class="input-mini" cssStyle="width:200px;">
				<form:option value="" label="默认(空)"/>
				<form:options items="${fns:getDictList('suoyouhangye')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				</div>
			</div>
		</c:if>
		<c:if test="${article.category.name=='知识'}" >
			<div class="control-group">
				<label class="control-label">实用知识:</label>
				<div class="controls">
				<form:select path="businessInformation" class="input-mini" cssStyle="width:200px;">
					<form:option value="" label="默认(空)"/>
					<form:options items="${fns:getDictList('shangwuzixu')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				</div>
			</div>
		    <div class="control-group">
				<label class="control-label">所有范围:</label>
			<div class="controls">
				<form:select path="allRange" class="input-mini" cssStyle="width:200px;">
					<form:option value="" label="默认(空)"/>
					<form:options items="${fns:getDictList('shuoyoufanwei')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
			</div>
			<div class="control-group">
				<label class="control-label">所有学科:</label>
				<div class="controls">
				<form:select path="businessNature" class="input-mini" cssStyle="width:200px;">
					<form:option value="" label="默认(空)"/>
					<form:options items="${fns:getDictList('hangyexingzi')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
			</div>
			<div class="control-group">
				<label class="control-label">所有分类:</label>
				<div class="controls">
				<form:select path="allBusiness" class="input-mini" cssStyle="width:200px;">
				<form:option value="" label="默认(空)"/>
				<form:options items="${fns:getDictList('suoyouhangye')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				</div>
			</div>
		</c:if>


		<%--<div class="control-group">
			<label class="control-label">摘要:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">缩略图:</label>
			<div class="controls">
                <input type="hidden" id="image" name="image" value="${article.imageSrc}" />
				<sys:ckfinder input="image" type="thumb" uploadPath="/cms/article" selectMultiple="false"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">正文:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true" path="articleData.content" rows="4" maxlength="200" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/cms/article" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">来源:</label>
			<div class="controls">
				<form:input path="articleData.copyfrom" htmlEscape="false" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<shiro:hasPermission name="cms:article:audit">
			<div class="control-group">
				<label class="control-label">发布状态:</label>
				<div class="controls">
					<form:radiobuttons path="delFlag" items="${fns:getDictList('cms_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"></span>
				</div>
			</div>
		</shiro:hasPermission>
		<div class="form-actions">
			<shiro:hasPermission name="cms:article:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>