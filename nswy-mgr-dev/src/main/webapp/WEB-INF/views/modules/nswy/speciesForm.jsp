<%@ page contentType="text/html;charset=UTF-8" %>
       <%@ include file="/WEB-INF/views/include/taglib.jsp"%>
       <html>
       <head>
       	<title>物种审核</title>
       	<meta name="decorator" content="default"/>
       </head>
       <body>
       	<form action="${ctx}/nswy/updateSpecies" method="post" class="form-horizontal">
       		<input type="hidden" id="indexid" name="indexid" value="${species.indexid}">
			<input type="hidden" id="speciesid" name="speciesid" value="${species.speciesid}">
			<h1 class="control-group">${species.fname}</h1>
			<div class="control-group">
       			${species.fshapefeatureid}
       		</div>
			<h1 class="control-group">简介</h1>
			<div class="control-group">
				<label >物种名称:</label>
       			<input value="${species.fname}" htmlEscape="false" maxlength="50" disabled="true" />
       			<label style="margin-left: 100px">汉语拼音:</label>
       			<input value="${species.fpinyin}" htmlEscape="false" maxlength="50" disabled="true" />
       		</div>
			<div class="control-group">
       			<label >保护级别:</label>
       			<input value="${species.fisprotectionInfo.val}" htmlEscape="false" maxlength="50" disabled="true" />
				<label style="margin-left: 100px">产业分类:</label>
       			<input value="${species.findustriaclassifiedidInfo.val}" htmlEscape="false" maxlength="50" disabled="true" />
       		</div>
       		<div class="control-group">
       			<label >物种分类:</label>
       			<input value="${species.fclassifiedidInfo.val}" htmlEscape="false" maxlength="50" disabled="true" />
				<label style="margin-left: 100px">其他分类:</label>
       			<input value="${species.otherClassifyInfo.val}" htmlEscape="false" maxlength="50" disabled="true" />
       		</div>
			<div class="control-group">
       			<label >物种俗名:</label>
       			<input value="${species.speciesVulgo}" htmlEscape="false" maxlength="50" disabled="true" />
       		</div>
			<div class="control-group">
       			<label >主要产品:</label>
       			<input value="${species.majorProduct}" htmlEscape="false" maxlength="50" disabled="true" />
       		</div>

			<c:if test="${species.auditstatus eq 0}">
                <div class="control-group">
                    <label >审核状态:</label>
                    <input type="radio" checked name="auditstatus" value="0">通过
                    <input type="radio" name="auditstatus" value="4">拒绝
                </div>
			</c:if>
			<c:if test="${species.auditstatus eq 2}">
				<div class="control-group">
					<label >审核状态:</label>
					<input type="radio" checked name="auditstatus" value="2">通过
					<input type="radio" name="auditstatus" value="4">拒绝
				</div>
			</c:if>
			<c:if test="${species.auditstatus eq 3}">
				<div class="control-group">
					<label >审核状态:</label>
					<input type="radio" checked name="auditstatus" value="3">通过
					<input type="radio" name="auditstatus" value="4">拒绝
				</div>
			</c:if>
            <div class="form-actions">
                <c:if test="${species.auditstatus eq 0 }">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="审 核" onclick="return confirm('确认要审核吗？')"/>&nbsp;
                </c:if>
				<c:if test="${species.auditstatus eq 2 }">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="审 核" onclick="return confirm('确认要审核吗？')"/>&nbsp;
				</c:if>
				<c:if test="${species.auditstatus eq 3 }">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="审 核" onclick="return confirm('确认要审核吗？')"/>&nbsp;
				</c:if>
       		    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
       	    </div>
       	</form>
     </body>
   </html>