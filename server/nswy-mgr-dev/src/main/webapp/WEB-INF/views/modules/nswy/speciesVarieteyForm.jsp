<%@ page contentType="text/html;charset=UTF-8" %>
       <%@ include file="/WEB-INF/views/include/taglib.jsp"%>
       <html>
       <head>
       	<title>品种审核</title>
       	<meta name="decorator" content="default"/>
       </head>
       <body>
       	<form action="${ctx}/nswy/updateSpeciesVarietey" method="post" class="form-horizontal">
       		<input type="hidden" id="indexid" name="indexid" value="${species.indexid}">
			<input type="hidden" id="speciesid" name="speciesid" value="${species.speciesid}">
			<input type="hidden" id="fid" name="fid" value="${species.fid}">
			<h1 class="control-group">${species.fname}</h1>
			<div class="control-group">
       			${species.fshapefeatureid}
       		</div>
			<h1 class="control-group">简介</h1>
			<div class="control-group">
				<label class="control-label">品种名称:</label>
       			<input value="${species.fname}" htmlEscape="false" maxlength="50" disabled="true" />
       			<label style="margin-left: 100px">汉语拼音:</label>
       			<input value="${species.fpinyin}" htmlEscape="false" maxlength="50" disabled="true" />
       		</div>
			<div class="control-group">
       			<label class="control-label">品种类型:</label>
       			<input value="${species.fvarietykind}" htmlEscape="false" maxlength="50" disabled="true" />
       		</div>
			<div class="control-group">
				<label class="control-label">品种来源:</label>
				<div style="margin-top:5px;">${species.fvarietyorigin}</div>
			</div>

       		<div class="control-group">
       			<label class="control-label">选育单位:</label>
       			<input value="${species.fbreedingunit}" htmlEscape="false" maxlength="50" disabled="true" />
				<label style="margin-left: 100px">是否转基因:</label>
                <c:if test="${species.fistransgene eq 0 }">
                    <input value="否" htmlEscape="false" maxlength="50" disabled="true" />
                </c:if>
                <c:if test="${species.fistransgene eq 1 }">
                    <input value="是" htmlEscape="false" maxlength="50" disabled="true" />
                </c:if>

       		</div>
			<div class="control-group">
       			<label class="control-label">申请日:</label>
       			<input value="${species.fapplydate}" htmlEscape="false" maxlength="50" disabled="true" />
				<label style="margin-left: 100px">申请号:</label>
				<input value="${species.fapplynumber}" htmlEscape="false" maxlength="50" disabled="true" />
       		</div>

			<div class="control-group">
				<label class="control-label">申请公众日:</label>
				<input value="${species.fapplyannouncedate}" htmlEscape="false" maxlength="50" disabled="true" />
				<label style="margin-left: 100px">申请公众号:</label>
				<input value="${species.fapplyannouncenumber}" htmlEscape="false" maxlength="50" disabled="true" />
			</div>

			<div class="control-group">
				<label class="control-label">授权日:</label>
				<input value="${species.fauthdate}" htmlEscape="false" maxlength="50" disabled="true" />
				<label style="margin-left: 100px">品种权号:</label>
				<input value="${species.fauthnumber}" htmlEscape="false" maxlength="50" disabled="true" />
			</div>

			<div class="control-group">
				<label class="control-label">授权公告日:</label>
				<input value="${species.fauthannouncedate}" htmlEscape="false" maxlength="50" disabled="true" />
				<label style="margin-left: 100px">授权公告号:</label>
				<input value="${species.fauthannouncenumber}" htmlEscape="false" maxlength="50" disabled="true" />
			</div>

			<div class="control-group">
				<label class="control-label">培育人:</label>
				<input value="${species.fgrowpeople}" htmlEscape="false" maxlength="50" disabled="true" />
				<label style="margin-left: 100px">品种权（申请）人:</label>
				<input value="${species.fvarietyowner}" htmlEscape="false" maxlength="50" disabled="true" />
			</div>

			<div class="control-group">
				<label class="control-label">审定编号:</label>
				<input value="${species.fvarietyapprnum}" htmlEscape="false" maxlength="50" disabled="true" />
				<label style="margin-left: 100px">审定年份:</label>
				<input value="${species.fvarietyapprdate}" htmlEscape="false" maxlength="50" disabled="true" />
			</div>
			<div class="control-group">
				<label class="control-label">审定单位:</label>
				<input value="${species.fvarietyapprunit}" htmlEscape="false" maxlength="50" disabled="true" />
				<label style="margin-left: 100px">适宜区域:</label>
				<input value="${species.fsuiteplatearea}" htmlEscape="false" maxlength="50" disabled="true" />
			</div>

			<div class="control-group">

				<label class="control-label">特征特性:</label>
				<div style="margin-top:5px;">${species.ffeature}</div>

			</div>

			<div class="control-group">

				<label class="control-label">产量:</label>
				<div style="margin-top:5px;">${species.foutput}</div>

			</div>

			<div class="control-group">
				<label class="control-label">栽培技术:</label>
				<div style="margin-top:5px;">${species.fgrowteachology}</div>

			</div>
			<div class="control-group">
				<label class="control-label">推广现状:</label>
				<div style="margin-top:5px;">${species.fmarketsituation}</div>
			</div>

			<c:if test="${species.auditstatus eq 0}">
                <div class="control-group">
                    <label class="control-label">审核状态:</label>
                    <input type="radio" checked name="auditstatus" value="0">通过
                    <input type="radio" name="auditstatus" value="4">拒绝
                </div>
			</c:if>
			<c:if test="${species.auditstatus eq 2}">
				<div class="control-group">
					<label class="control-label">审核状态:</label>
					<input type="radio" checked name="auditstatus" value="2">通过
					<input type="radio" name="auditstatus" value="4">拒绝
				</div>
			</c:if>
			<c:if test="${species.auditstatus eq 3}">
				<div class="control-group">
					<label class="control-label">审核状态:</label>
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