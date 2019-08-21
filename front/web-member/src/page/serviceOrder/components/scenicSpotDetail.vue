<template>
  <div>
    <Modal
        v-model="isCheckPackage" width="900" :mask-closable="false"
        title="订单详情">
           <Row :gutter="8">
            <Col span="12" >
            <div style="border:1px solid #e8e8e8" ref="orderLeft" :style="{'min-height': orderLeftHeight}">
              <div class="pd15">
              <b style="font-size:18px;">已选门票</b>
              </div>
              <Table :columns="orderColumns" v-if="checkType === '1'" :data="orderData"></Table>
              <Table :columns="orderColumns1" v-else :data="orderData"></Table>
              <p class="tc pt30">
                <span style="font-size:16px;">合计：</span>
                <span class="t-orange" style="font-size:22px;">优惠价￥<b class="h1">{{parseFloat(info.discountPrice).toFixed(2)}}</b></span>
                <span class="t-grey ml5" style="font-size:12px;">原价￥<b style="text-decoration: line-through;">{{parseFloat(info.price).toFixed(2)}}</b></span>
                <span class="t-green ml5" style="font-size:12px;">省￥<b>{{(parseFloat(info.price).toFixed(2) - parseFloat(info.discountPrice).toFixed(2)).toFixed(2)}}</b></span>
              </p>

            </div>
            </Col>
            <Col span="12">
            <div class="pd20" style="border:1px solid #e8e8e8" ref="orderRight" :style="{'min-height': orderRightHeight}">
              <div class="pd15">
                <b style="font-size:18px;">
                  <span>{{info.setMealName}}</span>
                </b>
              </div>
              <Row :gutter="8" class="pb10" type="flex" align="middle" v-if="contact[0] &&contact[0].contactNameStatus">
                <Col span="5" class="lable"><b>联系人：</b></Col>
                <Col span="19">{{contact[0].contact_name}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle" v-if="contact[0] && contact[0].phoneStatus">
                <Col span="5"  class="lable"><b>联系电话：</b></Col>
                <Col span="19">{{contact[0].phone}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>使用日期：</b></Col>
                <Col span="19"> {{moment(info.date).format('YYYY-MM-DD')}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>支付方式：</b></Col>
                <!-- 在线支付 0 预付订金 1 -->
                <Col span="19" v-if="checkedData[0] && checkedData[0].payType == 0">在线支付</Col>
                <Col span="19" v-else>预付订金</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle"  v-if="checkedData[0]">
                <Col span="5"  class="lable"><b>注意事项：</b></Col>
                <Col span="19">
                  {{checkedData[0].mattres_need_attention}}
                </Col>
              </Row>
              <div class="pd15">
                <p style="font-size:18px;">
                  <span>个人联系信息</span>
                </p>
              </div>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>联系人：</b></Col>
                <Col span="19">
                  {{info.name}}
                </Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>联系电话：</b></Col>
                <Col span="19">
                  {{info.phone}}
                </Col>
              </Row>
              </div>
            </Col>
          </Row>
          <div >
            <div class="pl20 pr20 mt40" style="border-top: 1px solid #eee;" v-if="!isbuyer && (status === 3 || status === 4 || status === 5)">
                <p class="pt30 pb20" >订单合计金额：{{info.discountPrice}} 元</p>
                <Form ref="form" :label-width="90" label-position="left" :model="info" :rules="infoRuleInline" v-if="status === 3">
                  <FormItem  label="退款金额：" prop="refundAmount">
                    <InputNumber :max="info.discountPrice" :min="0" v-model="form.refundAmount"></InputNumber>
                  </FormItem>
                  <FormItem label="处理备注：">
                    <Input type="textarea" v-model="form.remarks" :maxlength="200" :autosize="{minRows: 3, maxRows: 5}" placeholder="请输入"/>
                  </FormItem>
                </Form>
                <div class="tc pt20" v-if="status === 3">
                  <Button type="primary" @click="onSave('5')">确认退款</Button>
                  <Button  @click="onSave('4')">拒绝退款</Button>
                </div>
                <p class="pb20 " v-if="status === 4 || status === 5">退款金额：{{form.refundAmount}} <span v-if="form.refundAmount">元</span></p>
                <p class="pb20 " v-if="status === 4 || status === 5">处理备注：{{form.remarks}}</p>
                <p class="pb20 t-grey" v-if="status === 4 || status === 5">处理人员：{{form.handlePersonnel}}</p>
                <p class="pb20 t-grey" v-if="status === 4 || status === 5">处理时间：{{form.processingTime}}</p>
            </div>
            <div class="pl20 pr20 mt40" style="border-top: 1px solid #eee;" v-if="isbuyer && (status === 4 || status === 5)">
                <p class="pt30 pb20">订单合计金额：{{info.discountPrice}} 元</p>
                <p class="pb20 " v-if="status === 4 || status === 5">退款金额：{{form.refundAmount}} <span v-if="form.refundAmount">元</span></p>
                <p class="pb20 " v-if="status === 4 || status === 5">处理备注：{{form.remarks}}</p>
                <p class="pb20 t-grey" v-if="status === 4 || status === 5">处理人员：{{form.handlePersonnel}}</p>
                <p class="pb20 t-grey" v-if="status === 4 || status === 5">处理时间：{{form.processingTime}}</p>
            </div>
          </div>
        <div slot="footer" class="tc" >
          <div v-if="status != 3">
            <Button type="text" @click="isCheckPackage = false">取消</Button>
            <Button type="primary" @click="isCheckPackage = false">确定</Button>
          </div>
        </div>
    </Modal>
  </div>
</template>
<script>
import {numMulti} from '~utils/utils'
import {isPhone2} from '~utils/validate'
  export default {
    props: {
      data: {
        type: Array,
        default: () => {
          return []
        }
      },
      isbuyer: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        form: {
          refundAmount: null,
          remarks: '',
          handlePersonnel: '',
          processingTime: '',
        },
        infoRuleInline: {
          // refundAmount: [
          //   {required: true, message:'请填写退款金额', trigger: 'blur'}
          // ]
        },
        orderLeftHeight: '0px',
        orderRightHeight: '0px',
        isCheckPackage: false,
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
        orderColumns1: [
          {
            title: '名称',
            key: 'ticketName',
            align: 'center'
          },
          {
            title: '数量',
            key: 'num',
            align: 'center'
          },
          {
            title: '价格（元）',
            key: 'discountPrice',
            align: 'center',
            render: (h, params) => {
              return h('span', `￥ ${params.row.discountPrice ? parseFloat(numMulti(params.row.discountPrice, params.row.num)).toFixed(2) : parseFloat(numMulti(params.row.ticketPrice, params.row.num)).toFixed(2)}`)
            }
          },
        ],
        checkedData: [],
        orderData: [],
        showOrder: true,
        info: {
          name: '',
          phone: ''
        },
        contact: [],
        checkType: '0',
        status: 0,
        displayName: '',
      }
    },
    created() {
      this.getName()
    },
    methods: {
      getName () {
        this.$api.post('/member/login/findCurrentUser', {
          account: this.$user.loginAccount
        }).then(response => {
        	if (response.data.displayName) {
            this.displayName = response.data.displayName
        	}
        })
      },
      onSave (type) {
        this.form.status = type
        this.form.handlePersonnel = this.displayName
        this.form.processingTime = this.moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
        if (type === '5' && !this.form.refundAmount) {
          this.$Message.warning('请填写退款金额')
          return
        }
        this.$api.post('/member/fishing/updateOrderRefund', this.form).then(response => {
          if (response.code === 200) {
            this.$Message.success('操作成功')
          } else {
            this.$Message.success('操作失败')
          }
        })
      },
      checkOrder (data, list) {
        this.checkType = list.checkType
        this.form.id = list.id
        this.status = list.status
        if (list.checkType === '0') { // 0 在线自定义套餐 1 选择固定套餐
          this.orderData = data
        } else {
          this.checkedData = list.setMeal
          let datas = []
          this.checkedData.forEach(e => {
            datas = datas.concat(e.productList)
          })
          this.orderData = datas
        }
        this.info = {
          name: list.buyersName,
          phone: list.buyersPhone,
          discountPrice: list.discountPrice ? parseFloat(list.discountPrice) : 0,
          price: list.price,
          setMealName: list.setMealName,
          date: list.date,
        }
        if (list.type === 3) {
          this.form.refundAmount = list.discountPrice ? parseFloat(list.discountPrice) : 0
        } else {
          this.form.refundAmount = list.refundAmount
          this.form.handlePersonnel = list.handlePersonnel
          this.form.processingTime = list.processingTime
          this.form.remarks = list.remarks
        }
        this.contact = list.contact
        this.isCheckPackage = true
        this.showOrder = false
        this.changeHeight()
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
          } else if (orderRight < orderLeft) {
            that.orderRightHeight = `${orderLeft}px`
          }
        })
      },
    }
  }
</script>
