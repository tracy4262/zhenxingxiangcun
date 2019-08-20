<template>
    <div class="new-auth new-authlayout mt30">
      <Row type="flex" align="middle">
        <Col span="12">
          <h2>完善资料（{{ account }})</h2>
        </Col>
        <Col span="12" class="tr">
          <Button type="primary" @click="back">返回</Button>
        </Col>
      </Row>
      <Steps :current="current" class="ml100 pt30 pb30">
        <Step title="完善实名信息"></Step>
        <Step title="上传代理协议"></Step>
        <Step title="提交认证"></Step>
      </Steps>
      <real-name :account="account" v-if="current === 0" @next="next1"></real-name>
      <upload-proxy-protocol :account="account" v-if="current === 1" @next="next2" @last="last2"></upload-proxy-protocol>
      <submit :account="account" v-if="current === 2"></submit>
    </div>
</template>
<script>
import virtualInformation from './components/virtualInformation'
import realName from './components/realName'
import uploadProxyProtocol from '../uploadProxyProtocol'
import submit from '../submit'
import { loadavg } from 'os';
export default {
  components: {
    virtualInformation,
    realName,
    uploadProxyProtocol,
    submit
  },
  props: {
    account: String
  },
  data: () => ({
    current: 0
  }),
  created() {
    this.handleInit()
  },
  methods: {
    // 初始化获取数据
    handleInit () {
    },
    next1 () {
      this.current = 1
    },
    next2 () {
      this.current = 2
    },
    last2 () {
      this.current = 0
    },
    back () {
      this.$emit('back')
    }
  }
}
</script>
<style lang="scss">
</style>
