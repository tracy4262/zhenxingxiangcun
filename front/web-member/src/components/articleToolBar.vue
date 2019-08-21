<template>
  <Row type="flex" align="middle">
    <Col span="2">
      <Button type="text" @click.native="handleCollect"><Icon type="star" size="16"></Icon> 收藏</Button>
    </Col>
    <Col span="2">
      <Button type="text" @click.native="handleFollow"><Icon type="heart" size="15"></Icon> 关注</Button>
    </Col>
    <Col span="18">
      <div class="zanBtn">
        <Button @click="handleLike" shape="circle">
          <span class="zanIcon"></span>
          <span>赞</span>
          <span>|</span>
          <span>{{like}}</span>
        </Button>
      </div>
    </Col>
    <Col span="2">
    <Button type="text" @click.native="reportModal = true"><Icon type="alert-circled" size="16"></Icon> 举报</Button>
    </Col>

    <!--举报的弹框-->
    <Modal v-model="reportModal">
      <p slot="header" style="text-align:center;">举报</p>
      <Form :model="reportForm">
        <Row>
          <Col span="8" v-for="btn in btnlist">
            <Button :class="{activeEle:active}" @click="addActive($event,btn.text)">{{btn.text}}</Button>
          </Col>
        </Row>
        <div>
          <h3>截图证明</h3>
          <div class="jietu">
            <img src="../img/jietu-icon.png" />
            <img src="../img/jietu-add.png" />
          </div>
        </div>
        <div>
          <h3>举报详情</h3>
          <textarea v-model="reportForm.detail" rows="5" style="width:100%;border-radius:5px;margin-top:15px;"></textarea>
        </div>
      </Form>
      <div slot="footer" style="text-align:center;">
        <Button type="primary" shape="circle" class="btnReset" style="width:200px;height:50px;" @click="handleReport">举报</Button>
        <span>取消</span>
      </div>
    </Modal>
  </Row> 
</template>

<script>
export default {
  props: {
  },
  computed: {
  },
  data () {
    return {
      reportForm: {
        detail: ''
      },
      reportModal: false,
      active: false,
      like: 0,
      btnlist: [
        { text: '垃圾广告' },
        { text: '色情淫秽' },
        { text: '政治反动' },
        { text: '钓鱼诈骗' },
        { text: '网络敲诈' }
      ]
    }
  },
  methods: {
    // 收藏
    handleCollect () {
      this.$emit('on-collect')
    },
    // 关注
    handleFollow () {
      this.$emit('on-follow')
    },
    // 点赞
    handleLike () {
      this.$emit('on-like')
    },
    // 举报
    handleReport () {
      this.$emit('on-report')
    },
    addActive (event, value) {
      text = value;
      var btnDom = event.currentTarget
      btnDom.active = !btnDom.active
    }
  }
}
</script>

<style lang="scss">
</style>
