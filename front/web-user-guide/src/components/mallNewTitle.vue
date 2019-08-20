<template>
  <div class="vui-new-title" :style="{'background':background}">
    <template v-if="more">
      <Row>
        <Col span="8">
            <span class="vui-new-title-left">{{text}}</span>
        </Col>
        <Col span="16" class="tr vui-new-title-right">
          <slot name="more"></slot>
        </Col>
      </Row>
    </template>
    <template v-else>
        <span class="vui-new-title-left">{{text}}</span>
    </template>
  </div>
</template>

<script>
export default {
  name: 'vui-content-block',
  props: {
    background: String,
    text: String,
    more: {
      type:Boolean,
      default: false
    }
  },
  computed: {
  }
}
</script>

<style lang="scss">
.vui-new-title{
  padding:5px 8px;
  &-left{
    font-size: 18px;
    font-weight: 700;
    padding-left: 5px;
    border-left:2px solid #FF7921;
  }
  &-right{
    a{
      color:#4a4a4a;
    }
  }
}
</style>
