<template>
  <div>
    <detail-title
    :title="data.propertytitle"
    @on-click="handleEdit">
    </detail-title>
    <Row type="flex" align="top" class="pt20 pl20">
      <Col :span="data.propertyimage.length ? 18 : 24" class="pr25">
        <!-- <h6 class="b">{{data.propertytitle}}</h6> -->
        <p class="content">
          &nbsp;
          {{data.propertycontent}}
        </p>
      </Col>
      <Col span="6" v-if="data.propertyimage.length">
        <viewer
          :options="options"
          :images="data.propertyimage"
          @inited="inited"
          class="viewer"
          ref="viewer">
          <div scope="scope">
            <img
              v-for="(item, index) in data.propertyimage"
              :key="index"
              :src="item"
              height="120"
              width="100%"
              v-show="index === 0"
              @click="show">
          </div>
        </viewer>
        <p class="tc t-grey" v-if="data.propertyimage">图册({{data.propertyimage.length}})</p>
      </Col>
    </Row>
  </div>
</template>
<script>
import detailTitle from '~components/title'
import 'viewerjs/dist/viewer.css'
import Viewer from 'v-viewer/src/component.vue'
export default {
  props: {
    data: {
      type: Object,
      default: {}
    }
  },
  components: {
    detailTitle,
    Viewer
  },
  data: () => ({
    options: {}
  }),
  methods: {
    // 编辑
    handleEdit () {
      this.$emit('on-edit')
    },
    inited (viewer) {
      this.$viewer = viewer
    },
    show () {
      this.$viewer.show()
    }
  }
}
</script>
<style lang="scss" scoped>
.content{
  text-indent: 2em;
  line-height: 24px;
  font-size: 14px;
  margin: 10px 0;
  color: #4A4A4A;
  text-align: justify;
}
</style>
