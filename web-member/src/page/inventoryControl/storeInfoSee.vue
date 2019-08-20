<template>
  <div class="storeInfoSee">
    <div ref="top">
      <top :address="false" />
    </div>
    <div class="inv_main" :style="{'min-height': height}">
      <div class="main_top">
        <div class="main_top_wrap">
          <Breadcrumb>
            <BreadcrumbItem to="/inventoryControl/config">库存管理</BreadcrumbItem>
            <BreadcrumbItem>库存流水明细</BreadcrumbItem>
          </Breadcrumb>
          <div class="main_top_title">库存管理</div>
          <p class="main_top_desc">库存管理通过对入/出库类型、入/出库单据、入/出库商品数量的管理，及时反映各种物资的仓储、流向情况，为生产管理和成本核算提供依据</p>
        </div>
      </div>
      <div class="info_see">
        <div class="product">
          <div class="pro_name">{{productInfo.productName}}</div>
          <div>产品编码：{{productInfo.productCode}}</div>
          <div class="pro_cate">自定义分类：{{productInfo.customName}}</div>
        </div>
        <Divider/>
        <div class="pl20 pr20">
          <div class="pro_count">当前库存量：{{productInfo.totalStore}} = 期初库存量：{{productInfo.initialStore}} + 入库量：{{productInfo.inNumber}} - 出库量：{{productInfo.outNumber}}</div>
           <Form :label-width="90" ref="info" :model="info">
              <Row>
                <Col span="7">
                  <FormItem label="出入库日期">
                    <DatePicker 
                      v-model="outgoingTime"
                      @on-change="outgoingTimeChange" 
                      type="daterange" 
                      placement="bottom-end" ></DatePicker>
                  </FormItem>
                </Col>
                <Col span="7">
                  <FormItem label="所在仓库">
                    <Select v-model="info.inStore" clearable>
                        <Option v-for="(item,index) in inStoreList" :value="item.id" :key="index">{{ item.storeName }}</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="7">
                  <FormItem label="出入库类型" >
                    <Select v-model="outgoingType" @on-change="outgoingTypeChange" clearable>
                        <Option v-for="(item, index) in outgoingTypeList" :value="item.index" :key="index">{{ item.name }}</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="3" class="tr pr20">
                  <Button type="success" @click="onSearch">查询</Button>
                </Col>
              </Row>
            </Form>
          <div class="mt20">
            <Table border :columns="columns" :data="dataList"></Table>
            <Page class="tr pt30 pb10" :total="total" @on-change="getNextPage" :page-size="pageSize" :current="pageNum"></Page>
          </div>  
        </div>
      </div>
    </div>
    <storage ref="storage"></storage>
    <outboundOrder ref="outboundOrder"></outboundOrder>
    <div ref="foot">
      <foot ></foot>
    </div>
  </div>
</template>

<script>
import top from '../../top'
import foot from '../../foot'
import storage from './component/storage'
import outboundOrder from './component/outboundOrder'

export default {
  components: {
    top,
    foot,
    storage,
    outboundOrder
  },
  data () {
    return {
      total: 0,
      pageSize: 10,
      pageNum: 1,
      height: '',
      columns: [
        {
          title: '序号',
          key: 'orderNumber',
          align: 'center',
          fixed: 'left',
          width: 80
        },
        {
          title: '出入库日期',
          key: 'createTime',
          align: 'center',
          width: 100
        },
        {
          title: '出入库类型',
          key: 'storeTypeName',
          align: 'center',
          width: 100
        },
        {
          title: '业务单号',
          key: 'order',
          align: 'center',
          width: 180,
          render: (h, params) => {
            if (params.row.order) {
              return h('span', `${params.row.order}`)
            } else {
              return h('span', '——')
            }
          }
        },
        {
          title: '批次号',
          key: 'batchNumber',
          align: 'center',
          width: 180,
          render: (h, params) => {
            if (params.row.batchNumber) {
              return h('span', `${params.row.batchNumber}`)
            } else {
              return h('span', '——')
            }
          }
        },
        {
          title: '出库量',
          key: 'outNumber',
          align: 'center',
          width: 80,
          render: (h, params) => {
            if (params.row.outNumber) {
              return h('span', `${params.row.outNumber}`)
            } else {
              return h('span', '——')
            }
          }
        },
        {
          title: '入库量',
          key: '',
          align: 'center',
          width: 80,
          render: (h, params) => {
            if (params.row.inNumber) {
              return h('span', `${params.row.inNumber}`)
            } else {
              return h('span', '——')
            }
          }
        },
        {
          title: '单价(元)',
          key: 'price',
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
          title: '合计(元)',
          key: 'totalPrice',
          align: 'center',
          width: 100
        },
        {
          title: '经手人',
          key: 'operatorAccount',
          align: 'center',
          width: 100
        },
        {
          title: '操作',
          width: 100,
          fixed: 'right',
          render: (h, params) => {
            if (params.row.order) {
              return h('div',[
                h('Button',{
                    props: {
                        type: 'text',
                        size: 'small'
                    },
                    style: {
                        marginRight: '5px'
                    },
                    on: {
                        click: () => {
                          // type 1 入库 2 出库
                          if (params.row.type === 1) { // 入库
                            this.$api.post('/shop/inventory/basicSetting/enterOrder', {
                              account:this.$user.loginAccount,
                              order: params.row.order
                            }).then(response => {
                              if (response.code === 200) {
                                this.$refs['storage'].init(response.data, response.data.list)
                              }
                            })
                          } else {
                            this.$api.post('/shop/inventory/basicSetting/exitOrder', {
                              account:this.$user.loginAccount,
                              order: params.row.order
                            }).then(response => {
                              if (response.code === 200) {
                              this.$refs['outboundOrder'].init(response.data, response.data.list)
                              }
                            })
                          }
                        }
                    }
                }, '查看单据'),
              ])
            } else {
              return h('span', '——')
            }
          }
        }
      ],
      dataList: [],
      info: {
        type: '', // 出入库类型
        storeType: '', // 出入库类型 的id type与storeType要么一起传值，要么都不传
        endTime: '', // 出入库日期 结束
        beginTime: '', // 出入库日期 开始
        inStore: '', //仓库id， 条件，非必传
      },
      outgoingType: '',
      outgoingTime: [],
      inStoreList: [],
      outgoingTypeList: [],
      productCode: '',
      productInfo: {}
    }
  },
  created () {
    if (this.$route.query.code) {
      this.productCode = this.$route.query.code
      this.init()
      this.getDetail()
    }
    // 初始化入库仓库
    this.initStore()
    // 出入库类型
    this.getOutgoingTypeList()
  },
  mounted () {
    this.handleGetHeight()
  },
  methods: {
    getDetail () {
      let data = {
        account: this.$user.loginAccount,
        productCode: this.productCode
      }
      this.$api.post('/shop/inventory/basicSetting/productStoreDetail', data).then(response => {
        if (response.code === 200) {
          this.productInfo = response.data
        }
      })
    },
    // 初始化查询
    init () {
      let data = this.info
      data.account = this.$user.loginAccount
      data.pageSize = this.pageSize
      data.pageNum = this.pageNum
      data.productCode = this.productCode
      this.$api.post('/shop/inventory/basicSetting/storeDetail', data).then(response => {
        if (response.code === 200) {
          this.dataList = response.data.list
          this.total = response.data.total
        }
      })
    },
    // 查询
    onSearch () {
      this.getNextPage(1)
    },
    // 翻页
    getNextPage (e) {
      this.pageNum = e
      this.init()
    },
    // 出入库时间发生改变
    outgoingTimeChange (arr) {
      console.log(arr)
      if (arr[0]) {
        this.info.beginTime = this.moment(arr[0]).format('YYYY-MM-DD')
        this.info.endTime = this.moment(arr[1]).format('YYYY-MM-DD')
      } else {
        this.info.endTime = ''
        this.info.beginTime = ''
      }
    },
    // 出入库类型 改变
    outgoingTypeChange (e) {
      if (e && e > 0) { // 有值
        let item = this.outgoingTypeList[e-1]
        this.info.type = item.type
        this.info.storeType = item.id
      } else { // 没有值
        this.info.type = ''
        this.info.storeType = ''
      }
      console.log(this.info)
    },
    // 出入库类型
    getOutgoingTypeList () {
      this.$api.post('/shop/inventory/basicSetting/storeTypeList', {
        account: this.$user.loginAccount
      }).then(response => {
        if (response.code === 200) {
          let arr = []
          response.data.forEach((e, index) =>{
            e.index = index +1
            arr.push(e)
          })
          this.outgoingTypeList = arr
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
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    handleGetHeight () {
      let clientHeight = document.documentElement.clientHeight
      let topHeight = this.$refs.top.offsetHeight
      let footHeight = this.$refs.foot.offsetHeight
      this.height = `${clientHeight-topHeight-footHeight}px`
    }
  }
}
</script>

<style lang="scss" scoped>
.storeInfoSee{
  .inv_main{
    width: 100%;
    background: rgb(249, 249, 249);
    padding-bottom: 40px;
    .main_top{
      background: #fff;
      margin-bottom: 20px;
      .main_top_wrap{
        width: 1000px;
        margin: 0 auto;
        padding-top: 28px;
      }
      .main_top_title{
        font-size: 20px;
        color: #000 85%;
        font-weight: bold;
        margin: 16px 0;
      }
      .main_top_desc{
        width: 760px;
        line-height: 22px;
        font-size: 14px;
        color: rgba(0, 0, 0, .6);
        padding-bottom: 20px;
      }
      .btn_list{
        padding-bottom: 20px;
        .btn_item{
          margin-right: 12px;
          &:hover{
          background: #E2F6F2;
          }
        }
        
      }
    }
    .info_see{
      padding: 20px;
      width: 1000px;
      min-height: 800px;
      margin: 0 auto;
      background-color: #fff;
      .product{
        display: flex;
        align-items: center;
        .pro_name{
          font-size: 20px;
          font-weight: bold;
          margin-right: 14px;
        }
        .pro_cate{
          color: #4A4A4A;
          padding-left: 5px;
          border-left: 6px solid #56B07D;
          margin-left: 14px;
        }
      }
      .pro_count{
        font-size: 16px;
        margin-bottom: 20px;
      }
      .mark_name{
        display: inline-block;
        width: 90px;
        text-align: right;
      }
    }
  }
}
</style>


