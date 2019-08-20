<template>
  <Row class="mt10">
    <Col span="22">
      <Input
        ref="input"
        v-model="content"
        :maxlength="300"
        :autosize="{minRows: 2,maxRows: 5}" 
        type="textarea"
        :placeholder="placeholder"
        @on-enter="handleCommentBtn"
        ></Input>
    </Col>
    <Col span="2" class="pl10 pr10" >
      <Button type="primary" long @click="handleCommentBtn">评论</Button>
    </Col>
  </Row>
</template>
<script>
export default {
  props: {
    placeholder: {
      type: String,
      default: '写下你的评论...'
    }
  },
  data: () => ({
    content: ''
  }),
  methods: {
    // 发表评论
    handleCommentBtn () {
      if (this.content) {
        let date = new Date()
        // 代理事件 数据取用户信息与输入信息
        this.$emit('on-reply', {
          author: {
            name: '登录用户名',
            avatar: 'http://www.ns51.cn:8999/00/6A/wKgHPVqcmj2AAEQFAAGogh1z-2k751.jpg'
          },
          content: this.content,
          createdTime: date.getTime(),
          like: 0,
          isReply: false,
          replyBoxShow: false
        })
        this.content = ''
      }
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
