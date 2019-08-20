<%@ page contentType="text/html;charset=UTF-8" %>
       <%@ include file="/WEB-INF/views/include/taglib.jsp"%>
       <html>
       <head>
       	<title>病害审核</title>
       	<meta name="decorator" content="default"/>
       </head>
       <body>
       	<form action="${ctx}/nswy/updateSpeciesDisease" method="post" class="form-horizontal">
       		<input type="hidden" id="indexid" name="indexid" value="${species.indexid}">
			<input type="hidden" id="speciesid" name="speciesid" value="${species.speciesid}">
			<input type="hidden" id="fid" name="fid" value="${species.fid}">
			<h1 class="control-group">${species.fname}</h1>
			<div class="control-group">
       			${species.fshapefeatureid}
       		</div>
			<h1 class="control-group">简介</h1>
			<div class="control-group">
				<label >病害名称:</label>
       			<input value="${species.fname}" htmlEscape="false" maxlength="50" disabled="true" />
       			<label style="margin-left: 100px">汉语拼音:</label>
       			<input value="${species.fpinyin}" htmlEscape="false" maxlength="50" disabled="true" />
       		</div>
			<c:if test="species.">

			</c:if>
			<div class="control-group">
				<label>危害症状:</label>
				<div>${species.ffeature}</div>
			</div>
			<div class="control-group">
				<label>危害物种:</label>
				<div>${species.specName}</div>
			</div>
			<div class="control-group">
				<label>发生规律:</label>
				<div>${species.fdiseaseregular}</div>
			</div>
			<div class="control-group">
				<label >防治方法:</label>
				<div>${species.fprotectmethod}</div>
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