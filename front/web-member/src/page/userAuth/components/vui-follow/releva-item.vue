<template>
  <Row type="flex" align="middle">
    <Col :span="span">
      <template v-if="title === '物种' || title === '知识'">
        <Tag color="#FF7921">{{title}}（{{data.length}}）</Tag>
      </template>
      <template v-else-if="title === '产品' || title === '资讯'">
        <Tag color="#4B8EFF">{{title}}（{{data.length}}）</Tag>
      </template>
      <template v-else>
        <Tag color="#F5A623">{{title}}（{{data.length}}）</Tag>
      </template>
    </Col>
    <Col :span="24 - span">
      <Col :span="span" v-for="(item, index) in data" :key="index"><span class="item">{{item.name}}</span></Col>
    </Col>
  </Row>
</template>
<script>
export default {
  props: {
    title: String,
    span: {
      type: Number,
      default: 3
    },
    data: {
      type: Array,
      default () {
        return []
      }
    }
  },
  data: () => ({
  })
}
</script>
<style lang="scss" scoped>
.item{
  display: block;
}
</style>
