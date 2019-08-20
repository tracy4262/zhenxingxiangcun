<template>
  <div>
    <top :address="false" active="2" />
    <baidu-map id="baidu-map" :ak="ak" :center="center" :zoom="zoom" :double-click-zoom="false" :scroll-wheel-zoom="true" @ready="handleMapReady" class="baidu-map mt40">
      <!-- 自定义搜索 -->
      <bm-control offset="10" class="bm-control">
        <!-- 搜索面板 -->
        <search :data="searchList" v-show="searchPanelShow" @on-search="handleSearch" @on-click="handleNav"></search>
        <nav-panel v-show="!searchPanelShow" @on-back="handleBack">
          <Input v-model="startStr" placeholder="起点位置"></Input>
          <Input v-model="end" placeholder="终点位置"></Input>
        </nav-panel>
      </bm-control>

      <!-- 标注点显示 -->
      <bml-marker-clusterer :averageCenter="true" :styles="styles">
        <bm-marker v-for="(item,index) in markerDatas" :key="index" :position="item.point" @click="item.show=!item.show" :icon="item.icon">
          <bm-info-window :show="item.show" @close="item.show = false">
            <span class="t-green" @click="getPortals(item.account)"> {{item.name}}</span><br/> 简介：
            <span v-html="item.brief"></span>
          </bm-info-window>
        </bm-marker>
      </bml-marker-clusterer>
      <!-- 地图容器 视图 -->
      <bm-view style="height:100%" />
      <!-- 地图 区域 框选 -->
      <bm-polygon :path="polygonPath" stroke-color="blue" :stroke-opacity="0.5" :stroke-weight="2"></bm-polygon>
      <!-- 定位 -->
      <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true" @locationSuccess="locationSuccess"></bm-geolocation>

      <!-- <bm-transit
      v-if="navType === '公交'"
      :start="start"
      :end="end"
      :auto-viewport="true"
      location="武汉"></bm-transit> -->
      <bm-driving :start="start" :end="end" :auto-viewport="true"></bm-driving>
      <!-- <bm-walking
      v-else
      :start="start"
      :end="end"
      :auto-viewport="true"
      location="武汉"></bm-walking> -->
    </baidu-map>
  </div>
</template>
<script>
import top from '../../top'
import search from './components/search'
import navPanel from './components/nav'
import {
  BaiduMap,
  BmMarker,
  BmInfoWindow,
  BmView,
  BmControl,
  BmGeolocation,
  BmlMarkerClusterer,
  BmTransit,
  BmDriving,
  BmWalking,
  BmPolygon
} from 'vue-baidu-map'
export default {
  components: {
    top,
    search,
    navPanel,
    BaiduMap,
    BmMarker,
    BmInfoWindow,
    BmView,
    BmControl,
    BmGeolocation,
    BmlMarkerClusterer,
    BmTransit,
    BmDriving,
    BmWalking,
    BmPolygon
  },
  props: {},
  data() {
    return {
      ak: '7syPirZ2AWxacMfHeAfuujdDgFmxCB5g',
      center: { lng: 114.549804, lat: 30.486622 },
      // 114.269853,30.04322
      // center: { lng: 114.269853, lat: 30.04322 },
      area: {
        // province: '湖北省',
        // city: '武汉市',
        // district: '武昌区',
        // street: '街道口',
        // address: '湖北省武汉市武昌区街道口',
        // business: '',
        // point: {
        //   lat: 30.581084,
        //   lng: 114.3162
        // }
      },
      start: '',
      startStr: '',
      end: '',
      zoom: 10,
      searchPanelShow: true,
      pointModalShow: false,
      markerDatas: [],
      searchList: [],
      styles: [],
      BMap: null,
      navData: {},
      polygonPath: []
    }
  },
  created() {
    // this.loadData()
    const EXAMPLE_URL = '//localhost:8080/static/'
    this.styles = [
      {
        gridSize: 100,
        url: `${EXAMPLE_URL}img/red.png`,
        size: { width: 92, height: 92 },
        opt_anchor: [16, 0],
        textColor: '#fff',
        opt_textSize: 10
      },
      {
        url: `${EXAMPLE_URL}img/blue.png`,
        size: { width: 92, height: 92 },
        opt_anchor: [40, 35],
        textColor: '#fff',
        opt_textSize: 12
      },
      {
        url: `${EXAMPLE_URL}img/red.png`,
        size: { width: 92, height: 92 },
        opt_anchor: [32, 0],
        textColor: '#fff',
        opt_textSize: 14
      }
    ]
  },
  methods: {
    getPortals(account) {
      this.$toPortals(account)
    },
    // 地图加载完成
    handleMapReady(BMap) {
      this.map = BMap.map
      this.BMap = BMap
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
          console.log('r', r)
          if (this.getStatus() == BMAP_STATUS_SUCCESS) {
            let geocoder = new BMap.BMap.Geocoder()
            geocoder.getLocation(r.point, function(res) {
              v.$Spin.hide()
              v.area = {
                city: res.addressComponents.city,
                district: res.addressComponents.district,
                province: res.addressComponents.province,
                street: res.addressComponents.street,
                address: res.address,
                business: res.business,
                point: res.point
              }
              v.center = res.point
              let location = `${r.point.lng},${r.point.lat}`
              var myGeo = new BMap.BMap.Geocoder()
              // // 根据坐标得到地址描述 取到的跟 res 一样
              // myGeo.getLocation(res.point, function(result){
              //     if (result){
              //       console.log(result)
              //     }
              // })
              // // 高德地图 地理逆编码
              // let location = `${r.point.lng},${r.point.lat}`
              // let url = `https://restapi.amap.com/v3/geocode/regeo?location=${location}&key=95adbfe8184e519713d2b5c9bcd97019`
              // v.$api.ajaxGet(url).then(res => {
              //   if (res.status == 1) {
              //     v.area.address = res.regeocode.formatted_address
              //   }
              // })
            })
          } else {
            v.$Message.error('定位失败')
          }
        },
        { enableHighAccuracy: true }
      )
    },
    // 搜索
    handleSearch(obj, val) {
      if (val && val !== '') this.center = val
      this.loadData({name: obj.keyword, label: obj.area}, true)
    },
    // 点击导航
    handleNav(item) {
      this.navData = item
      this.start = `${this.area.point.lng},${this.area.point.lat}`
      this.startStr = this.area.address
      this.end = item.end
      this.searchPanelShow = false
    },
    // 返回搜索列表
    handleBack() {
      this.searchPanelShow = true
      this.navData = {}
      this.start = ''
      this.end = ''
    },
    loadData(obj = {name: '', label: ''}, search = false) {
      this.$api
        .post('/member/map-navigation/query-nearby', {
          name: obj.name,
          label: obj.label,
          coordinate: `${this.center.lng},${this.center.lat}`
        })
        .then(res => {
          this.markerDatas = []
          this.searchList = []
          if (res.data && res.data.length) {
            this.zoom = 15
            res.data.forEach(item => {
              /**
               * type 区分账号 0 个人 ,1企业 ,4专家 , 3机关, 5乡村
               * kind 区分类型 1企业 ,2政府 ,3生产基地
               */
              let point = item.point
              let name = item.name
              let type = parseInt(item.type)
              let id = item.id
              let show = false
              let kind = item.kind
              let url = ''
              let account = item.account
              let brief = item.description
              let icon = {
                url: '../../static/img/person.png',
                size: { width: 40, height: 47 }
              }
              if (type === 0) {
                // url = `/companyGate/index?uid=${account}`
                icon = Object.assign(icon, { url: '../../static/img/person.png' })
              } else if (type === 1) {
                // url = `/companyGate/index?uid=${account}`
                icon = Object.assign(icon, { url: '../../static/img/corp.png' })
              } else if (type === 4) {
                // url = `/expertGate/index?uid=${account}`
                icon = Object.assign(icon, { url: '../../static/img/exp.png' })
              } else if (type === 3) {
                // url = `/govGate/index?uid=${account}`
                icon = Object.assign(icon, { url: '../../static/img/gov.png' })
              } else if (type === 5) {
                // url = `/ruralGate/index?uid=${account}`
                icon = Object.assign(icon, { url: '../../static/img/town.png' })
              }
              if (kind === 3) {
                icon = Object.assign(icon, { url: '../../static/img/base.png' })
              }
              if (name == '湖北省武汉市江夏区山坡乡先锋村') {
                // url = 'http://192.168.7.42/portal/index.html'
                this.center = { lng: 114.269853, lat: 30.04322 }
              }
              this.markerDatas.push({
                point,
                name,
                type,
                id,
                show,
                icon,
                url,
                brief,
                end: item.end ? item.end.split('/').join('') : '',
                endCity: item.endCity,
                account: item.account
              })
              if (search === true) {
                this.searchList = this.markerDatas
              }
            })
          }
        })
    },
    handleInfoWinClose() {
      this.pointModalShow = false
    },
    handleInfoWinShow(event) {
      this.pointModalShow = true
    },
    // 定位成功
    locationSuccess (data) {
      this.center = data.point
      this.zoom = 13
      this.$Notice.open({
        desc: '如果定位点和您的实际位置偏差较大，建议您使用GPS类软件或设备获取更精确的经纬度数据'
      })
      // 框选江夏区
      // var Boundary = new BMap.Boundary()
      // Boundary.get('江夏区', (rs) => {
      //   console.log(rs)
      //   let count = rs.boundaries.length
      //   let arr = []
      //   for (var i = 0; i < count; i++) {
      //     let arrs = rs.boundaries[i].split(',')
      //       arrs.forEach((e, index) => {
      //         if (e.split(';').length > 1) {
      //           arr.push({lng: e.split(';')[1], lat: e.split(';')[0]})
      //         }
      //       })
      //     }
      //     this.polygonPath = arr
      // })
    }
  },
  mounted() {}
}
</script>


<style lang="scss">
.baidu-map {
  width: 100%;
  position: absolute;
  top: 63px;
  bottom: 0;
  .anchorBR{
    bottom: 60px !important;
  }
  > div:last-child {
    position: absolute;
    top: 168px;
    left: 10px;
    width: 320px;
    bottom: 10px;
    > div {
      height: 100%;
      overflow-y: auto;
      overflow-x: hidden;
    }
    .navtrans-res {
      overflow-y: auto;
      overflow-x: hidden;
      position: absolute;
      top: 115px;
      bottom: 10px;
    }
  }
  > div:last-child:empty {
    left: -999px;
  }
  .sel_n .sel_body_name {
    height: auto;
  }
}
.map-add-marker {
  .ivu-form-item {
    margin-bottom: 5px;
  }
}
.sample {
  width: 120px;
  height: 40px;
  background: rgba(0, 0, 0, 0.5);
  color: #fff;
  text-align: center;
  padding: 10px;
  position: absolute;
}
.bm-control {
  width: 320px;
  margin-top: 10px;
  .ivu-input {
    border-width: 0 0 1px 0;
    border-radius: 0;
    &:focus {
      box-shadow: none;
    }
  }
}
.spin {
  animation: spin 1s linear infinite;
}
@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(180deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
