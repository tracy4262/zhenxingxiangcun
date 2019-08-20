<template>
  <Row type="flex" align="middle" class="pd10" style="border: 1px solid #EDEDED;">
    <Col span="14">
      <span class="tit ell" :style="grey?'color: #666':''" :title="name" @click="toPortals">
        <Avatar v-if="avatar" :src="avatar" style="vertical-align: bottom;" class="mr5"/>
        <img v-else src="../../../../img/default_header1.png" style="vertical-align: bottom;width:32px;" class="mr5">
        {{name}}
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
    }
  },
  components: {

  },
  data () {
    return {

    }
  },
  methods: {
    toPortals () {
      this.$router.push(`/portals/index?uid=${this.$route.query.uid}`)
    }
  }
}
</script>

<style lang="scss" scoped>
.tit{
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
}
</style>
