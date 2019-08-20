<template>
  <div>
    <Title title="地图地块查询"></Title>
    <div class="pd20">
      <baidu-map ref="map" @on-show-land="handleShowLandInfo" :add="false">
      </baidu-map>
    </div>
  </div>
</template>
<script>
  import baiduMap from './components/map'
  import Title from '../../components/title'
  export default {
    components: {
      baiduMap,
      Title
    },
    props: {
        id: {
            type: String
        },
        appId: {
            type: String
        }
    },
    data () {
      return {

      }
    },
    methods: {
      initTitle () {},
      // 点击查看详情
      handleShowLandInfo () {
        this.$emit('on-show-land')
      },
      init (item) {
        // item 如果有值 说明是从地块信息页面跳转过来的，那么需要直接选中当前地块位置
        if (item) {
           // 标注点显示 markerDatas为点标识的 可多个
          item.point = {
              lng: item.longitude,
              lat: item.latitude
            }
          item.show = true
          let markerDatas = []
          markerDatas.push(item)
          // center 地图显示的中心区域
          let center = item.point
          // 框选区域
          let location = item.location
          this.$refs['map'].init(center, location, markerDatas, false)
        } else {
          this.$api.post('/member-reversion/productionBase/landInfo/findLandInfo',{
                  account: this.$user.loginAccount,
                  dictId: this.id,
                  baseId: this.$route.query.id
              }).then(response => {
                  if (response.code == 200) {
                      let data = response.data.list
                      let markerDatas = []
                      data.forEach(e => {
                          e.point = {
                            lng: e.longitude,
                            lat: e.latitude
                          }
                          e.show = false
                      })
                      if (data.length) {
                        this.$refs['map'].init(data[0].point, '', data, false) // true 用于地图页面判断是否自动定位当前位置
                      } else {
                        this.$refs['map'].init({}, '', [], true) // true 用于地图页面判断是否自动定位当前位置
                      }
                  }
              })
        }
      }
    }
  }
</script>
<style>

</style>

