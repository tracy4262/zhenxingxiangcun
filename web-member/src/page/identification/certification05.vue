
<template>
	<div>
		<Row class="tab-zhuce3">
			<Col span="1" :class="[this.$route.path=='/pro/member/step23/step24' ? biaoqian:'']"> 全部</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/step23/step25' ? biaoqian:'','tabzhuce1']">隐私信息</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/step23/step26' ? biaoqian:'','tabzhuce1']">网络信息</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/step23/step27' ? biaoqian:'','tabzhuce1']">教育经历</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/step23/step28' ? biaoqian:'','tabzhuce1']">工作经历</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/step23/step29' ? biaoqian:'','tabzhuce1']">种养信息</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/step23/step30' ? biaoqian:'','tabzhuce1']">政治面貌</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/step23/step31' ? biaoqian:'','tabzhuce1']">宗教信仰</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/step23/step32' ? biaoqian:'','tabzhuce1']">获得荣誉</Col>
			<Col span="2" :class="[this.$route.path=='/pro/member/step23/step33' ? biaoqian:'','tabzhuce1']">专业信息</Col>
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
			tabzhucetou: 'tabzhucetou',
			biaoqian: 'biaoqian'
		}
	},
	created: function() {
		var n=this.$parent.$parent.count1
		if(n<24){
			this.$parent.$router.push('/pro/member/step23/step24')
			this.$parent.$parent.count1 = 24
		}
		
	},
	methods: {

	}
}
</script>
<style scoped>
.biaoqian {
	color: #00c587;
	font-size: 14px;
}
.tabzhuce1 {
    font-size: 14px !important;
    width: 87px !important;
    text-align: right;
}
.tab-zhuce3{
    margin-top: 15px !important;
    margin-bottom: 30px;
}
</style>