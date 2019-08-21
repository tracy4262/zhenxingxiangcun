<template>
  <div class="sales">
    <Form :label-width="150" label-position="left">
      <FormItem :label="`${item.label === undefined ? '' : item.label+'：'}`" :key="index" v-for="(item, index) in data">
        <Row>
          <Col span="22">
            <!-- 输入控件 -->
            <span  v-if="item.type === 'text'">
              {{item.value}}
            </span>
            <span  v-if="item.type === 'textarea'">
              {{item.value}}
            </span>
            <!-- 下拉控件 -->
            <span  v-if="item.type === 'select'">
              {{item.value}}
            </span>
            <!-- 单选框 -->
            <span  v-if="item.type === 'radio'">
              {{item.value.value}}
            </span>
            <!-- 复选框 -->
            <span  v-if="item.type === 'checkbox'">
              <span v-for="(e, index) in item.value" :key="index">{{ e }} </span>
            </span>
            <!-- 开关 -->
            <span  v-if="item.type === 'switch'">
              <span v-if="item.value">{{item.open}}</span>
              <span v-else>{{item.close}}</span>
            </span>
            <!-- 农药指标 -->
            <pesticide-pick
            v-if="item.type === 'pesticidePick'"
            :data="item.list">
            </pesticide-pick>
            <!-- 污染物指标 -->
            <pollute-pick
            v-if="item.type === 'pollutePick'"
            :data="item.list">
            </pollute-pick>
          </Col>
        </Row>
      </FormItem>
    </Form>
  </div>
</template>
<script>
import pesticidePick from './components/pesticide-pick'
import pollutePick from './components/pollute-pick'
export default {
  props: {
    data: Array,
    title: {
      type: String,
      default: '自定义控件'
    },
    operate: {
      type: Boolean,
      default: true
    }
  },
  components: {
    pesticidePick,
    pollutePick
  },
  data: () => ({
  }),
  methods: {
  }
}
</script>
<style lang="scss" scoped>
</style>
