<template>
<div class="pd15">
  <Row>
    <Col class="mb20 tc">
        <label class="mr10">入库时生成入库单</label>
         <RadioGroup v-model="inBill">
            <Radio :label="1">是</Radio>
            <Radio :label="0">否</Radio>
         </RadioGroup >
    </Col>
    <Col class="mb40 tc">
        <label class="mr10">出库时生成出库单</label>
        <RadioGroup v-model="outBill">
            <Radio :label="1">是</Radio>
            <Radio :label="0">否</Radio>
         </RadioGroup >
    </Col>
    <Col class="tc">
      <Button type="primary" @click="saveBill">确定</Button>
    </Col>
  </Row>
</div>
  
</template>

<script>
export default {
  data () {
    return {
      inBill: 1,
      outBill: 1,
      id: ''
    }
  },
  created () {
    this.initBill()
  },
  methods: {
    initBill () {
      this.$api.post('/shop/inventory/basicSetting/billFind', {
        account: this.$user.loginAccount
      }).then(response => {
        console.log('initBill response', response)
        // 没保存过第一次进来会有 null 的情况
        if (response.code === 200 && response.data !== null) {
          this.inBill = response.data.inBill
          this.outBill = response.data.outBill
          this.id = response.data.id
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    saveBill () {
      this.$api.post('/shop/inventory/basicSetting/billSave', {
        account: this.$user.loginAccount,
        inBill: this.inBill,
        outBill: this.outBill,
        id: this.id
      }).then(response => {
        console.log('res', response)
        if (response.code === 200) {
          this.$Message.success('保存成功！')
          this.initBill()
        } else if (response.code === 400) {
          this.$Message.info('自定义分类名称已存在！')
        } else {
          this.$Message.error('服务器异常！')
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    }
  }
}
</script>

<style lang="scss">

</style>
