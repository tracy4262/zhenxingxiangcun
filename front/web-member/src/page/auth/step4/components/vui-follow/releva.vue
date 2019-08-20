<template>
  <div>
    <Row class="mt20 mb10">
      <Col span="16">
        <tabs :data="tabs" @on-click="handleTabClick"></tabs>
      </Col>
      <Col span="8">
        <Input v-model="keywords" icon="android-search" placeholder="请输入搜索关键字" @on-click="handleSearch"></Input>
      </Col>
    </Row>
    <releva-modal
    :index="index"
    :data="relevaData"
    :defaultSel="relevaDataSel"
    :filter="filter"
    @on-get-data="handleGetRelevaData"
    @on-get-filter="handleGetRelevaFilter">
      <Row type="flex" class="mt10 mb10" v-if="index > 0 &&  speciesSel.length">
        <Col span="3" class="pd5">物种关键字：</Col>
        <Col span="21">
          <Tag v-for="(item, index) in speciesSel" :key="index">{{item.name}}</Tag>
        </Col>
      </Row>
      <Row type="flex" class="mt10 mb10" v-if="index === 2 &&  productSel.length">
        <Col span="3" class="pd5">产品关键字：</Col>
        <Col span="21">
          <Tag v-for="(item, index) in productSel" :key="index">{{item.name}}</Tag>
        </Col>
      </Row>
      <Row type="flex" class="mt10 mb10">
        <Col span="3" class="pd5">
          <template v-if="index === 0 &&  speciesSel.length">物种关键字：</template>
          <template v-if="index === 1 &&  productSel.length">产品关键字：</template>
          <template v-if="serviceSel.length">服务关键字：</template>
        </Col>
        <Col span="21">
          <Tag closable v-for="(item, index) in relevaDataSel" :key="index" @on-close="handleDel(item)">{{item.name}}</Tag>
        </Col>
      </Row>
    </releva-modal>
    <div class="tc">
      <Page :total="total" :page-size="40" v-if="relevaData.length" size="small" @on-change="handlePageChange"></Page>
    </div>
  </div>
</template>
<script>
import tabs from './tabs'
import relevaModal from './releva-modal'
import {Reset, Mixins} from './mixins.js'
export default {
  components: {
    tabs,
    relevaModal
  },
  mixins: [Mixins],
  data: () => ({
    loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
    tabs: [{
      name: '物种关键词',
      checked: true
    }, {
      name: '产品关键词',
      checked: false
    }, {
      name: '服务关键词',
      checked: false
    }],
    keywords: '',
    filter: [],
    filterId: [],
    relevaData: [],
    relevaDataSel: [],
    index: 0,
    species: [],
    speciesSel: [],
    speciesFilter: [],
    speciesFilterId: [],
    product: [],
    productFilter: [],
    productFilterId: [],
    productSel: [],
    service: [],
    serviceFilter: [],
    serviceFilterId: [],
    serviceSel: [],
    total: 0,
    pageNum: 1,
  }),
  created () {
    // 取动物植物分类
    this.$api.post('/member-reversion/indivi/findIndividInfo', {
      account: this.loginuserinfo.loginAccount
    }).then(res => {
      let d = res.data.SpeciesclassData
      this.filter = d
      this.speciesFilter = d
    })
    this.$api.post('/member-reversion/indivi/findIndividInfo', {
      account: this.loginuserinfo.loginAccount
    }).then(res => {
      this.productFilter = res.data.SpeciesclassData
    })
    this.$api.post('/member-reversion/indivi/findIndividInfo', {
      account: this.loginuserinfo.loginAccount
    }).then(res => {
      this.serviceFilter = res.data.SpeciesclassData
    })
    this.loadData ()
  },
  methods: {
    // 取物种
    loadData (id = this.filterId, keywords = this.keywords, pageNum = this.pageNum, pageSize = 40) {
      this.$api.post('/wiki/api/species/getSpeciesListInfo', {
        fclassifiedid : id,
        keywords : keywords,
        pageNum: pageNum,
        pageSize: pageSize
      }).then(res => {
        // 选中数据
        var d = res.data
        if (d.speciesListData) {
          d.speciesListData.forEach(item => {
            this.relevaDataSel.forEach(child => {
              if (item.name === child.name) {
                item.checked = true
              }
            })
          })
          this.species = d.speciesListData
          this.relevaData = d.speciesListData
          this.total = d.totalNum
        } else {
          this.species = []
          this.relevaData = []
        }
      })
    },
    // 分页
    handlePageChange (size) {
      this.pageNum = size
      // 第一个标签
      if (this.index === 0) {
        this.loadData ()
      }
    },
    // 选中标签
    handleTabClick (index) {
      this.index = index
      if (index === 0) {
        this.relevaData = this.species
        this.relevaDataSel = this.speciesSel
        this.filter = this.speciesFilter
      } else if (index === 1) {
        this.relevaData = this.product
        this.relevaDataSel = this.productSel
        this.filter = this.productFilter
      } else {
        this.relevaData = this.service
        this.relevaDataSel = this.serviceSel
        this.filter = this.serviceFilter
      }
    },
    // 搜索按钮
    handleSearch () {
      // 第一个标签
      if (this.index === 0) {
        this.loadData ()
      }
    },
    // 删除
    handleDel (data) {
      this.relevaData.forEach(item => {
        if (item.name === data.name) {
          item.checked = false
        }
      })
      this.relevaDataSel.forEach((item, index) => {
        if (item.name === data.name) {
          this.relevaDataSel.splice(index, 1)
        }
      })
      this.$emit('on-get-data', this.speciesSel, this.productSel, this.serviceSel)
    },
    // 取选中结果
    handleGetRelevaData (data) {
      if (this.index === 0) {
        this.speciesSel = data
      } else if (this.index === 1) {
        this.productSel = data
      } else {
        this.serviceSel = data
      }
      this.$emit('on-get-data', this.speciesSel, this.productSel, this.serviceSel)
    },
    handleGetRelevaFilter (data) {
      this.filterId = data
      if (this.index === 0) {
        this.loadData ()
      }
    },
    // 重置组件
    handleReset () {
      this.index = 0
      this.relevaData = this.species
      this.relevaDataSel = []
      this.speciesSel = []
      this.productSel = []
      this.serviceSel = []
      Reset(this.species)
      Reset(this.speciesFilter)
      Reset(this.product)
      Reset(this.productFilter)
      Reset(this.service)
      Reset(this.serviceFilter)
      this.tabs.forEach((item, index) => {
        item.checked = false
        if (index === 0) {
          item.checked = true
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
