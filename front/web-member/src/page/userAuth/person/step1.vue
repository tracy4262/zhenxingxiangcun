<template>
    <div>
      <Card>
        <p class="t-orange pt20 pb20">您的身份信息仅用于实名认证，我们不会将此信息用于其它任何地方，实名认证后才能使用会员中心各项功能，请慎重填写。</p>
        <Title title="实名认证"></Title>
        <name-authority></name-authority>
      </Card>
    </div>
</template>
<script>
import Title from '../components/title'
import nameAuthority from './nameAuthority'
export default {
  components: {
    Title,
    nameAuthority
  },
  data: () => ({
  })
}
</script>
<style lang="scss">
</style>
