<template>
	<div class="mt40">
		<!-- <Row :gutter="16" style="margin-bottom: 20px">
			<Col span="6" class="pt20" v-for="(item,index) in experts" :key="index">
				<Card :padding="0">
					<router-link :to="{path:'../expertGate/index',query: {uid: item.loginAccount}}"
												class="block pd10">
							<img v-if="item.avatar" :src="item.avatar" alt="" width="100%" height="220">
							<img v-else src="../../img/default_header.png" alt="" width="100%" height="220">
					</router-link>
					<div class="info pd20">
							<div class="expert-info-ivu-list">
									<span class="h4">{{item.displayName}}</span>
									<p class="pt10 mb10 detail ell-2" :title="item.adeptField">{{item.adeptField}}</p>
							</div>
							<divider solid class="mt10 mb10"/>
							<router-link :to="{path:'../expertGate/index',query: {uid: item.loginAccount}}">
									<div style="text-align:center">
											<Button type="default">更多信息
													<Icon type="ios-arrow-right"></Icon>
											</Button>
									</div>
							</router-link>
					</div>
				</Card>
			</Col>
		</Row>
		<Button type="default" class="new-more mt10" @click="expert()" long>更多</Button> -->
		<div class="enterprise-card ml10 mr10 mb20 tc" v-for="item in experts" :key="item.id" v-if="experts.length > 0">
			<router-link :to="{path:'../expertGate/index',query: {uid: item.loginAccount}}">
				<Avatar class="ml5 mt5" size="large" :src="item.avatar" />
				<Row class="mt10">
					<span class="ell enterprise-name" :title="item.displayName">{{ item.displayName }}</span>
				</Row>
				<Row class="enterprise-addr mt10">
					<Col span="24"><span>华中农业大学畜牧业研究所</span></Col>
				</Row>
				<Row class="enterprise-addr mt10">
					<Col span="24"><span>副研究员</span></Col>
				</Row>
				<Row class="mt10">
					<span class="enterprise-addr ell-3" style="height: 60px;" :title="item.adeptField">
						擅长领域：{{ item.adeptField }}
					</span>
				</Row>
				<!-- <Row class="mt10">
					<span class="enterprise-contact">联系电话：</span><span class="enterprise-tel">027-88767643</span>
				</Row> -->
			</router-link>
		</div>
		<div class="tc mt30 mb60" v-if="experts.length > 0">
            <Button type="default" class="more-btn" @click="more">更多</Button>
        </div>
        <div class="ma-polic-img" v-else>
            <img src="../../img/ma-img-002.png"> 
            <p style="margin-top: 10px;">暂无数据</p>
        </div>
	</div>
</template>
<script>
import api from '~api'
import divider from '~components/divider'
export default {
    components: {
        divider
	},
	data() {
		return {
			currentPage: 1,
			experts: [],
			pageSize: 6,
			valueDisabled: 5,
			list: []
		}
	},
	mounted: function() { // 挂在完成后的生命周期钩子注册。
		this.show()
	},
	methods: {
		show() {
			api.post('/member/expertInfo/findExpertTitle/' + this.currentPage, {
				district: '',
				species: '',
				industry: '',
				goodname: '',
				servicename: '',
				type: '',
				adeptField: '',
				title: ''
			}).then(response => {
				console.log('resb', response)
				// 在无忧导航专家的TAB页中只显示前6个
				this.experts = response.data.list.slice(0, 10)
			})
		},
		more () {
			this.$router.push('/51index/expertList')
		}
	}
}
</script>
<style lang="scss" scoped>
    /* 列表样式 */
    .expert-info-ivu-list {
        height: 85px;
    }

    .expert-info-ivu-list .h4 {
        display: inherit !important;
        text-align: center;
        font-size: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 1;
    }

    .expert-info-ivu-list p {
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
	}
.enterprise-card {
	width: calc(100% / 5 - 20px);
	height: 260px;
	background: #FFFFFF;
	border: 1px solid #E8E8E8;
	border-radius: 3px;
	padding: 20px;
	display: inline-block;

	.enterprise-name {
		color: #4A4A4A;
		font-size: 16px;
	}
	.enterprise-addr {
		color: #4A4A4A;
		font-size: 14px;
	}
	.enterprise-contact {
		color: #000000;
		opacity: 0.65;
		font-size: 12px;
	}
	.enterprise-tel {
		color: #000000;
		font-size: 14px;
	}

	&:hover {
	  	background-color: #00C587;
		span {
			color: #FFFFFF;
		}
		.enterprise-contact {
			color: #FFFFFF;
			opacity: 1;
			font-size: 12px;
		}
		transition: color 0.7s, background-color 0.7s;
		-webkit-transition: color 0.7s, background-color 0.7s;
		-moz-transition: color 0.7s, background-color 0.7s;
		-o-transition: color 0.7s, background-color 0.7s;
    }
}
.more-btn {
    width: 198px;
    height: 32px;
}
</style>