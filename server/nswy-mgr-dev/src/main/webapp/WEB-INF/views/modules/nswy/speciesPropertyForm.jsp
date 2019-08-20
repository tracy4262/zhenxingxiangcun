<%@ page contentType="text/html;charset=UTF-8" %>
       <%@ include file="/WEB-INF/views/include/taglib.jsp"%>
       <html>
       <head>
       	<title>自定义审核</title>
       	<meta name="decorator" content="default"/>
       </head>
       <body>
       	<form action="${ctx}/nswy/updateSpeciesProperty" method="post" class="form-horizontal">
       		<input type="hidden" id="indexid" name="indexid" value="${species.indexid}">
			<input type="hidden" id="speciesid" name="speciesid" value="${species.speciesid}">
			<input type="hidden" id="propertyid" name="propertyid" value="${species.propertyid}">

			<h1 class="control-group">${species.propertytitle}</h1>
			<div class="control-group">
       			${species.fshapefeatureid}
       		</div>
			<h1 class="control-group">简介</h1>
			<div class="control-group">
				<label>自定义名称:</label>
       			<input value="${species.propertytitle}" htmlEscape="false" maxlength="50" disabled="true" />

       		</div>
			<div class="control-group">
				<label>内容:</label>
				<div >${species.propertycontent}</div>
			</div>
			<c:if test="${species.propertyimage ne ''}">
				<div class="control-group">
					<label >图片:</label>
					<div class="controls">
						<c:forEach items="${species.propertyimage}" var="proxy" >
							<img style="width: 450px;height: 260px;" src="${proxy}" />
						</c:forEach>
					</div>
				</div>
			</c:if>



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
					<label>审核状态:</label>
					<input type="radio" checked name="auditstatus" value="3">通过
					<input type="radio" name="auditstatus" value="4">拒绝
				</div>
			</c:if>
            <div class="form-actions" align="center">
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