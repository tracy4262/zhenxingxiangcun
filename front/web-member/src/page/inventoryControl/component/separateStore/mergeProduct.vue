<!--
    作者：chenqim
    时间：2019-01-22
    描述：拆装库存-合并产品
-->
<template>
  <div>
    <div>
      <div class="store_info">选择合并的产品<span style="color: #ff0000;">（注意：只有同一产品名称、同一计量单位且在同一仓库中的产品，才允许合并）</span></div>
      <!-- 查询条件 -->
      <Row>
        <Col span="5">
          <span class="mark_name">产品分类</span>
          <Input v-model="searchInfo.productTypeName" readonly @on-focus="handleFilterModal('productTypeFilter')" style="width: 140px;" />
        </Col>
        <Col span="5">
          <span class="mark_name">产品名称</span>
          <Input v-model="searchInfo.productName" :maxlength="30" style="width:140px"/>
        </Col>
        <Col span="5">
          <span class="mark_name">所在仓库</span>
          <Select v-model="searchInfo.inStore" clearable style="width:140px">
              <Option v-for="(item,index) in inStoreList" :value="item.id" :key="item.id">{{ item.storeName }}</Option>
          </Select>
        </Col>
        <Col span="5">
          <span class="mark_name">自定义分类</span>
          <Select v-model="searchInfo.customId" clearable style="width:140px" >
            <Option v-for="item in categories" :value="item.id" :key="item.id">{{ item.customName }}</Option>
          </Select>
        </Col>
        <Col span="4" class="tr">
          <Button type="success" @click="query" class="mr20">查询</Button>
        </Col>
      </Row>
      <!-- 数据表格 -->
      <div style="padding:20px">
        <Table border ref="selection" :columns="columns" :data="dataList" @on-selection-change="hasSelected"></Table>
        <Page :total="total" :current="pageNum" :page-size="pageSize" @on-change="pageChange" class="tr mt20" />
      </div>
      <!-- 选择的数据 -->
      <div class="choose">
        <p class="choose_text">已选择：</p>
        <ul>
          <li v-for="(item, index) in outStoreList" :key="index">
            <span class="choose_name">{{ item.productName }}</span>
            <span class="choose_count">{{ item.number }}{{ item.unit }}</span>
          </li>
        </ul>
        <div class="mt30 pl30">合计：{{ totalMoney }}</div>
      </div>
      <Divider />
      <div class="store_info">填写合并后信息</div>
      <Row class="mb40 pl20">
        <Form ref="info" :model="info" label-position="right" :label-width="100" :rules="ruleInline">
          <Row :gutter="32" class="pl20 pr20">
            <Col span="12">
              <FormItem label="产品名称" prop="productName">
                <Input v-model="info.productName" readonly />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="所在仓库" prop="storeName">
                <Input v-model="info.storeName" readonly />
              </FormItem>
            </Col>
          </Row>
          <Row :gutter="32" class="pl20 pr20">
            <Col span="12">
              <FormItem label="通用商品名称" prop="commodityName">
                <Input v-model="info.commodityName" readonly />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="自定义分类" prop="customName">
                <Input v-model="info.customName" readonly />
              </FormItem>
            </Col>
          </Row>
          <Row :gutter="32" class="pl20 pr20">
            <Col span="12">
              <FormItem label="产品分类" prop="productClassifyName">
                <Input v-model="info.productClassifyName" readonly />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="计量单位" prop="unit">
                <Input v-model="info.unit" readonly />
              </FormItem>
            </Col>
          </Row>
          <Row :gutter="32" class="pl20 pr20">
            <Col span="12">
              <FormItem label="总量" prop="number">
                <Input v-model="info.number" readonly />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="单价" prop="price">
                <InputNumber :max="100000000" :min="0" :step="1" :precision="2" :active-change="false" v-model="info.price" style="width: 95%;" @on-change="change"></InputNumber>&nbsp;&nbsp;元
              </FormItem>
            </Col>
          </Row>
          <Row :gutter="32" class="pl20 pr20">
            <Col span="12">
              <FormItem label="合计" prop="totalPrice">
                <Input v-model="info.totalPrice" readonly />
              </FormItem>
            </Col>
          </Row>
        </Form>
      </Row>
      <!-- 按钮 -->
      <div class="tc">
        <Button type="default" @click="$emit('close')">取消</Button>
        <Button type="primary" class="ml10" @click="saveStore(0)">确定合并并关闭</Button>
        <!-- <Button type="success" class="ml10" @click="saveStore(1)">确定拆分并继续</Button> -->
      </div>
    </div>
    <!-- 详情弹框 -->
    <Modal v-model="modal" width="1200" :mask-closable="false" :styles="{top: '60px'}" title="合并详情">
      <Table border ref="detailSelection" :columns="detailColumns" :data="detailDataList"></Table>
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
export default {
  components: {
    vuiFilter
  },
  data () {
    return {
      columns: [
        {
          type: 'selection',
          width: 60,
          align: 'center',
          fixed: 'left'
        },
        {
          title: '合并数量',
          key: 'number',
          align: 'center',
          width: 120,
          fixed: 'left',
          render: (h, params) => {
            return h('div', [
              h('InputNumber', {
                props: {
                  max: parseFloat(params.row.totalStore),
                  min: 0,
                  step: 1,
                  precision: 2,
                  activeChange: false,
                  value: params.row.number
                },
                on: {
                  'on-change': (value) => {
                    console.log('value', value, 'params', params)
                    this.dataList[params.index].number = value
                    if (this.outStoreList.length !== 0) {
                      this.totalMoney = 0
                      this.number = 0
                      this.outStoreList.forEach((element,index) => {
                        if (element.id === params.row.id) {
                          element.number = value
                        }
                        this.totalMoney += element.number * element.price
                        this.number += element.number
                      })
                      this.info.number = this.number
                      this.totalMoney = parseFloat(this.totalMoney.toFixed(2)) // 保留两位小数
                    }
                  }
                }
              })
            ])
          }
        },
        {
          title: '产品编码',
          key: 'productCode',
          align: 'center',
          width: 150,
        },
        {
          title: '产品名称',
          key: 'productName',
          align: 'center',
          width: 120,
        },
        {
          title: '通用商品名称',
          key: 'commodityName',
          align: 'center',
          width: 120,
        },
        {
          title: '产品分类',
          key: 'classifyName',
          align: 'center',
          width: 100,
        },
        {
          title: '当前库存',
          key: 'totalStore',
          align: 'center',
          width: 100,
        },
        {
          title: '计量单位',
          key: 'unit',
          align: 'center',
          width: 100,
        },
        {
          title: '所在仓库',
          key: 'storeName',
          align: 'center',
          width: 100,
        },
        {
          title: '单价(元)',
          key: 'price',
          align: 'center',
          width: 100,
        },
        {
          title: '合计(元)',
          key: 'totalPrice',
          align: 'center',
          width: 100,
        },
        {
          title: '自定义分类',
          key: 'customName',
          align: 'center',
          width: 150,
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          width: 120,
          fixed: 'right',
          render: (h, params) => {
            if (params.row.children.length > 0) {
              return h('div', [
                h('a', {
                  style: {
                    color: '#19be6b'
                  },
                  on: {
                    click: () => {
                      this.modal = true
                      this.detailDataList = params.row.children
                      console.log('det', params.row)
                    }
                  }
                }, '查看合并详情')
              ])
            } else {
              return h('div', '——')
            }
          }
        }
      ],
      dataList: [],
      detailColumns: [
        {
          title: '产品编码',
          key: 'productCode',
          align: 'center',
          width: 140
        },
        {
          title: '产品名称',
          key: 'productName',
          align: 'center'
        },
        {
          title: '通用商品名称',
          key: 'commodityName',
          align: 'center',
          width: 120
        },
        {
          title: '产品分类',
          key: 'classifyName',
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
          title: '计量单位',
          key: 'unit',
          align: 'center',
          width: 100
        },
        {
          title: '所在仓库',
          key: 'storeName',
          align: 'center',
          width: 100
        },
        {
          title: '单价(元)',
          key: 'price',
          align: 'center',
          width: 100
        },
        {
          title: '合计(元)',
          key: 'totalPrice',
          align: 'center',
          width: 100
        },
        {
          title: '自定义分类',
          key: 'customName',
          align: 'center',
          width: 110
        },
        {
          title: '是否合并项',
          key: 'isMerge',
          align: 'center'
        }
      ],
      detailDataList: [],
      // 商品分类
      total2: 0,
      pageCur2: 1,
      productTypeResultDatas: [],
      inStoreList: [],
      categories: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      searchInfo: {
        productTypeName: '',
        productTypeId: '',
        productName: '',
        inStore: '',
        customId: ''
      },
      outStoreList: [],
      totalMoney: 0,
      info: {
        productName: '',
        storeName: '',
        commodityName: '',
        customName: '',
        productClassifyName: '',
        unit: '',
        number: '',
        price: null,
        totalPrice: ''
      },
      ruleInline: {
        productName: [
          { required: true, type: 'string', message: '请填产品名称', trigger: 'change' }
        ],
        storeName: [
          { required: true, type: 'string', message: '请填写所在仓库', trigger: 'change' }
        ],
        commodityName: [
          { required: true, type: 'string', message: '请填写通用商品名称', trigger: 'change' }
        ],
        customName: [
          { required: true, type: 'string', message: '请填写自定义分类', trigger: 'change' }
        ],
        productClassifyName: [
          { required: true, type: 'string', message: '请填写产品分类', trigger: 'change' }
        ],
        unit: [
          { required: true, type: 'string', message: '请填写计量单位', trigger: 'change' }
        ],
        number: [
          { required: true, type: 'number', message: '请填写总量', trigger: 'change' }
        ],
        price: [
          { required: true, type: 'number', message: '请填写单价', trigger: 'change' }
        ],
        totalPrice: [
          { required: true, type: 'number', message: '请填写合计', trigger: 'change' }
        ]
      },
      number: 0,
      operatorAccount: '',
      modal: false
    }
  },
  created () {
    this.initStore()
    this.initClassify()
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
    separateStoreInit () {
      this.searchInfo = {
        productTypeName: '',
        productTypeId: '',
        productName: '',
        inStore: '',
        customId: ''
      }
      this.info = {
        productName: '',
        storeName: '',
        commodityName: '',
        customName: '',
        productClassifyName: '',
        unit: '',
        number: '',
        price: null,
        totalPrice: ''
      }
      this.$refs['info'].resetFields()
      // 表格数据及下拉框
      this.initStore()
      this.initClassify()
      this.initStoreList()
      this.outStoreList = []
      this.totalMoney = 0
      // 获取经手人
      this.$api.post('/member/login/findCurrentUser', {
        account: this.$user.loginAccount
      }).then(response => {
        if (response.data.displayName) {
          this.operatorAccount = response.data.displayName
        }
      })
    },
    // 初始化列表
    initStoreList () {
      this.$api.post('/shop/inventory/basicSetting/mergeList', {
        account: this.$user.loginAccount,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        productClassify: this.searchInfo.productTypeId,
        productName: this.searchInfo.productName,
        inStore: this.searchInfo.inStore,
        customId: this.searchInfo.customId
      }).then(response => {
        console.log('list', response)
        if (response.code === 200) {
          this.dataList = response.data.list
          this.dataList.forEach(element => {
            element.number = 0
            element.checked = false
          })
          this.total = response.data.total
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    // 初始化入库仓库
    initStore () {
      this.$api.post('/shop/inventory/basicSetting/storeFind', {
        account: this.$user.loginAccount,
        pageSize: 1000,
        pageNum: 1,
        key: '',
        status: 1
      }).then(response => {
        if (response.code === 200) {
          this.inStoreList = response.data.list
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    // 初始化自定义分类
    initClassify () {
      this.$api.post('/shop/inventory/basicSetting/customFind', {
        account: this.$user.loginAccount
      }).then(response => {
        console.log('res', response)
        if (response.code === 200) {
          let arr = response.data
          this.categories = arr.slice(1)
          console.log('cate', this.categories)
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    hasSelected (e) {
      console.log(e, 'e')
      this.outStoreList = e
      this.totalMoney = 0
      this.number = 0
      this.outStoreList.forEach(element => {
        this.totalMoney += element.number * element.price
        this.number += element.number
      })
      this.totalMoney = parseFloat(this.totalMoney.toFixed(2)) // 保留两位小数
      if (this.outStoreList.length !== 0) {
        this.info = {
          productName: this.outStoreList[0].productName,
          storeName: this.outStoreList[0].storeName,
          commodityName: this.outStoreList[0].commodityName,
          customName: this.outStoreList[0].customName,
          productClassifyName: this.outStoreList[0].classifyName,
          unit: this.outStoreList[0].unit,
          number: this.number,
          price: null,
          totalPrice: ''
        }
      } else {
        this.info = {
          productName: '',
          storeName: '',
          commodityName: '',
          customName: '',
          productClassifyName: '',
          unit: '',
          number: '',
          price: null,
          totalPrice: ''
        }
      }
    },
    query () {
      this.pageNum = 1
      this.initStoreList()
    },
    pageChange (page) {
      this.pageNum = page
      this.initStoreList()
    },
    change (event) {
      this.info.totalPrice = event * this.info.number
    },
    saveStore (type) {
      if (this.outStoreList.length === 0) {
        this.$Message.info('请先选择要合并的产品！')
        return
      }
      let code = this.outStoreList[0].productCode
      let unit = this.outStoreList[0].unit
      let inStore = this.outStoreList[0].inStore
      for (let index = 0; index < this.outStoreList.length; index++) {
        if (this.outStoreList[index].number === 0) {
          this.$Message.info('合并的产品数量不能为0！')
          return
        }
        if (this.outStoreList[index].productCode !== code || this.outStoreList[index].unit !== unit || this.outStoreList[index].inStore !== inStore) {
          this.$Message.info('只有同一产品名称、同一计量单位且在同一仓库中的产品，才允许合并！')
          return
        }
      }
      this.$refs['info'].validate((valid) => {
        if (valid) {
          // 请求接口
          this.$api.post('/shop/inventory/basicSetting/storeMerge', {
            account: this.$user.loginAccount,
            operatorAccount: this.operatorAccount,
            number: this.info.number,
            price: this.info.price,
            list: this.outStoreList
          }).then(response => {
            console.log('save', response)
            if (response.code === 200) {
              this.$Message.success('合并成功！')
              if (type === 0) {
                this.$emit('close')
              } else if (type === 1) {
                // 先关闭再打开
                this.$emit('close')
              }
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

<style lang="scss">
.store_info{
  color: #4A4A4A;
  font-size: 14px;
  padding-left: 10px;
  border-left: 6px solid #56B07D;
  margin: 20px 0;
}
.mark_name{
  display: inline-block;
  width: 90px;
  text-align: right;
  margin-right: 10px;
  margin-bottom: 26px;
}
.choose{
  position: relative;
  margin-top: 20px;
  font-size: 14px;
  .choose_text{
    position: absolute;
    left: 20px;
  }
  ul {
    padding-left: 70px;
    display: flex;
    flex-wrap: wrap;
    li{
      margin-right: 20px;
      padding: 10px;
      .choose_name{
        padding: 10px;
        background-color: #e8e8e8;
        margin-right: 6px;
      }
    }
  }
}
</style>
