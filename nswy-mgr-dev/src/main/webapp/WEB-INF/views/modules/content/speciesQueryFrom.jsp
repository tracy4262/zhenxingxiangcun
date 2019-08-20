<%@ page import="com.ovit.manager.common.config.Global" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物种修改</title>
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
    <li ><a href="${ctx}/nswy/speciesQuery/findSpeciesList/">物种列表</a></li>
    <li class="active">物种修改</li>
</ul>

	<form:form id="inputForm"  action="${ctx}/nswy/speciesQuery/updateSpecies" method="post" class="form-horizontal">

        <input type="hidden" id="indexid" name="indexid" value="${species.indexid}">

		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">物种编号:</label>
			<div class="controls">
				<input id="speciesCode" name="speciesCode" value="${species.fcode}" htmlEscape="false" maxlength="50" class="required input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">物种名称:</label>
			<div class="controls">
				<input id="speciesName" name="speciesName" value="${species.fname}" oninput="getPinyin($('#speciesName').val())" htmlEscape="false" maxlength="50" class="required input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

        <div class="control-group">
            <label class="control-label">拼音:</label>
            <div class="controls">
                <input id="pinyin" name="pinyin" value="${species.fpinyin}" readonly="true" htmlEscape="false" class="required input-xlarge" maxlength="50" />
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">俗名:</label>
            <div class="controls">
                <input id="speciesVulgo" name="speciesVulgo" value="${species.speciesVulgo}" htmlEscape="false" maxlength="50" class="input-xlarge"/>

            </div>
        </div>

        <div class="control-group">
            <label class="control-label">其他分类:</label>
            <div class="controls">
                <input id="otherClassify" name="otherClassify" value="${species.otherClassify}" class="input-xlarge"/>

            </div>
        </div>

        <div class="control-group">
            <label class="control-label">物种分类:</label>
            <div class="controls">
                <input id="fclassifiedid" name="fclassifiedid" value="${species.fclassifiedid}" class="input-xlarge"/>

            </div>
        </div>

        <div class="control-group">
            <label class="control-label">产业分类:</label>
            <div class="controls">
                <input id="findustriaclassifiedid" name="findustriaclassifiedid" value="${species.findustriaclassifiedid}" class="input-xlarge"/>

            </div>
        </div>

        <div class="control-group">
            <label class="control-label">是否保护:</label>
            <div class="controls">
                <input id="fisprotection" name="fisprotection" value="${species.fisprotection}" class="input-xlarge"/>

            </div>
        </div>

        <div class="control-group">
            <label class="control-label">物种图标:</label>
            <div class="controls">
                <input type="file" id="diseasePic" name="diseasePic" onchange="fileChange(this);" accept="image/png,image/jpeg"  htmlEscape="false" maxlength="50" />
                <span class="help-inline">图片大小不超过2MB,最多上传4张</span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">主要产品:</label>
            <div class="controls">
                <input id="majorProduct" name="majorProduct" value="${species.majorProduct}" htmlEscape="false" maxlength="50" class="input-xlarge"/>

            </div>
        </div>

        <div class="control-group">
            <label class="control-label">特征性状:</label>
            <div class="controls">
                <input id="fshapefeatureid" name="fshapefeatureid" value="${species.fshapefeatureid}" value="${species.fshapefeatureid}" htmlEscape="false" maxlength="50" class="input-xlarge"/>

            </div>
        </div>

        <div class="control-group">
            <label class="control-label">备注:</label>
            <div class="controls">
                <input id="remark" name="remark" value="${species.fremarks}" htmlEscape="false" maxlength="50" class="input-xlarge"/>

            </div>
        </div>

		<div class="form-actions">
			<shiro:hasPermission name="sys:area:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>