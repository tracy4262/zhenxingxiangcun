<template>
<div>
  <Row>
    <Col span="6">
      <span>产品分类：</span>
      <!-- productClassify  -->
      <vuiProduct ref="vuiProduct" 
      :values="info.productClassifyName" 
      @on-save="onSaveProductClassify" 
      @on-save-id="onSaveProductClassifyId" 
      :num="1" style="width:180px; display: inline-block;"></vuiProduct>
    </Col>
    <Col span="6">
      <span >产品名称：</span>
      <Input v-model="info.productName" style="width:180px" @on-change="onSearch"/>
    </Col>
    <Col span="6">
      <span>自定义分类：</span>
      <Select v-model="info.customId" style="width:180px" @on-change="onSearch" clearable>
          <Option v-for="(item,index) in customIds" :value="item.id" :key="index">{{ item.customName }}</Option>
      </Select>
    </Col>
    <Col span="6" class="mb20">
      <span>所在仓库：</span>
      <Select v-model="info.inStore" style="width:180px" @on-change="onSearch" clearable>
          <Option v-for="(item,index) in inStoreList" :value="item.id" :key="index">{{ item.storeName }}</Option>
      </Select>
    </Col>
    <Col span="12" class="mb20">
      <span>出库日期：</span>
      <DatePicker v-model="times" @on-change="timeChange" format="yyyy/MM/dd" type="daterange" placement="bottom-end" placeholder="请选择" style="width:472px"></DatePicker>
    </Col>
    <Col span="12">
      <span>出库单号：</span>
      <Input placeholder="" style="width:484px" v-model="info.order" @on-change="onSearch"/>
    </Col>
  </Row>
  <div>
    <Table border :columns="outStoreColumns" :data="outStoreData"></Table>
    <Page class="tr pt30 pb10" :total="total" @on-change="getNextPage" :page-size="pageSize" :current="pageNum"></Page>
  </div>  
  <outboundOrder ref="outboundOrder"></outboundOrder>
</div>
</template>

<script>
import vuiProduct from '~components/vui-product'
import outboundOrder from '../outboundOrder'
export default {
  components: {
    vuiProduct,
    outboundOrder
  },
  data () {
    return {
      total: 0,
      pageSize: 10,
      pageNum: 1,
      times: [],
      outStoreColumns: [
        {
          title: '出库单号',
          key: 'order',
          align: 'center',
          width: 180,
          fixed: 'left',
          render: (h, params) => {
            if (params.row.order) {
              return h('div',`${params.row.order}`)
            } else {
              return h('div','——')
            }
          }
        },
        {
          title: '产品编码',
          key: 'productCode',
          align: 'center',
          width: 150,
          fixed: 'left',
        },
        {
          title: '产品名',
          key: 'productName',
          align: 'center',
          width: 100,
          fixed: 'left',
        },
        {
          title: '自定义分类',
          key: 'customName',
          align: 'center',
          width: 100,
          fixed: 'left',
        },
        {
          title: '出库数',
          key: 'number',
          align: 'center',
          width: 100,
          fixed: 'left',
        },
        {
          title: '计量单位',
          key: 'unit',
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
          title: '所在仓库',
          key: 'storeName',
          align: 'center',
          width: 100,
        },
        {
          title: '出库日期',
          key: 'createTime',
          align: 'center',
          width: 100,
        },
        {
          title: '经手人',
          key: 'operatorAccount',
          align: 'center',
          width: 100,
        },
        {
          title: '操作',
          width: 100,
          fixed: 'right',
          align: 'center',
          key: '',
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
                }, '查看出库单'),
              ])
            } else {
              return h('div','——')
            }
          }
        },
      ],
      outStoreData: [
        {count: 12}
      ],
      customIds: [],
      inStoreList: [],
      info: {
        productClassify : '', // 产品分类
        productClassifyName : '', // 产品分类
        productName : '',
        customId : '',
        inStore : '', // 仓库
        order : '',  //出库单号
        beginTime : '',  //开始时间
        endTime : ''  //结束时间
      }
    }
  },
  created() {
    this.initClassify()
    this.initStore()
    this.onSearch()
  },
  methods: {
    onSearch () {
      this.info.pageSize =  this.pageSize
      this.info.pageNum =  this.pageNum
      this.info.account =  this.$user.loginAccount
      this.$api.post('/shop/inventory/basicSetting/exitRecord', this.info).then(response =>{
        if (response.code === 200) {
          this.total = response.data.total
          this.outStoreData = response.data.list
        }
      })
    },
    getNextPage (e) {
      this.pageNum = e
      this.onSearch()
    },
    // 产品分类 名称
    onSaveProductClassify (e) {
      if (e) {
        this.info.productClassifyName = e
      } else {
        this.info.productClassifyName = ''
      }
      this.onSearch()
    },
     // 产品分类 id
    onSaveProductClassifyId (e) {
      if (e) {
        this.info.productClassify = e
      } else {
        this.info.productClassify = ''
      }
      this.onSearch()
    },
    // 查询时间发生改变
    timeChange () {
      if (this.times[0]) {
        this.info.beginTime = this.moment(this.times[0]).format('YYYY-MM-DD')
      } else {
        this.info.beginTime = ''
      }
      if (this.times[1]) {
        this.info.endTime = this.moment(this.times[1]).format('YYYY-MM-DD')
      } else {
        this.info.endTime = ''
      }
      this.onSearch()
    },
    // 初始化自定义分类
    initClassify () {
      this.$api.post('/shop/inventory/basicSetting/customFind', {
        account: this.$user.loginAccount
      }).then(response => {
        if (response.code === 200) {
          // this.categories = response.data
          // let arr = this.categories
          this.customIds = response.data.slice(1)
        } else {
          this.$Message.error('服务器异常！')
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
  },
}
</script>

<style>

</style>
