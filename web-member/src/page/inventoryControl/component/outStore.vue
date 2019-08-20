<template>
  <div>
    <div>
      <div v-if="isOrder">
        <p class="pd20" style="font-size: 16px">订单信息</p>
        <div class="pl20 pr20 pb20">
            <Row type="flex" justify="center" align="middle">
                <Col span="10"> <div class="pd10">商品信息</div> </Col>
                <Col span="4"> <div class="pd10 tc">单价</div></Col>
                <Col span="5"> <div class="pd10 tc">数量</div>  </Col>
                <Col span="5"> <div class="pd10 tc">小计</div>  </Col>
            </Row>
            <div style="border:1px solid #f1f1f1;border-top:none;background:#FCFDFE;">
                <Row type="flex" align="middle" v-for="(item, index) in orderData.shopProducts" :key="index">
                    <Col span="10">
                        <Row type="flex" align="middle">
                            <Col span="7" class="tc pd10">
                                <img :src="item.productPic" alt="" width="80px" height="80px">
                            </Col>
                            <Col span="17">
                                <div class="pd10">{{item.productName}}</div> 
                            </Col>
                        </Row>
                    </Col>
                    <Col span="4" class="tc"> <div class="pd10">￥{{item.amount}}</div></Col>
                    <Col span="5" class="tc"> <div class="pd10">{{item.number}}件</div>  </Col>
                    <Col span="5" class="tc"> 
                        <div class="pd10" v-if="buy == '1'">
                            <p class="t-orange pb5"><b>总价￥{{item.subTotal}}</b></p>
                            <p class="pb5">(含运费：￥{{item.logisticAmount}})</p>
                            <p class="t-orange pb5">定金￥{{item.pennyTotal}}</p>
                            <p class="t-orange pb5">尾款￥{{item.restTotal}}</p>
                        </div>  
                        <div class="pd10" v-else-if="buy == '4'">
                            <p class="t-orange pb5"><b>总价￥{{item.subTotal}}</b></p>
                            <p class="pb5">(含运费：￥{{item.logisticAmount}})</p>
                            <p class="t-orange pb5" >保证金￥{{item.margin}}</p>
                            <p class="t-orange pb5">尾款￥{{item.restTotal}}</p>
                        </div>  
                        <div class="pd10" v-else>
                            <p class="t-orange pb5">￥{{item.subTotal}}</p>
                            <p>(含运费：￥{{item.logisticAmount}})</p>
                        </div>  
                    </Col>
                </Row>
            </div>
        </div>
      </div>
      <div class="store_info">选择出库产品</div>
      <!-- 查询条件 -->
      <Row class="">
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
        <Table border ref="selection" :columns="outStoreColumns" :data="outStoreData" @on-selection-change="hasSelected"></Table>
        <Page :total="total" :current="pageNum" :page-size="pageSize" @on-change="pageChange" class="tr mt20" />
      </div>
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
      <div class="store_info">填写出库信息</div>
      <Form ref="info" :model="info" label-position="right" :label-width="100" :rules="ruleInline">
        <Row>
          <Col span="8">
            <FormItem label="出库类型" prop="outStoreType">
              <Select v-model="info.outStoreType">
                <Option v-for="item in outTypeList" :value="item.id" :key="item.id">{{ item.type }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="经手人" prop="operatorAccount">
              <Input v-model="info.operatorAccount" readonly />
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="出库日期" prop="createTime">
              <Input v-model="info.createTime" readonly />
            </FormItem>
          </Col>
        </Row>
      </Form>
    </div>
    <div class="tc mt20">
        <Button type="default" @click="$emit('close')">取消</Button>
        <Button type="primary"  class="ml10" @click="saveStore">确定出库</Button>
    </div>
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
import {numMulti, numAdd, Subtr} from '~utils/utils'
export default {
  components: {
    vuiFilter
  },
  data () {
    return {
      number: '',
      outStoreColumns: [
        {
          type: 'selection',
          width: 60,
          align: 'center',
          fixed: 'left'
        },
        {
          title: '出库数量',
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
                    this.outStoreData[params.index].number = value
                    if (this.outStoreList.length !== 0) {
                      this.totalMoney = 0
                      this.outStoreList.forEach((element,index) => {
                        if (element.id === params.row.id) {
                          element.number = value
                        }
                        this.totalMoney += element.number * element.price
                      })
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
          fixed: 'left'
        },
        {
          title: '产品名称',
          key: 'productName',
          align: 'center',
          width: 100,
          fixed: 'left'
        },
        {
          title: '通用商品名称',
          key: 'commodityName',
          align: 'center',
          width: 120,
          fixed: 'left'
        },
        {
          title: '产品分类',
          key: 'classifyName',
          align: 'center',
          width: 100,
          fixed: 'left'
        },
        {
          title: '当前库存',
          key: 'totalStore',
          align: 'center',
          width: 100,
          fixed: 'left'
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
        }
      ],
      outStoreData: [],
      // 商品分类
      total2: 0,
      pageCur2: 1,
      productTypeResultDatas: [],
      inStoreList: [],
      outTypeList: [],
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
        outStoreType: '',
        operatorAccount: '',
        createTime: ''
      },
      ruleInline: {
        outStoreType: [
          { required: true, type: 'number', message: '请选择出库类型', trigger: 'change' }
        ],
        operatorAccount: [
          { required: true, type: 'string', message: '请填写经手人', trigger: 'change' }
        ],
        createTime: [
          { required: true, type: 'string', message: '请填写出库日期', trigger: 'change' }
        ]
      },
      isOrder : false,
      orderData: {
        shopProducts: []
      },
      buy: ''
    }
  },
  created () {
    this.initStore()
    this.initOutTypeList()
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
    showOrder (order, buy) {
      this.isOrder = true
      this.buy = buy
      this.$api.post('/shop/shopOrder/detail/code', {orderCode : order}).then(response => {
          if (response.code === 200) {
              this.orderData = response.data
              let margin = this.orderData.margin
              this.orderData.shopProducts.forEach(element => {
                  // <!-- 0 定价，1预售，2面议，3团购，4竞拍 -->
                  if(this.buy == '1') { // 说明是 预售订单 '1' 
                      // pennyTotal 定金金额
                      element.pennyTotal = parseFloat((numMulti(element.amount, element.number)).toFixed(2))
                      let total = parseFloat((numMulti(element.orderPrice, element.number)).toFixed(2))
                      let restTotal = parseFloat((numAdd(total, element.logisticAmount)).toFixed(2))
                      // restTotal尾款
                      element.restTotal = Subtr(restTotal, element.pennyTotal)
                      element.amount = element.orderPrice
                      // 总金额
                      element.total = parseFloat((numMulti(element.orderPrice, element.number)).toFixed(2))
                      element.subTotal = parseFloat((numAdd(element.total, element.logisticAmount)).toFixed(2))
                  } else if (this.buy == '4') {
                      element.total = parseFloat((numMulti(element.amount, element.number)).toFixed(2))
                      element.subTotal = parseFloat((numAdd(element.total, element.logisticAmount)).toFixed(2))
                      // margin 保证金 restTotal 剩余需要支付的金额
                      element.margin = margin
                      element.restTotal = Subtr(element.subTotal, element.margin)

                  } else { // 其他订单查看详情 '0'
                      element.total = parseFloat((numMulti(element.amount, element.number)).toFixed(2))
                      element.subTotal = parseFloat((numAdd(element.total, element.logisticAmount)).toFixed(2))
                  }
              })
          }
      })
    },
    // 重置当前页面
    outStoreInit () {
      // 查询条件
      this.searchInfo = {
        productTypeName: '',
        productTypeId: '',
        productName: '',
        inStore: '',
        customId: ''
      }
      // 表格数据及下拉框
      this.initStore()
      this.initOutTypeList()
      this.initClassify()
      this.initStoreList()
      this.outStoreList = []
      this.totalMoney = 0
      // 出库信息表单
      this.$refs['info'].resetFields()
      this.info.outStoreType = ''
      this.info.createTime = this.moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
      // 获取经手人
      this.$api.post('/member/login/findCurrentUser', {
        account: this.$user.loginAccount
      }).then(response => {
        if (response.data.displayName) {
          this.info.operatorAccount = response.data.displayName
        }
      })
    },
    initOutTypeList () {
      this.$api.post('/shop/inventory/basicSetting/outStoreList', {
        account: this.$user.loginAccount,
        pageSize: 1000,
        pageNum: 1
      }).then(response => {
        console.log('res', response)
        if (response.code === 200) {
          this.outTypeList = response.data
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    initStoreList () {
      this.$api.post('/shop/inventory/basicSetting/storeList', {
        account: this.$user.loginAccount,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        productClassify: this.searchInfo.productTypeId,
        productName: this.searchInfo.productName,
        inStore: this.searchInfo.inStore,
        customId: this.searchInfo.customId,
        productCode: '', // 这个页面没有这几个查询条件的字段
        commodityId: ''
      }).then(response => {
        console.log('list', response)
        if (response.code === 200) {
          this.outStoreData = response.data.list
          this.outStoreData.forEach(element => {
            element.number = 0
          })
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
    saveStore () {
      // 出库数量为0的判断
      if (this.outStoreList.length === 0) {
        this.$Message.info('请选择要出库的产品！')
        return
      } else {
        for (let i = 0; i < this.outStoreList.length; i++) {
          if (this.outStoreList[i].number === 0) {
            this.$Message.info('出库数量不能为0！')
            return
          }
        }
      }
      this.$refs['info'].validate((valid) => {
        if (valid) {
          this.$api.post('/shop/inventory/basicSetting/exitStoreSave', {
            account: this.$user.loginAccount,
            operatorAccount: this.info.operatorAccount,
            createTime: this.info.createTime,
            outStoreType: this.info.outStoreType,
            list: this.outStoreList
          }).then(response => {
            console.log('res', response)
            if (response.code === 200) {
              this.$Message.success('出库成功！')
              // this.initStoreList()
              // response.data 返回出库单号
              this.$emit('close', '2', response.data)
            } else {
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
    // 已选择出库商品
    hasSelected (e) {
      console.log(e, 'e')
      this.outStoreList = e
      this.totalMoney = 0
      this.outStoreList.forEach(element => {
        this.totalMoney += element.number * element.price
      })
      this.totalMoney = parseFloat(this.totalMoney.toFixed(2)) // 保留两位小数
      /* this.outStoreList.forEach(element => {
        e.forEach(item => {
          if (element.id === item.id) {

          }
        })
      }) */
      /* or (let i = 0; i < this.outStoreList.concat().length; i ++) {
        for (let j = 0; j < e.concat().length; j ++) {
          if (this.outStoreList.concat()[i].id === e.concat()[j].id) {
            e.concat().splice(j, 1)
          }
        }
      }
      let result = this.outStoreList.concat().concat(e)
      this.outStoreList = result
      console.log('result', result) */
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
