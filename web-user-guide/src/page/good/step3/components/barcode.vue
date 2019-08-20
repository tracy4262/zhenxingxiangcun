<template>
  <div class="pt30 pl10 pr10">
    <Form ref="data" :model="data" label-position="left" :label-width="150">
      <Row :gutter="32">
        <Col span="12">
          <Form-item prop="barcodeName" label="国际条形码名称">
            <Input v-model="data.barcodeName" :maxlength="200"></Input>
          </Form-item>
        </Col>
        <Col span="12">
          <Form-item prop="barcodeInfo" label="请输入国际条形码信息">
            <Input v-model="data.barcodeInfo" :maxlength="200"></Input>
          </Form-item>
        </Col>
      </Row>
      <view-panel title="添加自定义字段" :data="data.customData" @on-data="handleGetSafeForm" @on-add="handleAddBtn"></view-panel>
    </Form>
    <!-- 添加面板 -->
    <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
  </div>
</template>
<script>
import addPanel from '../../../goods/components/vui-form-control/add-panel'
import viewPanel from '../../../goods/components/vui-form-control/view-panel'
export default {
  components: {
    addPanel,
    viewPanel
  },
  data() {
    return {
      data: {
        barcodeName: '', // 国际条形码名称
        barcodeInfo: '', // 请输入国际条形码信息
        customData: [], // 自定义字段
      }
    }
  },
  methods: {
    // 添加组件
    handleControlBtn(data) {
      this.data.customData.push(data)
    },
    handleAddBtn() {
      this.$refs.addPanel.showAddPanel = true
    },
    // 取商品安全数据
    handleGetSafeForm(data) {
      console.log(data)
    },
    getData(val) {
      this.data = Object.assign(this.data, val)
    },
    handleSubmit() {
      this.$emit('on-submit', true)
    }
  }
}
</script>
<style>
</style>
