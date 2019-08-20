<template>
<div>
  <top :address="false" ref="top" active="1"></top>
  <head-nav :active="4"></head-nav>
    <router-view></router-view>
</div>
</template>
<script>
import top from '~src/top'
import headNav from "../../51index/components/nav";
export default {
    components:{
        top,
        headNav
    },
    data () {
        return {
        }
    },
    created () {
    },
    methods:{
    }
}
</script>
<style lang="scss">
.service-tabs .ivu-tabs{
    width: 470px;
    margin: 0 auto;
}

</style>
