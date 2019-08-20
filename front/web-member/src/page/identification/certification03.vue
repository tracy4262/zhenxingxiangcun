<template>
	<!--3设置栏目第二步开始-->
	<div>
		<div class="jingtu-top">
			<ul class="clear">
				<li>设置关注</li>
				<li>设置收藏</li>
				<li>好友分组</li>
				<li class="jd-r">设置账户</li>
			</ul>
		</div>
		<Progress :percent="baifen" status="active" hide-info :stroke-width="5"></Progress>
		<transition>
			<router-view></router-view>
		</transition>
	</div>
	<!--3设置栏目第二步结束-->
</template>
<script>
import $ from 'jquery'
export default {
	data() {
		return {
			baifen: 25
		}
	},
	methods: {

	},
	created: function() {
		/*var n=this.$parent.$parent.count1
		if(n<7){
			this.$parent.$router.push('/pro/member/step6/step7')
			this.$parent.$parent.count1 = 7
		}*/
		this.$parent.$parent.$router.push('/pro/member/step6/step7')
		this.$parent.$parent.$parent.count1 = 7
		
	}
}
</script>
<style scoped>
@import '../../css/identification.css';
.jing {
	color: #00c587;
}
</style>