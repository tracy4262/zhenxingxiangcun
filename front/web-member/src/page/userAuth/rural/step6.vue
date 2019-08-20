<template>
  <website :userType="4" @on-back="handleClickBack" @on-next="handleClickNext"></website>
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
      this.$router.push('/auth/ruralAuth/step5')
    },
    handleClickNext () {
      this.$router.push('/pro/member')
    }
  }
}
</script>
<style lang="scss">
</style>
