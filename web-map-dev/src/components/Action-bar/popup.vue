<template>
  <!-- <div style="min-width: 120px;height:300px">
    <div class="pd9 pb7 vui-line"> -->
  <div style="min-width: 120px;height:100px">
    <div class="pl10 pt3">
      <Row type="flex" align="middle">
        <Col span="18" class="t-green">
          {{title}}
        </Col>
        <Col span="6" class="tr">
          <div class="pt5 pb5 pl15 pr15 close" @click="handleCancel">
            <b><Icon type="md-close"/></b>
          </div>
        </Col>
      </Row>
    </div>
    <div class="pd10 pt2">
      <div class="ell-3">
        <span class="close" @click="toPortals(activeList.YHM)">{{ name }}，</span>
        {{activeList.XZQY}}
        <span class="green" @click="handleMore">查看更多</span>
      </div>
    </div>
    <detail ref="detail"></detail>
  </div>
</template>

<script>
import detail from './detail';
export default {
  name: '',
  components: {
    detail
  },
  data() {
    return {
      title: '',
      currentView: '',
      name: '',
      activeList: {}
    }
  },
  methods: {
    toPortals (YHM) {
      if (YHM) {
        this.$toPortals(YHM)
      }
    },
    handleMore () {
      this.$refs['detail'].handleShowForm(this.activeList, this.title, this.name, this.currentView)
    },
    handleCancel () {
      this.$emit('on-close')
    },
    // 显示表单 并将数据传递给表单
    handleShowForm (list, title, name, currentView) {
      console.log('list', list)
      console.log('list', title)
      console.log('list', name)
      console.log('list', currentView)
      this.activeList = Object.assign({}, list)
      this.title = title
      this.name = name
      this.currentView = currentView
    }
  },
}
</script>

<style lang="less" scoped>
@import '../../css/colors.less';
  .close{
    cursor: pointer;
    &:hover{
      color: @link-color;
    }
  }
  .green{
    cursor: pointer;
    color: @link-color;
  }
</style>