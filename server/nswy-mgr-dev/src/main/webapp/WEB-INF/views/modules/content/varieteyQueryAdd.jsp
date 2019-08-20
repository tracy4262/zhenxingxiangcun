<%@ page import="com.ovit.manager.common.config.Global" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>虫害添加</title>
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

        function fileChange(target) {
            var fileSize = 0;
            fileSize = target.files[0].size;
            var size = fileSize / 1024;
            if (size > 2000) {
                alert("图片大小超出限制");
                target.value = "";
                return false;   //阻止submit提交
            }
        }
	</script>
</head>
<body>

<ul class="nav nav-tabs">
	<li >
		<a href="${ctx}/nswy/varieteyQuery/findVarieteyList">品种列表</a>
	</li>
	<li class="active">
		<a >品种添加</a>
	</li>

</ul><br/>

	<form:form id="inputForm"  action="${ctx}/nswy/varieteyQuery/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<sys:message content="${message}"/>
		<input type="hidden" id="pid" name="pid" value="${pid}"/>

		<div class="control-group">

				<label class="control-label">物种名称:</label>
				<select id="speciesId" name="speciesId" htmlEscape="false" class="required input-xlarge">
					<option value=''></option>
					<c:forEach var="report" items="${specieslist}">
						<option value="${report.fid}" >
								${report.fname}
						</option>
					</c:forEach>
				</select>
			<span class="help-inline"><font color="red">*</font> </span>

				<label style="margin-left: 100px">品种名称:</label>
				<input id="varietyName" name="varietyName" htmlEscape="false" maxlength="50" class="required input-xlarge" oninput="getPinyin($('#varietyName').val())"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div class="control-group">

				<label class="control-label">拼音:</label>
				<input id="pinyin" name="pinyin" readonly="true" htmlEscape="false" class="required input-xlarge" maxlength="50" />

				<label style="margin-left: 100px">品种类型:</label>
				<input id="fvarietykind" name="fvarietykind"  htmlEscape="false" class="input-xlarge" maxlength="50" />

		</div>

		<div class="control-group">

				<label class="control-label">选育单位:</label>
				<input id="fbreedingunit" name="fbreedingunit" class="input-xlarge" htmlEscape="false" maxlength="50"/>

				<label style="margin-left: 100px">是否转基因:</label>
				<input type="radio" checked name="fistransgene" value="1">是
				<input type="radio" name="fistransgene" value="0">否

		</div>

		<div class="control-group">

				<label class="control-label">申请日:</label>
				<input type="date" id="fapplydate" name="fapplydate" class="input-xlarge" htmlEscape="false" maxlength="50"/>

				<label style="margin-left: 100px" >申请号:</label>
				<input id="fapplynumber" name="fapplynumber" class="input-xlarge" htmlEscape="false" maxlength="50"/>

		</div>

		<div class="control-group">

				<label class="control-label">申请公告日:</label>
				<input type="date" id="fapplyannouncedate" name="fapplyannouncedate" class="input-xlarge" htmlEscape="false" maxlength="50"/>

				<label style="margin-left: 100px">申请公告号:</label>
				<input id="fapplyannouncenumber" name="fapplyannouncenumber" class="input-xlarge" htmlEscape="false" maxlength="50"/>

		</div>

		<div class="control-group">

				<label class="control-label">授权日:</label>
				<input type="date" id="fauthdate" name="fauthdate" class="input-xlarge" htmlEscape="false" maxlength="50"/>

				<label style="margin-left: 100px" >授权号:</label>
				<input id="fauthnumber" name="fauthnumber" class="input-xlarge" htmlEscape="false" maxlength="50"/>

		</div>

		<div class="control-group">

				<label class="control-label">授权公告日:</label>
				<input type="date" id="fauthannouncedate" name="fauthannouncedate" class="input-xlarge"  htmlEscape="false" maxlength="50"/>

				<label style="margin-left: 100px" >授权公告号:</label>
				<input id="fauthannouncenumber" name="fauthannouncenumber" class="input-xlarge" htmlEscape="false" maxlength="50"/>

		</div>

		<div class="control-group">

				<label class="control-label">培育人:</label>
				<input id="fgrowpeople" name="fgrowpeople" class="input-xlarge" htmlEscape="false" maxlength="50"/>

				<label style="margin-left: 100px" >品种权(申请)人:</label>
				<input id="fvarietyowner" name="fvarietyowner" class="input-xlarge" htmlEscape="false" maxlength="50"/>

		</div>

		<div class="control-group">

				<label class="control-label">品种权审定编号 :</label>
				<input id="fvarietyapprnum" name="fvarietyapprnum" class="input-xlarge" htmlEscape="false" maxlength="50"/>

				<label style="margin-left: 100px" >审定年份 :</label>
				<input id="fvarietyapprdate" name="fvarietyapprdate" class="input-xlarge" htmlEscape="false" maxlength="50"/>

		</div>

		<div class="control-group">

			<label class="control-label">审定单位 :</label>
			<input id="fvarietyapprunit" name="fvarietyapprunit" class="input-xlarge" htmlEscape="false" maxlength="50"/>

			<label style="margin-left: 100px" >特征特性 :</label>
			<input id="ffeature" name="ffeature" class="input-xlarge" htmlEscape="false" maxlength="50"/>

		</div>

		<div class="control-group">

				<label class="control-label">产量 :</label>
				<input id="foutput" name="foutput" class="input-xlarge"  htmlEscape="false" maxlength="50"/>

				<label style="margin-left: 100px" >栽培技术 :</label>
				<input id="fgrowteachology" name="fgrowteachology" class="input-xlarge" htmlEscape="false" maxlength="50"/>

		</div>

		<div class="control-group">

				<label class="control-label">适宜种植地区 :</label>
				<input id="fsuiteplatearea" name="fsuiteplatearea" class="input-xlarge" htmlEscape="false" maxlength="50"/>

				<label style="margin-left: 100px" >推广现状 :</label>
				<input id="fmarketsituation" name="fmarketsituation" class="input-xlarge" htmlEscape="false" maxlength="50"/>

		</div>

		<div class="control-group">
			<label class="control-label">品种来源 :</label>
			<div class="controls">
				<textarea  id="fvarietyorigin"  name="fvarietyorigin" class="input-xlarge"></textarea>

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">上传图标:</label>
			<div class="controls">
				<input type="file" id="diseasePic" name="diseasePic" class="required" onchange="fileChange(this);" accept="image/png,image/jpeg" htmlEscape="false"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline">图片大小不超过2MB,最多上传1张</span>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="sys:area:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>