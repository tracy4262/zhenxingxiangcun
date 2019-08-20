<template>
  <div v-if="show">
    <Form :label-width="60" class="vui-form-sm mt25" v-if="add">
      <Row>
        <Col span="8">
          <FormItem label="位置">
            <Input :value="location" readonly />
          </FormItem>
        </Col>
        <Col span="5">
          <FormItem label="东经">
            <Input type="text" :value="center.lng" readonly />
          </FormItem>
        </Col>
        <Col span="5">
          <FormItem label="北纬">
            <Input type="text" :value="center.lat" readonly />
          </FormItem>
        </Col>
        <Col span="6" class="tr">
          <slot></slot>
        </Col>
      </Row>
    </Form>
    <!-- center是地图显示的区域 -->
    <baidu-map :ak="ak" :center="center" :zoom="zoom" :double-click-zoom="false" :scroll-wheel-zoom="true" @ready="handleMapReady" class="map">
      <!-- 标注点显示 markerDatas为点标识的 可多个-->
        <bm-marker v-for="(item,index) in markerDatas" :key="index" :position="item.point" @click="item.show=!item.show" :icon="item.icon">
          <bm-info-window :show="item.show" @close="item.show = false" class="bm-info-window" v-if="!getMap">
            <p>权利人：{{item.landUser}}</p>
            <p>地块编码：{{item.landCode}}</p>
            <p>地块名称：{{item.landName}}</p>
            <div class="tc">
              <Button type="default" @click="handleShowLandInfo" ghost class="mt10 mb10 t-grey">查看详情</Button>
            </div>
          </bm-info-window>
        </bm-marker>
      <!-- 框选区域  name名称只能中文，到区-->
      <bm-boundary :name="location" :strokeWeight="2" strokeColor="blue"></bm-boundary>
      <!-- 地图容器 视图 -->
      <bm-view style="height:100%;width:100%" />
    </baidu-map>
  </div>
</template>
<script>
import { BaiduMap, BmInfoWindow, BmMarker, BmView, BmBoundary } from 'vue-baidu-map'
export default {
  components: {
    BaiduMap,
    BmMarker,
    BmView,
    BmBoundary,
    BmInfoWindow
  },
  props: {
    add: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      show: false,
      getMap: false,
      location: '湖北武汉',
      ak: '7syPirZ2AWxacMfHeAfuujdDgFmxCB5g',
      // center 地图显示的中心区域
      center: {
          lng: 114.352619,
          lat: 30.548158
        },
      zoom: 10,
      // 标注点显示 markerDatas为点标识的 可多个
      markerDatas: [],
      styles: []
    }
  },
  created() {
  },
  methods: {
    init (center, location, markerDatas, init) {
      // init 是否定位
      this.getMap = init
      console.log('this.getMap', this.getMap)
      this.center = center
      this.location = location
      this.markerDatas = markerDatas
      this.show = true
    },
    // 点击查看地块
    handleShowLandInfo () {
      this.$emit('on-show-land')
    },
    // 地图加载完成
    handleMapReady(BMap) {
      if (this.getMap) {
        console.log('查询')
        let geolocation = new BMap.BMap.Geolocation()
        const v = this
        this.$Spin.show({
          render: h => {
            return h('div', [
              h('Icon', {
                class: 'spin',
                props: {
                  type: 'load-c',
                  size: 18
                }
              }),
              h('div', '定位中请稍后……')
            ])
          }
        })
        geolocation.getCurrentPosition(
          function(r) {
            if (this.getStatus() == BMAP_STATUS_SUCCESS) {
              let geocoder = new BMap.BMap.Geocoder()
              geocoder.getLocation(r.point, function(res) {
                console.log(res)
                v.center = res.point
                v.location = res.address
                v.markerDatas.push(res)
                v.$Spin.hide()
                v.$Message.success('定位成功')
              })
            } else {
              v.$Message.error('定位失败')
            }
          },
          { enableHighAccuracy: true }
        )
      }
    }
  }
}
</script>


<style lang="scss" scoped>
.map{
  height: 400px;
  width:100%;
}
.bm-info-window {
  p {
    line-height: 24px;
    color:#4b4b4b;
  }
  .ivu-btn-ghost.ivu-btn-dashed, .ivu-btn-ghost.ivu-btn-default{
    background:#f1f1f1;
  }
  .ivu-btn-ghost.ivu-btn-dashed:hover, .ivu-btn-ghost.ivu-btn-default:hover{
    border-color: #f1f1f1 !important;
    background:#f1f1f1;
  }
}

</style>