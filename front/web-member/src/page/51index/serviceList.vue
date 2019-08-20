<template>
<div>
  <top :address="false" ref="top" active="1"></top>
  <head-nav :active="5"></head-nav>
    <!-- <div class="wrapper">
        <serviceSeach @on-search="onSearch" ></serviceSeach>
    </div> -->
    <router-view></router-view>
</div>
</template>
<script>
import serverItem from './components/server-item'
import serviceSeach from './components/serviceSeach'
import top from '~src/top'
import headNav from './components/nav'
export default {
    components:{
        serverItem,
        serviceSeach,
        top,
        headNav
    },
    data () {
        return {
            index: 8,
            total: 0,
            serverData: [],
            tabActive: 'all',
            info: {
                address: '',
                species: '',
                speciesId: '',
                industry: '',
                industryId: '',
                serviceName:''
            }
        }
    },
    created () {
       this.tabActive = this.$router.history.current.name
       console.log(this.tabActive)
    },
    methods:{
        // 分页
        handleChangePage () {
        },
        handleTabsClick (name) {
            console.log(name)
            this.tabActive = name
            this.$router.push('/51index/services/' + name)
        },
        onSearch (info) {
            this.info = info
        },
    }
}
</script>
<style lang="scss">
.service-tabs .ivu-tabs{
    width: 470px;
    margin: 0 auto;
}

</style>
