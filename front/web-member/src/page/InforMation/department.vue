<template>
	<div class="mt40">
		<div class="enterprise-card ml10 mr10 mb20" v-for="item in depart" :key="item.id" v-if="depart.length > 0">
			<router-link :to="{path:'../govGate/index',query: {uid: item.loginAccount}}">
				<Col span="6">
					<Avatar class="ml5 mt5" size="large" :src="item.logoPictureList" />
				</Col>
				<Col span="18">
					<Row>
						<span class="ell enterprise-name" :title="item.govName">{{ item.govName }}</span>
					</Row>
					<Row class="enterprise-addr mt10">
						<Col span="24"><span>湖北武汉</span></Col>
					</Row>
					<Row class="mt10">
						<span class="ell-3 enterprise-intro">
							Ant Design是一个服务于企业级产品的设计体系，基于『确定』和『自然』的设计价值观和模块化的解决方案。
							Ant Design是一个服务于企业级产品的设计体系，基于『确定』和『自然』的设计价值观和模块化的解决方案。
						</span>
					</Row>
					<Row class="mt10">
						<span class="enterprise-contact">联系电话：</span><span class="enterprise-tel">027-88767643</span>
					</Row>
				</Col>
			</router-link>
		</div>
		<div class="tc mt30 mb60" v-if="depart.length > 0">
            <Button type="default" class="more-btn" @click="more">更多</Button>
        </div>
        <div class="ma-polic-img" v-else>
            <img src="../../img/ma-img-002.png"> 
            <p style="margin-top: 10px;">暂无数据</p>
        </div>
	</div>
</template>
<script>
import divider from '~components/divider'
export default {
	components: {
		divider
	},
	data() {
		return {
			depart: [],
		}
	},
	created() {
		this.show()
	},
	methods: {
		show () {
			this.$api.post('/member/govInfo/findByName/1', {
				addr: '',
				level: '',
				title: ''
			}).then(response => {
				if (response.code === 200) {
					this.depart = response.data.list.splice(0, 9)
				}
			}).catch(error => {
				this.$Message.error('操作异常！')
			})
		},
		more () {
			this.$router.push('/51index/departmentList')
		}
	}
}
</script>
<style lang="scss" scoped>
.enterprise-card {
	width: calc(100% / 3 - 20px);
	height: 191px;
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
	.enterprise-intro {
		color: #4A4A4A;
		font-size: 12px;
		line-height: 20px;
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
