<template>
	<!--3设置栏目第二步开始-->
	<div>
		<div style="margin: 26px 0 20px 0;padding: 0 20px">
			<div class="jingtu-top" style="padding: 0 10px;">
				<ul class="clear">
					<li @click="gaunzhu" :class="[this.$route.path=='/pro/member/progress6/progress7/progress8'
													  ||this.$route.path=='/pro/member/progress6/progress7/progress9'
													  ||this.$route.path=='/pro/member/progress6/progress7/progress10'
													  ||this.$route.path=='/pro/member/progress6/progress7/progress11'
													  ||this.$route.path=='/pro/member/progress6/progress7/progress12'
													  ||this.$route.path=='/pro/member/progress6/progress7/progress13'? jing :'']">设置关注</li>
					<li @click="collection" :class="[this.$parent.$route.path=='/pro/member/progress6/progress14' ? jing :'']">设置收藏</li>
					<li @click="friends" :class="[this.$parent.$route.path=='/pro/member/progress6/progress15' ? jing :'']">好友分组</li>
					<li @click="account" class="jd-r" :class="[this.$parent.$route.path=='/pro/member/progress6/progress16'
																	||this.$parent.$route.path=='/pro/member/progress6/progress17'
																	||this.$parent.$route.path=='/pro/member/progress6/progress18' ? jing :'']">设置账户</li>
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
			baifen: 25,
			jing: 'jing',
		}
	},
	methods: {
		gaunzhu() {
			this.$parent.$router.push('/pro/member/progress6/progress7/progress8')
			this.$parent.$parent.count1 = 8
		},
		collection() {
			this.$parent.$router.push('/pro/member/progress6/progress14')
			this.$parent.$parent.count1 = 14
		},
		friends() {
			this.$parent.$router.push('/pro/member/progress6/progress15')
			this.$parent.$parent.count1 = 15
		},
		account() {
			this.$parent.$router.push('/pro/member/progress6/progress16')
			this.$parent.$parent.count1 = 16
		}
	},
	created: function() {
		
		let path=this.$parent.$route.path
		console.log("path",path)
		if('/pro/member/progress6/progress14' != path){
			this.$parent.$router.push('/pro/member/progress6/progress7')
			this.$parent.$parent.count1 = 7
		}
		
	},
}
</script>
<style scoped>
@import '../../css/identification.css';
.jing {
	color: #00c587;
}
</style>