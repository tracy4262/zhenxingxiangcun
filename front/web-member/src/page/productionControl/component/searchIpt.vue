<template>
  <Row style="padding: 40px 32px 26px">
    <Col span="8">
      <div>
        <label for="" class="mr10">生产序号</label>
        <Select v-model="form.productNumber" style="width:200px" clearable>
          <Option v-for="item in productNumbers" :value="item.serialNumber" :key="item.id">{{ item.serialNumber }}</Option>
        </Select>
      </div>
    </Col>
    <Col span="8" v-if="type === '1'">
      <label for="" class="mr10">品种名称</label>
      <Select v-model="form.productNumber" style="width:200px">
        <Option v-for="item in productNumbers" :value="item.serialNumber" :key="item.id">{{ item.varietyName }}</Option>
      </Select>
    </Col>
    <Col span="8" v-if="type === '2'">
      <label for="" class="mr10">产品名称</label> 
       <Select v-model="form.productNumber" style="width:200px" clearable>
        <Option v-for="item in productNumbers" :value="item.serialNumber" :key="item.id">{{ item.productName }}</Option>
      </Select>
    </Col>
    <Col span="8">
      <Button type="primary" @click="onSearch">查询</Button>
    </Col>
  </Row>
</template>

<script>
export default {
  props: {
    type: { // 1 生产计划 2 产量测算
      type: String,
      default:'1'
    }
  },
  data () {
    return {
      form: {
        productNumber: '', // 生产序号 都绑定的是 生产序号
        varietyName: '', // 品种名称 都绑定的是 生产序号
        productName: '', // 产品名称 都绑定的是 生产序号
      },
      productNumbers: []
    }
  },
  methods: {
    init () {
      if (this.type === '1') { // 生产计划
        let list = {}
        list.wikiId = this.$route.query.id
        list.yearId = this.$route.query.yearId
        list.account = this.$user.loginAccount
        this.$api.post('/shop/plant/findPlantProductionNumber', list).then(response => {
          if (response.code === 200) {
            this.productNumbers = response.data
          }
        })
      } else { // 产量测算
        let list = {}
        list.wikiId = this.$route.query.id
        list.yearId = this.$route.query.yearId
        list.account = this.$user.loginAccount
        this.$api.post('/shop/plant/findProductionBaseOfOutput', list).then(response => {
          if (response.code === 200) {
            this.productNumbers = response.data
          }
        })
      }
    },
    onSearch () {
      this.$emit('on-search', this.form)
    },
  },
}
</script>
<style scoped>
</style>
