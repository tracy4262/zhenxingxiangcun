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
          <describe :data="describeData" @on-edit="handleEdit(0)"></describe>
          <item :title="fcausediseasesubjectData.title" :data="fcausediseasesubjectData.data" class="mb50" @on-edit="handleEdit(1)"></item>
          <item :title="fcommonfeatureData.title" :data="fcommonfeatureData.data" class="mb50" @on-edit="handleEdit(2)"></item>
          <item :title="fpathologycheckData.title" :data="fpathologycheckData.data" class="mb50" @on-edit="handleEdit(3)"></item>
          <item :title="fdiagnoseData.title" :data="fdiagnoseData.data" class="mb50" @on-edit="handleEdit(4)"></item>
          <item :title="fpreventionData.title" :data="fpreventionData.data" class="mb50" @on-edit="handleEdit(5)"></item>
          <!-- 相关知识资讯政策 -->
          <related></related>
        </Col>
        <Col span="6">
          <recommend-list :name="speciesName" ref="recommend"></recommend-list>
        </Col>
      </Row>
    </div>
    <edit ref="edit" @on-reload="handlegGetSpecies"></edit>
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
    fcausediseasesubjectData: {
      title: '病原学',
      data: ''
    },
    fcommonfeatureData: {
      title: '流行特点',
      data: ''
    },
    fpathologycheckData: {
      title: '病理剖检',
      data: ''
    },
    fdiagnoseData: {
      title: '诊断',
      data: ''
    },
    fpreventionData: {
      title: '防治',
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
    this.handlegGetSpecies()
  },
  methods: {
    // 查询详情
    handlegGetSpecies () {
      this.$api.get('wiki/api/wiki/getSpeciesDisease/' + this.indexid).then(response => {
        if (response.code === 200) {
          this.describeData = response.data
          this.speciesid = response.data.speciesid
          // 病原学
          this.fcausediseasesubjectData.data = response.data.fcausediseasesubject.replace(/<[^>]*>|&nbsp;/g, '')
          this.$refs['edit'].catalogData[1].data = this.fcausediseasesubjectData.data
          this.$refs['edit'].catalogData[1].fid = response.data.fid
          this.$refs['edit'].catalogData[1].speciesid = response.data.speciesid
          // 流行特点
          this.fcommonfeatureData.data = response.data.fcommonfeature.replace(/<[^>]*>|&nbsp;/g, '')
          this.$refs['edit'].catalogData[2].data = this.fcommonfeatureData.data
          this.$refs['edit'].catalogData[2].fid = response.data.fid
          this.$refs['edit'].catalogData[2].speciesid = response.data.speciesid
          // 病理剖检
          this.fpathologycheckData.data = response.data.fpathologycheck.replace(/<[^>]*>|&nbsp;/g, '')
          this.$refs['edit'].catalogData[3].data = this.fpathologycheckData.data
          this.$refs['edit'].catalogData[3].fid = response.data.fid
          this.$refs['edit'].catalogData[3].speciesid = response.data.speciesid
          // 诊断
          this.fdiagnoseData.data = response.data.fdiagnose.replace(/<[^>]*>|&nbsp;/g, '')
          this.$refs['edit'].catalogData[4].data = this.fdiagnoseData.data
          this.$refs['edit'].catalogData[4].fid = response.data.fid
          this.$refs['edit'].catalogData[4].speciesid = response.data.speciesid
          // 防治
          this.fpreventionData.data = response.data.fprevention.replace(/<[^>]*>|&nbsp;/g, '')
          this.$refs['edit'].catalogData[5].data = this.fpreventionData.data
          this.$refs['edit'].catalogData[5].fid = response.data.fid
          this.$refs['edit'].catalogData[5].speciesid = response.data.speciesid
          // 图片
          this.$refs['recommend'].albumData = response.data.fimagesrc
          this.$refs['edit'].getDescribeData(response.data)
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
