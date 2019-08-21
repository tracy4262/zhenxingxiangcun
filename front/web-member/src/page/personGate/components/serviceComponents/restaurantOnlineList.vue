<template>
  <div>
    <Modal
        v-model="isCheckPackage" width="1100" :mask-closable="false"
        :title="title">
        <Row  class="restaurant-choose-modal" v-if="showOrder" :gutter="8">
          <Col span="14" class="pr10">
          <div style="border-radius: 4px; overflow: hidden;border:1px solid #00c587;">
            <div style="height: 598px">
              <Steps :current="currentStep" class="pb20 pl20 pt20">
                <Step title="选择用餐时间" ></Step>
                <Step title="选择包房餐桌" ></Step>
                <Step title="选择菜品" ></Step>
              </Steps>
                <div v-if="currentStep == 0">
                  <Form :label-width="120">
                    <FormItem label="用餐日期：">
                      <DatePicker v-model="dateData.date" :options="options3" type="date" style="width: 200px" placeholder="请选择用餐日期"></DatePicker>
                    </FormItem>
                    <FormItem label="用餐时间：">
                      <RadioGroup v-model="dateData.time">
                        <Radio label="早餐"></Radio>
                        <Radio label="中餐"></Radio>
                        <Radio label="晚餐"></Radio>
                      </RadioGroup>
                    </FormItem>
                    <!-- <FormItem label="用餐人数：">
                      <InputNumber :min="1" v-model="diningNumber"></InputNumber>
                    </FormItem> -->
                  </Form>
                </div>
                <div v-if="currentStep == 1" style="margin-bottom:24px;">
                  <div  ref="step2Top">
                    <Row class="pl20">
                        <Col span="24" style="line-height: 30px;">
                            <span>分类</span>
                            <span v-for="(item,index) in privateRoomDatas" :key="index" class="pl10 pr10">
                                <span @click="choosePrivateRoom(item, index)" :class="{'farm-group-btn-active': index === activePrivateRoom, 'farm-group-btn': true}">
                                    {{ item.roomName }}
                                </span>
                            </span>
                        </Col>
                    </Row>
                  </div>
                  <div class="pd5 pt15">
                    <Table :columns="privateColumns" :height="diningTableDatasHieght" :data="diningTableDatas"></Table>
                  </div>
                </div>
                <div v-if="currentStep == 2" class="dish-list-ivu" style="margin-bottom:24px;">
                  <div ref="step3Top">
                    <Row class="pl20">
                        <Col span="24" style="line-height: 30px;">
                            <span>分类</span>
                            <span v-for="(item,index) in dishDatas" :key="index" class="pl10 pr10">
                                <span @click="chooseDishData(item, index)" :class="{'farm-group-btn-active': index === activeDish, 'farm-group-btn': true}">
                                    {{ item.foodClassName }}
                                </span>
                            </span>
                        </Col>
                    </Row>
                  </div>
                  <div class="pd5" >
                    <Row class="mt20" :gutter="18" :style="{'height': dishListHeight}" style="overflow: auto;">
                      <Col span="6" v-for="(item, index) in dishList" class="pb20" :key="item">
                        <Card>
                          <div class="tc" style="">
                            <img :src="item.foodImage[0]" alt="" width="100%" height="112px">
                            <p class="pt10 ell" :title="item.foodName">{{item.foodName}}</p>
                            <p class="pt5 pb10">
                              <span v-if="item.discountPrice"  class="t-orange">¥{{item.discountPrice}}</span>
                              <span v-else  class="t-orange">¥{{item.foodPrice}}</span>
                              <span class="t-grey" style="text-decoration: line-through;" v-if="item.discountPrice">¥{{item.foodPrice}}</span>
                              <Button type="primary" size="small" :disabled="item.checked" @click="handleChooseDish(item, index)">选择</Button>
                            </p>
                          </div>
                        </Card>
                      </Col>
                    </Row>
                  </div>
                </div>
            </div>
            <div class="tr pd20" >
              <Button type="default" @click="handlePre" v-if="currentStep != 0" class="mr10">上一步</Button>
              <Button type="primary" @click="handleNext" v-if="currentStep != 2" class="mr10">下一步</Button>
            </div>
          </div>
          </Col>
          <Col span="10">
            <div style="border-radius: 4px; overflow: hidden;border:1px solid #00c587;">
              <div style="height: 544px;"> 
                <div>
                  <p class="is-choose-title is-choose-titles">已选择</p>
                  <div class="pd5">
                    <p class="is-choose-title is-choose-title-food">已选菜品</p>
                    <Table :columns="foodColumns" :height="foodData.length >= 3 ? '139' : (foodData.length <= 1 ? '95' : (foodData.length + 1) * 48) - 5" :data="foodData"></Table>
                  </div>
                  <div class="pd5">
                  <p class="is-choose-title is-choose-title-food">已选餐桌</p>
                    <Table :columns="tableColumns" :data="tableData"></Table>
                  </div>
                  <div class="pd5">
                    <p class="is-choose-title is-choose-title-food">已选时间</p>
                    <Table :columns="dateColumns" :data="dateDatas"></Table>
                  </div>
                </div>
              </div>
              <div ref="rightBottom">
                <p class="tc pt30" v-if="foodData.length">
                  <span class="t-orange" style="font-size:16px;">优惠价￥<b class="h1">{{datas.total}}</b></span>
                  <span class="t-grey ml5" >原价￥<b style="text-decoration: line-through;">{{datas.price}}</b></span>
                  <span class="t-green ml5" >省￥<b>{{datas.salePrice}}</b></span>
                </p>
                <p  style="height: 54px;" v-if="!foodData.length">&nbsp;</p>
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
                <div class="pd15"  ref="orderLftTop">
                <b style="font-size:18px;">已选菜单</b>
                </div>
                <div :style="{'min-height': orderLftMiddle}">
                  <Table :columns="orderColumns" :data="foodData"></Table>
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
                <Col span="5"  class="lable"><b>用餐时间：</b></Col>
                <Col span="19">{{dateDatas[0].time}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>用餐日期：</b></Col>
                <Col span="19"> {{moment(dateDatas[0].date).format('YYYY-MM-DD')}}</Col>
              </Row>
              <Row :gutter="8" class="pb10" type="flex" align="middle">
                <Col span="5"  class="lable"><b>用餐餐桌：</b></Col>
                <Col span="19">{{tableData[0].number}}</Col>
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
import $ from 'jquery'
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
        diningNumber: 1,
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
        diningTableDatasHieght: '0',
        dishListHeight: '0',
        isCheckPackage: false,
        orderColumns: [
          {
            title: '名称',
            key: 'foodName',
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
              return h('span', `￥ ${params.row.discountPrice ? parseFloat(numMulti(params.row.discountPrice, params.row.num)).toFixed(2) : parseFloat(numMulti(params.row.foodPrice, params.row.num)).toFixed(2)}`)
            }
          },
        ],
        foodColumns: [
          {
            title: '名称',
            key: 'foodName',
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
                    this.foodData.splice(params.index, 1, params.row)
                    console.log('数量改变了')
                    this.countFn()
                    console.log('数量改变了')
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
              return h('span', `￥ ${params.row.discountPrice ? parseFloat(params.row.discountPrice).toFixed(2) : parseFloat(params.row.foodPrice).toFixed(2)}`)
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
                      this.foodData.splice(params.index, 1)
                      this.dishList.forEach((item, index) => {
                        if(item.foodName === params.row.foodName) {
                          item.checked = false
                        }
                      })
                      this.countFn()
                      // this.changeHeight()
                    }
                  }
              }, '删除')
            }
          },
        ],
        foodData: [],
        tableColumns: [
          {
            title: '位置',
            key: 'roomName',
            align: 'center'
          },
          {
            title: '名称',
            key: 'number',
            align: 'center'
          },
          {
            title: '用餐人数',
            key: 'haveMealsNum',
            align: 'center',
            render: (h, params) => {
              if (params.row.haveMealsNum) {
                return h('InputNumber', {
                  props: {
                    min: 1,
                    max: params.row.haveMealsNum,
                    value: params.row.num
                  },
                  on: {
                    'on-change': (val) => {
                      console.log(val)
                      params.row.num = val
                      this.tableData.splice(params.index, 1, params.row)
                      console.log('数量改变了')
                      this.countFn()
                      console.log('数量改变了')
                    }
                  }
                })
              } else {
                return h('InputNumber', {
                  props: {
                    min: 1,
                    value: params.row.num
                  },
                  on: {
                    'on-change': (val) => {
                      console.log(val)
                      params.row.num = val
                      this.tableData.splice(params.index, 1, params.row)
                      this.countFn()
                    }
                  }
                })
              }
            }
          },
          {
            title: '操作',
            key: 'setMealName',
            align: 'center',
            
            render: (h, params) => {
              return h('Button', {
                  props: {
                    type: 'default',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      console.log('点击了删除')
                      this.tableData = []
                      this.diningTableDatas.forEach(e => {
                        e.checked = false
                      })
                    }
                  }
              }, '删除')
            }
          },
        ],
        tableData: [],
        dateColumns: [
          {
            title: '日期',
            key: 'date',
            align: 'center',
            render: (h, params) => {
              return h('span',`${params.row.date ? this.moment(params.row.date).format('YYYY-MM-DD') : ''}`)
            }
          },
          {
            title: '时间',
            key: 'time',
            align: 'center'
          },
          {
            title: '操作',
            key: 'setMealName',
            align: 'center',
            render: (h, params) => {
              return h('Button', {
                  props: {
                    type: 'default',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.dateDatas = []
                      this.dateData = {date: '', time: '早餐'}
                    }
                  }
              }, '删除')
            }
          },
        ],
        dateData: {date: '', time: '早餐'},
        dateDatas:[],
        activePrivateRoom: 0,
        privateRoomDatas: [
          {roomName: '不限'},
          {roomName: '大厅', location: '0', minPrice: 0}
        ],
        diningTableDatas: [],
        diningTableData: [],
        privateColumns: [
          {
            title: '所在区域',
            key: 'roomName',
            align: 'center'
          },
          {
            title: '餐桌号码',
            key: 'number',
            align: 'center'
          },
          {
            title: '可用餐人数',
            key: 'haveMealsNum',
            align: 'center',
            render: (h, params) => {
              return h('span',`${params.row.haveMealsNum ? params.row.haveMealsNum : ''}`)
            }
          },
          {
            title: '操作',
            key: 'setMealName',
            align: 'center',
            render: (h, params) => {
              return h('Button', {
                  props: {
                    type: params.row.checked ? 'primary' : 'default',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      params.row.checked = true
                      this.diningTableDatas.forEach(e => {
                        e.checked = false
                      })
                      this.privateRoomDatas.forEach(element => {
                        if (element.roomName == params.row.roomName) {
                          params.row.minPrice = element.minPrice
                        }
                      })
                      this.diningTableDatas.splice(params.index, 1, params.row)
                      this.tableData = []
                      params.row.num = params.row.haveMealsNum ? params.row.haveMealsNum : 1
                      this.tableData.push(params.row)
                    }
                  }
              }, params.row.checked ? '已选择' : '选择')
            }
          },
        ],
        activeDish: 0,
        dishDatas: [
          {
            foodClassName: '不限',
            id: ''
          }
        ],
        dishColumns: [
          {
            title: '所在区域',
            key: 'setMealName',
            align: 'center'
          },
          {
            title: '餐桌号码',
            key: 'setMealName',
            align: 'center'
          },
          {
            title: '可用餐人数',
            key: 'setMealName',
            align: 'center'
          },
          {
            title: '操作',
            key: 'setMealName',
            align: 'center',
            render: (h, params) => {
              return h('Button', {
                  props: {
                    type: 'default',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      console.log('点击了删除')
                    }
                  }
              }, '删除')
            }
          },
        ],
        dishList: [],
        dishLists: [],
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
      this.handleGetdishDatas()
      this.handleGetdishList()
      
      // 初始化获取餐桌
      this.handleGetTableDatas({
          account: this.account,
          pageNum: 1,
          pageSize: 10000,
          number: '',
          status: '0' // 0空闲中 ‘’全部 1使用中
      })
      // 初始化获取包房列表
      this.handleGetprivateRoomDatas({
            roomName: '',
            status: '',
            pageNum: 1,
            pageSize: 10000,
            account: this.account
        })
    },
    watch: {
      currentStep () {
        if (this.currentStep == 1) {
          let that = this
           setTimeout(() => {
            this.$nextTick(function () {
              if (this.diningTableDatas.length > 8 ) {
                let height = that.$refs['step2Top'].offsetHeight
                // 598 67 
                this.diningTableDatasHieght = `${598 - 87 -height}px`
              } else if (this.diningTableDatas.length == 1) {
                this.diningTableDatasHieght = '95px'
              } else {
                this.diningTableDatasHieght = `${(this.diningTableDatas.length + 1) * 48 - 5}px`
              }
              
            })
           }, 100);
        } else if (this.currentStep == 2) {
          let that = this
           setTimeout(() => {
            this.$nextTick(function () {
              let height = that.$refs['step3Top'].offsetHeight
              // 598 67 
              this.dishListHeight = `${598 - 87 -height}px`
            })
           }, 100);
        }
      },
      showOrder () {
        this.changeHeight()
      },
    },
    mounted() {
     
    },
    methods: {
      show () {
        this.showOrder = true
        this.isCheckPackage = true
        this.changeHeight()
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
      // 提交订单
      handleOrderOk () {
        console.log('提交订单')
        this.$refs['info'].validate((v) => {
          if (v) {
            let foodData = []
            this.foodData.forEach(e => {
              foodData.push({
                // foodClassId 分类 菜品
                foodClassId: e.foodClassId,
                foodId: e.id
              })
            })
            let data = {
              contactName: this.packageData.contact && this.packageData.contact[0] ? this.packageData.contact[0].contact_name : '',
              phone:  this.packageData.contact && this.packageData.contact[0] ? this.packageData.contact[0].phone : '',
              account: this.packageData.account,
              sellAccount: this.loginAccount,
              roomId: this.tableData[0].location,
              date: this.moment(this.dateDatas[0].date).format('YYYY-MM-DD'),
              time: this.dateDatas[0].time,
              setMealId: '',
              foodData: foodData,
              setMeal: [{foodData: this.foodData, tableData: this.tableData, diningNumber: this.tableData[0].num}],
              checkType: '0', // 0 在线自定义套餐 1 选择固定套餐
              setMealName: '自定义套餐',
              type: '3',
              serviceId: this.id,
              discountPrice: this.datas.total,
              price: this.datas.price,
              payType: '0', // 0 在线支付 1 预付定金
              buyersName: this.info.name,
              buyersPhone: this.info.phone,
            }
            console.log('8888')
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
        this.foodData.forEach(item => {
          let currentPrice = item.discountPrice ? item.discountPrice : item.foodPrice
          total += parseFloat(numMulti(currentPrice, item.num))
          price += parseFloat(numMulti(item.foodPrice, item.num))
          salePrice = price -total
          this.datas.total = total.toFixed(2)
          this.datas.price = price.toFixed(2)
          this.datas.salePrice = salePrice.toFixed(2)
        })
      },
      // 点击下一步
      handleNext () {
        if (this.currentStep == 0) {
          if (this.dateData.date) {
            this.dateDatas = []
            this.dateDatas.push(this.dateData)
          } else {
            this.$Message.error('请选择用餐日期')
            return
          }
        }
        if (this.currentStep == 1) {
          if (!this.tableData.length) {
            this.$Message.error('请选择就餐餐桌')
            return
          }
        }
        this.currentStep += 1
      },
      // 获取包房列表
      handleGetprivateRoomDatas (data) {
        this.$api.post('/member/restaurant/findRoom', data).then(response => {
            if (response.code === 200) {
                this.privateRoomDatas = this.privateRoomDatas.concat(response.data.list)
                
            }
        }).catch(error => {
            console.log(error)
        })
      },
       // 初始化获取餐桌
      handleGetTableDatas (data) {
        this.$api.post('/member/restaurant/findTable', data).then(response => {
            if (response.code === 200) {
                this.diningTableDatas = response.data.list
                this.diningTableDatas.forEach(e => {
                  e.haveMealsNum = Number(e.haveMealsNum)
                })
                this.diningTableData = this.diningTableDatas
            }
        }).catch(error => {
            console.log(error)
        })
      },
      // 菜品化分类
      handleGetdishDatas () {
          this.$api.post('/member/restaurant/findRestaurant', {
              account: this.account,
              pageNum: 1,
              pageSize: 10000
          }).then(response => {
              if (response.code === 200) {
                  this.dishDatas = this.dishDatas.concat(response.data.list)
              }
          }).catch(error => {
              this.$Message.error('初始化分类失败！')
          })
      },
      // 初始化 获取全部菜品
      handleGetdishList (type) {
        this.$api.post('/member/restaurant/findFood', {
          account: this.account,
          pageNum: 1,
          pageSize: 100000,
          foodName: '',
          foodClassId: type,
          status: '1'
        }).then(response => {
              console.log('res table', response)
              if (response.code === 200) {
                this.dishList = response.data.list
                this.dishList.forEach(e => {
                  e.checked = false
                  e.num = 1
                })
                this.dishLists = this.dishList
              }
          }).catch(error => {
              console.log(error)
          })
      },
      // 选择菜品分类 筛选
      chooseDishData (item, index) {
        this.activeDish = index
        this.dishList = this.dishLists
        if (item.id) {
          let arr = this.dishList.filter(element => element.foodClassName === item.foodClassName)
          this.dishList = arr
        }
      },
      // 选择菜品
      handleChooseDish (item, index) {
        item.checked = true
        this.dishList.splice(index, 1, item)
        this.foodData.push(item)
        this.countFn()
      },
      // 选择包房
      choosePrivateRoom (item, index) {
        this.activePrivateRoom = index
        this.diningTableDatas = this.diningTableData
        if (item.roomName != '不限' ) {
          let arr = this.diningTableDatas.filter(element => element.roomName === item.roomName)
          this.diningTableDatas = arr
        }
      },
      // 点击确定
      handleOk () {
        // 判断菜品 餐桌 就餐时间是否选中
        if (!this.dateDatas.length) {
          this.$Message.error('请选择就餐时间')
          return
        }
        if (!this.tableData.length) {
          this.$Message.error('请选择就餐餐桌')
          return
        }
        if (!this.foodData.length) {
          this.$Message.error('请选择就餐菜品')
          return
        }
        if (!this.tableData[0].num || this.tableData[0].num < 0) {
          this.$Message.error('请输入用餐人数')
          return
        }
        console.log(this.tableData[0].minPrice)
        console.log(this.datas.total)
        let minPrice = parseFloat(this.tableData[0].minPrice)
        if (parseFloat(this.datas.total) < minPrice) {
          this.$Message.error('已选商品总价格小于该餐桌所在区域最低消费')
          return
        }
        let foodData = []
        this.foodData.forEach(e => {
          foodData.push({
            // foodClassId 分类 菜品
            foodClassId: e.foodClassId,
            foodId: e.id
          })
        })
        let data = {
          contactName: this.packageData.contact_name,
          phone: this.packageData.phone,
          account: this.packageData.account,
          sellAccount: this.loginAccount,
          roomId: this.tableData[0].location,
          date: this.moment(this.dateDatas[0].date).format('YYYY-MM-DD'),
          time: this.dateDatas[0].time,
          setMealId: '',
          foodData: foodData,
          checkedData: this.foodData,
          dateDatas: this.dateDatas,
          tableData: this.tableData,
          setMeal: [{foodData: this.foodData, tableData: this.tableData, diningNumber: this.tableData[0].num}],
          type: '3',
          serviceId: this.id,
          discountPrice: this.datas.total,
          price: this.datas.price,
          payType: '0' // 0 在线支付 1 预付定金
        }
      this.$emit('on-save', {data: data, checkedData: [{setMealName: '自定义套餐', setMealPrice: this.datas.total, totalPrice: this.datas.price}]})
        // this.$emit('on-buy', {data: data, checkedData: [{setMealName: '自定义套餐'}]})
        this.isCheckPackage = false
        // this.title = '订单详情：'
        // this.showOrder = false
      },
      checkOrder (data) {
        this.showOrder = false
        this.isCheckPackage = true
        console.log(data)
        this.foodData = data.checkedData
        this.tableData = data.tableData
        this.dateDatas = data.dateDatas
        // this.date = data.date
        this.title = '订单详情：'
        this.$nextTick(() => {
          this.$refs['info'].resetFields()
        })
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
