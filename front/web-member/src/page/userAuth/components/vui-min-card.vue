<template>
  <Row :gutter="20">
    <Col span="3" v-for="(item, index) in data" :key="index">
      <Card :padding="0" class="vui-min-card mb15" :class="{active: item.checked}" @click.native="handleClick(item)" :style="{background: item.background ? `url(${item.background}) top center no-repeat` : '', height: item.background ? '160px' : 'auto',overflow: 'hidden'}">
        <transition name="fade">
          <span class="triangle" v-if="item.checked"></span>
        </transition>
        <div class="tc pt10 pb10">
          <Icon v-if="item.icon" :size="32" :type="item.icon"></Icon>
          <img v-if="item.src" :src="`../../static/img/${item.src}.png`" height="28"></Icon>
          <p class="mt20" v-if="!item.background">{{item.name}}</p>
        </div>
      </Card>
      <p v-if="item.background" class="tc mt10" style="font-size:14px">
        {{item.name}}
      </p>
    </Col>
  </Row>
</template>
<script>
export default {
  props: {
    data: {
      type: Array,
      default: []
    }
  },
  data: () => ({
    checked: []
  }),
  methods: {
    handleClick (item) {
      this.checked = []
      item.checked = !item.checked
      if (item.disabled) this.$Message.info('该功能正在开发中……')
      // this.data.forEach((child) => {
      //   if (child.checked) this.checked.push(child)
      // })
      // 返回选中的数据
      this.$emit('on-click', this.data)
    }
  }
}
</script>
<style lang="scss" scoped>
.vui-min-card{
  position: relative;
  font-size: 12px;
  padding: 10px 0;
  cursor: pointer;
  min-height: 120px;
  &:hover{
    transform: translateY(-10px);
  }
  &.active{
    border: 1px solid #00c587
  }
}
.triangle{
  border-radius: 4px 0 0 0;
  overflow: hidden;
  width: 30px;
  height: 30px;
  &,&:before,&:after{
    position: absolute;
    top:0;
    left: 0;
  }
  &:before{
    content: '';
    border-style: solid;
    border-width: 30px 30px 0 0;
    border-color: #00c587 transparent transparent transparent;
  }
  &:after{
    left: 4px;
    top: 0;
    font-family: Ionicons;
    content: '\F121';
    color: #fff;
    font-size: 12px;
  }
}
</style>
