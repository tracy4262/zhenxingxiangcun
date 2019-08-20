<%@ page import="com.ovit.manager.common.config.Global" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通用服务名管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
            $("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    //loading('正在提交，请稍等...');
                    form.submit();
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

        function getPinyin(chinese){
           $.ajax({
                url:"<%= Global.getConfig("api.url")%>/wiki/api/species/getSpeciesPinYin/"+chinese,
                data:{

                },
                type:"GET",
                dataType: "JSON",
                success : function (data) {
                    $("#pinyin").val(data.data)
                }
            });
        }

	</script>
</head>
<body>
<ul class="nav nav-tabs">

	<li>
		<a href="${ctx}/nswy/dictionaryGeneralService/control">通用服务名列表</a>
	</li>
	<li class="active">
		<a >通用服务名添加</a>
	</li>

</ul><br/>

<form:form id="inputForm" modelAttribute="dictionaryGeneralService"  action="${ctx}/nswy/dictionaryGeneralService/save" method="post" class="form-horizontal" enctype="multipart/form-data">

	<input type="hidden" id="pid" name="pid" value="${pid}"/>
	<input type="hidden" id="level" name="level" value="${level}"/>

	<div class="control-group">
		<label class="control-label">通用服务名名称:</label>
		<div class="controls">
			<input type="text" id="name"  name="name" htmlEscape="false" maxlength="50" oninput="getPinyin($('#name').val())" class="required input-xlarge"/>

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">通用服务名拼音:</label>
		<div class="controls">
			<input type="text" id="pinyin"  name="pinyin" htmlEscape="false" readonly="true"  maxlength="50" class="required input-xlarge" />

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">俗名别名:</label>
		<div class="controls">
			<input type="text" id="alias"  name="alias" htmlEscape="false" maxlength="50" class=" input-xlarge"/>

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">编码:</label>
		<div class="controls">
			<input type="text" id="value"  name="value" htmlEscape="false" maxlength="50" class=" input-xlarge"/>

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">行业分类:</label>
		<div class="controls">
			<select id="industryTypeId" name="industryTypeId" htmlEscape="false" class="input-xlarge">
				<option value=''>无</option>
				<c:forEach var="report" items="${industryList}">
					<option value="${report.industryTypeId}" >
							${report.industryTypeName}
					</option>
				</c:forEach>
			</select>
		</div>
	</div>

    <div class="control-group">
        <label class="control-label">服务分类:</label>
        <div class="controls">
            <select id="serviceTypeId" name="serviceTypeId" htmlEscape="false"  class="input-xlarge">
				<option value=''>无</option>
                <c:forEach var="report" items="${serviceTypeList}">
                    <option value="${report.serviceTypeId}" >
                            ${report.serviceTypeName}
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>

	<div class="control-group">
		<label class="control-label">关联物种:</label>
		<div class="controls">
			<select id="relatedSpeciesId" name="relatedSpeciesId" htmlEscape="false" class="input-xlarge">
				<option value=''>无</option>
				<c:forEach var="report" items="${speciesList}">
					<option value="${report.fid}" >
							${report.fname}
					</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">关联服务:</label>
		<div class="controls">
			<select id="serviceId" name="serviceId" htmlEscape="false" class="input-xlarge">
				<option value=''>无</option>
				<c:forEach var="report" items="${serviceList}">
					<option value="${report.serviceId}" >
							${report.serviceName}
					</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">关联产品:</label>
		<div class="controls">
			<select id="productId" name="productId" productId="false" class="input-xlarge">
				<option value=''>无</option>
				<c:forEach var="report" items="${productList}">
					<option value="${report.productId}" >
							${report.productName}
					</option>
				</c:forEach>
			</select>
		</div>
	</div>

    <div class="control-group">
        <label class="control-label">服务对象:</label>
        <div class="controls">
            <input type="text" id="servicePeopleName"  name="servicePeopleName" htmlEscape="false" maxlength="50" class=" input-xlarge"/>

        </div>
    </div>

	<div class="control-group">
		<label class="control-label">说明:</label>
		<div class="controls">
            <textarea  id="remark"  name="remark" ></textarea>

		</div>
	</div>

	<div class="form-actions">
		<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
	</div>


</form:form>
</body>
</html>