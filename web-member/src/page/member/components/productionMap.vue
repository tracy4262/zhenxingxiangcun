<template>
    <Modal
        :mask-closable="false"
        :transfer="transfer"
        width="700"
        v-model="showMap"
        title="点击地图添加坐标点"
        @on-ok="onOk">
        <baidu-map
        @click="handleMapClick"
        center="武汉"
        ak="7syPirZ2AWxacMfHeAfuujdDgFmxCB5g"
        :zoom="12"
        :double-click-zoom="false"
        :scroll-wheel-zoom="true"
        style="position: relative">
            <!-- 地图容器 -->
            <bm-view class="vui-map" />
            <!-- 缩放控件 -->
            <!-- <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation> -->
            <!-- 标注显示 -->
            <!-- <bm-marker :position="points"></bm-marker> -->

            <!-- 检索 -->
            <bm-control>
                <Card :bordered="false" :padding="10">
                    <Input v-model="keyword" placeholder="请输入地名关键字" icon="search" class="mb5" style="width: 260px"></Input>
                    <p v-if="Object.keys(points).length">您的坐标是: {{points.lng}}, {{points.lat}}</p>
                </Card>
            </bm-control>
            <bm-local-search :keyword="keyword"
            :auto-viewport="true"
            :panel="false"
            @infohtmlset="onInfohtmlset"></bm-local-search>
            <bm-context-menu>
                <bm-context-menu-item :callback="selectPoint" text="选取坐标点"></bm-context-menu-item>
            </bm-context-menu>

            <!-- 定位 -->
            <bm-geolocation
            anchor="BMAP_ANCHOR_BOTTOM_RIGHT"
            :showAddressBar="true"
            :autoLocation="true"
            @locationSuccess="locationSuccess"></bm-geolocation>

        </baidu-map>
    </Modal>

</template>
<script>
import {
    BaiduMap,
    BmView,
    BmMarker,
    BmControl,
    BmContextMenu,
    BmContextMenuItem,
    // BmNavigation,
    BmLocalSearch,
    BmGeolocation
} from 'vue-baidu-map'
export default {
    components: {
        BaiduMap,
        BmView,
        BmMarker,
        BmControl,
        BmContextMenu,
        BmContextMenuItem,
        // BmNavigation,
        BmLocalSearch,
        BmGeolocation
    },
    props: {
      transfer: {
        type: Boolean,
        default: false
      },
        point:{
            type:String,
            default: function () {
                return ''
            }
        }
    },
    data() {
        return {
            // ak: 'neHcCTLl3Swnjmtu3tr8nXVZQVVAqQfs',
            // ak: '3i2MivMgsk6HpkktrEdeePLj',
            // keyword: '',
            showMap: false,
            keyword: '',
            points: {}
        }
    },
    created(){
    },
    methods:{
        handleMapClick(event){
            // var lng = event.point.lng
            // var lat = event.point.lat
            // this.points={lng:lng, lat: lat}
        },
        selectPoint (event) {
            this.points = event.point
        },
        locationSuccess (data) {
            this.points = data.point
            this.$Notice.open({
                desc: '如果定位点和您的实际位置偏差较大，建议您使用GPS类软件或设备获取更精确的经纬度数据'
            })
        },
        onInfohtmlset (LocalResultPoi) {
            this.points = LocalResultPoi.point
        },
        onOk () {
            this.$emit('on-get-point', this.points)
        }
    },
    mounted () {
    },
    watch:{
        point(curVal){
            // this.points = this.point
            console.log(curVal);
            if(curVal){
                this.points = {}
                var lng = curVal.split(',')[0]
                var lat = curVal.split(',')[1]
                this.points={lng:lng, lat: lat}
            }else{
                this.points = {}
            }
            console.log(this.points)
        }
    }
}
</script>

<style lang="scss">
.vui-map {
  width: 100%;
  height: 300px;
}
.tangram-suggestion-main{
    height: 400px;
    overflow-y: auto;
    overflow-x: hidden;
}
 .BMap_noprint.anchorTL{
    top: 10px !important;
    left: 10px !important;
 }
</style>
