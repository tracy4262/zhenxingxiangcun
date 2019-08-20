<template>
    <div class="new-auth new-authlayout">
      <Card class="pd20">
        <!-- <Title title="会员虚拟信息" class="mt10"></Title>
        <virtual-information></virtual-information> -->
        <Title title="会员实名认证信息" class="mt10"></Title>
        <real-name></real-name>
      </Card>
    </div>
</template>
<script>
import Title from '../components/title'
import virtualInformation from './components/virtualInformation'
import realName from './components/realName'
export default {
  components: {
    Title,
    virtualInformation,
    realName
  },
  data: () => ({
  }),
  created() {
    this.handleInit()
  },
  methods: {
    // 初始化获取数据
    handleInit () {
    }
  }
}
</script>
<style lang="scss">
</style>
