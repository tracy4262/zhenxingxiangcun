<template>
  <div style="background: #F9F9F9;">
    <top :address="false" />
    <add-head :title="titles" :toLinks="links">
    </add-head>
    <section class="layouts">
      <div class="pd20 mt20 bg-white">
        <Row>
          <Col span="7" class="pr20">
            <set-meal ref="setMeal" :data="setMealData" @on-get-data="handleGetMealData"></set-meal>
          </Col>
          <Col span="17">
            <Title title="已选产品" class="mb20"></Title>
            <Table :columns="checkCol" :data="checkData"></Table>
            
            <Title title="选择包房" class="mb20 mt30"></Title>
            <Table :columns="roomCol" :data="roomData"></Table>
           
            <Title title="服务信息" class="mb20 mt30"></Title>
            <Form ref="form" :model="form" :label-width="80" class="mt20 mb40" :rules="ruleInline">
              <FormItem label="套餐名称" prop="name">
                <Input v-model="form.name" placeholder="" style="width: 200px" />
              </FormItem>
            <!-- </Form> -->

            <Title title="服务费用" class="mt30"></Title>
            <!-- <Form :model="form" :label-width="80" class="mt20 mb40"> -->
              <Row>
                <Col span="10">
                  <FormItem label="销售方案">
                    <Select v-model="form.plan" style="width:200px">
                      <Option value="0">促销</Option>
                      <Option value="1">打折</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="10">
                  <FormItem label="套餐价格" prop="price">
                    <InputNumber :max="total" :min="0" v-model="form.price" style="width: 200px"></InputNumber>
                  </FormItem>
                </Col>
              </Row>
            <!-- </Form> -->

            <Title title="支付方式" class="mt30"></Title>
            <!-- <Form :model="form" :label-width="80" class="mt20 mb40"> -->
              <Row>
                <Col span="10">
                  <FormItem label="支付方式">
                    <Select v-model="form.payType" style="width:200px">
                      <Option value="0">在线支付</Option>
                      <Option value="1">预付订金</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="10" v-if="form.payType != '0'">
                  <FormItem label="预付金额" prop="money">
                    <InputNumber :max="form.price" :min="0" v-model="form.money" style="width: 200px"></InputNumber>
                  </FormItem>
                </Col>
              </Row>
            <!-- </Form> -->

            <Title title="截止日期" class="mt30"></Title>
            <!-- <Form :model="form" :label-width="80" class="mt20 mb40"> -->
            <FormItem label="截止日期" prop="date">
                <DatePicker :value="form.date" type="date" :options="options3" @on-change="handleDateChange" style="width: 200px"></DatePicker>
              </FormItem>
            </Form>

            <Title title="用餐时间" class="mt30"></Title>
            <RadioGroup v-model="form.time" class="mt20 mb40">
              <Radio label="早餐"></Radio>
              <Radio label="中餐"></Radio>
              <Radio label="晚餐"></Radio>
            </RadioGroup>

            <Row type="flex" align="middle" style="background: #F3F3F3;" class="pd10 tr mb30">
              <Col span="16">
                总价： <span class="h5 t-grey d">￥{{parseFloat(total).toFixed(2)}}</span>
                <span class="ml20">套餐价：<span class="h5 t-orange ">￥{{form.price == null ? parseFloat(0).toFixed(2) : parseFloat(form.price).toFixed(2)}}</span></span>
              </Col>
              <Col span="6" offset="2">
                <Button type="default" size="large" @click="cancel">取消</Button>
                <Button type="primary" size="large" class="ml20" :disabled="!checkData.length ? true : false" @click="handleSave">确定</Button>
              </Col>
            </Row>
          </Col>
        </Row>
      </div>
    </section>
  </div>
</template>

<script>
import {numAdd, numMulti} from '~utils/utils'
import {isMoney3 } from '~utils/validate'
import top from '../../../../top'
import addHead from '../head'
import setMeal from './list'
import Title from '~auth/components/title'
export default {
  components: {
    top,
    addHead,
    setMeal,
    Title
  },
  data () {
    return {
      titles: '添加套餐',
      links: '/scenicSpotAddService/step3',
      id:'',
      setMealData: [],
      checkCol: [
        {
          title: '名称',
          key: 'name'
        },
        {
          title: '价格',
          key: 'price',
          render: (h, params) => {
              return h('span',`￥ ${params.row.price}`)
          }
        },
        {
          title: '数量',
          key: 'num',
          render: (h, params) => {
            return h('InputNumber', {
              props: {
                min: 1,
                max: 1000,
                value: params.row.num
              },
              on: {
                'on-change': (val) => {
                  this.checkData[params.index].num = val
                  this.checkData[params.index].total = parseFloat((numMulti(params.row.price, val)).toFixed(2))
                  this.countFn()
                  this.roomData.forEach(item => {
                    if(this.total > parseFloat(item.price)) {
                      item.disabled = false
                    } else {
                      item.disabled = true
                    }
                    if (item.disabled) {
                      item.checked = false
                    }
                  })
                }
              }
            })
          }
        },
        {
          title: '操作',
          key: 'action',
          width: 90,
          align: 'center',
          render: (h, params) => {
            return h('Button', {
              props: {
                type: 'default',
                size: 'small',
                long: true
              },
              on: {
                click: () => {
                  this.checkData.splice(params.index, 1)
                  this.setMealData.forEach((item, index) => {
                    if(item.name === params.row.name) {
                      item.checked = false
                    }
                  })
                  this.countFn()
                  if (this.checkData.length === 0) this.total = 0
                }
              }
          }, '删除')}
        }
      ],
      checkData: [],
      roomCol: [
        {
          title: '包房名称',
          key: 'name'
        },
        {
          title: '最低消费',
          key: 'price',
          render: (h, params) => {
              return h('span',`￥ ${params.row.price}`)
          }
        },
        {
          title: '操作',
          key: 'action',
          width: 90,
          align: 'center',
          render: (h, params) => {
            return h('Button', {
              props: {
                type: params.row.checked ? 'primary' : 'default',
                disabled: this.total >= parseFloat(params.row.price) ? false : true,
                size: 'small',
                long: true
              },
              on: {
                click: () => {
                  this.roomData.forEach(item => {
                    item.checked = false
                  })
                  this.roomData[params.index].checked = true
                }
              }
          }, params.row.checked ? '已选择' : '选择')}
        }
      ],
      roomData: [],
      form: {
        name: '',
        plan: '0',
        price: null,
        payType: '0',
        money: null,
        date: '',
        time: '早餐'
      },
      ruleInline: {
        name: [
            { required: true, message: '请填写套餐名称', trigger: 'blur' }
        ],
        price: [
            { required: true, type: 'number', message: '请填写套餐价格', trigger: 'change' },
            {validator: isMoney3, trigger: 'change' ,message: '请填写最多两位小数',}
        ],
        money: [
            { required: true, type: 'number', message: '请填写预付金额', trigger: 'change' },
            {validator: isMoney3, trigger: 'change', message: '请填写最多两位小数'}
        ],
        date: [
            { required: true, message: '请选择截止日期', trigger: 'change' }
        ]
      },
      total: 0,
      loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      options3: {
          disabledDate (date) {
              return date && date.valueOf() < Date.now() - 86400000;
          }
      },
    }
  },
  created () {
    this.id = this.$route.query.id
    if (this.id) {
      this.links = `/scenicSpotAddService/step3?id=${this.id}`
    }
    if (this.$route.query.mealId !== undefined) {
      this.titles = '编辑套餐'
    }
    this.init()
  },
  methods: {
    init () {
      this.$api.post('/member/fishing/findProductService', {
          account: this.loginuserinfo.loginAccount,
          pageNum: 1,
          type: '3',
          fishServiceId: this.$route.query.id,
          status: 1,
          roomStatus: 0,
          setMealId: this.$route.query.mealId === undefined ? '' : this.$route.query.mealId
      }).then(response => {
          console.log('resp1', response)
          if (response.code === 200) {
            // 加载左侧分类数据
            response.data[0].leftProduct.forEach(element => {
              this.setMealData.push({
                id: element.id,
                name: element.foodName,
                price: parseFloat(element.foodPrice).toFixed(2),
                num: 1,
                total: parseFloat(element.foodPrice).toFixed(2),
                foodClassId: element.foodClassId,
                foodClassName: element.foodClassName,
                checked: element.checked
              })
            })
            // 加载包房数据
            response.data[0].selectedRoom.forEach(element => {
              this.roomData.push({
                id: element.id,
                name: element.roomName,
                price: parseFloat(element.minPrice).toFixed(2),
                checked: element.checked
              })
            })
            // 若是编辑 则将表单数据回显
            if (response.data[0].setMealName !== undefined) {
              this.form.name = response.data[0].setMealName
              this.form.plan = response.data[0].promotionPlan
              this.form.price = parseFloat(response.data[0].setMealPrice)
              this.form.payType = response.data[0].payType
              this.form.money = parseFloat(response.data[0].money)
              this.form.date = response.data[0].endDate
              this.total = parseFloat(response.data[0].totalPrice)
              this.form.time = response.data[0].diningTime
              // 回显已选产品
              response.data[0].selectedProduct.forEach(element => {
                this.checkData.push({
                  checked: true,
                  foodClassId: element.parent_id,
                  foodClassName: element.className,
                  id: element.id,
                  name: element.name,
                  num: element.num,
                  price: parseFloat(element.price).toFixed(2),
                  total: parseFloat(element.total).toFixed(2)
                })
              })
              // 子组件中已选产品的回显
              this.$refs.setMeal.checkData = this.checkData
            }
          }
      }).catch(error => {
          this.$Message.error('查询详情失败！')
      })
    },
    handleGetMealData (data) {
      console.log('datas', data)
      this.checkData = data
      this.countFn()
    },
    countFn () {
      let total = 0
      this.checkData.forEach(item => {
        total += parseFloat(item.total)
        this.total = Number(total.toFixed(2))
      })
    },
    handleSave () {
      if (this.total < this.form.price) {
        this.$Message.error('套餐价格不能大于总价')
        return
      } else if (this.form.money > this.form.price) {
        this.$Message.error('预付金额不能大于套餐价格')
        return
      }
      this.$refs['form'].validate((valid) => {
          if (valid) {
            let data = {
              flag: this.$route.query.mealId === undefined ? '0' : '1', // 新增0 更新1
              setMealId: this.$route.query.mealId === undefined ? null : this.$route.query.mealId,
              type: '3',
              fishServiceId: this.$route.query.id,
              account: this.loginuserinfo.loginAccount,
              setMealName: this.form.name,
              promotionPlan: this.form.plan,
              setMealPrice: this.form.price,
              payType: this.form.payType,
              money: this.form.money,
              endDate: this.form.date,
              diningTime: this.form.time,
              totalPrice: this.total,
              selectedProduct: [],
              selectedRoom: []
            }
            console.log('data', data)
            this.checkData.forEach(element => {
              data.selectedProduct.push({
                parentId: element.foodClassId,
                name: element.name,
                price: element.price,
                num: element.num,
                total: element.total,
                managementId: element.id
              })
            })
            this.roomData.forEach(element => {
              if (element.checked) {
                data.selectedRoom.push({
                  // parent_id: '分类id 没有就不传',
                  name: element.name,
                  price: element.price,
                  // num:数量,
                  // total:总价,
                  managementId: element.id
                })
              }
            })
            this.$api.post('/member/fishing/saveProduct', data).then(response => {
              console.log('resp', response)
              if (response.code === 200) {
                this.$Message.success('添加套餐成功！')
                this.$router.push({
                  path: '/restaurantAddService/step3',
                  query: {
                    id: this.$route.query.id
                  }
                })
              }
            }).catch(error => {
                this.$Message.error('添加套餐失败！')
            })
          } else {
            this.$Message.error('请核对输入信息!')
          }
      })
    },
    cancel () {
      this.$router.push({
        path: '/restaurantAddService/step3',
        query: {
          id: this.$route.query.id
        }
      })
    },
    handleDateChange (date) {
      this.form.date = date
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
