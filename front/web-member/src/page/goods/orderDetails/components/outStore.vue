<template>
  <Modal v-model="modalShow" width="1200" :mask-closable="false" title="出库" :styles="{top: '60px'}">
    <div>
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
      <div class="store_info">选择出库产品</div>
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
        <Button type="default" @click="modalShow = false">取消</Button>
        <Button type="primary"  class="ml10" @click="saveStore">确定出库</Button>
    </div>
    <div slot="footer">
    </div>
    <outboundOrder ref="outboundOrder"></outboundOrder>
  </Modal>
</template>

<script>
import outboundOrder from '../../../inventoryControl/component/outboundOrder'
import {numAdd, numMulti} from '~utils/utils'
export default {
  components: {
    outboundOrder
  },
  data () {
    return {
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
                          let total = parseFloat((numMulti(element.number, element.price)).toFixed(2))
                          this.totalMoney = numAdd(parseFloat(total).toFixed(2), parseFloat(this.totalMoney).toFixed(2)).toFixed(2)
                        }
                      })
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
      outTypeList: [],
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
      modalShow: false
    }
  },
  created () {
    this.initOutTypeList()
  },
  methods: {
    getExitOrderList (order) {
      console.log('关闭')
      this.modalShow = false
      if (order) {
        this.$api.post('/shop/inventory/basicSetting/exitOrder', {
          account:this.$user.loginAccount,
          order: order
        }).then(response => {
          if (response.code === 200) {
          this.$refs['outboundOrder'].init(response.data, response.data.list)
          }
        })
      }
    },
    // 重置当前页面 根据 产品名称 查询 
    outStoreInit (productNameList) {
      console.log(productNameList)
      // 查询条件
      this.searchInfo = {
        productTypeName: '',
        productTypeId: '',
        productName: productName,
        inStore: '',
        customId: ''
      }
      // 表格数据及下拉框
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
      this.modalShow = true
    },
    // 获取出库类型
    initOutTypeList () {
      this.$api.post('/shop/inventory/basicSetting/outStoreList', {
        account: this.$user.loginAccount,
        pageSize: 99999,
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
    // 获取初始化数据
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
        console.log(this.info)
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
              console.log(response)
              this.getExitOrderList(response.data)
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
        let total = parseFloat((numMulti(element.number, element.price)).toFixed(2))
        this.totalMoney = numAdd(parseFloat(total).toFixed(2), parseFloat(this.totalMoney).toFixed(2)).toFixed(2)
      })
    },
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
