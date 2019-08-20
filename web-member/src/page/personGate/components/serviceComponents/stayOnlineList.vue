<template>
  <div>
    <Modal
        v-model="isCheckPackage" width="1100" :mask-closable="false"
        :title="title">
        <Row  class="restaurant-choose-modal" v-if="showOrder" :gutter="8">
          <Col span="14"  >
            <div style="border-radius: 4px; overflow: hidden;border:1px solid #00c587;">
                <div style="height: 550px" class="pt20 pl20">
                  <Steps :current="currentStep">
                    <Step title="选择时间" ></Step>
                    <Step title="选择房间" ></Step>
                  </Steps>
                  <div v-if="currentStep == 0" class="pt30">
                    <Form :label-width="120">
<!-- <DatePicker v-model="row.date" @on-change="dateChange" :options="options3" v-if="row.checkDate" type="date" style="width: 200px" placeholder="请选择入住时间"></DatePicker> -->
<!-- <DatePicker ref="userTime" v-model="row.userTime" @on-change="userTimeChange" :options="options4" v-if="row.checkDate" type="date" style="width: 200px" placeholder="请选择退房时间"></DatePicker> -->
                      <FormItem label="入住时间：">
                        <!-- <DatePicker v-model="dates" :options="options3" type="daterange" style="width: 200px" placeholder="请选择住宿日期"></DatePicker> -->
                        <DatePicker v-model="dates.date" type="date" @on-change="dateChange" :options="options3" style="width: 200px" placeholder="请选择入住时间"></DatePicker>
                      </FormItem>
                      <FormItem label="退房时间：">
                        <DatePicker ref="userTime" v-model="dates.userTime" type="date" @on-change="userTimeChange"  :options="options4" style="width: 200px" placeholder="请选择退房时间"></DatePicker>
                      </FormItem>
                    </Form>
                  </div>
                  <div v-if="currentStep == 1" style="margin-bottom:24px;">
                    <div ref="step2Top">
                      <Row class="mt20 pl20">
                          <Col span="24" style="line-height: 30px;">
                              <span>分类</span>
                              <span v-for="(item,index) in typeList" :key="index" class="pl10 pr10">
                                  <span @click="chooseRoomType(item, index)" :class="{'farm-group-btn-active': index === activeRoom, 'farm-group-btn': true}">
                                      {{ item.roomClassName }}
                                  </span>
                              </span>
                          </Col>
                      </Row>
                    </div>
                    <div class="pd5">
                      <Table :columns="roomColumns" :height="roomDatasHigeht" :data="roomDatas"></Table>
                    </div>
                  </div>
                </div>
                <div class="tr pd20"  >
                  <Button type="default" @click="handlePre" v-if="currentStep != 0" class="mr10">上一步</Button>
                  <Button type="primary" @click="handleNext" v-if="currentStep != 1" class="mr10">下一步</Button>
                </div>
            </div>
          </Col>
          <Col span="10" >
            <div  style="border-radius: 4px; overflow: hidden;border:1px solid #00c587;">
              <div  style="height: 496px;">
                <p class="is-choose-title is-choose-titles">已选择</p>
                <div class="pd5" >
                  <p class="is-choose-title is-choose-title-food">已选房间</p>
                  <Table :columns="roomColumn" :height="roomData.length >= 5 ? '235' : (roomData.length <= 1 ? '95' : (roomData.length + 1) * 48) - 5" :data="roomData"></Table>
                </div>
                <div class="pd5" >
                  <p class="is-choose-title is-choose-title-food">已选日期</p>
                  <!-- <Table :columns="dateColumns" :data="dateDatas"></Table> -->
                  <p style="line-height:48px;color: #657180; font-size: 14px; border: 1px solid #e9eaec; border-bottom: none;">
                    <span style="width: 150px; display: inline-block;" class="tc">入住时间：</span>
                    {{dates.date ? moment(dates.date).format('YYYY-MM-DD') : ''}}
                  </p>
                  <p style="line-height:48px;color: #657180; font-size: 14px; border: 1px solid #e9eaec;">
                    <span style="width: 150px; display: inline-block;" class="tc">退房时间：</span>
                    {{dates.userTime ? moment(dates.userTime).format('YYYY-MM-DD') : ''}}
                  </p>
                </div>
              </div>
              <div>
                <p class="tc pt30" v-if="roomData.length">
                  <span class="t-orange" style="font-size:16px;">优惠价￥<b class="h1">{{datas.total}}</b></span>
                  <span class="t-grey ml5" >原价￥<b style="text-decoration: line-through;">{{datas.price}}</b></span>
                  <span class="t-green ml5" >省￥<b>{{datas.salePrice}}</b></span>
                </p>
                <p v-if="!roomData.length"  style="height: 54px;">&nbsp;</p>
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
                <b style="font-size:18px;">已选房间</b>
              </div>
              <div :style="{'min-height': orderLftMiddle}">
                <Table :columns="orderColumns" :height="roomData.length >= 5 ? '283' : (roomData.length <= 1 ? '90' : (roomData.length + 1) * 48 -5)" :data="roomData"></Table>
              </div>
              <p class="tc pt30" ref="orderLftBottom">
                <span style="font-size:16px;">合计：</span>
                <span class="t-orange" style="font-size:22px;">优惠价￥<b class="h1">{{parseFloat(datas.total).toFixed(2)}}</b></span>
                <span class="t-grey ml5" style="font-size:12px;">原价￥<b style="text-decoration: line-through;">{{parseFloat(datas.price).toFixed(2)}}</b></span>
                <span class="t-green ml5" style="font-size:12px;">省￥<b>{{parseFloat(datas.salePrice).toFixed(2)}}</b></span>
              </p>
            </div>
            </Col>
            <Col span="12">
            <div class="pd20" style="border:1px solid #e8e8e8"  ref="orderRight" :style="{'min-height': orderRightHeight}">
              <div class="pd15">
                <b style="font-size:18px;" >{{packageData.service_name}}</b>
              </div>
              <Row :gutter="8" class="pb10" type="flex" align="middle" v-if="packageData.contact[0] && packageData.contact[0].contactNameStatus">
                <Col span="5" class="lable"><b>联系人：</b></Col>
                <Col span="19">{{packageData.contact[0].contact_name}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle"  v-if="packageData.contact[0] && packageData.contact[0].phoneStatus">
                <Col span="5"  class="lable"><b>联系电话：</b></Col>
                <Col span="19">{{packageData.contact[0].phone}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>入住时间：</b></Col>
                <Col span="19"> {{dates.date ? moment(dates.date).format('YYYY-MM-DD') : ''}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>退房时间：</b></Col>
                <Col span="19"> {{dates.userTime ? moment(dates.userTime).format('YYYY-MM-DD') : ''}}</Col>
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
        options4: {
          disabledDate (date) {
            return date && date.valueOf() < Date.now() - 86400000;
          }
        },
        title: '在线选择套餐：',
        showOrder: true,
        orderLeftHeight: '0px',
        orderRightHeight: '0px',
        orderLftMiddle: '0px',
        roomDatasHigeht: '0px',
        isCheckPackage: false,
        // 已选中们描数据
        roomDatas: [],
        roomAllDatas: [],
        roomData: [],
        roomColumns: [
            {
                title: '房间名称',
                align: 'center',
                key: 'roomName'
            },
            {
                title: '房间类型',
                align: 'center',
                key: 'roomClassName'
            },
            {
                title: '房间价格',
                align: 'center',
                key: 'roomPrice',
                render: (h, params) => {
                    return h('span',`￥ ${parseFloat(params.row.roomPrice).toFixed(2)}`)
                }
            },
            {
                title: '优惠价格',
                align: 'center',
                key: 'discountPrice',
                render: (h, params) => {
                  if (params.row.discountPrice) {
                    return h('span',`￥ ${parseFloat(params.row.discountPrice).toFixed(2)}`)
                  } else {
                    return h('span',``)
                  }
                    
                }
            },
            // {
            //     title: '房间状态',
            //     align: 'center',
            //     key: 'status',
            // },
          {
              title: '操作',
              key: 'todo',
              width: 150,
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
                            this.roomDatas.splice(params.index, 1, params.row)
                            this.roomData.push(params.row)
                            this.countFn()
                          }
                        }
                      }
                  }, params.row.checked ? '已选择' : '选择')
                }
            }
        ],
        roomColumn: [
          {
            title: '名称',
            key: 'roomName',
            align: 'center'
          },
          {
            title: '价格',
            key: 'discountPrice',
            align: 'center',
            render: (h, params) => {
              return h('span', `￥ ${params.row.discountPrice ? parseFloat(params.row.discountPrice).toFixed(2) : parseFloat(params.row.roomPrice).toFixed(2)}`)
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
                      this.roomData.splice(params.index, 1)
                      this.roomDatas.forEach((item, index) => {
                        if(item.roomName === params.row.roomName) {
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
            key: 'roomName',
            align: 'center'
          },
          {
            title: '优惠价格',
            key: 'discountPrice',
            align: 'center',
            render: (h, params) => {
              return h('span', `￥ ${params.row.discountPrice ? parseFloat(params.row.discountPrice).toFixed(2) : parseFloat(params.row.roomPrice).toFixed(2)}`)
            }
          },
          {
            title: '价格',
            key: 'roomPrice',
            align: 'center',
            render: (h, params) => {
              return h('span', `￥ ${parseFloat(params.row.roomPrice).toFixed(2)}`)
            }
          },
        ],
        dates: {},
        // 房间分类
        typeList:[],
        roomClassName: '',
        activeRoom: 0,
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
      // 获取房间分类
      this.handleTypeList()
      // 初始化查询房间列表
      this.handleInitList()
    },
    watch: {
      showOrder () {
        this.changeHeight()
      },
      currentStep () {
        // this.changeHeight()
        if (this.currentStep == 1) {
          let that = this
           setTimeout(() => {
            this.$nextTick(function () {
              if (this.roomDatas.length > 8 ) {
                let height = that.$refs['step2Top'].offsetHeight
                // 550 67 
                this.roomDatasHigeht = `${550 - 87 -height}px`
              } else if (this.roomDatas.length == 1) {
                this.roomDatasHigeht = '95px'
              } else {
                this.roomDatasHigeht = `${(this.roomDatas.length + 1) * 48 - 5}px`
              }
            })
           }, 100);
        }
      },
    },
    methods: {
      // 日期发生变化，改变日期的可选择区域
      dateChange (e) {
        let that = this
        this.options4 = {
          disabledDate (date) {
              let activeDate = new Date(e);
              return date && date.valueOf() < activeDate
          }
        }
        this.$refs['userTime'].handleFocus()
        this.$refs['userTime'].focus()
        this.dates.userTime = this.moment(e).add("days", 1).format('YYYY-MM-DD')
      },
      userTimeChange (e) {
        let that = this
        this.options3 = {
            disabledDate (date) {
                let initdate = new Date(e);
                return (date && date.valueOf() >= (initdate - 86400000)) || (date && date.valueOf() < Date.now() - 86400000)
            }
        }
      },
      show () {
        this.showOrder = true
        this.isCheckPackage = true
      },
      changeHeight () {
        let that = this
        this.$nextTick(function () {
          if (!that.showOrder) {
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
          }
        })
      },
      // 获取房间分类
      handleTypeList () {
          this.$api.post('/member/accommodation/findRoomClass', 
          {account: this.account, pageNum: 1, pageSize: 100000})
          .then(response => {
              if (response.code === 200) {
                  let arr =  [{roomClassName: '所有分类', id: ''}]
                  this.typeList = arr.concat(response.data.list)
              }
          })
      },
      // 初始化查询房间列表
      handleInitList () {
        // 0 空闲中 1 使用中 ‘’ 全部 roomClassName 传中文
          this.$api.post('/member/accommodation/findRoomList', 
          {account: this.account, pageNum: 1, pageSize: 100000, status:'0', roomClassName: this.roomClassName})
          .then(response => {
              if (response.code === 200) {
                  this.roomDatas = response.data.list
                  this.roomDatas.forEach(e => {
                    e.checked = false
                    e.num = 1
                  })
                  this.roomAllDatas = this.roomDatas
              }
          })
      },
      // 提交订单
      handleOrderOk () {
        this.$refs['info'].validate((v) => {
          if (v) {
            let roomData = []
            this.roomData.forEach(e => {
              roomData.push({
                // roomClassId 房间分类id
                roomClassId: e.roomClassId,
                roomId: e.id
              })
            })
            let data = {
              contactName: this.packageData.contact && this.packageData.contact[0] ? this.packageData.contact[0].contact_name : '',
              phone:  this.packageData.contact && this.packageData.contact[0] ? this.packageData.contact[0].phone : '',
              account: this.packageData.account,
              sellAccount: this.loginAccount,
              date: this.moment(this.dates.date).format('YYYY-MM-DD'),
              userTime: this.moment(this.dates.userTime).format('YYYY-MM-DD'),
              setMealId: '',
              setMealName: '自定义套餐',
              setMeal: this.roomData,
              roomData: roomData,
              type: '4',
              checkType: '0', // 0 在线自定义套餐 1 选择固定套餐
              serviceId: this.id,
              discountPrice: this.datas.total,
              price: this.datas.price,
              buyersName: this.info.name,
              buyersPhone: this.info.phone,
              payType: '0' // 0 在线支付 1 预付定金
            }
            console.log(data)
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
        console.log('thisthis.scenicSpotData', this.roomData)
        this.roomData.forEach(item => {
          let currentPrice = item.discountPrice ? item.discountPrice : item.roomPrice
          total += parseFloat(numMulti(currentPrice, item.num))
          price += parseFloat(numMulti(item.roomPrice, item.num))
          salePrice = price -total
          this.datas.total = total.toFixed(2)
          this.datas.price = price.toFixed(2)
          this.datas.salePrice = salePrice.toFixed(2)
        })
      },
      // 点击下一步
      handleNext () {
        if (this.currentStep == 0) {
          if (this.dates.date && this.dates.userTime) {
          } else {
            this.$Message.error('请选择用餐日期')
            return
          }
        }
        this.currentStep += 1
      },
      checkOrder (data) {
        this.showOrder = false
        this.isCheckPackage = true
        console.log(data)
        this.roomData = data.setMeal
        this.dates = data.dates
        this.title = '订单详情：'
        this.$nextTick(() => {
          this.$refs['info'].resetFields()
        })
      },
      // 点击确定
      handleOk () {
        // 判断菜品 餐桌 就餐时间是否选中
        if (!this.dates.date) {
          this.$Message.error('请选入住时间')
          return
        }
        if (!this.dates.userTime) {
          this.$Message.error('请选退房时间')
          return
        }
        if (!this.roomData.length) {
          this.$Message.error('请选择房间')
          return
        }
        // this.title = '订单详情：'
        // this.showOrder = false
        let roomData = []
        this.roomData.forEach(e => {
          roomData.push({
            // roomClassId 房间分类id
            roomClassId: e.roomClassId,
            roomId: e.id
          })
        })
        let data = {
          contactName: this.packageData.contact && this.packageData.contact[0] ? this.packageData.contact[0].contact_name : '',
          phone:  this.packageData.contact && this.packageData.contact[0] ? this.packageData.contact[0].phone : '',
          account: this.packageData.account,
          sellAccount: this.loginAccount,
          date: this.moment(this.dates.date).format('YYYY-MM-DD'),
          userTime: this.moment(this.dates.userTime).format('YYYY-MM-DD'),
          setMealId: '',
          roomData: roomData,
          setMeal: this.roomData,
          dates: this.dates,
          type: '4',
          serviceId: this.id,
          discountPrice: this.datas.total,
          price: this.datas.price,
          buyersName: this.info.name,
          buyersPhone: this.info.phone,
          payType: '0' // 0 在线支付 1 预付定金
        }
        this.$emit('on-save', {data: data, checkedData: [{setMealName: '自定义套餐', setMealPrice: this.datas.total, totalPrice: this.datas.price}]})
        this.isCheckPackage = false
      },
      chooseRoomType (item, index) {
        this.activeRoom = index
        this.roomDatas = this.roomAllDatas
        console.log(this.roomDatas)
        if (item.id) {
          let arr = this.roomDatas.filter(element => element.roomClassName === item.roomClassName)
          this.roomDatas = arr
        }
        // if (item.id) {
        //   this.roomClassName = item.roomClassName
        // } else {
        //   this.roomClassName = ''
        // }
      }
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
