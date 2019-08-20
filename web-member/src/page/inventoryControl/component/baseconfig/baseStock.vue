<template>
  <div class="pd15">
    <!-- 搜索条件 -->
    <Row>
      <Col span="7">
          <label class="mr10">产品分类</label>
          <Input v-model="searchInfo.productTypeName" readonly @on-focus="handleFilterModal('productTypeFilter')" style="width: 200px;" />
      </Col>
      <Col span="7">
          <label class="mr10">产品名称</label>
          <Input v-model="searchInfo.productName" style="width: 200px;" />
      </Col>
      <Col span="7">
        <label class="mr10">产品编码</label>
        <Select v-model="searchInfo.productCode" style="width: 200px;" clearable>
            <Option v-for="(item,index) in productCodeList" :value="item.productCode" :key="index">{{ item.productCode }}</Option>
        </Select>
      </Col>
      <Col span="3" class="tr">
        <Button type="success" @click="query" >查询</Button>
      </Col>
    </Row>
    <!-- 表格数据 -->
    <Row class="mt30">
      <Col>
        <Table border :columns="columns" :data="data"></Table>
        <div class="tc mt20 mb20">
          <Page :total="total" :current="pageNum" :page-size="pageSize" @on-change="pageChange" />
        </div>
      </Col>
    </Row>
    <!-- 设置弹出框 -->
    <Modal v-model="setupShow" title="库存预警值设置">
      <Form ref="info" :model="info" label-position="right" :label-width="100" :rules="ruleInline">
        <FormItem label="产品名称" prop="productName">
          <Input v-model="info.productName" readonly />
        </FormItem>
        <FormItem label="库存上限" prop="upperLimit">
          <Input v-model="info.upperLimit" :maxlength="30" />
        </FormItem>
        <FormItem label="库存下限" prop="lowerLimit">
          <Input v-model="info.lowerLimit" :maxlength="30" />
        </FormItem>
      </Form>
      <div slot="footer">
          <Button type="text" @click="setupShow=false">取消</Button>
          <Button type="primary" @click="setupShowStock">确定</Button>
      </div>
    </Modal>
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
import vuiFilter from '~components/vuiFilter/filter'
import { isDecimal2 } from '~utils/validate'
export default {
  components: {
    vuiFilter
  },
  data () {
    return {
      setupShow: false,
      // 商品分类
      total2: 0,
      pageCur2: 1,
      productTypeResultDatas: [],
      productCodeList: [],
      searchInfo: {
        productTypeName: '',
        productTypeId: '',
        productName: '',
        productCode: ''
      },
      columns: [
        {
          title: '产品编码',
          key: 'productCode',
          align: 'center',
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
          title: '单位',
          key: 'unit',
          align: 'center',
          width: 100
        },
        {
          title: '当前库存',
          key: 'totalStore',
          align: 'center',
          width: 100
        },
        {
          title: '库存上限',
          key: 'upperLimit',
          align: 'center',
          width: 100
        },
        {
          title: '库存下限',
          key: 'lowerLimit',
          align: 'center',
          width: 100
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 80,
          render: (h, params) => {
            return h('div', [
              h('a', {
                style: {
                  color: '#19be6b'
                },
                on: {
                  click: () => {
                    this.editInit(params.row)
                  }
                }
              }, '设置')
            ])
          }
        }
      ],
      data: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      info: {
        productName: '',
        upperLimit: '',
        lowerLimit: ''
      },
      ruleInline: {
        upperLimit: [
          { required: false, validator: isDecimal2, tigger: 'blur' }
        ],
        lowerLimit: [
          { required: false, validator: isDecimal2, tigger: 'blur' }
        ],
      }
    }
  },
  created () {
    this.initProductCode()
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
      this.$api.post('/shop/inventory/basicSetting/storeList', {
        account: this.$user.loginAccount,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        productClassify: this.searchInfo.productTypeId,
        productName: this.searchInfo.productName,
        productCode: this.searchInfo.productCode,
        commodityId: '', // 这个页面没有这几个查询条件的字段
        inStore: '',
        customId: ''
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
    // 初始化产品编码
    initProductCode () {
      this.$api.post('/shop/inventory/basicSetting/productCodeList', {
        account: this.$user.loginAccount
      }).then(response => {
        console.log('code', response)
        if (response.code === 200) {
          this.productCodeList = response.data
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    editInit (item) {
      this.setupShow = true
      this.info.id = item.id
      this.info.productName = item.productName
      this.info.lowerLimit = item.lowerLimit === '不限' ? '' : item.lowerLimit
      this.info.upperLimit = item.upperLimit === '不限' ? '' : item.upperLimit
    },
    setupShowStock () {
      // 如果上下限都填写了的话就要比较大小
      if (this.info.upperLimit !== '' && this.info.lowerLimit !== '') {
        if (parseFloat(this.info.upperLimit) <= parseFloat(this.info.lowerLimit)) {
          this.$Message.info('库存上限应该大于库存下限')
          return
        }
      }
      this.$refs['info'].validate((valid) => {
        if (valid) {
          this.$api.post('/shop/inventory/basicSetting/warningSave', this.info).then(response => {
            console.log('res', response)
            if (response.code === 200) {
              this.$Message.success('设置成功！')
              this.initStoreList()
              this.setupShow = false
            }else {
              this.$Message.error('服务器异常！')
            }
          }).catch(error => {
            this.$Message.error('服务器异常！')
          })
        } else {
            this.$Message.error('请核对表单字段！')
        }
      })
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
</style>
