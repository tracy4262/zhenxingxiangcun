
<template>
	<!--3设置栏目第二步开始-->
	<div>
		<div style="margin: 26px 0 20px 0;padding: 0 20px">
			<div class="jingtu-down" style="padding: 0 10px;padding-bottom: 20px">
				<ul class="clear">
					<li @click="follow" :class="[this.$parent.$route.path=='/pro/member/progress6/progress7/progress8' ? jing :'']">关注物种</li>
					<li @click="follow1" :class="[this.$parent.$route.path=='/pro/member/progress6/progress7/progress9' ? jing :'']">关注产品 </li>
					<li @click="follow2" :class="[this.$parent.$route.path=='/pro/member/progress6/progress7/progress10' ? jing :'']">关注服务 </li>
					<li @click="follow3" :class="[this.$parent.$route.path=='/pro/member/progress6/progress7/progress11' ? jing :'']">关注知识</li>
					<li @click="follow4" :class="[this.$parent.$route.path=='/pro/member/progress6/progress7/progress12' ? jing :'']">关注资讯</li>
					<li @click="follow5" :class="[this.$route.path=='/pro/member/progress6/progress7/progress13' ? jing :'',jdr]">关注政策</li>
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
			jing: 'jing',
			jdr: 'jd-r'
		}
	},
	methods: {
		follow() {
			this.$parent.$parent.$router.push('/pro/member/progress6/progress7/progress8')
			this.$parent.$parent.$parent.count1 = 8
		},
		follow1() {
			this.$parent.$parent.$router.push('/pro/member/progress6/progress7/progress9')
			this.$parent.$parent.$parent.count1 = 9
		},
		follow2() {
			this.$parent.$parent.$router.push('/pro/member/progress6/progress7/progress10')
			this.$parent.$parent.$parent.count1 = 10
		},
		follow3() {
			this.$parent.$parent.$router.push('/pro/member/progress6/progress7/progress11')
			this.$parent.$parent.$parent.count1 = 11
		},
		follow4() {
			this.$parent.$parent.$router.push('/pro/member/progress6/progress7/progress12')
			this.$parent.$parent.$parent.count1 = 12
		},
		follow5() {
			this.$parent.$parent.$router.push('/pro/member/progress6/progress7/progress13')
			this.$parent.$parent.$parent.count1 = 13
		}
	},
	created: function() {
		this.$parent.$parent.$router.push('/pro/member/progress6/progress7/progress8')
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