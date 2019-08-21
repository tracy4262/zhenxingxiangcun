<template>
	<!--3设置栏目第二步开始-->
	<div>
		<div style="margin: 26px 0 20px 0;padding: 0 20px">
			<div class="jingtu-down" style="padding: 0 10px;padding-bottom: 20px">
				<ul>
					<li :class="[this.$parent.$parent.$route.path=='/pro/member/step6/step7/step8' ? jing :'']">关注物种</li>
					<li :class="[this.$parent.$parent.$route.path=='/pro/member/step6/step7/step9' ? jing :'']">关注产品</li>
					<li :class="[this.$parent.$parent.$route.path=='/pro/member/step6/step7/step10' ? jing :'']">关注服务</li>
					<li :class="[this.$parent.$parent.$route.path=='/pro/member/step6/step7/step11' ? jing :'']">关注知识</li>
					<li :class="[this.$parent.$parent.$route.path=='/pro/member/step6/step7/step12' ? jing :'']">关注资讯</li>
					<li class="jd-r" :class="[this.$parent.$parent.$route.path=='/pro/member/step6/step7/step13' ? jing :'']">关注政策</li>
				</ul>
			</div>
		</div>
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
			jing: 'jing'
		}
	},
	methods: {

	},
	created: function() {
		/*var n=this.$parent.$parent.$parent.count1
		if(n<8){
			
		}*/
		this.$parent.$parent.$router.push('/pro/member/step6/step7/step8')
		this.$parent.$parent.$parent.count1 = 8
	},
}
</script>
<style scoped>
@import '../../css/identification.css';
.jing {
	color: #00c587;
}
</style>