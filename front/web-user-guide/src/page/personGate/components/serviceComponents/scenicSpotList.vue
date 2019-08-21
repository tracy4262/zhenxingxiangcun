<template>
  <div>
    <Modal
        v-model="isCheckPackage" width="900" :mask-closable="false"
        title="可选套餐:">
        
        <Table :columns="columns" :data="data" v-if="showOrder" @on-row-click="onRowClick"></Table>
        <div v-else>
           <Row :gutter="8" v-if="checkedData[0]">
            <Col span="12" >
            <div style="border:1px solid #e8e8e8" ref="orderLeft" :style="{'min-height': orderLeftHeight}">
              <div class="pd15">
              <b style="font-size:18px;">已选门票</b>
              </div>
              <Table :columns="orderColumns" :data="orderData"></Table>
              <p class="tc pt30">
                <span style="font-size:16px;">合计：</span>
                <span class="t-orange" style="font-size:22px;">优惠价￥<b class="h1">{{parseFloat(checkedData[0].setMealPrice).toFixed(2)}}</b></span>
                <span class="t-grey ml5" style="font-size:12px;">原价￥<b style="text-decoration: line-through;">{{parseFloat(checkedData[0].totalPrice).toFixed(2)}}</b></span>
                <span class="t-green ml5" style="font-size:12px;">省￥<b>{{(parseFloat(checkedData[0].totalPrice).toFixed(2) - parseFloat(checkedData[0].setMealPrice).toFixed(2)).toFixed(2)}}</b></span>
              </p>

            </div>
            </Col>
            <Col span="12">
            <div class="pd20" style="border:1px solid #e8e8e8" ref="orderRight" :style="{'min-height': orderRightHeight}">
              <div class="pd15">
                <b style="font-size:18px;" v-if="checkedData[0]">
                  <span>{{checkedData[0].setMealName}}</span>
                </b>
              </div>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5" class="lable"><b>联系人：</b></Col>
                <Col span="19">{{checkedData[0].contact_name}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>联系电话：</b></Col>
                <Col span="19">{{checkedData[0].phone}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>使用日期：</b></Col>
                <Col span="19"> {{moment(checkedData[0].date).format('YYYY-MM-DD')}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>支付方式：</b></Col>
                <!-- 在线支付 0 预付订金 1 -->
                <Col span="19" v-if="checkedData[0].payType == 0">在线支付</Col>
                <Col span="19" v-else>预付订金</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>注意事项：</b></Col>
                <Col span="19">
                  {{checkedData[0].mattres_need_attention}}
                </Col>
              </Row>
              </div>
            </Col>
          </Row>
        </div>
        <div slot="footer">
          <Button type="text" @click="isCheckPackage = false">取消</Button>
          <Button type="primary" @click="handleOk" v-if="showOrder">确定</Button>
          <Button type="primary" @click="handleOrderOk" v-else>确定</Button>
        </div>
    </Modal>
  </div>
</template>
<script>
import expandRow from './table-expand.vue'
import {numMulti} from '~utils/utils'
  export default {
    components: {
      expandRow
    },
    props: {
      data: {
        type: Array,
        default: () => {
          return []
        }
      }
    },
    data() {
      return {
        orderLeftHeight: '0px',
        orderRightHeight: '0px',
        isCheckPackage: false,
        columns: [{
            type: 'expand',
            width: 50,
            render: (h, params) => {
              return h(expandRow, {
                props: {
                  row: params.row
                },
                on: {
                  'on-change': (e) => {
                    params.row.date = e
                    this.data.splice(params.index, 1, params.row)
                  }
                }
              })
            }
          },

          {
            title: '套餐名称',
            key: 'setMealName',
              render: (h, params) =>{
                return h('Radio', {
                  props: {
                    value: params.row.checked
                  },
                  on: {
                    'on-change': (val) => {
                      params.row.checked = true
                      this.data.forEach((item, index) => {
                        if (item.checked) {
                          item.checked = false
                          this.data.splice(index, 1, item)
                        }
                      })
                      this.data.splice(params.index, 1, params.row)
                    }
                  }
                }, params.row.setMealName)
              }
          },
          {
            title: '原价',
            key: 'totalPrice',
            align: 'center',
            render: (h, params) => {
              return h('span', `￥ ${parseFloat(params.row.totalPrice).toFixed(2)}`)
            }
          },
          {
            title: '现价',
            key: 'setMealPrice',
            align: 'center',
            render: (h, params) => {
              return h('span', `￥ ${parseFloat(params.row.setMealPrice).toFixed(2)}`)
            }
          }
        ],
        orderColumns: [
          {
            title: '名称',
            key: 'name',
            align: 'center'
          },
          {
            title: '数量',
            key: 'num',
            align: 'center'
          },
          {
            title: '价格（元）',
            key: 'total',
            align: 'center',
            render: (h, params) => {
              return h('span', `￥ ${parseFloat(params.row.total).toFixed(2)}`)
            }
          },
        ],
        loginUser: JSON.parse(sessionStorage.getItem('user')),
        type: '',
        id: '',
        checkedData: [],
        orderData: [],
        showOrder: true
      }
    },
    created() {
      this.id = this.$route.query.id
      this.type = this.$route.query.type
      this.account = this.loginUser ? this.loginUser.loginAccount : ''
    },
    methods: {
      // 单击表格
      onRowClick (row, index) {
        row._expanded = !row._expanded
        this.data.splice(index, 1, row)
      },
      handleOk () {
        this.checkedData = []
        let flag = true
        this.data.forEach(element => {
          if (element.checked) {
            if (element.date) {
              this.moment(element.date).format('YYYY-MM-DD')
            } else {
              this.$Message.error('请选择使用日期')
              flag =  false
            }
            this.checkedData.push(element)
          }
        })
        if (flag) {
          if (!this.checkedData.length) {
            this.$Message.error('请选择套餐')
          } else {
            this.showOrder = false
            this.title = '订单详情：'
            let data = []
            let foodData = []
            this.checkedData.forEach(e => {
              data = data.concat(e.productList)
            })
            this.orderData  =data
            this.changeHeight()
          }
        }
      },
      changeHeight () {
        let that = this
        this.$nextTick(function () {
          // 先计算左右两边的高度
          let orderRight = that.$refs['orderRight'].offsetHeight
          let orderLeft = that.$refs['orderLeft'].offsetHeight
          // 如果右边大于左边的
          if (orderRight > orderLeft) {
            // 让左边的高度等于右边的高度
            that.orderLeftHeight = `${orderRight}px`
            // 并计算出左边中间的table的最小高度，这样 价格就显示在最下方了
            let orderLftTop = that.$refs['orderLftTop'].offsetHeight
            let orderLftBottom = that.$refs['orderLftBottom'].offsetHeight
            that.orderLftMiddle = `${orderRight - orderLftTop - orderLftBottom}px`
          } else if (orderRight < orderLeft) {
            that.orderRightHeight = `${orderLeft}px`
          }
        })
      },
      handleOrderOk () {
        let data = {
          contactName: this.checkedData[0].contact_name,
          phone: this.checkedData[0].phone,
          account: this.checkedData[0].account,
          sellAccount: this.account,
          date: this.moment(this.checkedData[0].date).format('YYYY-MM-DD'),
          setMealId: this.checkedData[0].setMealId,
          scenicSpotData: [],
          type: '2',
          serviceId: this.id,
          discountPrice: this.checkedData[0].setMealPrice,
          price: this.checkedData[0].totalPrice,
        }
        console.log(data)
        console.log(this.checkedData)
        this.$emit('on-buy', {data: data, checkedData: this.checkedData})
        this.isCheckPackage = false
      },
    }
  }
</script>
