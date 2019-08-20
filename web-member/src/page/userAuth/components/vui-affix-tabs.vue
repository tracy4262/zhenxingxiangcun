<template>
  <Row>
    <Col span="4">
      <Affix @on-change="handleAffixChange" :offset-top="98">
        <Card :padding="0" :style="{'height': height}">
          <scrollactive ref="scrollactive"
            :offset="52"
            :always-track="false"
            :duration="600"
            :click-to-scroll="true"
            bezier-easing-value=".5,0,.35,1"
            @itemchanged="handleItemChange">
            <div class="vui-affix-vertical-tabs">
              <a
              v-for="(item, index) in data"
              :key="index" :href="`#${item.name}`"
              class="scrollactive-item block"
              @click="handleItemClick(index)" v-if="!item.none">{{item.title}}</a>
            </div>
          </scrollactive>
        </Card>
    </Affix>
    </Col>
    <Col span="20" class="pl20">
      <Card>
        <slot></slot>
      </Card>
    </Col>
  </Row>
</template>
<script>
export default {
  props: {
    data: Array
  },
  data: () => ({
    elements: [],
    flag: false,
    height: '',
    index: 0
  }),
  created(){
  },
  methods: {
    // 滚动改变
    handleItemChange (event, currentItem, lastActiveItem) {
    },
    // 点击
    handleItemClick (index) {
    },
    handleAffixChange (flag) {
    }
  },
  watch: {
  },
  mounted () {
    // this.height = `${window.innerHeight}px`
  },
  updated() {
    this.elements = this.$el.querySelectorAll('.scrollactive-item')
    this.elements.forEach((item, index) => {
      this.elements[index].index = index
    })
  }
}
</script>
<style lang="scss" scoped>
.scrollactive-nav{
  position: relative;
}
.vui-affix-vertical-tabs {
  padding-top: 10px;
  a{
    padding: 2px 25px;
    color: #333;
    margin: 4px 0;
    border-left: 2px solid transparent;
    &.is-active{
      color: #3DBD7D;
      border-left-color:#3dbd7d;
    }
  }
  .block{
    display: block;
  }
}
</style>
