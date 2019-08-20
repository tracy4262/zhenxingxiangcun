<template>
	<div class="layout">
		<!-- 导航栏 -->
		<Affix>
			<top :address="false"></top>
		</Affix>
		<!-- 会员中心头部 -->
		<member-header @showUserGuide="showUserGuide" :isMemberCenter="true"/>
		<!-- main开始 -->
		<div class="main">
			<div class="container">
				<Row>
					<Col span="4">
						<div class="main-l">
							<high-app name="高级应用" />
							<Divider />
							<base-app name="基础应用" />
							<Divider />
							<third-app name="第三方应用" />
						</div>
					</Col>
					<Col span="20">
						<!-- 导航管理 -->
						<Modal v-model="modalc" width="450" title="导航管理" :closable="true" :mask-closable="false" @on-ok="saveColumn">
							<Row type="flex" justify="center" align="middle" v-for="(singlecolum,index) in columns" :key="index" style="font-size: 16px;margin-top: 10px;text-align: center;">
								<Col span="8">
									{{singlecolum.name}}
								</Col>
								<Col span="8" v-if="singlecolum.name === '动态'">
									<i-switch  v-model="singlecolum.status" size="large" :disabled="disabled">
										<span slot="open">显示</span>
										<span slot="close">隐藏</span>
									</i-switch>
								</Col>
								<Col span="8" v-else>
									<i-switch  v-model="singlecolum.status" size="large">
										<span slot="open">显示</span>
										<span slot="close">隐藏</span>
									</i-switch>
								</Col>
							</Row>
						</Modal>
						<!-- 动态政策知识发布 -->
						<publish :options="options" @showDany="closePublish"  ref="publish"/>
						<!-- 标准发布 -->
						<standardPublish :options="standardOptions" @showDany="closePublish" ref="standardPublish"/>
						<!-- 栏目设置开始 -->
						<div class="main_r_d">
							<Row>
								<Col span="18" >
									<Row type="flex" justify="center" align="middle" class="publish_header mb10 mr10">
										<Col span="20" class="publish_con">
											<img src="../../img/publish_icon.png" class="mr20"/>
											<span >您可以点击发布说点什么！</span>
										</Col>
										<Col span="2" class="publish_con mr20" >
											<Button type="primary" v-if="columnNameForPublish === '动态'" @click="showDany">发布动态</Button>
											<Button type="primary" v-if="columnNameForPublish === '政策'" @click="showDany">发布政策</Button>
											<Button type="primary" v-if="columnNameForPublish === '知识'" @click="showDany">发布知识</Button>
											<Button type="primary" v-if="columnNameForPublish === '产品'" @click="showDany">发布产品</Button>
											<Button type="primary" v-if="columnNameForPublish === '服务'" @click="showDany">发布服务</Button>
											<Button type="primary" v-if="columnNameForPublish === '标准'" @click="showDany">发布标准</Button>
										</Col>
									</Row>
									<!-- 最外层tab -->
									<Tabs @on-click="tabsClick" class="tabs-edit-btn mr10">
										<Button type="default" size="small" slot="extra" @click="showColumn" v-show="isShowGudie">导航管理</Button>
										<Tab-pane v-for="(item1,index) in columnSetting" :label="item1.name" :key="index" v-if="item1.status">
											<!-- 第二层tab -->
											<Tabs @on-click="showMydynamic(index)" :animated="false" v-model="dyan" value="name1"
												v-if="columnNameForPublish === '动态' || columnNameForPublish === '政策' || columnNameForPublish === '知识'">
												<TabPane :label="'我的'+item1.name" name="name1">
													<!-- 第三层tab -->
													<Tabs @on-click="showColumnList" :animated="false" >
														<TabPane v-for="(info,index) in smallPaneData" :label="info.name" :key="index">
															<div class="pd10" v-if="columnList.length===0">
																<p>还没有相关动态！</p>
															</div>
															<div v-else>
																<div>
																	<articles :dataType="item1.name" :data="item" v-for="(item,index) in columnList" :key="index" class="mb15"/>
																</div>
															</div>
														</TabPane>
													</Tabs>
												</TabPane>

												<TabPane :label="'关注'+item1.name" :animated="false" name="name2">
													<articles :dataType="item1.name" :data="item" v-for="(item,index) in mydynamic" :key="index" class="mb15"/> 
												</TabPane>

											</Tabs>
											<div class="tc" v-if="columnNameForPublish === '产品' || columnNameForPublish === '服务'">
												<img src="../../img/developing.png"/>
												<p>开发中,敬请期待···！</p>
											</div>
											<div v-if="columnNameForPublish === '标准'">
												<standard-list ref="staList" v-if="columnNameForPublish === '标准'"></standard-list>
											</div>
										</Tab-pane>
									</Tabs>
								</Col>
								<Col span="6">
									<div style="padding: 0px 10px;border: 1px solid #ededed;margin-bottom: 20px;padding-bottom: 20px">
										<div class="live">
											<p style="font-size: 14px;float: right;margin-top: 20px;color: #333">
												<a :href="this.$url.liveVideo+'?account='+loginuserinfo.loginAccount">
													逛直播
												</a>
												<Icon type="ios-arrow-right"></Icon>
											</p>
											<h3 style="text-align: center;font-size: 20px;line-height: 58px">直播</h3>
											<img src="../../../static/datas/img/live.png" alt="" width="100%">
											<a :href="this.$url.liveVideo+'/liveVideoSetting?account='+loginuserinfo.loginAccount"  class="to-live">
													我要直播
												</a>
										</div>
									</div>
									<div style="padding: 0px 10px;border: 1px solid #ededed;margin-bottom: 20px;padding-bottom: 20px" v-if="false">
										<p style="font-size: 14px;float: right;margin-top: 20px;color: #333">
											<a href="javascript:;" @click="toCircle">逛圈子</a>
											<Icon type="ios-arrow-right"></Icon>
										</p>
										<h3 style="text-align: center;font-size: 20px;line-height: 58px">圈子</h3>
										<Input v-model="cname" placeholder="请输入..." style="width: 100%">
										<Icon type="ios-search" slot="append" @click.native="coteries"></Icon>
										</Input>
										<Row style="margin-top: 14px">
											<Col span="8" style="text-align: center" v-for="(c,index) in coterie" :key="index">
											<img src="../../img/images/quanzix-img01.png" alt="">
											<router-link :to="{ path: '/circleDetail', query: { 'id': c.id }}">
												<p class="ell">{{c.coterieName}}</p>
											</router-link>
											</Col>
										</Row>
										<Page :total='totalC' :page-size="pageSize_f" show-total :current='currentPageC' size="small" @on-change="nextPageC"></Page>
									</div>
								</Col>
							</Row>
						</div>
					</Col>
				</Row>
			</div>
		</div>
		<!-- main结束 -->

		<!-- 第一次认证引导 -->
		<div class="layout">
			<Modal v-model="modal_r" width="850" :closable="true" :mask-closable="false" @on-cancel="cancel" >
				<div style="margin-top: 17px;">
					<vui-steps :data="stepData" :active="current"></vui-steps>
				</div>
				<transition>
					<router-view></router-view>
				</transition>
				<div slot="footer" style="border: none;">
				</div>
			</Modal>
		</div>

		<!-- 重启会员引导 -->
		<div class="layout">
			<Modal v-model="modal3" width="850" :closable="true" :mask-closable="false" @on-cancel="cancelSec" style="border: none;" :styles="{top: '20px'}">
				<div style="margin-top: 17px;">
					<vui-steps :data="stepDataSec" :active="current"></vui-steps>
				</div>
				<transition>
					<router-view></router-view>
				</transition>
				<div slot="footer" style="border: none;">
				</div>

			</Modal>

			<!-- 关闭提示 -->
			<Modal v-model="modalsure">
				<p>确认关闭导航认证？</p>
				<div slot="footer" class="footer-btn" v-if="modal3===true">
					<i-button type="primary" @click="cancel">确认</i-button>
					<i-button type="primary" @click="modalsure=!modalsure">取消</i-button>
				</div>
				<div slot="footer" class="footer-btn" v-if="modal_r===true">
					<i-button type="primary" @click="cancelSec">确认</i-button>
					<i-button type="primary" @click="modalsure=!modalsure">取消</i-button>
				</div>
			</Modal>
		</div>
		<foot></foot>
	</div>
</template>
<script>
	import util from 'util'
	import foot from '../../foot'
	import top from '../../top'
	import publish from '../../components/publish.vue'
	import highApp from '~components/memberHighApp'
	import BaseApp from '~components/memberBaseApp'
	import UserApp from '~components/memberUserApp'
	import ThirdApp from '~components/memberThirdApp'
	import memberHeader from './components/memberHeader'
	import vuiSteps from '../../components/vui-steps'
	import standardPublish from './components/standardPublish'
	import standardList from './components/standardList'
	import articles from './components/articles.vue'


	var _this = this
	export default {
		components: {
			publish,
			highApp,
			BaseApp,
			ThirdApp,
			memberHeader,
			UserApp,
			vuiSteps,
			foot,
			top,
			standardPublish,
			standardList,
			articles
		},
		data() {
			//身份证验证规则
			return {
				dyan:'name1',
				modalsure: false,
				transfer: true,
				reg1: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
				reg2: /^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$/,
				//发布组件
				options: {
					realUrl: '',
					uploadId: 'DanyId',
					knowledgeShow: false,
					policyShow: false,
					infoShow: false,
					showAll: true,
					showPublish: false,
					categorys: [],
					columnSetting:[],
					title: '动态'
				},
				standardOptions: {
					realUrl: '',
					uploadId: 'DanyId',
					knowledgeShow: false,
					policyShow: false,
					infoShow: false,
					showAll: true,
					showPublish: false,
					columnSetting:[],
					title: '标准'
				},
				stepData:[
				{name: '实名认证', url: ''},
				{name: '设置栏目', url: ''},
				{name: '个性化',   url: ''},
				{name: '应用设置', url: ''},
				{name: '完善信息', url: ''},
				{name: '网站设置', url: ''}],
				stepDataSec:[
				{name: '实名认证', url: '/pro/member/progress1'},
				{name: '设置栏目', url: '/pro/member/progress3'},
				{name: '个性化',   url: '/pro/member/progress5'},
				{name: '应用设置', url: '/pro/member/progress19'},
				{name: '完善信息', url: '/pro/member/progress22'},
				{name: '网站设置', url: '/pro/member/progress34'}],
				likeBtn: false, //是否可点赞
				modalc: false,
				columns: [],
				findFriend: '', //搜索好友
				//圈子
				coterie: [],
				cname: '',
				totalC: 0,
				currentPageC: 1,
				disabled:true,
				//页签发布
				comlist: [],
				tabplans: [],
				modalp: false,
				isPlan: false,
				modalp1: false,
				labels: [],
				item: {},
				index: '',
				namep: '',
				publishPoly: false,
				publishLed: false,
				jing: 'jing',
				huifu: '',
				huifu2:'',
				publishShow: false,
				modal2: false,
				modal_r: false, //进入第一次认证
				stepr: '',
				modal3: false,
				current: 0, //进度步骤
				count1: 1, //step值
				loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
				portal: '',
				theme1: 'light',
				value13: '',
				total: 0,
				pageSize: 10,
				pageSize_f: 5,
				currentPage: 1,
				modal_loading: false,
				modal3: false,
				modal4: false,
				modal5: false,
				favorite: '0',
				theme1: 'light',
				count: '',
				number: '0',
				displayName: '',
				signature: '还没有签名！',
				dateOne: '',
				dateTwo: '',
				dateThree: '',
				weekOne: '',
				weekTwo: '',
				weekThree: '',
				value: '',
				list: [],
				columnSetting: [],
				columnMydynamic: [],
				mydynamic:[],
				columnList: [],
                tabIndex:0,
				formInline: {
					user: '',
					password: ''
				},
				con: {
					content: null,
					content1: null,
				},
				friend1: [],
				friend: [],
				user: sessionStorage.getItem(sessionStorage.getItem('key')),
				mydynamicComment: {
					dynamicId: '',
					content: ''
				},
				tabs: 0,
				inforMation: [],
				knowLedge: [],
				policy: [],
				ruleInline: {},
				isShowGudie: false,
				columnSettingForPublish: [],
				columnNameForPublish: '动态',
				showComment: false,
				userType: -1,
				smallPaneData: [],
				allSmallPaneData: [],
				account:'',
				datum:''
			}
		},
		created: function() {
			this.querySmallPane()
			this.checkAuth()
			this.getLabel('动态')

			this.findMyDnamic()
			this.findMyFollow()
			this.listpane()
			this.coteries()
			this.memberSearch()


			//默认动态
			this.defaultAttention()
		},
		methods: {
			// 打开用户向导
			showUserGuide () {
				// this.goToAuth(0, this.userType)
				if (this.$user.userType === 10 || this.$user.userType === '10') {
					this.$router.push(`user-auth-admin/auth/step1`)
				} else {
					this.$router.push(`/auth/step1`)
				}
			},
			substr (str) {
				return str.substring(0, 10)
			},
			// 查询每个页签下的小标签（自定义标签）
			querySmallPane () {
				this.$api.get('/member/columnSettings/findColumnByUserSettings?account=' + this.loginuserinfo.loginAccount).then(response => {
					console.log('response000', response)
					if (response.code === 200) {
						this.allSmallPaneData = response.data
						this.smallPaneData = this.allSmallPaneData[0].userSettings
					}
				}).catch(error => {
					this.$Message.error('获取自定义标签出错！')
				})
			},
			//发布
			showDany () {
				// 先判断是否实名
				this.$api.post('/member/login/findCurrentUser', {
					account: this.loginuserinfo.loginAccount
				}).then(res => {
					// 判断是否实名 企业机关专家由于是代理过来的 默认已实名
					console.log('res', res)
					if (res.data.userType === 0 || res.data.userType === 4) {
						if (res.data.isRealIdentity && res.data.isRealIdentity === 'Y') {
							this.publish()
						} else {
							this.$Message.error('请先实名认证!')
						}
					} else {
						this.publish()
					}
				})
			},
			publish () {
				// 政策知识资讯的发布是在同一个页面中的 产品服务标准的发布分别在新的页面中
				if (this.columnNameForPublish === '动态') {
					this.options.title = '动态'
					this.options.showPublish = true
					this.options.infoShow = true
					this.options.policyShow = false
					this.options.knowledgeShow = false
					this.options.realUrl = '/inforMation/publish'
					this.options.columnSetting=this.columnSetting
					this.$refs.publish.showMydynamic(0)
					this.options.categorys = [
						{
							label: '会员动态',
							value: '020107'
						},
						{
							label: '行业动态',
							value: '020101'
						},
						{
							label: '企业动态',
							value: '020102'
						},
						{
							label: '专家动态',
							value: '020103'
						},
						{
							label: '乡村动态',
							value: '020104'
						},
						{
							label: '群协动态',
							value: '020105'
						},
						{
							label: '国际动态',
							value: '020106'
						},
						{
							label: '城乡互动',
							value: '020108'
						}
					]
				} else if (this.columnNameForPublish === '政策') {
					this.options.title = '政策'
					this.options.showPublish = true
					this.options.infoShow = true
					this.options.policyShow = false
					this.options.knowledgeShow = false
					this.options.realUrl = '/inforMation/publish'
					this.options.columnSetting=this.columnSetting
					this.$refs.publish.showMydynamic(1)
				} else if (this.columnNameForPublish === '知识') {
					this.options.title = '知识'
					this.options.showPublish = true
					this.options.infoShow = true
					this.options.policyShow = false
					this.options.knowledgeShow = false
					this.options.realUrl = '/inforMation/publish'
					this.options.columnSetting=this.columnSetting
					this.$refs.publish.showMydynamic(2)
				} else if (this.columnNameForPublish === '产品') {
					// this.options.title = '产品'
					// this.$refs.publish.showMydynamic(3)
					this.$router.push('/release-goods/step1')
					// this.$Message.info('产品发布努力开发中...')
				} else if (this.columnNameForPublish === '服务') {
					// this.options.title = '服务'
					// this.$refs.publish.showMydynamic(4)
					this.$Message.info('服务发布努力开发中...')
				} else if (this.columnNameForPublish === '标准') {
					this.standardOptions.title = '标准'
					this.standardOptions.showPublish = true
					this.standardOptions.infoShow = true
					this.standardOptions.policyShow = false
					this.standardOptions.knowledgeShow = false
					this.standardOptions.realUrl = '/inforMation/publish'
					this.standardOptions.columnSetting=this.columnSetting
					this.$refs.standardPublish.showMydynamic(5)
				}
			},
			closePublish() {
				this.options.showPublish = false
			},
			memberSearch() {
				this.$api.get('/member/memberCenter/index')
					.then(response => {
						if(response.data != null) {
							this.favorite = response.data.favorite
							this.dateOne = response.data.dateOne
							this.dateTwo = response.data.dateTwo
							this.dateThree = response.data.dateThree
							this.weekOne = response.data.weekOne
							this.weekTwo = response.data.weekTwo
							this.weekThree = response.data.weekThree
							this.number = response.data.number
							this.signature = response.data.signature
							this.displayName = response.data.displayName
							this.signNum = response.data.signNum
						}
						if(this.signNum != '') {
							// $('#SignIn').attr('disabled', 'disabled')
						}
					})
			},
			//获得标签列表
			listpane() {
				var _this = this;
				this.$api.get('/member/columnSettings/listlabel')
					.then(response => {
						var result = response.data
						if(result.length) {
							result.forEach((r) => {
								_this.tabplans.push(r)
							})
						} else {
							_this.tabplans.push('全部')
						}
					})
			},
			// 链接圈子
            toCircle() {
                //判断是否开通圈子
                this.$api.post(this.$url.circle + '/circle/toLogin', {nameOrEmail: this.loginuserinfo.loginAccount }).then(
                    resp => {
                        if (resp.data.sc) {
                            window.open(this.$url.circle + '/circle/toLogin?account=' +this.loginuserinfo.loginAccount)
                        } else {
                            if( -1 === resp.data.data ){
                                this.$Message.error('没有开通圈子')
                                this.$Message.success('开通圈子')
                                this.$api.post(this.$url.circle + '/circle/openAccount',
                                    {
                                        userName: this.loginuserinfo.loginAccount,
                                        userPassword: this.loginuserinfo.loginAccount,
                                        userAppRole: '0',
                                        userEmail: '',
                                        avatar: this.loginuserinfo.avatar
                                    })
                                    .then(resp => {
                                        if(resp.data.sc) {
                                            this.$Message.error('开通成功')
                                            window.open(this.$url.circle + '/circle/toLogin?account=' + this.loginuserinfo.loginAccount)
                                        }else {
                                            this.$Message.error('开通失败!')
                                        }
                                    })
                            }else if(0 === resp.data.data) {
                                this.$Message.error('该账户异常')
                            }
                        }
                    }
                )
            },
			/**
			 * 圈子
			 */
			coteries() {
				this.$api.get('/member/CoterieArticle/search/' + this.currentPageC + '?name=' + this.cname)
					.then(response => {
						this.coterie = response.data.list
						this.totalC = response.data.total

					})
			},
			//圈子下一页
			nextPageC() {
				this.$api.get('/member/CoterieArticle/search/' + this.currentPageC + '?name=' + this.coterie)
					.then(response => {
						this.coterie = response.data.list
						this.totalC = response.data.total

					})
			},
			//查询动态
			findMyDnamic() {
				if(!this.pageSize) {
					this.pageSize = 10
				}
				if(!this.currentPage) {
					this.currentPage = 1
				}
				this.$api.get('/member/mydynamic/findMydynamic/' + this.currentPage + "?pageSize=" + this.pageSize + "&label=" + '全部')
					.then(response => {
						var d = response.data
						console.log('查询动态',d)
						d.list.forEach(item => {
							item.disabled = false
							item.showReply = false
						})
						this.list = d.list

					})
			},
			//栏目标签列表
			findMyFollow() {
				this.$api.get('/member/columnSettings/findColumnSetting?uid=' + this.$user.loginAccount).then(response => {
					console.log('lanmu', response)
					this.columnSetting = response.data
					if (this.columnSetting.length) {
						this.columns = []
						this.columnSetting.forEach(e => {
							let cu={
								name: e.name,
								status: e.status,
								authority: e.authority
							}
							this.columns.push(cu)
						})
						this.isShowGudie = true
					}
					this.columnSettingForPublish = this.columnSetting.filter(item => {
						return item.status
					})
				})
			},
			//显示我的政策 知识 咨询下面的页签,即点击第二层触发的方法
			showMydynamic(index) {
				var type = this.columnSetting[index].name
				if(type==='动态'){
					this.defaultAttention()
				}else if(type==='政策'){
					this.defaultPolicy()
				}else if(type==='知识'){
					this.defaultKnowledge()
				}
				this.getLabel(type)
			},
			getLabel (type) {
				this.$api.get('/member/columnSettings/findColumnLabel?columnName=' + type + '&account=' + this.loginuserinfo.loginAccount).then(response => {
					console.log('response666', response)
					this.columnMydynamic = []
					this.columnMydynamic = response.data
					if (this.columnMydynamic.length) {
						this.showColumnList(0)
					}
				})
			},
			//我的政策 、知识下面小页签的详情显示
			showColumnList(index) {
				this.$api.get('/member/columnSettings/findColumnList?label=' + this.columnMydynamic[this.tabIndex].userSettings[index].name +
						'&columnId=' + this.columnMydynamic[this.tabIndex].typeName + '&currentPage=' + this.currentPage + "&pageSize=" + this.pageSize)
					.then(response => {
						if (response.data) {
							var list = response.data
							console.log('小页签',response.data)
							list.forEach(item => {
								item.disabled = false
								item.showReply = false
								if(item.list.length) {
									item.list.forEach(l => {
										l.disabledchi = false
										l.showReplychi = false
										l.replyChildren=[]
										l.showOrHideComment = true
									})
								}
							})
							this.columnList = list
						}
					})
			},
			//关注详情
			defaultAttention() {
				this.$api.get('/member/columnSettings/findColumnListDany?columnId=' + '动态' + '&currentPage=' + this.currentPage)
					.then(response => {
						this.mydynamic=[]
						if(response.data !== undefined){
							var list = response.data
							if(list.length){
								this.mydynamic=list
							}
						}
					})
			},
			defaultPolicy() {
				this.$api.get('/member/columnSettings/findColumnListDany?columnId=' + '政策' + '&currentPage=' + this.currentPage)
					.then(response => {
						console.log('默认关注zc-----')
						var list = response.data
						this.mydynamic=[]
						if(list.length){
							this.mydynamic=list
						}
					})
			},
			defaultKnowledge() {
				this.$api.get('/member/columnSettings/findColumnListDany?columnId=' + '知识' + '&currentPage=' + this.currentPage)
					.then(response => {
						var list = response.data
						this.mydynamic=[]
						if(list.length){
							this.mydynamic=list
						}
					})
			},
			showColumnList1(name) {
				if(name === '全部') {
					this.findMyDnamic()
				} else {
					this.$api.get('/member/mydynamic/findMydynamic/' + this.currentPage + "?pageSize=" + this.pageSize + "&label=" + name)
						.then(response => {
							var d = response.data
							d.list.forEach(item => {
								item.disabled = false
								item.showReply = false
							})
							this.list = d.list
						})
				}

			},
			/* showHuiFu(item) {
				if(this.loginuserinfo) {
					item.showReply = !item.showReply
				} else {
					alert("请先登录！")
				}
			}, */
			/*
			 * 回复
			 * */
			/* reply(item, columnId, index) {
				var _url = ''
				if('政策' === columnId) {
					_url = '/member/policy'
				} else if('知识' === columnId){
					_url = '/member/knowLege'
				}else if('动态' === columnId){
					_url = '/member/inforMation'
				}
				api.post(
					_url + '/saveHuiFu', {
						dynamicId: item.id,
						comment: this.huifu
					}
				).then(response => {
					var flag = response.data
					if(flag === 1) {
						this.replyList(item, columnId, index)
						this.columnList[index].showReply = false
						this.huifu = ""
					} else {
						this.$Message.error('回复失败!')
					}
				})

			}, */
			//回复成功后的回调
			/* replyList(item, columnId, index) {
				var _url = ''
				if('政策' === columnId) {
					_url = '/member/policy'
				}else if('知识' === columnId){
					_url = '/member/knowLege'
				}else if('动态' === columnId){
					_url = '/member/inforMation'
				}
				api.post(
					_url + '/replylist', {
						replyid: item.id,
						currentPage: this.currentPage
					}
				).then(res => {
					if(res.code === 200) {
						if(res.data.length){
								res.data.forEach(e=>{
									e.showReplychi=false
									e.replyChildren=[]
								})
							}
	                    this.columnList[index].list = res.data
					} else {
						this.$Message.error('查询失败!')
					}
				})
			},  */
			/*
			 * 回复他人的评论
			 * */
			replyChildren(item, columnId) {
				var _url = ''
				if('政策' === columnId) {
					_url = '/member/policy'
				} else if('知识' === columnId){
					_url = '/member/knowLege'
				}else if('动态' === columnId){
					_url = '/member/inforMation'
				}
				this.$api.post(
					 _url+'/saveHuiFu', {
					 	postId:item.id,
						comment: this.huifu2,
						dynamicId:''
					}
				).then(response => {
					var flag = response.data
					if(flag == '1') {
						item.showReplychi=false
						item.showComment = true
						this.huifu2 = ""
						this.$Message.success('回复成功!')
						this.replyListChildren(item, columnId)
					}else{
						this.$Message.error('回复失败!')
					}
				})

			},
			//回复他人评论成功后的回调
			replyListChildren(item, columnId) {
				var _url = ''
				if('政策' === columnId) {
					_url = '/member/policy'
				} else if('知识' === columnId){
					_url = '/member/knowLege'
				}else{
					_url= '/member/inforMation'
				}
				this.$api.post(
					_url + '/replyChildrenlist', {
						postId: item.id,
						currentPage: this.currentPage
					}
				).then(res => {
					if(res.code === 200) {
						item.replyChildren=res.data.list
						item.showOrHideComment = false
						console.log('评价回复',res.data.list)
					} else {
						this.$Message.error('查询失败!')
					}
				})
			},
			/**
			 * 回复下面他人的点赞
			 * */
			handleLikeChi(item, columnId) {
				var _url = ''
				if('政策' === columnId) {
                    this.$api.post('/member/thumb/detailThumbCommentAdd',{
                        account: this.loginuserinfo.loginAccount,
                        commentId:parseInt(item.id),
                        commentType:'policy'
                    } ).then(response => {
                        if (response.code === 200) {
                            if(response.data === 'exist'){
                                this.$Message.error('您已点赞')
                            } else{
                                this.$Message.success('点赞成功')
                                item.thumbUpNum++
                            }
                        }
                    })
				} else if('知识' === columnId) {
                    this.$api.post('/member/thumb/detailThumbCommentAdd',{
                        account: this.loginuserinfo.loginAccount,
                        commentId:parseInt(item.id),
                        commentType:'knowledge'
                    } ).then(response => {
                        if (response.code === 200) {
                            if(response.data === 'exist'){
                                this.$Message.error('您已点赞')
                            } else{
                                this.$Message.success('点赞成功')
                                item.thumbUpNum++
                            }
                        }
                    })
				} else if('动态' === columnId) {
                    this.$api.post('/member/thumb/detailThumbCommentAdd',{
                        account: this.loginuserinfo.loginAccount,
                        commentId:parseInt(item.id),
                        commentType:'information'
                    } ).then(response => {
                        if (response.code === 200) {
                            if(response.data === 'exist'){
                                this.$Message.error('您已点赞')
                            } else{
                                this.$Message.success('点赞成功')
                                item.thumbUpNum++
                            }
                        }
                    })
				}
			},
			showHuiFuChi(item) {
				if(this.loginuserinfo) {
					item.showReplychi = !item.showReplychi
				} else {
					alert("请先登录！")
				}
			},
			danyReply(item, index) {
				this.$api.post(
					'/member/mydynamic/saveHuiFu', {
						dynamicId: item.id,
						comment: this.huifu
					}
				).then(response => {
					var flag = response.code
					if(flag === 200) {
						this.list[index].showReply = false
						this.huifu = ""
					} else {
						this.$Message.error('回复失败!')
					}
				})
			},
			nextPage(page) {
				this.currentPage = page;
				this.$api.get('/member/login/friendlist/' + this.currentPage)
					.then(response => {
						this.friend1 = response.data.list
						this.total = response.data.total
					})
			},
			tabsClick (index) {
				console.log(index)
				this.tabIndex = index
				this.columnNameForPublish = this.columnSettingForPublish[index].name
				this.showMydynamic(index)
				console.log('this.columnNameForPublish', this.columnNameForPublish)
				// 切换第一级页签时 改变第三级中的小页签
				this.allSmallPaneData.forEach(element => {
					if (element.typeName === this.columnNameForPublish) {
						this.smallPaneData = element.userSettings
					}
				})
				console.log(this.smallPaneData)
			},
			// 检查认证到了第几步
			checkAuth () {
				this.$api.post('/member-reversion/realStep/findEnableStep', {
					account: this.$user.loginAccount
				}).then(response => {
					console.log('check auth response', response)
					if (response.code === 200) {
						if (response.data) {
							// 说明已经进行了实名认证根据步骤跳转到相应的实名认证步骤 step为7说明实名认证已完成
							if (response.data.step !== '7') {
								this.$Notice.config({
									top: 120,
									duration: 10
								})
								this.$Notice.info({
									title: '实名认证',
									render: h => {
										return h('span', [
											'您还未完成实名认证，是否 ',
											h('a', {
												style: {
													color: '#00c587'
												},
												on: {
													click: () => {
														// this.goToAuth(this.$user.isIdentityVerification, response.data.userType)
														if (response.data.step === '6') {
															this.$router.push({
																path: `/auth/step6`,
																query: {
																	templateId: response.data.templateId
																}
															})
														} else if (response.data.step === '6.4') {
															this.$router.push({
																path: `/auth/step7`,
																query: {
																	templateId: response.data.templateId
																}
															})
														} else {
															let step = parseInt(response.data.step) + 1
															this.$router.push({
																path: `/auth/step${step}`,
																query: {
																	templateId: response.data.templateId
																}
															})
														}
														this.$Notice.destroy()
													}
												}
											}, '继续认证 '), '？'
										])
									}
								})
							}
						} else {
							// 说明还未进行过实名认证
							this.$Notice.config({
								top: 120,
								duration: 10
							})
							this.$Notice.info({
								title: '实名认证',
								render: h => {
									return h('span', [
										'您还未完成实名认证，是否 ',
										h('a', {
											style: {
												color: '#00c587'
											},
											on: {
												click: () => {
													this.$router.push({
														path: `/auth/step1`
													})
													this.$Notice.destroy()
												}
											}
										}, '继续认证 '), '？'
									])
								}
							})
						}
					}
				})
			},
			// 跳转认证页面
			goToAuth (isIdentityVerification, userType) {
				// 个人用户或者专家用户 /auth/step1
				console.log(isIdentityVerification)
				if (userType === 0 || userType === 4 || userType === 2) {
					this.$router.push(`/auth/step${isIdentityVerification}`)
				} else if (userType === 1) {
					// 企业
					this.$router.push(`/auth/step${isIdentityVerification}`)
				} else if (userType === 3) {
					// 机关
					this.$router.push(`/auth/step${isIdentityVerification}`)
				} else if (userType === 5) {
					// 乡村
					this.$router.push(`/auth/step${isIdentityVerification}`)
				}
			},
			// 导航管理 保存栏目
			saveColumn () {
				this.$api.post('/member/columnSettings/saveColumnSettings', {
					data: this.columns
				}).then(response => {
					if (response.code === 200) {
						this.$Message.success('保存成功')
						window.location.reload()
					} else {
						this.$Message.error('保存失败')
					}
				})
			},
			// 显示导航管理的弹出框
			showColumn () {
				this.modalc = !this.modalc
			},
			cancel () {
				this.$router.push('/pro/member/')
			},
			sure() {
				this.cancel()
			},
			cancelSec () {
				this.count1 = 1
				this.$router.push('/pro/member/')
				//window.location.reload()
			},
			//关闭动态，改变现实隐藏
			/* changedany(msg) {
				this.showDany = msg
			}, */
			//关闭知识，改变现实隐藏
			/* changeLed(msg) {
				this.showLed = msg
			}, */
			//关闭政策，改变现实隐藏
			/* changePol(msg) {
				this.showPol = msg
			}, */
			/* hideReplyListChildren (item) {
				item.replyChildren = []
				item.showOrHideComment = true
			} */
		}
	}
</script>
<style scoped>
	/*除头底的样式开始*/
	.main{
	  min-height: 500px;
	  margin: 10px auto 50px;
	}
	.main-l {
		border: 1px solid #ededed;
	}
	.container .region {
		width: 140px;
		margin-left: 0px;
	}

	/* .ivu-modal-footer {
		text-align: center !important;
	} */

	/* .jing {
		color: #00c587;
	} */
	.publish_header{
		border: 1px solid #ccc;
	}
	.publish_con{
		margin-top: 10px;
		margin-bottom: 10px;
	}
	/* .replyNames{
		font-size:14px;
	} */
</style>
<style lang="scss">
	.main_r_d {
		padding: 0 0 0 10px;
		.tabs-edit-btn {
			.ivu-tabs-nav-right {
				float: right !important;
			}
		}
		.like {
			color: #f90
		}
		.live{
			position: relative;
			.to-live{
				    position: absolute;
					width: 100px;
					height: 40px;
					text-align: center;
					line-height: 40px;
					font-size: 18px;
					border-radius: 20px;
					top: 90px;
					background: #fff;
					right: 20px;
			}
		}
	}

</style>
