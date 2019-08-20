<template>
  <div class="tc">
    <Icon type="md-checkmark-circle" size="100" color="#00c587" />
    <h2 class="mt20">您已提交认证信息，审核工作将在<span class="t-red"><strong>三个工作日</strong></span>内完成，请耐心等待。</h2>
    <div class="tc pt30 pb20">
      <Button type="primary" @click="handleNext">完成</Button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    handleNext () {
      this.$router.push('/member/pendingApproval')
    }
  }
}
</script>

<style>

</style>
