<template>
  <div>
    <Modal
        v-model="isCheckPackage" width="900" :mask-closable="false"
        title="评价">
        <!-- <div style="border: 1px solid #5EB758; background: #F9FEF8;" class="pt30 mt20" > -->
        <div class="pt10 mt20" >
          <Form :label-width="110">
            <Form-item label="满意度">
              <Rate allow-half v-model="commentRate" show-text ></Rate>
            </Form-item>
            <Form-item label="评价" class="pr50">
              <Input v-model.trim="commentText" type="textarea" :autosize="{minRows: 5,maxRows: 8}" placeholder="请输入..." :maxlength="500"></Input>
              <Row>
                <Col span="12">
                &nbsp;
                  <span style="color:#a0a0a0;" class="pl5" v-if="!commentText.length">评论内容不能为空</span>
                </Col>
                <Col span="12">
                  <p class="tr pr10"><span>{{commentText.length}}/500</span></p>
                </Col>
              </Row>
            </Form-item>
            <!-- <Form-item label=" ">
              <Button type="warning" @click="handleComment">发布评论</Button> 
              <span style="color:#a0a0a0;" class="pl50" v-if="!commentText.length">评论内容不能为空</span>
            </Form-item> -->
          </Form>
        </div>
        <div slot="footer" class="tc">
            <Button type="text" @click="isCheckPackage = false">取消</Button>
            <Button type="primary" @click="handleComment">发布评论</Button>
        </div>
    </Modal>
  </div>
</template>
<script>
export default {
  data () {
    return {
      isCheckPackage:false,
      commentText: '',
      commentRate: 0,
      data: {}
    }
  },
  methods: {
      showComment (data) {
        this.data = data
        this.commentRate = 0
        this.commentText = ''
        this.isCheckPackage = true
      },
      // 点击发布评价
      handleComment () {
        if (this.commentRate > 0) {
          if (this.commentText) {
            let data = {
              serviceId: this.data.serviceId,
              account: this.$user.loginAccount,
              star: this.commentRate*2,
              describeInfo: this.commentText,
            }
            this.$api.post('/member/fishing/saveComment',data).then(response => {
              if (response.code === 200) {
                this.$Message.success('评论成功')
                this.commentRate = 0
                this.commentText = ''
                this.isCheckPackage = false
                this.$emit('on-save', this.data)
              }
            })
          } else {
            this.$Message.error('请输入评价内容。')
            this.showTip = true
          }
        } else {
          this.$Message.error('请选择满意度。')
        }
      },
  },
}
</script>