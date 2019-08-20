<template>
  <Modal v-model="modalShow" width="1200" :mask-closable="false" title="出库" :styles="{top: '60px'}">
      <outStore @close="getExitOrderList" ref="out"></outStore>
      <div slot="footer"></div>
    <outboundOrder ref="outboundOrder"></outboundOrder>
  </Modal>
</template>

<script>
import outStore from '../../../inventoryControl/component/outStore'
import outboundOrder from '../../../inventoryControl/component/outboundOrder'
export default {
  components: {
    outStore,
    outboundOrder
  },
  data () {
    return {
      modalShow: false
    }
  },
  created () {
  },
  methods: {
    outStoreInit (order) {
        this.modalShow = true
        this.$refs['out'].outStoreInit()
        this.$refs['out'].showOrder(order)
    },
    getExitOrderList (type, order) {
      this.modalShow = false
      if (order) {
        this.$api.post('/shop/inventory/basicSetting/exitOrder', {
          account:this.$user.loginAccount,
          order: order
        }).then(response => {
          if (response.code === 200) {
          this.$refs['outboundOrder'].init(response.data, response.data.list)
          }
        })
      }
    },
  }
}
</script>