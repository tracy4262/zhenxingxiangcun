<template>
  <div>
      <Form :label-width="100">
        <FormItem label="好友提醒">
          <Input v-model="mydynamic.tixing" placeholder="好友提醒"></Input>
        </FormItem>
        <FormItem label="提醒内容">
          <Input v-model="mydynamic.con" placeholder="好友提醒"></Input>
        </FormItem>
        <Row>
            <Col span="12">
              <FormItem label="发送短信" prop="sms">
                <Radio-group v-model="mydynamic.sms">
                  <Radio label="是"></Radio>
                  <Radio label="否"></Radio>
                </Radio-group>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="提交首页显示" prop="xianshi">
                <Radio-group v-model="mydynamic.xianshi">
                  <Radio label="是"></Radio>
                  <Radio label="否"></Radio>
                </Radio-group>
              </FormItem>
            </Col>
        </Row>
        <FormItem label="公开范围" prop="security">
          <Radio-group v-model="mydynamic.security">
            <!-- var txt = this.mydynamic.security;
      if (txt == "所有人可见") {
        txt = 0;
      } else if (txt == "仅好友可见") {
        txt = 1;
      } else if (txt == "仅自己可见") {
        txt = 2;
      } else if (txt == "仅指定组可见") {
        txt = 3;
      } -->
            <Radio :label="0">所有人可见</Radio>
            <Radio :label="1">仅好友可见</Radio>
            <Radio :label="2">仅自己可见</Radio>
            <Radio :label="3">仅指定组可见</Radio>
          </Radio-group>
        </FormItem>
        <FormItem label="是否有偿" prop="money">
          <Radio-group v-model="mydynamic.money">
            <Radio label="收费"></Radio>
            <Radio label="免费"></Radio>
          </Radio-group>
        </FormItem>
        <div class="pl30 pr30">
          <div class="tc pb10"><Button @click="disclaimer = !disclaimer" class="t-green" type="text" size="large">免责声明</Button></div>
          <div v-if="disclaimer" style="background: #f8f8f8;" class="pd20">
            <p>1.本网站只提供一个信息交流平台，不控制发布内容。发布内容及因之产生的后果，均由发布者自行负责，</p>
            <p>与本网站无关。我们对用户使用本网站所产生的一切后果，不承担任何责任；</p>
            <p>2.发贴内容须符合中华人民共和国和所在国法律，符合当地道德规范和风俗文化。内容真实，无欺诈；</p>
            <p>3.谢绝一切与本网站主题无关的内容，尤其不涉及政治、宗教、种族等议题</p>
            <p>4.不发布涉及他人版权的内容，不发布与栏目无关之内容；</p>
            <p>5.不发布与传销、直销及其他事涉不规范经济活动有关的内容。</p>
          </div>
        </div>
      </Form>
  </div>
</template>
<script>
export default {
  props: {
    data: {
      type: Object,
      default: () => {
        return {}
      }
    },
    type: {
      type: String,
      default: '文章'
    }
  },
  data () {
    return {
      mydynamic: {
        sms: '否',
        xianshi: '是',
        security: '所有人可见',
        money: '免费',
        tixing: '',
        con: '',
      },
      disclaimer: false
    }
  },
  watch: {
    data: {
      handler(curVal, oldVal) {
        this.mydynamic = curVal
      },
      deep: true
    }
  },
  created() {
    this.mydynamic = this.data
  },
  methods: {
    next () {
      this.$emit('on-next', true)
    }
  },
}
</script>