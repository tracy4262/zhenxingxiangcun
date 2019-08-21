<template>
  <website :userType="3" @on-back="handleClickBack" @on-next="handleClickNext"></website>
</template>
<script>
import website from './components/website'
export default {
  components: {
    website
  },
  data: () => ({
  }),
  methods: {
    handleClickBack () {
      this.$router.push('/auth/step1')
    },
    handleClickNext () {
      this.$router.push('/auth/step3')
    }
  }
}
</script>
<style lang="scss">
</style>
