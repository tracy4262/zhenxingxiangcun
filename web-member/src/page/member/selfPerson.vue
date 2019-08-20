<template>
	<div class="layout">
		<!--header开始-->
		<header>
			<div class="container">
				<Row>
					<Col span="4" class="region ">
					<img src="../../img/huiyuan-logo.png" alt="">

					</Col>

					<Col span="11" class="nav-center">
					<Menu mode="horizontal" :theme="theme1" active-name="1" @on-select="routeTo">
						<Menu-item name="/index">
							<Icon type="ios-home"></Icon>
							首页
						</Menu-item>
						<Menu-item name="/pro/member">
							会员中心
						</Menu-item>
						<Menu-item name="/pro/member/self">
							我的无忧
						</Menu-item>
						<Menu-item name="/portal">
							我的门户
						</Menu-item>
						<Submenu name="5">
							<template slot="title">
								注册机构
							</template>
							<Menu-item name="3-1">企业注册</Menu-item>
							<Menu-item name="3-2">机关注册</Menu-item>
						</Submenu>
					</Menu>

					</Col>
					<Col span="7">
					<Input v-model="value13" style="margin-top: 25px;margin-left: 60px;">
					<Select v-model="select3" slot="prepend" style="width: 80px">
						<Option>综合</Option>
						<Option>资讯</Option>
						<Option>政策</Option>
						<Option>知识</Option>
						<Option>产品</Option>
						<Option>企业</Option>
						<Option>机关</Option>
						<Option>服务</Option>

					</Select>
					<Button slot="append" icon="ios-search"></Button>
					</Input>

					</Col>
					<Col span="2" style="float: right">
					<p href="#" style=";float: right;
							line-height: 81px;font-size: 14px;">
						周周周</p>

					<!--<Badge count="3" style="margin-top:30px;float: right;margin-right: 20px">-->
						<!--<a href="#" class="demo-badge">-->
							<!--<Icon type="ios-bell" size="22" style="color: #00c587">-->

							<!--</Icon>-->
						<!--</a>-->

					<!--</Badge>-->

					</Col>
				</Row>

			</div>
		</header>
		<!--header结束-->

		<!--mian开始-->

		<div class="main">
			<div class="container">
				<Row>
					<Row>
						<!--mian左边开始-->

						<Col span="6" class="mian-left">
						<div style="height: 14px;width: auto"></div>
						<Menu active-name="/pro/member/self/dynamic" width="auto" :theme="theme1" @on-select="routeTo">

							<Menu-item name="/pro/member/self/info">
								<img src="../../img/icon-1.png" alt="" style="margin-right: 26px;vertical-align:text-top;">
								<span class="layout-text">基本信息</span>
							</Menu-item>
							<Menu-item name="3">
								<img src="../../img/icon-3.png" alt="" style="margin-right: 26px;vertical-align:text-top;">
								<span class="layout-text">我的认证</span>
							</Menu-item>
							<Menu-item name="4">
								<img src="../../img/icon-4.png" alt="" style="margin-right: 26px;vertical-align:text-top;">
								<span class="layout-text">联系方式</span>
							</Menu-item>
							<Menu-item name="5">
								<img src="../../img/icon-5.png" alt="" style="margin-right: 26px;vertical-align:text-top;">
								<span class="layout-text">我的商品</span>
							</Menu-item>
							<Menu-item name="6">
								<img src="../../img/icon-6.png" alt="" style="margin-right: 26px;vertical-align:text-top;">
								<span class="layout-text">我的服务</span>
							</Menu-item>
							<Menu-item name="7">
								<img src="../../img/icon-7.png" alt="" style="margin-right: 14px;vertical-align:text-top;">
								<span class="layout-text" style="padding-left: 15px">我的知识库</span>
							</Menu-item>
							<Menu-item name="8">
								<img src="../../img/icon-8.png" alt="" style="margin-right: 26px;vertical-align:text-top;">
								<span class="layout-text">我的网站</span>
							</Menu-item>
							<Menu-item name="/pro/member/self/set">
								<img src="../../img/icon-9.png" alt="" style="margin-right: 26px;vertical-align:text-top;">
								<span class="layout-text">自定义设置</span>
							</Menu-item>
						</Menu>

						</Col>
						<!--mian左边结束-->

						<!--mian中边开始-->

						<Col span="18" class="mian-center">
						<router-view></router-view>
						</Col>
						<!--mian中边结束-->

					</Row>
				</Row>
			</div>
		</div>
		<!--mian结束-->
		<foot></foot>
	</div>

</template>
<script>
	import foot from '../../foot'
	export default {
		components: {
			foot
		},
		data() {

			return {

				cityList: [{
						value: 'beijing',
						label: '北京市'
					},
					{
						value: 'shanghai',
						label: '上海市'
					},
					{
						value: 'shenzhen',
						label: '深圳市'
					},
					{
						value: 'hangzhou',
						label: '杭州市'
					},
					{
						value: 'nanjing',
						label: '南京市'
					},
					{
						value: 'chongqing',
						label: '重庆市'
					},

				],
				modal2: false,
				modal_loading: false,
				theme1: 'light',
				modal3: false,
				modal4: false,
				modal5: false,
				portal :'',
				formInline: {
					user: '',
					password: ''
				},
				ruleInline: {
					user: [{
						required: true,
						message: '请填写用户名',
						trigger: 'blur'
					}],
					password: [{
							required: true,
							message: '请填写密码',
							trigger: 'blur'
						},
						{
							type: 'string',
							min: 6,
							message: '密码长度不能小于6位',
							trigger: 'blur'
						}
					],
					methods: {
						del() {
							this.modal_loading = true;
							setTimeout(() => {
								this.modal_loading = false;
								this.modal2 = false;
								this.$Message.success('删除成功');
							}, 2000);
						}
					}
				}
			}
		},
		methods: {
			routeTo(e) {
				if(e == '/portal'){
                    this.portal = this.$url.shop + '/center/gateway.htm?uid='+this.loginuserinfo.uniqueId
                    window.open(this.portal)
                }else{
                    this.$router.push(e)
                }
			}
		},
		created: function() {
			this.$router.push('/pro/member/self/dynamic')
		}
	}
</script>
<style scoped>
	.layout {
		background: #fff;
	}
	/*header样式开始*/

	header {
		height: 81px;
		background: #FFFFFF;
		border-bottom: 1px solid #e7e7e7;
	}

	.container {
		width: 1196px;
		margin: 0 auto;
	}

	.header-top {
		height: 81px;
	}

	.region {
		float: left;
		margin-left: 14px;
		width: 108px;
		margin-top: 20px;
		height: 36px;
	}

	.nav-center {
		margin-left: 20px;
	}

	.nav-r {
		float: right;
		margin-top: 26px;
	}

	.btn1 {
		height: 36px;
		margin-left: 12px;
	}

	.banner {
		background: url("../../img/index-banner.png") center top no-repeat;
		height: 512px;
		background-size: cover;
	}

	.banner-main {
		width: 802px;
		padding: 24px;
		background: #272727;
		border-radius: 4px;
		opacity: 0.7;
		margin-top: 80px;
		margin: auto;
	}

	.banner-logo {
		width: 109px;
		margin: 20px auto;
	}

	.ivu-tabs.ivu-tabs-card>.ivu-tabs-bar .ivu-tabs-tab {
		margin: 0;
		margin-right: 14px;
		height: 31px;
		padding: 5px 16px 4px;
		border: 1px solid #d7dde4;
		border-bottom: 0;
		border-radius: 4px 4px 0 0;
		transition: all .3s ease-in-out;
		background: #f5f7f9;
	}

	.ivu-input {
		display: inline-block;
		width: 100%;
		height: 41px;
		line-height: 1.5;
		padding: 4px 7px;
		font-size: 12px;
		border: 1px solid #d7dde4;
		border-radius: 4px;
		color: #657180;
		background-color: #fff;
		background-image: none;
		position: relative;
		cursor: text;
		transition: border .2s ease-in-out, background .2s ease-in-out, box-shadow .2s ease-in-out;
	}

	.ivu-tabs.ivu-tabs-card>.ivu-tabs-bar .ivu-tabs-tab-active {
		height: 32px;
		padding-bottom: 5px;
		background: #fd1212;
		transform: translateZ(0);
		border-color: #d7dde4;
		color: #39f;
	}
	/*header样式结束*/
	/*main样式开始*/

	.main {
		margin-top: 10px;
		margin-bottom: 10px;
	}

	.mian-left {
		border-right: 1px solid #fafafa;
		width: 210px;
	}
	/*main样式结束*/

	.mian-center {
		max-width: 702px;
		margin-left: 10px;
		margin-top: 20px;
		background: #fafafa;
		border: 1px solid #eeeeee;
		padding-bottom: 100px;
		font-size: 16px;
	}

	.main-user {
		position: absolute;
		left: 0;
		right: 0;
		margin: auto;
		margin-top: -46px;
	}

	.main-top-bg {
		height: 135px;
		width: 100%;
		background: url("../../img/mian-img.png") no-repeat center top;
		position: relative;
	}

	.main-t-r {
		width: 74%;
		margin-top: 24px;
		height: 100px;
	}

	.main-t-r ul li {
		float: right;
		margin-right: 28px;
		text-align: center;
	}

	.main-t-r span {
		font-size: 20px;
		font-weight: 500;
	}

	.main-t-r p {
		font-size: 14px;
	}

	.main-bot {
		/*width: 600px;*/
		border-top: 1px solid #ededed;
		position: relative;
	}

	.main-bot-title {
		width: 120px;
		height: 35px;
		font-size: 18px;
		background: #ededed;
		text-align: center;
		line-height: 35px;
		position: absolute;
		top: -20px;
		left: 0px;
		right: 0px;
		margin: auto;
		color: #333;
		border-radius: 18px;
	}

	.mian-bot1-title {
		width: auto;
		height: 18px;
		font-size: 16px;
		margin-top: 46px;
		border-left: 4px solid #00c587;
		padding-left: 10px;
		line-height: 16px;
	}

	.mian-bot1-title a {
		color: #333;
		font-size: 12px;
	}

	.main-bot2 img {
		margin-right: 16px;
		margin-top: 8px;
	}

	.main-bot2 p {
		font-size: 14px;
		line-height: 30px;
	}

	.main-bot2 ul {
		margin-right: 63px;
	}

	.main-bot2 ul li {
		line-height: 30px;
		font-size: 14px;
	}

	.main-bot3 li {
		line-height: 18px;
	}

	.main-bot3 ul {
		margin-left: 24px;
	}

	.check-box {
		margin: 50px auto;
	}

	.check-box ul li {
		margin-bottom: 20px;
		font-size: 16px;
	}

	.ivu-switch-checked {
		border-color: #00c587;
		background-color: #00c587;
	}

	.ivu-btn-group-small>.ivu-btn {
		padding: 4px 12px;
		font-size: 12px;
		border-radius: 3px;
	}

	.ivu-btn-group:not(.ivu-btn-group-vertical) .ivu-btn-primary:not(:first-child):not(:last-child) {
		border: 1px solid #00c587;
	}

	.ivu-btn:hover {
		color: #00c587;
		background-color: #fff;
		border-color: #00c587;
	}
	/*footer样式开始*/

	.footer {
		height: 163px;
		border-top: 5px solid #00c587;
		background: #333333;
		padding-top: 22px;
	}

	.footer-l {
		float: left;
	}

	.footer-r {
		float: right;
		color: #fff;
	}

	.footer-l ul li {
		color: #fff;
		float: left;
		padding: 0 16px;
	}

	.footer-r ul li {
		color: #fff;
		float: left;
		padding: 0 16px;
	}

	.footer-l div {
		color: #b4b4b4;
		padding-left: 16px;
	}
</style>