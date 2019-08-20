<template>
  <Row type="flex" align="middle" class="pd10" style="border: 1px solid #EDEDED;">
    <Col span="14">
      <span class="tit ell cp" :style="grey?'color: #666':''" :title="name" @click="toPortal">
        <Avatar v-if="avatar" :src="avatar" style="vertical-align: bottom;height: 35px;" class="mr5"/>
        <img v-else src="../../../../img/default_header1.png" style="vertical-align: bottom;height:35px;" class="mr5">
        <span style="line-height: 35px;">{{name}}</span>
      </span>
    </Col>
    <Col span="10" class="tr">
      <slot></slot>
    </Col>
  </Row>
</template>

<script>
export default {
  props: {
    name: String,
    grey: {
      type: Boolean,
      default: false
    },
    avatar: {
      type: String,
      default: ''
    },
    account: {
      type: String,
      default: ''
    }
  },
  components: {

  },
  data () {
    return {

    }
  },
  methods: {
    toPortal () {
      this.$toPortals(this.account)
    }
  }
}
</script>

<style lang="scss" scoped>
.tit{
  font-size: 16px;
  font-weight: 700;
}
.cp {
  cursor: pointer;
}
</style>
