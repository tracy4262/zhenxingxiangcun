<template>
    <div>
        <Tabs size="small" value="airQuality" @on-click="goToPage">
            <TabPane label="空气质量" name="airQuality"></TabPane>
            <TabPane label="灌溉水质" name="irrigationWaterQuality"></TabPane>
            <TabPane label="渔业水质" name="fisheryWaterQuality"></TabPane>
            <TabPane label="畜禽养殖水质" name="livestockWaterQuality"></TabPane>
            <TabPane label="加工用水" name="processWater"></TabPane>
            <TabPane label="土壤质量" name="soilQuality"></TabPane>
            <TabPane label="土壤肥力" name="soilFertility"></TabPane>
            <TabPane label="食用菌栽培基质" name="edibleMushrooms"></TabPane>
        </Tabs>
        <router-view></router-view>
    </div>
</template>

<script>
export default {
    components:{
    },
    data() {
        return {
        }
    },
    methods: {
        goToPage (e) {
            this.$router.push(`/member/${e}?id=${this.$route.query.id}`)
        }
    },
    mounted () {
        // this.$router.push('/member/airQuality')
    }
}
</script>
<style lang="scss">
.table{
    border: 1px solid #dddee1;
    border-bottom: 0;
    border-right: 0;
    table{
        table-layout: auto !important;
        border-collapse: collapse;
        border-spacing: 0;
        width: 100%;
        th,td{
            padding-left: 10px;
            padding-right: 10px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: normal;
            word-break: break-all;
            box-sizing: border-box;
        }
    }
} 

</style>