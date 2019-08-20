<template>
    <div class="wrapper">
        <img src="../../../static/img/gate-service.png" height="250" width="100%" alt="">
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
import { navStatus } from '~page/companyGate/mixins/commonMixins'
import serverItem from './components/server-item'
export default {
    mixins: [navStatus],
    components:{
        serverItem
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
            this.$router.push('/personGate/service/' + name + '?uid=' + this.$route.query.uid)
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