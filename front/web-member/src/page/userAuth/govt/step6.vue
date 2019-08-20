<template>
  <website :userType="5" @on-back="handleClickBack" @on-next="handleClickNext"></website>
</template>
<script>
import website from '../components/website'
export default {
  components: {
    website
  },
  data: () => ({
  }),
  methods: {
    handleClickBack () {
      this.$router.push('/auth/govtAuth/step5')
    },
    handleClickNext () {
      this.$router.push('/pro/member')
    }
  }
}
</script>
<style lang="scss">
</style>
