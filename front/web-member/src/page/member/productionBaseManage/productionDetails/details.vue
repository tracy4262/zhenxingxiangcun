<template>
	<div class="pd10">
		<Tabs size="small" value="productioncViewIndex" @on-click="goToPage">
	        <TabPane label="地理位置" name="productioncViewIndex"></TabPane>
	        <TabPane label="地形地貌" name="features"></TabPane>
	        <TabPane label="气候条件" name="climate"></TabPane>
	        <TabPane label="土地利用现状" name="land"></TabPane>
	        <TabPane label="生产环境" name="productionAmbient"></TabPane>
	        <TabPane label="交通条件" name="traffic"></TabPane>
	        <TabPane label="水利条件" name="water"></TabPane>
	        <TabPane label="电力条件" name="power"></TabPane>
	        <TabPane label="网络条件" name="signal"></TabPane>
	    </Tabs>
	    <router-view></router-view> 
	</div>
</template>

<script>
import location from './index'
export default {
	components: {
		location
	},
	data() {
		return {
		}
	},
	methods: {
        goToPage (e) {
            this.$router.push(`/member/${e}?id=${this.$route.query.id}&current=${this.$route.query.current}`)
        }
	},
	mounted () {
		// this.$router.push(`/member/productioncViewIndex?id=${this.$route.query.id}`)
	},
}
</script>

<style lang="scss">
</style>
