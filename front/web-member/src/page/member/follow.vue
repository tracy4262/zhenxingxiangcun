<template>
	<div class="layout">
		<top :address="false" />
		<div>
			<member-header/>
		</div>
		<div class="main">
			<div class="container">
				<Row>
					<Col span="4" class="main-l">
					<high-app name="高级应用" />
					<Divider />
					<base-app name="基础应用" />
					<Divider />
					<user-app name="通用应用" />
					</Col>
					<Col span="20">

					<Row>
						<div>
							<div style="margin: 20px 0 20px 0;padding: 0 20px">
								<div class="jingtu-down1" style="padding: 0 10px;padding-bottom: 20px">
									<ul>
										<li @click="follow" class="hand" :class="[this.$parent.$route.path=='/pro/follow/follow01' ? jing :'']">关注物种</li>
										<li @click="follow1" class="hand" :class="[this.$route.path=='/pro/follow/follow02' ? jing :'']">关注产品 </li>
										<li @click="follow2" class="hand" :class="[this.$route.path=='/pro/follow/follow03' ? jing :'']">关注服务 </li>
										<li @click="follow3" class="hand" :class="[this.$route.path=='/pro/follow/follow04' ? jing :'']">关注知识</li>
										<li @click="follow4" class="hand" :class="[this.$route.path=='/pro/follow/follow05' ? jing :'']">关注资讯</li>
										<li @click="follow5" class="hand" :class="[this.$route.path=='/pro/follow/follow06' ? jing :'',jdr]">关注政策</li>
									</ul>
								</div>
							</div>
							<transition>
								<router-view></router-view>
							</transition>
						</div>
					</Row>

					</Col>
				</Row>
			</div>
		</div>
	</div>
</template>

<script>
	import top from '../../top'
	import highApp from '~components/memberHighApp'
	import BaseApp from '~components/memberBaseApp'
    import UserApp from '~components/memberUserApp'
	import axios from '~src/api/api'
	import $ from 'jquery'
	import api from '~api'
	import memberHeader from './components/memberHeader'

	export default {
		components: {
			top,
			highApp,
			BaseApp,
            UserApp,
			memberHeader
		},

		data() {
			return {
                jing: 'jing',
                jdr: 'jd-r',
				displayName: '',
				loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
				signature: '还没有签名！',
				favorite:0,
				num:0,
				modal1: false
			}
		},
		created: function() {
			this.shouTop()
			this.$router.push('/pro/follow/follow01')
			axios.get('highApp.json').then(res=>{
				this.highAppData = res.data
			})
			axios.get('baseApp.json').then(res=>{
				this.baseAppData = res.data[0]
				this.useAppData = res.data[1]
			})
		},

		methods: {
            follow() {
                this.$router.push('/pro/follow/follow01')
            },
            follow1() {
                this.$router.push('/pro/follow/follow02')
            },
            follow2() {
                this.$router.push('/pro/follow/follow03')
            },
            follow3() {
                this.$router.push('/pro/follow/follow04')
            },
            follow4() {
                this.$router.push('/pro/follow/follow05')
            },
            follow5() {
                this.$router.push('/pro/follow/follow06')
            },
			/**
			 * 头部数据显示
			 */
			shouTop(){
				api.get('/member/memberCenter/index')
				.then(response => {
					this.favorite = response.data.favorite
					this.num = response.data.number
					this.signature = response.data.signature
					this.displayName = response.data.displayName
				})
				.catch(function(error) {
                    this.$Message.error(error)
				})
			}
		},
		mounted () {
        }
	}
</script>

<style scoped>
	@import '../../css/identification.css';
	.jing {
		color: #00c587;
	}
	/*main样式开始*/
	.demo-upload-list{
		display: inline-block;
        width: 60px;
        height: 60px;
        text-align: center;
        line-height: 60px;
        border: 1px solid transparent;
        border-radius: 4px;
        overflow: hidden;
        background: #fff;
        position: relative;
        box-shadow: 0 1px 1px rgba(0,0,0,.2);
        margin-right: 4px;
    }
    .demo-upload-list img{
        width: 100%;
        height: 100%;
    }
    .demo-upload-list:hover .demo-upload-list-cover{
        display: block;
    }
	.demo-upload-list-cover{
		color:#fff;
		font-size: 16px;
        display: none;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(0,0,0,.6);
    }

	.main-l {
		border: 1px solid #ededed;
		margin-right: 0;
	}

	.main-r-top {
		height: 134px;
		background: url("../../img/images/hyzx-banner.png") no-repeat top center;
	}

	.main-r-top ul {
		float: right;
		margin: 70px 30px 0 0;
	}

	.main-r-top ul li {
		float: left;
		margin-left: 34px;
		text-align: center;
		font-size: 16px;
	}

	.main-r-top ul li p {
		font-size: 20px;
	}

	.main-r-top ul li span {
		margin-left: 0px;
		font-size: 14px;
	}
	.hand{
		cursor: pointer;
	}
</style>
