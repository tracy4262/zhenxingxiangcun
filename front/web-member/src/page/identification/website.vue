<template>
	<div >
		<Steps :current="current" style="margin-top: 20px;width: 860px;padding: 0 20px">
			<Step title="设置名称"></Step>
			<Step title="选择模板"></Step>
			<Step title="选择模块"></Step>
		</Steps>
		<transition>
			<router-view></router-view>
		</transition>
	</div>
</template>
<script>
export default {
	data() {
		return {
			current:0,
		}
	},
	created: function() {
		this.$router.push('/pro/member/step35/step36')
		this.$parent.$parent.count1 = 36
	},
	methods: {

	}
}
</script>
<style scoped>
</style>