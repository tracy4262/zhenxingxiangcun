<template>
  <div class="wiki-detail-bg">
    <div class="pt80 pb20">
      <div class="vui-layout">
        <wiki-search @on-get-keyword="handleKeyord" select></wiki-search>
      </div>
    </div>
    <div class="vui-layout pd20" style="background:#fff">
      <Breadcrumb class="pb10">
        <BreadcrumbItem to="/">物种百科</BreadcrumbItem>
        <BreadcrumbItem>{{describeData.fname}}</BreadcrumbItem>
      </Breadcrumb>
      <Row>
        <Col span="18" class="pr20">
          &nbsp;
          <template v-if="catalogData.length">
            <describe :id="catalogData[0].propertyid" @on-edit="handleEdit(0)" :data="describeData"></describe>
            <catalog class="mt30 mb50" :catalogData="catalogData"></catalog>
            <img-list v-if="classType == '植物'" url="disease-detail" :id="catalogData[1].propertyid" title="常见病害" @on-change="handleSpeciesDisease" :data="speciesDisease" class="mb50" @on-edit="handleEdit(1)" :speciesName="speciesName" :classId="classId" :speciesid="speciesid"></img-list>
            <img-list v-if="classType == '动物'" url="disease-animal-detail" :id="catalogData[1].propertyid" title="常见病害" @on-change="handleSpeciesDisease" :data="speciesDisease" class="mb50" @on-edit="handleEdit(1)" :speciesName="speciesName" :classId="classId" :speciesid="speciesid"></img-list>
            <img-list url="pests-detail" :id="catalogData[2].propertyid" title="常见虫害" @on-change="handleSpeciesPest" :data="speciesPest" class="mb50" @on-edit="handleEdit(2)" :speciesName="speciesName" :classId="classId" :speciesid="speciesid"></img-list>
            <img-list url="variety-detail" :id="catalogData[3].propertyid" title="主要品种" @on-change="handleSpeciesVarietey" :data="speciesVarietey" class="mb50" @on-edit="handleEdit(3)" :speciesName="speciesName" :classId="classId" :speciesid="speciesid"></img-list>
            <custom :id="`${item.propertyid}`" :key="index" v-for="(item, index) in customCatalogDataList" :data="item" @on-edit="handleEdit(index + 4)"></custom>
            <!-- 相关知识资讯政策 -->
            <related></related>
          </template>
        </Col>
        <Col span="6">
          <recommend-list :name="speciesName" :album="true" ref="recommend"></recommend-list>
          <!-- 目录 -->
          <Affix class="affix-catalog" :offset-top="400">
            <vui-affix-tabs :data="catalogData" v-show="affixShow"></vui-affix-tabs>
          </Affix>
        </Col>
      </Row>
    </div>
    <login-register ref="loginRegister" @on-success="handleSuccess"></login-register>
    <edit ref="edit" :speciesName="speciesName" :classType="classType"></edit>
  </div>
</template>

<script>
import loginRegister from '~components/loginRegister/index'
import wikiSearch from '~components/wiki-search'
import describe from './components/describe'
import catalog from './components/catalog'
import vuiAffixTabs from '~components/vui-affix-tabs'
import imgList from './components/img-list'
import custom from './components/custom'
import recommendList from '~components/recommend-list'
import edit from './edit'
import {catalogData, loginuserinfo} from '~components/mixins'
import articles from './components/articles'
import related from './components/related'
export default {
  components: {
    wikiSearch,
    loginRegister,
    describe,
    vuiAffixTabs,
    catalog,
    imgList,
    custom,
    recommendList,
    edit,
    articles,
    related
  },
  mixins: [catalogData, loginuserinfo],
  data: () => ({
    affixShow: false,
    keyword: '',
    speciesDisease: {
      data: [],
      total: 0,
      current: 1
    },
    speciesPest: {
      data: [],
      total: 0,
      current: 1
    },
    speciesVarietey: {
      data: [],
      total: 0,
      current: 1
    },
    indexid: '',
    speciesid: '',
    classId: '',
    speciesName: '',
    classType: '植物',
    // 详情
    describeData: {},
    pageSize: 12
  }),
  created () {
    this.indexid = this.$route.query.indexid
    this.speciesid = this.$route.query.speciesid
    this.classId = this.$route.query.classId
    // 取详情数据
    this.handlegGetSpecies()
    // 常见病害
    this.handleSpeciesDisease(1)
    // 常见虫害
    this.handleSpeciesPest(1)
    // 主要品种
    this.handleSpeciesVarietey(1)
    // 查询自定义列表数据
    this.handleGetCatalogDataList()
  },
  methods: {
    // 查询自定义列表数据
    handleGetCatalogDataList () {
      this.$api.post('wiki/api/property/listSpeciesProperty', {speciesid: this.speciesid}).then(response => {
        if (response.code === 200) {
          this.customCatalogDataList = response.data
        }
      })
    },
    // 查询详情
    handlegGetSpecies () {
      this.$api.get('wiki/api/species/getSpecies/' + this.indexid).then(response => {
        if (response.code === 200) {
          this.describeData = response.data
          this.speciesName = this.describeData.fname
          let classType = response.data.fclassifiedidInfo
          if (classType.val) {
            this.classType = classType.val.split('/')[0]
          } else {
            this.classType = '植物'
          }
          if (response.data.speciesAtlas) {
            this.$refs['recommend'].albumData = response.data.speciesAtlas
          } else {
            this.$refs['recommend'].albumData = []
          }
        }
      })
    },
    // 常见病害
    handleSpeciesDisease (e) {
      this.$api.post('wiki/api/wiki/listSpeciesDisease', {speciesid: this.speciesid, pageSize: this.pageSize, pageNum: e}).then(response => {
        if (response.code === 200) {
          this.speciesDisease.current = e
          this.speciesDisease.data = response.data
          this.speciesDisease.total = response.total
        }
      })
    },
    // 常见虫害
    handleSpeciesPest (e) {
      this.$api.post('wiki/api/wiki/listSpeciesPest', {speciesid: this.speciesid, pageSize: this.pageSize, pageNum: e}).then(response => {
        if (response.code === 200) {
          this.speciesPest.current = e
          this.speciesPest.data = response.data
          this.speciesPest.total = response.total
        }
      })
    },
    // 主要品种
    handleSpeciesVarietey (e) {
      this.$api.post('wiki/api/wiki/listSpeciesVarietey', {speciesid: this.speciesid, pageSize: this.pageSize, pageNum: e}).then(response => {
        if (response.code === 200) {
          this.speciesVarietey.current = e
          this.speciesVarietey.data = response.data
          this.speciesVarietey.total = response.total
        }
      })
    },
    // 搜索
    handleKeyord (item) {
      let indexid = item.indexid
      let speciesid = item.speciesid
      let classId = item.fclassifiedid
      let path = `${this.$router.history.base}${this.$router.history.current.path}?indexid=${indexid}&speciesid=${speciesid}&classId=${classId}`
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
  },
  mounted () {
    window.addEventListener('scroll', () => {
      if ((document.documentElement.scrollTop || document.body.scrollTop) > 800) {
        this.affixShow = true
      } else {
        this.affixShow = false
      }
    })
  }
}
</script>

<style lang="scss">
.affix-catalog{
  .ivu-affix{
    margin-left: 40px;
  }
}
</style>
