<template>
	<div class="publish">
		<Modal v-model="options.showPublish" width="800" @on-cancel="cancel" :title="'发布'+options.title" :mask-closable="false" :styles="{top: '20px'}">
			<!-- <Tabs type="card"  @on-click="changeLabel">
				<Tab-pane :label="info.name" v-for="(info,index) in smallPaneData" :name="info.name" :key="index">
				</Tab-pane>
				<Button type="default" @click="handleTabsAdd" size="small" slot="extra" :disabled="tabs===10" class="tabadd">+
					</Button>
				<Button type="default" @click="handleTabsDel" size="small" slot="extra" class="tabadd"  v-show="isHide" >-
				</Button>
			</Tabs> -->
			<div style="margin-left: 60px;" class="pt20 pb30">
				<Steps :current="current">
					<Step title="基本信息"></Step>
					<Step title="详细信息"></Step>
					<Step title="关联属性"></Step>
					<Step title="发布设置"></Step>
				</Steps>
			</div>
			<Form ref="mydynamic" :model="mydynamic" :rules="ruleInline" label-position="right" :label-width="100" v-show="process === 1">
                <FormItem label="标题" prop="title">
                    <Input type="text" v-model="mydynamic.title" :maxlength="40" placeholder="标题" />
                </FormItem>
				<FormItem label="分类" prop="standardType">
					<Cascader :data="policyTypeDatas" v-model="mydynamic.standardType" :load-data="loadInfoTypeDatas" filterable></Cascader>
				</FormItem>
				<FormItem label="信息来源" prop="source" style="text-align: left;">
					<Radio-group v-model="mydynamic.source" @on-change="changeSource(mydynamic)">
						<Radio label="原创">
						</Radio>
						<Radio label="转载">
						</Radio>
					</Radio-group>
				</FormItem>
				<FormItem label="发布人" prop="author">
					<Input type="text" v-model="mydynamic.author" readonly placeholder="发布人" />
				</FormItem>
				<FormItem label="原创作者" prop="ycauthor" v-if="isYC">
					<Input type="text" v-model="mydynamic.ycauthor" placeholder="原创作者" />
				</FormItem>
				<FormItem label="来源网站" prop="sourceWebsite" v-if="isYC">
					<Input type="text" v-model="mydynamic.sourceWebsite" placeholder="来源网站" />
				</FormItem>
				<FormItem label="摘要">
					<Input type="textarea" v-model="mydynamic.summary" :maxlength="300" placeholder="摘要" />
				</FormItem>
				<FormItem label="地区" prop="district">
					<Cascader :data="cityList" :render-format="format" v-model="mydynamic.district" :load-data="loadData" change-on-select>
					</Cascader>
				</FormItem>
			</Form>
			<Form ref="pubstep1" :model="mydynamic" :rules="ruleInline1" label-position="right" :label-width="180" v-show="process === 2">
				<Row>
					<Col span="12">
						<FormItem label="标准特点" prop="standardTrait">
							<Select v-model="mydynamic.standardTrait">
								<Option value="强制性标准" key="强制性标准">强制性标准</Option>
								<Option value="推荐性标准" key="推荐性标准">推荐性标准</Option>
							</Select>
						</FormItem>
					</Col>
					<Col span="12">
						<FormItem label="标准状态" prop="standardStatus">
							<Select v-model="mydynamic.standardStatus">
								<Option value="现行" key="现行">现行</Option>
								<Option value="即将实施" key="即将实施">即将实施</Option>
							</Select>
						</FormItem>
					</Col>
				</Row>
				<Row>
					<Col span="12">
						<FormItem label="中国标准分类号（CCS）" prop="ccs">
							<Select v-model="mydynamic.ccs" filterable>
								<Option v-for="item in cssList" :value="item.value" :key="item.value">{{ item.label }}</Option>
							</Select>
						</FormItem>
					</Col>
					<Col span="12">
						<FormItem label="国际标准分类号（ICS）" prop="ics">
							<Select v-model="mydynamic.ics" filterable>
								<Option v-for="item in icsList" :value="item.value" :key="item.value">{{ item.label }}</Option>
							</Select>
						</FormItem>
					</Col>
				</Row>
				<Row>
					<Col span="12">
						<FormItem label="标准号" prop="standardNumber">
							<Input type="text" v-model="mydynamic.standardNumber" :maxlength="50" placeholder="标准号" />
						</FormItem>
					</Col>
					<Col span="12">
						<FormItem label="发布日期" prop="publishDate">
							<Date-picker type="date"
								style="width: 100%;" :options="options3"
								@on-change="mydynamic.publishDate=$event">
							</Date-picker>
						</FormItem>
					</Col>
				</Row>
				<Row>
					<Col span="12">
						<FormItem label="中文标准名称" prop="chineseStandardName">
							<Input type="text" v-model="mydynamic.chineseStandardName" :maxlength="50" placeholder="中文标准名称" />
						</FormItem>
					</Col>
					<Col span="12">
						<FormItem label="实施日期" prop="implementDate">
							<Date-picker type="date"
								style="width: 100%;" :options="options4"
								@on-change="mydynamic.implementDate=$event">
							</Date-picker>
						</FormItem>
					</Col>
				</Row>
				<FormItem label="英文标准名称" prop="englishStandardName">
					<Input type="text" v-model="mydynamic.englishStandardName" :maxlength="100" placeholder="英文标准名称" />
				</FormItem>
				<FormItem label="主管部门" prop="competentDepartment">
					<Input type="text" v-model="mydynamic.competentDepartment" :maxlength="50" placeholder="主管部门" />
				</FormItem>
				<FormItem label="归口单位" prop="focalPoint">
					<Input type="text" v-model="mydynamic.focalPoint" :maxlength="50" placeholder="归口单位" />
				</FormItem>
				<FormItem label="发布单位" prop="releaseUnit">
					<Input type="text" v-model="mydynamic.releaseUnit" :maxlength="50" placeholder="发布单位" />
				</FormItem>
				<FormItem label="标准文本" prop="standardText">
					<quilEditor ref="abc" :content="mydynamic.standardText" @quilCon="getCon" uploadId='corpUpload' myQuillEditor="corpQuillEditor">
					</quilEditor>
				</FormItem>
				<FormItem prop="standardFile" class="legal_person" label="上传标准文件">
					<vuiUploadFile
						ref="standardFile"
						:onload="false"
						:format="['jpg', 'png', 'doc', 'docx', 'pdf', 'xlsx', 'xls']"
						hint="支持jpg/png/doc/docx/pdf/xlsx/xls"
						@on-getFileList="getStandardFile($event)">
						</vuiUploadFile>
				</FormItem>
				<FormItem label="">
					<span style="font-size: 12px;line-height: 30px;color: #2d8cf0;">
						提示：标准文本和标准文件至少选择其中一种上传，都上传将优先显示文件，可切换到标准文本显示。
					</span>
				</FormItem>
			</Form>
			<Form ref="pubstep2" :model="mydynamic" :rules="ruleInline2" label-position="right" :label-width="100" v-show="process === 3">
				<FormItem label="关联物种" prop="species" >
					<Input v-model="species" readonly @on-focus="handleFilterModal('speciFilter')" />
				</FormItem>
				<FormItem label="通用商品名" prop="goodsname">
          <vuiCommodity ref="products" @on-save="getProducts" type="1" :values="products"></vuiCommodity>
					<!-- <Input v-model="products" readonly @on-focus="handleFilterModal('goodFilter')" /> -->
				</FormItem>
				<FormItem label="通用服务名" prop="servicename">
          <vuiCommodity ref="service" @on-save="getService" type="2" :values="service"></vuiCommodity>
					<!-- <Input  readonly v-model="service" @on-focus="handleFilterModal('serviceFilter')" /> -->
				</FormItem>
				<FormItem label="行业分类" prop="industry">
					<Input v-model="industryName" readonly @on-focus="handleFilterModal('tradeFilter')" />
				</FormItem>
				<Form-item label="解读专家" prop="expert">
					<Input type="text" v-model="mydynamic.expert" placeholder="解读专家" />
				</Form-item>
			</Form>
			<Form ref="pubstep3" :model="mydynamic"  label-position="right" :label-width="100" v-show="process === 4">
				<FormItem label="好友提醒" prop="tixing">
					<Input type="text" v-model="mydynamic.tixing" placeholder="好友提醒" />
				</FormItem>
				<FormItem label="提醒内容" prop="con">
					<Input type="textarea" v-model="mydynamic.con" placeholder="提醒内容" />
				</FormItem>
				<Row>
					<Col span="11">
					<FormItem label="发送短信" prop="sms" style="text-align: left;">
						<Radio-group v-model="mydynamic.sms">
							<Radio label="是">
							</Radio>
							<Radio label="否">
							</Radio>
						</Radio-group>
					</FormItem>
					</Col>
					<Col span="11" offset="1">
					<FormItem label="提交首页显示" prop="xianshi">
						<Radio-group v-model="mydynamic.xianshi" @on-change="submitIndex(mydynamic)">
							<Radio label="是">
							</Radio>
							<Radio label="否">
							</Radio>
						</Radio-group>
					</FormItem>
					</Col>
				</Row>
				<FormItem label="公开范围" prop="security" style="text-align: left;">
					<Radio-group v-model="mydynamic.security">
						<Radio label="所有人可见">
						</Radio>
						<Radio label="仅好友可见">
						</Radio>
						<Radio label="仅自己可见">
						</Radio>
						<Radio label="仅指定组可见">
						</Radio>
					</Radio-group>
				</FormItem>
				<FormItem label="是否有偿" prop="money">
					<Radio-group v-model="mydynamic.money" @on-change="changeMoney(mydynamic)">
						<Radio label="收费">
						</Radio>
						<Radio label="免费">
						</Radio>
					</Radio-group>
				</FormItem>
			</Form>
			<div class="disclaimer" v-show="process === 4">
				<div class="disclaimer-title"><a href="javascript:void(0);" @click="onDisclaimer"><span>免责声明</span></a></div>
				<div class="disclaimer-content" v-show="disclaimer">
					<p>1.本网站只提供一个信息交流平台，不控制发布内容。发布内容及因之产生的后果，均由发布者自行负责，</p>
					<p>与本网站无关。我们对用户使用本网站所产生的一切后果，不承担任何责任；</p>
					<p>2.发贴内容须符合中华人民共和国和所在国法律，符合当地道德规范和风俗文化。内容真实，无欺诈；</p>
					<p>3.谢绝一切与本网站主题无关的内容，尤其不涉及政治、宗教、种族等议题</p>
					<p>4.不发布涉及他人版权的内容，不发布与栏目无关之内容；</p>
					<p>5.不发布与传销、直销及其他事涉不规范经济活动有关的内容。</p>
				</div>
			</div>
			<div slot="footer" style="text-algin:center">
				<div style="text-algin:center">
					<Button type="primary" shape="circle" @click.native="preStep" style="width:100px;margin-right: 20px;" v-if="process !== 1">上一步</Button>
					<Button type="primary" shape="circle" @click.native="next('mydynamic')" style="width:100px;margin-right: 20px;" v-if="process === 1">下一步</Button>
					<Button type="primary" shape="circle" @click.native="next('pubstep1')" style="width:100px;margin-right: 20px;" v-if="process === 2">下一步</Button>
					<Button type="primary" shape="circle" @click.native="next('pubstep2')" style="width:100px;margin-right: 20px;" v-if="process === 3">下一步</Button>
					<Button type="primary" shape="circle" @click.native="publishSubmit('pubstep3')" style="width:100px" v-if="process === 4">发布</Button>
				</div>
			</div>

			<!-- 相关行业tradeFilter -->
			<vui-filter
					ref="tradeFilter"
					:simple="true"
					:pageShow="true"
					:total="total2"
					:pageCur="pageCur2"
					:resultDatas="tradeResultDatas"
					@on-search="handleTradeSearch"
					@on-page-change="handleTradePageChange"
					@on-get-result="handleGetTradeResult"/>
			<!-- 相关物种 -->
			<vui-filter ref="speciFilter" :cols="2" :pageShow="true" :total="total" :pageCur="pageCur" :classifyDatas="speciClassifyDatas" :resultDatas="speciResultDatas" :load-data="loadSpeciDatas" @on-search="handleSpeciSearch" @on-get-classify="handleGetSpeciClassify" @on-get-result="handleGetSpeciResult" @on-page-change="handleSpeciPageChange" />

			<!-- 相关服务 -->
			<vui-filter ref="serviceFilter" :simple="true" :resultDatas="serviceResultDatas" @on-search="handleServiceSearch" @on-get-result="handleGetServiceResult" />
			<!-- 相关商品名 -->
			<vui-filter ref="goodFilter" :simple="true" :resultDatas="[]" />
			<Modal v-model="modalp" :closable="false" @on-ok="savepane" width="300" :transfer="false">
				<h3>标签名</h3>
				<Input v-model="labelName" type="text" class="mt20" />
			</Modal>
		</Modal>
	</div>
</template>
<script>
	import api from "~api";
	import quilEditor from "../../../components/vuequilEditor";
	import upload from "../../../components/upload";
	import $ from "jquery";
	import vuiFilter from "../../../components/vuiFilter/filter";
	import photoSelector from '~components/photoSelector'
	import vuiUploadFile from '../../../components/vui-upload-file'
	import vuiCommodity from "~components/vui-commodity";

	export default {
		name: "dany",
		components: {
			quilEditor,
			upload,
			vuiFilter,
			photoSelector,
			vuiUploadFile,
			vuiCommodity
		},
		props: {
			options: {
				type: Object
			}
		},
		data() {
			return {
				isYC: false,
				process: 1,
				total: 0,
				total2: 0,
				pageCur2: 1,
				pageCur: 1,
				columnMydynamic: [], //页签
				modalp: false, //显示页签增加
				labelName: '', //页签名
				indexpane: 0, //页签的顺序
				tabs: 0,
				isHide:false,//当前页是全部时隐藏
				policyTypeDatas:[
					{
						value: 'party',
						label: '党务',
						loading: false,
						children: []
					},
					{
						value: 'affairs',
						label: '政务',
						loading: false,
						children: []
					}
				],
				disclaimer:false,//免责声明
				speciClassifyDatas: [
					{
						label: '动物',
						value: '0',
						classId: '',
						loading: false,
						checked: false,
						children: []
					},
					{
						label: '植物',
						value: '1',
						classId: '',
						loading: false,
						checked: false,
						children: []
					}
				],
				speciResultDatas: [],
				serviceResultDatas: [],
				type: 'publish',
				tradeResultDatas: [],
				transfer: true,
				ruleInline: {
					title: [
						{ required: true, message: '标题不能为空', trigger: "blur" },
						{ type: 'string', max: 50, message: '标题不能超过20个字', trigger: 'blur' }
					],
					standardType: [
						{ required: true, type: 'array', message: '分类不能为空', trigger: 'change' }
					],
					source: [
						{ required: true }
					],
					district: [
						{ required: true, type: 'array', message: '地区不能为空', trigger: 'change' }
					],
					ycauthor: [
						{ required: true, message: '原创作者不能为空', trigger: "blur" }
					],
					sourceWebsite: [
						{ required: true, message: '来源网站不能为空', trigger: "blur" }
					]
				},
				ruleInline1: {
					standardTrait: [
						{ required: true, message: '标准特点不能为空', trigger: "change" }
					],
					standardStatus: [
						{ required: true, message: '标准状态不能为空', trigger: "change" }
					],
					ccs: [
						{ required: true, message: '中国标准分类号不能为空', trigger: "change" }
					],
					ics: [
						{ required: true, message: '国际标准分类号不能为空', trigger: "change" }
					],
					standardNumber: [
						{ required: true, message: '标准号不能为空', trigger: "blur" }
					],
					publishDate: [
						{ required: true, message: '发布日期不能为空', trigger: "change" }
					],
					chineseStandardName: [
						{ required: true, message: '中文标准名称不能为空', trigger: "blur" }
					],
					implementDate: [
						{ required: true, message: '实施日期不能为空', trigger: "change" }
					],
					englishStandardName: [
						{ required: true, message: '英文标准名称不能为空', trigger: "blur" }
					],
					competentDepartment: [
						{ required: true, message: '主管部门不能为空', trigger: "blur" }
					],
					focalPoint: [
						{ required: true, message: '归口单位不能为空', trigger: "blur" }
					],
					releaseUnit: [
						{ required: true, message: '发布单位不能为空', trigger: "blur" }
					],
				},
				ruleInline2: {},
				loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
				show: true,
				cityList: [],
				con: true,
				step: '',
				mydynamic: {},
				species: '',
				products:'',
				service:'',
				industryName: '',
				current: 0,
				cssList: [
					{
						label: 'A00标准化管理与一般规定',
						value: 'A00标准化管理与一般规定'
					},
					{
						label: 'A10经济、文化',
						value: 'A10经济、文化'
					},
					{
						label: 'A20基础科学',
						value: 'A20基础科学'
					}
				],
				icsList: [
					{
						label: '01综合、术语学、标准化',
						value: '01综合、术语学、标准化'
					},
					{
						label: '03社会学、服务、公司',
						value: '03社会学、服务、公司'
					},
					{
						label: '07数学、自然科学',
						value: '07数学、自然科学'
					}
				],
				options3: {
                    disabledDate (date) {
                        return date && date.valueOf() > Date.now()
                    }
                },
				options4: {
                    disabledDate (date) {
                        return date && date.valueOf() < Date.now() - 86400000
                    }
                },
				smallPaneData: [],
				allSmallPaneData: []
			}
		},
		created() {
			// 取地址
			api.post("/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816").then(res => {
				this.cityList = res.data;
			});
			// 取相关行业数据
            this.$api.post('/member/system-dict/getSystemDict', {
                typeName:'行业分类',
                pageNum: 1
            }).then(res => {
                var data = res.data.list
                this.total2 = res.data.total
                this.tradeResultDatas = data
            })
			// 取相关物种结果
			this.loadSpeciResult('', '', [], this.pageCur, [])
			// 取相关服务数据
			api.post('/member/service/find', {}).then(res => {
				var data = res.data
				data.forEach((item, index) => {
					item.checked = false
				})
				this.serviceResultDatas = data
			})
		},
		methods: {
			getName () {
				this.$refs['mydynamic'].resetFields()
				this.$refs['pubstep1'].resetFields()
				this.$refs['pubstep2'].resetFields()
				this.$refs['pubstep3'].resetFields()
				this.process = 1
				this.mydynamic = {
					/* 第一步 */
					title: '',
					standardType: [],
					source: '原创',
					author: '',
					ycauthor: '',
					sourceWebsite: '',
					summary: '',
					district: [],
					/* 第二步 */
					standardTrait: '',
					standardStatus: '',
					ccs: '',
					ics: '',
					standardNumber: '',
					publishDate: '',
					chineseStandardName: '',
					implementDate: '',
					englishStandardName: '',
					competentDepartment: '',
					focalPoint: '',
					releaseUnit: '',
					standardText: '',
					standardFile: '',

					/* 第三步 */
					speciesId: '',
					commonProductId: '',
					commonServiceId: '',
					industryTypeId: '',
					expert: '',
					/* 第四步 */
					tixing: '',
					con: '',
					xianshi: '否',
					sms: '否',
					security: '所有人可见',
					money: '免费',
					/* 自定义分类 */
					custom: '全部'
				}
				console.log('33333', this.mydynamic)
				//获得名字
				this.$api.post('/member/login/findCurrentUser', {
					account: this.$user.loginAccount
				}).then(res => {
					if (res.data.displayName) {
						this.mydynamic.author = res.data.displayName
					}
				})
			},
			getStandardFile ($event) {
				if ($event[0]) {
				// origin 地址 src pdf name 名称
					$event[0].response.data.src ? 
					this.mydynamic.standardFile = $event[0].response.data.src : 
					this.mydynamic.standardFile = $event[0].response.data.origin;
					this.mydynamic.standardFileName = $event[0].response.data.name
				} else {
					this.mydynamic.standardFile = ''
					this.mydynamic.standardFileName = ''
				}
			},
			// 取资讯类型
			loadInfoTypeDatas (item, callback) {
					item.loading = true
				api.get(`/member/infoClass/policy/${item.value}`).then(res => {
					item.loading = false
					item.children = res.data
					callback()
				})
			},
			// 通用服务名
			getService(result) {
				console.log('result1', result)
				var arr = [];
				result.forEach(item => arr.push(item.value));
				var service = [];
				result.forEach(item => service.push(item.label));
				this.service = service.join(" ");
				this.mydynamic.commonServiceId = arr.join(" ");
			},
			// 通用商品名
			getProducts(result) {
				console.log('result333', result)
				var arr = [];
				result.forEach(item => arr.push(item.value));
				var products = [];
				result.forEach(item => products.push(item.label));
				this.products = products.join(" ");
				this.mydynamic.commonProductId = arr.join(" ");
			},
			//选择页签
			changeLabel(e){
				this.mydynamic.custom=e
				if (this.mydynamic.custom === '全部'){
                    this.isHide = false
				}else{
                    this.isHide = true
				}
			},
			showMydynamic(index) {
				this.indexpane = index
				let columnName=this.options.columnSetting[index].columnName
				// 获取标准下的所有页签
				/* api.get('/member/columnSettings/findColumnLabel?columnName=' + columnName).then(response => {
					this.columnMydynamic = []
					this.columnMydynamic = response.data
				}) */
				// 获取标准下所有自定义标签
				// this.$api.get('/member/columnSettings/findColumnByUserSettings?account=' + this.loginuserinfo.loginAccount).then(response => {
				// 	console.log('response000', response)
				// 	if (response.code === 200) {
				// 		this.allSmallPaneData = response.data
				// 		console.log('this.options', this.options)
				// 		this.allSmallPaneData.forEach(element => {
				// 			if (element.typeName === '标准') {
				// 				this.smallPaneData = element.userSettings
				// 			}
				// 		})
				// 	}
				// 	console.log('this.smallPaneData', this.smallPaneData)
				// }).catch(error => {
				// 	this.$Message.error('获取自定义标签出错！')
				// })
			},
			//发布到首页时判断是否实名
			submitIndex(e) {
				if('是' === e.xianshi) {
				    this.mydynamic.xianshi = '是'
				}else{
                    this.mydynamic.xianshi = '否'
				}
			},
			//添加页签
			handleTabsAdd () {
				this.tabs = this.smallPaneData.length
				if (this.tabs > 9) {
					this.$Message.info('最多添加10个页签！')
				} else {
					this.modalp = true
				}
			},
			//标签的删除
            handleTabsDel(){
                this.$Modal.confirm({
					title: '操作提示',
					content: '确定删除该标签？',
					onOk: () => {
						this.allSmallPaneData.forEach(element => {
							if (element.typeName === '标准') {
								element.userSettings.forEach((item, i) => {
									if (this.mydynamic.custom === item.name) {
										element.userSettings.splice(i, 1)
									}
								})
							}
						})
						api.post('/member/columnSettings/savecolumn', {
							label: JSON.stringify(this.allSmallPaneData),
							account: this.loginuserinfo.loginAccount
						}).then(response => {
							if (response.code === 200) {
								this.$Message.success('删除自定义标签成功!')
							} else {
								this.$Message.error('删除自定义标签失败!')
							}
						})
					}
				})
			},
			//确认保存
			savepane () {
				if (this.labelName) {
					// 保存自定义标签需要将整个json串都回传this.allSmallPaneData
					this.allSmallPaneData.forEach(element => {
						if (element.typeName === '标准') {
							element.userSettings.push({
								index: element.userSettings.length,
								name: this.labelName
							})
						}
					})
					api.post('/member/columnSettings/savecolumn', {
						label: JSON.stringify(this.allSmallPaneData),
						account: this.loginuserinfo.loginAccount
					}).then(response => {
						if (response.code === 200) {
							this.$Message.success('添加自定义标签成功!')
						} else {
							this.$Message.error('添加自定义标签失败!')
						}
					})
				} else {
					this.$Message.info('页签名字不能为空！')
				}
			},
			// 高级搜索弹窗
			handleFilterModal(name) {
				this.$refs[name].highFilterShow = true;
			},
            // 行业类型
            handleTradeSearch (letter, keyword, result) {
                this.$api.post(`/member/system-dict/getSystemDict`, {
                    dictName : keyword,
                    typeName : '行业分类',
                    character: letter === '全部' ? '' : letter,
                    pageNum: 1
                }).then(res => {
                    var d = res.data.list
                    this.total2 = res.data.total
                    this.pageCur2 = 1
                    this.tradeResultDatas = []
                    if (!d.length) return
                    if (result.length) {
                        result.forEach(item => {
                            d.forEach(child => {
                                if (child.label === item.label) {
                                    child.checked = true
                                    child.label = child.label
                                    child.value = child.value
                                }
                            })
                        })
                    } else {
                        d.forEach(child => {
                            child.checked = false
                            child.label = child.label
                            child.value = child.value
                        })
                    }
                    this.tradeResultDatas = d
                })
            },
            handleTradePageChange (letter, keyword, classify, num, result) {
                this.pageCur2 = num
                this.$api.post('/member/system-dict/getSystemDict', {
                    typeName:'行业分类',
                    character: letter === '全部' ? '' : letter,
                    dictName: keyword,
                    pageNum: num
                }).then(res => {
                    var data = res.data.list
                    this.total2 = res.data.total
                    this.tradeResultDatas = data
                })
                this.$Message.success('筛选完成！')
            },
            handleGetTradeResult (classify, result) {
                var arr = []
                var arrId = []
                result.forEach(item => {
                    arr.push(item.label)
                    arrId.push(item.value)
                })
                this.mydynamic.industryTypeId = arrId.join(' ')
                this.industryName = arr.join(' ')
            },
			loadData(item, callback) {
				item.loading = true
				api.post(`/member/town/next/${item.value}`).then(res => {
					item.loading = false
					item.children = res.data
					callback()
				});
			},
			// 取相关物种
			handleSpeciSearch(letter, keyword, classify, result) {
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
			},
			loadSpeciDatas(item, callback) {
				item.loading = true
				api.post(`/member/specicesClass/findByParentId/${item.value}`).then(res => {
					item.loading = false
					var d = res.data
					d.forEach(child => {
						child.checked = false
						child.label = child.className
					})
					item.children = d
					callback()
				})
			},
			handleGetSpeciClassify(letter, keyword, classify, result) {
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
			},
			handleSpeciPageChange(letter, keyword, classify, num, result) {
				this.pageCur = num
				this.loadSpeciResult(letter, keyword, classify, this.pageCur, result)
			},
			loadSpeciResult(letter, keyword, classify, num, result) {
				if(classify.length) {
					var arr = []
                    var type= ''
					classify.forEach(item => {
						arr.push(item.classId)
                        if(item.value !== undefined) type = item.value
					})
				} else {
					arr = null
				}
				api.post('/member/specicesClass/findSpecies', {
					keywords: keyword,
					fpinyin: letter === '全部' ? '' : letter,
					fclassifiedid: arr,
                    type: type,
					pageNum: num,
					pageSize: 32
				}).then(res => {
					var data = res.data
					this.total = data.total
					if(result) {
						result.forEach(item => {
							data.list.forEach((child, index) => {
								if(child.label === item.label) {
									child.checked = true
								}
							})
						})
					} else {
						data.list.forEach(child => {
							child.checked = false
						})
					}
					this.speciResultDatas = data.list
				})
			},
			handleGetSpeciResult(classify, result) {
				let ids = []
				let names = []
				result.forEach( item => {
				    ids.push(item.value)
					names.push(item.label)
				})
                this.species = names.join(' ')
                this.mydynamic.speciesId = ids.join(' ')
			},
			// 相关服务
			handleServiceSearch(letter, keyword, classify) {
				api.post(`/member/service/find`, {
					character: letter === '全部' ? '' : letter,
					keyword: keyword
				}).then(res => {
					var d = res.data
					this.serviceResultDatas = []
					if(d.length) {
						if(classify.length) {
							classify.forEach(item => {
								d.forEach(child => {
									if(child.label === item.label) {
										child.checked = true
									}
								})
							})
						} else {
							d.forEach(child => {
								child.checked = false
							})
						}
						this.serviceResultDatas = d
					}
				})
			},
			handleGetServiceResult(classify, result) {				
				var arr = []
				var service = []
				result.forEach(item => {
					arr.push(item.value)
					service.push(item.label)
				})
				this.service = service.join(' ')
				this.mydynamic.commonServiceId = arr.join(' ')
			},
			format(labels, selectedData) {
				if(labels.length < 1) {
					return ""
				} else {
					this.mydynamic.district = labels
					return labels.join("/")
				}
			},
			//免责声明
            onDisclaimer(){
                this.disclaimer = !this.disclaimer
			},
			publishSubmit (name) {
				console.log('standard', this.mydynamic)
				let security = ''
				if (this.mydynamic.security === '所有人可见') {
					security = '0'
				} else if (this.mydynamic.security === '仅好友可见') {
					security = '1'
				} else if (this.mydynamic.security === '仅自己可见') {
					security = '2'
				} else if (this.mydynamic.security === '仅分组可见') {
					security = '3'
				}
				let data = {
					/* 第一步 */
					title: this.mydynamic.title,
					standardType: this.mydynamic.standardType,
					source: this.mydynamic.source === '原创' ? 'O' : 'R',
					author: this.mydynamic.author,
					ycauthor: this.mydynamic.source === '原创' ? '' : this.mydynamic.ycauthor,
					sourceWebsite: this.mydynamic.source === '原创' ? '' : this.mydynamic.sourceWebsite,
					summary: this.mydynamic.summary,
					district: this.mydynamic.district,
					/* 第二步 */
					standardTrait: this.mydynamic.standardTrait === '强制性标准' ? 'M' : 'R',
					standardStatus: this.mydynamic.standardStatus === '现行' ? 'A' : 'F',
					ccs: this.mydynamic.ccs,
					ics: this.mydynamic.ics,
					standardNumber: this.mydynamic.standardNumber,
					publishDate: this.mydynamic.publishDate,
					chineseStandardName: this.mydynamic.chineseStandardName,
					implementDate: this.mydynamic.implementDate,
					englishStandardName: this.mydynamic.englishStandardName,
					competentDepartment: this.mydynamic.competentDepartment,
					focalPoint: this.mydynamic.focalPoint,
					releaseUnit: this.mydynamic.releaseUnit,
					standardText: this.mydynamic.standardText,
					standardFile: this.mydynamic.standardFile,

					/* 第三步 */
					speciesId: this.mydynamic.speciesId,
					commonProductId: this.mydynamic.commonProductId,
					commonServiceId: this.mydynamic.commonServiceId,
					industryTypeId: this.mydynamic.industryTypeId,
					expert: this.mydynamic.expert,
					/* 第四步 */
					tixing: this.mydynamic.tixing,
					con: this.mydynamic.con,
					xianshi: this.mydynamic.xianshi === '否' ? '1' : '0',
					sms: this.mydynamic.sms === '否' ? '0' : '1',
					security: security,
					money: this.mydynamic.money === '免费' ? '0' : '1',
					/* 自定义分类 */
					custom: this.mydynamic.custom,
					/* 帐号 */
					account: this.loginuserinfo.loginAccount
				}
				console.log('data', data)
				this.$api.post('/member/standard/release', data).then(response => {
					console.log('response', response)
					if (response.code === 200) {
						this.$Message.success('发布成功！')
						this.options.showPublish = false
						this.$emit('on-init')
					}
				}).catch(error => {
					console.log('error', error)
				})
			},
			changeMoney (e) {
				if("收费" === e.money) {
					window.open('/payReading')
				}
			},
			//网站来源框显示
			changeSource (e) {
				if("转载" === e.source) {
					this.isYC = true
				} else {
					this.isYC = false
				}
			},
			cancel () {
				this.$refs['mydynamic'].resetFields()
				this.$refs['pubstep1'].resetFields()
				this.process = 1
				this.current = 0
			},
			preStep() {
				this.process --
				this.current --	
			},
			next (name) {
				this.$refs[name].validate(valid=> {
					if (valid) {
						if (this.process === 2) {
							if (this.mydynamic.standardText === '' && this.mydynamic.standardFile.length === 0) {
								this.$Message.error('标准文本或者标准文件至少上传其中一种！')
							} else {
								this.process ++
								this.current ++
							}
						} else {
							this.process ++
							this.current ++
						}
					}
				})
			},
			getCon (msg) {
				this.mydynamic.standardText = msg
			}
		}
	}
</script>
<style scoped>
	.disclaimer-title{
		text-align: center;
		font-size: 14px;
		color: #00c587;
		margin-bottom: 10px;
	}
	.disclaimer-title span{
		color: #00c587;
	}
	.disclaimer-content{
		width: 664px;
		margin-left: 48px;
		background-color: #F8F8F8;
		padding-left: 15px;
		padding-top: 15px;
		padding-bottom: 15px;
	}
</style>
