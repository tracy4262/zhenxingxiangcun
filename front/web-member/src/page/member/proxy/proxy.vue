<template>
	<div class="layout">
		<top :address="false" />
		<!-- <member-header /> -->
		<div class="main">
			<div class="container">
				<Row :gutter="20">
					<!-- <i-col span="4" class="main-l">
						<high-app name="高级应用" />
						<Divider />
						<base-app name="基础应用" />
						<Divider />
						<user-app name="通用应用" />
					</i-col> -->
					<app-banner src="../../../../static/img/app-banner-proxy.png" title="代理管理">
					</app-banner>
					<i-col span="24">
						<div class="wrapper" style="height: 500px; text-align: center;" v-if="one">
							<template class="pages" v-if="stepOne">
								<div class="build-ring">
									<div class="build-ts pd20">
										<Icon type="alert-circled" style="font-size: 56px;"></Icon>
										<p style="margin-top: 30px;">在成为企业/机关代理人之前，您需要先提交认证资料，为企业/机关创建账号。</p>
									</div>
								</div>
								<div class="index-btn">
									<Button type="primary" @click="start">开始</Button>&nbsp;&nbsp;或者&nbsp;&nbsp;
									<span @click="finish" style="color:#108EE9;cursor: pointer;">以后再说</span>
								</div>
							</template>
							<template v-else>
								<Row class="add-button">
									<Button type="primary" @click="corpRegister" size="large">创建企业帐号</Button>
								</Row>
								<Row class="add-button">
									<Button type="primary" @click="govRegister" size="large">创建机关帐号</Button>
								</Row>
								<Row class="add-button">
									<Button type="primary" @click="villRegister" size="large">创建乡村帐号</Button>
								</Row>
							</template>
						</div>
						<div v-if="two">
							<div class="top-bar">
								<Cascader :render-format="format" v-model="locationIdArr" :data="locationList" :load-data="loadPositionDatas" @on-change="searchOrganization2" change-on-select style="width: 200px;">
								</Cascader>
								<Input icon="ios-search" @on-change="searchOrganization" v-model="search" :placeholder="'请输入'+type+'名称'" style="width: 200px;margin-left: 10px;" />
								<Button type="primary" @click="corpRegister" style="margin-left: 10px;" v-if="key !== '2' && type === '企业'">创建{{ type }}帐号</Button>
								<Dropdown trigger="click" style="margin-left: 10px;" v-if="key !== '2' && type === '机关'">
									<i-button type="primary">
										创建帐号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<Icon type="ios-arrow-down"></Icon>
									</i-button>
									<Dropdown-menu slot="list">
										<Dropdown-item>
											<Button type="text" class="drop-button" @click="govRegister">创建机关帐号</Button>
										</Dropdown-item>
										<Dropdown-item>
											<Button type="text" class="drop-button" @click="villRegister">创建乡村帐号</Button>
										</Dropdown-item>
									</Dropdown-menu>
								</Dropdown>
								<Dropdown trigger="click" style="margin-left: 10px;">
									<i-button type="primary">
										更多操作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<Icon type="ios-arrow-down"></Icon>
									</i-button>
									<Dropdown-menu slot="list">
										<Dropdown-item>
											<Button type="text" class="drop-button" @click="download">下载代理协议模板</Button>
										</Dropdown-item>
									</Dropdown-menu>
								</Dropdown>
							</div>
							<Row>
								<i-col span="24">
									<Tabs type="card" @on-click="getKey" :value="activeTabPane">
										<TabPane label="已代理" name="0">
											<Row>
												<ButtonGroup style="float: right;">
													<Button :type="type1" @click="changeType">企业</Button>
													<Button :type="type2" @click="changeType2">机关</Button>
												</ButtonGroup>
											</Row>
											<Row style="margin: 10px; height: 250px;">
												<Col v-if="proxyList.length === 0" span="24" style="text-align: center;">
												<img src="../../../img/no-content.png" alt="" style="width: 150px;height: 200px;">
												<h3 style="margin-top: 15px;">暂无已代理数据</h3>
												</Col>
												<Col v-if="proxyList.length !== 0" span="6" v-for="item in proxyList" :key="item.id" class="box">
												<img :src="item.logo_url" @click="onUrl(item.account)" width="85px" height="85px" style="float: left;cursor:pointer" />
												<p class="ell" :title="item.name">
													<span>&nbsp;&nbsp;{{item.name}}</span>
												</p>
												<p>
													<span>&nbsp;&nbsp;登录名：{{item.account}}</span>
												</p>
												<p>
													<span>&nbsp;&nbsp;注册时间：{{item.createTime}}</span>
												</p>
												<span>&nbsp;&nbsp;
													<Button size="small" type="primary" v-if="type === '企业'" @click="goInTo(item.account)">进入该{{ type }}</Button>
													<Button size="small" type="primary" v-if="type === '机关' && item.proxyType === '3'" @click="goInTo(item.account)">进入该机关</Button>
													<Button size="small" type="primary" v-if="type === '机关' && item.proxyType === '5'" @click="goInTo(item.account)">进入该乡村</Button>
												</span>
												</Col>
											</Row>
											<Row style="height: 200px;">
												<Page class="page" :total="total" :page-size="pageSize" show-elevator @on-change="getNextPage" :current="page"></Page>
											</Row>
										</TabPane>
										<TabPane label="未被代理" name="1">
											<Row>
												<ButtonGroup style="float: right; margin-right: 1px;">
													<Button :type="type1" @click="changeType">企业</Button>
													<Button :type="type2" @click="changeType2">机关</Button>
												</ButtonGroup>
											</Row>
											<Row style="margin: 10px; height: 250px;">
												<Col v-if="notProxyList.length === 0" span="24" style="text-align: center;">
												<img src="../../../img/no-content.png" alt="" style="width: 150px;height: 200px;">
												<h3 style="margin-top: 15px;">暂无未被代理数据</h3>
												</Col>
												<Col v-if="notProxyList.length !== 0" span="6" v-for="item in notProxyList" :key="item.id" class="box">
												<img :src="item.logoUrl" @click="onUrl(item.loginAccount)" width="85px" height="85px" style="float: left;cursor:pointer" />
												<p class="ell" :title="item.orgName">
													<span>&nbsp;&nbsp;{{item.orgName}}</span>
												</p>
												<p>
													<span>&nbsp;&nbsp;登录名：{{item.loginAccount}}</span>
												</p>
												<p>
													<span>&nbsp;&nbsp;注册时间：{{item.createTime}}</span>
												</p>
												<span>&nbsp;&nbsp;
													<Button size="small" type="primary" v-if="type === '企业'" @click="proxy(item.id)">代理该{{ type }}</Button>
													<Button size="small" type="primary" v-if="type === '机关' && item.countryType === '3'" @click="proxy(item.id, item.countryType)">代理该机关</Button>
													<Button size="small" type="primary" v-if="type === '机关' && item.countryType === '5'" @click="proxy(item.id, item.countryType)">代理该乡村</Button>
												</span>&nbsp;&nbsp;
												</Col>
											</Row>
											<Row style="height: 200px;">
												<Page class="page" :total="total2" :page-size="pageSize2" show-elevator @on-change="getNextPage2" :current="page2"></Page>
											</Row>
										</TabPane>
										<TabPane label="进度" name="2">
											<Row>
												<ButtonGroup style="float: right; margin-right: 1px;">
													<Button :type="type1" @click="changeType">认证审核进度</Button>
													<Button :type="type2" @click="changeType2">代理审核进度</Button>
												</ButtonGroup>
											</Row>
											<Row>
												<Col span="24">
												<Table style="margin-top: 20px;" border :columns="columns" :data="data"></Table>
												<Row style="height: 200px;">
													<Page class="page" :total="total3" :current="page3" :page-size="pageSize3" show-elevator @on-change="getNextPage3"></Page>
												</Row>
												</Col>
											</Row>
										</TabPane>
									</Tabs>
								</i-col>
							</Row>
						</div>
					</i-col>
				</Row>
			</div>
		</div>
		<foot></foot>
	</div>
</template>

<script>
import top from '../../../top'
import foot from '../../../foot'
import api from '~api'
import $ from 'jquery'
import highApp from '~components/memberHighApp'
import BaseApp from '~components/memberBaseApp'
import UserApp from '~components/memberUserApp'
import memberHeader from '../components/memberHeader'
import sha1 from './sha1.js'
import appBanner from '~components/app-banner'
export default {
	components: {
		top,
		foot,
		highApp,
		BaseApp,
		UserApp,
		memberHeader,
		appBanner
	},

	data() {
		return {

			one: false,
			two: false,
			stepOne: true,
			search: '',
			locationStr: '',

			total: 0,
			pageNum: 0,
			pageSize: 8,
			page: 1,

			total2: 0,
			pageNum2: 0,
			pageSize2: 8,
			page2: 1,

			total3: 0,
			pageNum3: 0,
			pageSize3: 10,
			page3: 1,

			type: '企业',
			proxyList: [],
			notProxyList: [],

			editerUrl: '',
			activeTabPane: '0',

			type1: 'primary',
			type2: 'default',

			type3: '认证',

			key: '0',
			locationList: [],
			locationIdArr: [],

			columns: [
				{
					title: '机构类型',
					key: 'type',
					align: 'center'
				},
				{
					title: '机构全称',
					key: 'full_name',
					align: 'center'
				},
				{
					title: '审核状态',
					key: 'status',
					align: 'center'
				},
				{
					title: '备注',
					key: 'mark',
					align: 'center',
					render: (h, params) => {
						return h('div',{
							domProps: {
								innerHTML: `${params.row.mark ? params.row.mark : ''}`
							}
						})
					}
				},
				{
					title: '操作',
					key: 'action',
					align: 'center',
					render: (h, params) => {
						if (params.row.status === '审核未通过') {
							if (this.type3 === '认证') {
								return h('div', [
									h('Button', {
										props: {
											type: 'error',
											size: 'small'
										},
										style: {
											marginRight: '5px',
											backgroundColor: '#0c6',
											borderColor: '#0c6'
										},
										on: {
											click: () => {
												this.showInfo(params)
											}
										}
									}, '详细')
								])
							} else if (this.type3 === '代理') {
								return h('div', [
									h('Button', {
										props: {
											type: 'error',
											size: 'small'
										},
										style: {
											marginRight: '5px',
											backgroundColor: '#0c6',
											borderColor: '#0c6'
										},
										on: {
											click: () => {
												this.showInfo(params)
											}
										}
									}, '详细'),
									h('Button', {
										props: {
											type: 'error',
											size: 'small',
											disabled: true
										},
										style: {
											marginRight: '5px'
										},
										on: {
											click: () => {
												this.confirm(params.row.id, params.row.type)
											}
										}
									}, '取消代理')
								])
							}
						} else {
							if (this.type3 === '认证') {
								return h('div', [
									h('Button', {
										props: {
											type: 'error',
											size: 'small'
										},
										style: {
											marginRight: '5px',
											backgroundColor: '#0c6',
											borderColor: '#0c6'
										},
										on: {
											click: () => {
												this.showInfo(params)
											}
										}
									}, '详细')
								])
							} else if (this.type3 === '代理') {
								return h('div', [
									h('Button', {
										props: {
											type: 'error',
											size: 'small'
										},
										style: {
											marginRight: '5px',
											backgroundColor: '#0c6',
											borderColor: '#0c6'
										},
										on: {
											click: () => {
												this.showInfo(params)
											}
										}
									}, '详细'),
									h('Button', {
										props: {
											type: 'error',
											size: 'small'
										},
										style: {
											marginRight: '5px'
										},
										on: {
											click: () => {
												this.confirm(params.row.id, params.row.type)
											}
										}
									}, '取消代理')
								])
							}
						}
					}
				}
			],
			data: []
		}
	},
	created() {
		if (this.$route.query.tag !== undefined && this.$route.query.tag !== '') {
			this.activeTabPane = this.$route.query.tag
			this.key = this.$route.query.tag
			this.two = true
			if (this.$route.query.type !== undefined && this.$route.query.type === '机关') {
				this.changeType2()
			} else if (this.$route.query.type !== undefined && this.$route.query.type === '企业') {
				this.changeType()
			}
		} else {
			// 用于判断是否显示提示引导！！！
			this.$api.post('/member/proxy/query', {
				type: '企业',
				pageNum: 0,
				pageSize: 10,
				keyName: '',
				location: '',
				approve_status: 1,
				loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
			}).then(response => {
				console.log('response333', response)
				if (response.code === 200) {
					if (response.data.list.length !== 0) {
						this.two = true
					} else {
						this.$api.post('/member/proxy/query', {
							type: '机关',
							pageNum: 0,
							pageSize: 10,
							keyName: '',
							location: '',
							approve_status: 1,
							loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
						}).then(response => {
							console.log('response333', response)
							if (response.code === 200) {
								if (response.data.list.length !== 0) {
									this.two = true
								} else {
									this.one = true
								}
							}
						})
					}
				}
			})
		}
		// 取地址
		this.$api.post('/member/town/next/4cc0ce9b1b8d1e8ab8c005056bc3816').then(res => {
			this.locationList = res.data
		})
		/*
		*   TODO 请求接口获取代理的信息展示在Tab页中
		* */
		let params = {
			type: this.type,
			pageNum: 0,
			pageSize: this.pageSize,
			keyName: this.search,
			location: this.locationStr,
			approve_status: 1,
			loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
		}
		this.query(params)
	},
	methods: {
		corpRegister() {
			this.$router.push('/member/corpRegister')
		},
		govRegister() {
			this.$router.push('/member/govRegister')
		},
		villRegister() {
			this.$router.push('/member/villRegister')
		},
		start() {
			// 开始
			this.stepOne = false
		},
		finish() {
			// 以后再说
			// this.$router.push({ path: '/member/proxy' })
			this.one = false
			this.two = true
		},
		f1(callback) {
			setTimeout(function () {
				callback();
			}, 1000);
		},
		// 已代理企业机关乡村
		query(params) {
			let that = this
			this.$api.post('/member/proxy/query', params).then(response => {
				console.log('response333', response)
				if (response.code === 200) {
					this.proxyList = response.data.list
					this.total = response.data.total
				}
			}).catch(error => {
				this.$Message.error('查询失败！')
			})
		},
		// 未代理企业
		queryCorpNotProxy(params) {
			this.$api.post('/member/corpInfo/query-corp-not-proxy', params).then(response => {
				console.log('response444', response)
				if (response.code === 200) {
					this.notProxyList = response.data.list
					this.total2 = response.data.total
				}
			}).catch(error => {
				this.$Message.error('查询失败！')
			})
		},
		// 未代理机关以及乡村
		queryGovNotProxy(params) {
			this.$api.post('/member/govInfo/query-gov-not-proxy', params).then(response => {
				console.log('response555', response)
				if (response.code === 200) {
					this.notProxyList = response.data.list
					this.total2 = response.data.total
				}
			}).catch(error => {
				this.$Message.error('查询失败！')
			})
		},
		// 代理进度查询
		queryProxyStatus(params) {
			api.post('/member/proxy/queryStatus', params).then(response => {
				console.log('res123', response)
				if (response.code === 200) {
					for (let i = 0; i < response.data.list.length; i++) {
						let obj = {
							id: response.data.list[i].id,
							type: response.data.list[i].infoType,
							full_name: response.data.list[i].name,
							// agent_account: response.data.list[i].account,
							// legal_name: response.data.list[i].legal_name,
							// credit_code: response.data.list[i].credit_code,
							status: response.data.list[i].approveStatus,
							mark: response.data.list[i].remark
						}
						this.data.push(obj)
					}
					this.total3 = response.data.total
				}
			}).catch(error => {
				this.$Message.error('查询异常！')
			})
		},
		// 认证进度查询
		queryRegisterStatus(params) {
			api.post('/member/proxy/queryInfo', params).then(response => {
				console.log('vill reg response', response)
				if (response.code === 200) {
					for (let i = 0; i < response.data.list.length; i++) {
						let obj = {
							id: response.data.list[i].id,
							type: response.data.list[i].infoType,
							full_name: response.data.list[i].name,
							status: response.data.list[i].approveStatus,
							mark: response.data.list[i].remark
						}
						this.data.push(obj)
					}
					this.total3 = response.data.total
				}
			}).catch(error => {
				this.$Message.error('查询异常！')
			})
		},
		// 机关图片跳转
		onUrl(account) {
			api.get('/member/login/findbyname/' + account)
				.then(resp => {
					if (200 === resp.code) {
						if (0 === resp.data.userType) {// 个人跳转个人主页
							this.$router.push({ path: '/personGate/index', query: { uid: account } })
						} else if (1 === resp.data.userType) {//企业
							this.$router.push({ path: '/companyGate/index', query: { uid: account } })
							//item.url = '/companyGate/index?uid=' + account
						} else if (2 === resp.data.userType) {//政府
							this.$router.push({ path: '/personGate/index', query: { uid: account } })
							//  this.editerUrl = '/personGate/index?uid='+this.editerAccount
						} else if (3 === resp.data.userType) {//机关
							this.$router.push({ path: '/govGate/index', query: { uid: account } })
							//item.url = '/govGate/index?uid=' + account
						} else if (4 === resp.data.userType) {//专家
							this.$router.push({ path: '/expertGate/index', query: { uid: account } })
							//item.url = '/expertGate/index?uid=' + account
						} else if (5 === resp.data.userType) {//乡村
							this.$router.push({ path: '/ruralGate/index', query: { uid: account } })
							//item.url = '/ruralGate/index?uid=' + account
						} else {
							//item.url = ''
						}
					}
				})
		},
		getKey(key) {
			this.key = key
			console.log('key', key)
			this.search = ''
			this.locationStr = ''
			this.locationIdArr = []
			this.type1 = 'primary'
			this.type2 = 'default'
			this.type = '企业'
			if (key === '0') {
				this.page = 1
				let params = {
					type: this.type,
					pageNum: 0,
					pageSize: this.pageSize,
					keyName: this.search,
					location: this.locationStr,
					approve_status: 1,
					loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
				}
				this.query(params)
			} else if (key === '1') {
				this.page2 = 1
				let params = {
					loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
					pageNum: 1,
					pageSize: this.pageSize2,
					corpName: this.search,
					location: this.locationStr
				}
				this.queryCorpNotProxy(params)
			} else if (key === '2') {
				this.type = ''
				this.type3 = '认证'
				this.data = []
				let params = {
					pageNum: 0,
					pageSize: this.pageSize3,
					name: this.search,
					location: this.locationStr,
					agencyer: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
				}
				this.queryRegisterStatus(params)
			}
		},
		searchOrganization() {
			/*
			* 	TODO 调用接口查询对应的机构
* */
			if (this.key === '0') {
				this.page = 1
				let params = {
					type: this.type,
					pageNum: 0,
					pageSize: this.pageSize,
					keyName: this.search,
					location: this.locationStr,
					approve_status: 1,
					loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
				}
				this.query(params)
			} else if (this.key === '1') {
				this.page2 = 1
				if (this.type === '企业') {
					let params = {
						loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
						pageNum: 1,
						pageSize: this.pageSize2,
						corpName: this.search,
						location: this.locationStr
					}
					this.queryCorpNotProxy(params)
				} else {
					let params = {
						loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
						pageNum: 1,
						pageSize: this.pageSize2,
						govName: this.search,
						location: this.locationStr
					}
					this.queryGovNotProxy(params)
				}
			} else if (this.key === '2') {
				this.page3 = 1
				this.data = []
				if (this.type3 === '认证') {
					let params = {
						pageNum: 0,
						pageSize: this.pageSize3,
						name: this.search,
						location: this.locationStr,
						agencyer: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
					}
					this.queryRegisterStatus(params)
				} else {
					this.queryProxyStatus({
						pageNum: 0,
						pageSize: this.pageSize3,
						name: this.search,
						location: this.locationStr,
						loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
					})
				}
			}
		},
		searchOrganization2(value, selectedData) {
			/*
			* 	TODO 调用接口查询对应的机构
* */
			console.log('value', value)
			console.log('selectedData', selectedData)
			let locationArr = []
			selectedData.forEach(element => {
				locationArr.push(element.label)
			})
			this.locationStr = locationArr.join('')
			console.log('locationStr', this.locationStr)
			if (this.key === '0') {
				this.page = 1
				let params = {
					type: this.type,
					pageNum: 0,
					pageSize: this.pageSize,
					keyName: this.search,
					location: this.locationStr,
					approve_status: 1,
					loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
				}
				this.query(params)
			} else if (this.key === '1') {
				this.page2 = 1
				if (this.type === '企业') {
					let params = {
						loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
						pageNum: 1,
						pageSize: this.pageSize2,
						corpName: this.search,
						location: this.locationStr
					}
					this.queryCorpNotProxy(params)
				} else {
					let params = {
						loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
						pageNum: 1,
						pageSize: this.pageSize2,
						govName: this.search,
						location: this.locationStr
					}
					this.queryGovNotProxy(params)
				}
			} else if (this.key === '2') {
				this.page3 = 1
				this.data = []
				if (this.type3 === '认证') {
					let params = {
						pageNum: 0,
						pageSize: this.pageSize3,
						name: this.search,
						location: this.locationStr,
						agencyer: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
					}
					this.queryRegisterStatus(params)
				} else {
					this.queryProxyStatus({
						pageNum: 0,
						pageSize: this.pageSize3,
						name: this.search,
						location: this.locationStr,
						loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
					})
				}
			}
		},
		corpRegister() {
			this.$router.push('/member/corpRegister')
		},
		govRegister() {
			this.$router.push('/member/govRegister')
		},
		villRegister() {
			this.$router.push('/member/villRegister')
		},
		getNextPage(page) {
			/*
			* 	TODO 根据返回的page值调用查询接口实现分页功能
			* */
			this.page = page
			let params = {
				type: this.type,
				pageNum: page,
				pageSize: this.pageSize,
				keyName: this.search,
				location: this.locationStr,
				approve_status: 1,
				loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
			}
			this.query(params)
		},
		getNextPage2(page) {
			this.page2 = page
			if (this.type === '企业') {
				let params = {
					loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
					pageNum: page,
					pageSize: this.pageSize2,
					corpName: this.search,
					location: this.locationStr
				}
				this.queryCorpNotProxy(params)
			} else {
				let params = {
					loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
					pageNum: page,
					pageSize: this.pageSize2,
					govName: this.search,
					location: this.locationStr
				}
				this.queryGovNotProxy(params)
			}
		},
		getNextPage3(page) {
			this.page3 = page
			this.data = []
			if (this.type3 === '认证') {
				let params = {
					pageNum: (page - 1) * this.pageSize3,
					pageSize: this.pageSize3,
					name: this.search,
					location: this.locationStr,
					agencyer: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
				}
				this.queryRegisterStatus(params)
			} else {
				let params = {
					pageNum: (page - 1) * this.pageSize3,
					pageSize: this.pageSize3,
					name: this.search,
					location: this.locationStr,
					loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
				}
				this.queryProxyStatus(params)
			}
		},
		goInTo (account) {
            window.open(`${window.location.origin}/pro/member?uid=${account}&type=proxy`, "_blank");
			/* // 先移除原有账户的session
			sessionStorage.removeItem('user')
			// console.log(sha1.SHA2('ccdtgf9qsessionSALT'))
			// TODO 通过account查询带被代理机构的帐号密码 直接进行登录（登录前进行提示）
			this.$api.get('/member/login/findbyname/' + account).then(response => {
				if (response.code === 200) {
					// 登录改为加密传输 传输过程中密码进行sha1加密 后台拿到密文直接进行比对（此步骤获取的密码已经为密文密码）
					this.$api.post('/member/login/login', {
						username: response.data.loginAccount,
						pwd: response.data.loginPasswd
					}).then(response => {
						if (response.data) {
							sessionStorage.setItem("user", JSON.stringify(response.data))
							this.$Message.success('代理登录成功!')
							window.location.href = `${window.location.origin}/pro/member`
						} else {
							this.$Message.error('代理登录失败!')
						}
					})
				}
			}).catch(error => {
				this.$Message.error('代理登录失败！')
			}) */
		},
		changeType() {
			this.type1 = 'primary'
			this.type2 = 'default'
			this.type = '企业'
			this.search = ''
			this.locationStr = ''
			this.locationIdArr = []
			if (this.key === '0') {
				this.page = 1
				let params = {
					type: this.type,
					pageNum: 0,
					pageSize: this.pageSize,
					keyName: this.search,
					location: this.locationStr,
					approve_status: 1,
					loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
				}
				this.query(params)
			} else if (this.key === '1') {
				this.page2 = 1
				let params = {
					loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
					pageNum: 1,
					pageSize: this.pageSize2,
					corpName: this.search,
					location: this.locationStr
				}
				this.queryCorpNotProxy(params)
			} else if (this.key === '2') {
				this.type = ''
				this.type3 = '认证'
				this.data = []
				this.page3 = 1
				let params = {
					pageNum: 0,
					pageSize: this.pageSize3,
					name: this.search,
					location: this.locationStr,
					agencyer: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
				}
				this.queryRegisterStatus(params)
			}
		},
		changeType2() {
			this.type2 = 'primary'
			this.type1 = 'default'
			this.type = '机关'
			this.search = ''
			this.locationStr = ''
			this.locationIdArr = []
			if (this.key === '0') {
				this.page = 1
				let params = {
					type: this.type,
					pageNum: 0,
					pageSize: this.pageSize,
					keyName: this.search,
					location: this.locationStr,
					approve_status: 1,
					loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
				}
				this.query(params)
			} else if (this.key === '1') {
				this.page2 = 1
				let params = {
					loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount,
					pageNum: 1,
					pageSize: this.pageSize2,
					govName: this.search,
					location: this.locationStr
				}
				this.queryGovNotProxy(params)
			} else if (this.key === '2') {
				this.type = ''
				this.type3 = '代理'
				this.data = []
				this.page3 = 1
				this.queryProxyStatus({
					pageNum: 0,
					pageSize: this.pageSize3,
					name: this.search,
					location: this.locationStr,
					loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
				})
			}
		},
		format(labels, selectedData) {
			let locationStr = ''
			labels.forEach(item => {
				locationStr += item
			})
			return locationStr
		},
		loadPositionDatas(item, callback) {
			item.loading = true
			this.$api.post(`/member/town/next/${item.value}`).then(res => {
				item.loading = false
				item.children = res.data
				callback()
			})
		},
		proxy(id, type = '3') {
			console.log('111111111')
			if (this.type === '企业') {
				this.$router.push({
					name: 'corpProxy',
					params: {
						id: id
					}
				})
			} else if (this.type === '机关') {
				if (type === '3') {
					this.$router.push({
						name: 'govProxy',
						params: {
							id: id,
							type: 'gov'
						}
					})
				} else if (type === '5') {
					this.$router.push({
						name: 'villProxy',
						params: {
							id: id,
							type: 'gov'
						}
					})
				}
			}
		},
		remove(id, type) {
			console.log(id, type)
			if (this.type3 === '代理') {
				/*
				 *   TODO 请求取消代理接口取消相应的代理
				 * */
				api.post('/member/proxy/delProxy', { id: id, flag: type === '企业' ? 0 : 1 }).then(response => {
					if (response.code === 200) {
						this.data = []
						this.queryProxyStatus({
							pageNum: (this.page - 1) * this.pageSize3,
							pageSize: this.pageSize3,
							name: this.search,
							location: this.locationStr,
							loginAccount: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
						})
					} else {
						this.$Message.error('取消代理失败！')
					}
				}).catch(error => {
					this.$Message.error('服务器异常！')
				})
			} else if (this.type3 === '认证') {
				/*
				 *   TODO 请求取消认证接口取消相应的认证
				 *   用户不允许取消认证
				 * */
				/* api.post('/member/proxy/delProxyInfo', {id: id, flag: type === '企业' ? 0 : 1}).then(response => {
					if (response.code === 200) {
						this.data = []
						let params = {
							pageNum: this.page3,
							pageSize: this.pageSize3,
							name: this.search,
							location: this.locationStr,
							agencyer: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
						}
						this.queryRegisterStatus(params)
					} else {
						this.$Message.error('取消认证失败！')
					}
				}).catch(error => {
					this.$Message.error('服务器异常！')
				}) */
			}
		},
		showInfo(params) {
			console.log('params', params)
			// 根据不同的类型去不同的详情页
			if (params.row.type === '企业') {
				this.$router.push({
					path: '/member/corpDetail',
					query: {
						id: params.row.id,
						tag: this.type3 === '认证' ? 'register' : 'proxy'
					}
				})
			} else if (params.row.type === '机关') {
				this.$router.push({
					path: '/member/govDetail',
					query: {
						id: params.row.id,
						tag: this.type3 === '认证' ? 'register' : 'proxy'
					}
				})
			} else if (params.row.type === '乡村') {
				this.$router.push({
					path: '/member/villDetail',
					query: {
						id: params.row.id,
						tag: this.type3 === '认证' ? 'register' : 'proxy'
					}
				})
			}
		},
		confirm(id, type) {
			this.$Modal.confirm({
				title: '操作提示',
				content: this.type3 === '认证' ? '<p>是否确认取消认证？</p><p>如果该机构已被代理，则代理也会被删除。</p>' : '<p>是否确认取消代理？</p>',
				onOk: () => {
					this.remove(id, type)
				},
				onCancel: () => {
				}
			})
		},
		download() {
			this.$Message.info('开发中...')
		}
	}
}
</script>
<style scoped>
.box {
  margin-top: 10px;
}
.add-button {
  margin-left: 100px;
  margin-top: 20px;
  margin-right: 20px;
  /* float: right; */
}
.page {
  float: right;
  margin-top: 20px;
}
.drop-button {
  padding-top: 0;
  padding-bottom: 0;
}
.top-bar {
  position: absolute;
  z-index: 999;
  right: 20px;
  display: flex;
}
</style>
