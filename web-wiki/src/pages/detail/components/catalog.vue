<template>
  <Row type="flex" align="middle" class="wiki-detail-catalog">
    <Col span="3">
      <div class="wiki-detail-catalog-left b h5" :style="style">目录</div>
    </Col>
    <Col span="21">
      <vui-affix-tabs :data="catalogData" :type="1" :height="style"></vui-affix-tabs>
    </Col>
  </Row>
</template>
<script>
import vuiAffixTabs from '~components/vui-affix-tabs'
export default {
  props: {
    catalogData: {
      type: Array,
      default: []
    }
  },
  components: {
    vuiAffixTabs
  },
  data: () => ({
    elements: []
  }),
  computed: {
    style () {
      let height = ''
      if (this.catalogData.length < 12) {
        height = `${3 * 28}px`
      } else if (this.catalogDatadata.length < 18) {
        height = `${4 * 28}px`
      } else if (this.catalogData.length < 24) {
        height = `${5 * 28}px`
      } else {
        height = `${6 * 28}px`
      }
      return {
        height: height
      }
    }
  },
  updated () {
    this.elements = this.$el.querySelectorAll('.catalog-right .scrollactive-item')
    this.elements.forEach((item, index) => {
      this.elements[index].index = index
    })
  }
}
</script>
<style lang="scss">
.wiki-detail-catalog {
  border: 1px solid #f2f2f2;
  &-left {
    padding: 10px 20px;
    background: #f8f8f8;
    border-right: 1px solid #f2f2f2;
  }
}
</style>
