<template>
  <Card dis-hover>
    <vui-comments-item :data="data"></vui-comments-item>
    <div class="tc">
      <Page :total="100" size="small"></Page>
    </div>
    <vui-reply @on-reply="handleSendReply"></vui-reply>
  </Card>
</template>
<script>
import vuiCommentsItem from './item'
import vuiReply from './reply'
export default {
  props: {
    data: {
      type: Array,
      default () {
        return []
      }
    }
  },
  components: {
    vuiCommentsItem,
    vuiReply
  },
  data: () => ({
  }),
  methods: {
    // 发表评论
    handleSendReply (data) {
      this.data.push(data)
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
