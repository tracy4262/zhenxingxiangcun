<template>
  <div class="baseConfig">
    <Row style="padding: 40px 32px 0px">
      <Col span="7">
        <div>
          <label class="mr10">产品分类</label>
          <Input v-model="searchInfo.productTypeName" readonly @on-focus="handleFilterModal('productTypeFilter')" style="width: 180px;" />
        </div>
      </Col>
      <Col span="7">
        <label class="mr10">产品名称</label>
        <Input v-model="searchInfo.productName" style="width: 180px;" />
      </Col>
      <Col span="8">
        <label class="mr10">通用商品名称</label>
        <Input v-model="searchInfo.commodityName" readonly @on-focus="commodityName" style="width: 180px;" />
      </Col>
      <Col span="2" class="tr">
        <Button type="success" class="ml20" @click="query">查询</Button>
      </Col>
    </Row>
    <Divider />
    <div class="base_tab">
      <Table border :columns="columns" :data="data" ></Table>
    </div>
    <div class="base_page">
      <Page :total="total" :current="pageNum" :page-size="pageSize" @on-change="pageChange" />
    </div>
    <!-- 通用商品名控件 -->
    <vuiCommodity ref="vuiCommodity" @on-save="onSaveFocus" :input="false" :num="1"></vuiCommodity>
    <!-- 商品分类控件 -->
    <vui-filter
      ref="productTypeFilter"
      :num="1"
      :simple="true"
      :pageShow="true"
      :cols="2"
      :total="total2"
      :pageCur="pageCur2"
      :transfer="true"
      :resultDatas="productTypeResultDatas"
      @on-search="handleProductTypeSearch"
      @on-page-change="handleProductTypePageChange"
      @on-get-result="handleGetProductTypeResult" />
  </div>
</template>

<script>
import vuiCommodity from '~components/vui-commodity'
import vuiFilter from '~components/vuiFilter/filter'
export default {
  components: {
    vuiCommodity,
    vuiFilter
  },
  data () {
    return {
      columns: [
        {
          title: '产品编码',
          key: 'productCode',
          align: 'center',
          width: 140
        },
        {
          title: '产品名称',
          key: 'productName',
          align: 'center',
        },
        {
          title: '通用商品名称',
          key: 'commodityName',
          align: 'center',
        },
        {
          title: '产品分类',
          key: 'classifyName',
          align: 'center',
        },
        {
          title: '期初库存',
          key: 'initialStore',
          align: 'center',
          width: 100
        },
        {
          title: '库存数',
          key: 'totalStore',
          align: 'center',
          width: 100
        },
        {
          title: '计量单位',
          key: 'unit',
          align: 'center',
          width: 100
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 120,
          render: (h, params) => {
            return h('div', [
              h('a', {
                style: {
                  color: '#19be6b'
                },
                on: {
                  click: () => {
                    this.$router.push({name: 'storeInfoSee',query: {code: params.row.productCode}})
                  }
                }
              }, '查看库存详情')
            ])
          }
        }
      ],
      data: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      searchInfo: {
        productTypeName: '',
        productTypeId: '',
        productName: '',
        commodityName: '',
        commodityId: ''
      },
      // 商品分类
      total2: 0,
      pageCur2: 1,
      productTypeResultDatas: []
    }
  },
  created () {
    this.initStoreList()
    // 商品分类数据初始化
    this.$api.post('/portal/shopCommdoity/findProductClassification', {
        pinyin: '',
        name: '',
        num: 1,
        size: 32
    }).then(res => {
        this.total2 = res.data.total
        res.data.list.forEach(element => {
            this.productTypeResultDatas.push({
                label: element.className,
                value: element.productCode
            })
        })
    })
  },
  methods: {
    initStoreList () {
      this.$api.post('/shop/inventory/basicSetting/storeCombineList', {
        account: this.$user.loginAccount,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        productClassify: this.searchInfo.productTypeId,
        productName: this.searchInfo.productName,
        productCode: '', // 这个页面没有这几个查询条件的字段
        inStore: '',
        customId: '',
        commodityId: this.searchInfo.commodityId
      }).then(response => {
        console.log('list', response)
        if (response.code === 200) {
          this.data = response.data.list
          this.total = response.data.total
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    pageChange (page) {
      this.pageNum = page
      this.initStoreList()
    },
    query () {
      this.pageNum = 1
      this.initStoreList()
    },
    onSaveFocus (e) {
      if (e.length > 0) {
        this.searchInfo.commodityId = e[0].commodityId
        this.searchInfo.commodityName = e[0].commodityName
      } else {
        this.searchInfo.commodityId = ''
        this.searchInfo.commodityName = ''
      }
    },
    // 通用商品名
    commodityName () {
      this.$refs['vuiCommodity'].handleFilterModal()
    },
    // 高级搜索弹窗
    handleFilterModal (name) {
      this.$refs[name].highFilterShow = true
    },
    // 商品分类
    handleProductTypeSearch (letter, keyword, result) {
      this.$api.post(`/portal/shopCommdoity/findProductClassification`, {
          name : keyword,
          pinyin: letter === '全部' ? '' : letter,
          num: 1,
          size: 32
      }).then(res => {
          console.log('123', res)
          var data = []
          if (res.data.list) {
              res.data.list.forEach(element => {
                  data.push({
                      label: element.className,
                      value: element.productCode
                  })
              })
          }
          this.total2 = res.data.total
          this.productTypeResultDatas = []
          // 选中回显
          if(result) {
              result.forEach(item => {
                  data.forEach((child, index) => {
                      if(child.label === item.label) {
                          child.checked = true
                      }
                  })
              })
          } else {
              data.forEach(child => {
                  child.checked = false
              })
          }
          this.productTypeResultDatas = data
        })
    },
    handleProductTypePageChange (letter, keyword, classify, num, result) {
        this.pageCur2 = num
        this.$api.post('/portal/shopCommdoity/findProductClassification', {
            name : keyword,
            pinyin: letter === '全部' ? '' : letter,
            num: num,
            size: 32
        }).then(res => {
            var data = []
            res.data.list.forEach(element => {
                data.push({
                    label: element.className,
                    value: element.productCode
                })
            })
            this.total2 = res.data.total
            this.productTypeResultDatas = []
            // 选中回显
            if(result) {
                result.forEach(item => {
                    data.forEach((child, index) => {
                        if(child.label === item.label) {
                            child.checked = true
                        }
                    })
                })
            } else {
                data.forEach(child => {
                    child.checked = false
                })
            }
            this.productTypeResultDatas = data
        })
        this.$Message.success('筛选完成！')
    },
    handleGetProductTypeResult (classify, result) {
      let arr = []
      let arrId = []
      result.forEach(item => arr.push(item.label))
      result.forEach(item => arrId.push(item.value))
      this.searchInfo.productTypeName = arr.join(' ')
      this.searchInfo.productTypeId = arrId.join(' ')
    }
  }
}
</script>

<style lang="scss" scoped>
.baseConfig{
  padding: 0 8px;
  width: 1000px;
  min-height: 800px;
  margin: 0 auto;
  background-color: #fff;
  .base_tab{
    padding: 10px 33px;
    margin-bottom: 26px;
  }
  .base_page{
    text-align: center;
  }
}
</style>
