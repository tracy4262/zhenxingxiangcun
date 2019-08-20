<template>
	<div class="layout">
		<!--header开始-->
			<header>
			<div class="container">
				<Row>
					<Col span="4" class="region ">
					<img src="../../img/huiyuan-logo.png" alt="">
					</Col>
					<Col span="10" class="nav-center" style="width:45%;">
					<Menu mode="horizontal" :theme="theme1" active-name="/pro/member" @on-select="routeTo">
						<Menu-item name="/index">
							<Icon type="ios-home"></Icon>
							首页
						</Menu-item>
						<Menu-item name="/pro/member">
							会员中心
						</Menu-item>
						<Menu-item name="/profile">
							我的无忧
						</Menu-item>
						<Menu-item name="/portal">
							我的门户
						</Menu-item>
					</Menu>
					</Col>
					
					<Col span="3" style="float: right;height:81px;">
					<p href="#" style=";float: right;line-height: 81px;font-size: 14px;">
						<Dropdown v-if="loginuserinfo != null">
                            <a href="javascript:void(0)" style="color: #00c587">
                                {{loginuserinfo.displayName}}
                                <Icon type="ios-arrow-down"></Icon>
                            </a>
                            <Dropdown-menu slot="list" trigger="click">
                                <Dropdown-item>账户安全</Dropdown-item>
								<Dropdown-item><a :href="[this.$url.shop +'/center/address.htm?uid='+loginuserinfo.uniqueId]" target="_blank">收货地址</a></Dropdown-item>
								<Dropdown-item><a :href="[this.$url.shop + '/center/order/list.htm?uid='+loginuserinfo.uniqueId]" target="_blank">消费记录</a></Dropdown-item>
                                <Dropdown-item><a href="javascript:void0" @click="logout">退出</a></Dropdown-item>
                            </Dropdown-menu>
                        </Dropdown>
					</p>
					<!--<Badge count="3" style="margin-top: 30px;float: right;margin-right: 20px">-->
						<!--<a href="#" class="demo-badge">-->
							<!--<Icon type="ios-bell" size="22" style="color: #00c587"></Icon>-->
						<!--</a>-->
					<!--</Badge>-->
					</Col>
				</Row>
			</div>
		</header>
		<!--header结束-->
		<div class="main" style="margin-top: 20px">
			<div class="container container2">
				<Row style="margin-bottom: 20px">
					<Col span="3" class="quanzi-mian-top1">
					<div style="font-size: 28px;text-align: center;line-height:126px;color: #fff;font-weight: 600 ;  text-shadow: 0px 0px 19.71px rgba(18, 32, 40, 0.4);">官方圈</div>
					</Col>
					<Col span="3" class="quanzi-mian-top2">
					<div style="font-size: 28px;text-align: center;line-height:126px;color: #fff;font-weight: 600 ;  text-shadow: 0px 0px 19.71px rgba(18, 32, 40, 0.4);">本地圈
					</div>
					</Col>
					<Col span="3" class="quanzi-mian-top3">
					<div style="font-size: 28px;text-align: center;line-height:126px;color: #fff;font-weight: 600 ;  text-shadow: 0px 0px 19.71px rgba(18, 32, 40, 0.4);">论坛圈</div>
					</Col>
					<Col span="3" class="quanzi-mian-top4">
					<div style="font-size: 28px;text-align: center;line-height:126px;color: #fff;font-weight: 600 ;  text-shadow: 0px 0px 19.71px rgba(18, 32, 40, 0.4);">热门圈</div>
					</Col>
					<Col span="3" class="quanzi-mian-top5">
					<div style="font-size: 28px;text-align: center;line-height:126px;color: #fff;font-weight: 600 ;  text-shadow: 0px 0px 19.71px rgba(18, 32, 40, 0.4);">种植圈</div>
					</Col>
					<Col span="3" class="quanzi-mian-top6">
					<div style="font-size: 28px;text-align: center;line-height:126px;color: #fff;font-weight: 600 ;  text-shadow: 0px 0px 19.71px rgba(18, 32, 40, 0.4);">养殖圈</div>
					</Col>
					<Col span="3" class="quanzi-mian-top7">
					<div style="font-size: 28px;text-align: center;line-height:126px;color: #fff;font-weight: 600 ;  text-shadow: 0px 0px 19.71px rgba(18, 32, 40, 0.4);">政策圈</div>
					</Col>
				</Row>
				<Row>
					<Col span="16" style="width: 1018px">
					<Row>
						<Col span="12">
						<Carousel autoplay v-model="value1">
							<Carousel-item>
								<div class="demo-carousel"><img src="../../img/quanzi-banner-img1.png" alt=""></div>
							</Carousel-item>
							<Carousel-item>
								<div class="demo-carousel"><img src="../../img/quanzi-banner-img1.png" alt=""></div>
							</Carousel-item>
							<Carousel-item>
								<div class="demo-carousel"><img src="../../img/quanzi-banner-img1.png" alt=""></div>
							</Carousel-item>
							<Carousel-item>
								<div class="demo-carousel"><img src="../../img/quanzi-banner-img1.png" alt=""></div>
							</Carousel-item>
						</Carousel>
						</Col>
						<Col span="12">
						<Tabs type="card" style="margin-left: 34px">
							<Tab-pane label="最新帖子">
								<div>
									<div class="news-list" v-for="(a,index) in article">
										<Tag :class="[index+1==1?red:'',index+1==2?yellow:'',index+1==3?blue:'']">{{index+1}}</Tag>
										<router-link to="/detail" style="font-size: 16px;color: #333;vertical-align: middle;">{{a.title}}</router-link>
										<span style="font-size: 14px;color: #9f9f9f ;float: right;vertical-align: middle;margin-top: 4px;" v-if="a.time<60">{{a.time}}分钟前</span>
										<span style="font-size: 14px;color: #9f9f9f ;float: right;vertical-align: middle;margin-top: 4px;" v-else-if="a.time>60&a.time<1440">{{Math.floor(a.time/60)}}小时前</span>
										<span style="font-size: 14px;color: #9f9f9f ;float: right;vertical-align: middle;margin-top: 4px;" v-else="a.time>1440">{{Math.floor(a.time/1440)}}天前</span>
									</div>
								</div>
							</Tab-pane>
							<Tab-pane label="本周热门">
								<div>
									<div class="news-list" v-for="(a,index) in week">
										<Tag :class="[index+1==1?red:'',index+1==2?yellow:'',index+1==3?blue:'']">{{index+1}}</Tag>
										<router-link to="/detail" style="font-size: 16px;color: #333;vertical-align: middle;">{{a.title}}</router-link>
										<span style="font-size: 14px;color: #9f9f9f ;float: right;vertical-align: middle;margin-top: 4px;" v-if="a.time<60">{{a.time}}分钟前</span>
										<span style="font-size: 14px;color: #9f9f9f ;float: right;vertical-align: middle;margin-top: 4px;" v-else-if="a.time>60&a.time<1440">{{Math.floor(a.time/60)}}小时前</span>
										<span style="font-size: 14px;color: #9f9f9f ;float: right;vertical-align: middle;margin-top: 4px;" v-else="a.time>1440">{{Math.floor(a.time/1440)}}天前</span>
									</div>
								</div>
							</Tab-pane>
							<Tab-pane label="今日之星">
								<div>
									<div class="news-list" v-for="(a,index) in week">
										<Tag :class="[index+1==1?red:'',index+1==2?yellow:'',index+1==3?blue:'']">{{index+1}}</Tag>
										<router-link to="/detail" style="font-size: 16px;color: #333;vertical-align: middle;">{{a.title}}</router-link>
										<span style="font-size: 14px;color: #9f9f9f ;float: right;vertical-align: middle;margin-top: 4px;" v-if="a.time<60">{{a.time}}分钟前</span>
										<span style="font-size: 14px;color: #9f9f9f ;float: right;vertical-align: middle;margin-top: 4px;" v-else-if="a.time>60&a.time<1440">{{Math.floor(a.time/60)}}小时前</span>
										<span style="font-size: 14px;color: #9f9f9f ;float: right;vertical-align: middle;margin-top: 4px;" v-else="a.time>1440">{{Math.floor(a.time/1440)}}天前</span>
									</div>
								</div>
							</Tab-pane>
						</Tabs>
						</Col>
					</Row>
					<!--圈子-->
					<div v-for="(value,key) in circle">
						<div class="mian-title">
							<h4>{{key}}</h4>
						</div>
						<Row>
							<Col span="8" style="margin-bottom: 20px" v-for="(v,index) in value" :key="index">
							<div>
								<img src="../../img/zongzhiyuan-img.png" alt="" style="float: left">
								<div style="float: left;margin-top: -3px;margin-left: 10px">
									<p class="text1"><router-link :to="{ path: '/circleDetail', query: { 'id': v.id }}">
										{{v.coterie_name}}</router-link>
										<span class="text4">({{v.today}})</span></p>
									<p class="text2">主题:{{v.topic}},贴数:{{v.COMMENT}}</p>
									<p class="text3">蔬菜交流 蔬菜展示</p>
								</div>
							</div>
							</Col>
						</Row>
					</div>
				</Col>
					<Col span="8" style="width: 370px;margin-left: 34px">
					<div style="margin-bottom: 20px">
						<div class="main-r-title2">
							<h4>排行榜</h4>
						</div>
						<div class="news-list" v-for="(r,index) in rank">
							<Tag :class="[index+1==1?red:'',index+1==2?yellow:'',index+1==3?blue:'']">{{index+1}}</Tag>
							<a style="font-size: 16px;color: #333;vertical-align: middle;">{{r.category}}</a>
							<span style="font-size: 14px;color: #9f9f9f ;float: right;vertical-align: middle;margin-top: 4px;">今日 <span class="text-huang">{{r.tour}}</span>
							<span style="float: right;margin-top: 2px;"><img src="../../img/up.png" alt=""></span>
							</span>
						</div>
					</div>
					<div style="margin-bottom: 20px">
						<div class="main-r-title2">
							<h4>达人
                            	<div style="float: right;font-size: 12px" size="small" > <Page :current="2" :total="50" simple></Page></div>
             				</h4>
						</div>
						<div class="daren-list" style="width: 370px;height: 64px">
							<div style="padding: 14px 0;width: 100%">
								<img src="../../img/quanzi-user1.png" alt="" style="float: left">
								<div style="float: left;margin-top: 3px;margin-left: 10px">
									<p class="text2">朱建威</p>
									<p class="text2"><span style="margin-left: 0;color: #999999">发帖  <span style="color: #ff8b3f;">765</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
										<span style="margin-left: 0;color: #999999">粉丝 <span style="color: #00c587;">75</span></span>
									</p>
								</div>
								<Button style="float: right;margin-top: 12px" size="small">+ 关注</Button>
							</div>
						</div>
						<div class="daren-list" style="width: 370px;height: 64px">
							<div style="padding: 14px 0;width: 100%">
								<img src="../../img/quanzi-user2.png" alt="" style="float: left">
								<div style="float: left;margin-top: 3px;margin-left: 10px">
									<p class="text2">朱建威</p>
									<p class="text2"><span style="margin-left: 0;color: #999999">发帖  <span style="color: #ff8b3f;">765</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
										<span style="margin-left: 0;color: #999999">粉丝 <span style="color: #00c587;">75</span></span>
									</p>
								</div>
								<Button style="float: right;margin-top: 12px" size="small">+ 关注</Button>
							</div>
						</div>
						<div class="daren-list" style="width: 370px;height: 64px">
							<div style="padding: 14px 0;width: 100%">
								<img src="../../img/quanzi-user1.png" alt="" style="float: left">
								<div style="float: left;margin-top: 3px;margin-left: 10px">
									<p class="text2">朱建威</p>
									<p class="text2"><span style="margin-left: 0;color: #999999">发帖  <span style="color: #ff8b3f;">765</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
										<span style="margin-left: 0;color: #999999">粉丝 <span style="color: #00c587;">75</span></span>
									</p>
								</div>
								<Button style="float: right;margin-top: 12px" size="small">+ 关注</Button>
							</div>
						</div>
						<div class="daren-list" style="width: 370px;height: 64px">
							<div style="padding: 14px 0;width: 100%">
								<img src="../../img/quanzi-user2.png" alt="" style="float: left">
								<div style="float: left;margin-top: 3px;margin-left: 10px">
									<p class="text2">朱建威</p>
									<p class="text2"><span style="margin-left: 0;color: #999999">发帖  <span style="color: #ff8b3f;">765</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
										<span style="margin-left: 0;color: #999999">粉丝 <span style="color: #00c587;">75</span></span>
									</p>
								</div>
								<Button style="float: right;margin-top: 12px" size="small">+ 关注</Button>
							</div>
						</div>
						<div class="daren-list" style="width: 370px;height: 64px">
							<div style="padding: 14px 0;width: 100%">
								<img src="../../img/quanzi-user1.png" alt="" style="float: left">
								<div style="float: left;margin-top: 3px;margin-left: 10px">
									<p class="text2">朱建威</p>
									<p class="text2"><span style="margin-left: 0;color: #999999">发帖  <span style="color: #ff8b3f;">765</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
										<span style="margin-left: 0;color: #999999">粉丝 <span style="color: #00c587;">75</span></span>
									</p>
								</div>
								<Button style="float: right;margin-top: 12px" size="small">+ 关注</Button>
							</div>
						</div>
					</div>
					<div style="margin-bottom: 20px">
						<div class="main-r-title2">
							<h4>公告</h4>
						</div>
						<div class="daren-list" style="width: 370px;height: 64px">
							<div style="padding-top: 12px ">
								<p style="font-size: 16px;color: #333">关于用户违规操作通知</p>
								<p style="font-size: 12px;line-height: 24px">系统消息 &nbsp &nbsp 昨天</p>
							</div>
							<div style="padding-top: 12px ">
								<p style="font-size: 16px;color: #333">关于用户违规操作通知</p>
								<p style="font-size: 12px;line-height: 24px">系统消息 &nbsp &nbsp 昨天</p>
							</div>
						</div>
					</div>
					</Col>
				</Row>
			</div>
		</div>
		<foot></foot>
	</div>
</template>
<script>
import api from '~api'
import foot from '../../foot'
export default {
	components: {
		foot
	},
	data() {
		return {
			loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
			portal :'',
			value: '',
			value1: '',
			value13: '',
			select3: '',
			theme1: 'light',
			valueDisabled: 4,
			modal3: false,
			modal4: false,
			modal5: false,
			article: [],
			week: [],
			rank: [],
			circle: [],
			red: 'red',
			yellow: 'yellow',
			blue: "blue",
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
	},
	methods: {
		//最新帖子
		articles() {
			api.get('/member/CoterieArticle/query/')
				.then(response => {
					this.article = response.data;
					console.log("圈子文章.......")
					console.log(response.data);
				})
		},
		//本周最热
		weeks() {
			api.get('/member/CoterieArticle/weekHot/')
				.then(response => {
					this.week = response.data;
					console.log("圈子本周最热.......")
					console.log(response.data);
				})
		},
		//圈子
		circles() {
			api.get('/member/CoterieArticle/circle/')
				.then(response => {
					this.circle = response.data;
					console.log("圈子.......")
					console.log(this.circle);
				})
		},
		//排行榜
		ranks() {
			api.get('/member/CoterieArticle/rank/')
				.then(response => {
					this.rank = response.data;
					console.log("圈子排行榜.......")
					console.log(this.rank);
				})
		},
		routeTo(e) {
			if(e == '/portal'){
				this.portal = this.$url.shop + '/center/gateway.htm?uid='+this.loginuserinfo.uniqueId
				window.open(this.portal)
			}else{
				this.$router.push(e)
			}
		},
		logout(){
			api.get('/member/login/logout')
				.then(response => {
					console.log(response.data)
				})
				sessionStorage.removeItem("user")
				this.$router.push('/index')
		}
	},
	created: function () {
		this.articles();
		this.weeks();
		this.circles();
		this.ranks();
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
		width: 80px;
		margin-top: 26px;
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

	.ivu-tabs-nav .ivu-tabs-tab-active {
		color: #fff;
	}

	.search {
		margin: 20px 0;
		border-radius: 0 4px 4px 0;
		width: 94px;
		margin-left: -4px;
	}

	.news-img {
		position: absolute;
		right: 0;
		top: 0
	}
	.mian p {
		font-size: 14px;
		line-height: 36px;
	}

	.nav-padding {
		float: left;
		padding: 0 48px;
		position: relative;
		cursor: pointer;
		z-index: 3;
		transition: all .2s ease-in-out;
		border-top: 6px solid #fff;
	}

	.new-tab {
		border: 1px solid #00c587 !important;
		color: #00c587 !important;
		float: left;
		margin-right: 6px;
	}

	.main-time {
		padding: 0 8px;
		border-left: 1px solid #ccc;
		color: #949494;
	}

	.main span {
		margin-left: 6px;
	}

	.new-last li {
		border-bottom: 1px solid #ccc;
		padding: 0 12px 70px 12px;
		margin-bottom: 20px;
	}


	.new-title-h5 {
		font-size: 24px;
		font-weight: normal;
	}

	.new-text {
		text-align: center;
		background: #fff;
		position: absolute;
		top: -20px;
		left: 0px;
		right: 0;
		margin: auto;
		width: 80px;
	}

	.new2 ul li {
		width: 410px;
		margin: 0 auto;
		font-size: 18px;
		border-bottom: 1px dashed #ccc;
		padding: 20px 0;
	}

	.new-more {
		width: 100%;
		height: 50px;
	}

	.new2 {
		height: 609px;
		padding-top: 20px;
	}

	.top {
		padding: 10px;
		background: #00c587;
		color: #fff;
		text-align: center;
		border-radius: 2px;
		bottom: 30px;
	}

	.footer {
		height: 163px;
		border-top: 5px solid #00c587;
		background: #333333;
		/*margin-top: 140px;*/
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

	.fenlei-list ul li {
		float: left;
		margin-left: 30px;
		color: #333;
		padding: 2px;
	}

	.fenlei {
		margin-bottom: 10px;
	}

	.fenlei-list ul li a {
		color: #333;
	}

	.fenlei-list ul li a :hover {
		color: #00c587;
	}

	.list-title {
		background: #00c587;
		text-align: center;
		padding: 2px;
		color: #fff;
	}

	.active a {
		color: #00c587;
	}

	.zj-mian-1 {
		background: #fafafa;
		padding: 14px;
		border: 1px solid #ededed;
	}

	.zj-mian-2 {
		border-left: 1px solid #ffffff;
		padding-left: 1px;
		/*padding: 8px 14px 14px 14px;*/
		margin-top: 16px;
	}

	.zj-mian-2 h3 {
		font-size: 18px;
	}

	.zj-mian-2 p {
		font-size: 14px;
	}

	.ivu-rate-star-full:before,
	.ivu-rate-star-half .ivu-rate-star-content:before {
		color: #00c587;
	}

	.zj-kuang {
		border: 1px solid #ededed;
		padding: 8px 14px 14px 14px;
		height: 186px;
		width: 308px;
		margin: 0 6px 11px 6px;
	}

	.zj-tiao {
		position: absolute;
		bottom: 0;
		background: #fafafa;
		width: 305px;
		margin-left: -7px
	}

	.right-img {
		text-align: center;
		margin-bottom: 24px;
	}

	.lishi {
		width: 100%;
		height: 64px;
		padding: 6px;
		background: #fafafa;
		border-top: 1px solid #ededed;
		margin-top: 140px;
		position: relative;
		bottom: 0;
	}

	.box {
		float: left;
	}

	.lishi p {
		float: left;
	}

	.box ul li {
		float: left;
		margin-left: 6px;
	}
	/*企业样式开始  */

	.qiye-box {
		height: 182px;
		width: 182px;
		/*border: 1px solid #ededed;*/
		margin-right: 14px;
		margin-left: 8px;
		margin-bottom: 66px;
		padding: 0;
	}

	.qiye-box-r {
		height: 182px;
		width: 182px;
		/*border: 1px solid #ededed;*/
		margin-right: 0;
		margin-bottom: 66px;
		padding: 0;
	}

	.qiye p {
		font-size: 14px;
		text-align: center;
	}

	.qiye-box-hover {
		background: rgba(81, 173, 121, 0.9);
		height: 182px;
		width: 182px;
		position: absolute;
		top: 0;
	}

	.qiye-box-hover h2 {
		font-size: 24px;
		color: #fff;
		text-align: center;
		font-weight: normal;
		width: 80%;
		margin: auto;
		height: 50px;
		line-height: 50px;
		border-bottom: 4px solid #fff;
		margin-top: 14px;
	}

	.qiye-box-hover span {
		font-size: 14px;
		color: #fff;
		width: 70%;
		display: block;
		margin: auto;
		margin-top: 8px;
	}

	.quanzi-mian-top1 {
		width: 186px;
		height: 126px;
		margin-right: 20px;
		background-image: url("../../img/qianzi-maintop-img.png");
	}

	.quanzi-mian-top2 {
		width: 186px;
		height: 126px;
		margin-right: 20px;
		background-image: url("../../img/qianzi-maintop-img2.png");
	}

	.quanzi-mian-top3 {
		width: 186px;
		height: 126px;
		margin-right: 20px;
		background-image: url("../../img/qianzi-maintop-img3.png");
	}

	.quanzi-mian-top4 {
		width: 186px;
		height: 126px;
		margin-right: 20px;
		background-image: url("../../img/qianzi-maintop-img4.png");
	}

	.quanzi-mian-top5 {
		width: 186px;
		height: 126px;
		margin-right: 20px;
		background-image: url("../../img/qianzi-maintop-img5.png");
	}

	.quanzi-mian-top6 {
		width: 186px;
		height: 126px;
		margin-right: 20px;
		background-image: url("../../img/qianzi-maintop-img6.png");
	}

	.quanzi-mian-top7 {
		width: 186px;
		height: 126px;
		margin-right: 20px;
		background-image: url("../../img/qianzi-maintop-img7.png");
	}

	.container2 {
		width: 1442px;
		margin: auto;
	}
	/*.main p{*/
	/*font-size: 16px;*/
	/*color: #333;*/
	/*}*/

	.main span {
		font-size: 13px;
		color: #333;
	}

	.news-list {
		border-bottom: 1px dashed #dddddd;
		padding: 10px 0;
	}
	/*.tag-color{*/
	/*background: red;*/
	/*color: #fff;*/
	/*border-radius: px;*/
	/*}*/

	.ivu-tabs-bar {
		border-bottom: 1px solid #d7dde4;
		margin-bottom: 0px;
	}

	.mian-title {
		height: 50px;
		background: #f8f8f8;
		margin: 20px 0;
		padding-top: 13px;
	}

	.mian-title h4 {
		color: #333;
		font-size: 16px;
		font-weight: normal;
		padding-left: 10px;
		border-left: 6px solid #00c587;
		display: block;
	}

	.main-r-title2 {
		height: 50px;
		background: #f8f8f8;
		padding-top: 13px;
		border-bottom: 2px solid #00c587;
	}

	.main .main-r-title2 h4 {
		color: #333;
		font-size: 16px;
		font-weight: normal;
		padding-left: 10px;
		display: block;
	}

	.text1 {
		font-size: 16px;
		color: #333;
		font-weight: 500;
	}

	.text2 {
		font-size: 14px;
		color: #666666;
	}

	.text3 {
		font-size: 14px;
		color: #999999;
	}

	.main .text4 {
		font-size: 16px;
		color: #f90;
		font-weight: 400;
	}

	.main .text-huang {
		font-size: 14px;
		color: #f90;
		margin-right: 10px;
		margin-left: -2px;
	}
	/**
	 * 最新帖子样式
	 */

	.red {
		background-color: #fe3f3f;
		color: white;
	}

	.yellow {
		background-color: #fe880b;
		color: white;
	}

	.blue {
		background-color: #0cabe9;
		color: white;
	}
</style>

