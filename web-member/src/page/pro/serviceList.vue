<template>
    <div class="wrapper">
        <headNav :active="5" />
        <img src="../../../static/img/index-service.png" width="100%">
        <div class="layouts service-tabs pt20">
            <Tabs :value="tabActive" :animated="false" @on-click="handleTabsClick">
                <TabPane label="全部" name="all"></TabPane>
                <TabPane label="垂钓" name="fishing"></TabPane>
                <TabPane label="采摘" name="picking"></TabPane>
                <TabPane label="民宿" name="stay"></TabPane>
                <TabPane label="农家乐" name="restaurant"></TabPane>
                <TabPane label="景区" name="scenicSpot"></TabPane>
            </Tabs>
            <router-view></router-view>
        </div>
    </div>
</template>
<script>
import serverItem from './components/server-item'
import headNav from './components/headNav.vue';
export default {
    components:{
        serverItem,
        headNav
    },
    data () {
        return {
            index: 8,
            total: 0,
            serverData: [],
            tabActive: 'all'
        }
    },
    created () {
       this.tabActive = this.$router.history.current.name
    },
    methods:{
        // 分页
        handleChangePage () {
        },
        handleTabsClick (name) {
            this.$router.push('/pro/serviceList/' + name)
        }
    }
}
</script>
<style lang="scss">
.service-tabs .ivu-tabs{
    width: 470px;
    margin: 0 auto;
}

</style>