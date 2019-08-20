<template>
  <div>
    <top :address="false" active="3" />
    <baidu-map
    :ak="ak"
    :center="area.point"
    :zoom="zoom"
    :double-click-zoom="false"
    :scroll-wheel-zoom="true"
    @ready="handleMapReady"
    class="baidu-map">
      <!-- 自定义搜索 -->
      <bm-control offset="10" class="bm-control" v-if="searchPanel">
        <Cascader :data="cascader" v-model="location"></Cascader>
        <Input v-model="keyword" size="large" placeholder="请输入搜索关键字 基地名、专家、机关、企业" @on-change="handleSearch" style="width: 350px" icon="ios-search"></Input>
        <ul class="list">
          <li v-for="(item,index) in searchResultList" :key="index">
            <div @click="handleNav(item)">
              <Row type="flex" align="middle">
                <Col span="22">
                  <Row>
                    <Col span="2">
                      <Icon type="ios-location" size="20" class="t-red"></Icon>
                    </Col>
                    <Col span="22">
                      <p class="ell b">{{item.name}}</p>
                      <p class="ell t-grey" style="font-size:12px">{{item.address}}</p>
                    </Col>
                  </Row>
                </Col>
                <Col span="2" class="tc">
                  <Icon type="ios-navigate" size="20" class="t-green"></Icon>
                </Col>
              </Row>
            </div>
            <div v-if="item.checked">
              <p>驾车前往 {{item.address}}</p>
              <p>{{item.distance}} 需要 {{item.time}}</p>
            </div>
          </li>
        </ul>
      </bm-control>

      <!-- 标注点显示 -->
      <bm-marker v-for="(item,index) in markerDatas" :key="index" :position="{lng:item.positions[0],lat:item.positions[1]}" @click="item.pointModalShow=!item.pointModalShow" :icon="item.icon">
        <bm-info-window :show="item.pointModalShow" @close="item.pointModalShow = false">
          <a :href="item.url"> {{item.name}}</a><br/> 简介：
          <span v-html="item.description"></span>
        </bm-info-window>
      </bm-marker>
      <!-- <bml-marker-clusterer :averageCenter="true" :styles="styles">
      </bml-marker-clusterer>
      <!-- 地图容器 视图 -->
      <bm-view style="height:100%" />

      <!-- 定位 -->
      <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>

      <!-- 驾车导航 -->
      <bm-driving
      :start="start"
      :end="end"
      :startCity="startCity"
      :endCity="endCity"
      :panel="false"
      :auto-viewport="true"
      :selectFirstResult="true"
      @searchcomplete="searchcomplete"></bm-driving>

    </baidu-map>
  </div>
</template>
<script>
import top from '../top'
import {
  BaiduMap,
  BmMarker,
  BmInfoWindow,
  BmView,
  BmControl,
  BmGeolocation,
  BmlMarkerClusterer,
  BmDriving
} from 'vue-baidu-map'
export default {
  components: {
    top,
    BaiduMap,
    BmMarker,
    BmInfoWindow,
    BmView,
    BmControl,
    BmGeolocation,
    BmlMarkerClusterer,
    BmDriving
  },
  props: {},
  data() {
    return {
      ak: '7syPirZ2AWxacMfHeAfuujdDgFmxCB5g',
      area: {},
      zoom: 10,
      cascader: [],
      location: [],
      keyword: '',
      searchResultList: [{
        name: '湖北省农业科学研究院',
        address: '湖北省武汉市洪山区南湖瑶苑特1号',
        city: '武汉市',
        checked: false,
        point: {
          lng: 114.314029,
          lat: 30.009168
        },
      }, {
        name: '湖北省农业科学研究院',
        address: '湖北省武汉市洪山区南湖瑶苑特1号',
        city: '武汉市',
        checked: false,
        point: {
          lng: 114.533503,
          lat: 30.249684
        },
      }],
      searchPanel: true,
      start: '',
      end: '',
      startCity: '',
      endCity: '',
      pointModalShow: false,
      markerDatas: [],
      styles: []
    }
  },
  created() {
    this.loadData()
    this.handleAddOk()
    // const EXAMPLE_URL = "//localhost:8080/static/"
    // this.styles = [{
    //   gridSize: 100,
    //   url: `${EXAMPLE_URL}img/red.png`,
    //   size: { width: 92, height: 92 },
    //   opt_anchor: [16, 0],
    //   textColor: '#fff',
    //   opt_textSize: 10 },
    //   { url: `${EXAMPLE_URL}img/blue.png`,
    //   size: { width: 92, height: 92 },
    //   opt_anchor: [40, 35],
    //   textColor: '#fff', opt_textSize: 12 },
    //   { url: `${EXAMPLE_URL}img/red.png`,
    //   size: { width: 92, height: 92 },
    //   opt_anchor: [32, 0],
    //   textColor: '#fff',
    //   opt_textSize: 14
    // }]
  },
  methods: {
    // 地图加载完成
    handleMapReady (BMap) {
      //  navigator.geolocation.getCurrentPosition(function(success) {
      //    console.log(success)
      //  }, function(error){
      //    console.log(error)
      //  })
      // this.$Spin.show()
      let geolocation = new BMap.BMap.Geolocation()
      const v = this
      geolocation.getCurrentPosition(function(r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
          let geocoder = new BMap.BMap.Geocoder()
          geocoder.getLocation(r.point, function(res){
            // v.$Spin.hide()
            v.area = res
            v.startCity = res.addressComponents.city
            v.start = res.point
          })
        }
        else {
          alert('定位失败')
        }        
      },{enableHighAccuracy: true})
    },
    // 导航
    handleNav (item) {
      this.startCity = item.city
      this.end = item.point
      item.time = this.time
      item.distance = this.distance
      this.searchResultList.forEach(child => child.checked = false)
      item.checked = true
    },
    searchcomplete (results) {
      if (results) {
        var plan = results.getPlan(0)
        this.time = plan.getDuration(true)
        this.distance = plan.getDistance(true)
      }
    },
    // 搜索
    handleSearch(event) {
      this.loadData(event.target.value)
    },
    //显示标注点
    handleAddOk() {
      this.loadData()
    },
    loadData (name = '') {
      this.$api.post('/member/map-navigation/query-nearby', {
        name: name
      }).then(res => {
        this.markerDatas = []
        if (res.data.length) {
          res.data.forEach(val => {
            /**
             * type 区分账号 0 个人 ,1企业 ,4专家 , 3机关, 5乡村
             * kind 区分类型 1企业 ,2政府 ,3生产基地
             */
            let positions = val.coordinate.split(',')
            let name = val.detailName
            let type = val.type
            let id = val.id
            let positionName = val.position
            let pointModalShow = false
            let icon = {
              url: '../../static/img/person.png',
              size: { width: 40, height: 47 }
            }
            let kind = val.kind
            let url = ''
            if (type === 0) {
              url = '/companyGate/index?uid=' + val.account
              icon = {
                url: '../../static/img/person.png',
                size: { width: 40, height: 47 }
              }
            } else if (type === 1) {
              url = '/companyGate/index?uid=' + val.account
              icon = {
                url: '../../static/img/corp.png',
                size: { width: 40, height: 47 }
              }
            } else if (type === 4) {
              url = '/expertGate/index?uid=' + val.account
              icon = {
                url: '../../static/img/exp.png',
                size: { width: 40, height: 47 }
              }
            } else if (type === 3) {
              url = '/govGate/index?uid=' + val.account
              icon = {
                url: '../../static/img/gov.png',
                size: { width: 40, height: 47 }
              }
            } else if (type === 5) {
              url = '/ruralGate/index?uid=' + val.account
              icon = {
                url: '../../static/img/town.png',
                size: { width: 40, height: 47 }
              }
            }
            if (kind === 3) {
              icon = {
                url: '../../static/img/base.png',
                size: { width: 40, height: 47 }
              }
            }
            this.markerDatas.push({
              positions: positions,
              name: val.detailName,
              type: val.type,
              id: val.id,
              positionName: val.position,
              pointModalShow: false,
              icon: icon,
              url: url,
              description: val.description
            })
          })
        }
      })
    },
    handleInfoWinClose() {
      this.pointModalShow = false
    },
    handleInfoWinShow(event) {
      this.pointModalShow = true
    }
  },
  mounted() {
    
  }
}
</script>


<style lang="scss">
.baidu-map{
  width:100%;
  position: absolute;
  top:63px;
  bottom:0;
  // > div:last-child {
  //   position: absolute;
  //   top: 0;
  // }
}
.map-add-marker {
  .ivu-form-item {
    margin-bottom: 5px;
  }
}
.sample {
  width: 120px;
  height: 40px;
  background: rgba(0,0,0,0.5);
  color: #fff;
  text-align: center;
  padding: 10px;
  position: absolute;
}
.bm-control{
  background: #fff;
  box-shadow: 0 1px 5px rgba(0,0,0,.5);
  margin-top: 10px;
  padding: 10px;
  .ivu-input{
    border-width: 0 0 1px 0;
    border-radius: 0;
  }
  .list{
    margin-top: 20px;
    li {
      padding: 10px;
      cursor: pointer;
      border-bottom: 1px solid #eee;
      &:last-child{
        border: none;
      }
      &:hover{
        background: #F3F3F3;
      }
    }
  }
}
</style>