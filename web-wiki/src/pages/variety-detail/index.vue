<template>
  <div class="wiki-detail-bg">
    <div class="pt80 pb20">
      <div class="vui-layout">
        <wiki-search @on-get-keyword="handleKeyord" select></wiki-search>
      </div>
    </div>
    <div class="vui-layout pd20" style="background:#fff">
      <Breadcrumb class="pb30">
        <BreadcrumbItem to="/">物种百科</BreadcrumbItem>
        <BreadcrumbItem :to="{path:'/detail', query:{indexid: parentId, speciesid: speciesid , classId: classId, speciesName: speciesName}}">{{speciesName}}</BreadcrumbItem>
        <BreadcrumbItem>{{describeData.fname}}</BreadcrumbItem>
      </Breadcrumb>
      <Row>
        <Col span="18" class="pr20">
          <describe :data="describeData" :speciesName="speciesName"  @on-edit="handleEdit(0)"></describe>
          <!-- <item :title="item.title" :data="item.data" :key="index" v-for="(item, index) in detailData" class="mb50" @on-edit="handleEdit(index + 1)"></item> -->
          <item :title="featureData.title" :data="featureData.data" class="mb50" @on-edit="handleEdit(1)"></item>
          <item :title="yieldData.title" :data="yieldData.data" class="mb50" @on-edit="handleEdit(2)"></item>
          <item :title="cultivateData.title" :data="cultivateData.data" class="mb50" @on-edit="handleEdit(3)"></item>
          <item :title="suitableData.title" :data="suitableData.data" class="mb50" @on-edit="handleEdit(4)"></item>
          <item :title="fmarketsituationData.title" :data="fmarketsituationData.data" class="mb50" @on-edit="handleEdit(5)"></item>
          <!-- 相关知识资讯政策 -->
          <related></related>
        </Col>
        <Col span="6">
          <recommend-list :name="speciesName" ref="recommend"></recommend-list>
        </Col>
      </Row>
    </div>
    <edit ref="edit" :speciesid="speciesid" @on-reload="handleGetVarietey"></edit>
    <login-register ref="loginRegister" @on-success="handleSuccess"></login-register>
  </div>
</template>
<script>
import wikiSearch from '~components/wiki-search'
import describe from './components/describe'
import item from './components/item'
import recommendList from '~components/recommend-list'
import edit from './edit'
import {loginuserinfo} from '~components/mixins'
import loginRegister from '~components/loginRegister/index'
import related from '../detail/components/related'
export default {
  components: {
    wikiSearch,
    describe,
    item,
    recommendList,
    edit,
    loginRegister,
    related
  },
  mixins: [loginuserinfo],
  data: () => ({
    describeData: {},
    speciesName: '',
    featureData: {
      title: '特征特性',
      data: ''
    },
    yieldData: {
      title: '产量',
      data: ''
    },
    cultivateData: {
      title: '栽培技术',
      data: ''
    },
    suitableData: {
      title: '适宜区域',
      data: ''
    },
    fmarketsituationData: {
      title: '推广现状',
      data: ''
    },
    indexid: '',
    classId: '',
    speciesid: '',
    parentId: ''
  }),
  created () {
    this.classId = this.$route.query.classId
    this.indexid = this.$route.query.indexid
    this.parentId = this.$route.query.parentId
    this.speciesName = this.$route.query.speciesName
    this.handleGetVarietey()
  },
  methods: {
    handleGetVarietey () {
      this.$api.get('wiki/api/wiki/getSpeciesVarietey/' + this.indexid).then(response => {
        if (response.code === 200) {
          // 简介
          this.describeData = response.data
          this.speciesid = response.data.speciesid
          this.$refs['edit'].getDescribeData(Object.assign({}, response.data))
          // 物种id
          this.speciesid = response.data.speciesid
          // 特性特征
          this.featureData.data = response.data.ffeature.replace(/<[^>]*>|&nbsp;/g, '')
          this.$refs['edit'].catalogData[1].data = response.data.ffeature
          this.$refs['edit'].catalogData[1].fid = response.data.fid
          // 产量
          this.yieldData.data = response.data.foutput.replace(/<[^>]*>|&nbsp;/g, '')
          this.$refs['edit'].catalogData[2].data = response.data.foutput
          this.$refs['edit'].catalogData[2].fid = response.data.fid
          // 栽培技术
          this.cultivateData.data = response.data.fgrowteachology.replace(/<[^>]*>|&nbsp;/g, '')
          this.$refs['edit'].catalogData[3].data = response.data.fgrowteachology
          this.$refs['edit'].catalogData[3].fid = response.data.fid
          // 适宜区域
          this.suitableData.data = response.data.fsuiteplatearea.replace(/<[^>]*>|&nbsp;/g, '')
          this.$refs['edit'].catalogData[4].data = response.data.fsuiteplatearea
          this.$refs['edit'].catalogData[4].fid = response.data.fid
          // 推广现状
          this.fmarketsituationData.data = response.data.fmarketsituation.replace(/<[^>]*>|&nbsp;/g, '')
          this.$refs['edit'].catalogData[5].data = response.data.fmarketsituation
          this.$refs['edit'].catalogData[5].fid = response.data.fid
          // 图片
          this.$refs['recommend'].albumData = response.data.ficon
        }
      })
    },
    // 搜索
    handleKeyord (item) {
      let indexid = item.indexid
      let speciesid = item.speciesid
      let classId = item.fclassifiedid
      let path = `${this.$router.history.base}/detail?indexid=${indexid}&speciesid=${speciesid}&classId=${classId}`
      window.location.href = path
    },
    // 编辑
    handleEdit (active) {
      if (this.loginuserinfo === null) {
        this.$Message.error('请先登录')
        this.$refs['loginRegister'].loginuser()
      } else {
        this.$refs.edit.show = true
        this.$refs.edit.active = active
      }
    },
    // 登录成功的回调
    handleSuccess (response) {
      sessionStorage.setItem('key', response.data.key)
      response.data.proxy.forEach(element => {
        sessionStorage.setItem(element.account, JSON.stringify(element.session))
      })
      this.loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
      console.log(sessionStorage)
      console.log('session', sessionStorage.getItem(sessionStorage.getItem('key')))
      window.location.reload()
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
