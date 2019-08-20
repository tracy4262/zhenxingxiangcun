<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <style>
        #treeTable tr td {
            text-overflow: ellipsis; /* for IE */
            -moz-text-overflow: ellipsis; /* for Firefox,mozilla */
            overflow: hidden;
            white-space: nowrap;
            text-align: left;
        }
    </style>
	<script type="text/javascript">
        $(document).ready(function() {
            $("#treeTable").treeTable({expandLevel : 3}).show();
        });
        function updateSort() {
            loading('正在提交，请稍等...');
            $("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
            $("#listForm").submit();
        }

        function pictureUseHome(value,infoId,name) {
            var userType = "";
            if(name =="privacy_information"){
				userType = "隐私信息";
			}else if(name =="network_information"){
                userType = "网络信息";
			}else if(name =="education_live"){
                userType = "教育经历";
            }else if(name =="work_live"){
                userType = "工作经历";
            }else if(name =="political_status"){
                userType = "政治面貌";
            }else if(name =="religion_faith"){
                userType = "宗教信仰";
            }else if(name =="specialty_information"){
                userType = "专业信息";
            }else if(name =="honour_style"){
                userType = "荣誉风采";
            }else if(name =="houses_condition"){
                userType = "资产管理";
            }else if(name =="organization"){
                userType = "组织机构";
            }else if(name =="management_team"){
                userType = "管理团队";
            }else if(name =="survey_operation"){
                userType = "经营概况";
            }else if(name =="finance_information"){
                userType = "财务信息";
            }else if(name =="property_information"){
                userType = "资产信息";
            }else if(name =="product_serve"){
                userType = "产品&服务";
            }else if(name =="specialty_credentials"){
                userType = "专业资质";
            }else if(name =="common_assets"){
                userType = "普通资产";
            }else if(name =="overview_info"){
                userType = "概览信息";
            }else if(name =="family_member"){
                userType = "家庭成员";
            }else if(name =="output_product"){
                userType = "产出产品";
            }else if(name =="provide_services"){
                userType = "提供服务";
            }else if(name =="purchase_intention"){
                userType = "求购意向";
            }

            var text = value == '0' ? "是否设置"+userType+"到完善信息显示？" : "是否取消"+userType+"到完善信息显示？"
			var status = 0;
            if(value=='1'){
                status = 0;
			}else{
                status = 1;
			}
            var  confirmStatus = confirm(text)
            if (confirmStatus == true) {
                $.ajax({
                    type : 'POST',
                    url : '${ctx}/nswy/updateSysUserInfo',
                    data : {
                        user_status : status,
                        infoId : infoId,
                        INFO_NAME:name
                    },
                    success : function () {
                        //window.location.href="${ctx}/nswy/picture/control";
                        refresh();
                    }
                });
            }
        }

        function refresh() {
            $("#btnSubmit").click;
        }
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active">
		<a href="${ctx}/nswy/findList">用户列表</a>
	</li>

</ul>
<sys:message content="${message}"/>

<form id="listForm" method="post">
	<table id="treeTable" class="table table-striped table-bordered table-condensed hide">
		<thead>
			<tr>

				<th>用户类型</th>
				<th>隐私信息</th>
				<th>网络信息</th>
				<th>教育经历</th>
				<th>工作经历</th>
				<th>政治面貌</th>
				<th>宗教信仰</th>
				<th>专业信息</th>
				<th>荣誉风采</th>
				<th>家庭成员</th>
				<th>产出产品</th>
				<th>提供服务</th>
				<th>求购意向</th>
				<th>资产管理</th>
				<th>组织机构</th>
				<th>管理团队</th>
				<th>经营概况</th>
				<th>财务信息</th>
				<th>资产信息</th>
                <th>产品&服务</th>
                <th>专业资质</th>

				<th>普通资产</th>
				<th>概览信息</th>


			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<c:choose>
					<c:when test="${item.user_type eq 0}">
						<td>个人</td>
					</c:when>
					<c:when test="${item.user_type eq 2}">
						<td>种养户</td>
					</c:when>
					<c:when test="${item.user_type eq 1}">
						<td>企业</td>
					</c:when>
					<c:when test="${item.user_type eq 3}">
						<td>机关</td>
					</c:when>
					<c:when test="${item.user_type eq 4}">
						<td>专家</td>
					</c:when>
					<c:when test="${item.user_type eq 5}">
						<td>乡村</td>
					</c:when>
				</c:choose>
                <td>
                    <input type="checkbox"  value="" ${item.privacy_information eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.privacy_information}','${item.infoId}','privacy_information')"/>
                </td>
				<td>
					<input type="checkbox"  value="" ${item.network_information eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.network_information}','${item.infoId}','network_information')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.education_live eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.education_live}','${item.infoId}','education_live')"/>
				</td>
				<td>
					<input type="checkbox" value="" ${item.work_live eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.work_live}','${item.infoId}','work_live')"/>
				</td>
				<td>
					<input type="checkbox" value="" ${item.political_status eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.political_status}','${item.infoId}','political_status')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.religion_faith eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.religion_faith}','${item.infoId}','religion_faith')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.specialty_information eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.specialty_information}','${item.infoId}','specialty_information')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.honour_style eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.honour_style}','${item.infoId}','honour_style')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.family_member eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.family_member}','${item.infoId}','family_member')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.output_product eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.output_product}','${item.infoId}','output_product')"/>

				</td>
				<td>
					<input type="checkbox"  value="" ${item.provide_services eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.provide_services}','${item.infoId}','provide_services')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.purchase_intention eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.purchase_intention}','${item.infoId}','purchase_intention')"/>

				</td>
				<td>
					<input type="checkbox"  value="" ${item.houses_condition eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.houses_condition}','${item.infoId}','houses_condition')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.organization eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.organization}','${item.infoId}','organization')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.management_team eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.management_team}','${item.infoId}','management_team')"/>
				</td>
				<td>
					<input type="checkbox" value="" ${item.survey_operation eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.survey_operation}','${item.infoId}','survey_operation')"/>
				</td>

				<td>
					<input type="checkbox"  value="" ${item.finance_information eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.finance_information}','${item.infoId}','finance_information')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.property_information eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.property_information}','${item.infoId}','property_information')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.product_serve eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.product_serve}','${item.infoId}','product_serve')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.specialty_credentials eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.specialty_credentials}','${item.infoId}','specialty_credentials')"/>
				</td>

				<td>
					<input type="checkbox"  value="" ${item.common_assets eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.common_assets}','${item.infoId}','common_assets')"/>
				</td>
				<td>
					<input type="checkbox"  value="" ${item.overview_info eq '1' ? 'checked' : ''} onchange="pictureUseHome('${item.overview_info}','${item.infoId}','overview_info')"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</form>
</body>
</html>