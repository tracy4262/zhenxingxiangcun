<template>
  <div>
    <Modal
        v-model="isCheckPackage" width="1100" :mask-closable="false"
        :title="title">
        <Row  class="restaurant-choose-modal" v-if="showOrder" :gutter="8">
          <Col span="14" class="pr10" >
            <div  style="border-radius: 4px; overflow: hidden;border:1px solid #00c587;">
              <div style="height: 550px" class="pl20 pt20">
                <Steps :current="currentStep" class="pb30">
                  <Step title="选择时间" ></Step>
                  <Step title="选择门票" ></Step>
                </Steps>
                  <div v-if="currentStep == 0">
                    <Form :label-width="120">
                      <FormItem label="游玩时间：">
                        <DatePicker v-model="date" :options="options3" type="date" style="width: 200px" placeholder="请选择游玩日期"></DatePicker>
                      </FormItem>
                    </Form>
                  </div>
                  <div v-if="currentStep == 1" style="margin-bottom:24px;">
                    <div class="pd5">
                      <Table :columns="scenicSpotColumns" :height="scenicSpotDatas.length >= 9 ? '463' : (scenicSpotDatas.length <= 1 ? '95' : (scenicSpotDatas.length + 1) * 48) - 5" :data="scenicSpotDatas"></Table>
                    </div>
                  </div>
                </div>
                <div class="tr pd20" >
                  <Button type="default" @click="handlePre" v-if="currentStep != 0" class="mr10">上一步</Button>
                  <Button type="primary" @click="handleNext" v-if="currentStep != 1" class="mr10">下一步</Button>
                </div>
            </div>
          </Col>
          <Col span="10" >
            <div style="border-radius: 4px; overflow: hidden;border:1px solid #00c587;">
              <div style="height: 496px;">
                <p class="is-choose-title is-choose-titles">已选择</p>
                <div class="pd5">
                  <p class="is-choose-title is-choose-title-food">已选门票</p>
                  <Table :columns="scenicSpotColumn"  :height="scenicSpotData.length >= 6 ? '283' : (scenicSpotData.length <= 1 ? '95' : (scenicSpotData.length + 1) * 48) - 5" :data="scenicSpotData"></Table>
                </div>
                <div class="pd5">
                  <p class="is-choose-title is-choose-title-food">已选日期</p>
                  <p style="line-height:48px;color: #657180; font-size: 14px; border: 1px solid #e9eaec;"><span style="width: 150px; display: inline-block;" class="tc">游玩时间：</span>{{date ? moment(date).format('YYYY-MM-DD') : ''}}</p>
                </div>
              </div>
              <div>
                <p class="tc pt30" v-if="scenicSpotData.length">
                  <span class="t-orange" style="font-size:16px;">优惠价￥<b class="h1">{{datas.total}}</b></span>
                  <span class="t-grey ml5" >原价￥<b style="text-decoration: line-through;">{{datas.price}}</b></span>
                  <span class="t-green ml5" >省￥<b>{{datas.salePrice}}</b></span>
                </p>
                <p style="height: 54px;" v-if="!scenicSpotData.length">&nbsp;</p>
                <div class="tc pd20">
                  <Button type="primary" @click="handleOk" class="mr20">确定</Button> 
                  <Button type="default" @click="isCheckPackage = false">取消</Button> 
                </div>
              </div>
            </div>
          </Col>
        </Row>
        <div v-else class="service-detail-order">
          <Row :gutter="8">
            <Col span="12" >
            <div style="border:1px solid #e8e8e8" ref="orderLeft" :style="{'min-height': orderLeftHeight}">
              <div class="pd15" ref="orderLftTop">
                <b style="font-size:18px;">已选门票</b>
              </div>
              <div :style="{'min-height': orderLftMiddle}">
                <Table :columns="orderColumns" :height="scenicSpotData.length >= 5 ? '283' : (scenicSpotData.length <= 1 ? '90' : (scenicSpotData.length + 1) * 48 -5)" :data="scenicSpotData"></Table>
              </div>
              <p class="tc pt30" ref="orderLftBottom">
                <span style="font-size:16px;">合计：</span>
                <span class="t-orange" style="font-size:22px;">优惠价￥<b class="h1">{{datas.total}}</b></span>
                <span class="t-grey ml5" style="font-size:12px;">原价￥<b style="text-decoration: line-through;">{{datas.price}}</b></span>
                <span class="t-green ml5" style="font-size:12px;">省￥<b>{{datas.salePrice}}</b></span>
              </p>
            </div>
            </Col>
            <Col span="12">
            <div class="pd20" style="border:1px solid #e8e8e8" ref="orderRight" :style="{'min-height': orderRightHeight}">
              <div class="pd15">
                <b style="font-size:18px;" >{{packageData.service_name}}</b>
              </div>
              <Row :gutter="8" class="pb10" type="flex" align="middle" v-if="packageData.contact[0] && packageData.contact[0].contactNameStatus">
                <Col span="5" class="lable"><b>联系人：</b></Col>
                <Col span="19">{{packageData.contact[0].contact_name}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle" v-if="packageData.contact[0] && packageData.contact[0].phoneStatus">
                <Col span="5"  class="lable"><b>联系电话：</b></Col>
                <Col span="19">{{packageData.contact[0].phone}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>使用日期：</b></Col>
                <Col span="19"> {{moment(date).format('YYYY-MM-DD')}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>支付方式：</b></Col>
                <Col span="19">在线支付</Col>
              </Row>
              
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>注意事项：</b></Col>
                <Col span="19">
                  {{packageData.mattres_need_attention}}
                </Col>
              </Row>
              <div class="pd15">
                <p style="font-size:18px;">
                  <span>个人联系信息</span>
                </p>
              </div>
              <Form :label-width="80" :model="info" :rules="infoRules" ref="info">
                <FormItem label="联系人：" prop="name">
                  <Input style="width:200px;" v-model="info.name" :maxlength="20"/>
                </FormItem>
                <FormItem label="联系电话："  prop="phone">
                  <Input style="width:200px;" v-model="info.phone" :maxlength="11"/>
                </FormItem>
              </Form>
              </div>
            </Col>
          </Row>
        </div>
        <div slot="footer">
          <div  v-if="!showOrder">
            <Button type="primary" @click="handleOrderOk">提交</Button>
              <Button type="default" @click="isCheckPackage = false">取消</Button>
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
      packageData: {
        type: Object,
        default: () => {
          return {}
        }
      }
    },
    data() {
      return {
        info: {
          name: '',
          phone: ''
        },
        infoRules: {
          name: [
            { required: true, message: '请填写联系人', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '请填写联系电话', trigger: 'blur' },
            {validator: isPhone2},
          ]
        },
        options3: {
          disabledDate (date) {
            return date && date.valueOf() < Date.now() - 86400000;
          }
        },
        title: '在线选择套餐：',
        showOrder: true,
        orderLeftHeight: '0px',
        orderRightHeight: '0px',
        orderLftMiddle: '0px',
        isCheckPackage: false,
        scenicSpotDatas: [],
        // 已选中们描数据
        scenicSpotData: [],
        scenicSpotColumns: [
          {
            title: '门票名称',
            key: 'ticketName',
            align: 'center'
          },
          {
            title: '原价',
            key: 'ticketPrice',
            align: 'center',
            render: (h, params) => {
              return h('span', `￥ ${parseFloat(params.row.ticketPrice).toFixed(2)}`)
            }
          },
          {
            title: '折扣价格',
            key: 'discountPrice',
            align: 'center',
            render: (h, params) => {
              return h('span', `￥ ${params.row.discountPrice ? parseFloat(params.row.discountPrice).toFixed(2) : parseFloat(params.row.ticketPrice).toFixed(2)}`)
            }
          },
          {
            title: '操作',
            align: 'center',
            render: (h, params) => {
              return h('Button', {
                  props: {
                    type: params.row.checked ? 'primary' : 'default',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      if (!params.row.checked) {
                        params.row.checked = true
                        this.scenicSpotDatas.splice(params.index, 1, params.row)
                        this.scenicSpotData.push(params.row)
                        this.countFn()
                      }
                    }
                  }
              }, params.row.checked ? '已选择' : '选择')
            }
          },
        ],
        scenicSpotColumn: [
          {
            title: '名称',
            key: 'ticketName',
            align: 'center'
          },
          {
            title: '数量',
            key: 'num',
            align: 'center',
            render: (h, params) => {
              return h('InputNumber', {
                props: {
                  min: 1,
                  max: 1000,
                  value: params.row.num
                },
                on: {
                  'on-change': (val) => {
                    params.row.num += 1
                    this.scenicSpotData.splice(params.index, 1, params.row)
                    this.countFn()
                  }
                }
              })
            }
          },
          {
            title: '价格',
            key: 'discountPrice',
            align: 'center',
            render: (h, params) => {
              return h('span', `￥ ${params.row.discountPrice ? parseFloat(params.row.discountPrice).toFixed(2) : parseFloat(params.row.ticketPrice).toFixed(2)}`)
            }
          },
          {
            title: '操作',
            key: 'totalPrice',
            align: 'center',
            render: (h, params) => {
              return h('Button', {
                  props: {
                    type: 'default',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.scenicSpotData.splice(params.index, 1)
                      this.scenicSpotDatas.forEach((item, index) => {
                        if(item.ticketName === params.row.ticketName) {
                          item.checked = false
                        }
                      })
                      this.countFn()
                    }
                  }
              }, '删除')
            }
          },
        ],
        orderColumns: [
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
        date: '',
        loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
        type: '',
        id: '',
        currentStep: 0,
        datas:{
          total: 0,
          price: 0,
          salePrice: 0
        },
        loginAccount: ''
      }
    },
    created() {
      this.id = this.$route.query.id
      this.type = this.$route.query.type
      this.account = this.$route.query.uid
      this.loginAccount = this.loginUser ? this.loginUser.loginAccount : ''
      // 初始化获取门票列表
      this.handleInitList()
    },
    // updated () {
    //   this.changeHeight()
    // },
    
    watch: {
      // currentStep () {
      // },
      showOrder () {
        this.changeHeight()
      },
      // scenicSpotData: {
      //   handler(newName, oldName) {
      //     this.changeHeight()
      //   },
      //   deep: true
      // }
    },
    methods: {
      show () {
        console.log('打开')
        this.showOrder = true
        this.isCheckPackage = true
        // setTimeout(() => {
        //   this.changeHeight()
        // }, 500);
      },
      changeHeight () {
        let that = this
        this.$nextTick(function () {
          if (that.showOrder) {
            // let rightHeight = that.$refs['right'].offsetHeight
            // let leftHeight = that.$refs['left'].offsetHeight
            // if (rightHeight > leftHeight) {
            //   console.log('右边大')
            //   that.leftHeight = `${rightHeight}px`
            //   let leftBottom = that.$refs['leftBottom'].offsetHeight + 30
            //   that.leftHeightTop = `${rightHeight - leftBottom}px`
            // } else if (rightHeight < leftHeight) {
            //   console.log('左边大')
            //   that.rightHeight = `${leftHeight}px`
            //   let rightBottom = that.$refs['rightBottom'].offsetHeight
            //   that.rightHeightTop = `${leftHeight - rightBottom}px`
            // }
          } else {
            // 先计算左右两边的高度
            let orderRight = that.$refs['orderRight'].offsetHeight
            let orderLeft = that.$refs['orderLeft'].offsetHeight
            // 如果右边大于左边的
            if (orderRight > orderLeft) {
              // 让左边的高度等于右边的高度
              that.orderLeftHeight = `${orderRight}px`
              // 并计算出左边中间的table的最小高度，这样 价格就显示在最下方了
              // let orderLftTop = that.$refs['orderLftTop'].offsetHeight
              // let orderLftBottom = that.$refs['orderLftBottom'].offsetHeight
              // that.orderLftMiddle = `${orderRight - orderLftTop - orderLftBottom}px`
            } else if (orderRight < orderLeft) {
              that.orderRightHeight = `${orderLeft}px`
            }
          }
        })
      },
      // 初始化查询门票列表
      handleInitList () {
          this.$api.post('/member/scenicSpot/findScenicSpot', {account: this.account, pageNum: 1, pageSize: 100000}).then(response => {
              if (response.code === 200) {
                  this.scenicSpotDatas = response.data.list
                  this.scenicSpotDatas.forEach(e => {
                    e.checked = false
                    e.num = 1
                  })
              }
          })
      },
      checkOrder (data) {
        this.showOrder = false
        this.isCheckPackage = true
        console.log(data)
        this.scenicSpotData = data.checkedData
        this.date = data.date
        this.title = '订单详情：'
        this.$nextTick(() => {
          this.$refs['info'].resetFields()
        })
      },
      // 提交订单
      handleOrderOk () {
        this.$refs['info'].validate((v) => {
          if (v) {
            let scenicSpotData = []
            this.scenicSpotData.forEach(e => {
              scenicSpotData.push({
                // scenicSpotId 门票ID
                scenicSpotId: e.id,
              })
            })
            console.log(scenicSpotData)
            console.log(this.scenicSpotData)
            let data = {
              contactName: this.packageData.contact && this.packageData.contact[0] ? this.packageData.contact[0].contact_name : '',
              phone:  this.packageData.contact && this.packageData.contact[0] ? this.packageData.contact[0].phone : '',
              account: this.packageData.account,
              sellAccount: this.loginAccount,
              date: this.moment(this.date).format('YYYY-MM-DD'),
              setMealId: '',
              setMeal: this.scenicSpotData,
              checkType: '0', // 0 在线自定义套餐 1 选择固定套餐
              setMealName: '自定义套餐',
              scenicSpotData: scenicSpotData,
              type: '2',
              serviceId: this.id,
              discountPrice: this.datas.total,
              price: this.datas.price,
              payType: '0', // 0 在线支付 1 预付定金
              buyersName: this.info.name,
              buyersPhone: this.info.phone,
            }
            console.log('data', data)
            this.$emit('on-buy', {data: data, checkedData: [{setMealName: '自定义套餐'}]})
            this.isCheckPackage = false
          } else {
            this.$Message.warning('请核对表单信息！')
          }
        })
      },
      // 点击上一步
      handlePre () {
        this.currentStep -= 1
        this.changeHeight()
      },
      // 计算金额
      countFn () {
        let total = 0
        let price = 0
        let salePrice = 0
        this.datas = {
          total: 0,
          price: 0,
          salePrice: 0
        }
        this.scenicSpotData.forEach(item => {
          let currentPrice = item.discountPrice ? item.discountPrice : item.ticketPrice
          total += parseFloat(numMulti(currentPrice, item.num))
          price += parseFloat(numMulti(item.ticketPrice, item.num))
          salePrice = price - total
          console.log(salePrice)
          this.datas.total = total.toFixed(2)
          this.datas.price = price.toFixed(2)
          this.datas.salePrice = salePrice.toFixed(2)
        })
      },
      // 点击下一步
      handleNext () {
        if (this.currentStep == 0) {
          if (this.date) {
          } else {
            this.$Message.error('请选择用餐日期')
            return
          }
        }
        this.currentStep += 1
        this.changeHeight()
      },
      // 点击确定
      handleOk () {
        // 判断菜品 餐桌 就餐时间是否选中
        if (!this.date) {
          this.$Message.error('请选择使用日期')
          return
        }
        if (!this.scenicSpotData.length) {
          this.$Message.error('请选择门票')
          return
        }
        this.title = '订单详情：'
        let scenicSpotData = []
        this.scenicSpotData.forEach(e => {
          scenicSpotData.push({
            // scenicSpotId 门票ID
            scenicSpotId: e.id,
          })
        })
        let data = {
          contactName: this.packageData.contact[0].contact_name,
          phone: this.packageData.contact[0].phone,
          account: this.packageData.account,
          sellAccount: this.loginAccount,
          date: this.moment(this.date).format('YYYY-MM-DD'),
          setMealId: '',
          scenicSpotData: scenicSpotData,
          checkedData: this.scenicSpotData,
          type: '2',
          serviceId: this.id,
          discountPrice: this.datas.total,
          price: this.datas.price,
          payType: '0' // 0 在线支付 1 预付定金
        }
        this.$emit('on-save', {data: data, checkedData: [{setMealName: '自定义套餐', setMealPrice: this.datas.total, totalPrice: this.datas.price}]})
        this.isCheckPackage = false
      },
    }
  }
</script>
<style lang="scss" scoped>
.restaurant-choose-modal{
  .is-choose-title{
    line-height: 50px;
    text-align: center;
    font-size: 18px;
  }
  .is-choose-titles{
    color: #fff;
    background: #00c587;
  }
  .is-choose-title-food{
    font-size: 16px;
    color: #00c587;
    background: #efefef;
  }
}

  .service-detail-order{
    .lable{
      text-align: right;
      font-size: 16px;
    }
  }
  
</style>

<style scoped>
    .farm-group-btn {
        color: #9B9B9B;
        cursor: pointer;
        font-family: 'PingFangSC-Medium';
    }
    .farm-group-btn-active {
        color: #00c587;
        cursor: pointer;
        font-family: 'PingFangSC-Medium';
    }
</style>
