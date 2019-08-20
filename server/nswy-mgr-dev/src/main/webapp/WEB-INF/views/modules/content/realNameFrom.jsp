<%@ page import="com.ovit.manager.common.config.Global" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>实名认证</title>
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
	</script>
</head>
<body>

<ul class="nav nav-tabs">
	<li class="active">
		<a href="${ctx}/nswy/realName/control">用户</a>
	</li>

</ul><br/>

<form  action="${ctx}/nswy/updateSpecies" method="post" class="form-horizontal" enctype="multipart/form-data">

    <label>资质认证</label><hr>
    <div>
	<div class="control-group">
		<label class="control-label">会员分类:</label>
		<div  style="display: inline;float: left">
			<input value="${real.memberClass}" disabled="disabled">
		</div>

		<label class="control-label">会员全称:</label>
		<div  style="display: inline;float: left">
            <input value="${real.memberName}" disabled="disabled">
		</div>

		<label class="control-label">全称拼音:</label>
		<div  style="display: inline;float: left">
            <input value="${real.memberNamePinyin}" disabled="disabled">
		</div>
	</div>
    <div class="control-group">
        <label class="control-label">名称简写:</label>
        <div style="display: inline;float: left">
            <input value="${real.memberAbbreviation}" disabled="disabled">
        </div>

        <label class="control-label">简称拼音:</label>
        <div  style="display: inline;float: left">
            <input value="${real.memberAbbreviation}" disabled="disabled">
        </div>

        <label class="control-label">资质名称:</label>
        <div  style="display: inline;float: left">
            <input value="${real.aptitudeName}" disabled="disabled">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">资质编号:</label>
        <div style="display: inline;float: left">
            <input value="${real.aptitudeNumber}" disabled="disabled">
        </div>

        <label class="control-label">说明:</label>
        <div  style="display: inline;float: left;width: 50%">
            <textarea style="width: 100%" disabled="disabled">${real.remark}</textarea>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">资质照片:</label>
        <div  style="display: inline;float: left">
            <img src="${real.aptitudeImage}" disabled="disabled">
        </div>

    </div>
    </div>

    <label>联系方式</label><hr>
    <div>
    <div class="control-group">
        <label class="control-label">会员全称:</label>
        <div style="display: inline;float: left">
            <input value="${real.memberName}" disabled="disabled">
        </div>

        <label class="control-label">名称简写:</label>
        <div  style="display: inline;float: left">
            <input value="${real.memberAbbreviation}" disabled="disabled">
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">联系人姓名:</label>
        <div style="display: inline;float: left">
            <input value="${real.contactName}" disabled="disabled">
        </div>

        <label class="control-label">身份证号:</label>
        <div  style="display: inline;float: left">
            <input value="${real.card}" disabled="disabled">
        </div>

        <label class="control-label">座机电话:</label>
        <div  style="display: inline;float: left">
            <input value="${real.seatPhone}" disabled="disabled">
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">手机号码:</label>
        <div style="display: inline;float: left">
            <input value="${real.phone}" disabled="disabled">
        </div>

        <label class="control-label">QQ号码:</label>
        <div  style="display: inline;float: left">
            <input value="${real.qqNumber}" disabled="disabled">
        </div>

        <label class="control-label">微信账号:</label>
        <div  style="display: inline;float: left">
            <input value="${real.wechatNumber}" disabled="disabled">
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">邮箱:</label>
        <div style="display: inline;float: left">
            <input value="${real.email}" disabled="disabled">
        </div>

        <label class="control-label">网站地址:</label>
        <div  style="display: inline;float: left">
            <input value="${real.websiteUrl}" disabled="disabled">
        </div>

        <label class="control-label">邮政编码:</label>
        <div  style="display: inline;float: left">
            <input value="${real.postalCode}" disabled="disabled">
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">所在位置:</label>
        <div  style="display: inline;float: left">
            <input value="${real.location}" disabled="disabled">
        </div>

        <label class="control-label">详细地址:</label>
        <div  style="display: inline;float: left">
            <input value="${real.address}" disabled="disabled">
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">个人照片:</label>
        <div style="display: inline;float: left">
            <img src="${real.image}">
        </div>
    </div>

    </div>

    <label>法人或个人身份</label><hr>
    <div>
        <div class="control-group">
            <label class="control-label">会员全称:</label>
            <div style="display: inline;float: left">
                <input value="${real.memberName}" disabled="disabled">
            </div>

            <label class="control-label">名称简写:</label>
            <div  style="display: inline;float: left">
                <input value="${real.memberAbbreviation}" disabled="disabled">
            </div>

            <label class="control-label">性别:</label>
            <div  style="display: inline;float: left">
                <input value="${real.sex}" disabled="disabled">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">姓名:</label>
            <div style="display: inline;float: left">
                <input value="${real.name}" disabled="disabled">
            </div>

            <label class="control-label">手机:</label>
            <div  style="display: inline;float: left">
                <input value="${real.pesonPhone}" disabled="disabled">
            </div>

            <label class="control-label">身份证号:</label>
            <div  style="display: inline;float: left">
                <input value="${real.pesonCard}" disabled="disabled">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">职务:</label>
            <div style="display: inline;float: left">
                <input value="${real.job}" disabled="disabled">
            </div>

            <label class="control-label">职称:</label>
            <div  style="display: inline;float: left">
                <input value="${real.jobTitle}" disabled="disabled">
            </div>

            <label class="control-label">邮箱:</label>
            <div  style="display: inline;float: left">
                <input value="${real.pesonEmail}" disabled="disabled">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">职责:</label>
            <div style="display: inline;float: left;width: 50%">
                <textarea style="width: 100%;" disabled="disabled">${real.duty}</textarea>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">身份证照片(非公开):</label>
            <div  style="display: inline;float: left">
                <c:set var="var" value="${real.cardImage}"/>
                <c:forEach items="${fn:split(var,';' )}" var="item">
                    <img src="${item}" disabled="disabled">
                </c:forEach>
            </div>
        </div>
    </div>

    <label>法人或个人身份(管理员)</label><hr>
    <div>
        <div class="control-group">
            <label class="control-label">会员全称:</label>
            <div style="display: inline;float: left">
                <input value="${real.memberName}" disabled="disabled">
            </div>

            <label class="control-label">名称简写:</label>
            <div  style="display: inline;float: left">
                <input value="${real.memberAbbreviation}" disabled="disabled">
            </div>

            <label class="control-label">网站管理员姓名:</label>
            <div  style="display: inline;float: left">
                <input value="${real.adminName}" disabled="disabled">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">性别:</label>
            <div style="display: inline;float: left">
                <input value="${real.adminSex}" disabled="disabled">
            </div>

            <label class="control-label">身份证号:</label>
            <div  style="display: inline;float: left">
                <input value="${real.adminCard}" disabled="disabled">
            </div>

            <label class="control-label">手机:</label>
            <div  style="display: inline;float: left">
                <input value="${real.adminPhone}" disabled="disabled">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">职务:</label>
            <div style="display: inline;float: left">
                <input value="${real.adminJob}" disabled="disabled">
            </div>

            <label class="control-label">职称:</label>
            <div  style="display: inline;float: left">
                <input value="${real.adminJobTitle}" disabled="disabled">
            </div>

            <label class="control-label">邮箱:</label>
            <div  style="display: inline;float: left">
                <input value="${real.adminEmail}" disabled="disabled">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">职责:</label>
            <div style="display: inline;float: left;width: 50%">
                <textarea style="width: 100%;" disabled="disabled">${real.adminDuty}</textarea>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">身份证照片(非公开):</label>
            <div style="display: inline;float: left">
                <c:set var="var" value="${real.adminCardImage}"/>
                <c:forEach items="${fn:split(var,';' )}" var="item">
                    <img src="${item}" disabled="disabled">
                </c:forEach>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">授权证书（非公开):</label>
            <div  style="display: inline;float: left">
                <c:set var="var" value="${real.empowerImage}"/>
                <c:forEach items="${fn:split(var,';' )}" var="item">
                    <img src="${item}" disabled="disabled">
                </c:forEach>
            </div>
        </div>
    </div>

	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form>

</body>
</html>