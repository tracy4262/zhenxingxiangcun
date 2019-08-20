<template>
  <div style="background: #F9F9F9;">
    <top :address="false" />
    <add-head :title="titles" :toLinks="links">
    </add-head>
    <section class="layouts">
      <div class="pd20 mt20 bg-white">
        <Row>
          <Col span="7" class="pr20">
            <set-meal ref="setMeal" :data="setMealData" @on-get-data="handleGetMealData" @on-checked="handleOnChecked"></set-meal>
          </Col>
          <Col span="17">
            <Title title="已选产品" class="mb20"></Title>
            <Table :columns="checkCol" :data="checkData"></Table>
            
            <Title title="服务信息" class="mt30"></Title>
            <Form ref="setMealName" :model="form" :label-width="80" class="mt20 mb40" :rules="rulesInline">
              <FormItem label="套餐名称"  prop="setMealName">
                <Input v-model="form.setMealName" placeholder="" style="width: 200px" :maxlength="20"></Input>
              </FormItem>

            <Title title="服务费用" class="mt30"></Title>
              <Row class="mt20 mb40">
                <Col span="10">
                  <FormItem label="销售方案">
                    <Select v-model="form.promotionPlan" style="width:200px">
                      <Option value="0">促销</Option>
                      <Option value="1">打折</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="10">
                  <FormItem label="套餐价格"  prop="setMealPrice">
                    <InputNumber :max="totalPrice" :min="0" v-model="form.setMealPrice" style="width: 200px"></InputNumber>
                  </FormItem>
                </Col>
              </Row>

            <Title title="支付方式" class="mt30"></Title>
              <Row class="mt20 mb40">
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
                    <InputNumber :max="form.setMealPrice" :min="0" v-model="form.money" style="width: 200px"></InputNumber>
                  </FormItem>
                </Col>
              </Row>

            <Title title="截止日期" class="mt30"></Title>
              <FormItem label="截止日期" class="mt20 mb40" prop="endDate">
                <DatePicker :value="form.endDate" :options="options3" type="date" @on-change="handleDateChange" style="width: 200px"></DatePicker>
              </FormItem>
            </Form>

            <Row type="flex" align="middle" style="background: #F3F3F3;" class="pd10 tr">
              <Col span="16">
                总价： <span class="h5 t-grey d">￥{{parseFloat(totalPrice).toFixed(2)}}</span>
                <span class="ml20">套餐价：<span class="h5 t-orange ">￥{{form.setMealPrice == null ? parseFloat(0).toFixed(2) : parseFloat(form.setMealPrice).toFixed(2)}}</span></span>
              </Col>
              <Col span="6" offset="2">
                <a :href="`/stayAddService/step3?id=${this.id}`">
                  <Button type="default" size="large">取消</Button>
                </a>
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
      titles: '',
      links: '/stayAddService/step3',
      id:'',
      activeId: '',
      setMealData: [],
      setMealDatas: [],
      checkCol: [
        {
          title: '名称',
          key: 'name'
        }, {
          title: '价格',
          key: 'price',
          render: (h, params) => {
            return h('span',`￥ ${parseFloat(params.row.price).toFixed(2)}`)
          }
        }, {
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
                  console.log(this.checkData[params.index].total)
                  this.countFn()
                }
              }
            })
          }
        },{
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
      form: {
        setMealName: '', // 套餐名称
        promotionPlan: '0', //促销方案
        setMealPrice: null, //套餐价格
        payType: '0', //支付方式
        money: null, // 预付金格
        endDate: '' //截止日期
      },
      totalPrice: 0,
      account: '',
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      rulesInline: {
        setMealName: [ 
          {required: true,  message: '请填写套餐名称',trigger: 'blur' }
        ],
        setMealPrice: [ 
          { required: true, message: '请填写套餐价格', type: 'number', trigger: 'change' },
          {validator: isMoney3, trigger: 'change' ,message: '请填写最多两位小数',}
        ],
        money: [ 
          {required: true, message: '请填写预付金额', type: 'number', trigger: 'change' },
          {validator: isMoney3, trigger: 'change', message: '请填写最多两位小数'}
        ],        
        endDate: [
            { required: true, message: '请选择截止日期', trigger: 'change' }
        ]
      },
      options3: {
          disabledDate (date) {
              return date && date.valueOf() < Date.now() - 86400000;
          }
      },
    }
  },
  created() {
    this.id = this.$route.query.id
    this.account = this.loginUser.loginAccount
    if (this.$route.query.activeId) {
      this.activeId = this.$route.query.activeId
    }                                       
    if (this.id) {
      this.links = `/stayAddService/step3?id=${this.id}`
    }
    if (this.activeId) {
      this.titles = '编辑套餐'
    } else {
      this.titles = '添加套餐'
    }
     // // 初始化获取数据
    this.$api.post('/member/fishing/findProductService', {account: this.account, setMealId: this.activeId, type: '4', fishServiceId: this.id, status: '0'}).then(response => {
      if (response.code === 200) {
        this.setMealData = response.data[0].leftProduct
        this.setMealDatas = response.data[0].leftProduct
        this.setMealDatas.forEach(e => {
          e.total = parseFloat(e.roomPrice).toFixed(2)
          e.managementId = e.id
          e.name = e.roomName
          e.account = this.account
          e.price =  parseFloat(e.roomPrice).toFixed(2)
          e.num = 1
          e.parentId = e.roomClassId
        })
        this.setMealData.forEach(e => {
          e.total = parseFloat(e.roomPrice).toFixed(2)
          e.managementId = e.id
          e.name = e.roomName
          e.account = this.account
          e.price = parseFloat(e.roomPrice).toFixed(2)
          e.num = 1
          e.parentId = e.roomClassId
          e.roomClassName = e.roomClassName
          e.roomClassId = e.roomClassId
        })
        if (this.activeId) {
          this.form = response.data[0]
          this.form.setMealName = response.data[0].setMealName
          this.form.promotionPlan = response.data[0].promotionPlan
          this.form.setMealPrice = Number(response.data[0].setMealPrice)
          this.form.payType = response.data[0].payType
          this.form.money = Number(response.data[0].money)
          this.form.endDate = response.data[0].endDate
          this.$refs['setMeal'].handleInit(response.data[0].selectedProduct)
          this.totalPrice = Number(response.data[0].totalPrice)
        }
      }
    })
  },
  methods: {
    // 条件筛选
    handleOnChecked (id){
      console.log(id)
      if (id == -1) {
        this.setMealData = this.setMealDatas
      } else {
        let arr = []
        this.setMealDatas.forEach(e => {
          if (e.roomClassId == id) {
            arr.push(e)
          }
        })
        this.setMealData = arr
      }
    },
    // 左侧选择之后的回调，返回已选中数据
    handleGetMealData (data) {
      this.checkData = data
      console.log(data)
      this.countFn()
    },
    countFn () {
      let total = 0
      this.checkData.forEach(item => {
        if (item.management_id) {
          item.managementId = item.management_id
        }
        total += parseFloat(item.total)
        this.totalPrice = Number(total.toFixed(2))
      })
    },
    // 保存 // 0 垂钓 1采摘 2景区 3餐饮 4住宿
    handleSave () {
      if (this.totalPrice < this.form.setMealPrice) {
        this.$Message.error('套餐价格不能大于总价')
        return
      } else if (this.form.money > this.form.setMealPrice) {
        this.$Message.error('预付金额不能大于套餐价格')
        return
      }
      this.$refs['setMealName'].validate((valid) => {
          if (valid) {
            let list = Object.assign({account: this.account, type: '4', fishServiceId: this.id,}, this.form)
            // flag 0保存，1，更新
            if (this.activeId) {
              list.setMealId = this.activeId
              list.flag = '1'
            } else {
              list.flag = '0'
            }
            list.totalPrice = this.totalPrice
            list.selectedProduct = this.checkData
            this.$api.post('/member/fishing/saveProduct', list).then(response => {
              if (response.code === 200) {
                this.$Message.success('保存成功')
                this.$router.push(`/stayAddService/step3?id=${this.id}`)
              } else {
                this.$Message.error('保存失败')
              }
            })
          } else {
            this.$Message.error('请核对表单信息')
          }
      })
    },
    handleDateChange (date) {
      this.form.endDate = date
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
