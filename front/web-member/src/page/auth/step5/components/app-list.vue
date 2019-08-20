<template>
    <div class="mt40">
        <div class="app ml10 mr10 mb20 tc" v-for="(item, index) in appDatas" :key="index">
            <div><img :src="item.icon" alt=""></div>
            <div class="mt20 app-name">{{ item.appName }}</div>
            <div class="mt10 app-number">使用人数：{{ item.number }}</div>
            <div class="mt10 app-price">{{ item.price }}</div>
            <div class="mt15">
                <Button type="primary" v-if="!item.isAdd" @click="add(item, index)" style="width: 84px;">添加</Button>
                <Button v-else @click="cancel(item, index)" style="width: 84px;">取消</Button>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'app-list',
    props: {
        appData: {
            type: Array,
            default: []
        }
    },
    data () {
        return {
            appDatas: []
        }
    },
    created () {
        this.appDatas = this.appData
    },
    watch: {
        appData: {
            handler (newValue, oldValue) {
                this.appDatas = newValue
            },
            deep: true
        }
    },
    methods: {
        add (item, index) {
            this.$emit('on-change', item.appId)
        },
        cancel (item, index) {
            this.$emit('on-change', item.appId)
        }
    }
}
</script>
<style lang="scss" scoped>
.app {
    width: calc(100% / 5 - 20px);
	height: 252px;
	background: #FFFFFF;
	border: 1px solid #E8E8E8;
	border-radius: 3px;
	padding: 20px;
	display: inline-block;
}
.app-name {
    color: #4A4A4A;
    font-size: 16px;
    font-weight: bold;
}
.app-number {
    color: #4A4A4A;
    font-size: 12px;
}
.app-price {
    color: #00C587;
}
</style>

