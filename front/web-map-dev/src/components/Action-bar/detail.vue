<template>

<Modal
  v-model="showForm"
  :title="title"
  width="800"
  >
    <!-- 地块信息 -->
    <!-- 自然地理信息 -->
    <div>
      <div v-if="currentView == '04'">
        <Collapse>
          <Panel name="1">
            地块土壤氮磷钾含量信息
            <div slot="content">
              <landContent ref="landContent" view></landContent>
            </div>
          </Panel>
          <Panel name="2">
            地块信息
            <div slot="content">
              <landUseAuthority ref="landUseAuthority" view></landUseAuthority>
            </div>
          </Panel>
          <Panel name="3">
            地块土壤质量信息
            <div slot="content">
              <soilQuality ref="soilQuality" view></soilQuality>
            </div>
          </Panel>
          <Panel name="4">
            地块水质信息
            <div slot="content">
              <waterQuality ref="waterQuality" view></waterQuality>
            </div>
          </Panel>
        </Collapse>
      </div>
      <div v-else-if="currentView == '05'">
        <Collapse>
          <Panel name="1">
            生物资源信息
            <div slot="content">
              <biological ref="biological" ></biological>
            </div>
          </Panel>
          <Panel name="2">
            国土面积信息
            <div slot="content">
              <geographyLand ref="geographyLand" ></geographyLand>
            </div>
          </Panel>
          <Panel name="3">
            气候信息
            <div slot="content">
              <climate ref="climate" ></climate>
            </div>
          </Panel>
          <Panel name="4">
            地质矿产信息
            <div slot="content">
              <minerals ref="minerals" ></minerals>
            </div>
          </Panel>
          <Panel name="5">
            地形地貌信息
            <div slot="content">
              <topography ref="topography" ></topography>
            </div>
          </Panel>
        </Collapse>
      </div>
      <components v-else :is="currentView" :ref="currentView" view></components>
    </div>
    <div slot="footer"></div>
  </div>
</Modal>
</template>

<script>
// 文化景观
import culturalHeritage from './components/culturalLandscape/culturalHeritage'
import famousScenery from './components/culturalLandscape/famousScenery'

// 公共设施
import communicationFacilitiesAndUser from './components/communalFacilities/communicationFacilitiesAndUser'
import culturalFacility from './components/communalFacilities/culturalFacility'
import educationalFacilities from './components/communalFacilities/educationalFacilities'
import garbageDisposalFacilities from './components/communalFacilities/garbageDisposalFacilities'
import pensionFacilities from './components/communalFacilities/pensionFacilities'
import runningWaterAndUser from './components/communalFacilities/runningWaterAndUser'
import securityFacilities from './components/communalFacilities/securityFacilities'
import sportsFacilities from './components/communalFacilities/sportsFacilities'
import trafficAndUser from './components/communalFacilities/trafficAndUser'
import waterConservancyFacilities from './components/communalFacilities/waterConservancyFacilities'

// 营业网点
import businessOutlets from './components/businessOutlets/businessOutlets'

// 地块信息
import landContent from './components/landInfo/landContent'
import landUseAuthority from './components/landInfo/landUseAuthority'
import soilQuality from './components/landInfo/soilQuality'
// import landStatus from './components/landInfo/status'
import waterQuality from './components/landInfo/waterQuality'

// 自然地理信息
import biological from './components/geography/biological'
import geographyLand from './components/geography/land'
import climate from './components/geography/climate'
import minerals from './components/geography/minerals'
import topography from './components/geography/topography'
export default {
  name: '',
  components: {
    // 文化景观
    culturalHeritage,
    famousScenery,
    // 公共设施
    communicationFacilitiesAndUser,
    culturalFacility,
    educationalFacilities,
    garbageDisposalFacilities,
    pensionFacilities,
    runningWaterAndUser,
    securityFacilities,
    sportsFacilities,
    trafficAndUser,
    waterConservancyFacilities,
    // 营业网点
    businessOutlets,
    // 地块信息
    landContent,
    landUseAuthority,
    soilQuality,
    // landStatus,
    waterQuality,
    // 自然地理信息
    biological,
    climate,
    minerals,
    topography,
    geographyLand
  },
  data() {
    return {
      showForm: false,
      title: '',
      currentView: '',
      name: ''
    }
  },
  methods: {
    // 显示表单 并将数据传递给表单
    handleShowForm (list, title, name, currentView) {
      this.title = title
      this.name = name
      this.currentView = currentView
      this.showForm = true
      this.$nextTick(() => {
        // 地块信息
        if (this.currentView == '04') {
          this.$refs['landContent'].initShow(list)
          this.$refs['landUseAuthority'].initShow(list)
          this.$refs['soilQuality'].initShow(list)
          // this.$refs['landStatus'].initShow(list)
          this.$refs['waterQuality'].initShow(list)
        } else if (this.currentView == '05') { //自然地理信息
          this.$refs['biological'].initShow(list)
          this.$refs['geographyLand'].initShow(list)
          this.$refs['climate'].initShow(list)
          this.$refs['minerals'].initShow(list)
          this.$refs['topography'].initShow(list)
        } else { // 其他
          this.$refs[`${this.currentView}`].initShow(list)
        }
      })
    }
  },
}
</script>

<style lang="less" scoped>
@import '../../css/colors.less';
  .close{
    cursor: pointer;
    &:hover{
      color: @link-color;
    }
  }
</style>