<!--
    作者：chenqim
    时间：2019-01-18
    描述：拆装库存-拆分产品
-->
<template>
  <div>
    <div>
      <div class="store_info">选择拆分的产品</div>
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
        <Table border ref="selection" :columns="columns" :data="dataList" @on-select="hasSelected"></Table>
        <Page :total="total" :current="pageNum" :page-size="pageSize" @on-change="pageChange" class="tr mt20" />
      </div>
      <!-- 已选择数据 -->
      <div class="choose">
        <p class="choose_text">已选择：</p>
        <ul>
          <li v-for="(item, index) in outStoreList" :key="index">
            <span class="choose_name">{{ item.productName }}</span>
            <span class="choose_count">{{ item.number }} {{ item.unit }}</span>
          </li>
        </ul>
        <div class="mt30 pl30">合计：{{ totalMoney }} 元</div>
      </div>
      <Divider />
      <div class="store_info">填写拆分信息</div>
      <!-- 拆分 -->
      <Row class="mb40 pl20">
        <Col span="24" style="line-height:32px">
          <div>拆分数量：<span v-if="this.outStoreList.length > 0">{{ this.outStoreList[0].number }} {{ this.outStoreList[0].unit }}</span></div>
        </Col>
        <Col span="24" style="line-height:32px">
          <span class="mr10">拆分形式：</span>
          <RadioGroup v-model="way" @on-change="radioChange">
            <Radio label="按比例"></Radio>
            <Radio label="按数量"></Radio>
          </RadioGroup>
          <div v-if="way==='按比例'">
            <div style="color: #ff0000;">各部分比例相加之和必须为1，且每份需为整数</div>
            <Form label-position="right" :label-width="100" class="mt10">
              <FormItem label="拆分的份额">
                <InputNumber :max="10" :min="1" v-model="count" @on-change="countChange" style="width: 100px;" />
              </FormItem>
            </Form>
            <div v-if="infoList.length > 0" class="mt20">
              <Form v-for="(info, index) in infoList" :key="item" :ref="`info${index}`" :model="info" label-position="right" :label-width="100" :rules="ruleInline">
                <Row>
                  <Col span="6">
                    <FormItem :label="`第${index+1}份拆分比例`" prop="percent">
                      <InputNumber :max="100" :min="1" v-model="info.percent" :ref="`percent${index}`" style="width: 100%;" @on-change="percentChange($event, index)"
                        :formatter="value => `${value}%`" :parser="value => value.replace('%', '')"  />
                    </FormItem>
                  </Col>
                  <Col span="6">
                    <FormItem label="数量" prop="number">
                      <Input v-model="info.number" readonly />
                    </FormItem>
                  </Col>
                  <Col span="6">
                    <FormItem label="所入仓库" prop="inStore">
                      <Select v-model="info.inStore">
                        <Option v-for="item in inStoreList" :value="item.id" :key="item.id">{{ item.storeName }}</Option>
                      </Select>
                    </FormItem>
                  </Col>
                </Row>
              </Form>
            </div>
          </div>
          <div v-else>
            <div style="color: #ff0000;">各部分数量相加之和必须等于拆分的总数量，且每份需为整数</div>
            <Form label-position="right" :label-width="100" class="mt10">
              <FormItem label="拆分的份额">
                <InputNumber :max="10" :min="1" v-model="count2" @on-change="countChange2" style="width: 100px;" />
              </FormItem>
            </Form>
            <div v-if="infoList2.length > 0" class="mt20">
              <Form v-for="(info, index) in infoList2" :key="info" :ref="`info2${index}`" :model="info" label-position="right" :label-width="100" :rules="ruleInline2">
                <Row>
                  <Col span="6">
                    <FormItem label="数量" prop="number">
                      <InputNumber :max="outStoreList.length > 0 ? outStoreList[0].number : 0" :min="1" v-model="info.number" style="width: 100%;" />
                    </FormItem>
                  </Col>
                  <Col span="6">
                    <FormItem label="所入仓库" prop="inStore">
                      <Select v-model="info.inStore">
                        <Option v-for="item in inStoreList" :value="item.id" :key="item.id">{{ item.storeName }}</Option>
                      </Select>
                    </FormItem>
                  </Col>
                </Row>
              </Form>
            </div>
          </div>
        </Col>
      </Row>
      <!-- 按钮 -->
      <div class="tc">
        <Button type="default" @click="$emit('close')">取消</Button>
        <Button type="primary" class="ml10" @click="saveStore(0)">确定拆分并关闭</Button>
        <!-- <Button type="success" class="ml10" @click="saveStore(1)">确定拆分并继续</Button> -->
      </div>
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
export default {
  components: {
    vuiFilter
  },
  data () {
    return {
      columns: [
        {
          title: '单选',
          width: 60,
          align: 'center',
          fixed: 'left',
          render: (h, params) => {
            return h('div', [
              h('Radio', {
                props: {
                  value: params.row.checked
                },
                on: {
                  'on-change': (value) => {
                    // 重置单选的状态 只能选中一个
                    let arr = this.dataList.concat() // 必须重新生成一个数组做操作 在原数组上操作无效
                    this.dataList = []
                    arr.forEach((element, index) => {
                      if (params.index === index) {
                        element.checked = true
                        params.row.number = element.number // 也有可能是先变数量再单选至这一项
                        this.outStoreList = []
                        this.outStoreList.push(params.row)
                        this.totalMoney = element.number * element.price // 重新计算合计
                        this.totalMoney = parseFloat(this.totalMoney.toFixed(2)) // 保留两位小数
                        console.log('element', element)
                        console.log('params', params)
                      } else {
                        element.checked = false
                      }
                    })
                    this.dataList = arr
                  }
                }
              })
            ])
          }
        },
        {
          title: '拆分数量',
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
          width: 120,
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
      dataList: [],
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
      operatorAccount: '',
      way: '按比例',
      count: null,
      infoList: [],
      ruleInline: {
        percent: [
          { required: true, type: 'number', message: '请填写拆分比例', trigger: 'change' }
        ],
        number: [
          { required: true, type: 'number', message: '请填写数量', trigger: 'change' }
        ],
        inStore: [
          { required: true, type: 'number', message: '请选择所入仓库', trigger: 'change' }
        ]
      },
      count2: null,
      infoList2: [],
      ruleInline2: {
        number: [
          { required: true, type: 'number', message: '请填写数量', trigger: 'change' }
        ],
        inStore: [
          { required: true, type: 'number', message: '请选择所入仓库', trigger: 'change' }
        ]
      }
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
    // 重置当前页面
    separateStoreInit () {
      // 获取经手人
      this.$api.post('/member/login/findCurrentUser', {
        account: this.$user.loginAccount
      }).then(response => {
        if (response.data.displayName) {
          this.operatorAccount = response.data.displayName
        }
      })
      // 查询条件
      this.searchInfo = {
        productTypeName: '',
        productTypeId: '',
        productName: '',
        inStore: '',
        customId: ''
      }
      this.way = '按比例'
      // 表格数据及下拉框
      this.initStore()
      this.initClassify()
      this.initStoreList()
      this.outStoreList = []
      this.totalMoney = 0
      // 拆分信息表单
      this.count = null
      this.count2 = null
      this.infoList = []
      this.infoList2 = []
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
    hasSelected (selection, row) {
      console.log('selection', selection)
      console.log('row', row)
      let arr = this.dataList.concat() // 必须重新生成一个数组做操作 在原数组上操作无效
      this.dataList = []
      arr.forEach(element => {
        if (element.id === row.id) {
          element._checked = true
        } else {
          element._checked = false
        }
      })
      this.dataList = arr
    },
    countChange (event) {
      this.infoList = []
      console.log('event', event)
      for (let i = 0; i < event; i ++) {
        let obj = {
          percent: null,
          number: '',
          inStore: ''
        }
        this.infoList.push(obj)
      }
    },
    countChange2 (event) {
      this.infoList2 = []
      for (let i = 0; i < event; i ++) {
        let obj = {
          number: null,
          inStore: ''
        }
        this.infoList2.push(obj)
      }
    },
    percentChange (event, index) {
      console.log('event', event, index)
      this.infoList[index].number = event * this.outStoreList[0].number / 100
    },
    radioChange (value) {
      console.log(value)
      this.count = null
      this.count2 = null
      this.infoList = []
      this.infoList2 = []
    },
    saveStore (type) {
      if (this.outStoreList.length === 0) {
        this.$Message.info('请先选择要拆分的产品！')
        return
      }
      if (this.outStoreList[0].number === 0) {
        this.$Message.info('拆分的产品数量不能为0！')
        return
      }
      if (this.way === '按比例') {
        if (this.count === null) {
          this.$Message.info('拆分的份额不能为空！')
          return
        }
        let percent = null
        for (let index = 0; index < this.infoList.length; index++) {
          if (this.infoList[index].percent !== null) {
            percent += this.infoList[index].percent
          } else {
            // 判断每一份比例是否存在
            this.$Message.info('每一份比例均不能为空！')
            return
          }
          // 表单验证
          this.$refs[`info${index}`][0].validate((valid) => {
            if (!valid) {
              return
            }
          })
        }
        console.log('percent', percent)
        // 判断比例是否为100%
        if (percent !== 100) {
          this.$Message.info('各部分比例相加之和必须为100%！')
          return
        }
      } else if (this.way === '按数量') {
        if (this.count2 === null) {
          this.$Message.info('拆分的份额不能为空！')
          return
        }
        let number = null
        for (let index = 0; index < this.infoList2.length; index++) {
          if (this.infoList2[index].number !== null) {
            number += this.infoList2[index].number
          } else {
            // 判断每一份比例是否存在
            this.$Message.info('每一份数量均不能为空！')
            return
          }
          // 表单验证
          this.$refs[`info2${index}`][0].validate((valid) => {
            if (!valid) {
              return
            }
          })
        }
        console.log('number', number)
        // 判断比例是否为100%
        if (number !== this.outStoreList[0].number) {
          this.$Message.info('各部分数量相加之和必须等于拆分的总数量！')
          return
        }
      }
      // 请求接口
      this.$api.post('/shop/inventory/basicSetting/storeSplit', {
        account: this.$user.loginAccount,
        operatorAccount: this.operatorAccount,
        map: this.outStoreList[0], // 拆分只会有一个对象
        list: this.way === '按比例' ? this.infoList : this.infoList2
      }).then(response => {
        console.log('save', response)
        if (response.code === 200) {
          this.$Message.success('拆分成功！')
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
