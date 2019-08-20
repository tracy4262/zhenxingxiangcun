<template>
  <div class="right-bar">
    <div class="right-bar-top">
      <Card>
        <Row>
          <Col span="24">
            <Cascader :data="data" v-model="info.select" @on-change="selectChange"></Cascader>
          </Col>
        </Row>
        <Row class="pt15 tr" v-if="showEdit">
          <Col span="24">
            <Button type="primary" class="mr10" :disabled="info.select.length == 0" @click="handleEditCoordinate">编辑</Button>
            <Button type="primary" class="mr10" :disabled="info.select.length == 0" @click="handleDelCoordinate">删除</Button>
            <Button type="primary" class="mr10" :disabled="info.select.length == 0 " @click="handleAdd('Point')">添加点</Button>
            <Button type="primary" class="mr10" :disabled="info.select.length == 0" @click="handleAdd('LineString')">添加线</Button>
            <Button type="primary" :disabled="info.select.length == 0" @click="handleAdd('Polygon')">添加面</Button>
          </Col>
        </Row>
      </Card>
    </div>
    <Card class="mt20 right-bar-form" v-if="showForm">
      <div slot="title" class="pt20">
        <Row type="flex" align="middle">
          <Col span="5">
            {{title}}
          </Col>
          <Col span="19" class="tr">
            <!-- <Button type="text" @click="handleCancel"><Icon type="md-close" /></Button> -->
          </Col>
        </Row>
      </div>
      <!-- 地块信息 -->
      <div>
        <div v-if="currentView == '04'">
          <Collapse>
            <Panel name="1">
              地块土壤氮磷钾含量信息
              <div slot="content">
                <landContent ref="landContent" @on-save="onSave"></landContent>
              </div>
            </Panel>
            <Panel name="2">
              地块信息
              <div slot="content">
                <landUseAuthority ref="landUseAuthority" @on-save="onSave"></landUseAuthority>
              </div>
            </Panel>
            <Panel name="3">
              地块土壤质量信息
              <div slot="content">
                <soilQuality ref="soilQuality" @on-save="onSave"></soilQuality>
              </div>
            </Panel>
            <Panel name="4">
              地块水质信息
              <div slot="content">
                <waterQuality ref="waterQuality" @on-save="onSave"></waterQuality>
              </div>
            </Panel>
            <!-- <Panel name="5">
              土地利用现状信息
              <div slot="content">
                <landStatus ref="landStatus" @on-save="onSave"></landStatus>
              </div>
            </Panel> -->
          </Collapse>
        </div>
        <!-- 自然地理信息 -->
        <div v-else-if="currentView == '05'">
          <Collapse>
            <Panel name="1">
              生物资源信息
              <div slot="content">
                <biological ref="biological" @on-save="onSave"></biological>
              </div>
            </Panel>
            <Panel name="2">
              国土面积信息
              <div slot="content">
                <geographyLand ref="geographyLand" @on-save="onSave"></geographyLand>
              </div>
            </Panel>
            <Panel name="3">
              气候信息
              <div slot="content">
                <climate ref="climate" @on-save="onSave"></climate>
              </div>
            </Panel>
            <Panel name="4">
              地质矿产信息
              <div slot="content">
                <minerals ref="minerals" @on-save="onSave"></minerals>
              </div>
            </Panel>
            <Panel name="5">
              地形地貌信息
              <div slot="content">
                <topography ref="topography" @on-save="onSave"></topography>
              </div>
            </Panel>
          </Collapse>
        </div>
        <components v-else :is="currentView" :ref="currentView" @on-save="onSave"></components>
        <div class="tc" :class="[(currentView == '04' || currentView == '05') ? 'mt20' : '']">
          <Button type="primary" :loading="loading" @click="handleSave">保存</Button>
        </div>
      </div>
    </Card>
  </div>
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
// import { selectData } from '@/views/Mixin';


export default {
  name: '',
  // mixins: [selectData],
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
  props: {
    data: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data () {
    return {
      showEdit: '',
      title: '创建要素',
      showForm: false,
      isNext: true,
      currentView: 'culturalHeritage',
      info: {
        select: []
      },
      selectList: {},
      active: 0,
      activeList: {},
      loading: false
    }
  },
  created () {
  },
  methods: {
    // 关闭 表单
    handleCancel () {
      this.hide()
      this.$emit('on-cancel')
    },
    // 隐藏表单
    hide () {
      this.showForm = false
    },
    // 显示表单 并将数据传递给表单
    handleShowForm (list) {
      this.loading = false
      this.showForm = true
      this.activeList = Object.assign({}, list)
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
    },
    // 调用每个表单的 保存事件返回的值
    onSave (e) {
      if (!e) {
        this.isNext = e
      }
    },
    // 保存
    handleSave () {
        this.loading = true
        this.isNext = true
        let dataList = {}
        // 地块信息
        if (this.currentView == '04') {
          this.$refs['landContent'].save()
          dataList = Object.assign(this.activeList, this.$refs[`landContent`].data)
          this.$refs['landUseAuthority'].save()
          dataList = Object.assign(this.activeList, this.$refs[`landUseAuthority`].data)
          this.$refs['soilQuality'].save()
          dataList = Object.assign(this.activeList, this.$refs[`soilQuality`].data)
          // this.$refs['landStatus'].save()
          // dataList = Object.assign(this.activeList, this.$refs[`landStatus`].data)
          this.$refs['waterQuality'].save()
          dataList = Object.assign(this.activeList, this.$refs[`waterQuality`].data)
        } else if (this.currentView == '05') { //自然地理信息
          this.$refs['biological'].save()
          this.$refs['geographyLand'].save()
          this.$refs['climate'].save()
          this.$refs['minerals'].save()
          this.$refs['topography'].save()
        } else { // 其他
          this.$refs[`${this.currentView}`].save()
          dataList = Object.assign(this.activeList, this.$refs[`${this.currentView}`].data)
        }
        // 表单全部验证通过
        if (this.isNext) {
          this.$emit('on-save', dataList)
          // this.showForm = false
        } else {
          this.loading = false
          this.$Message.error('请核对表单信息！')
        }
    },
    // 下拉改变
    selectChange (value, selectedData) {
      if (selectedData.length) {
        let obj = selectedData[selectedData.length - 1]
        this.currentView = value[value.length - 1]
        this.selectList = Object.assign({}, obj)
        this.showEdit = selectedData[0].showEdit
      } else {
        this.currentView = ''
        this.selectList ={}
        this.showEdit = ''
      }
      this.$emit('on-change', this.selectList)
      this.showForm = false
    },
    // 点击 添加点 或者添加面
    handleAdd (type) {
      this.$emit('on-add', {selectList: this.selectList, type: type})
      this.showForm = false
    },
    // 编辑编辑坐标
    handleEditCoordinate () {
      this.$emit('on-edit')
      this.showForm = false
    },
    // 点击删除
    handleDelCoordinate () {
      this.$emit('on-del')
      this.showForm = false
    }
  }
}
</script>

<style lang="less" scoped>
.right-bar{
  position: fixed;
  top: 110px;
  right: 20px;
  z-index: 999;
  .right-bar-top{
    width: 400px;
  }
  .right-bar-form{
    width: 400px;
    /deep/ .ivu-card-body{
      // min-height: 400px;s
      height: calc(100vh - 320px);
      overflow-y: scroll;
    }
    /deep/ .ivu-card-head{
      padding: 8px 16px;
    }
    /deep/ .ivu-card-head p{
      height: 34px;
      line-height: 34px;
    }
  }
}
</style>
