<template>
	<div>
		<Row class="tab-zhuce2">
			<Col span="1" :class="[this.$route.path=='/pro/member/progress23/progress24' ? biaoqian:'',tabzhucetou]" @click.native="all"> 全部
			</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/progress23/progress25' ? biaoqian:'',tabzhuce]" @click.native="zhuce">隐私信息</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/progress23/progress26' ? biaoqian:'',tabzhuce]" @click.native="mobile">网络信息</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/progress23/progress27' ? biaoqian:'',tabzhuce]" @click.native="tecletge">教育经历</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/progress23/progress28' ? biaoqian:'',tabzhuce]" @click.native="work">工作经历</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/progress23/progress29' ? biaoqian:'',tabzhuce]" @click.native="plant">种养信息</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/progress23/progress30' ? biaoqian:'',tabzhuce]" @click.native="politics">政治面貌</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/progress23/progress31' ? biaoqian:'',tabzhuce]" @click.native="religion">宗教信仰</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/progress23/progress32' ? biaoqian:'',tabzhuce]" @click.native="honor">获得荣誉</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/progress23/progress33' ? biaoqian:'',tabzhuce]" @click.native="expert">专业信息</Col>
		</Row>
		<transition>
			<router-view></router-view>
		</transition>
	</div>
</template>
<script>
export default {
	data() {
		return {
			tabzhuce: 'tabzhuce',
			tabzhucetou: 'tabzhucetou',
			biaoqian: 'biaoqian'
		}
	},
	created: function() {
		this.$parent.$router.push('/pro/member/progress23/progress24')
		this.$parent.$parent.count1 = 24
	},
	methods: {
		all() {
			this.$parent.$parent.$router.push('/pro/member/progress23/progress24')
			this.$parent.$parent.count1 = 24
		},
		zhuce() {
			this.$parent.$parent.$router.push('/pro/member/progress23/progress25')
			this.$parent.$parent.count1 = 25
		},
		mobile() {
			this.$parent.$parent.$router.push('/pro/member/progress23/progress26')
			this.$parent.$parent.count1 = 26
		},
		tecletge() {
			this.$parent.$parent.$router.push('/pro/member/progress23/progress27')
			this.$parent.$parent.count1 = 27
		},

		work() {
			this.$parent.$parent.$router.push('/pro/member/progress23/progress28')
			this.$parent.$parent.count1 = 28
		},
		plant() {
			this.$parent.$parent.$router.push('/pro/member/progress23/progress29')
			this.$parent.$parent.count1 = 29
		},
		politics() {
			this.$parent.$parent.$router.push('/pro/member/progress23/progress30')
			this.$parent.$parent.count1 = 30
		},
		religion() {
			this.$parent.$parent.$router.push('/pro/member/progress23/progress31')
			this.$parent.$parent.count1 = 31
		},
		honor() {
			this.$parent.$parent.$router.push('/pro/member/progress23/progress32')
			this.$parent.$parent.count1 = 32
		},
		expert(){
			this.$parent.$parent.$router.push('/pro/member/progress23/progress33')
			this.$parent.$parent.count1 = 33
		}
	}
}
</script>
<style scoped>
@import '../../css/identification.css';	
.biaoqian {
	color: #00c587;
	font-size: 14px;
	text-align: center;
}
.tabzhuce {
    font-size: 14px !important;
    width: 87px !important;
    text-align: right;
}
.tab-zhuce2 {
	font-size: 14px;
	text-align: center;
}

.tab-zhuce2 .ivu-col-span-3 {
	display: block;
	width: 11.3333% !important;
	text-align: center;
	margin-left: -2px;
}
</style>
<style lang="scss">
	.vui-collapse-wrap{
		.hd{
			width: 80%;
			margin: 0 auto;
			padding: 5px;
			.edit-cour{
				cursor: pointer;
				color: #00c587;
				text-align: right;
				font-size: 15px;
			}
		}
	}
</style>