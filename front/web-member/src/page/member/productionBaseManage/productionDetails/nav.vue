<template>
	<Row type="flex" justify="space-between" class="ma-nav">
		<Col span="2">
            <router-link :to="{path:'/member/productionBaseDetail', query:{id:0}}">
                地理位置
            </router-link>
		</Col>
		<Col span="2">
            <router-link :to="{path:'/member/productionBaseDetail/features', query:{id:0}}">
                地形地貌
            </router-link>
		</Col>
		<Col span="2">
            <router-link :to="{path:'/member/productionBaseDetail/climate', query:{id:0}}">
                气候条件
            </router-link>
		</Col>
		<Col span="3">
            <router-link :to="{path:'/member/productionBaseDetail/land', query:{id:0}}">
                土地利用现状
            </router-link>
		</Col>
		<Col span="2">
            <router-link to="/51index">
                生产环境
            </router-link>
		</Col>
		<Col span="2">
            <router-link :to="{path:'/member/productionBaseDetail/traffic', query:{id:0}}">
                交通条件
            </router-link>
		</Col>
		<Col span="2">
            <router-link :to="{path:'/member/productionBaseDetail/water', query:{id:0}}">
                水利条件
            </router-link>
		</Col>
		<Col span="2">
            <router-link :to="{path:'/member/productionBaseDetail/power', query:{id:0}}">
                电力条件
            </router-link>
		</Col>
		<Col span="3">
            <router-link :to="{path:'/member/productionBaseDetail/signal', query:{id:0}}">
                网络通信条件
            </router-link>
		</Col>
	</Row>
</template>

<script>
export default {
	data() {
		return {
		}
	},
	props: {
    },
}
</script>

<style lang="scss">
.ma-nav{border-bottom: 1px solid #e3e3e3;line-height: 70px;}
</style>
