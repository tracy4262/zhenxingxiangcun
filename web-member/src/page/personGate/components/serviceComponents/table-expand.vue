<template>
  <div>
    <Row>
      <Col span="3" class="tc pr10">
        套餐内容
      </Col>
      <Col span="21">
        <Table size="small" :columns="columns" :data="row.productList"></Table>
        <div class="tr mt10">合计：￥{{parseFloat(row.setMealPrice).toFixed(2)}}</div>
      </Col>
    </Row>
    <Row class="mt10">
      <Col span="3" class="tc pr10">
        截止日期
      </Col>
      <Col span="21">
        {{row.endDate}}
      </Col>
    </Row>
    <Row class="mt10" v-if="type == 3">
      <Col span="3" class="tc pr10">
        用餐时间
      </Col>
      <Col span="21">
        {{row.diningTime}}
      </Col>
    </Row>
     <Row class="mt10" v-if="type == 3">
      <Col span="3" class="tc pr10">
        包房
      </Col>
      <Col span="21" v-if="row.selectedRoom && row.selectedRoom[0]">
        <span v-for="(item, index) in row.selectedRoom">{{item.name}}</span>
      </Col>
    </Row>
    <Row class="mt10" v-if="type == 3">
      <Col span="3" class="tc pr10">
        用餐人数
      </Col>
      <Col span="21">
        <InputNumber :min="1" v-model="row.diningNumber" @on-change="diningNumberChange"></InputNumber>
      </Col>
    </Row>
    <Row class="mt10">
      <Col span="3" class="tc pr10">
        服务费用
      </Col>
      <Col span="21">
        <span class="t-orange">现价：￥ {{parseFloat(row.setMealPrice).toFixed(2)}}</span>
        <span class="ml5 t-grey" style="text-decoration: line-through;">原价：￥ {{parseFloat(row.totalPrice).toFixed(2)}}</span>
        <span class="ml5 t-green">已优惠：￥ {{(parseFloat(row.totalPrice).toFixed(2) - parseFloat(row.setMealPrice).toFixed(2)).toFixed(2)}}</span>
      </Col>
    </Row>
    <Row class="mt10">
      <Col span="3" class="tc pr10">
        支付方式
      </Col>
      <!-- 0 在线支付 1 预付订金 -->
      <Col span="9" v-if="row.payType == 0">
        在线支付
      </Col>
       <Col span="21" v-else>
        预付订金
      </Col>
    </Row>
    <Row class="mt10" type="flex" align="middle">
      <Col span="3" class="tc pr10">
        使用日期
      </Col>
      <Col span="21">
        <div v-if="type !=4">
          <Checkbox v-model="row.checkDate"></Checkbox>
          <DatePicker v-model="row.date" @on-change="change" :options="options3" v-if="row.checkDate" type="date" style="width: 200px" :placeholder="placeholder"></DatePicker>
        </div>
        <div v-else>
          <Checkbox v-model="row.checkDate"></Checkbox>
          <!-- <DatePicker v-model="date" @on-change="daterangeChange" :options="options3" v-if="row.checkDate" type="daterange" style="width: 200px" :placeholder="placeholder"></DatePicker> -->
          <DatePicker v-model="row.date" @on-change="dateChange" :options="options3" v-if="row.checkDate" type="date" style="width: 200px" placeholder="请选择入住时间"></DatePicker>
          <DatePicker ref="userTime" v-model="row.userTime" @on-change="userTimeChange" :options="options4" v-if="row.checkDate" type="date" style="width: 200px" placeholder="请选择退房时间"></DatePicker>
        </div>
      </Col>
    </Row>
   
  </div>
</template>

<script>
export default {
  props: {
    row: Object
  },
  data () {
    return {
      columns: [{
        title: '名称',
        key: 'name'
      },
      {
        title: '单价',
        key: 'price',
        render: (h, params) => {
            return h('span',`￥ ${parseFloat(params.row.price).toFixed(2)}`)
        }
      },
      {
        title: '数量/规格',
        key: 'num'
      },
      {
        title: '小计',
        key: 'total',
        render: (h, params) => {
            return h('span',`￥ ${parseFloat(params.row.total).toFixed(2)}`)
        }
      }],
      checkDate: false,
      type: '',
      date: [],
      placeholder: '',
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
      diningNumber: 1,
    }
  },
  mounted() {
    let that = this
    this.options3 = {
          disabledDate (date) {
              let initdate = new Date(that.row.endDate);
              return (date && date.valueOf() >= initdate) || (date && date.valueOf() < Date.now() - 86400000)
          }
      }
    this.options4 = {
        disabledDate (date) {
            let initdate = new Date(that.row.endDate);
            return (date && date.valueOf() >= initdate) || (date && date.valueOf() < Date.now() - 86400000)
        }
    }
  },
  created() {
    this.type = this.$route.query.type
    if (this.type == 2) {
      this.placeholder = '请选择使用日期'
    } else if (this.type == 3) {
      this.placeholder = '请选择使用日期'
      this.row.diningNumber = 1
      this.$emit('on-diningNumber-change', this.row.diningNumber)
    } else if (this.type == 4) {
      this.placeholder = '请选住宿日期'
    }
  },
  methods: {
    diningNumberChange () {
      this.$emit('on-diningNumber-change', this.row.diningNumber)
    },
    change (e) {
      this.$emit('on-change', e)
    },
    dateChange (e) {
      let that = this
      this.options4 = {
        disabledDate (date) {
            let initdate = new Date(that.row.endDate);
            let activeDate = new Date(e);
            return (date && date.valueOf() >= initdate) || (date && date.valueOf() < activeDate)
        }
      }
      this.$emit('on-date', e)
      this.$refs['userTime'].handleFocus()
      this.$refs['userTime'].focus()
      this.row.userTime = this.moment(e).add("days", 1).format('YYYY-MM-DD')
    },
    userTimeChange (e) {
      let that = this
      this.options3 = {
          disabledDate (date) {
              let initdate = new Date(e);
              return (date && date.valueOf() >= (initdate - 86400000)) || (date && date.valueOf() < Date.now() - 86400000)
          }
      }
      this.$emit('on-userTime', e)
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
